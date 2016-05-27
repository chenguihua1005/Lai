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
public class MyXuZhangActivity extends BaseActivity implements XunZhangListManager.XunZhangListCallback {

    XuZhangAdapter xuZhangAdapter;
    XuZhangNullAdapter xuZhangNullAdapter;
    XunZhangListManager xunZhangListManager;
    private List<XunZhangModel> xunZhangModelList=new ArrayList<XunZhangModel>();
    private List<XunZhangModel> xunZhangModelList1=new ArrayList<XunZhangModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        xunZhangListManager=new XunZhangListManager(this);
        xunZhangListManager.doGetXunZhang();
        MyGridView gridview1 = (MyGridView) findViewById(R.id.grid_view1);
        xuZhangAdapter=new XuZhangAdapter(this,xunZhangModelList);
        gridview1.setAdapter(xuZhangAdapter);
        MyGridView gridview2 = (MyGridView) findViewById(R.id.grid_view2);
        xuZhangNullAdapter=new XuZhangNullAdapter(this,xunZhangModelList1);
        gridview2.setAdapter(xuZhangNullAdapter);
//        MyGridView gridview2 = (MyGridView) findViewById(R.id.grid_view2);
//        gridview2.setAdapter(new ImageAdapter(this));
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
            xunZhangModelList.add(xunZhangModel);
            xuZhangAdapter.updateData(xunZhangModelList);
            xunZhangModelList1.add(xunZhangModel);
            xunZhangModelList1.add(xunZhangModel);
            xunZhangModelList1.add(xunZhangModel);
            xuZhangNullAdapter.updateData(xunZhangModelList1);

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
