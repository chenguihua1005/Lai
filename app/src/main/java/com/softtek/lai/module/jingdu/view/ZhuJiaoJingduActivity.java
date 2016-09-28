/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softtek.lai.widgets.Chart;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame2.view.PersonalDataActivity;
import com.softtek.lai.module.grade.view.GradeHomeActivity;
import com.softtek.lai.module.jingdu.Adapter.RankAdapter;
import com.softtek.lai.module.jingdu.model.PaimingModel;
import com.softtek.lai.module.jingdu.model.SPModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.jingdu.presenter.GetProinfoImpl;
import com.softtek.lai.module.jingdu.presenter.IGetProinfopresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

//import com.umeng.socialize.bean.SocializeConfig;
//import com.umeng.socialize.sso.UMSsoHandler;

@InjectLayout(R.layout.activity_zhujiaojingdu)
public class ZhuJiaoJingduActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;

    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tv_right)
    TextView tv_right;

    @InjectView(R.id.list_rank)
    ListView list_rank;

    //班级布局
    @InjectView(R.id.ll_yue1)
    LinearLayout ll_yue1;
    @InjectView(R.id.ll_yue2)
    LinearLayout ll_yue2;
    @InjectView(R.id.ll_yue3)
    LinearLayout ll_yue3;

    @InjectView(R.id.tv_yue1)
    TextView tv_yue1;
    @InjectView(R.id.tv_yue2)
    TextView tv_yue2;
    @InjectView(R.id.tv_yue3)
    TextView tv_yue3;

    @InjectView(R.id.img_oneban)
    ImageView img_oneban;

    @InjectView(R.id.img_twoban)
    ImageView img_twoban;

    @InjectView(R.id.img_threeban)
    ImageView img_threeban;

    @InjectView(R.id.ll_oneban)
    LinearLayout ll_oneban;

    @InjectView(R.id.ll_twoban)
    LinearLayout ll_twoban;

    @InjectView(R.id.ll_threeban)
    LinearLayout ll_threeban;

    //班级累计减重数
    @InjectView(R.id.tv_oneban)
    TextView tv_oneban;

    @InjectView(R.id.tv_twoban)
    TextView tv_twoban;

    @InjectView(R.id.tv_threeban)
    TextView tv_threeban;

    //班级名称
    @InjectView(R.id.tv_classname1)
    TextView tv_classname1;

    @InjectView(R.id.tv_classname2)
    TextView tv_classname2;

    @InjectView(R.id.tv_classname3)
    TextView tv_classname3;

    @InjectView(R.id.total_weight)
    Chart total_weight;

    private List<Table1Model> table1ModelList = new ArrayList<Table1Model>();
    private List<PaimingModel> paimingModelList = new ArrayList<PaimingModel>();
    private IGetProinfopresenter iGetProinfopresenter;

    private SPModel spModel;
    public RankAdapter rankAdapter;

    //班级名称
    String onebanname;
    String twobanname;
    String threebanname;

    //班级累计减重数
    String oneban;
    String twoban;
    String threeban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //初始化排名序号
        initpaiming();
        rankAdapter = new RankAdapter(this, table1ModelList, paimingModelList);
        list_rank.setAdapter(rankAdapter);
        list_rank.setOnItemClickListener(this);
        ll_left.setOnClickListener(this);
    }

    private void initpaiming() {
        PaimingModel p1 = new PaimingModel(1);
        paimingModelList.add(p1);
        PaimingModel p2 = new PaimingModel(2);
        paimingModelList.add(p2);
        PaimingModel p3 = new PaimingModel(3);
        paimingModelList.add(p3);
        PaimingModel p4 = new PaimingModel(4);
        paimingModelList.add(p4);
        PaimingModel p5 = new PaimingModel(5);
        paimingModelList.add(p5);
        PaimingModel p6 = new PaimingModel(6);
        paimingModelList.add(p6);
        PaimingModel p7 = new PaimingModel(7);
        paimingModelList.add(p7);
        PaimingModel p8 = new PaimingModel(8);
        paimingModelList.add(p8);
        PaimingModel p9 = new PaimingModel(9);
        paimingModelList.add(p9);
        PaimingModel p10 = new PaimingModel(10);
        paimingModelList.add(p10);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initViews() {
        iGetProinfopresenter = new GetProinfoImpl();
        iGetProinfopresenter.getspproinfo();
    }

    @Override
    protected void initDatas() {
        tv_title.setText("当前进度");
    }

    @Subscribe
    public void onEvent(final SPModel spModel) {
        //Table1: 教练所有班级的所有学员减重最多的前10名学员
        table1ModelList = spModel.getTable1();
        rankAdapter.updateData(table1ModelList, paimingModelList);

        //Table2:各个班本月累计减重

        if (spModel.getTable2().size() == 1) {
            img_oneban.setVisibility(View.VISIBLE);
            // 需要解析的日期字符串
            String yue1 = spModel.getTable2().get(0).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(yue1);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int month = calendar.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            tv_classname1.setText("本月减重");
            tv_oneban.setText(oneban);
            float a = Float.parseFloat(oneban);
            total_weight.setValue(a, 0, 0);
            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(0).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
        } else if (spModel.getTable2().size() == 2) {
            String yue1 = spModel.getTable2().get(0).getStartDate();
            String yue2 = spModel.getTable2().get(1).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(yue1);
                Date date2 = format.parse(yue2);
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                calendar1.setTime(date1);
                calendar2.setTime(date2);
                int month1 = calendar1.get(Calendar.MONTH);
                int month2 = calendar2.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month1 + 1));
                ll_yue2.setVisibility(View.VISIBLE);
                tv_yue2.setText("" + (month2 + 1));

            } catch (Exception e) {
                e.printStackTrace();
            }


            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);

            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            twobanname = spModel.getTable2().get(1).getClassName();
            twoban = spModel.getTable2().get(1).getLoseWeight();
            tv_classname1.setText("本月减重");
            tv_oneban.setText(oneban);
            tv_classname2.setText("本月减重");
            tv_twoban.setText(twoban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            total_weight.setValue(a, b, 0);
            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(0).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_twoban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(1).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
        } else{

            String yue1 = spModel.getTable2().get(0).getStartDate();
            String yue2 = spModel.getTable2().get(1).getStartDate();
            String yue3 = spModel.getTable2().get(2).getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(yue1);
                Date date2 = format.parse(yue2);
                Date date3 = format.parse(yue3);
                Calendar calendar1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar3 = Calendar.getInstance();
                calendar1.setTime(date1);
                calendar2.setTime(date2);
                calendar3.setTime(date3);
                int month1 = calendar1.get(Calendar.MONTH);
                int month2 = calendar2.get(Calendar.MONTH);
                int month3 = calendar3.get(Calendar.MONTH);
                ll_yue1.setVisibility(View.VISIBLE);
                tv_yue1.setText("" + (month1 + 1));
                ll_yue2.setVisibility(View.VISIBLE);
                tv_yue2.setText("" + (month2 + 1));
                ll_yue3.setVisibility(View.VISIBLE);
                tv_yue3.setText("" + (month3 + 1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            img_oneban.setVisibility(View.VISIBLE);
            img_twoban.setVisibility(View.VISIBLE);
            img_threeban.setVisibility(View.VISIBLE);

            onebanname = spModel.getTable2().get(0).getClassName();
            oneban = spModel.getTable2().get(0).getLoseWeight();
            twobanname = spModel.getTable2().get(1).getClassName();
            twoban = spModel.getTable2().get(1).getLoseWeight();
            threebanname = spModel.getTable2().get(2).getClassName();
            threeban = spModel.getTable2().get(2).getLoseWeight();
            tv_classname1.setText("本月减重");
            tv_oneban.setText(oneban);
            tv_classname2.setText("本月减重");
            tv_twoban.setText(twoban);
            tv_classname3.setText("本月减重");
            tv_threeban.setText(threeban);
            float a = Float.parseFloat(oneban);
            float b = Float.parseFloat(twoban);
            float c = Float.parseFloat(threeban);
            total_weight.setValue(a, b, c);
            ll_oneban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(0).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_twoban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(1).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
            ll_threeban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String classId = spModel.getTable2().get(2).getClassId();
                    Intent intent = new Intent(ZhuJiaoJingduActivity.this, GradeHomeActivity.class);
                    intent.putExtra("classId", classId);
                    intent.putExtra("review", 1);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
            //分享功能逻辑
            case R.id.tv_right:
//                ShareUtils shareUtils = new ShareUtils(ZhuJiaoJingduActivity.this);
//                shareUtils.setShareContent("康宝莱体重管理挑战赛，坚持只为改变！", "http://www.baidu.com", R.drawable.logo, "我已成功在**天减重**斤，快来见证我的改变，和我一起参加体重管理挑战赛吧！", "我已成功在**天减重**斤，快来见证我的改变，和我一起参加体重管理挑战赛吧！");
//                shareUtils.getController().openShare(ZhuJiaoJingduActivity.this, false);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMSsoHandler ssoHandler = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }

    /*
     * listview的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Table1Model table1Model = table1ModelList.get(position);
        long userId = Long.parseLong(table1Model.getAccountId());
        long classId = Long.parseLong(table1Model.getClassId());
        Intent intent = new Intent(this, PersonalDataActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("classId", classId);
        startActivity(intent);
    }
}
