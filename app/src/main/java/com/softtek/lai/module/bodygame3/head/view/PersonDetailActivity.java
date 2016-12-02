package com.softtek.lai.module.bodygame3.head.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.conversation.model.ClassMemberModel;
import com.softtek.lai.module.bodygame3.head.model.MemberInfoModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_person_detail)
public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "PersonDetailActivity";
    private int[] mImgIds;
    private LayoutInflater mInflater;

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.gallery)
    LinearLayout gallery;
    HeadService headService;
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
    String img[], thImg[];
    ClassMemberModel classMemberModel;


    @Override
    protected void initViews() {
        doGetData(Long.parseLong("3399"), Long.parseLong("3399"), "C4E8E179-FD99-4955-8BF9-CF470898788B");

        classMemberModel = (ClassMemberModel) getIntent().getSerializableExtra("classMemberModel");

        Log.i(TAG, "classMemberModel= " + new Gson().toJson(classMemberModel));


    }


    @Override
    protected void initDatas() {
        mInflater = LayoutInflater.from(this);
        mImgIds = new int[]{R.drawable.img_default, R.drawable.img_default, R.drawable.img_default,
                R.drawable.img_default, R.drawable.img_default, R.drawable.img_default, R.drawable.img_default,
                R.drawable.img_default, R.drawable.img_default};
        for (int i = 0; i < mImgIds.length; i++) {

            View view = mInflater.inflate(R.layout.activity_index_gallery_item, gallery, false);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view.findViewById(R.id.tv_te);
            txt.setText("some info ");
            gallery.addView(view);

        }

        ll_left.setOnClickListener(this);

    }

    private void doGetData(long userid, long accountid, String classid) {
        headService = ZillaApi.NormalRestAdapter.create(HeadService.class);
        headService.doGetClassMemberInfo(UserInfoModel.getInstance().getToken(), userid, accountid, classid, new RequestCallback<ResponseData<MemberInfoModel>>() {
            @Override
            public void success(ResponseData<MemberInfoModel> memberInfoModelResponseData, Response response) {
                int status = memberInfoModelResponseData.getStatus();
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
                        }
                        break;
                    default:
                        Util.toastMsg(memberInfoModelResponseData.getMsg());
                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
