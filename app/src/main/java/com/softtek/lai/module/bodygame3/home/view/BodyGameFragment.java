package com.softtek.lai.module.bodygame3.home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame.model.TotolModel;
import com.softtek.lai.module.bodygame.net.BodyGameService;
import com.softtek.lai.module.bodygame2.adapter.SPPCAdapter;
import com.softtek.lai.module.bodygame2.adapter.SaiKuangAdapter;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.module.bodygame2.model.Tips;
import com.softtek.lai.module.bodygame2.present.SPManager;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.bodygame2.view.SearchPcActivity;
import com.softtek.lai.module.counselor.view.AssistantActivity;
import com.softtek.lai.module.counselor.view.GameActivity;
import com.softtek.lai.module.counselor.view.SPHonorActivity;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.jingdu.view.JingduActivity;
import com.softtek.lai.module.message.net.MessageService;
import com.softtek.lai.module.message.view.JoinGameDetailActivity;
import com.softtek.lai.module.message2.view.Message2Activity;
import com.softtek.lai.module.review.view.ReviewActivity;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.softtek.lai.module.tips.view.AskDetailActivity;
import com.softtek.lai.module.tips.view.TipsActivity;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CustomGridView;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame)
public class BodyGameFragment extends LazyBaseFragment {


    public BodyGameFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

}
