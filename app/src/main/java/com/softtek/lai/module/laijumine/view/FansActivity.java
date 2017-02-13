package com.softtek.lai.module.laijumine.view;

import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laijumine.model.FansInfoModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_fans)
public class FansActivity extends BaseActivity {

    @InjectView(R.id.list_fans)
    ListView list_fans;

    EasyAdapter<FansInfoModel>adapter;
    FansInfoModel fansInfoModel;
    List<FansInfoModel>fansInfoModels=new ArrayList<FansInfoModel>();
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        adapter=new EasyAdapter<FansInfoModel>(this,fansInfoModels,R.layout.acitivity_fans_list) {
            @Override
            public void convert(ViewHolder holder, FansInfoModel data, int position) {
                if (data!=null)
                {
                    CircleImageView cir_photo=holder.getView(R.id.cir_photo);
                    if (!TextUtils.isEmpty(data.getThPhoto()))
                    {
                        Picasso.with(getBaseContext()).load(AddressManager.get("photoHost")+data.getThPhoto())
                                .centerCrop().fit().placeholder(R.drawable.img_default).error(R.drawable.img_default)
                                .into(cir_photo);
                    }
                    TextView tv_fansname=holder.getView(R.id.tv_fansname);
                    tv_fansname.setText(data.getUserName());
                    TextView tv_fanssignature=holder.getView(R.id.tv_fanssignature);
                    tv_fanssignature.setText(data.getSignature());
                }
            }
        };
        list_fans.setAdapter(adapter);

    }
}
