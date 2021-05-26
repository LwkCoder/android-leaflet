package com.lwkandroid.lib.common.permission;

import android.content.Context;
import android.text.TextUtils;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogController;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.utils.common.AppUtils;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * Description:默认运行时权限拒绝后解释性弹窗
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultRuntimeDeniedRationale implements Rationale<List<String>>
{
    @Override
    public void showRationale(Context context, List<String> data, RequestExecutor executor)
    {
        String content = ResourceUtils.getString(R.string.dialog_runtime_rationale_message,
                AppUtils.getAppName(), TextUtils.join("\n", Permission.transformText(context, data)));
        DialogBuilder.with(new CommonDialogController()
                .setTitle(R.string.dialog_permission_title)
                .setContent(content)
                .setPositive(R.string.dialog_permission_auth)
                .setNegative(R.string.cancel))
                .setCancelable(false)
                .setDarkWindowDegree(0.1f)
                .setCanceledOnTouchOutside(false)
                .build()
                .addOnChildClickListener(R.id.tv_dialog_template_negative, (viewId, view, contentView, dialog) -> {
                    dialog.dismiss();
                    executor.cancel();
                })
                .addOnChildClickListener(R.id.tv_dialog_template_positive, (viewId, view, contentView, dialog) -> {
                    dialog.dismiss();
                    executor.execute();
                })
                .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
    }
}
