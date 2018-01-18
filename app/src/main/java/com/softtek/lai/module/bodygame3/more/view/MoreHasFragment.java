package com.softtek.lai.module.bodygame3.more.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.ggx.widgets.nicespinner.ListDialog;
import com.ggx.widgets.view.CustomProgress;
import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.model.ClassRole;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.widgets.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.id.tv_role;

public class MoreHasFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.arrow_spinner)
    ListDialog arrow;
    @InjectView(tv_role)
    TextView tv_role_name;
    @InjectView(R.id.tv_number)
    TextView tv_number;

    @InjectView(R.id.rl_change_role)
    RelativeLayout rl_change_role;//非总教练可以修改角色

    private List<ClassModel> classModels;
    private ClassModel model;
    private DeleteClass deleteClass;
    private int selected;

    private int classRole;//班级角色1:总教练,2:教练,3:助教,4:学员
//    private int classRole_current ;

    private List<ClassRole> classRoleList = new ArrayList<>();

    public MoreHasFragment() {

    }

    public void setDeleteClass(DeleteClass deleteClass) {
        this.deleteClass = deleteClass;
    }


    public static MoreHasFragment getInstance(DeleteClass deleteClass) {
        MoreHasFragment fragment = new MoreHasFragment();
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
        ButterKnife.inject(this, view);
        classModels = getArguments().getParcelableArrayList("class");
        model = getArguments().getParcelable("classModel");


        rl_change_role.setOnClickListener(this);

        //初始当前角色
        if (model != null) {
            classRole = model.getClassRole();
        }
        //非 总教练可以修改身份
        if (classRole == 2 || classRole == 3 || classRole == 4) {
            rl_change_role.setEnabled(true);
        } else {
            rl_change_role.setEnabled(false);
        }


        if (classModels != null && !classModels.isEmpty()) {
            if (model != null) {
                boolean isExist = false;
                for (int i = 0; i < classModels.size(); i++) {
                    ClassModel model = classModels.get(i);
                    if (model.getClassCode().equals(this.model.getClassCode())) {
                        selected = i;
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    model = classModels.get(0);
                    if (deleteClass != null) {
                        deleteClass.doSelected(model);
                    }
                }
            } else {
                model = classModels.get(0);
                if (deleteClass != null) {
                    deleteClass.doSelected(model);
                }
            }
            int role = model.getClassRole();
            tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "未知");
            //添加小组名字
            if (role != 1) {
                tv_role_name.append("(");
                tv_role_name.append(TextUtils.isEmpty(model.getCGName()) ? "未知" : model.getCGName());
                tv_role_name.append(")");
            }
            tv_number.setText(model.getClassCode());


            boolean IsWorker = model.isWorker();//是否是俱乐部工作人员，true-是，false-否
            choosePanel(IsWorker, role);

        }
        arrow.setTintColor(R.color.black);
        arrow.attachCustomSource(new ArrowSpinnerAdapter<ClassModel>(getContext(), classModels, R.layout.selector_class_item) {
            @Override
            public void convert(ViewHolder holder, ClassModel data, int position) {
                ImageView iv_icon = holder.getView(R.id.iv_icon);
                boolean selected = arrow.getSelectedIndex() == position;
                int icon = R.drawable.class_xueyuan;

                boolean isWorker = data.isWorker();//是否是俱乐部工作人员，true-是，false-否
                if (isWorker) {
                    icon = R.drawable.worker;
                }
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
                    case 4:
                        icon = R.drawable.class_xueyuan;
                        break;
                }
                iv_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
                TextView tv_number = holder.getView(R.id.tv_number);
                tv_number.setText("班级编号:" + data.getClassCode());
                TextView tv_class_name = holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data.getClassName());
                RadioButton iv_sel = holder.getView(R.id.iv_select);
                iv_sel.setChecked(selected);
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
                if (deleteClass != null) {
                    deleteClass.doSelected(model);
                }
                arrow.setSelected(i);
                int role = model.getClassRole();
                tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                //添加小组名字
                if (role != 1) {
                    tv_role_name.append("(");
                    tv_role_name.append(model.getCGName());
                    tv_role_name.append(")");
                }
                tv_number.setText(model.getClassCode());

                //切换班级，身份也随之改变
                classRole = model.getClassRole();
                //非 总教练可以修改身份
                if (classRole == 2 || classRole == 3 || classRole == 4) {
                    rl_change_role.setEnabled(true);
                } else {
                    rl_change_role.setEnabled(false);
                }

                boolean isWorker = model.isWorker();
                //没有角色的是工作人员的就显示工作人员
//                if (role != 1 && role != 2 && role != 3 && role != 4 && isWorker) {
//                    tv_role_name.setText("工作人员");
//                }
                choosePanel(isWorker, role);
            }
        });
        if (model != null) {
            arrow.setSelected(selected);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    private void choosePanel(boolean isWorker, int role) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("class", model);

        if (isWorker) {
            if (role != 1 && role != 2 && role != 3 && role != 4 && isWorker) {
                tv_role_name.setText("工作人员");
            }

            HeadCoachFragment headCoachFragment = new HeadCoachFragment();
            headCoachFragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction().replace(R.id.container, headCoachFragment).commitAllowingStateLoss();
        } else {
            StudentFragment studentFragment = new StudentFragment();
            studentFragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction().replace(R.id.container, studentFragment).commitAllowingStateLoss();
        }
//        switch (role) {
//            case 1://总教练
//                HeadCoachFragment headCoachFragment = new HeadCoachFragment();
//                headCoachFragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().replace(R.id.container, headCoachFragment).commitAllowingStateLoss();
//                break;
//            case 2://教练
//                CoachFragment coachFragment = new CoachFragment();
//                coachFragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().replace(R.id.container, coachFragment).commitAllowingStateLoss();
//                break;
//            case 3://助教
//                AssistantFragment assistantFragment = new AssistantFragment();
//                assistantFragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().replace(R.id.container, assistantFragment).commitAllowingStateLoss();
//                break;
//            case 4://学员
//                StudentFragment studentFragment = new StudentFragment();
//                studentFragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().replace(R.id.container, studentFragment).commitAllowingStateLoss();
//                break;
//        }
    }


    @Subscribe
    public void updateClass(UpdateClass clazz) {
        if (clazz.getStatus() == 0) {
            //更新班级姓名
            ClassModel model = clazz.getModel();
            arrow.setText(model.getClassName());
            this.model.setClassName(model.getClassName());
            arrow.getAdapter().notifyDataSetChanged();
        } else if (clazz.getStatus() == 1) {
            //添加新班级
            this.classModels.add(clazz.getModel());
            arrow.notifChange();
        } else if (clazz.getStatus() == 2) {
            //删除班级
            Iterator<ClassModel> iter = classModels.iterator();
            while (iter.hasNext()) {
                ClassModel model = iter.next();
                if (model.getClassId().equals(clazz.getModel().getClassId())) {
                    iter.remove();
                    arrow.notifChange();
                    break;
                }
            }
            if (classModels.isEmpty()) {
                if (deleteClass != null) {
                    deleteClass.deletClass(0);
                }
            } else {
                arrow.setSelected(0);
                this.model = classModels.get(0);
                int role = model.getClassRole();
                tv_role_name.setText(role == 1 ? "总教练" : role == 2 ? "教练" : role == 3 ? "助教" : role == 4 ? "学员" : "");
                //添加小组名字
                if (role != 1) {
                    tv_role_name.append("(");
                    tv_role_name.append(model.getCGName());
                    tv_role_name.append(")");
                }
                tv_number.setText(model.getClassCode());
                arrow.setTintColor(R.color.black);
                choosePanel(this.model.isWorker(), role);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change_role:
                //初始化班级角色
                classRoleList.clear();
                classRoleList.add(new ClassRole("教练", 2));
                classRoleList.add(new ClassRole("助教", 3));
                classRoleList.add(new ClassRole("学员", 4));
                showGroupName(false, new EasyAdapter<ClassRole>(getContext(), classRoleList, android.R.layout.simple_list_item_single_choice) {
                    @Override
                    public void convert(ViewHolder holder, ClassRole data, int position) {
                        CheckedTextView tv = holder.getView(android.R.id.text1);
                        tv.setText(data.getRoleName());
                        SpannableString ss = null;
                        if ("助教".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的助教及复测，摄影等工作人员)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        } else if ("教练".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的小组长)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        } else if ("学员".equals(data.getRoleName())) {
                            tv.append("\n");
                            ss = new SpannableString("(线下体管赛班级中的学员)");
                            ss.setSpan(new AbsoluteSizeSpan(12, true), 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                        if (ss != null) {
                            tv.append(ss);
                        }
                    }
                });

                break;
        }
    }

    private void showGroupName(final boolean isGroup, EasyAdapter adapter) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_trans_view, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv);
        tv_title.setText(isGroup ? "选择小组" : "选择角色");
        final ListView lv = (ListView) view.findViewById(R.id.lv);
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.trans_group_footer, null);
        lv.addFooterView(footer);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setItemChecked(isGroup ? checkedGroup : checkedRole, true);
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (lv.getFirstVisiblePosition() != 0) {
                            lv.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        TextView tv_ok = (TextView) footer.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedPosition = lv.getCheckedItemPosition();
                if (checkedPosition > -1) {
                    if (!isGroup) {
                        checkedRole = checkedPosition;
                        if (checkedPosition < classRoleList.size()) {
                            ClassRole role = classRoleList.get(checkedPosition);

                            if (classRole == role.getRoleId()) {
                                Util.toastMsg("您选择的角色和当前角色一致，无需修改");
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                return;
                            }

                            //向后台请求
                            updateClassRole(role);
                        }
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }
        });
        TextView tv_cancel = (TextView) footer.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }

    BottomSheetDialog dialog;
    int checkedGroup;
    int checkedRole;

    private void updateClassRole(final ClassRole classRole_model) {
        dialogShow("请稍候...");
        MoreService service = ZillaApi.NormalRestAdapter.create(MoreService.class);
        service.updateClassRole(UserInfoModel.getInstance().getToken(), UserInfoModel.getInstance().getUserId(), model.getClassId(), classRole_model.getRoleId(), new Callback<ResponseData>() {
            @Override
            public void success(ResponseData responseData, Response response) {
                dialogDissmiss();
                int status = responseData.getStatus();
                Util.toastMsg(responseData.getMsg());
                if (200 == status) {
                    tv_role_name.setText(classRole_model.getRoleName());
                    classRole = classRole_model.getRoleId();
                    //添加小组名字
                    tv_role_name.append("(");
                    tv_role_name.append(TextUtils.isEmpty(model.getCGName()) ? "未知" : model.getCGName());
                    tv_role_name.append(")");

                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialogDissmiss();
                ZillaApi.dealNetError(error);
            }
        });

    }

    protected CustomProgress progressDialog;

    private void dialogShow(String value) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = CustomProgress.build(getContext(), value);
            progressDialog.show();

        }
    }

    private void dialogDissmiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public interface DeleteClass {
        void deletClass(int classCount);

        void doSelected(ClassModel model);
    }

}
