package com.dhq.permissionlibrary;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * DESC 权限的真正申请界面
 * Author douhaoqiang
 * Create by 2017/8/21.
 */

public class PermissionFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private PermissionListener listener;

    public PermissionFragment() {
    }

    public void setListener(PermissionListener listener) {
        this.listener = listener;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @TargetApi(23)
    public void requestPermissions(@NonNull String[] permissions) {
        ArrayList requestPermissionList = new ArrayList();

        for (int i = 0; i < permissions.length; ++i) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                //表明需要申请此权限
                requestPermissionList.add(permission);
            }
        }

        if (requestPermissionList.isEmpty()) {
            //表明此权限已经全部通过
            this.permissionAllGranted();
        } else {
            //发起权限申请请求
            this.requestPermissions((String[]) requestPermissionList.toArray(new String[requestPermissionList.size()]), PERMISSIONS_REQUEST_CODE);
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {

                ArrayList deniedPermissionList = new ArrayList();

                for (int i = 0; i < grantResults.length; ++i) {
                    //再次检查请求权限是否已经通过
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissionList.add(permissions[i]);
                    }
                }

                if (deniedPermissionList.isEmpty()) {
                    //表明此权限已经全部通过
                    this.permissionAllGranted();
                } else {
                    //表明有权限被拒绝
                    this.permissionHasDenied();
//                    Iterator iterator = deniedPermissionList.iterator();
//
//                    while (iterator.hasNext()) {
//                        String deniedPermission = (String) iterator.next();
//                        boolean flag = this.shouldShowRequestPermissionRationale(deniedPermission);
//                        if (!flag) {
//                            this.permissionHasDenied();
//                            return;
//                        }
//                    }

                }
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void permissionAllGranted() {
        if (this.listener != null) {
            this.listener.onPermissionGranted();
        }

    }

    private void permissionHasDenied() {
        if (this.listener != null) {
            this.listener.onPermissionDenied();
        }

    }


}
