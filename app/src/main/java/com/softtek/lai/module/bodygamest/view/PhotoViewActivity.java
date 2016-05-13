package com.softtek.lai.module.bodygamest.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Rule;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.module.login.presenter.ILoginPresenter;
import com.softtek.lai.module.login.presenter.LoginPresenterImpl;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.SoftInputUtil;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ImageFileCropSelector;

import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;
import zilla.libcore.file.AddressManager;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_photo_view)
public class PhotoViewActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.retestwritest_image)
    PhotoView retestwritest_image;

    private String photo;




    @Override
    protected void initViews() {
        tv_title.setText("复测录入");
        ll_left.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        Intent intent=getIntent();
        photo=intent.getStringExtra("img");
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        Picasso.with(this).load(path + photo)
                .resize(DisplayUtil.getMobileWidth(this), DisplayUtil.getMobileHeight(this)).centerInside()
                .placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(retestwritest_image);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;


        }
    }

    /**
     * 点击屏幕隐藏软键盘
     **/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftInputUtil.isShouldHideKeyboard(v, ev)) {

                SoftInputUtil.hideKeyboard(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
