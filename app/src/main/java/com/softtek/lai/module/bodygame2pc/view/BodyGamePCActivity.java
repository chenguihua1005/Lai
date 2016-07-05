package com.softtek.lai.module.bodygame2pc.view;

import android.net.Uri;
import android.view.View;

import com.ggx.jerryguan.widget_lib.SimpleButton;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_bodygame)
public class BodyGamePCActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener {

    @InjectView(R.id.btn_bodygame)
    SimpleButton btn_bodygame;
    @InjectView(R.id.btn_chat)
    SimpleButton btn_chat;
    @InjectView(R.id.btn_contact)
    SimpleButton btn_contact;
    @InjectView(R.id.btn_fuce)
    SimpleButton btn_fuce;
    @InjectView(R.id.btn_class)
    SimpleButton btn_class;

    private BodyGamePCFragment bodyGamePCFragment;
    private ContactFragment contactFragment;
    private ChatFragment chatFragment;
    private FuCeFragment fuCeFragment;
    private ClassFragment classFragment;

    private int current=0;

    @Override
    protected void initViews() {

        btn_bodygame.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_contact.setOnClickListener(this);
        btn_fuce.setOnClickListener(this);
        btn_class.setOnClickListener(this);

        bodyGamePCFragment=new BodyGamePCFragment();
        contactFragment =new ContactFragment();
        chatFragment =new ChatFragment();
        fuCeFragment=new FuCeFragment();
        classFragment=new ClassFragment();
        //设置第一个fragment
        current=R.id.btn_bodygame;
        restoreState();
        btn_bodygame.setProgress(1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bodyGamePCFragment).commit();
    }

    @Override
    protected void initDatas() {

    }


  @Override
    public void onClick(View v) {
        restoreState();
        switch (v.getId()){
            case R.id.btn_bodygame:
                btn_bodygame.setProgress(1);
                if(current==R.id.btn_bodygame){
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bodyGamePCFragment).commit();
                break;
            case R.id.btn_chat:
                btn_chat.setProgress(1);
                if(current==R.id.btn_chat){
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, chatFragment).commit();
                break;
            case R.id.btn_contact:
                btn_contact.setProgress(1);
                if(current==R.id.btn_contact){
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactFragment).commit();
                break;
            case R.id.btn_fuce:
                btn_fuce.setProgress(1);
                if(current==R.id.btn_fuce){
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fuCeFragment).commit();
                break;
            case R.id.btn_class:
                btn_class.setProgress(1);
                if(current==R.id.btn_class){
                    return;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, classFragment).commit();
                break;
        }
        current=v.getId();
    }
    private void restoreState() {
        btn_bodygame.setProgress(0);
        btn_chat.setProgress(0);
        btn_contact.setProgress(0);
        btn_fuce.setProgress(0);
        btn_class.setProgress(0);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
