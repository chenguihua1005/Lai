package com.softtek.lai.module.bodygame3.more.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MoreHasFragment extends Fragment {

    @InjectView(R.id.arrow_spinner)
    ArrowSpinner2 arrow;
    @InjectView(R.id.tv_role)
    TextView tv_role_name;
    @InjectView(R.id.tv_number)
    TextView tv_number;

    private List<ClassModel> classModels;
    private ClassModel model;

    private DeleteClass deleteClass;

    public MoreHasFragment() {

    }

    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }

    public static MoreHasFragment getInstance(DeleteClass deleteClass){
        MoreHasFragment fragment=new MoreHasFragment();
        fragment.setDeleteClass(deleteClass);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_has, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this,view);
        classModels=getArguments().getParcelableArrayList("class");
        if (classModels != null && !classModels.isEmpty()) {
            model = classModels.get(0);
            int role = model.getClassRole();
            tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
            tv_number.setText(model.getClassCode());
            choosePanel(role);

        }
        arrow.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, ClassModel data, int position) {
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data.getClassName());
                ImageView iv_icon = holder.getView(R.id.iv_icon);
                int icon;
                switch (data.getClassRole()) {
                    case 1:
                        icon = R.drawable.class_zongjiaolian;
                        break;
                    case 2:
                        icon = R.drawable.class_jiaolian;
                        break;
                    case 3:
                        icon = R.drawable.class_zhujiao;
                        break;
                    default:
                        icon = R.drawable.class_xueyuan;
                        break;
                }
                iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                TextView tv_role = holder.getView(R.id.tv_role_name);
                int role = data.getClassRole();
                tv_role.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                TextView tv_number = holder.getView(R.id.tv_number);
                tv_number.setText(data.getClassCode());
            }

            @Override
            public String getText(int position) {
                if (classModels != null && !classModels.isEmpty()) {
                    return classModels.get(position).getClassName();
                } else {
                    return "尚未开班";
                }
            }
        });

        arrow.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model = classModels.get(i);
                int role = model.getClassRole();
                tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                tv_number.setText(model.getClassCode());
                choosePanel(role);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    private void choosePanel(int role) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("class", model);
        switch (role) {
            case 1://总教练
                HeadCoachFragment headCoachFragment = new HeadCoachFragment();
                headCoachFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, headCoachFragment).commitAllowingStateLoss();
                break;
            case 2://教练
                CoachFragment coachFragment = new CoachFragment();
                coachFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, coachFragment).commitAllowingStateLoss();
                break;
            case 3://助教
                getChildFragmentManager().beginTransaction().replace(R.id.container, new AssistantFragment()).commitAllowingStateLoss();
                break;
            case 4://学员
                StudentFragment studentFragment = new StudentFragment();
                studentFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.container, studentFragment).commitAllowingStateLoss();
                break;
        }
    }


    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 0) {
            //更新班级姓名
            ClassModel model = clazz.getModel();
            arrow.setText(model.getClassName());
            this.model.setClassName(model.getClassName());
            arrow.getAdapter().notifyDataSetChanged();
        }else if(clazz.getStatus()==1){
            //添加新班级
            this.classModels.add(clazz.getModel());
            arrow.getAdapter().notifyDataSetChanged();
        }else if(clazz.getStatus()==2){
            //删除班级
            for (ClassModel model:classModels){
                if(model.getClassCode().equals(clazz.getModel().getClassCode())){
                    this.classModels.remove(model);
                    arrow.getAdapter().notifyDataSetChanged();
                    break;
                }
            }
            if(classModels.isEmpty()){
                if(deleteClass!=null){
                    deleteClass.deletClass(0);
                }
            }else {
                arrow.setSelected(0);
                this.model=classModels.get(0);
                choosePanel(this.model.getClassRole());
            }
        }
    }

    public interface DeleteClass{
        void deletClass(int classCount);
    }
}
