package com.lwkandroid.lib.common.qrcode;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenterå±‚
 *
 * @author
 * @date
 */
class QRCodeScanPresenter extends MvpBasePresenterImpl<QRCodeScanContract.IView, QRCodeScanContract.IModel>
        implements QRCodeScanContract.IPresenter<QRCodeScanContract.IView, QRCodeScanContract.IModel>
{
    public QRCodeScanPresenter(QRCodeScanContract.IView viewImpl, QRCodeScanContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
