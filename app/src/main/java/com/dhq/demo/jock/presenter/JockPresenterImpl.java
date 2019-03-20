package com.dhq.demo.jock.presenter;
import com.dhq.demo.jock.contract.JockContract;

/**
* Created by MVPHelper on 2016/10/24
*/

public class JockPresenterImpl extends JockContract.Presenter{

    public JockPresenterImpl(JockContract.IJockView jockView) {
        super(jockView);
    }

}