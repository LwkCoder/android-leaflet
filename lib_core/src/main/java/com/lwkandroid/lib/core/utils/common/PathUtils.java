package com.lwkandroid.lib.core.utils.common;

import android.os.Build;
import android.os.Environment;

import com.lwkandroid.lib.core.context.AppContext;

import java.io.File;

/**
 * 路径工具类
 */
public final class PathUtils
{

    private PathUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the path of /system.
     *
     * @return the path of /system
     */
    public static String getRootPath()
    {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    /**
     * Return the path of /data.
     *
     * @return the path of /data
     */
    public static String getDataPath()
    {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    /**
     * Return the path of /cache.
     *
     * @return the path of /cache
     */
    public static String getDownloadCachePath()
    {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
    }

    /**
     * Return the path of /data/data/package.
     *
     * @return the path of /data/data/package
     */
    public static String getInAppDataPath()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        {
            return AppContext.get().getApplicationInfo().dataDir;
        }
        return getAbsolutePath(AppContext.get().getDataDir());
    }

    /**
     * Return the path of /data/data/package/code_cache.
     *
     * @return the path of /data/data/package/code_cache
     */
    public static String getInAppCodeCacheDir()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            return AppContext.get().getApplicationInfo().dataDir + "/code_cache";
        }
        return getAbsolutePath(AppContext.get().getCodeCacheDir());
    }

    /**
     * Return the path of /data/data/package/cache.
     *
     * @return the path of /data/data/package/cache
     */
    public static String getInAppCachePath()
    {
        return getAbsolutePath(AppContext.get().getCacheDir());
    }

    /**
     * Return the path of /data/data/package/databases.
     *
     * @return the path of /data/data/package/databases
     */
    public static String getInAppDbsPath()
    {
        return AppContext.get().getApplicationInfo().dataDir + "/databases";
    }

    /**
     * Return the path of /data/data/package/databases/name.
     *
     * @param name The name of database.
     * @return the path of /data/data/package/databases/name
     */
    public static String getInAppDbPath(String name)
    {
        return getAbsolutePath(AppContext.get().getDatabasePath(name));
    }

    /**
     * Return the path of /data/data/package/files.
     *
     * @return the path of /data/data/package/files
     */
    public static String getInAppFilesPath()
    {
        return getAbsolutePath(AppContext.get().getFilesDir());
    }

    /**
     * Return the path of /data/data/package/shared_prefs.
     *
     * @return the path of /data/data/package/shared_prefs
     */
    public static String getInAppSpPath()
    {
        return AppContext.get().getApplicationInfo().dataDir + "/shared_prefs";
    }

    /**
     * Return the path of /data/data/package/no_backup.
     *
     * @return the path of /data/data/package/no_backup
     */
    public static String getInAppNoBackupFilesPath()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            return AppContext.get().getApplicationInfo().dataDir + "/no_backup";
        }
        return getAbsolutePath(AppContext.get().getNoBackupFilesDir());
    }

    /**
     * Return the path of /storage/emulated/0.
     *
     * @return the path of /storage/emulated/0
     */
    @Deprecated
    public static String getExStoragePath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    /**
     * Return the path of /storage/emulated/0/Music.
     *
     * @return the path of /storage/emulated/0/Music
     */
    @Deprecated
    public static String getExMusicPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Podcasts.
     *
     * @return the path of /storage/emulated/0/Podcasts
     */
    @Deprecated
    public static String getExPodcastsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Ringtones.
     *
     * @return the path of /storage/emulated/0/Ringtones
     */
    @Deprecated
    public static String getExRingtonesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Alarms.
     *
     * @return the path of /storage/emulated/0/Alarms
     */
    @Deprecated
    public static String getExAlarmsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Notifications.
     *
     * @return the path of /storage/emulated/0/Notifications
     */
    @Deprecated
    public static String getExNotificationsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Pictures.
     *
     * @return the path of /storage/emulated/0/Pictures
     */
    @Deprecated
    public static String getExPicturesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Movies.
     *
     * @return the path of /storage/emulated/0/Movies
     */
    @Deprecated
    public static String getExMoviesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Download.
     *
     * @return the path of /storage/emulated/0/Download
     */
    @Deprecated
    public static String getExDownloadsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/DCIM.
     *
     * @return the path of /storage/emulated/0/DCIM
     */
    @Deprecated
    public static String getExDcimPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Documents.
     *
     * @return the path of /storage/emulated/0/Documents
     */
    @Deprecated
    public static String getExDocumentsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     *
     * @return the path of /storage/emulated/0/Android/data/package
     */
    public static String getExAppDataPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        File externalCacheDir = AppContext.get().getExternalCacheDir();
        if (externalCacheDir == null)
        {
            return "";
        }
        return getAbsolutePath(externalCacheDir.getParentFile());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/cache.
     *
     * @return the path of /storage/emulated/0/Android/data/package/cache
     */
    public static String getExAppCachePath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalCacheDir());
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files
     */
    public static String getExAppFilesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(null));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Music.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Music
     */
    public static String getExAppMusicPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
     */
    public static String getExAppPodcastsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
     */
    public static String getExAppRingtonesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
     */
    public static String getExAppAlarmsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
     */
    public static String getExAppNotificationsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
     *
     * @return path of /storage/emulated/0/Android/data/package/files/Pictures
     */
    public static String getExAppPicturesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Movies
     */
    public static String getExAppMoviesPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Download.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Download
     */
    public static String getExAppDownloadPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
     */
    public static String getExAppDcimPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
     *
     * @return the path of /storage/emulated/0/Android/data/package/files/Documents
     */
    public static String getExAppDocumentsPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            return getAbsolutePath(AppContext.get().getExternalFilesDir(null)) + "/Documents";
        }
        return getAbsolutePath(AppContext.get().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    /**
     * Return the path of /storage/emulated/0/Android/obb/package.
     *
     * @return the path of /storage/emulated/0/Android/obb/package
     */
    public static String getExAppObbPath()
    {
        if (isExStorageDisable())
        {
            return "";
        }
        return getAbsolutePath(AppContext.get().getObbDir());
    }

    private static boolean isExStorageDisable()
    {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static String getAbsolutePath(final File file)
    {
        if (file == null)
        {
            return "";
        }
        return file.getAbsolutePath();
    }
}
