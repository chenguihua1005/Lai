package com.softtek.lai.module.retest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.retest.model.RetestAudit;
import com.softtek.lai.module.retest.model.RetestWrite;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.utils.DisplayUtil;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_audit)
public class Audit extends BaseActivity {
    //标题栏
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_right)
    TextView tv_right;
private RetestPre retestPre;
    private RetestAudit retestAudit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initDatas() {

        title.setText(R.string.AuditBarT);
        tv_right.setText(R.string.AuditBarR);
        tv_right.setTextColor(Color.BLACK);
        retestPre=new RetestclassImp();
        retestAudit=new RetestAudit();
        retestPre.doGetAudit(3,2,"36");

    }
}
