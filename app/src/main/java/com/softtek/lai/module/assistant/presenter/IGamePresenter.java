package com.softtek.lai.module.assistant.presenter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IGamePresenter {

    //大赛赛况
    void getMatchInfo(String dtime, String group,ListView list_game);

}
