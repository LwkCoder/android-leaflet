package com.lwkandroid.lib.common.permission;

import android.text.TextUtils;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogController;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.utils.common.AppUtils;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.lwkandroid.lib.core.utils.common.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * Description:默认运行时权限拒绝后的操作
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultRuntimeDeniedAction implements Action<List<String>>
{
    @Override
    public void onAction(List<String> data)
    {
        if (AndPermission.hasAlwaysDeniedPermission(ActivityLifecycleHelper.get().getTopActivity(), data))
        {
            String content = ResourceUtils.getString(R.string.dialog_runtime_neverask_message,
                    AppUtils.getAppName(), TextUtils.join("\n", Permission.transformText(ActivityLifecycleHelper.get().getTopActivity(), data)));
            DialogBuilder.with(new CommonDialogController()
                    .setTitle(R.string.dialog_permission_title)
                    .setContent(content)
                    .setPositive(R.string.dialog_permission_setting)
                    .setNegative(R.string.cancel))
                    .setCancelable(false)
                    .setDarkWindowDegree(0.1f)
                    .setCanceledOnTouchOutside(false)
                    .build()
                    .addOnChildClickListener(R.id.tv_dialog_template_negative, (viewId, view, contentView, dialog) -> {
                        dialog.dismiss();
                        ToastUtils.showShort(R.string.warning_permission_denied);
                    })
                    .addOnChildClickListener(R.id.tv_dialog_template_positive, (viewId, view, contentView, dialog) -> {
                        dialog.dismiss();
                        //跳转到设置界面
                        AndPermission.with(ActivityLifecycleHelper.get().getTopActivity())
                                .runtime()
                                .setting()
                                .start(AndPermissionHelper.REQUEST_CODE_SETTING);
                    })
                    .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
        } else
        {
            ToastUtils.showShort(R.string.warning_permission_denied);
        }
    }

}
