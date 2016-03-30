package com.softtek.lai.module.assistant.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.assistant.adapter.GameAdapter;
import com.softtek.lai.module.assistant.model.MarchInfo;
import com.softtek.lai.module.assistant.net.GameService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class GameImpl implements IGamePresenter {

    private GameService gameService;
    private Context context;

    public GameImpl(Context context) {
        this.context = context;
        gameService = ZillaApi.NormalRestAdapter.create(GameService.class);
    }


    @Override
    public void getMatchInfo(String dtime, String group, final ListView list_game) {
        String token = SharedPreferenceService.getInstance().get("token", "");
        gameService.getMatchInfo(token, dtime, group, new Callback<ResponseData<List<MarchInfo>>>() {
            @Override
            public void success(ResponseData<List<MarchInfo>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                int status = listResponseData.getStatus();
                List<MarchInfo> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        GameAdapter adapter=new GameAdapter(context,list);
                        list_game.setAdapter(adapter);
                        break;
                    default:
                        Util.toastMsg(listResponseData.getMsg());
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Util.toastMsg("获取龙虎榜列表失败");
            }
        });
    }
}
