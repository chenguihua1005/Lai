package com.softtek.lai.module.laicheng_new.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng_new.adapter.GroupRecyclerViewAdapter;
import com.softtek.lai.module.laicheng_new.model.GroupModel;
import com.softtek.lai.module.laicheng_new.net.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 3/14/2018.
 */

public class BodyGameListFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private GroupRecyclerViewAdapter groupAdapter;
    private List<GroupModel> groupList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(getActivity(), R.layout.fragment_body_game_list, null);
        initData();
        initView();
        return mView;
    }

    private void initData() {
        groupList.clear();
        ZillaApi.NormalRestAdapter.create(CustomerService.class)
                .getListOfClassMember(UserInfoModel.getInstance().getToken(), new RequestCallback<ResponseData<List<GroupModel>>>() {
                    @Override
                    public void success(ResponseData<List<GroupModel>> responseData, Response response) {
                        if (responseData.getStatus() == 200) {
                            groupList.addAll(responseData.getData());
                            groupAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.rcv_content);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        groupAdapter = new GroupRecyclerViewAdapter(groupList, getActivity(), new GroupRecyclerViewAdapter.FinishListener() {
            @Override
            public void setOnFinishListener() {
                getActivity().finish();
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(groupAdapter);
    }
}
