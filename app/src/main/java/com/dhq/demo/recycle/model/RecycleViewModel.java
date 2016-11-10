package com.dhq.demo.recycle.model;

import com.dhq.demo.R;
import com.dhq.demo.recycle.bean.MyMessage;
import com.dhq.demo.recycle.contract.RecycleContract;

import java.util.ArrayList;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class RecycleViewModel implements RecycleContract.IRecycleModel {


    public ArrayList<MyMessage> getListData() {
        ArrayList<MyMessage> myMessages=new ArrayList<>();
        MyMessage myMessage;
        for(int i=1;i<=10;i++){
            myMessage=new MyMessage(R.mipmap.ic_launcher,i+"-Title",i+"---描述的卡口监控了发动机是两个");
            myMessages.add(myMessage);
        }
        return myMessages;
    }

}
