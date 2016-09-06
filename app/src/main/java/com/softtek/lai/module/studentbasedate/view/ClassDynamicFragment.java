package com.softtek.lai.module.studentbasedate.view;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.grade.adapter.DynamicAdapter;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.studentbasedate.net.StudentBaseDateService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jerry.guan on 4/13/2016.
 */
@InjectLayout(R.layout.fragment_basedate_dynamic)
public class ClassDynamicFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2{

    @InjectView(R.id.lv)
    PullToRefreshListView lv;

    private StudentBaseDateService service;
    private DynamicAdapter dynamicAdapter;
    private List<DynamicInfoModel> dynamicInfoModels=new ArrayList<>();
    private int pageIndex=1;
    private long classId=0;

    public static ClassDynamicFragment getInstance(){
        ClassDynamicFragment fragment=new ClassDynamicFragment();
        return fragment;
    }

    @Override
    protected void initViews() {
        lv.setOnRefreshListener(this);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    protected void initDatas() {
        service= ZillaApi.NormalRestAdapter.create(StudentBaseDateService.class);
        dynamicAdapter=new DynamicAdapter(getContext(),dynamicInfoModels);
        lv.setAdapter(dynamicAdapter);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex=1;
        String token= UserInfoModel.getInstance().getToken();
        service.getClassDynamic(token, classId, 1, new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                dealCallbackData(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                dealCallbackData(null);
                super.failure(error);
            }
        });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        String token= UserInfoModel.getInstance().getToken();
        service.getClassDynamic(token, classId, pageIndex, new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                dealCallbackData(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                dealCallbackData(null);
                super.failure(error);
            }
        });
    }

    private void dealCallbackData(List<DynamicInfoModel> models){
        lv.onRefreshComplete();
        if(models==null){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(models.isEmpty()){
            pageIndex=--pageIndex<1?1:pageIndex;
            return;
        }
        if(pageIndex==1){
            dynamicInfoModels.clear();
        }
        dynamicInfoModels.addAll(models);
        dynamicAdapter.notifyDataSetChanged();
    }

    public void loadDynamic(long classId){
        String token= UserInfoModel.getInstance().getToken();
        this.classId=classId;
        service.getClassDynamic(token, classId, 1, new RequestCallback<ResponseData<List<DynamicInfoModel>>>() {
            @Override
            public void success(ResponseData<List<DynamicInfoModel>> listResponseData, Response response) {
                dealCallbackData(listResponseData.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                dealCallbackData(null);
                super.failure(error);
            }
        });
    }
}
