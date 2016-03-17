package com.softtek.lai.module.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import com.ggx.jerryguan.viewflow.CircleFlowIndicator;
import com.ggx.jerryguan.viewflow.ViewFlow;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.home.adapter.AdvAdapter;
import com.softtek.lai.module.login.contants.Constants;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.module.retest.Write;
import com.softtek.lai.utils.ACache;
import com.softtek.lai.widgets.CustomGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.util.Util;

/**
 * Created by jerry.guan on 3/15/2016.
 */
@InjectLayout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements View.OnTouchListener{
    private ACache aCache;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.vf_adv)
    ViewFlow vf_adv;

    @InjectView(R.id.cfi_circle)
    CircleFlowIndicator cfi_circle;

    @InjectView(R.id.gv_model)
    CustomGridView gv_model;

   @InjectView(R.id.button)
   Button button;


    @Override
    protected void initViews() {
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        aCache=ACache.get(getActivity(), Constants.USER_ACACHE_DATA_DIR);
        User user= (User) aCache.getAsObject(Constants.USER_ACACHE_KEY);
        switch(user.getUserrole())
        {
            case "0":
            {
                Intent intent = new Intent(getContext(), Counselor.class);
                startActivity(intent);
                Util.toastMsg(user.getUserrole());

            }
            break;
            case "1":
            {
                Intent intent = new Intent(getActivity(), Write.class);
                startActivity(intent);
                Util.toastMsg(user.getUserrole());
            }
            break;
            case "2":
            {
                Intent intent = new Intent(getActivity(), Write.class);
                startActivity(intent);
                Util.toastMsg(user.getUserrole());
            }
            break;
            case "3":
            {
                Intent intent = new Intent(getActivity(), Write.class);
                startActivity(intent);
                Util.toastMsg(user.getUserrole());
            }
            break;
            case "4":
            {
                Intent intent = new Intent(getActivity(), Write.class);
                startActivity(intent);
                Util.toastMsg(user.getUserrole());
            }
            break;
            case "5":
            {
                Intent intent = new Intent(getActivity(), Write.class);
                startActivity(intent);
                Log.i("用户角色",user.getUserrole());
                Util.toastMsg(user.getUserrole());
            }
            break;
        }

    }
});
    }

    @Override
    protected void initDatas() {
        List<String> datas=new ArrayList<>();
        for(int i=0;i<10;i++){
            datas.add("item");
        }
        BaseAdapter adapter=new ZillaAdapter<String>(getContext(),datas,R.layout.gridview_item,ViewHolderModel.class);
        gv_model.setAdapter(adapter);
        vf_adv.setAdapter(new AdvAdapter(getContext()));
        vf_adv.setFlowIndicator(cfi_circle);
        vf_adv.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        getViewPage(v.getParent()).requestDisallowInterceptTouchEvent(true);
        return false;
    }

    static class ViewHolderModel {

    }

    private ViewParent getViewPage(ViewParent v){

        if(v!=null&&v.getClass().getName().equals("android.support.v4.view.ViewPager")){
            Log.i(v.getClass().getName());
            return v;
        }
        return getViewPage(v.getParent());
    }
}
