package com.softtek.lai.module.laicheng_new.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.laicheng_new.adapter.ChooseCustomerRecyclerViewAdapter;
import com.softtek.lai.module.laicheng_new.model.CustomerData;
import com.softtek.lai.module.laicheng_new.net.CustomerService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;

/**
 * Created by jia.lu on 11/29/2017.
 */

public class StaffFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private ChooseCustomerRecyclerViewAdapter recyclerViewAdapter;
    private List<CustomerData.ItemsBean> customerDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(getActivity(), R.layout.fragment_customer_intention,null);
        initData();
        initView();
        return mView;
    }

    private void initView(){
        mRecyclerView = mView.findViewById(R.id.rcv_content);
        recyclerViewAdapter = new ChooseCustomerRecyclerViewAdapter(customerDataList, new ChooseCustomerRecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(CustomerData.ItemsBean item) {

            }
        },getActivity());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
    }

    private void initData(){
        ZillaApi.NormalRestAdapter.create(CustomerService.class)
                .getMarketPersonnel(UserInfoModel.getInstance().getToken(), 1, 999, new RequestCallback<ResponseData<CustomerData>>() {
            @Override
            public void success(ResponseData<CustomerData> responseData, Response response) {
                if (responseData.getStatus() == 200){
                    if (responseData.getData().getItems() != null){
                        customerDataList.addAll(responseData.getData().getItems());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
                super.failure(error);
            }
        });
    }
}
