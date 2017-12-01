package com.softtek.lai.module.customermanagement.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ggx.widgets.view.CustomProgress;

/**
 * Created by jia.lu on 12/1/2017.
 */

public abstract class MakiBaseActivity extends FragmentActivity{
    private CustomProgress progressDialog;
    /**
     * 通用progressDialog
     *
     * @param value
     */
    protected void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog = CustomProgress.build(this, value);
                progressDialog.show();
            }
        }
    }

    /**
     * 通用progressDialog
     */
    protected void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
