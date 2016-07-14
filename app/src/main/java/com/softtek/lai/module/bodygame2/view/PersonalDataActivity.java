package com.softtek.lai.module.bodygame2.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChat;
import com.softtek.lai.R;
import com.softtek.lai.chat.Constant;
import com.softtek.lai.chat.model.ChatContactInfoModel;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame2.model.ClmInfoModel;
import com.softtek.lai.module.bodygame2.model.memberDetialModel;
import com.softtek.lai.module.bodygame2.net.BodyGameService;
import com.softtek.lai.module.bodygame2.present.ClemeberExitManager;
import com.softtek.lai.module.bodygame2.present.PersonDateManager;
import com.softtek.lai.module.bodygamest.view.FuceStActivity;
import com.softtek.lai.module.bodygamest.view.StudentHonorGridActivity;
import com.softtek.lai.module.bodygamest.view.UploadPhotoActivity;
import com.softtek.lai.module.health.view.DateForm;
import com.softtek.lai.module.pastreview.view.PassPhotoActivity;
import com.softtek.lai.module.retest.AuditActivity;
import com.softtek.lai.module.retest.WriteActivity;
import com.softtek.lai.module.retest.eventModel.RetestAuditModelEvent;
import com.softtek.lai.module.retest.present.RetestPre;
import com.softtek.lai.module.retest.present.RetestclassImp;
import com.softtek.lai.module.studetail.adapter.StudentDetailFragmentAdapter;
import com.softtek.lai.module.studetail.view.DimensionChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightChartFragment;
import com.softtek.lai.module.studetail.view.LossWeightLogActivity;
import com.softtek.lai.utils.ChMonth;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_personal_data)
public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, BaseFragment.OnFragmentInteractionListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.tabcontent)
    ViewPager tabcontent;
    @InjectView(R.id.tv_totle_lw)
    TextView tv_totle_lw;
    @InjectView(R.id.tv_loss_before)
    TextView tv_loss_before;
    @InjectView(R.id.tv_loss_after)
    TextView tv_loss_after;
    @InjectView(R.id.iv_loss_before)
    ImageView iv_loss_before;
    @InjectView(R.id.iv_loss_after)
    ImageView iv_loss_after;
    @InjectView(R.id.tv_loss_after_tip)
    TextView tv_loss_after_tip;
    @InjectView(R.id.tv_total_loss_tip)
    TextView tv_total_loss_tip;
    @InjectView(R.id.ll_persondatefuce)
    LinearLayout ll_persondatefuce;

    @InjectView(R.id.lin_send_message)
    LinearLayout lin_send_message;
    //奖章一
    @InjectView(R.id.ll_honorn1)
    LinearLayout ll_honorn1;
    //奖章二
    @InjectView(R.id.ll_honorn2)
    LinearLayout ll_honorn2;
    //奖章三
    @InjectView(R.id.ll_honorn3)
    LinearLayout ll_honorn3;
    @InjectView(R.id.cir_headim)
    CircleImageView cir_headim;
    @InjectView(R.id.tv_username)
    TextView tv_username;
    @InjectView(R.id.tv_tel)
    TextView tv_tel;
    @InjectView(R.id.tv_personclassname)
    TextView tv_personclassname;
    @InjectView(R.id.tv_SuperName)
    TextView tv_SuperName;
    @InjectView(R.id.tv_classdate)
    TextView tv_classdate;
    @InjectView(R.id.tv_weightday)
    TextView tv_weightday;
    @InjectView(R.id.tv_mon)
    TextView tv_mon;
    @InjectView(R.id.tv_storycontent)
    TextView tv_storycontent;
    @InjectView(R.id.im_pict1)
    ImageView im_pict1;
    @InjectView(R.id.im_pict2)
    ImageView im_pict2;
    @InjectView(R.id.im_pict3)
    ImageView im_pict3;
    @InjectView(R.id.im_pict4)
    ImageView im_pict4;
    @InjectView(R.id.im_pict5)
    ImageView im_pict5;
    @InjectView(R.id.im_pict6)
    ImageView im_pict6;
    @InjectView(R.id.tv_valuetext)
    TextView tv_valuetext;
    @InjectView(R.id.tv_valuetext2)
    TextView tv_valuetext2;
    @InjectView(R.id.tv_valuetext3)
    TextView tv_valuetext3;
    @InjectView(R.id.ll_remove_class)
    LinearLayout ll_remove_class;
    @InjectView(R.id.re_xunzhang)
    RelativeLayout re_xunzhang;
    @InjectView(R.id.re_jianzh)
    RelativeLayout re_jianzh;
    @InjectView(R.id.Re_personphoto)
    RelativeLayout Re_personphoto;
    private long userId = 0;
    private long classId = 0;
    private String review_flag = "1";
    ChMonth chMonth;
    String typedate="";
    String AMStatus="";
    RetestPre retestPre;
    private List<Fragment> fragmentList = new ArrayList<>();
    PersonDateManager persondatemanager;
    ClemeberExitManager clemberExitmanager;
    BodyGameService service;
    private static final int GET_BODY = 2;

    ClmInfoModel clmInfoModel;
    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_remove_class.setOnClickListener(this);
        re_xunzhang.setOnClickListener(this);
        re_jianzh.setOnClickListener(this);
        Re_personphoto.setOnClickListener(this);
        ll_persondatefuce.setOnClickListener(this);
        lin_send_message.setOnClickListener(this);
        tv_title.setText("个人资料");
        userId = getIntent().getLongExtra("userId", 0);
        classId = getIntent().getLongExtra("classId", 0);
        review_flag = getIntent().getStringExtra("review");
        review_flag = review_flag == null ? "1" : review_flag;
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        params.put("classId", classId + "");
        LossWeightChartFragment lwcf = LossWeightChartFragment.newInstance(params);
        DimensionChartFragment dcf = DimensionChartFragment.newInstance(params);
        fragmentList.add(lwcf);
        fragmentList.add(dcf);
        tabcontent.setAdapter(new StudentDetailFragmentAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(tabcontent);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initDatas() {
        persondatemanager = new PersonDateManager();
        persondatemanager.doGetClmemberDetial(this, userId + "", classId + "");
    }


    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_send_message:
                boolean isLogin = EMChat.getInstance().isLoggedIn();
                System.out.println("isLogin:" + isLogin);
                if (isLogin) {
                    String HX_ID=clmInfoModel.getHXAccountId();
                    if(TextUtils.isEmpty(HX_ID) || HX_ID==null ||"null".equals(HX_ID)){
                        Util.toastMsg("会话异常，请稍候");
                    }else {
                        Intent intent = new Intent(PersonalDataActivity.this, ChatActivity.class);
                        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
                        intent.putExtra(Constant.EXTRA_USER_ID, clmInfoModel.getHXAccountId().toLowerCase());
                        intent.putExtra("name", clmInfoModel.getUserName());
                        intent.putExtra("photo", path + clmInfoModel.getPhoto());
                        startActivity(intent);
                    }
                }else {
                    Util.toastMsg("会话异常，请稍候再试");
                }
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.ll:
                Intent intent = new Intent(this, LossWeightLogActivity.class);
                intent.putExtra("accountId", userId);
                intent.putExtra("review", Integer.parseInt(review_flag));
                startActivity(intent);
                break;
            case R.id.ll_remove_class:
                clemberExitmanager = new ClemeberExitManager();
                clemberExitmanager.doClmemberExit(this, userId + "", classId + "");
                break;
            case R.id.re_xunzhang:
//                startActivity(new Intent(this, StudentHonorGridActivity.class));
                break;
            case R.id.re_jianzh:
                Intent intent1 = new Intent(this, LossWeightLogActivity.class);
                intent1.putExtra("accountId", userId);
                intent1.putExtra("review", Integer.parseInt(review_flag));
                startActivity(intent1);
                break;
            case R.id.Re_personphoto:
                Intent intent2 = new Intent(this, PassPhotoActivity.class);
                intent2.putExtra("userId", userId);
                intent2.putExtra("classId", classId);
                startActivity(intent2);
                break;
            case R.id.ll_persondatefuce:
                if(AMStatus.equals("-1"))
                {
                    Intent fucewrite=new Intent(this, WriteActivity.class);
                    fucewrite.putExtra("accountId",userId+"");
                    fucewrite.putExtra("classId",classId+"");
                    startActivityForResult(fucewrite,GET_BODY);
                }
                else if (AMStatus.equals("0")){
                    Intent fucewrite=new Intent(this, AuditActivity.class);
                    fucewrite.putExtra("accountId",userId+"");
                    fucewrite.putExtra("classId",classId+"");
                    String[] date=typedate.split(" ");
                    fucewrite.putExtra("typeDate",date[0]);
                    startActivityForResult(fucewrite,GET_BODY);
                }
                else if (AMStatus.equals("1")){
                    Intent fucewrite=new Intent(this, FuceStActivity.class);
                    fucewrite.putExtra("accountId",userId+"");
                    startActivity(fucewrite);
                }
                break;
        }
    }

    public void onloadCompleted(memberDetialModel data) {
        if (data != null) {
            AMStatus=data.getClmInfo().getIstest();
            clmInfoModel=data.getClmInfo();
            typedate=data.getClmInfo().getTypedate();
            String path = AddressManager.get("photoHost");
            if (!TextUtils.isEmpty(data.getClmInfo().getPhoto())) {
                Picasso.with(this).load(path + data.getClmInfo().getPhoto()).fit().error(R.drawable.img_default).into(cir_headim);
            }
            tv_username.setText(data.getClmInfo().getUserName());
            tv_tel.setText(data.getClmInfo().getMobile());
            tv_personclassname.setText("班级：" + data.getClmInfo().getClassName());
            if (data.getClmInfo().getSuperType().equals("2")) {
                tv_SuperName.setText("助教：" + data.getClmInfo().getSuperName());
            }else if (data.getClmInfo().getSuperType().equals("2"))
            {
                tv_SuperName.setText("guwen：" + data.getClmInfo().getSuperName());
            }
            String[] star = data.getClmInfo().getStartDate().split(" ");
            String[] stardate = star[0].split("-");
            String[] end = data.getClmInfo().getEndDate().split(" ");
            String[] enddate = end[0].split("-");
            tv_classdate.setText("（" + stardate[0] + "." + stardate[1] + "." + stardate[2] + "-" + enddate[0] + "." + enddate[1] + "." + enddate[2] + "）");
            if (data.getLossStory() == null) {

            } else {
                if (!TextUtils.isEmpty(data.getLossStory().getCreateDate())) {
                    String[] day = data.getLossStory().getCreateDate().split(" ");
                    String[] date = day[0].split("-");
                    tv_weightday.setText(date[2]);
                    chMonth = new ChMonth();
                    tv_mon.setText(chMonth.tomonth(date[1]));
                    tv_storycontent.setText(data.getLossStory().getLogContent());
                }
            }
            for (int i = 0; i < data.getHonorList().size(); i++) {
                getview(data.getHonorList().get(i).getHonorType(), i, data.getHonorList().get(i).getValue());

            }
            for (int j = 0; j < data.getPhotoList().size(); j++) {
                if (!TextUtils.isEmpty(data.getPhotoList().get(j).getImgUrl())) {
                    if (j == 0) {
                        im_pict1.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict1);
                    } else if (j == 1) {
                        im_pict2.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict2);
                    } else if (j == 2) {
                        im_pict3.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict3);
                    } else if (j == 3) {
                        im_pict4.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict4);
                    } else if (j == 4) {
                        im_pict5.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict5);
                    } else if (j == 5) {
                        im_pict6.setVisibility(View.VISIBLE);
                        Picasso.with(this).load(path + data.getPhotoList().get(j).getImgUrl()).fit().error(R.drawable.default_icon_square).into(im_pict6);
                    }

                }
            }
            if (StringUtils.isNotEmpty(data.getClmInfo().getTotalLoss()) && Float.parseFloat(data.getClmInfo().getTotalLoss()) < 0) {
                tv_total_loss_tip.setText("共增重");
            } else {
                tv_total_loss_tip.setText("共减重");
            }
            tv_totle_lw.setText(Float.parseFloat(StringUtils.isEmpty(data.getClmInfo().getAfterWeight()) ? "0" : data.getClmInfo().getAfterWeight()) == 0 ? "0斤" : Math.abs(Float.parseFloat(data.getClmInfo().getTotalLoss())) + "斤");
            tv_loss_before.setText(StringUtil.getFloatValue(data.getClmInfo().getBeforeWeight()) + "斤");
            float lossAfter = StringUtil.getFloat(data.getClmInfo().getAfterWeight());
            tv_loss_after.setText(lossAfter == 0 ? "尚未复测" : StringUtil.getFloat(data.getClmInfo().getAfterWeight()) + "斤");
            if (lossAfter == 0) {
                tv_loss_after_tip.setVisibility(View.GONE);
            } else {
                tv_loss_after_tip.setVisibility(View.VISIBLE);
            }

            if (!StringUtils.isEmpty(data.getClmInfo().getBeforeImage())) {
                Picasso.with(this).load(path + data.getClmInfo().getBeforeImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_before);
            }
            if (!StringUtils.isEmpty(data.getClmInfo().getAfterImage())) {
                Picasso.with(this).load(path + data.getClmInfo().getAfterImage()).fit().placeholder(R.drawable.default_icon_rect).error(R.drawable.default_icon_rect).into(iv_loss_after);
            }

        }
    }

    public void getview(String type, int num, String value) {
        if (type.equals("0")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_jz_item, getview1(num, type, value));
            TextView tv_jz_value = (TextView) findViewById(R.id.tv_jz_value);
            tv_jz_value.setText(value);

        } else if (type.equals("1")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_fc_item, getview1(num, type, value));
            ImageView img_fuce = (ImageView) findViewById(R.id.img_fuce);
            if (value.equals("1")) {
                img_fuce.setImageResource(R.drawable.img_student_honor_tong
                );
            } else if (value.equals("2")) {
                img_fuce.setImageResource(R.drawable.img_student_honor_yin);

            } else {
                img_fuce.setImageResource(R.drawable.img_student_honor_jin);

            }
        } else if (type.equals("2")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_ygj_item, getview1(num, type, value));
            TextView tv_yuegj_value = (TextView) findViewById(R.id.tv_yuegj_value);
            tv_yuegj_value.setText(value);

        } else if (type.equals("3")) {
            LayoutInflater.from(this).inflate(R.layout.person_honor_star_item, getview1(num, type, value));
            TextView tv_jzstar_value = (TextView) findViewById(R.id.tv_jzstar_value);
            tv_jzstar_value.setText(value);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_BODY && resultCode == RESULT_OK) {
            Log.i("adsasad","wo jinlai le a dsa ds ad a");
            AMStatus="1";
        }
    }

    public LinearLayout getview1(int n, String ty, String value) {
        LinearLayout view = ll_honorn1;
        if (n == 0) {
            view = ll_honorn1;
            if (ty.equals("1")) {
                if (value.equals("1")) {
                    tv_valuetext.setText("复测铜牌");
                } else if (value.equals("2")) {
                    tv_valuetext.setText("复测银牌");
                } else if (value.equals("3")) {
                    tv_valuetext.setText("复测金牌");
                }
            } else if (ty.equals("0")) {
                tv_valuetext.setText("减重" + value + "斤奖章");
            } else if (ty.equals("2")) {
                tv_valuetext.setText("月冠军" + value + "名奖章");
            } else if (ty.equals("3")) {
                tv_valuetext.setText("全国排名奖章");
            }
        } else if (n == 1) {
            view = ll_honorn2;
            if (ty.equals("1")) {
                if (value.equals("1")) {
                    tv_valuetext2.setText("复测铜牌");
                } else if (value.equals("2")) {
                    tv_valuetext2.setText("复测银牌");
                } else if (value.equals("3")) {
                    tv_valuetext2.setText("复测金牌");
                }
            } else if (ty.equals("0")) {
                tv_valuetext2.setText("减重" + value + "斤奖章");
            } else if (ty.equals("2")) {
                tv_valuetext2.setText("月冠军" + value + "名奖章");
            } else if (ty.equals("3")) {
                tv_valuetext2.setText("全国排名奖章");
            }
        } else {
            view = ll_honorn3;
            if (ty.equals("1")) {
                if (value.equals("1")) {
                    tv_valuetext3.setText("复测铜牌");
                } else if (value.equals("2")) {
                    tv_valuetext3.setText("复测银牌");
                } else if (value.equals("3")) {
                    tv_valuetext3.setText("复测金牌");
                }
            } else if (ty.equals("0")) {
                tv_valuetext3.setText("减重" + value + "斤奖章");
            } else if (ty.equals("2")) {
                tv_valuetext3.setText("月冠军" + value + "名奖章");
            } else if (ty.equals("3")) {
                tv_valuetext3.setText("全国排名奖章");
            }
        }
        return view;
    }

    public void onExitompleted(String aTrue) {
        if (aTrue.equals("true")) {
            Util.toastMsg("移出班级成功");
            finish();
        } else {
            Util.toastMsg("移出班级失败");
        }
    }
}
