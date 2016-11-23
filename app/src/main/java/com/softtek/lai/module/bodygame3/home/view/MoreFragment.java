package com.softtek.lai.module.bodygame3.home.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.module.bodygame3.more.view.AssistantFragment;
import com.softtek.lai.module.bodygame3.more.view.CoachFragment;
import com.softtek.lai.module.bodygame3.more.view.HeadCoachFragment;
import com.softtek.lai.module.bodygame3.more.view.StudentFragment;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;

import java.util.List;

import butterknife.InjectView;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment {

    @InjectView(R.id.arrow_spinner)
    ArrowSpinner2 arrow;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_role)
    TextView tv_role_name;
    @InjectView(R.id.tv_number)
    TextView tv_number;

    private List<ClassModel> classModels;
    private ClassModel model;

    public MoreFragment() {

    }

    @Override
    protected void lazyLoad() {
        ZillaApi.NormalRestAdapter.create(MoreService.class)
        .getMoreInfo(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId()
                , new RequestCallback<ResponseData<List<ClassModel>>>() {
                    @Override
                    public void success(ResponseData<List<ClassModel>> listResponseData, Response response) {
                        classModels=listResponseData.getData();
                        arrow.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(),classModels,R.layout.selector_class_item) {
                            @Override
                            public void convert(ViewHolder holder, ClassModel data, int position) {
                                TextView tv_class_name=holder.getView(R.id.tv_class_name);
                                tv_class_name.setText(data.getClassName());
                                ImageView iv_icon=holder.getView(R.id.iv_icon);
                                int icon;
                                switch (data.getClassRole()){
                                    case 1:
                                        icon=R.drawable.class_zongjiaolian;
                                        break;
                                    case 2:
                                        icon=R.drawable.class_jiaolian;
                                        break;
                                    case 3:
                                        icon=R.drawable.class_zhujiao;
                                        break;
                                    default:
                                        icon=R.drawable.class_xueyuan;
                                        break;
                                }
                                iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(),icon));
                                TextView tv_role=holder.getView(R.id.tv_role_name);
                                int role=data.getClassRole();
                                tv_role.setText(role==1?"总教练":role==2?"教练":role==3?"助教":role==4?"学员":"");
                                TextView tv_number=holder.getView(R.id.tv_number);
                                tv_number.setText(data.getClassCode());
                            }

                            @Override
                            public String getText(int position) {
                                if(classModels!=null&&!classModels.isEmpty()){
                                    return classModels.get(position).getClassName();
                                }else {
                                    return "尚未开班";
                                }
                            }
                        });
                        if(classModels!=null&&!classModels.isEmpty()){
                            model=classModels.get(0);
                            int role=model.getClassRole();
                            tv_role_name.setText(role==1?"总教练":role==2?"教练":role==3?"助教":role==4?"学员":"");
                            tv_number.setText(model.getClassCode());
                            choosePanel(role);

                        }
                    }
                });
    }

    @Override
    protected void initViews() {
        tv_title.setText("更多");

        arrow.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model=classModels.get(i);
                int role=model.getClassRole();
                tv_role_name.setText(role==1?"总教练":role==2?"教练":role==3?"助教":role==4?"学员":"");
                tv_number.setText(model.getClassCode());
                choosePanel(role);
            }
        });

    }

    @Override
    protected void initDatas() {

    }

    private void choosePanel(int role){
        Bundle bundle=new Bundle();
        bundle.putParcelable("class",model);
        switch (role) {
            case 1://总教练
                HeadCoachFragment headCoachFragment=new HeadCoachFragment();
                headCoachFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, headCoachFragment).commit();
                break;
            case 2://教练
                CoachFragment coachFragment=new CoachFragment();
                coachFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, coachFragment).commit();
                break;
            case 3://助教
                getChildFragmentManager().beginTransaction().replace(R.id.container, new AssistantFragment()).commit();
                break;
            case 4://学员
                StudentFragment studentFragment=new StudentFragment();
                studentFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, studentFragment).commit();
                break;
        }
    }
}
