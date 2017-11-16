package com.dhq.net.entity;

import com.dhq.baselibrary.entity.BaseEntity;

/**
 * DESC
 * Created by douhaoqiang on 2017/11/16.
 */

public class UserInfo extends BaseEntity {

    private String userId;
    private String name;
    private String age;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
