package com.lwkandroid.lib.common.permission;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.core.utils.common.ToastUtils;
import com.yanzhenjie.permission.Action;

/**
 * Description:发送通知栏拒绝后的操作
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultShowNotificationDeniedAction implements Action<Void>
{
    @Override
    public void onAction(Void data)
    {
        ToastUtils.showShort(R.string.warning_permission_denied);
    }
}
