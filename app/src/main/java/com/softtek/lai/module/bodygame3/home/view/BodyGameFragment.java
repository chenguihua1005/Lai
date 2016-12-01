package com.softtek.lai.module.bodygame3.home.view;

import android.util.Log;
import android.view.View;
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
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.head.adapter.PartnerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment;
import com.softtek.lai.module.bodygame3.head.view.HeadGameFragment1;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.StudentFragment;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.chart.Chart;
import com.softtek.lai.widgets.chart.Entry;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment {

    @InjectView(R.id.chart)
    Chart chart;
    private List<String> xAxis = new ArrayList<>();//x轴数据
    private List<Entry> yAxis = new ArrayList<>();//轴数据


    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentEmpty(false);
                setContentShown(true);
            }
        }, 3000);*/
    }

    @Override
    protected void initViews() {
        Log.e("0000",UserInfoModel.getInstance().getUser().getHasThClass()+"");
        if(UserInfoModel.getInstance().getUser().getHasThClass()==1){//0无班级，1有班级
            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg,new HeadGameFragment1()).commit();
        }else if(UserInfoModel.getInstance().getUser().getHasThClass()==0){
            getChildFragmentManager().beginTransaction().replace(R.id.contain_frg,new HeadGameFragment()).commit();
        }


        xAxis.add("10月18");
        xAxis.add("19");
        xAxis.add("20");
        xAxis.add("21");
        xAxis.add("22");
        xAxis.add("23");
        xAxis.add("24");

        yAxis.add(new Entry(0, 3000));
        //yAxis.add(new Entry(1,8000));
        yAxis.add(new Entry(2, 3000));
        yAxis.add(new Entry(3, 1000));
        yAxis.add(new Entry(4, 3000));
        yAxis.add(new Entry(5, 6000));
        yAxis.add(new Entry(6, 8000));
        chart.setDate(xAxis, yAxis, 8000);
    }

    @Override
    protected void initDatas() {

    }

    public void show(View view) {

    }

}
