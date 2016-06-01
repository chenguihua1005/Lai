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
import android.widget.ScrollView;
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
    @InjectView(R.id.tv_title)
            TextView tv_title;
    @InjectView(R.id.scroll_content)
    ScrollView scroll_content;
    @InjectView(R.id.tv_huode)
            TextView tv_huode;
    XuZhangAdapter xuZhangAdapter;
    XuZhangNullAdapter xuZhangNullAdapter;
    XunZhangListManager xunZhangListManager;
    List<Integer> images=new ArrayList<Integer>();
    List<Integer> images1=new ArrayList<Integer>();
    List<Integer> pknull=new ArrayList<Integer>();
    List<Integer> pkimage=new ArrayList<Integer>();
    List<String> content=new ArrayList<String>();
    List<String> content1=new ArrayList<String>();
    List<String> imgagecontent=new ArrayList<String>();
    List<String> imgagecontent1=new ArrayList<String>();
    private List<XunZhangModel> xunZhangModelList=new ArrayList<XunZhangModel>();
    private List<XunZhangModel> xunZhangModelList1=new ArrayList<XunZhangModel>();



    @Override
    protected void initViews() {
        tv_title.setText("我的勋章");
        ll_left.setOnClickListener(this);
        tv_huode.setFocusable(true);
        tv_huode.setFocusableInTouchMode(true);
        tv_huode.requestFocus();
        tv_huode.findFocus();
    }

    @Override
    protected void initDatas() {
        xunZhangListManager=new XunZhangListManager(this);
        xunZhangListManager.doGetXunZhang();
        MyGridView gridview1 = (MyGridView) findViewById(R.id.grid_view1);
        xuZhangAdapter=new XuZhangAdapter(this,xunZhangModelList,images1,content1,imgagecontent1);
        gridview1.setAdapter(xuZhangAdapter);
        MyGridView gridview2 = (MyGridView) findViewById(R.id.grid_view2);
        xuZhangNullAdapter=new XuZhangNullAdapter(this,xunZhangModelList1,images,content,imgagecontent);
        gridview2.setAdapter(xuZhangNullAdapter);



    }
    private void pkxunzhangList(int n)
    {
        switch (n)

        {
            case 0:
                pknull.add(R.drawable.darenjin,R.drawable.darenyin);
                pknull.add(R.drawable.darentong,R.drawable.starjin);
                pknull.add(R.drawable.staryin,R.drawable.startong);
                break;
            case 1:
                pknull.add(R.drawable.darenyin);
                pknull.add(R.drawable.darentong,R.drawable.starjin);
                pknull.add(R.drawable.staryin,R.drawable.startong);
                pkimage.add(R.drawable.darenjin);
                break;
            case 2:
                pknull.add(R.drawable.darentong,R.drawable.starjin);
                pknull.add(R.drawable.staryin,R.drawable.startong);
                pkimage.add(R.drawable.darenjin,R.drawable.darenyin);
                break;
            case 3:
                pknull.add(R.drawable.starjin);
                pknull.add(R.drawable.staryin,R.drawable.startong);
                pkimage.add(R.drawable.darenjin,R.drawable.darenyin);
                pkimage.add(R.drawable.darentong);
                break;
            case 4:
                pknull.add(R.drawable.staryin,R.drawable.startong);
                pkimage.add(R.drawable.darenjin,R.drawable.darenyin);
                pkimage.add(R.drawable.darentong,R.drawable.starjin);
                break;
            case 5:
                pknull.add(R.drawable.startong);
                pkimage.add(R.drawable.darenjin,R.drawable.darenyin);
                pkimage.add(R.drawable.darentong,R.drawable.starjin);
                pkimage.add(R.drawable.staryin);
                break;
            case 6:
                pkimage.add(R.drawable.darenjin,R.drawable.darenyin);
                pkimage.add(R.drawable.darentong,R.drawable.starjin);
                pkimage.add(R.drawable.staryin,R.drawable.startong);
                break;
        }


    }

    @Override
    public void getXunZhangList(XunZhangModel xunZhangModel) {
        if (!xunZhangModel.toString().isEmpty()) {
//            for (int i=0;i<14;i++)
//            {
////                xunZhangModelList1.add(xunZhangModel);
            int i=0,n=0,count=0,acount=0;
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

               switch (xunZhangModel.getPK().size())
               {
                   //未获得任何pk勋章
                   case 0:

                            images.add(i,R.drawable.darentong);
                            content.add(i++,"pk挑战成功1次");
                            images.add(i,R.drawable.darenyin);
                            content.add(i++,"pk挑战成功50次");
                            images.add(i,R.drawable.darenjin);
                            content.add(i++,"pk挑战成功100次");
                            images.add(i,R.drawable.startong);
                            content.add(i++,"pk挑战成功200次");
                            images.add(i,R.drawable.staryin);
                            content.add(i++,"pk挑战成功300次");
                            images.add(i,R.drawable.starjin);
                            content.add(i++,"pk挑战成功500次");
                            xunZhangModelList1.add(xunZhangModel);

                       break;
                   //获得任何1个pk勋章
                   case 1:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images.add(i,R.drawable.darenyin);
                       content.add(i++,"pk挑战成功50次");
                       images.add(i,R.drawable.darenjin);
                       content.add(i++,"pk挑战成功100次");
                       images.add(i,R.drawable.startong);
                       content.add(i++,"pk挑战成功200次");
                       images.add(i,R.drawable.staryin);
                       content.add(i++,"pk挑战成功300次");
                       images.add(i,R.drawable.starjin);
                       content.add(i++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;
                   //未获得任何2个pk勋章
                   case 2:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images1.add(n,R.drawable.darenyin);
                       content1.add(n++,"pk挑战成功50次");
                       images.add(i,R.drawable.darenjin);
                       content.add(i++,"pk挑战成功100次");
                       images.add(i,R.drawable.startong);
                       content.add(i++,"pk挑战成功200次");
                       images.add(i,R.drawable.staryin);
                       content.add(i++,"pk挑战成功300次");
                       images.add(i,R.drawable.starjin);
                       content.add(i++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;
                   //未获得任何3个pk勋章
                   case 3:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images1.add(n,R.drawable.darenyin);
                       content1.add(n++,"pk挑战成功50次");
                       images1.add(n,R.drawable.darenjin);
                       content1.add(n++,"pk挑战成功100次");
                       images.add(i,R.drawable.startong);
                       content.add(i++,"pk挑战成功200次");
                       images.add(i,R.drawable.staryin);
                       content.add(i++,"pk挑战成功300次");
                       images.add(i,R.drawable.starjin);
                       content.add(i++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;
                   //未获得任何4个pk勋章
                   case 4:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images1.add(n,R.drawable.darenyin);
                       content1.add(n++,"pk挑战成功50次");
                       images1.add(n,R.drawable.darenjin);
                       content1.add(n++,"pk挑战成功100次");
                       images1.add(n,R.drawable.startong);
                       content1.add(n++,"pk挑战成功200次");
                       images.add(i,R.drawable.staryin);
                       content.add(i++,"pk挑战成功300次");
                       images.add(i,R.drawable.starjin);
                       content.add(i++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;
                   //未获得任何5个pk勋章
                   case 5:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images1.add(n,R.drawable.darenyin);
                       content1.add(n++,"pk挑战成功50次");
                       images1.add(n,R.drawable.darenjin);
                       content1.add(n++,"pk挑战成功100次");
                       images1.add(n,R.drawable.startong);
                       content1.add(n++,"pk挑战成功200次");
                       images1.add(n,R.drawable.staryin);
                       content1.add(n++,"pk挑战成功300次");
                       images.add(i,R.drawable.starjin);
                       content.add(i++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;
                   //未获得任何6个pk勋章
                   case 6:
                       images1.add(n,R.drawable.darentong);
                       content1.add(n++,"pk挑战成功1次");
                       images1.add(n,R.drawable.darenyin);
                       content1.add(n++,"pk挑战成功50次");
                       images1.add(n,R.drawable.darenjin);
                       content1.add(n++,"pk挑战成功100次");
                       images1.add(n,R.drawable.startong);
                       content1.add(n++,"pk挑战成功200次");
                       images1.add(n,R.drawable.staryin);
                       content1.add(n++,"pk挑战成功300次");
                       images1.add(n,R.drawable.starjin);
                       content1.add(n++,"pk挑战成功500次");
                       xunZhangModelList1.add(xunZhangModel);
                       break;

            }
            if (xunZhangModel.getTotals().size()<6)
            {
                switch (xunZhangModel.getTotals().size())
                {
                    case 0:
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数10万步");
                        imgagecontent.add(count++,"10万");

                        images.add(i,R.drawable.fifty);
                        content.add(i++,"累计步数50万步");
                        imgagecontent.add(count++,"50万");

                        images.add(i,R.drawable.one_hundred);
                        content.add(i++,"累计步数100万步");
                        imgagecontent.add(count++,"100万");

                        images.add(i,R.drawable.one_hundred);
                        content.add(i++,"累计步数500万步");
                        imgagecontent.add(count++,"500万");

                        images.add(i,R.drawable.one_hundred);
                        content.add(i++,"累计步数1000万步");
                        imgagecontent.add(count++,"1000万");
                        break;
                    //获得任何1个pk勋章
                    case 1:
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数10万步");
                        imgagecontent1.add(acount++,"10万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数50万步");
                        imgagecontent.add(count++,"50万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数100万步");
                        imgagecontent.add(count++,"100万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数500万步");
                        imgagecontent.add(count++,"500万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数1000万步");
                        imgagecontent.add(count++,"1000万");
                        xunZhangModelList1.add(xunZhangModel);
                        break;
                    //未获得任何2个pk勋章
                    case 2:
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数10万步");
                        imgagecontent1.add(acount++,"10万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数50万步");
                        imgagecontent1.add(acount++,"50万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数100万步");
                        imgagecontent.add(count++,"100万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数500万步");
                        imgagecontent.add(count++,"500万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数1000万步");
                        imgagecontent.add(count++,"1000万");
                        xunZhangModelList1.add(xunZhangModel);
                        break;
                    //未获得任何3个pk勋章
                    case 3:
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数10万步");
                        imgagecontent1.add(acount++,"10万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数50万步");
                        imgagecontent1.add(acount++,"50万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数100万步");
                        imgagecontent1.add(acount++,"100万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数500万步");
                        imgagecontent.add(count++,"500万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数1000万步");
                        imgagecontent.add(count++,"1000万");
                        xunZhangModelList1.add(xunZhangModel);
                        break;
                    //未获得任何4个pk勋章
                    case 4:
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数10万步");
                        imgagecontent1.add(acount++,"10万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数50万步");
                        imgagecontent1.add(acount++,"50万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数100万步");
                        imgagecontent1.add(acount++,"100万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数500万步");
                        imgagecontent1.add(acount++,"500万");
                        images.add(i,R.drawable.ten);
                        content.add(i++,"累计步数1000万步");
                        imgagecontent.add(count++,"1000万");
                        xunZhangModelList1.add(xunZhangModel);
                        break;
                    //未获得任何5个pk勋章
                    case 5:
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数10万步");
                        imgagecontent1.add(acount++,"10万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数50万步");
                        imgagecontent1.add(acount++,"50万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数100万步");
                        imgagecontent1.add(acount++,"100万");
                        images1.add(n,R.drawable.ten);
                        content1.add(n++,"累计步数500万步");
                        imgagecontent1.add(acount++,"500万");
                        images1.add(i,R.drawable.ten);
                        content1.add(i++,"累计步数1000万步");
                        imgagecontent1.add(acount++,"1000万");
                        xunZhangModelList1.add(xunZhangModel);
                        break;

                }
            }
            else {

            }




//            }


            xuZhangAdapter.updateData(xunZhangModelList,images1,content1,imgagecontent1);
            xuZhangNullAdapter.updateData(xunZhangModelList1,images,content,imgagecontent);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_left:
                finish();
                break;
        }

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
