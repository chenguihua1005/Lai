package com.softtek.lai.module.bodygame3.home.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.adapter.ClassMainStudentAdapter;
import com.softtek.lai.module.bodygame2.adapter.ClassSelectAdapter;
import com.softtek.lai.module.bodygame2.model.ClassChangeModel;
import com.softtek.lai.module.bodygame2.model.ClassDetailModel;
import com.softtek.lai.module.bodygame2.model.ClassListModel;
import com.softtek.lai.module.bodygame2.model.ClassMainModel;
import com.softtek.lai.module.bodygame2.model.ClassModel;
import com.softtek.lai.module.bodygame2.model.ClmListModel;
import com.softtek.lai.module.bodygame2.model.DyNoticeModel;
import com.softtek.lai.module.bodygame2.model.DySysModel;
import com.softtek.lai.module.bodygame2.model.MemberChangeModel;
import com.softtek.lai.module.bodygame2.present.ClassMainManager;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2.view.DYActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.counselor.net.CounselorService;
import com.softtek.lai.module.counselor.presenter.IStudentPresenter;
import com.softtek.lai.module.counselor.presenter.StudentImpl;
import com.softtek.lai.module.counselor.view.AssistantListActivity;
import com.softtek.lai.module.counselor.view.CreateCounselorClassActivity;
import com.softtek.lai.module.counselor.view.InviteStudentActivity;
import com.softtek.lai.module.grade.model.BannerUpdateCallBack;
import com.softtek.lai.module.grade.net.GradeService;
import com.softtek.lai.module.grade.presenter.GradeImpl;
import com.softtek.lai.module.grade.presenter.IGrade;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.ListViewUtil;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.ObservableScrollView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment {
    public MoreFragment() {
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
