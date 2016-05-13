package com.softtek.lai.module.act.view;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.module.tips.adapter.AskHealthyAdapter;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.model.AskHealthyResponseModel;
import com.softtek.lai.module.tips.presenter.AskHealthyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis on 4/27/2016.
 */
@InjectLayout(R.layout.fragment_act_detail)
public class ActDetailsFragment extends BaseFragment implements AskHealthyManager.AskHealthyManagerCallback {
//    @InjectView(R.id.img_jd)
//    ImageView img_jd;
//    @InjectView(R.id.img_full)
//    ImageView img_full;
//    ClipDrawable clipDrawable;
//    final Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0x1233) {
//                //修改ClipDrawable的level值
//                clipDrawable.setLevel(clipDrawable.getLevel() + 100);
//            } else {
//                img_full.setVisibility(View.VISIBLE);
//                img_jd.setVisibility(View.GONE);
//            }
//        }
//    };

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
//        clipDrawable = (ClipDrawable) img_jd.getDrawable();
//        clipDrawable.setLevel(0);
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                Message msg = new Message();
//                msg.what = 0x1233;
//                //发送消息,通知应用修改ClipDrawable对象的level值
//                handler.sendMessage(msg);
//                //取消定时器
//                if (clipDrawable.getLevel() >= 10000) {
//                    timer.cancel();
//                    Message msgs = new Message();
//                    msgs.what = 0x1234;
//                    //发送消息,通知应用修改ClipDrawable对象的level值
//                    handler.sendMessage(msgs);
//                }
//            }
//        }, 0, 60);


    }

    @Override
    public void getHealthyList(AskHealthyResponseModel model) {

    }
}
