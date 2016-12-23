package com.softtek.lai.module.bodygame3.head.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
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
import com.softtek.lai.module.bodygame3.conversation.service.ContactService;
import com.softtek.lai.module.bodygame3.graph.GraphActivity;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.model.NewsTopFourModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.module.community.view.PersionalActivity;
import com.softtek.lai.module.picture.view.PictureActivity;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.softtek.lai.widgets.HorizontalListView;
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
public class PersonDetailActivity extends BaseActivity implements View.OnClickListener, TitlePopup.OnItemOnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "PersonDetailActivity";
    private int[] mImgIds;
    private LayoutInflater mInflater;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.fl_right)
    FrameLayout fl_right;

    @InjectView(R.id.iv_email)
    ImageView iv_email;


    HeadService headService;
    NewsTopFourModel newsTopFourModel;
    Long userid;
    int SetLove = 1;
    MemberInfoModel memberInfoModel;
    @InjectView(R.id.ll_weigh)
    LinearLayout ll_weigh;
    @InjectView(R.id.hlist_dy)
    HorizontalListView hlist_dy;
    @InjectView(R.id.re_hlist_dy)
    RelativeLayout re_hlist_dy;
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
    @InjectView(R.id.tv_chart)
    TextView tv_chart;
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

    @InjectView(R.id.im_guanzhu)
    CheckBox im_guanzhu;
    private List<NewsTopFourModel> newsTopFourModels = new ArrayList<NewsTopFourModel>();
    EasyAdapter<NewsTopFourModel> easyAdapter;
    //定义标题栏弹窗按钮
    private TitlePopup titlePopup;
    boolean show_state = true;

    private int isFriend = 0;//1: 好友  0 ： 不是好友
    private long AccountId;
    private String HXAccountId;//环信id
    private String UserName;
    private String AFriendId;//好友关系id
    private String ClassId;
    ArrayList<String> images = new ArrayList<>();

    private static final int GET_Sian = 1;//发布签名

    @Override
    protected void initViews() {
        tv_dynamic.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        ll_chart.setOnClickListener(this);
        tv_chart.setOnClickListener(this);
        im_guanzhu.setOnClickListener(this);
        tv_title.setText("个人详情");
        fl_right.setVisibility(View.INVISIBLE);

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
        easyAdapter = new EasyAdapter<NewsTopFourModel>(this, newsTopFourModels, R.layout.activity_index_gallery_item) {
            @Override
            public void convert(ViewHolder holder, NewsTopFourModel data, int position) {
                ImageView img = holder.getView(R.id.img);
                if (!TextUtils.isEmpty(data.getThumbnailImgUrl())) {
                    Picasso.with(getParent()).load(AddressManager.get("photoHost") + data.getThumbnailImgUrl()).fit().centerCrop().into(img);
                } else if (!TextUtils.isEmpty(data.getImgUrl())) {
                    Picasso.with(getParent()).load(AddressManager.get("photoHost") + data.getImgUrl()).fit().centerCrop().into(img);

                }
            }
        };
        hlist_dy.setAdapter(easyAdapter);
        hlist_dy.setOnItemClickListener(this);

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
        tv_personlityName.setOnClickListener(this);

        doGetService(userid, AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId, HXAccountId);
    }

    private void doGetService(final long userid, long AccountId, String classid, String HXAccountId) {
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
        if (!TextUtils.isEmpty(HXAccountId)) {
            headService.doGetClassMemberInfoByHx(UserInfoModel.getInstance().getToken(), userid, HXAccountId, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
                @Override
                public void success(ResponseData<MemberInfoModel> memberInfoModelResponseData, Response response) {
                    int status = memberInfoModelResponseData.getStatus();
                    try {
                        switch (status) {
                            case 200:
                                memberInfoModel = memberInfoModelResponseData.getData();
                                doGetData();
                                break;
                            default:
                                Util.toastMsg(memberInfoModelResponseData.getMsg());
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            headService.doGetClassMemberInfo(UserInfoModel.getInstance().getToken(), userid, AccountId, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
                @Override
                public void success(ResponseData<MemberInfoModel> memberInfoModelResponseData, Response response) {
                    int status = memberInfoModelResponseData.getStatus();
                    try {
                        switch (status) {
                            case 200:
                                memberInfoModel = memberInfoModelResponseData.getData();
                                doGetData();
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
    }

    private void doGetData() {
        try {
            if (memberInfoModel != null) {
                String url = AddressManager.get("photoHost");
                //加载头像
                if (!TextUtils.isEmpty(memberInfoModel.getUserPhoto())) {
                    Picasso.with(getParent()).load(url + memberInfoModel.getUserPhoto()).error(R.drawable.img_default).fit().into(cir_userimg);
                }
                tv_stuname.setText(memberInfoModel.getUserName());//用户名
                AccountId = memberInfoModel.getAccountid();
                HXAccountId = memberInfoModel.getHXAccountId();
                UserName = memberInfoModel.getUserName();
                tv_angle.setText((TextUtils.isEmpty(memberInfoModel.getMilkAngle()) ? "暂无奶昔天使" : "奶昔天使：" + memberInfoModel.getMilkAngle()));
                tv_love.setText((TextUtils.isEmpty(memberInfoModel.getIntroducer()) ? "暂无爱心学员" : "爱心学员：" + memberInfoModel.getIntroducer()));
                if (AccountId == userid)//如果是本人，显示查看曲线图,如果没有爱心天使可修改爱心天使
                {

                    tv_personlityName.setEnabled(true);
                    //个性签名
                    if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName())) {
                        tv_personlityName.setText(memberInfoModel.getPersonalityName());
                        tv_personlityName.setCompoundDrawables(null, null, null, null);

                    }
                    ll_chart.setVisibility(View.VISIBLE);
                    if ("4".equals(memberInfoModel.getClassRole())) {
                        if (TextUtils.isEmpty(memberInfoModel.getIntroducer())) {
                            titlePopup.addAction(new ActionItem(PersonDetailActivity.this, "修改爱心学员", R.drawable.modifylove));
                            fl_right.setVisibility(View.VISIBLE);
                        }
                    }
                    im_guanzhu.setVisibility(View.GONE);

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
                        ll_chart.setVisibility(View.VISIBLE);
                    }
                    if ("1".equals(memberInfoModel.getIsFriend()))//如果是好友，显示发起聊天
                    {
                        btn_chat.setVisibility(View.VISIBLE);
                        titlePopup.addAction(new ActionItem(PersonDetailActivity.this, "删除好友", R.drawable.deletefriend));
                        fl_right.setVisibility(View.VISIBLE);
                    } else {//不是好友，可发起临时会话，显示添加好友
                        btn_chat.setVisibility(View.VISIBLE);
                        btn_chat.setText("发起临时会话");
                        btn_addguy.setVisibility(View.VISIBLE);
                        iv_email.setVisibility(View.INVISIBLE);
                    }
                    if ("false".equals(memberInfoModel.getIsFocus()))//没有关注
                    {
                        im_guanzhu.setChecked(false);
                    } else {
                        im_guanzhu.setChecked(true);

                    }
                }

                doGetPhotoView();//展示图片
                if ("4".equals(memberInfoModel.getClassRole())) {
                    ll_weigh.setVisibility(View.VISIBLE);
                    if (Float.parseFloat(memberInfoModel.getTotalLossWeight()) < 0) {
                        String lossweight[] = memberInfoModel.getTotalLossWeight().split("-");
                        tv_Lossweight.setText("增重  " + lossweight[1] + "斤");//减重d
                    } else {
//                        DecimalFormat df = new DecimalFormat("0.0");//.00就表示保留后两位数

                        tv_Lossweight.setText("减重  " + memberInfoModel.getTotalLossWeight() + "斤");//减重
                    }
                    tv_initWeit.setText("0".equals(memberInfoModel.getInitWeight()) ? "暂无数据" :"初始体重 "+ memberInfoModel.getInitWeight()+"斤");//初始体重
                    tv_currenweight.setText("0".equals(memberInfoModel.getCurrentWeight()) ? "尚未复测" : "当前体重 "+memberInfoModel.getCurrentWeight()+"斤");//现在体重

                    if (!TextUtils.isEmpty(memberInfoModel.getInitThImg()))//初始体重图片
                    {
                        Log.i("初始体重图片" + url + memberInfoModel.getInitThImg());
                        Picasso.with(getParent()).load(url + memberInfoModel.getInitThImg()).fit().into(im_InitImage);
                    }
                    if (!TextUtils.isEmpty(memberInfoModel.getCurttentThImg())) {   //现在体重图片
                        Picasso.with(getParent()).load(url + memberInfoModel.getCurttentThImg()).fit().into(im_currenimWeight);
                        Log.i("现在体重图片" + url + memberInfoModel.getCurttentThImg());
                    }
                } else {
                    ll_chart.setVisibility(View.GONE);
                }

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
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
                Log.i("图片测试", AddressManager.get("photoHost") + newsTopFourModels.get(n).getThumbnailImgUrl());

            }
            easyAdapter.notifyDataSetChanged();
        } else {
            re_hlist_dy.setVisibility(View.GONE);
            tv_no_dy.setVisibility(View.VISIBLE);
        }
    }

    private int PERSONDY = 3;

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
            case R.id.tv_dynamic:
                Intent personal = new Intent(this, PersionalActivity.class);
                personal.putExtra("personalId", AccountId + "");
                personal.putExtra("personalName", memberInfoModel.getUserName());
                personal.putExtra("isFocus", Integer.parseInt("true".equals(memberInfoModel.getIsFocus()) ? "1" : "0"));
                startActivityForResult(personal, PERSONDY);
                break;
            case R.id.tv_chart:
                Intent graph = new Intent(this, GraphActivity.class);
                graph.putExtra("accountId", AccountId);
                graph.putExtra("classId", ClassId);
                startActivity(graph);
                //查看曲线图
                break;
            case R.id.btn_chat:
                Log.i(TAG, "userId = " + HXAccountId + " UserName = " + UserName);
                final String hxid = SharedPreferenceService.getInstance().get("HXID", "-1");
                if (!hxid.equals(HXAccountId)) {
                    Intent intent = new Intent(PersonDetailActivity.this, ChatActivity.class);
                    intent.putExtra("chatType", EaseConstant.CHATTYPE_SINGLE);
                    intent.putExtra("userId", HXAccountId);
                    intent.putExtra("name", UserName);
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
            case R.id.fl_right:
                titlePopup.show(view);
                break;
            case R.id.im_guanzhu:
                headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
                if (im_guanzhu.isChecked()) {
                    Log.i("关注");
                    headService.doFocusAccount(UserInfoModel.getInstance().getToken(), userid, AccountId, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                            int status = responseData.getStatus();
                            switch (status) {
                                case 200:
                                    memberInfoModel.setIsFocus("true");
                                    break;
                                default:
                                    im_guanzhu.setChecked(true);
                                    Util.toastMsg(responseData.getMsg());
                                    break;
                            }
                        }
                    });
                } else {
                    Log.i("取消关注");

                    headService.doCancleFocusAccount(UserInfoModel.getInstance().getToken(), userid, AccountId, new RequestCallback<ResponseData>() {
                        @Override
                        public void success(ResponseData responseData, Response response) {
                            int status = responseData.getStatus();
                            switch (status) {
                                case 200:
                                    memberInfoModel.setIsFocus("false");
                                    break;
                                default:
                                    im_guanzhu.setChecked(false);
                                    Util.toastMsg(responseData.getMsg());
                                    break;
                            }
                        }
                    });

                }
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(HXAccountId, s);

                    ContactService service = ZillaApi.NormalRestAdapter.create(ContactService.class);
                    service.sentFriendApply(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), AccountId, TextUtils.isEmpty(ClassId) ? " " : ClassId, new Callback<ResponseData>() {
                        @Override
                        public void success(final ResponseData responseData, Response response) {
                            int status = responseData.getStatus();

                            if (200 == status) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Util.toastMsg(getResources().getString(R.string.send_successful));
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        String s2 = getResources().getString(R.string.Request_add_buddy_failure);
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
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                } catch (final HyphenateException e) {//环信加好友成功与否并不影响这边逻辑
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
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
        final ProgressDialog pd = new ProgressDialog(PersonDetailActivity.this);
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
            doGetService(userid, AccountId, ClassId, HXAccountId);
            titlePopup.cleanAction();
            fl_right.setVisibility(View.INVISIBLE);
        }
        if (requestCode == GET_Sian && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(data.getStringExtra("sina"))) {
                tv_personlityName.setText(data.getStringExtra("sina"));
                tv_personlityName.setCompoundDrawables(null, null, null, null);
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

    //POPMenu监听
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(this, PictureActivity.class);
        intent1.putExtra("images", images);
        intent1.putExtra("position", i);
        startActivity(intent1);
    }
}
