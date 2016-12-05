package com.softtek.lai.module.bodygame3.activity.view;

import android.widget.EditText;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.net.FuceSevice;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by Terry on 2016/12/3.
 */

@InjectLayout(R.layout.activity_initwrite)
public class FcStuActivity extends BaseActivity {

    @InjectView(R.id.tv_write_chu_weight)
    EditText tv_write_chu_weight;//初始体重
    @InjectView(R.id.tv_retestWrite_nowweight)
    EditText tv_retestWrite_nowweight;//现在体重
    @InjectView(R.id.tv_retestWrite_tizhi)
    EditText tv_retestWrite_tizhi;//体脂
    @InjectView(R.id.tv_retestWrite_neizhi)
    TextView tv_retestWrite_neizhi;//内脂

    FuceSevice fuceSevice;
    InitDataModel initDataModel;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        fuceSevice= ZillaApi.NormalRestAdapter.create(FuceSevice.class);
        doData();
        if (initDataModel!=null)
        {
            tv_write_chu_weight.setText(initDataModel.getInitWeight());//初始体重
            tv_retestWrite_nowweight.setText(initDataModel.getWeight());//现在体重
            tv_retestWrite_tizhi.setText(initDataModel.getPysical());//体脂
            tv_retestWrite_neizhi.setText(initDataModel.getFat());//内脂


        }

    }

    private void doData() {
        fuceSevice.dogetInitData(UserInfoModel.getInstance().getToken(), Long.parseLong("3399"), "C4E8E179-FD99-4955-8BF9-CF470898788B", new RequestCallback<ResponseData<InitDataModel>>() {
            @Override
            public void success(ResponseData<InitDataModel> initDataModelResponseData, Response response) {
                int status=initDataModelResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        initDataModel=initDataModelResponseData.getData();
                        break;
                    default:
                        Util.toastMsg(initDataModelResponseData.getMsg());
                        break;
                }
            }
        });
    }
}
