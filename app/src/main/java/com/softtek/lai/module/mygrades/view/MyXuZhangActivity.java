package com.softtek.lai.module.mygrades.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.mygrades.adapter.XuZhangAdapter;
import com.softtek.lai.module.mygrades.adapter.XuZhangNullAdapter;
import com.softtek.lai.module.mygrades.model.XunZhangModel;
import com.softtek.lai.module.mygrades.presenter.XunZhangListManager;
import com.softtek.lai.module.retest.adapter.ClassAdapter;
import com.softtek.lai.module.retest.adapter.StudentAdapter;
import com.softtek.lai.module.retest.model.BanjiModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;
@InjectLayout(R.layout.activity_my_xu_zhang)
public class MyXuZhangActivity extends BaseActivity implements XunZhangListManager.XunZhangListCallback,View.OnClickListener {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    XuZhangAdapter xuZhangAdapter;
    XuZhangNullAdapter xuZhangNullAdapter;
    XunZhangListManager xunZhangListManager;
    List<Integer> images=new ArrayList<Integer>();
    List<Integer> images1=new ArrayList<Integer>();
    List<String> content=new ArrayList<String>();
    List<String> content1=new ArrayList<String>();
    private List<XunZhangModel> xunZhangModelList=new ArrayList<XunZhangModel>();
    private List<XunZhangModel> xunZhangModelList1=new ArrayList<XunZhangModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        ll_left.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        xunZhangListManager=new XunZhangListManager(this);
        xunZhangListManager.doGetXunZhang();
        MyGridView gridview1 = (MyGridView) findViewById(R.id.grid_view1);
        xuZhangAdapter=new XuZhangAdapter(this,xunZhangModelList,images1,content1);
        gridview1.setAdapter(xuZhangAdapter);
        MyGridView gridview2 = (MyGridView) findViewById(R.id.grid_view2);
        xuZhangNullAdapter=new XuZhangNullAdapter(this,xunZhangModelList1,14,images,content);
        gridview2.setAdapter(xuZhangNullAdapter);


//        GridView gridView=(GridView)findViewById(R.id.gridview);
//        gridView.setAdapter(new ImageAdapter(this));
//        GridView gridView2=(GridView)findViewById(R.id.gridview2);
//        gridView2.setAdapter(new ImageAdapter(this));
        //单击GridView元素的响应
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                //弹出单击的GridView元素的位置
//                Toast.makeText(MyXuZhangActivity.this,mThumbIds[position], Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public void getXunZhangList(XunZhangModel xunZhangModel) {
        if (!xunZhangModel.toString().isEmpty()) {
//            for (int i=0;i<14;i++)
//            {
////                xunZhangModelList1.add(xunZhangModel);
            int i=0,n=0;
            //3天
                if (xunZhangModel.getThreeDays().equals("0")) {
                    images.add(i,R.drawable.three);
                    content.add(i,"连续3天1万步");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
            else {
                    images1.add(n,R.drawable.three);
                    content1.add(n,"连续3天1万步");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            //7天
                if (xunZhangModel.getSevenDays().equals("0"))
                {
                    mThumbIds[i] = new Integer(R.drawable.three);
                    images.add(i,R.drawable.senven);
                    content.add(i,"连续7天1万步");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.senven);
                    content1.add(n,"连续7天1万步");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            //21天
                if (xunZhangModel.getTwentyOneDays().equals("0"))
                {

                    images.add(i,R.drawable.twenty_one);
                    content.add(i,"连续21天1万步");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.twenty_one);
                    content1.add(n,"连续21天1万步");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            //30天
                if (xunZhangModel.getThirtyDays().equals("0"))
                {

                    images.add(i,R.drawable.thirty);
                    content.add(i,"连续30天1万步");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.thirty);
                    content1.add(n,"连续30天1万步");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            //100天
                if (xunZhangModel.getOneHundredDays().equals("0"))
                {
                    images.add(i,R.drawable.hundred_day);
                    content.add(i,"连续100天1万步");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.hundred_day);
                    content1.add(n,"连续100天1万步");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            //200天
            if (xunZhangModel.getTwoHundredyDays().equals("0"))
            {
                mThumbIds[i] = new Integer(R.drawable.three);
                images.add(i,R.drawable.day200);
                content.add(i,"连续200天1万步");
                xunZhangModelList1.add(xunZhangModel);
                i++;
            }
            else {
                images1.add(n,R.drawable.day200);
                content1.add(n,"连续200天1万步");
                xunZhangModelList.add(xunZhangModel);
                n++;
            }
            //365天
            if (xunZhangModel.getOneYearDays().equals("0"))
            {
                mThumbIds[i] = new Integer(R.drawable.three);
                images.add(i,R.drawable.day365);
                content.add(i,"连续365天1万步");
                xunZhangModelList1.add(xunZhangModel);
                i++;
            }
            else {
                images1.add(n,R.drawable.day365);
                content1.add(n,"连续3天1万步");
                xunZhangModelList.add(xunZhangModel);
                n++;
            }
            if (xunZhangModel.getAngle().equals("0"))
            {
                images.add(i,R.drawable.angel);
                content.add(i,"爱心天使");
                xunZhangModelList1.add(xunZhangModel);
                i++;
            }
            else {
                images1.add(n,R.drawable.angel);
                content1.add(n,"爱心天使");
                xunZhangModelList.add(xunZhangModel);
                n++;
            }
            if (xunZhangModel.getPK().size()==0)
            {
                images.add(i,R.drawable.darentong);
                content.add(i++,"pk挑战成功1次");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.darenyin);
                content.add(i++,"pk挑战成功50次");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.darenjin);
                content.add(i++,"pk挑战成功100次");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.startong);
                content.add(i++,"pk挑战成功200次");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.staryin);
                content.add(i++,"pk挑战成功300次");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.starjin);
                content.add(i++,"pk挑战成功500次");
                xunZhangModelList1.add(xunZhangModel);
            }
            if (xunZhangModel.getTotals().size()==0)
            {
                images.add(i,R.drawable.ten);
                content.add(i++,"累计步数10万步");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.fifty);
                content.add(i++,"累计步数50万步");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.one_hundred);
                content.add(i++,"累计步数100万步");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.one_hundred);
                content.add(i++,"累计步数500万步");
                xunZhangModelList1.add(xunZhangModel);
                images.add(i,R.drawable.one_hundred);
                content.add(i++,"累计步数1000万步");
                xunZhangModelList1.add(xunZhangModel);
            }

            for (int totalcount=0;totalcount<xunZhangModel.getTotals().size();totalcount++)
            {
                if (!xunZhangModel.getTotals().get(totalcount).equals("10"))
                {
                    images.add(i,R.drawable.ten);
                    content.add(i,"累计步数10万");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.ten);
                    content1.add(n,"累计步数10万");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
                if (!xunZhangModel.getTotals().get(totalcount).equals("50"))
                {
                    images.add(i,R.drawable.fifty);
                    content.add(i,"累计步数50万");
                    xunZhangModelList1.add(xunZhangModel);
                    i++;
                }
                else {
                    images1.add(n,R.drawable.fifty);
                    content1.add(n,"累计步数50万");
                    xunZhangModelList.add(xunZhangModel);
                    n++;
                }
            }

//            }


            xuZhangAdapter.updateData(xunZhangModelList,images1,content1);
//            MyGridView gridview1 = (MyGridView) findViewById(R.id.grid_view1);
//            gridview1.setAdapter(new ImageAdapter(this));
            xuZhangNullAdapter.updateData(xunZhangModelList1,images,content);
        }

    }

    @Override
    public void onClick(View v) {

    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context context) {
            this.mContext=context;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);

            return imageView;
        }



    }
    //展示图片
    private Integer[] mThumbIds = {
            R.drawable.three, R.drawable.senven,
            R.drawable.twenty_one, R.drawable.thirty,
            R.drawable.ten, R.drawable.one_hundred,
            R.drawable.hundred_day, R.drawable.fifty,
            R.drawable.day200, R.drawable.wan200,
            R.drawable.day365, R.drawable.wan500,
            R.drawable.three, R.drawable.senven,
            R.drawable.twenty_one, R.drawable.thirty,
            R.drawable.ten, R.drawable.one_hundred,
            R.drawable.hundred_day, R.drawable.fifty,
            R.drawable.day200, R.drawable.wan200,
            R.drawable.three, R.drawable.senven,
            R.drawable.twenty_one, R.drawable.thirty,
            R.drawable.ten, R.drawable.one_hundred,
            R.drawable.hundred_day, R.drawable.fifty,
            R.drawable.day200, R.drawable.wan200,
            R.drawable.day365, R.drawable.wan500,
            R.drawable.three, R.drawable.senven,
            R.drawable.twenty_one, R.drawable.thirty,
            R.drawable.ten, R.drawable.one_hundred,
            R.drawable.hundred_day, R.drawable.fifty,
            R.drawable.day200, R.drawable.wan200
    };

}
