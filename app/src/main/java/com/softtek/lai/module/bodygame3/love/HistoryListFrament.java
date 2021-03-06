package com.softtek.lai.module.bodygame3.love;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
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
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

@InjectLayout(R.layout.fragment_history_list)
public class HistoryListFrament extends LazyBaseFragment2 {

    private LoverMemberAdapter adapter;
    private List<LoverModel> loverModels = new ArrayList<>();

    @InjectView(R.id.history_list)
    ListView listview;
    @InjectView(R.id.empty)
    FrameLayout empty;

    public static Fragment getInstance(long accountId, String classId) {
        Fragment fragment = new HistoryListFrament();
        Bundle bundle = new Bundle();
        bundle.putLong("accountId", accountId);
        bundle.putString("classId", classId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        LoverService service = ZillaApi.NormalRestAdapter.create(LoverService.class);
        String classId = getArguments().getString("classId");
        service.getIntroducerList(classId, UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), classId, 0, new RequestCallback<ResponseData<List<LoverModel>>>() {
            @Override
            public void success(ResponseData<List<LoverModel>> listResponseData, Response response) {
                try {
                    loverModels.clear();
                    int status = listResponseData.getStatus();
                    setContentEmpty(false);
                    setContentShown(true);
                    if (200 == status) {
                        if (listResponseData.getData() != null) {
                            loverModels.addAll(listResponseData.getData());
                        }

                        if (loverModels != null && loverModels.size() > 0) {
                            listview.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                            adapter.updateData(loverModels);
                        } else {
                            listview.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Util.toastMsg(listResponseData.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                try {
                    setContentEmpty(true);
                    setContentShown(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        adapter = new LoverMemberAdapter(getContext(), loverModels);
        listview.setAdapter(adapter);

    }
}
