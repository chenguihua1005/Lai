package com.softtek.lai.module.health.view;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.health.adapter.HealthyRecordFragmentAdapter;
import com.softtek.lai.module.health.model.HealthCircrumModel;
import com.softtek.lai.module.health.model.HealthFatModel;
import com.softtek.lai.module.health.model.HealthHiplieModel;
import com.softtek.lai.module.health.model.HealthUpArmGirthModel;
import com.softtek.lai.module.health.model.HealthWaistlineModel;
import com.softtek.lai.module.health.model.HealthWeightModel;
import com.softtek.lai.module.health.model.HealthdoLegGirthModel;
import com.softtek.lai.module.health.model.HealthupLegGirthModel;
import com.softtek.lai.module.health.model.PysicalModel;
import com.softtek.lai.module.health.presenter.HealthRecordManager;
import com.softtek.lai.module.health.presenter.HealthyRecordImpl;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.widgets.NoSlidingViewPage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_weight)
public class HealthyRecordActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener,HealthRecordManager.HealthRecordCallBack{

    private HealthyRecordImpl healthyRecord;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.tab_content)
    NoSlidingViewPage tab_content;
    RetestPre retestPre;
    UserInfoModel userInfoModel=UserInfoModel.getInstance();
    String moblie=userInfoModel.getUser().getMobile();
    List<Float> dates=new ArrayList<Float>();

    List<Fragment> fragmentList=new ArrayList<>();
    HealthRecordManager healthRecordManager;

    @Override
    protected void initViews() {

        ll_left.setOnClickListener(this);
        tv_title.setText("历史数据");
        retestPre=new RetestclassImp();
        retestPre.GetUserMeasuredInfo(moblie);

    }

//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }

    @Override
    protected void initDatas() {
//        EventBus.getDefault().register(this);
        healthRecordManager=new HealthRecordManager(this);
        WeightFragment weightFragment=new WeightFragment();
        BodyFatFragment bodyFatFragment=new BodyFatFragment();
        FatFragment fatFragment=new FatFragment();
        WaistlineFragment waistlineFragment=new WaistlineFragment();
        BustFragment bustFragment=new BustFragment();
        HiplineFragment hiplineFragment=new HiplineFragment();
        UpHiplineFragment upHiplineFragment=new UpHiplineFragment();
        LegFragment legFragment=new LegFragment();
        ShinFragment shinFragment=new ShinFragment();
        fragmentList.add(weightFragment);
        fragmentList.add(bodyFatFragment);
        fragmentList.add(fatFragment);
        fragmentList.add(bustFragment);
        fragmentList.add(waistlineFragment);
        fragmentList.add(hiplineFragment);
        fragmentList.add(upHiplineFragment);
        fragmentList.add(legFragment);
        fragmentList.add(shinFragment);
        tab_content.setAdapter(new HealthyRecordFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tab.setupWithViewPager(tab_content);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        int item=getIntent().getIntExtra("id",0);
        tab_content.setCurrentItem(item);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void getHealthPysicalRecords(PysicalModel pysicalModel) {

    }

    @Override
    public void getHealthWeightRecords(HealthWeightModel healthWeightModel) {

    }

    @Override
    public void getHealthfatRecords(HealthFatModel healthFatModel) {

    }

    @Override
    public void getHealthcircumRecords(HealthCircrumModel healthCircrumModel) {

    }

    @Override
    public void getHealthwaistlineRecords(HealthWaistlineModel healthWaistlineModel) {

    }

    @Override
    public void getHealthhiplieRecords(HealthHiplieModel healthHiplieModel) {

    }

    @Override
    public void getHealthupArmGirthRecords(HealthUpArmGirthModel healthUpArmGirthModel) {

    }

    @Override
    public void getGetHealthupLegGirthRecords(HealthupLegGirthModel healthupLegGirthModel) {

    }

    @Override
    public void getHealthdoLegGirthRecords(HealthdoLegGirthModel healthdoLegGirthModel) {

    }

//    @Subscribe
//    public void getPysicalList(PysicalModel pysicalModel) {
//        System.out.println("健康记录脂肪" + pysicalModel.getFirstrecordtime());
//        for (int i=pysicalModel.getPysicallist().size()-1;i>-1;i--) {
//            dates.add(Float.parseFloat(pysicalModel.getPysicallist().get(i).getPysical()));
//
//        }
//
//    }
//    @Subscribe
//    public void getWeightList(HealthWeightModel healthWeightModel) {
//        System.out.println("健康记录体重" + healthWeightModel.getFirstrecordtime());
//        int n=healthWeightModel.getweightlist().size();
//        for (int i=healthWeightModel.getweightlist().size()-1;i>-1;i--) {
//            dates.add(Float.parseFloat(healthWeightModel.getweightlist().get(i).getWeight()));
//        }


//    }



}
