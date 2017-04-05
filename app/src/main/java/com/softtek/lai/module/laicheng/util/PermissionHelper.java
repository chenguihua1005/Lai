package com.softtek.lai.module.laicheng.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PermissionHelper {

    private static final int REQUEST_PERMISSION_CODE = 0x12;

    /**
     * 动态检查权限
     * @param context 上下文
     * @param permissions 需要申请的权限
     * @return 是不是需要申请
     */
    public static boolean checkPermission(Activity context, String[] permissions) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //没有权限需要申请时
            if (permissions == null || permissions.length <= 0) return true;
            //检查权限是不是已经授予
            List<String> noOkPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    noOkPermissions.add(permission);
                }
            }
            //该权限已经授予，不再申请
            if (noOkPermissions.size() <= 0) return true;
            //6.0以上需要申请权限
            context.requestPermissions(noOkPermissions.toArray(new String[noOkPermissions.size()]), REQUEST_PERMISSION_CODE);
            return false;
        }
        //6.0以下下不需要申请
        return true;
    }

    /**
     * 处理权限申请的结果，返回结构化的数据
     * @param requestCode 请求码
     * @param permissions 被请求的权限
     * @param grantResults 请求结果
     * @param onPermissionHandleOverListener 权限检查监听器
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionHandleOverListener onPermissionHandleOverListener) {
        if (requestCode != REQUEST_PERMISSION_CODE) return;
        Map<String, Integer> result = new HashMap<>();
        boolean isHavePermissionNotOk = false;
        for (int i = 0; i < Math.min(permissions.length, grantResults.length); i++) {
            result.put(permissions[i], grantResults[i]);
            //有权限没有同意
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isHavePermissionNotOk = true;
            }
        }
        //如果权限全部同意，继续执行
        if (onPermissionHandleOverListener != null) {
            onPermissionHandleOverListener.onHandleOver(!isHavePermissionNotOk, result);
        }
    }

    public interface OnPermissionHandleOverListener {
        void onHandleOver(boolean isOkExactly, Map<String, Integer> result);
    }
}