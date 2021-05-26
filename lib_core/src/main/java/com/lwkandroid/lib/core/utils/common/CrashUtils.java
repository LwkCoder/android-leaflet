package com.lwkandroid.lib.core.utils.common;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;

import com.lwkandroid.lib.core.context.AppContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

/**
 * 崩溃相关工具类
 *
 * @author LWK
 */
public final class CrashUtils
{
    private static String defaultDir;
    private static String dir;
    private static String versionName;
    private static long versionCode;

    private static ExecutorService sExecutor;

    private static final String FILE_SEP = System.getProperty("file.separator");
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH-mm-ss");

    private static final String CRASH_HEAD;

    private static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    private static OnCrashListener sOnCrashListener;

    static
    {
        versionName = AppUtils.getAppVersionName();
        versionCode = AppUtils.getAppVersionCode();

        CRASH_HEAD = "************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK 版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(final Thread t, final Throwable e)
            {
                if (e == null)
                {
                    if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null)
                    {
                        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, null);
                    } else
                    {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                    return;
                }
                if (sOnCrashListener != null)
                {
                    sOnCrashListener.onCrash(e);
                }
                Date now = new Date(System.currentTimeMillis());
                String fileName = FORMAT.format(now) + ".txt";
                final String fullPath = (dir == null ? defaultDir : dir) + fileName;
                if (!createOrExistsFile(fullPath))
                {
                    return;
                }
                if (sExecutor == null)
                {
                    sExecutor = new ThreadPoolExecutor(1, 1,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>(), r -> new Thread(r, "CrashLogPrinter"));
                }
                sExecutor.execute(() -> {
                    PrintWriter pw = null;
                    try
                    {
                        pw = new PrintWriter(new FileWriter(fullPath, false));
                        pw.write(CRASH_HEAD);
                        e.printStackTrace(pw);
                        Throwable cause = e.getCause();
                        while (cause != null)
                        {
                            cause.printStackTrace(pw);
                            cause = cause.getCause();
                        }
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();
                    } finally
                    {
                        if (pw != null)
                        {
                            pw.close();
                        }
                    }
                });
                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null)
                {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private CrashUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     */
    public static void init()
    {
        init("");
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir 崩溃文件存储目录
     */
    public static void init(@NonNull final File crashDir)
    {
        init(crashDir.getAbsolutePath(), null);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir 崩溃文件存储目录
     */
    public static void init(final String crashDir)
    {
        init(crashDir, null);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param onCrashListener 崩溃监听事件
     */
    public static void init(final OnCrashListener onCrashListener)
    {
        init("", onCrashListener);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir        崩溃文件存储目录
     * @param onCrashListener 崩溃监听事件
     */
    public static void init(@NonNull final File crashDir, final OnCrashListener onCrashListener)
    {
        init(crashDir.getAbsolutePath(), onCrashListener);
    }

    /**
     * 初始化
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir        崩溃文件存储目录
     * @param onCrashListener 崩溃监听事件
     */
    public static void init(final String crashDir, final OnCrashListener onCrashListener)
    {
        if (isSpace(crashDir))
        {
            dir = null;
        } else
        {
            dir = crashDir.endsWith(FILE_SEP) ? crashDir : crashDir + FILE_SEP;
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && AppContext.get().getExternalCacheDir() != null)
        {
            defaultDir = AppContext.get().getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        } else
        {
            defaultDir = AppContext.get().getCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        sOnCrashListener = onCrashListener;
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }

    private static boolean createOrExistsFile(final String filePath)
    {
        File file = new File(filePath);
        if (file.exists())
        {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile()))
        {
            return false;
        }
        try
        {
            return file.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file)
    {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s)
    {
        if (s == null)
        {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i)
        {
            if (!Character.isWhitespace(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public interface OnCrashListener
    {
        void onCrash(Throwable e);
    }
}