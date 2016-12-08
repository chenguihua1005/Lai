package com.softtek.lai.module.bodygame3.activity.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggx.widgets.nicespinner.ArrowSpinner3;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.head.view.SearchClassActivity;
import com.softtek.lai.module.bodygame3.home.view.ActivityFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
@InjectLayout(R.layout.activity_no_class_fragment)
public class NoClassFragment extends LazyBaseFragment {
//    @InjectView(R.id.spinner_title1)
//    ArrowSpinner3 spinner_title;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setMessage("请先加入班级");
        alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getContext(), SearchClassActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
