package com.softtek.lai.module.bodygame3.more.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.home.event.UpdateClass;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup;
import com.softtek.lai.module.bodygame3.more.model.ClassGroup2;
import com.softtek.lai.module.bodygame3.more.model.ClassModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.SharedPreferenceService;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

import static com.softtek.lai.R.id.tv_delete;

@InjectLayout(R.layout.activity_class_manager)
public class ClassManagerActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.rl_update_class)
    RelativeLayout rl_update_class;
    @InjectView(R.id.rl_person_manager)
    RelativeLayout rl_person_manager;
    @InjectView(R.id.rl_add_group)
    RelativeLayout rl_add_group;
    @InjectView(R.id.rl_update_fuce)
    RelativeLayout rl_update_fuce;
    @InjectView(R.id.tv_class_name)
    TextView tv_class_name;
    @InjectView(R.id.rl_closs_class)
    RelativeLayout rl_closs_class;

    @InjectView(R.id.lv)
    ListView lv;
    List<ClassGroup2> groups = new ArrayList<>();
    EasyAdapter<ClassGroup2> adapter;

    String classId;
    String className;
    ClassModel classModel;

    @Override
    protected void initViews() {
        tv_title.setText("班级管理");
        ll_left.setOnClickListener(this);
        rl_add_group.setOnClickListener(this);
        rl_person_manager.setOnClickListener(this);
        rl_update_class.setOnClickListener(this);
        rl_update_fuce.setOnClickListener(this);
        rl_closs_class.setOnClickListener(this);
        classModel = getIntent().getParcelableExtra("class");
        className = classModel.getClassName();
        classId = classModel.getClassId();

    }

    @Override
    protected void initDatas() {
        tv_class_name.setText(className);
        adapter = new EasyAdapter<ClassGroup2>(this, groups, R.layout.item_class_group) {
            @Override
            public void convert(ViewHolder holder, ClassGroup2 data, final int position) {
                TextView tv_groupName = holder.getView(R.id.tv_group_name);
                tv_groupName.setText(data.getClassGroupName());
                TextView tv_serial = holder.getView(R.id.tv_serial);
                tv_serial.setText(String.valueOf(position + 1));
                //侧滑操作
                final HorizontalScrollView hsv = holder.getView(R.id.hsv);
                TextView tv_editor = holder.getView(R.id.tv_editor);
                tv_editor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //关闭
                        hsv.post(new Runnable() {
                            @Override
                            public void run() {
                                hsv.smoothScrollTo(0, 0);
                            }
                        });
                        Intent updateGroupIntent = new Intent(ClassManagerActivity.this, EditorTextOlineActivity.class);
                        updateGroupIntent.putExtra("flag", EditorTextActivity.UPDATE_GROUP_NAME);
                        updateGroupIntent.putExtra("position", position);
                        updateGroupIntent.putExtra("classId", classId);
                        updateGroupIntent.putExtra("name", groups.get(position).getClassGroupName());
                        updateGroupIntent.putExtra("groupId", groups.get(position).getClassGoupId());
                        startActivityForResult(updateGroupIntent, 102);
                    }
                });
                TextView tv_delete = holder.getView(R.id.tv_delete);
//                if(groups.size()!=1&&data.getGrouperCount()==0){
                if (data.isCanBeDeleted()) {
                    tv_delete.setVisibility(View.VISIBLE);
                    tv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClassGroup2 group2 = groups.get(position);
                            ZillaApi.NormalRestAdapter.create(MoreService.class)
                                    .deleteGroup(UserInfoModel.getInstance().getToken(),
                                            group2.getClassGoupId(),
                                            new RequestCallback<ResponseData>() {
                                                @Override
                                                public void success(ResponseData responseData, Response response) {
                                                    if (responseData.getStatus() == 200) {
                                                        groups.remove(position);
                                                        adapter.notifyDataSetChanged();
                                                        int count = adapter.getCount();
                                                        setListViewHeight(count);
                                                    } else {
                                                        Util.toastMsg(responseData.getMsg());
                                                    }
                                                }

                                            });

                        }
                    });
                } else {
                    tv_delete.setVisibility(View.GONE);
                }

                RelativeLayout container = holder.getView(R.id.rl_container);
                ViewGroup.LayoutParams params = container.getLayoutParams();
                params.width = DisplayUtil.getMobileWidth(ClassManagerActivity.this);
                container.setLayoutParams(params);
                final LinearLayout ll_operation = holder.getView(R.id.ll_operation);
                hsv.setOnTouchListener(new View.OnTouchListener() {
                    int dx;
                    int lastX;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                lastX = (int) motionEvent.getX();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                dx = (int) motionEvent.getX() - lastX;
                                break;
                            case MotionEvent.ACTION_UP:
                                int width = ll_operation.getWidth() / 2;
                                boolean show = dx < 0 ? dx <= -width : !(dx >= width);
                                if (show) {
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(hsv.getMaxScrollAmount(), 0);
                                        }
                                    });

                                } else {
                                    hsv.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            hsv.smoothScrollTo(0, 0);
                                        }
                                    });

                                }
                                break;
                        }
                        return false;
                    }
                });

            }
        };
        lv.setAdapter(adapter);
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getClassGroupsInfo(classId, UserInfoModel.getInstance().getToken(),
                        classId,
                        new RequestCallback<ResponseData<List<ClassGroup2>>>() {
                            @Override
                            public void success(ResponseData<List<ClassGroup2>> data, Response response) {
                                if (data.getStatus() == 200) {
                                    try {
                                        groups.addAll(data.getData());
                                        adapter.notifyDataSetChanged();
                                        int count = adapter.getCount();
                                        setListViewHeight(count);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
    }

    private void setListViewHeight(int count) {
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = count * DisplayUtil.dip2px(ClassManagerActivity.this, 50)
                + (lv.getDividerHeight() * (count - 1));
        lv.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                finish();
                break;
            case R.id.rl_add_group: {
                Intent intent = new Intent(this, EditorTextOlineActivity.class);
                intent.putExtra("flag", EditorTextOlineActivity.ADD_GROUP_NAME);
                intent.putExtra("classId", classId);
                startActivityForResult(intent, 100);
            }
            break;
            case R.id.rl_person_manager: {
                Intent intent = new Intent(this, ClassMemberActivity.class);
                intent.putExtra("classId", classId);
                intent.putExtra("classHxId", classModel.getHXGroupId());
                startActivity(intent);
            }
            break;
            case R.id.rl_update_class: {
                Intent intent = new Intent(this, EditorTextOlineActivity.class);
                intent.putExtra("flag", EditorTextOlineActivity.UPDATE_CLASS_NAME);
                intent.putExtra("classId", classId);
                intent.putExtra("classHxId", classModel.getHXGroupId());
                intent.putExtra("name", className);
                startActivityForResult(intent, 101);
            }
            break;
            case R.id.rl_update_fuce: {
                Intent intent = new Intent(this, UpdateFuceTimeActivity.class);
                intent.putExtra("classId", classId);
                startActivity(intent);
            }
            break;
            case R.id.rl_closs_class: {
                new AlertDialog.Builder(this)
                        .setTitle("温馨提示")
                        .setMessage("是否确定要关闭当前班级？班级关闭后将无法再次开启哦!")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogShow("正在解散");
                                ZillaApi.NormalRestAdapter.create(MoreService.class)
                                        .shutDownClass(classId, UserInfoModel.getInstance().getToken(),
                                                classId,
                                                new RequestCallback<ResponseData>() {
                                                    @Override
                                                    public void success(ResponseData responseData, Response response) {
                                                        dialogDissmiss();
                                                        Log.i("关闭班级+" + responseData.toString());
                                                        if (responseData.getStatus() == 200) {
                                                            EventBus.getDefault().post(new UpdateClass(2, classModel));
                                                            String classId = SharedPreferenceService.getInstance().get("default_classId", "-1");
                                                            if (!classId.equals("-1") && classId.equals(classModel.getClassId())) {
                                                                SharedPreferenceService.getInstance().put("default_classId", "-1");
                                                            }
                                                            finish();
                                                        } else {
                                                            Util.toastMsg(responseData.getMsg());
                                                        }
                                                    }

                                                    @Override
                                                    public void failure(RetrofitError error) {
                                                        dialogDissmiss();
                                                        super.failure(error);
                                                    }
                                                });
                            }
                        }).show();

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                ClassGroup group = data.getParcelableExtra("group");
                ClassGroup2 group2 = new ClassGroup2();
                group2.setClassGoupId(group.getCGId());
                group2.setClassGroupName(group.getCGName());
                group2.setCanBeDeleted(true);//设置可以被删除
                groups.add(group2);
                adapter.notifyDataSetChanged();
                int count = adapter.getCount();
                setListViewHeight(count);
            } else if (requestCode == 101) {
                String value = data.getStringExtra("value");
                tv_class_name.setText(value);
                className = value;
                classModel.setClassName(value);
                EventBus.getDefault().post(new UpdateClass(0, classModel));
            } else if (requestCode == 102) {
                String value = data.getStringExtra("value");
                int position = data.getIntExtra("position", -1);
                if (position >= 0 && position < groups.size()) {
                    ClassGroup2 group2 = groups.get(position);
                    group2.setClassGroupName(value);
                    groups.set(position, group2);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
