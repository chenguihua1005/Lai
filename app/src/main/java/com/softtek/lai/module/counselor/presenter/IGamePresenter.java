package com.softtek.lai.module.counselor.presenter;

import android.widget.ListView;

/**
 * Created by jarvis on 3/3/2016.
 */
public interface IGamePresenter {

    //大赛赛况
    void getMatchInfo(String dtime, String group, ListView list_game);

}
