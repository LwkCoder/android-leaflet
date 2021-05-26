package com.lwkandroid.lib.common.permission;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.Rationale;

import java.io.File;
import java.util.List;

/**
 * Description:AndPermission权限弹窗帮助类
 *
 * @author LWK
 * @date 2020/3/11
 */
public final class AndPermissionHelper
{
    private AndPermissionHelper()
    {
    }

    public static final int REQUEST_CODE_SETTING = 100;

    /**
     * 运行时权限被拒绝后的解释性过程
     */
    private static Rationale<List<String>> mRuntimeDeniedRationale;

    /**
     * 运行时权限被拒绝后的操作
     */
    private static Action<List<String>> mRuntimeDeniedAction;

    /**
     * 安装apk权限被拒绝后的解释性过程
     */
    private static Rationale<File> mInstallDeniedRationale;

    /**
     * 安装apk时权限被拒绝后的操作
     */
    private static Action<File> mInstallDeniedAction;

    /**
     * 悬浮窗权限被拒绝后的解释性过程
     */
    private static Rationale<Void> mOverlayDeniedRationale;

    /**
     * 悬浮窗权限被拒绝后的操作
     */
    private static Action<Void> mOverlayDeniedAction;

    /**
     * 发送通知栏权限被拒绝后的解释性过程
     */
    private static Rationale<Void> mNotificationShowDeniedRationale;

    /**
     * 发送通知栏权限被拒绝后的操作
     */
    private static Action<Void> mNotificationShowDeniedAction;

    /**
     * 访问通知栏权限被拒绝后的解释性过程
     */
    private static Rationale<Void> mNotificationAccessDeniedRationale;

    /**
     * 访问通知栏权限被拒绝后的操作
     */
    private static Action<Void> mNotificationAccessDeniedAction;

    public static Rationale<List<String>> getRuntimeDeniedRationale()
    {
        if (mRuntimeDeniedRationale == null)
        {
            mRuntimeDeniedRationale = new DefaultRuntimeDeniedRationale();
        }
        return mRuntimeDeniedRationale;
    }

    public static void setRuntimeDeniedRationale(Rationale<List<String>> rationale)
    {
        AndPermissionHelper.mRuntimeDeniedRationale = rationale;
    }

    public static Action<List<String>> getRuntimeDeniedAction()
    {
        if (mRuntimeDeniedAction == null)
        {
            mRuntimeDeniedAction = new DefaultRuntimeDeniedAction();
        }
        return mRuntimeDeniedAction;
    }

    public static void setRuntimeDeniedAction(Action<List<String>> action)
    {
        AndPermissionHelper.mRuntimeDeniedAction = action;
    }

    public static Rationale<File> getInstallDeniedRationale()
    {
        if (mInstallDeniedRationale == null)
        {
            mInstallDeniedRationale = new DefaultInstallDeniedRationale();
        }
        return mInstallDeniedRationale;
    }

    public static void setInstallDeniedRationale(Rationale<File> rationale)
    {
        AndPermissionHelper.mInstallDeniedRationale = rationale;
    }

    public static Action<File> getInstallDeniedAction()
    {
        if (mInstallDeniedAction == null)
        {
            mInstallDeniedAction = new DefaultInstallDeniedAction();
        }
        return mInstallDeniedAction;
    }

    public static void setInstallDeniedAction(Action<File> action)
    {
        AndPermissionHelper.mInstallDeniedAction = action;
    }

    public static Rationale<Void> getOverlayDeniedRationale()
    {
        if (mOverlayDeniedRationale == null)
        {
            mOverlayDeniedRationale = new DefaultOverlayDeniedRationale();
        }
        return mOverlayDeniedRationale;
    }

    public static void setOverlayDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionHelper.mOverlayDeniedRationale = rationale;
    }

    public static Action<Void> getOverlayDeniedAction()
    {
        if (mOverlayDeniedAction == null)
        {
            mOverlayDeniedAction = new DefaultOverlayDeniedAction();
        }
        return mOverlayDeniedAction;
    }

    public static void setOverlayDeniedAction(Action<Void> action)
    {
        AndPermissionHelper.mOverlayDeniedAction = action;
    }

    public static Rationale<Void> getNotificationShowDeniedRationale()
    {
        if (mNotificationShowDeniedRationale == null)
        {
            mNotificationShowDeniedRationale = new DefaultShowNotificationDeniedRationale();
        }
        return mNotificationShowDeniedRationale;
    }

    public static void setNotificationShowDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionHelper.mNotificationShowDeniedRationale = rationale;
    }

    public static Action<Void> getNotificationShowDeniedAction()
    {
        if (mNotificationShowDeniedAction == null)
        {
            mNotificationShowDeniedAction = new DefaultShowNotificationDeniedAction();
        }
        return mNotificationShowDeniedAction;
    }

    public static void setNotificationShowDeniedAction(Action<Void> action)
    {
        AndPermissionHelper.mNotificationShowDeniedAction = action;
    }

    public static Rationale<Void> getNotificationAccessDeniedRationale()
    {
        if (mNotificationAccessDeniedRationale == null)
        {
            mNotificationAccessDeniedRationale = new DefaultAccessNotificationDeniedRationale();
        }
        return mNotificationAccessDeniedRationale;
    }

    public static void setNotificationAccessDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionHelper.mNotificationAccessDeniedRationale = rationale;
    }

    public static Action<Void> getNotificationAccessDeniedAction()
    {
        if (mNotificationAccessDeniedAction == null)
        {
            mNotificationAccessDeniedAction = new DefaultAccessNotificationDeniedAction();
        }
        return mNotificationAccessDeniedAction;
    }

    public static void setNotificationAccessDeniedAction(Action<Void> action)
    {
        AndPermissionHelper.mNotificationAccessDeniedAction = action;
    }
}
