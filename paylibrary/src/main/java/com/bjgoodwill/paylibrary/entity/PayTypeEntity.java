package com.bjgoodwill.paylibrary.entity;


import com.dhq.base.entity.BaseEntity;

/**
 * Created by dongpeng on 2016/12/2.
 */

public class PayTypeEntity extends BaseEntity {
    String name;
    int type;
    boolean checked = false;

    public PayTypeEntity(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
