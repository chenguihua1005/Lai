package com.softtek.lai.module.bodygame3.more.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by jia.lu on 1/4/2018.
 */

public class MakiBottomDialog extends BottomSheetDialog {
    public MakiBottomDialog(@NonNull Context context) {
        super(context);
    }

    public MakiBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected MakiBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
