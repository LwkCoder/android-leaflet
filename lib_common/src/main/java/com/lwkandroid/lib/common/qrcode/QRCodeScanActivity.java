package com.lwkandroid.lib.common.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.permission.AndPermissionHelper;
import com.lwkandroid.lib.common.rx.ApiLoadingObserver;
import com.lwkandroid.lib.common.widgets.view.RCheckBox;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.qrcode.QRCodeHelper;
import com.lwkandroid.lib.core.rx.life.RxLife;
import com.lwkandroid.lib.core.utils.common.BarUtils;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;
import com.lwkandroid.lib.core.utils.common.ScreenUtils;
import com.lwkandroid.lib.core.utils.common.VibrateUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.annotation.Nullable;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class QRCodeScanActivity extends MvpBaseActivity<QRCodeScanPresenter> implements QRCodeScanContract.IView<QRCodeScanPresenter>, QRCodeView.Delegate, CompoundButton.OnCheckedChangeListener
{
    public static final String KEY_RESULT = "qrcode_result";
    private static final String KEY_OPTIONS = "options";
    private static final int REQUEST_CODE_IMAGEPICKER = 100;
    private QRCodeOptions mOptions;
    private ImageView mImgClose;
    private ZXingView mZXingView;
    private RCheckBox mCkLight;
    private RTextView mTvPictures;

    public static void start(Activity activity, int requestCode)
    {
        start(activity, requestCode, new QRCodeOptions());
    }

    public static void start(Activity activity, int requestCode, QRCodeOptions options)
    {
        Intent intent = new Intent(activity, QRCodeScanActivity.class);
        intent.putExtra(KEY_OPTIONS, options);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(androidx.fragment.app.Fragment fragment, int requestCode)
    {
        start(fragment, requestCode, new QRCodeOptions());
    }

    public static void start(androidx.fragment.app.Fragment fragment, int requestCode, QRCodeOptions options)
    {
        Intent intent = new Intent(fragment.getActivity(), QRCodeScanActivity.class);
        intent.putExtra(KEY_OPTIONS, options);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected QRCodeScanPresenter createPresenter()
    {
        return new QRCodeScanPresenter(this, new QRCodeScanModel());
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {
        if (intent != null)
        {
            mOptions = intent.getParcelableExtra(KEY_OPTIONS);
        }
        if (mOptions == null)
        {
            mOptions = new QRCodeOptions();
        }
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    protected void initUI(View contentView)
    {
        BarUtils.setAllBarTransparent(this);

        mImgClose = find(R.id.img_qrcode_scan_close);
        BarUtils.compatMarginWithStatusBar(mImgClose);
        addClick(mImgClose, v -> finish());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        requestPermission();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (mZXingView != null)
        {
            mZXingView.startSpotAndShowRect();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (mZXingView != null)
        {
            mZXingView.stopCamera();
            mZXingView.stopSpotAndHiddenRect();
        }
    }

    @Override
    protected void onDestroy()
    {
        if (mZXingView != null)
        {
            lightOff();
            mZXingView.onDestroy();
        }
        super.onDestroy();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        overridePendingTransition(mOptions.getEnterAnim(), mOptions.getExitAnim());
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(mOptions.getEnterAnim(), mOptions.getExitAnim());
    }

    @Override
    public void onScanQRCodeSuccess(String result)
    {
        KLog.i("QRCode scan success:" + result);
        VibrateUtils.vibrate(150);
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark)
    {
        //摄像头环境亮度发生变化
    }

    @Override
    public void onScanQRCodeOpenCameraError()
    {
        KLog.e("QRCodeScanActivity.onScanQRCodeOpenCameraError!!!");
        showLongToast(R.string.qrcodescan_open_camera_error);
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGEPICKER)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                List<ImageBean> imageList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
                String picPath = imageList.get(0).getImagePath();
                QRCodeHelper.decodeQRCodeByRxJava(picPath)
                        .compose(RxLife.with(this).bindUtilOnDestroy())
                        .subscribe(new ApiLoadingObserver<String>()
                        {
                            @Override
                            public void onAccept(String s)
                            {
                                onScanQRCodeSuccess(s);
                            }

                            @Override
                            public void onError(ApiException e)
                            {
                                showLongToast(R.string.qrcodescan_decode_error);
                            }
                        }.setMessage(R.string.qrcode_dialog_message)
                                .setCancelable(false)
                                .setCanceledOnTouchOutside(false));
            } else
            {
                if (mZXingView != null)
                {
                    mZXingView.startSpotAndShowRect();
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked)
        {
            lightOn();
        } else
        {
            lightOff();
        }
    }

    private void requestPermission()
    {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA)
                .rationale(AndPermissionHelper.getRuntimeDeniedRationale())
                .onDenied(AndPermissionHelper.getRuntimeDeniedAction())
                .onGranted(data -> inflateContent())
                .start();
    }

    private void inflateContent()
    {
        ViewStub viewStub = find(R.id.vs_qrcode);
        viewStub.inflate();

        mZXingView = find(R.id.zxv_qrcode_scan);
        mCkLight = find(R.id.ck_qrcode_scan_light);
        mCkLight.setOnCheckedChangeListener(this);
        mTvPictures = find(R.id.tv_qrcode_scan_pictures);
        if (mOptions.isShowAlbum())
        {
            mTvPictures.setVisibility(View.VISIBLE);
            addClick(mTvPictures, v -> requestSelectPicture());
        } else
        {
            mTvPictures.setVisibility(View.GONE);
        }

        //动态计算下扫描框宽高，取屏幕宽高的最小值-2*40dp
        int width = ScreenUtils.getScreenWidth();
        int height = ScreenUtils.getScreenHeight();
        int minSize = Math.min(width, height);
        minSize -= 2 * ResourceUtils.getDimenPixelSize(R.dimen.spacing_huge);
        mZXingView.getScanBoxView().setRectWidth(minSize);
        mZXingView.getScanBoxView().setRectHeight(minSize);

        mZXingView.getScanBoxView().setIsBarcode(mOptions.isBarCodeMode());
        mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(mOptions.isFullScreenScan());
        mZXingView.getScanBoxView().setBorderColor(mOptions.getRectColor());
        mZXingView.getScanBoxView().setCornerColor(mOptions.getRectCornerColor());
        mZXingView.getScanBoxView().setScanLineColor(mOptions.getScanLineColor());
        mZXingView.getScanBoxView().setAnimTime(mOptions.getScanLineAnimDuration());
        mZXingView.getScanBoxView().setTipText(mOptions.getHintText());
        mZXingView.getScanBoxView().setQRCodeTipText(mOptions.getHintText());
        mZXingView.getScanBoxView().setBarCodeTipText(mOptions.getHintText());
        mZXingView.getScanBoxView().setTipTextColor(mOptions.getHintColor());
        mZXingView.getScanBoxView().setAutoZoom(mOptions.isAutoZoom());
        mZXingView.setDelegate(QRCodeScanActivity.this);
        mZXingView.startSpotAndShowRect();
    }

    /**
     * 关闭闪光灯
     */
    private void lightOff()
    {
        if (mZXingView != null)
        {
            mZXingView.closeFlashlight();
        }
        if (mCkLight != null)
        {
            mCkLight.setText(R.string.qrcode_turn_on_light);
        }
    }

    /**
     * 打开闪光灯
     */
    private void lightOn()
    {
        if (mZXingView != null)
        {
            mZXingView.openFlashlight();
        }
        if (mCkLight != null)
        {
            mCkLight.setText(R.string.qrcode_turn_off_light);
        }
    }

    /**
     * 跳转选择图片
     */
    private void requestSelectPicture()
    {
        new ImagePicker()
                .pickType(ImagePickType.SINGLE)
                .needCamera(false)
                .start(this, REQUEST_CODE_IMAGEPICKER);
    }
}
