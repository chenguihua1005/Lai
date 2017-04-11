package com.softtek.lai.module.laicheng;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.view.CreateActActivity;
import com.softtek.lai.module.bodygame3.home.view.MoreFragment;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.view.MoreHasFragment;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;
import com.softtek.lai.module.laicheng.presenter.VisitorPresenter;
import com.softtek.lai.utils.ShareUtils;
import com.softtek.lai.widgets.CustomDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_visitortest)
public class VisitortestFragment extends LazyBaseFragment<VisitorPresenter> implements VisitorPresenter.VisitorView, View.OnClickListener {

    @InjectView(R.id.bt_again)
    Button bt_again;
    private LinearLayout.LayoutParams parm;
    @InjectView(R.id.tv_weight)
    TextView tv_weight;

    @InjectView(R.id.bt_create)
    Button bt_create;//
    @InjectView(R.id.bt_history)
    Button bt_history;

    @InjectView(R.id.ll_visitor)
    LinearLayout ll_visitor;//访客信息
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phoneNo)
    TextView tv_phoneNo;
    @InjectView(R.id.tv_age)
    TextView tv_age;
    @InjectView(R.id.tv_gender)
    TextView tv_gender;
    @InjectView(R.id.tv_height)
    TextView tv_height;

    VisitorModel visitorModel = new VisitorModel();
    private int gender = 0;
    private int visitorId;

    public VisitortestFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        bt_again.setOnClickListener(this);
        bt_create.setOnClickListener(this);
        bt_history.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        setPresenter(new VisitorPresenter(this));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/wendy.ttf");
        tv_weight.setTypeface(tf);
    }

    private void showTypeDialog() {
        final CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.info_visitor, null);
        RelativeLayout ll_area = (RelativeLayout) view.findViewById(R.id.rl_area);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_old = (EditText) view.findViewById(R.id.et_old);
        final EditText et_height = (EditText) view.findViewById(R.id.et_height);
        final EditText et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        RadioGroup rg_up = (RadioGroup) view.findViewById(R.id.rg_up);
        Button btn_commit = (Button) view.findViewById(R.id.btn_commit);
        parm = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        parm.gravity = Gravity.CENTER;

        builder.setView(view);
        builder.show();


        rg_up.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int groupId = group.getCheckedRadioButtonId();
                switch (groupId) {
                    case R.id.rb_male:
                        gender = 0;//男
                        break;
                    case R.id.rb_female:
                        gender = 1; //女
                        break;
                }
            }
        });
        ll_area.setClickable(true);
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitorModel.setName(et_name.getText().toString());
                visitorModel.setHeight(Float.parseFloat(et_height.getText().toString()));
                visitorModel.setAge(Integer.parseInt(et_old.getText().toString()));
                visitorModel.setPhoneNo(et_mobile.getText().toString());
                visitorModel.setGender(gender);
                Log.i("访客信息", visitorModel.toString());
                setVisitorModel(visitorModel);
                getPresenter().commitData(UserInfoModel.getInstance().getToken(), visitorModel);
                builder.dismiss();
            }
        });
        ll_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
    }


    public VisitorModel getVisitorModel() {
        return visitorModel;
    }

    public void setVisitorModel(VisitorModel visitorModel) {
        this.visitorModel = visitorModel;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_again:
                break;
            case R.id.bt_create:
                showTypeDialog();
                break;
            case R.id.bt_history:
                Intent intent=new Intent(getActivity(),VisithistoryActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void commit(Visitsmodel visitsmodel, VisitorModel model) {
        if (model != null) {
            ll_visitor.setVisibility(View.VISIBLE);
            tv_name.setText(model.getName());
            tv_phoneNo.setText(model.getPhoneNo());
            tv_age.setText(model.getAge());
            if (0 == model.getGender()) {
                tv_gender.setText("男");
            } else {
                tv_gender.setText("女");
            }
            tv_height.setText(model.getGender());

        }

        if (visitsmodel != null) {
            visitorId = visitsmodel.getVisitorId();
        }
    }
}
