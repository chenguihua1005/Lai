package com.softtek.lai.module.pastreview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.pastreview.model.ClassListModel;
import com.softtek.lai.module.pastreview.model.MyPhotoListItemModel;
import com.softtek.lai.module.pastreview.model.MyPhotoListModel;
import com.softtek.lai.utils.ChMonth;
import com.softtek.lai.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class MyPhotoListAdapter extends BaseAdapter {
    private Context context;
    DateUtil dateUtil;
    private List<MyPhotoListItemModel> myPhotoListItemModelList=new ArrayList<MyPhotoListItemModel>();

    public MyPhotoListAdapter(Context context, List<MyPhotoListItemModel> myPhotoListItemModelList) {
        this.context = context;
        this.myPhotoListItemModelList = myPhotoListItemModelList;
    }

    @Override
    public int getCount() {
        return myPhotoListItemModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return myPhotoListItemModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int leng=myPhotoListItemModelList.size();
        Log.i("chang>>>>",leng+"");
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_myphoto_listitem,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        MyPhotoListItemModel myPhotoListItemModel=myPhotoListItemModelList.get(position);
        String path = AddressManager.get("photoHost");
        String day="26";
        String month="二月";
        ChMonth chMonth=new ChMonth();
        switch (myPhotoListItemModel.getImgUrl().size())
        {
            case 0:
                viewHolder.re_mulpic.setVisibility(View.GONE);
                viewHolder.ll_singlepic.setVisibility(View.VISIBLE);
                break;
            case 1:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.im_pic2.setVisibility(View.GONE);
                viewHolder.im_pic3.setVisibility(View.GONE);
                viewHolder.im_pic4.setVisibility(View.GONE);
                viewHolder.im_pic5.setVisibility(View.GONE);
                viewHolder.im_pic6.setVisibility(View.GONE);
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text2.setVisibility(View.GONE);
                viewHolder.re_text3.setVisibility(View.GONE);
                viewHolder.re_text4.setVisibility(View.GONE);
                viewHolder.re_text5.setVisibility(View.GONE);
                viewHolder.re_text6.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.im_pic3.setVisibility(View.GONE);
                viewHolder.im_pic4.setVisibility(View.GONE);
                viewHolder.im_pic5.setVisibility(View.GONE);
                viewHolder.im_pic6.setVisibility(View.GONE);
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text3.setVisibility(View.GONE);
                viewHolder.re_text4.setVisibility(View.GONE);
                viewHolder.re_text5.setVisibility(View.GONE);
                viewHolder.re_text6.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.tv_photoweight3.setText("体重1");
                viewHolder.im_pic4.setVisibility(View.GONE);
                viewHolder.im_pic5.setVisibility(View.GONE);
                viewHolder.im_pic6.setVisibility(View.GONE);
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text4.setVisibility(View.GONE);
                viewHolder.re_text5.setVisibility(View.GONE);
                viewHolder.re_text6.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 4:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.tv_photoweight3.setText("体重1");
                viewHolder.tv_photoweight4.setText("体重1");
                viewHolder.im_pic5.setVisibility(View.GONE);
                viewHolder.im_pic6.setVisibility(View.GONE);
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text5.setVisibility(View.GONE);
                viewHolder.re_text6.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 5:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.tv_photoweight3.setText("体重1");
                viewHolder.tv_photoweight4.setText("体重1");
                viewHolder.tv_photoweight5.setText("体重1");
                viewHolder.im_pic6.setVisibility(View.GONE);
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text6.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 6:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.tv_photoweight3.setText("体重1");
                viewHolder.tv_photoweight4.setText("体重1");
                viewHolder.tv_photoweight5.setText("体重1");
                viewHolder.tv_photoweight6.setText("体重1");
                viewHolder.im_pic7.setVisibility(View.GONE);
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text7.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 7:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重1");
                viewHolder.tv_photoweight3.setText("体重1");
                viewHolder.tv_photoweight4.setText("体重1");
                viewHolder.tv_photoweight5.setText("体重1");
                viewHolder.tv_photoweight6.setText("体重1");
                viewHolder.tv_photoweight7.setText("体重1");
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 8:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重2");
                viewHolder.tv_photoweight3.setText("体重3");
                viewHolder.tv_photoweight4.setText("体重4");
                viewHolder.tv_photoweight5.setText("体重5");
                viewHolder.tv_photoweight6.setText("体重6");
                viewHolder.tv_photoweight7.setText("体重7");
                viewHolder.tv_photoweight8.setText("体重8");
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 9:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重2");
                viewHolder.tv_photoweight3.setText("体重3");
                viewHolder.tv_photoweight4.setText("体重4");
                viewHolder.tv_photoweight5.setText("体重5");
                viewHolder.tv_photoweight6.setText("体重6");
                viewHolder.tv_photoweight7.setText("体重7");
                viewHolder.tv_photoweight8.setText("体重8");
                viewHolder.tv_photoweight9.setText("体重9");
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 10:
                viewHolder.tv_photoweight1.setText("1");
                viewHolder.tv_photoweight2.setText("体重2");
                viewHolder.tv_photoweight3.setText("体重3");
                viewHolder.tv_photoweight4.setText("体重4");
                viewHolder.tv_photoweight5.setText("体重5");
                viewHolder.tv_photoweight6.setText("体重6");
                viewHolder.tv_photoweight7.setText("体重7");
                viewHolder.tv_photoweight8.setText("体重8");
                viewHolder.tv_photoweight9.setText("体重9");
                viewHolder.tv_photoweight10.setText("体重10");
                break;
        }

        return convertView;
    }
    class ViewHolder{
        LinearLayout ll_singlepic;
        RelativeLayout re_mulpic;
        RelativeLayout re_text1;
        RelativeLayout re_text2;
        RelativeLayout re_text3;
        RelativeLayout re_text4;
        RelativeLayout re_text5;
        RelativeLayout re_text6;
        RelativeLayout re_text7;
        RelativeLayout re_text8;
        RelativeLayout re_text9;
        RelativeLayout re_text10;
        TextView tv_photoweight1;
        TextView tv_photoweight2;
        TextView tv_photoweight3;
        TextView tv_photoweight4;
        TextView tv_photoweight5;
        TextView tv_photoweight6;
        TextView tv_photoweight7;
        TextView tv_photoweight8;
        TextView tv_photoweight9;
        TextView tv_photoweight10;
        TextView tv_photodate1;
        TextView tv_photodate2;
        TextView tv_photodate3;
        TextView tv_photodate4;
        TextView tv_photodate5;
        TextView tv_photodate6;
        TextView tv_photodate7;
        TextView tv_photodate8;
        TextView tv_photodate9;
        TextView tv_photodate10;
        ImageView im_pic1;
        ImageView im_pic2;
        ImageView im_pic3;
        ImageView im_pic4;
        ImageView im_pic5;
        ImageView im_pic6;
        ImageView im_pic7;
        ImageView im_pic8;
        ImageView im_pic9;
        ImageView im_pic10;
        public ViewHolder(View view){
            ll_singlepic= (LinearLayout) view.findViewById(R.id.ll_singlepic);
            re_mulpic= (RelativeLayout) view.findViewById(R.id.re_mulpic);
            re_text1= (RelativeLayout) view.findViewById(R.id.re_text1);
            re_text2= (RelativeLayout) view.findViewById(R.id.re_text2);
            re_text3= (RelativeLayout) view.findViewById(R.id.re_text3);
            re_text4= (RelativeLayout) view.findViewById(R.id.re_text4);
            re_text5= (RelativeLayout) view.findViewById(R.id.re_text5);
            re_text6= (RelativeLayout) view.findViewById(R.id.re_text6);
            re_text7= (RelativeLayout) view.findViewById(R.id.re_text7);
            re_text8= (RelativeLayout) view.findViewById(R.id.re_text8);
            re_text9= (RelativeLayout) view.findViewById(R.id.re_text9);
            re_text10= (RelativeLayout) view.findViewById(R.id.re_text10);
            tv_photoweight1=(TextView)view.findViewById(R.id.tv_photoweight1);
            tv_photoweight2=(TextView)view.findViewById(R.id.tv_photoweight2);
            tv_photoweight3=(TextView)view.findViewById(R.id.tv_photoweight3);
            tv_photoweight4=(TextView)view.findViewById(R.id.tv_photoweight4);
            tv_photoweight5=(TextView)view.findViewById(R.id.tv_photoweight5);
            tv_photoweight6=(TextView)view.findViewById(R.id.tv_photoweight6);
            tv_photoweight7=(TextView)view.findViewById(R.id.tv_photoweight7);
            tv_photoweight8=(TextView)view.findViewById(R.id.tv_photoweight8);
            tv_photoweight9=(TextView)view.findViewById(R.id.tv_photoweight9);
            tv_photoweight10=(TextView)view.findViewById(R.id.tv_photoweight10);
            tv_photodate1= (TextView) view.findViewById(R.id.tv_photodate1);
            tv_photodate2= (TextView) view.findViewById(R.id.tv_photodate2);
            tv_photodate3= (TextView) view.findViewById(R.id.tv_photodate3);
            tv_photodate4= (TextView) view.findViewById(R.id.tv_photodate4);
            tv_photodate5= (TextView) view.findViewById(R.id.tv_photodate5);
            tv_photodate6= (TextView) view.findViewById(R.id.tv_photodate6);
            tv_photodate7= (TextView) view.findViewById(R.id.tv_photodate7);
            tv_photodate8= (TextView) view.findViewById(R.id.tv_photodate8);
            tv_photodate9= (TextView) view.findViewById(R.id.tv_photodate9);
            tv_photodate10= (TextView) view.findViewById(R.id.tv_photodate10);
            im_pic1=(ImageView) view.findViewById(R.id.im_pic1);
            im_pic2= (ImageView) view.findViewById(R.id.im_pic2);
            im_pic3= (ImageView) view.findViewById(R.id.im_pic3);
            im_pic4= (ImageView) view.findViewById(R.id.im_pic4);
            im_pic5= (ImageView) view.findViewById(R.id.im_pic5);
            im_pic6= (ImageView) view.findViewById(R.id.im_pic6);
            im_pic7= (ImageView) view.findViewById(R.id.im_pic7);
            im_pic8= (ImageView) view.findViewById(R.id.im_pic8);
            im_pic9= (ImageView) view.findViewById(R.id.im_pic9);
            im_pic10= (ImageView) view.findViewById(R.id.im_pic10);
        }
    }
}
