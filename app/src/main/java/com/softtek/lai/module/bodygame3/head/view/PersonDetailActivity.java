package com.softtek.lai.module.bodygame3.head.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.softtek.lai.module.bodygame3.graph.GraphActivity;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsTopFourModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.PopUpWindow.ActionItem;
import com.softtek.lai.widgets.PopUpWindow.TitlePopup;
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
public class PersonDetailActivity extends BaseActivity implements View.OnClickListener, TitlePopup.OnItemOnClickListener {
    private static final String TAG = "PersonDetailActivity";
    private int[] mImgIds;
    private LayoutInflater mInflater;
    @InjectView(R.id.gallery)
    LinearLayout gallery;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_email)
    ImageView iv_email;


    HeadService headService;
    NewsTopFourModel newsTopFourModel;
    Long userid, accountid;
    String classid;
    MemberInfoModel memberInfoModel;
    @InjectView(R.id.ll_weigh)
    LinearLayout ll_weigh;
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

    //定义标题栏弹窗按钮
    private TitlePopup titlePopup;
    boolean show_state = true;

//    ClassMemberModel classMemberModel;

    private int isFriend = 0;//1: 好友  0 ： 不是好友
    private long AccountId;
    private String HXAccountId;//环信id
    private String UserName;
    private String AFriendId;//好友关系id
    private String ClassId;


    @Override
    protected void initViews() {
        tv_dynamic.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        tv_chart.setOnClickListener(this);
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

        //实例化标题栏弹窗
        titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(this);
        //给标题栏弹窗添加子类
//        titlePopup.addAction(new ActionItem(this, "删除好友", R.drawable.deletefriend));
//        titlePopup.setItemOnClickListener(this);

    }


    @Override
    protected void initDatas() {
        mInflater = LayoutInflater.from(this);
        final int[] imgs = new int[]{R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect
                , R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect};
        for (int i = 0; i < imgs.length; i++) {
            View view = mInflater.inflate(R.layout.activity_index_gallery_item, gallery, false);
            final ImageView img = (ImageView) view.findViewById(R.id.img);
            Picasso.with(this).load(imgs[i]).fit().into(img);
            gallery.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    String content = (String) view.getTag(R.layout.activity_index_gallery_item);
                    Log.i("点击图片了" + view.getTag() + "haha" + content);
                    for (int i = 0; i < imgs.length; i++) {
                        if (i == id) {
                            Log.i("点击图片了" + view.getTag() + "haha" + content);
                        }
                    }

                }
            });

        }
        userid = UserInfoModel.getInstance().getUserId();
        AccountId = getIntent().getLongExtra("AccountId", 0);
        ClassId = getIntent().getStringExtra("ClassId");

        isFriend = getIntent().getIntExtra("isFriend", 0);
        HXAccountId = getIntent().getStringExtra("HXAccountId");
        UserName = getIntent().getStringExtra("UserName");
        AFriendId = getIntent().getStringExtra("AFriendId");//好友关系id

        Log.i(TAG, "isFriend =" + isFriend + " AccountId=" + AccountId + " HXAccountId=" + HXAccountId + " UserName= " + UserName + " AFriendId= " + AFriendId + " ClassId = " + ClassId);

        iv_email.setImageResource(R.drawable.more_menu);


        btn_chat.setOnClickListener(this);
        btn_addguy.setOnClickListener(this);
        fl_right.setOnClickListener(this);

        doGetData(userid, AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId);
    }

    private void doGetData(final long userid, long accountid, String classid) {
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
                                //加载头像
                                if (!TextUtils.isEmpty(memberInfoModel.getUserPhoto())) {
                                    Picasso.with(getParent()).load(AddressManager.get("photoHost") + memberInfoModel.getUserPhoto()).fit().error(R.drawable.img_default).into(cir_userimg);
                                }
                                tv_stuname.setText(memberInfoModel.getUserName());//用户名

                                tv_angle.setText((TextUtils.isEmpty(memberInfoModel.getMilkAngle()) ? "暂无奶昔天使" : "奶昔天使：" + memberInfoModel.getMilkAngle()));
                                tv_love.setText((TextUtils.isEmpty(memberInfoModel.getIntroducer()) ? "暂无爱心学员" : "爱心学员：" + memberInfoModel.getIntroducer()));
                                if (AccountId == userid)//如果是本人，显示查看曲线图
                                {
                                    //个性签名
                                    if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                                        tv_personlityName.setText(memberInfoModel.getPersonalityName());
                                    }
                                    tv_chart.setVisibility(View.VISIBLE);
                                    if (TextUtils.isEmpty(memberInfoModel.getIntroducer())) {
                                        titlePopup.addAction(new ActionItem(PersonDetailActivity.this, "修改爱心学员", R.drawable.modifylove));
                                    }
                                    titlePopup.addAction(new ActionItem(PersonDetailActivity.this, "退出体管赛", R.drawable.exit_tiguan));

                                } else {
                                    //个性签名
                                    if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                                        tv_personlityName.setText(memberInfoModel.getPersonalityName());
                                        tv_personlityName.setCompoundDrawables(null, null, null, null);//去除个性签名文本图标
                                    } else {
                                        tv_personlityName.setText("暂无个性签名");
                                        tv_personlityName.setCompoundDrawables(null, null, null, null);
                                    }
                                    if ((memberInfoModel.getIntroducerId()).equals(userid))//如果是登陆id是该学员的爱心学员，显示查看曲线图
                                    {
                                        tv_chart.setVisibility(View.VISIBLE);
                                    }
                                    if ("1".equals(memberInfoModel.getIsFriend()))//如果是好友，显示发起聊天
                                    {
                                        btn_chat.setVisibility(View.VISIBLE);
                                        titlePopup.addAction(new ActionItem(PersonDetailActivity.this, "删除好友", R.drawable.deletefriend));
                                    } else {//不是好友，可发起临时会话，显示添加好友
                                        btn_chat.setVisibility(View.VISIBLE);
                                        btn_chat.setText("发起临时会话");
                                        btn_addguy.setVisibility(View.VISIBLE);
                                        iv_email.setVisibility(View.INVISIBLE);
                                    }
                                    if ("false".equals(memberInfoModel.getIsFocus()))//没有关注
                                    {
                                        im_guanzhu.setVisibility(View.VISIBLE);
                                    } else {
                                        im_guanzhu.setVisibility(View.VISIBLE);
                                        im_guanzhu.setBackground(getResources().getDrawable(R.drawable.add_yiguanzhu));
                                    }
                                }
                                newsTopFourModels = memberInfoModel.getNewsTopFour();
                                doGetPhotoView();//展示图片
                                if ("4".equals(memberInfoModel.getClassRole())) {
                                    ll_weigh.setVisibility(View.VISIBLE);
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
            int[] imgs = new int[]{R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect
                    , R.drawable.default_icon_rect, R.drawable.default_icon_rect, R.drawable.default_icon_rect};
            for (int i = 0; i < imgs.length; i++) {
                View view = mInflater.inflate(R.layout.activity_index_gallery_item, gallery, false);
                ImageView img = (ImageView) view.findViewById(R.id.img);
                Picasso.with(this).load(imgs[i]).fit().into(img);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = view.getId();
                        Log.i("点击图片了" + id);
                    }
                });
                gallery.addView(view);
            }
        }
        for (int i = 0; i < newsTopFourModels.size(); i++) {

            View view = mInflater.inflate(R.layout.activity_index_gallery_item, gallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            Picasso.with(this).load(AddressManager.get("PhotoHost") + newsTopFourModels.get(i).getThumbnailImgUrl()).fit().into(img);
            Log.i("动态" + AddressManager.get("PhotoHost") + newsTopFourModels.get(i).getThumbnailImgUrl());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    Log.i("点击图片了" + id);
                }
            });
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
                Intent graph = new Intent(this, GraphActivity.class);
                graph.putExtra("accountId", AccountId);
                graph.putExtra("classId", ClassId);
                startActivity(graph);
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

//                    Intent intent = new Intent(this, ConversationListActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();


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
            case R.id.fl_right:
//                removeFriend();
                titlePopup.show(view);
                break;
        }
    }

    //加好友请求
    private void sentFriendApply() {
        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Log.i(TAG, "好友 getUserId = " + UserInfoModel.getInstance().getUserId() + " getAccountId=  " + AccountId + " ClassId = " + ClassId);
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        service.sentFriendApply(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                int status = responseData.getStatus();
                if (200 == status) {
                    progressDialog.dismiss();
                    Util.toastMsg(getResources().getString(R.string.send_successful));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(HXAccountId, s);
                            } catch (final HyphenateException e) {//环信加好友成功与否并不影响这边逻辑
                                e.printStackTrace();
                                progressDialog.dismiss();
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        progressDialog.dismiss();
//                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
//                                        Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
//                                    }
//                                });
                            }

                        }
                    }).start();
                }
            }

            @Override
            public void failure(final RetrofitError error) {
                ZillaApi.dealNetError(error);
                progressDialog.dismiss();
                String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                Toast.makeText(getApplicationContext(), s2, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void removeFriend() {
        Log.i(TAG, "本人 getUserId = " + UserInfoModel.getInstance().getUserId() + " 好友 getAccountId=  " + AccountId + " AFriendId =" + AFriendId);

        String st1 = getResources().getString(R.string.deleting);
        final String st2 = getResources().getString(R.string.Delete_failed);
        final ProgressDialog pd = new ProgressDialog(PersonDetailActivity.this);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
        service.removeFriend(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AFriendId, new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                pd.dismiss();
                int status = responseData.getStatus();
                if (200 == status) {
                    Util.toastMsg("删除成功！");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().deleteContact(HXAccountId);
                                Log.i(TAG, "删除好友成功！");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                Log.i(TAG, "删除好友error！");
                            }
                        }
                    }).start();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                pd.dismiss();
                Util.toastMsg(st2 +error.getMessage());
                ZillaApi.dealNetError(error);
            }
        });
    }


    //POPMenu监听
    @Override
    public void onItemClick(ActionItem item, int position) {
        if ("删除好友".equals(item.mTitle)) {
            removeFriend();
        }
        if ("修改爱心学员".equals(item.mTitle)) {
//            removeFriend();
        }
        if ("删除好友".equals(item.mTitle)) {
//            removeFriend();
        }

    }
}
