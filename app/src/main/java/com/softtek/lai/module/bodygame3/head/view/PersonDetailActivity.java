package com.softtek.lai.module.bodygame3.head.view;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
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
import retrofit.client.Response;
import zilla.libcore.Zilla;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.id.img;
import static com.softtek.lai.R.id.thursday;
import static java.security.AccessController.getContext;

@InjectLayout(R.layout.activity_person_detail)
public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {
    private int[] mImgIds;
    private LayoutInflater mInflater;
    @InjectView(R.id.gallery)
    LinearLayout gallery;
    HeadService headService;
    NewsTopFourModel newsTopFourModel;
    Long userid,accountid;
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
    Button btn_chat;//发起聊天
    @InjectView(R.id.btn_addguy)
    Button btn_addguy;//加好友
    @InjectView(R.id.im_guanzhu)
    ImageView im_guanzhu;
    private List<NewsTopFourModel> newsTopFourModels=new ArrayList<NewsTopFourModel>();
    @Override
    protected void initViews() {
        doGetData(Long.parseLong("3399"),Long.parseLong("3399"),"C4E8E179-FD99-4955-8BF9-CF470898788B");
        tv_dynamic.setOnClickListener(this);
        try {
            if (memberInfoModel!=null) {
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
        mInflater=LayoutInflater.from(this);
        userid=UserInfoModel.getInstance().getUserId();
        accountid=UserInfoModel.getInstance().getUserId();
    }
    private void doGetData(long userid,long accountid,String classid) {
        headService= ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doGetClassMemberInfo(UserInfoModel.getInstance().getToken(), userid, accountid, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
            @Override
            public void success(ResponseData<MemberInfoModel> memberInfoModelResponseData, Response response) {
                int status=memberInfoModelResponseData.getStatus();
                try {
                    switch (status)
                    {
                        case 200:
                            memberInfoModel=memberInfoModelResponseData.getData();
                            if (memberInfoModel!=null)
                            {
                                if (!TextUtils.isEmpty(memberInfoModel.getUserThPhoto()))
                                {
                                    Picasso.with(getParent()).load(AddressManager.get("PhotoHost")+memberInfoModel.getUserThPhoto()).fit().into(cir_userimg);
                                }
                                tv_stuname.setText(memberInfoModel.getUserName());
                                if (!TextUtils.isEmpty(memberInfoModel.getPersonalityName()))
                                {
                                    tv_personlityName.setText(memberInfoModel.getPersonalityName());
                                }
                                tv_angle.setText("爱心天使："+memberInfoModel.getMilkAngle());
                                tv_love.setText("爱心学员："+memberInfoModel.getIntroducer());
                                newsTopFourModels=memberInfoModel.getNewsTopFour();
                                doGetPhotoView();//展示图片
                                if ("4".equals(memberInfoModel.getClassRole()))
                                {
                                    if (Long.parseLong(memberInfoModel.getTotalLossWeight())>0)
                                    {
                                        tv_Lossweight.setText("+"+memberInfoModel.getTotalLossWeight());//减重
                                    }
                                    else {
                                        tv_Lossweight.setText(memberInfoModel.getTotalLossWeight());//减重
                                    }
                                    tv_initWeit.setText(memberInfoModel.getInitWeight());//初始体重
                                    tv_currenweight.setText(memberInfoModel.getCurrentWeight());//现在体重
                                    if (!TextUtils.isEmpty(memberInfoModel.getInitThImg()))//初始体重图片
                                    {
                                        Log.i("初始体重图片"+AddressManager.get("PhotoHost")+memberInfoModel.getInitThImg());
                                        Picasso.with(getParent()).load(AddressManager.get("PhotoHost")+memberInfoModel.getInitThImg()).fit().into(im_InitImage);
                                    }
                                    if (!TextUtils.isEmpty(memberInfoModel.getCurttentThImg()))
                                    {   //现在体重图片
                                        Picasso.with(getParent()).load(AddressManager.get("PhotoHost")+memberInfoModel.getCurttentThImg()).fit().into(im_currenimWeight);
                                        Log.i("现在体重图片"+AddressManager.get("PhotoHost")+memberInfoModel.getCurttentThImg());
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
    private void doGetPhotoView()
    {
        if (newsTopFourModels.size()==0)
        {

        }
        for(int i=0;i<newsTopFourModels.size();i++)
        {

            View view=mInflater.inflate(R.layout.activity_index_gallery_item,gallery,false);
            ImageView img = (ImageView)view.findViewById(R.id.img);
            Picasso.with(this).load(AddressManager.get("PhotoHost")+newsTopFourModels.get(i).getThumbnailImgUrl()).fit().into(img);
            Log.i("动态"+AddressManager.get("PhotoHost")+newsTopFourModels.get(i).getThumbnailImgUrl());
            gallery.addView(view);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_dynamic:
                if (userid==accountid)
                {
                    Intent personal=new Intent(this, PersionalActivity.class);
                    personal.putExtra("isFocus",memberInfoModel.getIsFocus());
                    personal.putExtra("personalId",userid);
                    personal.putExtra("personalName",memberInfoModel.getUserName());
                    startActivity(personal);
                }
                break;
            case R.id.tv_chart:
                //查看曲线图
                break;
        }
    }
}
