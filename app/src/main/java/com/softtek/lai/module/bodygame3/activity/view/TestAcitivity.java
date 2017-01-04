package com.softtek.lai.module.bodygame3.activity.view;

import android.widget.ExpandableListView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by lareina.qiao on 1/4/2017.
 */
@InjectLayout(R.layout.activity_fcst)
public class TestAcitivity extends BaseActivity {
    @InjectView(R.id.exlisview_body)
    ExpandableListView exlisview_body;
    private List<String> groupArray=new ArrayList<>();
    private List<List<String>> childArray=new ArrayList<>();
    private List<String> child=new ArrayList<>();
    FcStDataModel fcStDataModel;

    @Override
    protected void initViews() {
        groupArray.add(0,"身体围度（选填）");
//        String[]chi={"胸围"};
        child.add(0,"胸围");
        child.add(1,"腰围");
        child.add(2,"臀围");
        childArray.add(child);
        fcStDataModel=new FcStDataModel();
        fcStDataModel.setCircum("102");
        fcStDataModel.setWaistline("103");
        fcStDataModel.setHiplie("104");
//        fcStDataModel.setDoLegGirth("102");
        exlisview_body = (ExpandableListView) findViewById(R.id.exlisview_body);
        exlisview_body.setGroupIndicator(null);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,groupArray,childArray,fcStDataModel);
        exlisview_body.setAdapter(adapter);
    }

    @Override
    protected void initDatas() {

    }
}
