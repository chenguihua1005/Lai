package com.softtek.lai.module.assistant.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.assistant.model.AssistantApplyInfo;
import com.softtek.lai.module.assistant.model.AssistantClassInfo;
import com.softtek.lai.module.assistant.model.AssistantDetailInfo;
import com.softtek.lai.module.assistant.model.AssistantInfo;
import com.softtek.lai.module.assistant.model.InviteStudentInfo;
import com.softtek.lai.module.assistant.model.MarchInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface GameService {
    @GET("/Index/GetMatchInfo")
    void getMatchInfo(@Header("token") String token,
                      @Query("dtime") String dtime,
                      @Query("group") String group,
                      Callback<ResponseData<List<MarchInfo>>> callback);

}
