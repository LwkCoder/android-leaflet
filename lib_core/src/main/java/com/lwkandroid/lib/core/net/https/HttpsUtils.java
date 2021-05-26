/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwkandroid.lib.core.net.https;


import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.utils.common.CloseUtils;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Https工具类,来自RxEasyHttp
 *
 * @author LWK
 */
public final class HttpsUtils
{
    private HttpsUtils()
    {
    }

    private static final String TAG = "HttpsUtils";

    /**
     * 信任证书类型
     */
    private static final String TRUST_KEYSTORE_TYPE = "BKS";
    /**
     * 协议类型
     */
    private static final String PROTOCOL_TYPE = "TLS";
    /**
     * 认证标准
     */
    private static final String CERTIFICATE_STANDARD = "X.509";
    private static final String CERTIFICATE_PROVIDER = "BC";

    public static class SSLParams
    {
        private SSLSocketFactory mSLSocketFactory;
        private X509TrustManager mTrustManager;

        public SSLSocketFactory getSSLSocketFactory()
        {
            return mSLSocketFactory;
        }

        public void setsSLSocketFactory(SSLSocketFactory sSLSocketFactory)
        {
            this.mSLSocketFactory = sSLSocketFactory;
        }

        public X509TrustManager getTrustManager()
        {
            return mTrustManager;
        }

        public void setTrustManager(X509TrustManager trustManager)
        {
            this.mTrustManager = trustManager;
        }
    }

    public static SSLParams createSSLParams(InputStream bksFile, String password, InputStream[] certificates)
    {
        SSLParams sslParams = new SSLParams();
        try
        {
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TYPE);
            X509TrustManager x509TrustManager;
            if (trustManagers != null)
            {
                x509TrustManager = new MyTrustManager(chooseTrustManager(trustManagers));
            } else
            {
                x509TrustManager = new UnSafeTrustManager();
            }
            sslContext.init(keyManagers, new TrustManager[]{x509TrustManager}, new SecureRandom());

            sslParams.setsSLSocketFactory(sslContext.getSocketFactory());
            sslParams.setTrustManager(x509TrustManager);
            return sslParams;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e)
        {
            throw new AssertionError(e);
        }
    }

    private static TrustManager[] prepareTrustManager(InputStream... certificates)
    {
        if (certificates == null || certificates.length <= 0)
        {
            return null;
        }
        try
        {
            //若此处不加参数 "BC" 会报异常：CertificateException - OpenSSLX509CertificateFactory$ParsingException
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CERTIFICATE_STANDARD, CERTIFICATE_PROVIDER);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                if (certificate != null)
                {
                    certificate.close();
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.e(TAG, "prepareTrustManager fail:" + e.toString());
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password)
    {
        try
        {
            if (bksFile == null || password == null)
            {
                return null;
            }
            KeyStore clientKeyStore = KeyStore.getInstance(TRUST_KEYSTORE_TYPE);
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.e(TAG, "prepareKeyManager fail:" + e.toString());
        } finally
        {
            if (bksFile != null)
            {
                CloseUtils.closeIO(bksFile);
            }
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
    {
        for (TrustManager trustManager : trustManagers)
        {
            if (trustManager instanceof X509TrustManager)
            {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private static class UnSafeTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[]{};
        }
    }

    private static class MyTrustManager implements X509TrustManager
    {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException
        {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(factory.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
        {
            try
            {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce)
            {
                ce.printStackTrace();
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }
    }
}
