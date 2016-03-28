package com.softtek.lai.module.assistant.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.assistant.model.AssistantDetailInfo;
import com.softtek.lai.module.assistant.presenter.AssistantManageImpl;
import com.softtek.lai.module.assistant.presenter.IAssistantManagePresenter;
import com.softtek.lai.module.bodygame.model.TiGuanSai;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.utils.SoftInputUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_assistant_detail)
public class AssistantDetailActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    @LifeCircleInject
    ValidateLife validateLife;


    @InjectView(R.id.tv_left)
    TextView tv_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.text_name)
    TextView text_name;

    @InjectView(R.id.text_phone)
    TextView text_phone;

    @InjectView(R.id.text_count)
    TextView text_count;

    @InjectView(R.id.text_retest)
    TextView text_retest;

    @InjectView(R.id.text_total)
    TextView text_total;

    private IAssistantManagePresenter assistantManagePresenter;
    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tv_left.setOnClickListener(this);

    }

    @Subscribe
    public void onEvent(AssistantDetailInfo assistantDetailInfo) {
        System.out.println("assistantDetailInfo:" + assistantDetailInfo);
        text_name.setText(assistantDetailInfo.getUserName().toString());
        text_phone.setText(assistantDetailInfo.getMobile().toString());
        text_count.setText(assistantDetailInfo.getNum().toString());
        text_retest.setText(assistantDetailInfo.getMrate().toString());
        text_total.setText(assistantDetailInfo.getTotalWeight().toString());
        if ("".equals(assistantDetailInfo.getPhoto())) {
            Picasso.with(this).load("111").error(R.drawable.img_default).into(img);
        } else {
            Picasso.with(this).load(assistantDetailInfo.getPhoto()).error(R.drawable.img_default).into(img);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        tv_left.setBackgroundResource(R.drawable.back);
        //tv_left.setLayoutParams(new Toolbar.LayoutParams(DisplayUtil.dip2px(this,15),DisplayUtil.dip2px(this,30)));
        tv_title.setText("助教详情");

    }

    @Override
    protected void initDatas() {
        assistantManagePresenter = new AssistantManageImpl(this);
        aCache = ACache.get(this, Constants.USER_ACACHE_DATA_DIR);
        String assistantId = getIntent().getStringExtra("assistantId");
        String classId = getIntent().getStringExtra("classId");
        assistantManagePresenter.showAssistantDetails(assistantId, classId);
    }

    @Override
    public void onClick(View v) {
        SoftInputUtil.hidden(this);
        switch (v.getId()) {

            case R.id.tv_left:
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView, failedRule);
    }


}
