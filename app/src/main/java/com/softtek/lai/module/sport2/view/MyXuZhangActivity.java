package com.softtek.lai.module.sport2.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.sport2.adapter.XuZhangAdapter;
import com.softtek.lai.module.sport2.adapter.XuZhangNullAdapter;
import com.softtek.lai.module.sport2.model.XunZhangModel;
import com.softtek.lai.module.sport2.presenter.XunZhangListManager;
import com.softtek.lai.widgets.CustomGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_my_xu_zhang)
public class MyXuZhangActivity extends BaseActivity implements XunZhangListManager.XunZhangListCallback, View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_huode)
    TextView tv_huode;
    @InjectView(R.id.grid_view1)
    CustomGridView grid_view1;
    @InjectView(R.id.grid_view2)
    CustomGridView grid_view2;

    XuZhangAdapter xuZhangAdapter;
    XuZhangNullAdapter xuZhangNullAdapter;
    XunZhangListManager xunZhangListManager;
    List<Integer> images = new ArrayList<>();
    List<Integer> images1 = new ArrayList<>();
    List<String> content = new ArrayList<>();
    List<String> content1 = new ArrayList<>();


    @Override
    protected void initViews() {
        tv_title.setText("我的勋章");
        ll_left.setOnClickListener(this);
        tv_huode.setFocusable(true);
        tv_huode.setFocusableInTouchMode(true);
        tv_huode.requestFocus();
        tv_huode.findFocus();
        //设置girdview无点击效果
        grid_view1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        grid_view2.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void initDatas() {
        xunZhangListManager = new XunZhangListManager(this);
        xunZhangListManager.doGetXunZhang();
        xuZhangAdapter = new XuZhangAdapter(this, images1, content1);
        grid_view1.setAdapter(xuZhangAdapter);
        xuZhangNullAdapter = new XuZhangNullAdapter(this, images, content);
        grid_view2.setAdapter(xuZhangNullAdapter);


    }


    //勋章接口回调
    @Override
    public void getXunZhangList(XunZhangModel xunZhangModel) {
        try {
            //爱心天使勋章是否获得
            if (xunZhangModel.getAngle().equals("0")) {
                images.add(R.drawable.jijinno);//爱心天使勋章图片
                content.add("康宝莱公益基金会");
            } else {
                images1.add(R.drawable.jijin);//爱心天使勋章图片
                content1.add("康宝莱公益基金会");
            }
            if (xunZhangModel!=null) {
                //images是存放未获得勋章，content存放未获得勋章文字内容
                //images1是存放获得勋章，content1存放获得勋章文字内容
                //3天勋章是否获得
                if (xunZhangModel.getThreeDays().equals("0")) {
                    images.add(R.drawable.img_three_none);//3天勋章图片
                    content.add("连续3天步数1万");
                } else {
                    images1.add(R.drawable.img_three_have);//3天勋章图片
                    content1.add("连续3天步数1万");
                }
                //7天勋章是否获得
                if (xunZhangModel.getSevenDays().equals("0")) {

                    images.add(R.drawable.img_seven_none);//7天勋章图片
                    content.add("连续7天步数1万");
                } else {
                    images1.add(R.drawable.img_seven_have);//7天勋章图片
                    content1.add("连续7天步数1万");
                }
                //21天勋章是否获得
                if (xunZhangModel.getTwentyOneDays().equals("0")) {

                    images.add(R.drawable.img_twenty_one_none);//21天勋章图片
                    content.add("连续21天步数1万");
                } else {
                    images1.add(R.drawable.img_twenty_one_have);//21天勋章图片
                    content1.add("连续21天步数1万");
                }
                //30天勋章是否获得
                if (xunZhangModel.getThirtyDays().equals("0")) {

                    images.add(R.drawable.img_thirty_none);//30天勋章图片
                    content.add("连续30天步数1万");
                } else {
                    images1.add(R.drawable.img_thirty_have);//30天勋章图片
                    content1.add("连续30天步数1万");
                }
                //100天勋章是否获得
                if (xunZhangModel.getOneHundredDays().equals("0")) {
                    images.add(R.drawable.img_hundred_day_none);//100天勋章图片
                    content.add("连续100天步数1万");
                } else {
                    images1.add(R.drawable.img_hundred_day_have);//100天勋章图片
                    content1.add("连续100天步数1万");
                }
                //200天勋章是否获得
                if (xunZhangModel.getTwoHundredyDays().equals("0")) {

                    images.add(R.drawable.img_day200_none);//200天勋章图片
                    content.add("连续200天步数1万");
                } else {
                    images1.add(R.drawable.img_day200_have);//200天勋章图片
                    content1.add("连续200天步数1万");
                }
                //365天勋章是否获得
                if (xunZhangModel.getOneYearDays().equals("0")) {
                    images.add(R.drawable.img_day365_none);//365天勋章图片
                    content.add("连续365天步数1万");
                } else {
                    images1.add(R.drawable.img_day365_have);//365天勋章图片
                    content1.add("连续3天步数1万");
                }


                Map<Integer, String> map_pk = new HashMap<>();
                List<String> pk_list = xunZhangModel.getPK();
                int pk_size = pk_list.size();
                for (int i = 0; i < 6; i++) {
                    if (i < pk_size) {
                        map_pk.put(i, "1");              //标记已获得该勋章
                    } else {
                        map_pk.put(i, "0");              //标记未获得该勋章
                    }
                }
                if (map_pk.get(0).equals("0")) {
                    images.add(R.drawable.img_pk_1_none);//pk成功一次挑战达人铜牌勋章
                    content.add("pk挑战成功1次");
                } else {
                    images1.add(R.drawable.img_pk_1_have);//pk成功一次挑战达人铜牌勋章
                    content1.add("pk挑战成功1次");
                }
                if (map_pk.get(1).equals("0")) {
                    images.add(R.drawable.img_pk_50_none);//pk成功50次挑战达人铜牌勋章
                    content.add("pk挑战成功50次");
                } else {
                    images1.add(R.drawable.img_pk_50_have);//pk成功50次挑战达人铜牌勋章
                    content1.add("pk挑战成功50次");
                }
                if (map_pk.get(2).equals("0")) {
                    images.add(R.drawable.img_pk_100_none);//pk挑战成功100次
                    content.add("pk挑战成功100次");
                } else {
                    images1.add(R.drawable.img_pk_100_have);//pk挑战成功100次
                    content1.add("pk挑战成功100次");
                }
                if (map_pk.get(3).equals("0")) {
                    images.add(R.drawable.img_pk_none);//pk挑战成功200次
                    content.add("pk挑战成功200次");
                } else {
                    images1.add(R.drawable.img_pk_200_have);//pk挑战成功200次
                    content1.add("pk挑战成功200次");
                }
                if (map_pk.get(4).equals("0")) {
                    images.add(R.drawable.img_pk_none);//pk挑战成功300次
                    content.add("pk挑战成功300次");
                } else {
                    images1.add(R.drawable.img_pk_300_have);//pk挑战成功300次
                    content1.add("pk挑战成功300次");
                }
                if (map_pk.get(5).equals("0")) {
                    images.add(R.drawable.img_pk_none);//pk挑战成功500次
                    content.add("pk挑战成功500次");
                } else {
                    images1.add(R.drawable.img_pk_500_have);//pk挑战成功500次
                    content1.add("pk挑战成功500次");
                }

                Map<Integer, String> map_total = new HashMap<>();
                List<String> total_list = xunZhangModel.getTotals();
                int total_size = total_list.size();
                for (int i = 0; i < 8; i++) {
                    if (i < total_size) {
                        map_total.put(i, "1");              //标记已获得该勋章
                    } else {
                        map_total.put(i, "0");              //标记未获得该勋章
                    }
                }
                if (map_total.get(0).equals("0")) {
                    images.add(R.drawable.img_total_10_none);//累计步数10万步
                    content.add("累计步数10万");
                } else {
                    images1.add(R.drawable.img_total_10_have);//累计步数10万步
                    content1.add("累计步数10万");
                }

                if (map_total.get(1).equals("0")) {
                    images.add(R.drawable.img_total_50_none);//累计步数50万步
                    content.add("累计步数50万");
                } else {
                    images1.add(R.drawable.img_total_50_have);//累计步数50万步
                    content1.add("累计步数50万");
                }
                if (map_total.get(2).equals("0")) {
                    images.add(R.drawable.img_total_100_none);//累计步数100万步
                    content.add("累计步数100万");
                } else {
                    images1.add(R.drawable.img_total_100_have);//累计步数100万步
                    content1.add("累计步数100万");
                }
                if (map_total.get(3).equals("0")) {
                    images.add(R.drawable.img_total_200_none);//累计步数200万步
                    content.add("累计步数200万");
                } else {
                    images1.add(R.drawable.img_total_200_have);//累计步数200万步
                    content1.add("累计步数200万");
                }
                if (map_total.get(4).equals("0")) {
                    images.add(R.drawable.img_total_500_none);//累计步数500万步
                    content.add("累计步数500万");
                } else {
                    images1.add(R.drawable.img_total_500_have);//累计步数500万步
                    content1.add("累计步数500万");
                }
                if (map_total.get(5).equals("0")) {
                    images.add(R.drawable.img_total_1000_none);//累计步数1000万步
                    content.add("累计步数1000万");
                } else {
                    images1.add(R.drawable.img_total_1000_have);//累计步数1000万步
                    content1.add("累计步数1000万");
                }
                if (map_total.get(6).equals("0")) {
                    images.add(R.drawable.img_total_2000_none);//累计步数2000万步
                    content.add("累计步数2000万");
                } else {
                    images1.add(R.drawable.img_total_2000_have);//累计步数2000万步
                    content1.add("累计步数2000万");
                }
                if (map_total.get(7).equals("0")) {
                    images.add(R.drawable.img_total_3000_none);//累计步数3000万步
                    content.add("累计步数3000万");
                } else {
                    images1.add(R.drawable.img_total_3000_have);//累计步数3000万步
                    content1.add("累计步数3000万");
                }

                xuZhangAdapter.updateData(images1, content1);
                xuZhangNullAdapter.updateData(images, content);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                finish();
                break;
        }

    }

//    private class ImageAdapter extends BaseAdapter {
//        private Context mContext;
//
//        public ImageAdapter(Context context) {
//            this.mContext = context;
//        }
//
//        @Override
//        public int getCount() {
//            return mThumbIds.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mThumbIds[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return 0;
//        }
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            //定义一个ImageView,显示在GridView里
//            ImageView imageView;
//            if (convertView == null) {
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setPadding(8, 8, 8, 8);
//            } else {
//                imageView = (ImageView) convertView;
//            }
//            imageView.setImageResource(mThumbIds[position]);
//
//            return imageView;
//        }
//
//
//    }

    //展示图片
//    private Integer[] mThumbIds = {
//            R.drawable.three, R.drawable.senven,
//            R.drawable.twenty_one, R.drawable.thirty,
//            R.drawable.ten, R.drawable.one_hundred,
//            R.drawable.hundred_day, R.drawable.fifty,
//            R.drawable.day200, R.drawable.wan200,
//            R.drawable.day365, R.drawable.wan500,
//            R.drawable.three, R.drawable.senven,
//            R.drawable.twenty_one, R.drawable.thirty,
//            R.drawable.ten, R.drawable.one_hundred,
//            R.drawable.hundred_day, R.drawable.fifty,
//            R.drawable.day200, R.drawable.wan200,
//            R.drawable.three, R.drawable.senven,
//            R.drawable.twenty_one, R.drawable.thirty,
//            R.drawable.ten, R.drawable.one_hundred,
//            R.drawable.hundred_day, R.drawable.fifty,
//            R.drawable.day200, R.drawable.wan200,
//            R.drawable.day365, R.drawable.wan500,
//            R.drawable.three, R.drawable.senven,
//            R.drawable.twenty_one, R.drawable.thirty,
//            R.drawable.ten, R.drawable.one_hundred,
//            R.drawable.hundred_day, R.drawable.fifty,
//            R.drawable.day200, R.drawable.wan200
//    };

}
