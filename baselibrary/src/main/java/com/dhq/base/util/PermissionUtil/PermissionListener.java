package com.dhq.base.util.PermissionUtil;

/**
 * DESC
 * Author douhaoqiang
 * Create by 2017/8/21.
 */

public interface PermissionListener {

    /**
     * 权限通过
     */
    void onPermissionGranted();

    /**
     * 权限拒绝
     */
    void onPermissionDenied();


}
