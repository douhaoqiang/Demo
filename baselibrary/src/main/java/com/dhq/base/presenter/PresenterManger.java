package com.dhq.base.presenter;

/**
 * DESC
 *
 * @author douhaoqiang on 2019/3/20.
 */
public interface PresenterManger {

    /**
     * 添加绑定 presenter
     * @param presenter
     */
    void addPresenter(BasePresenter presenter);

    /**
     * 解绑view
     */
    void detachView();

}
