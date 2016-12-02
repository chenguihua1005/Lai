package com.softtek.lai.module.bodygame3.head.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.activity.model.UseredModel;
import com.softtek.lai.module.bodygame3.head.model.HonorRankModel;
import com.softtek.lai.module.bodygame3.head.model.TypeModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.module.ranking.persenter.RankManager;
import com.softtek.lai.module.ranking.view.NationalFragment;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by lareina.qiao on 11/24/2016.
 */
@InjectLayout(R.layout.fragment_weekhonor)
public class WeekHonorFragment extends LazyBaseFragment implements RankManager.RankManagerCallback {
    @InjectView(R.id.spinner)
    ArrowSpinner2 spinner;//下拉
    HeadService headService;

    public static WeekHonorFragment getInstance(){
        WeekHonorFragment fragment=new WeekHonorFragment();
        Bundle data=new Bundle();
        fragment.setArguments(data);
        return fragment;
    }
    @Override
    protected void lazyLoad() {
        doGetData();
        final List<String> datas = new ArrayList<String>();
        datas.add("第一周");
        datas.add("第二周");
        datas.add("第三周");
        datas.add("第四周");
        datas.add("第五周");
        spinner.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), datas, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
//                TextView tv_class_name = holder.getView(R.id.tv_title);
//                tv_class_name.setText(data);
            }


            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return datas.get(position);
            }


        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void doGetData() {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doGetHonorRoll(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(),"194b5166-a151-42a8-951e-e437dae8d015","ByWeightRatio","ByWeek",1,true, new RequestCallback<ResponseData<HonorRankModel>>() {
            @Override
            public void success(ResponseData<HonorRankModel> honorRankModelResponseData, Response response) {
                int status=honorRankModelResponseData.getStatus();
                Util.toastMsg(honorRankModelResponseData.getMsg());
            }
        });
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void getResult(RankModel result) {

    }
}
