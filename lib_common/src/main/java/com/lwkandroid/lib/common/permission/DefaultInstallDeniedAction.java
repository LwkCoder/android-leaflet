package com.lwkandroid.lib.common.permission;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.core.utils.common.ToastUtils;
import com.yanzhenjie.permission.Action;

import java.io.File;

/**
 * Description:安装应用权限拒绝后的操作
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultInstallDeniedAction implements Action<File>
{
    @Override
    public void onAction(File data)
    {
        ToastUtils.showShort(R.string.warning_permission_denied);
    }
}
