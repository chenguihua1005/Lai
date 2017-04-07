package com.softtek.lai.module.laicheng;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.widgets.CustomDialog;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_visitortest)
public class VisitortestFragment extends LazyBaseFragment implements View.OnClickListener {

    @InjectView(R.id.btn_input)
    Button btn_input;

    private LinearLayout.LayoutParams parm;

    public VisitortestFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        btn_input.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {


    }

    private void showTypeDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.info_visitor, null);
        EditText et_name = (EditText) view.findViewById(R.id.et_name);
        RelativeLayout ll_area = (RelativeLayout) view.findViewById(R.id.rl_area);
        parm = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        parm.gravity = Gravity.CENTER;

        builder.setView(view);
        builder.show();

        ll_area.setClickable(true);
        ll_area.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
//
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_input:
//                showTypeDialog();
                break;
        }
    }
}
