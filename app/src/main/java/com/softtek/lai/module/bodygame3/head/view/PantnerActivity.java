package com.softtek.lai.module.bodygame3.head.view;

import android.view.View;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_pantner)
public class PantnerActivity extends BaseActivity implements View.OnClickListener {
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
        overridePendingTransition(0,0);
        tv_title.setText("搜索");
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ll_left.setOnClickListener(this);
        pantnerContent.setOnClickListener(this);
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
//                (Picasso.with(PantnerActivity.this).load(R.drawable.img_default).into(head_image)
                tv_certificate.setText(data.getMobile());
                TextView certificate_tv = holder.getView(R.id.certificate_tv);
                certificate_tv.setText(data.getCertification());
            }
        };
        pantner_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.pantnerContent:

                break;
            case R.id.search_partner:
                String content_searc = pantnerContent.getText().toString().trim();
                pb.setVisibility(View.VISIBLE);
                ZillaApi.NormalRestAdapter.create(HeadService.class).getpartner(UserInfoModel.getInstance().getToken(),
                        content_searc, classId_first, 100, 1, new RequestCallback<ResponseData<PantnerpageModel>>() {
                            @Override
                            public void success(ResponseData<PantnerpageModel> pantnerpageModelResponseData, Response response) {
                             pb.setVisibility(View.GONE);
                                if (200 == pantnerpageModelResponseData.getStatus()) {
                                    if (pantnerpageModelResponseData.getData() != null) {
                                        PantnerpageModel pantnerpageModel = pantnerpageModelResponseData.getData();
                                        if (pantnerpageModel.getPartnersList() != null) {
                                            partnerlistModels.addAll(pantnerpageModel.getPartnersList());
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                pb.setVisibility(View.GONE);
                                super.failure(error);
                            }
                        });
                break;
        }
    }
}
