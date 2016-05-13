package com.softtek.lai.module.laisportmine.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.laisportmine.adapter.MyPkNoticeAdapter;
import com.softtek.lai.module.laisportmine.model.PkNoticeModel;
import com.softtek.lai.module.laisportmine.present.PkNoticeManager;
import com.softtek.lai.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_pk_list)
public class MyPkListActivity extends BaseActivity implements View.OnClickListener,PkNoticeManager.PkNoticeCallback,
        AdapterView.OnItemLongClickListener{
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.listview_pk)
    ListView listview_pk;
    @InjectView(R.id.ll_nomessage)
    LinearLayout ll_nomessage;
    private PkNoticeManager pkNoticeManager;
    private MyPkNoticeAdapter myPkNoticeAdapter;
    private List<PkNoticeModel>pkNoticeModelList=new ArrayList<PkNoticeModel>();
    private CharSequence[] items={"删除"};
    int positions;
    @Override
    protected void initViews() {
        tv_title.setText("PK消息");
        ll_left.setOnClickListener(this);
        listview_pk.setOnItemLongClickListener(this);
    }

    @Override
    protected void initDatas() {
        myPkNoticeAdapter=new MyPkNoticeAdapter(this,pkNoticeModelList);
        listview_pk.setAdapter(myPkNoticeAdapter);
        pkNoticeManager=new PkNoticeManager(this);
        pkNoticeManager.doGetPKINotice();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void getPkNotice(List<PkNoticeModel> pkNoticeModels) {
        if (pkNoticeModels==null)
        {
            ll_nomessage.setVisibility(View.VISIBLE);
        }
        else {
            pkNoticeModelList = pkNoticeModels;
            myPkNoticeAdapter.updateData(pkNoticeModelList);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        positions=position;
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pkNoticeModelList.remove(positions);
                myPkNoticeAdapter.notifyDataSetChanged();


            }
        }).create().show();
        return false;
    }
}
