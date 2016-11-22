package com.softtek.lai.module.bodygame3.head.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.PartnerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkHeadGameFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkHeadGameFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_head_game_fragment1)
public class HeadGameFragment1 extends LazyBaseFragment {
    //toolbar标题
    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;
    @InjectView(R.id.spinner_title)
    ArrowSpinner2 spinner_title;
    @InjectView(R.id.list_partner)
    ListView list_partner;
    HeadService service;
    PartnerAdapter partnerAdapter;
    private List<PartnersModel> partnersModels = new ArrayList<PartnersModel>();

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        partnerAdapter = new PartnerAdapter((BaseActivity) getActivity(), partnersModels);
        list_partner.setAdapter(partnerAdapter);
        //配置列表数据
        final List<String> data = new ArrayList<>();
        data.add("测试数据1测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        final List<String> datas = new ArrayList<>();
        datas.add("体重比");
        datas.add("体脂");
        datas.add("减重比");
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), data, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return data.get(position);
            }

        });
        spinner_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), datas, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_title);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return datas.get(position);
            }

        });
        Log.e("ddddd", UserInfoModel.getInstance().getToken() + "," + UserInfoModel.getInstance().getUser().getUserid());
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        service.getfirst(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), 10, new RequestCallback<ResponseData<ClassinfoModel>>() {
            @Override
            public void success(ResponseData<ClassinfoModel> classinfoModelResponseData, Response response) {
                Util.toastMsg(classinfoModelResponseData.getMsg());
                if(classinfoModelResponseData.getData()!=null){
                    ClassinfoModel classinfoModel = classinfoModelResponseData.getData();
                    if(classinfoModel.getPartnersList()!=null){
                        partnersModels.addAll(classinfoModel.getPartnersList());
                        partnerAdapter.update(partnersModels);
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
