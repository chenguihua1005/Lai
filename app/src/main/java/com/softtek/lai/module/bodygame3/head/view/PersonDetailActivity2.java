package com.softtek.lai.module.bodygame3.head.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.graph.GraphActivity;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsTopFourModel;
import com.softtek.lai.module.bodygame3.head.presenter.PersonDetailPresenter;
import com.softtek.lai.module.bodygame3.home.view.ContactFragment;
import com.softtek.lai.module.bodygame3.more.view.FuceAlbumActivity;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.home.view.ModifyPersonActivity;
import com.softtek.lai.module.login.model.UserModel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.HorizontalListView;
import com.softtek.lai.widgets.PopUpWindow.ActionItem;
import com.softtek.lai.widgets.PopUpWindow.TitlePopup;
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
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * Created by jessica.zhang on 3/30/2017.
 */
@InjectLayout(R.layout.activity_person_detail)
public class PersonDetailActivity2 extends BaseActivity<PersonDetailPresenter> implements PersonDetailPresenter.PersonDetail, View.OnClickListener, TitlePopup.OnItemOnClickListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_email)
    ImageView iv_email;


    @InjectView(R.id.ll_weigh)
    LinearLayout ll_weigh;
    @InjectView(R.id.hlist_dy)
    HorizontalListView hlist_dy;
    @InjectView(R.id.re_hlist_dy)
    RelativeLayout re_hlist_dy;
    @InjectView(R.id.cir_userimg)
    CircleImageView cir_userimg;
    //用户名
    @InjectView(R.id.tv_stuname)
    TextView tv_stuname;
    @InjectView(R.id.tv_personlityName)
    TextView tv_personlityName;//个性签名
    @InjectView(R.id.tv_angle)
    TextView tv_angle;//爱心天使姓名
    @InjectView(R.id.tv_love)
    TextView tv_love;//爱心学员
    @InjectView(R.id.tv_Lossweight)
    TextView tv_Lossweight;//减重
    @InjectView(R.id.tv_initWeit)
    TextView tv_initWeit;//初始体重
    @InjectView(R.id.tv_chart)
    RelativeLayout tv_chart;

    @InjectView(R.id.className_tv)  //班级名称
            TextView className_tv;

    @InjectView(R.id.tv_currenweight)
    TextView tv_currenweight;
    @InjectView(R.id.im_InitImage)
    ImageView im_InitImage;//初始体重图片
    @InjectView(R.id.im_currenimWeight)
    ImageView im_currenimWeight;//现在体重图片
    //跳转点击
    @InjectView(R.id.tv_dynamic)
    TextView tv_dynamic;//动态
    @InjectView(R.id.ll_chart)
    LinearLayout ll_chart;//曲线图

    @InjectView(R.id.btn_chat)
    Button btn_chat;//发起聊天 或者临时聊天
    @InjectView(R.id.btn_addguy)
    Button btn_addguy;//加好友
    @InjectView(R.id.tv_no_dy)
    TextView tv_no_dy;
    @InjectView(R.id.ll_news)
    LinearLayout ll_news;

    @InjectView(R.id.im_guanzhu)
    CheckBox im_guanzhu;


    private List<NewsTopFourModel> newsTopFourModels = new ArrayList<>();
    EasyAdapter<NewsTopFourModel> easyAdapter;
    //定义标题栏弹窗按钮
    private TitlePopup titlePopup;

    private long AccountId;
    private String HXAccountId;//环信id
    private String UserName;
    private String AFriendId;//好友关系id
    private String ClassId;
    ArrayList<String> images = new ArrayList<>();
    private int issendFriend = 0;

    private static final int GET_Sian = 1;//发布签名
    private String IsFriend;//是否是好友

    private int comeFromClass = 1;//来自于哪一个班级   1  当前班级   0 往期


    int SetLove = 1;
    private MemberInfoModel memberInfoModel;

    private int PERSONDY = 3;

    private Long userid;


    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
        ll_chart.setOnClickListener(this);
        tv_chart.setOnClickListener(this);
        im_guanzhu.setOnClickListener(this);
        ll_weigh.setOnClickListener(this);


        ll_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memberInfoModel != null) {
                    Intent personal = new Intent(PersonDetailActivity2.this, PersionalActivity.class);
                    personal.putExtra("personalId", AccountId + "");
                    personal.putExtra("personalName", memberInfoModel.getUserName());
                    personal.putExtra("isFocus", Integer.parseInt("true".equals(memberInfoModel.getIsFocus()) ? "1" : "0"));
                    startActivityForResult(personal, PERSONDY);
                }
            }
        });
        tv_title.setText("个人详情");
        fl_right.setVisibility(View.INVISIBLE);

        //实例化标题栏弹窗
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        setPresenter(new PersonDetailPresenter(this));

        easyAdapter = new EasyAdapter<NewsTopFourModel>(this, newsTopFourModels, R.layout.activity_index_gallery_item) {
            @Override
            public void convert(ViewHolder holder, NewsTopFourModel data, int position) {
                ImageView img = holder.getView(R.id.img);
                if (!TextUtils.isEmpty(data.getThumbnailImgUrl())) {
                    Picasso.with(PersonDetailActivity2.this).load(AddressManager.get("photoHost") + data.getThumbnailImgUrl()).fit().centerCrop().into(img);
                } else if (!TextUtils.isEmpty(data.getImgUrl())) {
                    Picasso.with(PersonDetailActivity2.this).load(AddressManager.get("photoHost") + data.getImgUrl()).fit().centerCrop().into(img);

                }
            }
        };
        hlist_dy.setAdapter(easyAdapter);
        //暂且隐藏点击图片查看大图功能
        hlist_dy.setOnItemClickListener(this);


        userid = UserInfoModel.getInstance().getUserId();
        AccountId = getIntent().getLongExtra("AccountId", 0);
        ClassId = getIntent().getStringExtra("ClassId");
        //1: 好友  0 ： 不是好友
        int isFriend = getIntent().getIntExtra("isFriend", 0);
        HXAccountId = getIntent().getStringExtra("HXAccountId");
        UserName = getIntent().getStringExtra("UserName");
        AFriendId = getIntent().getStringExtra("AFriendId");//好友关系id

        comeFromClass = getIntent().getIntExtra("comeFrom", 1);//默认当前班级

        Log.i(TAG, "isFriend =" + isFriend + " AccountId=" + AccountId + " HXAccountId=" + HXAccountId + " UserName= " + UserName + " AFriendId= " + AFriendId + " ClassId = " + ClassId);

        iv_email.setImageResource(R.drawable.more_menu);

        btn_chat.setOnClickListener(this);
        btn_addguy.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        tv_personlityName.setOnClickListener(this);
        if (AccountId == UserInfoModel.getInstance().getUserId()) {
            cir_userimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PersonDetailActivity2.this, ModifyPersonActivity.class));
                }
            });
        }

        //获取个人信息
        doGetService(AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId, HXAccountId);


    }

    private void doGetService(long AccountId, String classid, String HXAccountId) {
        if (!TextUtils.isEmpty(HXAccountId)) {
            getPresenter().doGetClassMemberInfoByHx(HXAccountId, classid);
        } else {
            getPresenter().doGetClassMemberInfo(AccountId, classid);
        }
    }

    @Override
    public void getMemberInfo(MemberInfoModel memberInfoModel) {
        this.memberInfoModel = memberInfoModel;

        try {
            if (memberInfoModel != null) {
                String url = AddressManager.get("photoHost");
                //加载头像
                if (!TextUtils.isEmpty(memberInfoModel.getUserPhoto())) {
                    int px = DisplayUtil.dip2px(PersonDetailActivity2.this, 45);
                    Picasso.with(PersonDetailActivity2.this).load(AddressManager.get("photoHost") + memberInfoModel.getUserPhoto()).resize(px, px).centerCrop().placeholder(R.drawable.img_default)
                            .error(R.drawable.img_default).into(cir_userimg);
                } else {
                    Picasso.with(PersonDetailActivity2.this).load(R.drawable.img_default).placeholder(R.drawable.img_default).into(cir_userimg);
                }
                //用户名
                tv_stuname.setText(memberInfoModel.getUserName());
                //用户id环信id用户名
                AFriendId = memberInfoModel.getAFriendId();
                AccountId = memberInfoModel.getAccountid();
                HXAccountId = memberInfoModel.getHXAccountId();
                UserName = memberInfoModel.getUserName();
                if (UserInfoModel.getInstance().getUserId() == memberInfoModel.getAccountid()) {
                    if (!UserInfoModel.getInstance().getUser().getUserrole().equals("3")) {
                        tv_angle.setText((TextUtils.isEmpty(memberInfoModel.getMilkAngle()) ? "暂无奶昔天使" : "奶昔天使：" + memberInfoModel.getMilkAngle()));
                        tv_angle.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (!"1".equals(memberInfoModel.getClassRole())) {
                        tv_angle.setText((TextUtils.isEmpty(memberInfoModel.getMilkAngle()) ? "暂无奶昔天使" : "奶昔天使：" + memberInfoModel.getMilkAngle()));
                        tv_angle.setVisibility(View.VISIBLE);
                    }
                }

                if (!"4".equals(memberInfoModel.getClassRole())) {
                    fl_right.setVisibility(View.INVISIBLE);
                } else {
                    tv_love.setVisibility(View.VISIBLE);
                    tv_love.setText((TextUtils.isEmpty(memberInfoModel.getIntroducer()) ? "暂无爱心学员" : "爱心学员：" + memberInfoModel.getIntroducer()));
                    if (AccountId == userid) {
                        if (TextUtils.isEmpty(memberInfoModel.getIntroducer())) {
                            titlePopup.addAction(new ActionItem(PersonDetailActivity2.this, "修改爱心学员", R.drawable.modifylove));
                            fl_right.setVisibility(View.VISIBLE);

                        }
                    }
                    ll_weigh.setVisibility(View.VISIBLE);
                    if (Float.parseFloat(memberInfoModel.getTotalLossWeight()) < 0) {
                        String lossweight[] = memberInfoModel.getTotalLossWeight().split("-");
                        tv_Lossweight.setText("增重  " + lossweight[1] + "斤");//减重d
                    } else {
                        tv_Lossweight.setText("减重  " + memberInfoModel.getTotalLossWeight() + "斤");//减重
                    }
                    tv_initWeit.setText("0".equals(memberInfoModel.getInitWeight()) ? "暂无数据" : "初始体重 " + memberInfoModel.getInitWeight() + "斤");//初始体重
                    tv_currenweight.setText("0".equals(memberInfoModel.getCurrentWeight()) ? "尚未复测" : "当前体重 " + memberInfoModel.getCurrentWeight() + "斤");//现在体重

                    if (!TextUtils.isEmpty(memberInfoModel.getInitThImg()))//初始体重图片
                    {
                        Picasso.with(PersonDetailActivity2.this).load(url + memberInfoModel.getInitThImg()).fit().into(im_InitImage);
                    }
                    if (!TextUtils.isEmpty(memberInfoModel.getCurttentThImg())) {   //现在体重图片
                        Picasso.with(PersonDetailActivity2.this).load(url + memberInfoModel.getCurttentThImg()).fit().into(im_currenimWeight);
                    }
                }

                className_tv.setText(memberInfoModel.getClassName());

                if (AccountId == userid)//如果是本人，显示查看曲线图,如果没有爱心天使可修改爱心天使
                {   //是本人可编辑个性签名

                    ClassId = memberInfoModel.getClassId();
                    tv_personlityName.setEnabled(true);
                    //个性签名已存在现实个性签名内容并隐藏图标
                    if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                        tv_personlityName.setText(memberInfoModel.getPersonalityName());
                        tv_personlityName.setCompoundDrawables(null, null, null, null);
                        tv_personlityName.setVisibility(View.VISIBLE);
                    } else {
                        tv_personlityName.setVisibility(View.VISIBLE);//显示编辑签名
                    }
                    if ("4".equals(memberInfoModel.getClassRole())) {
                        Log.i(TAG, "本人且 身份是学员......");
                        ll_chart.setVisibility(View.VISIBLE);
                    }
                } else {
                    tv_personlityName.setVisibility(View.VISIBLE);
                    im_guanzhu.setVisibility(View.VISIBLE);
                    //个性签名
                    tv_personlityName.setCompoundDrawables(null, null, null, null);//去除个性签名文本图标
                    if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                        tv_personlityName.setText(memberInfoModel.getPersonalityName());
                    } else {
                        tv_personlityName.setText("暂无个性签名");
                    }
                    if ((memberInfoModel.getIntroducerId()).equals(userid + ""))//如果是登陆id是该学员的爱心学员，显示查看曲线图
                    {
                        ll_chart.setVisibility(View.VISIBLE);
                    }

                    issendFriend = memberInfoModel.getIsSendFriend();
                    IsFriend = memberInfoModel.getIsFriend();

                    //如果是好友的话，并且对方是学员时候，可查看当前班级 曲线图放开
                    if ("4".equals(memberInfoModel.getClassRole())) {
                        ClassId = memberInfoModel.getClassId();
                        if (!TextUtils.isEmpty(memberInfoModel.getClassId())) {
                            ll_chart.setVisibility(View.VISIBLE);
                        }

                        if (Constants.FROM_CONTACT == comeFromClass) {
                            if (memberInfoModel.getIsCurrClass() == 1) {
                                ll_chart.setVisibility(View.VISIBLE);
                                ClassId = memberInfoModel.getClassId();
                            } else {
                                ll_chart.setVisibility(View.GONE);
                            }
                        }
                    }


                    if ("1".equals(IsFriend))//如果是好友，显示发起聊天
                    {
//                        btn_chat.setVisibility(View.VISIBLE);
//                        titlePopup.addAction(new ActionItem(PersonDetailActivity2.this, "删除好友", R.drawable.deletefriend));
//                        fl_right.setVisibility(View.VISIBLE);


                    } else {//不是好友，可发起临时会话，显示添加好友
                        if (issendFriend > 0) {//如果大于0，则为已发送过该好友请求
//                            btn_chat.setVisibility(View.VISIBLE);
//                            btn_chat.setText("发起临时会话");
//                            btn_addguy.setVisibility(View.VISIBLE);//添加好友
//                            btn_addguy.setText("待确认");
//                            btn_addguy.setTextColor(ContextCompat.getColor(PersonDetailActivity2.this, R.color.white));
//                            btn_addguy.setBackground(ContextCompat.getDrawable(PersonDetailActivity2.this, R.drawable.bg_assistant_refuse));
                            iv_email.setVisibility(View.INVISIBLE);
                        } else {
//                            btn_chat.setVisibility(View.VISIBLE);
//                            btn_chat.setText("发起临时会话");
//                            btn_addguy.setVisibility(View.VISIBLE);//添加好友
                            iv_email.setVisibility(View.INVISIBLE);

                        }

                        //如果不是好友的话，曲线图隐藏
//                        ll_chart.setVisibility(View.GONE);

                    }
                    if ("false".equals(memberInfoModel.getIsFocus()))//没有关注
                    {
                        im_guanzhu.setChecked(false);
                    } else {
                        im_guanzhu.setChecked(true);

                    }
                }


                doGetPhotoView();//展示图片
            }


            btn_chat.setVisibility(View.GONE);
            btn_addguy.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void doGetPhotoView() {
        newsTopFourModels.clear();
        newsTopFourModels.addAll(memberInfoModel.getNewsTopFour());
        if (newsTopFourModels.size() != 0) {
            re_hlist_dy.setVisibility(View.VISIBLE);
            tv_no_dy.setVisibility(View.GONE);
            images.clear();
            for (int n = 0; n < newsTopFourModels.size(); n++) {
                images.add(n, newsTopFourModels.get(n).getImgUrl());
            }
            easyAdapter.notifyDataSetChanged();
        } else {
            re_hlist_dy.setVisibility(View.GONE);
            tv_no_dy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personlityName:
                Intent intent1 = new Intent(this, EditSignaActivity.class);
                if (TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                    intent1.putExtra("sina", "");
                } else {
                    intent1.putExtra("sina", tv_personlityName.getText().toString());
                }
                startActivityForResult(intent1, GET_Sian);
                break;
            case R.id.tv_chart:
                if (memberInfoModel == null) {
                    return;
                }
                //查看曲线图
                Intent graph = new Intent(this, GraphActivity.class);
                graph.putExtra("accountId", AccountId);
                graph.putExtra("classId", ClassId);
                //只有班级管理和自己能看到
                int role = memberInfoModel.getLoginUserClassRole();
                boolean isShow = role == 1 || role == 2 || role == 3 || UserInfoModel.getInstance().getUserId() == AccountId
                        || String.valueOf(UserInfoModel.getInstance().getUserId()).equals(memberInfoModel.getMilkAngleId());
                graph.putExtra("isShow", isShow);
                startActivity(graph);
                break;
            case R.id.btn_chat:
                final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
                if (!hxid.equals(HXAccountId)) {
                    Intent intent = new Intent(PersonDetailActivity2.this, ChatActivity.class);
                    intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                    intent.putExtra("userId", HXAccountId);
                    intent.putExtra("name", UserName);
                    startActivity(intent);

                } else {
                    Util.toastMsg("不能给自己发消息！");
                }
                break;
//            case R.id.btn_addguy://添加好友
//                if ("0".equals(IsFriend)) {//0:不是好友
//                    if (issendFriend > 0) {//未发送过好友申请
//                        Util.toastMsg("您已发送过好友申请，请等待确认");
//                        return;
//                    } else {
//                        //参数为要添加的好友的username和添加理由
//                        sentFriendApply();
//                    }
//                }
//
//                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.fl_right:
                titlePopup.show(view);
                break;
            case R.id.ll_weigh:
                Intent intent = new Intent(PersonDetailActivity2.this, FuceAlbumActivity.class);
                intent.putExtra("account", String.valueOf(memberInfoModel.getAccountid()));
                startActivity(intent);
                break;
            case R.id.im_guanzhu: {
                if (im_guanzhu.isChecked()) {
                    getPresenter().doFocusAccount(AccountId);
                } else {
                    Log.i("取消关注");
                    getPresenter().doCancleFocusAccount(AccountId);
                }
            }
            break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountId == UserInfoModel.getInstance().getUserId()) {
            String path = AddressManager.get("photoHost");
            UserModel model = UserInfoModel.getInstance().getUser();
            if (!TextUtils.isEmpty(model.getPhoto())) {
                Picasso.with(this).load(path + model.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(cir_userimg);
            }
            if (StringUtils.isEmpty(model.getNickname())) {
                tv_stuname.setText(model.getMobile());
            } else {
                tv_stuname.setText(model.getNickname());
            }

        }
    }

    //点击关注回调
    @Override
    public void doFocusAccount(int flag) {
        if (1 == flag) {
            memberInfoModel.setIsFocus("true");
        } else {
            im_guanzhu.setChecked(true);
        }
    }

    @Override
    public void doCancleFocusAccount(int flag) {
        if (1 == flag) {
            memberInfoModel.setIsFocus("false");
        } else {
            im_guanzhu.setChecked(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent personal = new Intent(PersonDetailActivity2.this, PersionalActivity.class);
        personal.putExtra("personalId", AccountId + "");
        personal.putExtra("personalName", memberInfoModel.getUserName());
        personal.putExtra("isFocus", Integer.parseInt("true".equals(memberInfoModel.getIsFocus()) ? "1" : "0"));
        startActivityForResult(personal, PERSONDY);

    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        if ("删除好友".equals(item.mTitle)) {
            removeFriend();
            btn_addguy.setText("添加好友");
            btn_addguy.setVisibility(View.VISIBLE);
        }
        if ("修改爱心学员".equals(item.mTitle)) {
            Intent intent = new Intent(this, SetLoveStuActivity.class);
            intent.putExtra("AccounId", AccountId);
            intent.putExtra("ClassId", ClassId);
            startActivityForResult(intent, SetLove);
        }

    }


    //加好友请求
    private void sentFriendApply() {
        String stri = getResources().getString(R.string.Is_sending_a_request);
        dialogShow(stri);
        Log.i(TAG, "好友 getUserId = " + UserInfoModel.getInstance().getUserId() + " getAccountId=  " + AccountId + " ClassId = " + ClassId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(HXAccountId, s);

                    ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                    service.sentFriendApply(UserInfoModel.getInstance().getToken(), TextUtils.isEmpty(ClassId) ? " " : ClassId, UserInfoModel.getInstance().getUserId(), AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId, new Callback<ResponseData>() {
                        @Override
                        public void success(final ResponseData responseData, Response response) {
                            int status = responseData.getStatus();

                            if (200 == status) {
                                issendFriend = 1;
                                btn_chat.setVisibility(View.VISIBLE);
                                btn_chat.setText("发起临时会话");
                                btn_addguy.setVisibility(View.VISIBLE);//添加好友
                                btn_addguy.setText("待确认");
                                btn_addguy.setTextColor(ContextCompat.getColor(PersonDetailActivity2.this, R.color.white));
                                btn_addguy.setBackground(ContextCompat.getDrawable(PersonDetailActivity2.this, R.drawable.bg_assistant_refuse));
                                iv_email.setVisibility(View.INVISIBLE);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogDissmiss();
                                        Util.toastMsg(getResources().getString(R.string.send_successful));
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        dialogDissmiss();
                                        Toast.makeText(getApplicationContext(), responseData.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void failure(final RetrofitError error) {
                            ZillaApi.dealNetError(error);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    dialogDissmiss();
                                }
                            });
                        }
                    });

                } catch (final HyphenateException e) {//环信加好友成功与否并不影响这边逻辑
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialogDissmiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }).start();


    }

    private void removeFriend() {
        Log.i(TAG, "本人 getUserId = " + UserInfoModel.getInstance().getUserId() + " 好友 getAccountId=  " + AccountId + " AFriendId =" + AFriendId);

        String st1 = getResources().getString(R.string.deleting);
        final String st2 = getResources().getString(R.string.Delete_failed);
        final ProgressDialog pd = new ProgressDialog(PersonDetailActivity2.this);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(HXAccountId);

                    ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                    service.removeFriend(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AFriendId, new Callback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                            int status = responseData.getStatus();
                            if (200 == status) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Util.toastMsg("删除好友成功");
                                        //发通知更新好友列表
                                        Intent intent = new Intent(ContactFragment.UPDATE_CONTACT_MSG);
                                        sendBroadcast(intent);


                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                        Util.toastMsg("删除好友失败！");
                                    }
                                });

                            }
                        }

                        @Override
                        public void failure(final RetrofitError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    Log.i(TAG, "error222222 =" + error.toString());
                                    Util.toastMsg(st2 + error.getMessage());
                                }
                            });

                            ZillaApi.dealNetError(error);
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Util.toastMsg(st2 + e.getMessage());
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SetLove && resultCode == RESULT_OK) {
            doGetService(AccountId, ClassId, HXAccountId);
            titlePopup.cleanAction();
            fl_right.setVisibility(View.INVISIBLE);
        }
        if (requestCode == GET_Sian && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("sina"))) {
                tv_personlityName.setText(data.getStringExtra("sina"));
                tv_personlityName.setCompoundDrawables(null, null, null, null);
            } else {
                tv_personlityName.setText("编辑个性签名");
                Drawable drawable = getResources().getDrawable(R.drawable.lable);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                tv_personlityName.setCompoundDrawables(drawable, null, null, null);//画在右边
            }

        }
        if (requestCode == PERSONDY && resultCode == RESULT_OK) {
            int isFocus = data.getIntExtra("isFocus", 0);
            if (isFocus == 1) {
                im_guanzhu.setChecked(true);
                memberInfoModel.setIsFocus("true");
            } else {
                im_guanzhu.setChecked(false);
                memberInfoModel.setIsFocus("false");
            }
        }
    }
}
