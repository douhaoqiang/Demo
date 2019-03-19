package com.dhq.mywidget.dialog;

/**
 * DESC 弹框工具类
 * Created by douhaoqiang on 2019/3/18.
 */
public class DialogUtils {

    private Builder mBuilder;

    private DialogUtils(Builder builder) {
        this.mBuilder=builder;
    }

    /**
     * 弹框显示
     */
    public void show(){

    }

    /**
     * 弹框消失
     */
    public void dismiss(){

    }


    public static class Builder{

        /*
        点击外面是否可以消失
         */
        boolean isDismissOutTouch=true;

        /*
        是否显示半透明背景
         */
        boolean isShadowBg=true;

        /**
         * 是否可以点击周围消失
         * @param dismissOutTouch
         */
        public void setDismissOutTouch(boolean dismissOutTouch) {
            isDismissOutTouch = dismissOutTouch;
        }

        /**
         * 是否显示半透明背景
         * @param shadowBg
         */
        public void setShadowBg(boolean shadowBg) {
            isShadowBg = shadowBg;
        }

        public DialogUtils build(){

            return new DialogUtils(this);
        }

    }


}
