package com.softtek.lai.module.bodygamest.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2pc.view.*;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygamest.model.LossModel;
import com.softtek.lai.module.bodygamest.present.IStudentPresenter;
import com.softtek.lai.module.bodygamest.present.PhotoListIml;
import com.softtek.lai.module.bodygamest.present.PhotoListPre;
import com.softtek.lai.module.bodygamest.present.StudentImpl;
import com.softtek.lai.module.home.view.HomeActviity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.widgets.SelectPicPopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_student_score)
public class StudentScoreActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.iv_email)
    ImageView iv_email;

    @InjectView(R.id.img_mo_message)
    ImageView img_mo_message;

    @InjectView(R.id.list_student_score)
    ListView list_student_score;
    private IStudentPresenter studentPresenter;
    private UserModel userModel;
    SelectPicPopupWindow menuWindow;
    String url;
    private ProgressDialog progressDialog;
    private PhotoListPre photoListPre;
    LossModel lossModel;
    PopupWindow mPopupWindow;
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        ll_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        iv_email.setVisibility(View.VISIBLE);
        iv_email.setImageResource(R.drawable.img_share_bt);
        iv_email.setOnClickListener(this);
        list_student_score.setEmptyView(img_mo_message);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载内容...");
    }

    @Override
    protected void initDatas() {
        tv_title.setText("成绩单");
        photoListPre = new PhotoListIml();
        studentPresenter = new StudentImpl(this);
        userModel = UserInfoModel.getInstance().getUser();
        String id = userModel.getUserid();
        dialogShow("加载中");
        studentPresenter.getTranscrip(id, list_student_score);
    }

    @Subscribe
    public void onEvent(LossModel model) {
        if (UserInfoModel.getInstance().getUser() == null) {
            return;
        }
        lossModel = model;
        String path = AddressManager.get("shareHost");
        url = path + "ShareTranscript?AccountId=" + UserInfoModel.getInstance().getUser().getUserid();
        menuWindow = new SelectPicPopupWindow(StudentScoreActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        menuWindow.showAtLocation(findViewById(R.id.lin), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            startActivity(new Intent(StudentScoreActivity.this, BodyGamePCActivity.class));
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                //startActivity(new Intent(StudentScoreActivity.this, BodyGamePCActivity.class));
                break;
            case R.id.iv_email:
            case R.id.fl_right:
                //new AlertDialog.Builder(this).setMessage("功能开发中敬请期待").create().show();
                progressDialog.setMessage("加载中");
                progressDialog.show();
                photoListPre.getLossData(UserInfoModel.getInstance().getUser().getUserid(), progressDialog);
                break;
        }

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.lin_weixin:
                    new ShareAction(StudentScoreActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(StudentScoreActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_circle:
                    new ShareAction(StudentScoreActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withTitle("康宝莱体重管理挑战赛，坚持只为改变！")
                            .withText(lossModel.getContent())
                            .withTargetUrl(url)
                            .withMedia(new UMImage(StudentScoreActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                case R.id.lin_sina:
                    new ShareAction(StudentScoreActivity.this)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withText(lossModel.getContent() + url)
                            .withMedia(new UMImage(StudentScoreActivity.this, R.drawable.img_share_logo))
                            .share();
                    break;
                default:
                    break;
            }


        }

    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
