package com.sources.javacode.project.home;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class HomePresenter extends MvpBasePresenterImpl<HomeContract.IView, HomeContract.IModel>
        implements HomeContract.IPresenter<HomeContract.IView, HomeContract.IModel>
{
    public HomePresenter(HomeContract.IView viewImpl, HomeContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
