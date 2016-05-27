package com.softtek.lai.module.act.view;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.presenter.ActManager;
import com.softtek.lai.module.tips.adapter.AskHealthyAdapter;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.presenter.AskHealthyManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 */
@InjectLayout(R.layout.fragment_act_detail)
public class ActDetailsFragment extends BaseFragment implements ActManager.GetActDetailsCallBack {
    @InjectView(R.id.img)
    ImageView img;

    @InjectView(R.id.img_state)
    ImageView img_state;

    @InjectView(R.id.text_title)
    TextView text_title;

    @InjectView(R.id.text_state)
    TextView text_state;

    @InjectView(R.id.text_time)
    TextView text_time;

    @InjectView(R.id.text_lx)
    TextView text_lx;

    @InjectView(R.id.text_gz)
    TextView text_gz;

    @InjectView(R.id.text_sm)
    TextView text_sm;

    @InjectView(R.id.text_mb)
    TextView text_mb;

    @InjectView(R.id.text_mb_value)
    TextView text_mb_value;

    ActManager actManager;
    String id;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        actManager = new ActManager(this);
        id = getActivity().getIntent().getStringExtra("id");
        actManager.getActDetails(id);
    }

    @Override
    public void getActDetails(String type, ActDetailModel model) {
        if ("true".equals(type)) {
            String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
            if ("".equals(model.getActimg()) || "null".equals(model.getActimg()) || model.getActimg() == null) {
                Picasso.with(getContext()).load("111").fit().error(R.drawable.img_default).into(img);
            } else {
                Picasso.with(getContext()).load(path + model.getActimg()).error(R.drawable.img_default).into(img);
            }
            text_title.setText(model.getActTitle().toString());
            String status = model.getAcStatus();
            if ("1".equals(status)) {
                text_state.setText("进行中");
                img_state.setImageResource(R.drawable.img_activity_1);
                text_state.setTextColor(getResources().getColor(R.color.editorText));
            } else if ("0".equals(status)) {
                text_state.setText("已结束");
                img_state.setImageResource(R.drawable.img_activity_3);
                text_state.setTextColor(getResources().getColor(R.color.word16));
            } else {
                text_state.setText("未开始");
                img_state.setImageResource(R.drawable.img_activity_2);
                text_state.setTextColor(getResources().getColor(R.color.word15));

            }
            String activeType = model.getActiveType();
            if ("1".equals(activeType)) {
                text_lx.setText("团体赛");
            } else {
                text_lx.setText("个人赛");
            }
            String start = model.getStart();
            String end = model.getEnd();

            String start_time = "";
            String end_time = "";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("MM月dd号 HH:mm");
            try {
                Date start_date = sdf.parse(start);
                Date end_date = sdf.parse(end);
                start_time = format.format(start_date);
                end_time = format.format(end_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            text_time.setText(start_time + " - " + end_time);
            text_sm.setText(model.getActIntroduction().toString());
            String targetType = model.getTargetType();
            if ("1".equals(targetType)) {
                text_mb.setText("目标公里数： ");
                text_gz.setText("目标公里数");
                text_mb_value.setText(model.getTarget() + "公里");
            } else {
                text_mb.setText("目标步数： ");
                text_gz.setText("目标步数");
                text_mb_value.setText(model.getTarget());
            }
        }
    }
}
