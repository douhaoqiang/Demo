package com.dhq.demo.recycle.bean;

import java.io.Serializable;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/6.
 */

public class MyMessage implements Serializable {

    public int logo;
    public String name;
    public String desc;

    public MyMessage(int logo, String name, String desc) {
        this.logo = logo;
        this.name = name;
        this.desc = desc;
    }
}
