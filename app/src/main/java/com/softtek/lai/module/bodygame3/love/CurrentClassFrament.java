package com.softtek.lai.module.bodygame3.love;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment2;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.love.adapter.LoverMemberAdapter;
import com.softtek.lai.module.bodygame3.love.model.LoverModel;
import com.softtek.lai.module.bodygame3.love.service.LoverService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

@InjectLayout(R.layout.fragment_current_class)
public class CurrentClassFrament extends LazyBaseFragment2 {
    private static final String TAG = "CurrentClassFrament";

    private LoverMemberAdapter adapter;
    private List<LoverModel> loverModels;

    @InjectView(R.id.currentClass_listview)
    ListView listview;

    public static Fragment getInstance(long accountId, String classId) {
        Fragment fragment = new CurrentClassFrament();
        Bundle bundle = new Bundle();
        bundle.putLong("accountId", accountId);
        bundle.putString("classId", classId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        setEmptyText("暂无数据");
        LoverService service = ZillaApi.NormalRestAdapter.create(LoverService.class);
        String classId=getArguments().getString("classId");
        service.getIntroducerList(classId,UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classId, 1, new RequestCallback<ResponseData<List<LoverModel>>>() {
            @Override
            public void success(ResponseData<List<LoverModel>> listResponseData, Response response) {
                try {
                    setContentEmpty(false);
                    setContentShown(true);
                    loverModels.addAll(listResponseData.getData());
                    if (loverModels != null) {
                        adapter.updateData(loverModels);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    setContentEmpty(true);
                    setContentShown(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.failure(error);
            }
        });

    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {
        loverModels = new ArrayList<LoverModel>();
        adapter = new LoverMemberAdapter(getContext(), loverModels);
        listview.setAdapter(adapter);

    }
}
