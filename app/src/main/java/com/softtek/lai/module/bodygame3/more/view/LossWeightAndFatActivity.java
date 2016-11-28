package com.softtek.lai.module.bodygame3.more.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.LossWeightAndFat;
import com.softtek.lai.module.bodygame3.more.net.MoreService;

import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_loss_weight_and_fat)
public class LossWeightAndFatActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;


    LossWeightAndFat.LossFatLevel[] fatLevels=new LossWeightAndFat.LossFatLevel[10];
    LossWeightAndFat.LossWeightLevel[] weightLevels=new LossWeightAndFat.LossWeightLevel[10];

    @Override
    protected void initViews() {
        tv_title.setText("体馆赛等级");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View head= LayoutInflater.from(this).inflate(R.layout.head_weight_fat_loss,null);
        lv.addHeaderView(head);

    }

    @Override
    protected void initDatas() {

        //lv.setAdapter(adapter);
        dialogShow("获取数据");
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getLossLevel(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new Callback<ResponseData<LossWeightAndFat>>() {
                            @Override
                            public void success(ResponseData<LossWeightAndFat> data, Response response) {
                                dialogDissmiss();

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                ZillaApi.dealNetError(error);
                            }
                        });
    }
}
