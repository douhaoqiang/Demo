package com.dhq.baselibrary.presenter;

import java.util.ArrayList;

/**
 * DESC
 *
 * @author douhaoqiang on 2019/3/20.
 */
public interface PresenterManger {


    void addPresenter(BasePresenter presenter);

    void detachView();

}
