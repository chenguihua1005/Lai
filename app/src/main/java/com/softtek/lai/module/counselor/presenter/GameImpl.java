/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.counselor.adapter.GameAdapter;
import com.softtek.lai.module.counselor.model.MarchInfoModel;
import com.softtek.lai.module.counselor.net.CounselorService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.util.Util;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class GameImpl implements IGamePresenter {

    private CounselorService counselorService;
    private Context context;

    public GameImpl(Context context) {
        this.context = context;
        counselorService = ZillaApi.NormalRestAdapter.create(CounselorService.class);
    }


    @Override
    public void getMatchInfo(String dtime, String group, final ListView list_game, final ProgressDialog progressDialog) {
        String token = UserInfoModel.getInstance().getToken();
        counselorService.getMatchInfo(token, dtime, group, new Callback<ResponseData<List<MarchInfoModel>>>() {
            @Override
            public void success(ResponseData<List<MarchInfoModel>> listResponseData, Response response) {
                Log.e("jarvis", listResponseData.toString());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                int status = listResponseData.getStatus();
                List<MarchInfoModel> list = listResponseData.getData();
                switch (status) {
                    case 200:
                        list_game.setVisibility(View.VISIBLE);
                        GameAdapter adapter = new GameAdapter(context, list);
                        list_game.setAdapter(adapter);
                        break;
                    case 100:
                        list_game.setVisibility(View.GONE);
                        break;
                    default:
                        list_game.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                ZillaApi.dealNetError(error);
                error.printStackTrace();
            }
        });
    }
}
