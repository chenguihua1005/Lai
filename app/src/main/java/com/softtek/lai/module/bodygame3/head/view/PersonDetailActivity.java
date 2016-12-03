package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.softtek.lai.R;
import com.softtek.lai.chat.ui.ChatActivity;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsTopFourModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

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

@InjectLayout(R.layout.activity_person_detail)
public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "PersonDetailActivity";
    private int[] mImgIds;
    private LayoutInflater mInflater;
    @InjectView(R.id.gallery)
    LinearLayout gallery;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;


    HeadService headService;
    NewsTopFourModel newsTopFourModel;
    Long userid, accountid;
    String classid;
    MemberInfoModel memberInfoModel;
    @InjectView(R.id.cir_userimg)//用户id
            CircleImageView cir_userimg;
    @InjectView(R.id.tv_stuname)//用户名
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
    @InjectView(R.id.tv_currenweight)
    TextView tv_currenweight;
    @InjectView(R.id.im_InitImage)
    ImageView im_InitImage;//初始体重图片
    @InjectView(R.id.im_currenimWeight)
    ImageView im_currenimWeight;//现在体重图片
    //跳转点击
    @InjectView(R.id.tv_dynamic)
    TextView tv_dynamic;//动态
    @InjectView(R.id.tv_chart)
    TextView tv_chart;//曲线图

    @InjectView(R.id.btn_chat)
    Button btn_chat;//发起聊天 或者临时聊天
    @InjectView(R.id.btn_addguy)
    Button btn_addguy;//加好友

    @InjectView(R.id.im_guanzhu)
    ImageView im_guanzhu;
    private List<NewsTopFourModel> newsTopFourModels = new ArrayList<NewsTopFourModel>();

//    ClassMemberModel classMemberModel;

    private int isFriend = 0;//1: 好友  0 ： 不是好友
    private long AccountId;
    private String HXAccountId;
    private String UserName;
    private String AFriendId;//好友关系id



    @Override
    protected void initViews() {
        tv_dynamic.setOnClickListener(this);
        try {
            if (memberInfoModel != null) {
                if (userid == accountid || userid == Long.parseLong(memberInfoModel.getMilkAngleId())) {
                    tv_chart.setVisibility(View.VISIBLE);
                }
                if (userid == accountid) {
                    btn_chat.setVisibility(View.GONE);
                    btn_addguy.setVisibility(View.GONE);
                    im_guanzhu.setVisibility(View.GONE);
                } else if ("1".equals(memberInfoModel.getIsFriend())) {
                    //是好友
                    btn_addguy.setVisibility(View.GONE);
                } else {
                    //不是好友
                    btn_chat.setText("发起临时会话");
                }
                if ("false".equals(memberInfoModel.getIsFocus())) {
                    im_guanzhu.setBackground(getResources().getDrawable(R.drawable.add_yiguanzhu));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initDatas() {
        mInflater = LayoutInflater.from(this);
        accountid = Long.parseLong(getIntent().getIntExtra("student_id", 0) + "");
        userid = UserInfoModel.getInstance().getUserId();
        classid = getIntent().getStringExtra("classId_first");

//        classMemberModel = (ClassMemberModel) getIntent().getSerializableExtra("classMemberModel");

        isFriend = getIntent().getIntExtra("isFriend", 0);
        AccountId = getIntent().getLongExtra("AccountId", 0);
        HXAccountId = getIntent().getStringExtra("HXAccountId");
        UserName = getIntent().getStringExtra("UserName");
        AFriendId = getIntent().getStringExtra("AFriendId");//好友关系id

        Log.i(TAG, "isFriend =" + isFriend + " AccountId=" + AccountId + " HXAccountId=" + HXAccountId + " UserName= " + UserName + " AFriendId= " + AFriendId);
        if (String.valueOf(UserInfoModel.getInstance().getUserId()).equals(AccountId)) {//是本人的话，隐藏聊天和加好友按钮
            btn_chat.setVisibility(View.GONE);
            btn_addguy.setVisibility(View.GONE);
        }

        if (1 == isFriend) {
            btn_addguy.setVisibility(View.GONE);
            btn_chat.setText("发起会话");
            btn_chat.setBackgroundColor(getResources().getColor(R.color.exit_btn));

        } else {
            btn_chat.setText("发起临时会话");
            btn_addguy.setVisibility(View.VISIBLE);
        }

        btn_chat.setOnClickListener(this);
        btn_addguy.setOnClickListener(this);


        doGetData(userid, accountid, classid);
    }

    private void doGetData(long userid, long accountid, String classid) {
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doGetClassMemberInfo(UserInfoModel.getInstance().getToken(), userid, accountid, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
            @Override
            public void success(ResponseData<MemberInfoModel> memberInfoModelResponseData, Response response) {
                int status = memberInfoModelResponseData.getStatus();
                try {
                    switch (status) {
                        case 200:
                            memberInfoModel = memberInfoModelResponseData.getData();
                            if (memberInfoModel != null) {
                                if (!TextUtils.isEmpty(memberInfoModel.getUserThPhoto())) {
                                    Picasso.with(getParent()).load(AddressManager.get("PhotoHost") + memberInfoModel.getUserThPhoto()).fit().into(cir_userimg);
                                }
                                tv_stuname.setText(memberInfoModel.getUserName());
                                if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                                    tv_personlityName.setText(memberInfoModel.getPersonalityName());
                                }
                                tv_angle.setText("爱心天使：" + memberInfoModel.getMilkAngle());
                                tv_love.setText("爱心学员：" + memberInfoModel.getIntroducer());
                                newsTopFourModels = memberInfoModel.getNewsTopFour();
                                doGetPhotoView();//展示图片
                                if ("4".equals(memberInfoModel.getClassRole())) {
                                    if (Long.parseLong(memberInfoModel.getTotalLossWeight()) > 0) {
                                        tv_Lossweight.setText("+" + memberInfoModel.getTotalLossWeight());//减重
                                    } else {
                                        tv_Lossweight.setText(memberInfoModel.getTotalLossWeight());//减重
                                    }
                                    tv_initWeit.setText(memberInfoModel.getInitWeight());//初始体重
                                    tv_currenweight.setText(memberInfoModel.getCurrentWeight());//现在体重
                                    if (!TextUtils.isEmpty(memberInfoModel.getInitThImg()))//初始体重图片
                                    {
                                        Log.i("初始体重图片" + AddressManager.get("PhotoHost") + memberInfoModel.getInitThImg());
                                        Picasso.with(getParent()).load(AddressManager.get("PhotoHost") + memberInfoModel.getInitThImg()).fit().into(im_InitImage);
                                    }
                                    if (!TextUtils.isEmpty(memberInfoModel.getCurttentThImg())) {   //现在体重图片
                                        Picasso.with(getParent()).load(AddressManager.get("PhotoHost") + memberInfoModel.getCurttentThImg()).fit().into(im_currenimWeight);
                                        Log.i("现在体重图片" + AddressManager.get("PhotoHost") + memberInfoModel.getCurttentThImg());
                                    }
                                }

                            }
                            break;
                        default:
                            Util.toastMsg(memberInfoModelResponseData.getMsg());
                            break;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void doGetPhotoView() {
        if (newsTopFourModels.size() == 0) {

        }
        for (int i = 0; i < newsTopFourModels.size(); i++) {

            View view = mInflater.inflate(R.layout.activity_index_gallery_item, gallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            Picasso.with(this).load(AddressManager.get("PhotoHost") + newsTopFourModels.get(i).getThumbnailImgUrl()).fit().into(img);
            Log.i("动态" + AddressManager.get("PhotoHost") + newsTopFourModels.get(i).getThumbnailImgUrl());
            gallery.addView(view);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dynamic:
                if (userid == accountid) {
                    Intent personal = new Intent(this, PersionalActivity.class);
                    personal.putExtra("isFocus", memberInfoModel.getIsFocus());
                    personal.putExtra("personalId", userid);
                    personal.putExtra("personalName", memberInfoModel.getUserName());
                    startActivity(personal);
                }
                break;
            case R.id.tv_chart:
                //查看曲线图
                break;
            case R.id.btn_chat:
                final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
                if (!hxid.equals(HXAccountId)) {
                    Intent intent = new Intent(PersonDetailActivity.this, ChatActivity.class);
                    intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                    intent.putExtra("userId", HXAccountId);
                    intent.putExtra("name", UserName);
//                intent.putExtra("classId", classMemberModel.getCGId());//??
                    startActivity(intent);
                } else {
                    Util.toastMsg("不能给自己发消息！");
                }
                break;
            case R.id.btn_addguy://添加好友
                //参数为要添加的好友的username和添加理由
                sentFriendApply();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    //加好友请求
    private void sentFriendApply() {
        Log.i(TAG, "好友 getUserId = " + UserInfoModel.getInstance().getUserId() + " getAccountId=  " + AccountId);
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        service.sentFriendApply(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    try {
                        EMClient.getInstance().contactManager().addContact(HXAccountId, "love you");
                        Log.i(TAG, "好友请求成功！");
                        Util.toastMsg("已发加好友请求！");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        Log.i(TAG, "加好友请求失败！");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ZillaApi.dealNetError(error);

            }
        });

    }
}
