package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.model.PantnerpageModel;
import com.softtek.lai.module.bodygame3.head.model.PartnerlistModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_pantner)
public class PantnerActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.pantner_list)
    ListView pantner_list;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.pantnerContent)
    EditText pantnerContent;
    @InjectView(R.id.search_partner)
    Button search_partner;
    @InjectView(R.id.pb)
    ProgressBar pb;
    private String classId_first;
    private List<PartnerlistModel> partnerlistModels = new ArrayList<PartnerlistModel>();
    EasyAdapter<PartnerlistModel> adapter;
    @Override
    protected void initViews() {
        overridePendingTransition(0, 0);
        tv_title.setText("搜索小伙伴");
        ll_left.setOnClickListener(this);
        search_partner.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        classId_first = getIntent().getStringExtra("classId_first");
        adapter = new EasyAdapter<PartnerlistModel>(this, partnerlistModels, R.layout.partner_item) {
            @Override
            public void convert(ViewHolder holder, PartnerlistModel data, int position) {
                CircleImageView head_image = holder.getView(R.id.head_image);
                Picasso.with(PantnerActivity.this).load(AddressManager.get("photoHost") + data.getPhoto())
                        .error(R.drawable.img_default).placeholder(R.drawable.img_default).into(head_image);
                TextView tv_name = holder.getView(R.id.tv_name);
                tv_name.setText(data.getUserName());
                TextView tv_certificate = holder.getView(R.id.tv_certificate);
                tv_certificate.setText(data.getMobile());
                TextView certificate_tv = holder.getView(R.id.certificate_tv);
                if(!TextUtils.isEmpty(data.getCertification())){
                    certificate_tv.setText("资格证号：" + " " + data.getCertification());
                }else {
                    certificate_tv.setText("资格证号：" + "无");
                }

                TextView role_tv=holder.getView(R.id.role_tv);
                if(data.getClassRole().equals("1")){
                     role_tv.setText("总教练");
                }else if(data.getClassRole().equals("2")){
                    role_tv.setText("教练");
                }else if(data.getClassRole().equals("3")){
                   role_tv.setText("助教");
                }else if(data.getClassRole().equals("4")){
                    role_tv.setText("学员");
                }
            }
        };
        pantner_list.setAdapter(adapter);
        pantner_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PartnerlistModel partnerlistModel = partnerlistModels.get(i);
                int student_id = partnerlistModel.getAccountId();
                Intent intent = new Intent(PantnerActivity.this, PersonDetailActivity2.class);
                intent.putExtra("ClassId", classId_first);
                intent.putExtra("AccountId", Long.parseLong(student_id+""));
                startActivity(intent);
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                finish();
                break;
            case R.id.search_partner:
                String content_searc = pantnerContent.getText().toString();
                if(StringUtils.isNotEmpty(content_searc)) {
                    pb.setVisibility(View.VISIBLE);
                    ZillaApi.NormalRestAdapter.create(HeadService.class).getpartner(classId_first,UserInfoModel.getInstance().getToken(),
                            content_searc, classId_first, 10, 1, new RequestCallback<ResponseData<PantnerpageModel>>() {
                                @Override
                                public void success(ResponseData<PantnerpageModel> pantnerpageModelResponseData, Response response) {
                                    partnerlistModels.clear();
                                    pb.setVisibility(View.GONE);
                                    if (200 == pantnerpageModelResponseData.getStatus()) {
                                        if (pantnerpageModelResponseData.getData() != null) {
                                            PantnerpageModel pantnerpageModel = pantnerpageModelResponseData.getData();
                                            if (pantnerpageModel.getPartnersList() != null) {
                                                partnerlistModels.addAll(pantnerpageModel.getPartnersList());
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    } else {
                                        Util.toastMsg("未找到该小伙伴");
                                    }
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    pb.setVisibility(View.GONE);
                                    super.failure(error);
                                }
                            });
                }else{
                    Util.toastMsg("请输入姓名或者手机号");
                }
                break;
        }
    }
}
