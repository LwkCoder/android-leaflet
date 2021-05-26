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

package com.lwkandroid.lib.core.net.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lwkandroid.lib.core.context.AppContext;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.utils.common.AppUtils;
import com.lwkandroid.lib.core.utils.encode.EncodeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Cookie存储器
 */
public class PersistentCookieStore
{
    private static final String COOKIE_PREFS = "Cookies_Prefs";

    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore()
    {
        cookiePrefs = AppContext.get().getSharedPreferences(AppUtils.getAppName(), Context.MODE_PRIVATE);
        cookies = new HashMap<>();
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet())
        {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames)
            {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null)
                {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null)
                    {
                        if (!cookies.containsKey(entry.getKey()))
                        {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    protected String getCookieToken(Cookie cookie)
    {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie)
    {
        String name = getCookieToken(cookie);
        // 添加 host key. 否则有可能抛空.
        if (!cookies.containsKey(url.host()))
        {
            cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>(8));
        }
        // 删除已经有的.
        if (cookies.containsKey(url.host()))
        {
            cookies.get(url.host()).remove(name);
        }
        // 添加新的进去
        cookies.get(url.host()).put(name, cookie);
        // 是否保存到 SP 中
        if (cookie.persistent())
        {
            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
            prefsWriter.apply();
        } else
        {
            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            prefsWriter.remove(url.host());
            prefsWriter.remove(name);
            prefsWriter.apply();
        }
    }

    public void addCookies(List<Cookie> cookies)
    {
        for (Cookie cookie : cookies)
        {
            String domain = cookie.domain();
            ConcurrentHashMap<String, Cookie> domainCookies = this.cookies.get(domain);
            if (domainCookies == null)
            {
                domainCookies = new ConcurrentHashMap<>(8);
                this.cookies.put(domain, domainCookies);
            }
        }
    }

    public List<Cookie> get(HttpUrl url)
    {
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host()))
        {
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    public boolean removeAll()
    {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl url, Cookie cookie)
    {
        String name = getCookieToken(cookie);

        if (cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name))
        {
            cookies.get(url.host()).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name))
            {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
            prefsWriter.apply();

            return true;
        } else
        {
            return false;
        }
    }

    public List<Cookie> getCookies()
    {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet())
        {
            ret.addAll(cookies.get(key).values());
        }

        return ret;
    }

    /**
     * cookies to string
     */
    protected String encodeCookie(SerializableOkHttpCookies cookie)
    {
        if (cookie == null)
        {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e)
        {
            KLog.d("IOException in encodeCookie" + e.getMessage());
            return null;
        }

        return EncodeUtils.hex().encode(os.toByteArray());
    }

    /**
     * String to cookies
     */
    protected Cookie decodeCookie(String cookieString)
    {
        byte[] bytes = EncodeUtils.hex().decodeToBytes(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e)
        {
            KLog.d("IOException in decodeCookie" + e.getMessage());
        } catch (ClassNotFoundException e)
        {
            KLog.d("ClassNotFoundException in decodeCookie" + e.getMessage());
        }

        return cookie;
    }
}