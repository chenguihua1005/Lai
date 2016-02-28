/*
 * Copyright (c)  2015. Softtek
 */

package com.softtek.lai.module.msg;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.test.adapter.ListViewTestActivity;
import com.softtek.lai.test.api.APIActivity;
import com.softtek.lai.test.binding.BindingActivity;
import com.softtek.lai.test.db.DBTestActivity;
import com.softtek.lai.test.validate.ValidateActivity;
import com.softtek.lai.test.zlistview.ZListViewActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.PropertiesManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.ZillaAdapter;
import zilla.libzilla.listview.ZListViewWraper;

/**
 * 消息中心页面
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MsgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_test_home)
public class MsgFragment extends BaseFragment{

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.goadapter, R.id.godb, R.id.goapi, R.id.gobinding,R.id.gozlistview,R.id.govalidate})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.goadapter:
                startActivity(new Intent(getActivity(), ListViewTestActivity.class));
                break;
            case R.id.godb:
                startActivity(new Intent(getActivity(), DBTestActivity.class));
                break;
            case R.id.goapi:
                startActivity(new Intent(getActivity(), APIActivity.class));
                break;
            case R.id.gobinding:
                startActivity(new Intent(getActivity(), BindingActivity.class));
                break;
            case R.id.gozlistview:
                startActivity(new Intent(getActivity(), ZListViewActivity.class));
                break;
            case R.id.govalidate:
                startActivity(new Intent(getActivity(), ValidateActivity.class));
                break;
            default:
                break;
        }
    }
}
