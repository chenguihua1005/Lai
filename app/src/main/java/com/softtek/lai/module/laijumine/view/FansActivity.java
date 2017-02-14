package com.softtek.lai.module.laijumine.view;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laijumine.model.FansInfoModel;
import com.softtek.lai.module.laijumine.net.MineSevice;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_fans)
public class FansActivity extends BaseActivity {

    @InjectView(R.id.list_fans)
    ListView list_fans;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    EasyAdapter<FansInfoModel>adapter;
    MineSevice mineSevice;
    List<FansInfoModel>fansInfoModels=new ArrayList<FansInfoModel>();
    @Override
    protected void initViews() {
        list_fans.addFooterView(new ViewStub(this));//list底部分割线
        int fansnum=getIntent().getIntExtra("fansnum",0);//粉丝数量
        tv_title.setText("粉丝（"+fansnum+"）");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initDatas() {
        mineSevice= ZillaApi.NormalRestAdapter.create(MineSevice.class);
        adapter=new EasyAdapter<FansInfoModel>(this,fansInfoModels,R.layout.acitivity_fans_list) {
            @Override
            public void convert(ViewHolder holder, FansInfoModel data, int position) {
                if (data!=null)
                {
                    CircleImageView cir_photo=holder.getView(R.id.cir_photo);
                    if (!TextUtils.isEmpty(data.getPhoto()))
                    {
                        Picasso.with(getBaseContext()).load(AddressManager.get("photoHost")+data.getPhoto())
                                .centerCrop().fit().placeholder(R.drawable.img_default).error(R.drawable.img_default)
                                .into(cir_photo);
                    }
                    TextView tv_fansname=holder.getView(R.id.tv_fansname);
                    tv_fansname.setText(data.getUserName());
                    TextView tv_fanssignature=holder.getView(R.id.tv_fanssignature);
                    tv_fanssignature.setText(data.getSignature());
                    final ImageView im_guanzhu=holder.getView(R.id.im_guanzhu);
                    im_guanzhu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            im_guanzhu.setImageResource(R.drawable.add_focus_icon);
                        }
                    });
                }
            }
        };
        list_fans.setAdapter(adapter);
        doGetData();
    }
    /*
    * 获取列表数据
    * */
    private void doGetData()
    {
        mineSevice.GetLovePelist(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), new RequestCallback<ResponseData<List<FansInfoModel>>>() {
            @Override
            public void success(ResponseData<List<FansInfoModel>> listResponseData, Response response) {
                int status=listResponseData.getStatus();
                switch (status)
                {
                    case 200:
                        fansInfoModels.addAll(listResponseData.getData());
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }
        });
    }



}
