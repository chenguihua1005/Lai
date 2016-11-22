package com.softtek.lai.module.bodygame3.head.view;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.head.adapter.PartnerAdapter;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;
import com.softtek.lai.module.bodygame3.head.model.PartnersModel;
import com.softtek.lai.module.bodygame3.head.model.RongyuModel;
import com.softtek.lai.module.bodygame3.head.model.TuijianModel;
import com.softtek.lai.module.bodygame3.head.model.TypeModel;
import com.softtek.lai.module.bodygame3.head.model.ZhaopianModel;
import com.softtek.lai.module.bodygame3.head.net.HeadService;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@linkHeadGameFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@linkHeadGameFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
@InjectLayout(R.layout.fragment_head_game_fragment1)
public class HeadGameFragment1 extends LazyBaseFragment implements View.OnClickListener {
    //toolbar标题
    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;
    @InjectView(R.id.spinner_title)
    ArrowSpinner2 spinner_title;
    @InjectView(R.id.list_partner)
    ListView list_partner;
    @InjectView(R.id.searchContent)
    EditText searchContent;
    @InjectView(R.id.group_name)
    TextView group_name;//组名
    @InjectView(R.id.jianzhongbi_tv)
    TextView jianzhongbi_tv;//总减重比
    @InjectView(R.id.iv_group)
    ImageView iv_group;//图标
    @InjectView(R.id.student_tv)
    TextView student_tv;//优秀学员名字
    @InjectView(R.id.iv_studenticon)
    ImageView studenticon;//优秀学员头像
    @InjectView(R.id.student_jianzhong)
    TextView student_jianzhong;//优秀学员减重比
    @InjectView(R.id.student_jianzhi)
    TextView student_jianzhi;//优秀学员减脂比
    @InjectView(R.id.iv_imagevideo1)
    RelativeLayout iv_imagevideo1;//本周推荐1
    @InjectView(R.id.iv_imagevideo2)
    RelativeLayout iv_imagevideo2;
    @InjectView(R.id.video_type1)
    TextView video_type1;
    @InjectView(R.id.video_name1)
    TextView video_name1;
    @InjectView(R.id.video_type2)
    TextView video_type2;
    @InjectView(R.id.video_name2)
    TextView video_name2;
    @InjectView(R.id.head_images)
    ImageView head_images;//评论区头像
    @InjectView(R.id.name_user)
    TextView name_user;//用户
    @InjectView(R.id.gengxin)
    TextView gengxin;
    @InjectView(R.id.pinglun)
    TextView pinglun;
    HeadService service;
    PartnerAdapter partnerAdapter;
    private List<PartnersModel> partnersModels = new ArrayList<PartnersModel>();
    private List<TuijianModel> tuijianModels = new ArrayList<TuijianModel>();
    private int typecode;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        searchContent.setOnClickListener(this);
        partnerAdapter = new PartnerAdapter((BaseActivity) getActivity(), partnersModels);
        list_partner.setAdapter(partnerAdapter);
        //配置列表数据
        final List<String> data = new ArrayList<>();
        data.add("测试数据1测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        final List<TypeModel> datas = new ArrayList<TypeModel>();
        TypeModel model1 = new TypeModel(1, "体重比");
        datas.add(model1);
        TypeModel model2 = new TypeModel(2, "体脂");
        datas.add(model2);
        TypeModel model3 = new TypeModel(3, "减重比");
        datas.add(model3);
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(), data, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                return data.get(position);
            }

        });
        spinner_title.attachCustomSource(new ArrowSpinnerAdapter<TypeModel>(getContext(), datas, R.layout.class_title) {
            @Override
            public void convert(ViewHolder holder, TypeModel data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_title);
                tv_class_name.setText(data.getTypename());
            }

            @Override
            public String getText(int position) {
                //根据position返回当前值给标题
                typecode = datas.get(position).getTypecode();
                Log.e("typecode", typecode + "");
                return datas.get(position).getTypename();
            }

        });
        Log.e("ddddd", UserInfoModel.getInstance().getToken() + "," + UserInfoModel.getInstance().getUser().getUserid());

    }

    @Override
    protected void initDatas() {
        service = ZillaApi.NormalRestAdapter.create(HeadService.class);
        service.getfirst(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), 10, new RequestCallback<ResponseData<ClassinfoModel>>() {
            @Override
            public void success(ResponseData<ClassinfoModel> classinfoModelResponseData, Response response) {
                Util.toastMsg(classinfoModelResponseData.getMsg());
                if (classinfoModelResponseData.getData() != null) {
                    ClassinfoModel classinfoModel = classinfoModelResponseData.getData();
                    //荣誉榜
                    RongyuModel rongyuModel = classinfoModel.getHonor();
                    group_name.setText(rongyuModel.getGroupName());
                    jianzhongbi_tv.setText("总减重比" + rongyuModel.getGroupLossPre() + "斤");
                    student_tv.setText(rongyuModel.getStuName());
                    String path = AddressManager.getHost();
                    if (StringUtils.isNotEmpty(rongyuModel.getStuPhoto())) {
                        Picasso.with(getContext()).load(path + rongyuModel.getStuPhoto()).into(studenticon);
                    }
                    student_jianzhong.setText("减重" + rongyuModel.getLossPre() + "斤");
                    student_jianzhi.setText("减脂" + rongyuModel.getPysPre() + "%");
                    if (classinfoModel.getPartnersList() != null) {
                        partnersModels.addAll(classinfoModel.getPartnersList());
                        partnerAdapter.update(partnersModels);
                    }
                    //本周推荐
                    tuijianModels.addAll(classinfoModel.getListRec());
                    video_type1.setText(tuijianModels.get(0).getVideoType());
                    video_name1.setText(tuijianModels.get(0).getTitle());
                    iv_imagevideo1.setBackground(Drawable.createFromPath(path + tuijianModels.get(0).getPhoto()));
                    video_type2.setText(tuijianModels.get(1).getVideoType());
                    video_name2.setText(tuijianModels.get(1).getTitle());
                    iv_imagevideo2.setBackground(Drawable.createFromPath(path + tuijianModels.get(1).getPhoto()));
                    //照片墙
                    ZhaopianModel zhaopianModel = classinfoModel.getPhotoWall();
                    if (StringUtils.isNotEmpty(zhaopianModel.getUserPhoto())) {
                        Picasso.with(getContext()).load(path + zhaopianModel.getUserPhoto()).into(head_images);
                    }
                    name_user.setText(zhaopianModel.getUserName());
                    pinglun.setText(zhaopianModel.getNum() + "条评论");
                    //计算时间
                    long[] days = DateUtil.getInstance().getDaysForNow(zhaopianModel.getReleaseTime());
                    String time = "";
                    if (days[0] == 0) {//今天
                        if (days[3] < 60) {//小于1分钟
                            time = "刚刚";
                            gengxin.setText("最后更新" + time);
                        } else if (days[3] >= 60 && days[3] < 3600) {//>=一分钟小于一小时
                            time = days[2] + "分钟前";
                            gengxin.setText("最后更新" + time);
                        } else {//大于一小时
                            time = days[1] + "小时前";
                            gengxin.setText("最后更新" + time);
                        }
                    } else if (days[0] == 1) {//昨天
                        time = "昨天";
                        gengxin.setText("最后更新" + time);
                    } else {
                        time = days[0] + "天前";
                        gengxin.setText("最后更新" + time);
                    }

                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchContent:
                String content = searchContent.getText().toString().trim();
                service.getpartner(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUser().getUserid(), content, new RequestCallback<ResponseData<PartnersModel>>() {
                    @Override
                    public void success(ResponseData<PartnersModel> partnersModelResponseData, Response response) {
                        Util.toastMsg(partnersModelResponseData.getMsg());

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
                break;
        }
    }
}
