package com.softtek.lai.module.bodygame3.more.view;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.ClasslistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.head.view.ClassDetailActivity;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_search_class2)
public class SearchClassActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,Validator.ValidationListener{


    @LifeCircleInject
    ValidateLife validateLife;

    @InjectView(R.id.pantner_list)
    ListView lv_class;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @Required(order = 1,message = "请输入班级名称或班级编号")
    @InjectView(R.id.pantnerContent)
    EditText pantnerContent;
    @InjectView(R.id.search_partner)
    Button search_partner;
    @InjectView(R.id.pb)
    ProgressBar pb;

    ClasslistModel classlistModel;
    private List<ClasslistModel> classlistModels = new ArrayList<>();
    EasyAdapter<ClasslistModel> adapter;
    @Override
    protected void initViews() {
        overridePendingTransition(0, 0);
        tv_title.setText("加入新班级");
        ll_left.setOnClickListener(this);
        search_partner.setOnClickListener(this);
        SpannableString ss=new SpannableString("请输入班级名称或班级编号");
        ss.setSpan(new AbsoluteSizeSpan(10,true),0,ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        pantnerContent.setHint(new SpannableString(ss));
    }

    @Override
    protected void initDatas() {
        adapter = new EasyAdapter<ClasslistModel>(this, classlistModels, R.layout.class_list) {
            @Override
            public void convert(ViewHolder holder, ClasslistModel data, int position) {
                TextView class_name = holder.getView(R.id.class_name);
                class_name.setText(data.getClassName());
                TextView class_code = holder.getView(R.id.class_code);
                class_code.setText(data.getClassCode());
            }

        };
        lv_class.setAdapter(adapter);
        lv_class.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.search_partner:
                validateLife.validate();

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        classlistModel = classlistModels.get(i);
        Intent intent = new Intent(this, ClassDetailActivity.class);
        intent.putExtra("ClasslistModel", classlistModel);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        String text = pantnerContent.getText().toString();
        pb.setVisibility(View.VISIBLE);
        ZillaApi.NormalRestAdapter.create(HeadService.class).getclass(UserInfoModel.getInstance().getToken(),
                text, new RequestCallback<ResponseData<List<ClasslistModel>>>() {
                    @Override
                    public void success(ResponseData<List<ClasslistModel>> data, Response response) {
                        try {
                            pb.setVisibility(View.GONE);
                            if (data.getStatus() == 200) {
                                classlistModels.clear();
                                classlistModels.addAll(data.getData());
                                adapter.notifyDataSetChanged();
                            } else if (data.getStatus() == 100) {
                                Util.toastMsg(data.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            pb.setVisibility(View.GONE);
                            super.failure(error);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
