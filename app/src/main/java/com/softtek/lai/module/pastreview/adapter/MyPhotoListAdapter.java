package com.softtek.lai.module.pastreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.lossweightstory.view.PictureActivity;
import com.softtek.lai.module.pastreview.model.MyPhotoListModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by lareina.qiao on 6/28/2016.
 */
public class MyPhotoListAdapter extends BaseAdapter {
    private Context context;
    private List<MyPhotoListModel> myPhotoListModelList;

    public MyPhotoListAdapter(Context context, List<MyPhotoListModel> myPhotoListModelList) {
        this.context = context;
        this.myPhotoListModelList = myPhotoListModelList;
    }

    @Override
    public int getCount() {
        return myPhotoListModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return myPhotoListModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_myphoto_listitem, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MyPhotoListModel myPhotoListModel = myPhotoListModelList.get(position);

        String path = AddressManager.get("photoHost");
        switch (myPhotoListModel.getPhotoList().size()) {

            case 1:
                int px = Math.min(DisplayUtil.getMobileHeight(context), DisplayUtil.getMobileWidth(context));
                ViewGroup.LayoutParams params = viewHolder.im_single.getLayoutParams();
                params.height = px;
                params.width = px;
                viewHolder.im_single.setLayoutParams(params);
                viewHolder.re_mulpic.setVisibility(View.GONE);
                viewHolder.ll_singlepic.setVisibility(View.VISIBLE);
                viewHolder.tv_singleweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_singledate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_single);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_single);
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(position).getWeight());
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(position).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(position).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
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
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
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
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
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
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
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
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(5).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(5).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photoweight6.setText(myPhotoListModel.getPhotoList().get(5).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
                viewHolder.tv_photodate6.setText(getDate(myPhotoListModel.getPhotoList().get(5).getCreateDate(), 0));
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
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(5).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(5).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(6).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(6).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photoweight6.setText(myPhotoListModel.getPhotoList().get(5).getWeight()+"斤");
                viewHolder.tv_photoweight7.setText(myPhotoListModel.getPhotoList().get(6).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
                viewHolder.tv_photodate6.setText(getDate(myPhotoListModel.getPhotoList().get(5).getCreateDate(), 0));
                viewHolder.tv_photodate7.setText(getDate(myPhotoListModel.getPhotoList().get(6).getCreateDate(), 0));
                viewHolder.im_pic8.setVisibility(View.GONE);
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text8.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 8:
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(5).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(5).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(6).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(6).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(7).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(7).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photoweight6.setText(myPhotoListModel.getPhotoList().get(5).getWeight()+"斤");
                viewHolder.tv_photoweight7.setText(myPhotoListModel.getPhotoList().get(6).getWeight()+"斤");
                viewHolder.tv_photoweight8.setText(myPhotoListModel.getPhotoList().get(7).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
                viewHolder.tv_photodate6.setText(getDate(myPhotoListModel.getPhotoList().get(5).getCreateDate(), 0));
                viewHolder.tv_photodate7.setText(getDate(myPhotoListModel.getPhotoList().get(6).getCreateDate(), 0));
                viewHolder.tv_photodate8.setText(getDate(myPhotoListModel.getPhotoList().get(7).getCreateDate(), 0));
                viewHolder.im_pic9.setVisibility(View.GONE);
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text9.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 9:
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(5).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(5).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(6).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(6).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(7).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(7).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(8).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(8).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic9);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic9);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photoweight6.setText(myPhotoListModel.getPhotoList().get(5).getWeight()+"斤");
                viewHolder.tv_photoweight7.setText(myPhotoListModel.getPhotoList().get(6).getWeight()+"斤");
                viewHolder.tv_photoweight8.setText(myPhotoListModel.getPhotoList().get(7).getWeight()+"斤");
                viewHolder.tv_photoweight9.setText(myPhotoListModel.getPhotoList().get(8).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
                viewHolder.tv_photodate6.setText(getDate(myPhotoListModel.getPhotoList().get(5).getCreateDate(), 0));
                viewHolder.tv_photodate7.setText(getDate(myPhotoListModel.getPhotoList().get(6).getCreateDate(), 0));
                viewHolder.tv_photodate8.setText(getDate(myPhotoListModel.getPhotoList().get(7).getCreateDate(), 0));
                viewHolder.tv_photodate9.setText(getDate(myPhotoListModel.getPhotoList().get(8).getCreateDate(), 0));
                viewHolder.im_pic10.setVisibility(View.GONE);
                viewHolder.re_text10.setVisibility(View.GONE);
                break;
            case 10:
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(0).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(0).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(1).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(1).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic2);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic1);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(2).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(2).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic3);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(3).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(3).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic4);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(4).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(4).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic5);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(5).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(5).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic6);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(6).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(6).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic7);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(7).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(7).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic8);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(8).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(8).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic9);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic9);
                }
                if (!TextUtils.isEmpty(myPhotoListModel.getPhotoList().get(9).getImgUrl())) {
                    Picasso.with(context).load(path + myPhotoListModel.getPhotoList().get(9).getImgUrl()).fit().error(R.drawable.default_icon_square).into(viewHolder.im_pic10);
                } else {
                    Picasso.with(context).load(R.drawable.default_icon_square).into(viewHolder.im_pic10);
                }
                viewHolder.tv_photoweight1.setText(myPhotoListModel.getPhotoList().get(0).getWeight()+"斤");
                viewHolder.tv_photoweight2.setText(myPhotoListModel.getPhotoList().get(1).getWeight()+"斤");
                viewHolder.tv_photoweight3.setText(myPhotoListModel.getPhotoList().get(2).getWeight()+"斤");
                viewHolder.tv_photoweight4.setText(myPhotoListModel.getPhotoList().get(3).getWeight()+"斤");
                viewHolder.tv_photoweight5.setText(myPhotoListModel.getPhotoList().get(4).getWeight()+"斤");
                viewHolder.tv_photoweight6.setText(myPhotoListModel.getPhotoList().get(5).getWeight()+"斤");
                viewHolder.tv_photoweight7.setText(myPhotoListModel.getPhotoList().get(6).getWeight()+"斤");
                viewHolder.tv_photoweight8.setText(myPhotoListModel.getPhotoList().get(7).getWeight()+"斤");
                viewHolder.tv_photoweight9.setText(myPhotoListModel.getPhotoList().get(8).getWeight()+"斤");
                viewHolder.tv_photoweight10.setText(myPhotoListModel.getPhotoList().get(9).getWeight()+"斤");
                viewHolder.tv_photodate1.setText(getDate(myPhotoListModel.getPhotoList().get(0).getCreateDate(), 0));
                viewHolder.tv_photodate2.setText(getDate(myPhotoListModel.getPhotoList().get(1).getCreateDate(), 0));
                viewHolder.tv_photodate3.setText(getDate(myPhotoListModel.getPhotoList().get(2).getCreateDate(), 0));
                viewHolder.tv_photodate4.setText(getDate(myPhotoListModel.getPhotoList().get(3).getCreateDate(), 0));
                viewHolder.tv_photodate5.setText(getDate(myPhotoListModel.getPhotoList().get(4).getCreateDate(), 0));
                viewHolder.tv_photodate6.setText(getDate(myPhotoListModel.getPhotoList().get(5).getCreateDate(), 0));
                viewHolder.tv_photodate7.setText(getDate(myPhotoListModel.getPhotoList().get(6).getCreateDate(), 0));
                viewHolder.tv_photodate8.setText(getDate(myPhotoListModel.getPhotoList().get(7).getCreateDate(), 0));
                viewHolder.tv_photodate9.setText(getDate(myPhotoListModel.getPhotoList().get(8).getCreateDate(), 0));
                viewHolder.tv_photodate10.setText(getDate(myPhotoListModel.getPhotoList().get(9).getCreateDate(), 0));
                break;
        }
        viewHolder.im_pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 1);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 2);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 3);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 4);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 5);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 6);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 7);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 8);
                context.startActivity(intent);
            }
        });
        viewHolder.im_pic10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10 + 9);
                context.startActivity(intent);
            }
        });
        viewHolder.im_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureActivity.class);
                ArrayList<String> img = new ArrayList<>();
                for (int j = 0; j < myPhotoListModelList.size(); j++) {
                    for (int i = 0; i < myPhotoListModelList.get(j).getPhotoList().size(); i++) {
                        img.add(myPhotoListModelList.get(j).getPhotoList().get(i).getImgUrl());
                    }
                }
                intent.putStringArrayListExtra("images", img);
                intent.putExtra("position", position * 10);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        FrameLayout ll_singlepic;
        RelativeLayout re_singletext1;
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
        TextView tv_singleweight1;
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
        TextView tv_singledate1;
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
        ImageView im_single;

        public ViewHolder(View view) {
            ll_singlepic = (FrameLayout) view.findViewById(R.id.ll_singlepic);
            re_singletext1 = (RelativeLayout) view.findViewById(R.id.re_singletext1);
            re_mulpic = (RelativeLayout) view.findViewById(R.id.re_mulpic);
            re_text1 = (RelativeLayout) view.findViewById(R.id.re_text1);
            re_text2 = (RelativeLayout) view.findViewById(R.id.re_text2);
            re_text3 = (RelativeLayout) view.findViewById(R.id.re_text3);
            re_text4 = (RelativeLayout) view.findViewById(R.id.re_text4);
            re_text5 = (RelativeLayout) view.findViewById(R.id.re_text5);
            re_text6 = (RelativeLayout) view.findViewById(R.id.re_text6);
            re_text7 = (RelativeLayout) view.findViewById(R.id.re_text7);
            re_text8 = (RelativeLayout) view.findViewById(R.id.re_text8);
            re_text9 = (RelativeLayout) view.findViewById(R.id.re_text9);
            re_text10 = (RelativeLayout) view.findViewById(R.id.re_text10);
            tv_photoweight1 = (TextView) view.findViewById(R.id.tv_photoweight1);
            tv_photoweight2 = (TextView) view.findViewById(R.id.tv_photoweight2);
            tv_photoweight3 = (TextView) view.findViewById(R.id.tv_photoweight3);
            tv_photoweight4 = (TextView) view.findViewById(R.id.tv_photoweight4);
            tv_photoweight5 = (TextView) view.findViewById(R.id.tv_photoweight5);
            tv_photoweight6 = (TextView) view.findViewById(R.id.tv_photoweight6);
            tv_photoweight7 = (TextView) view.findViewById(R.id.tv_photoweight7);
            tv_photoweight8 = (TextView) view.findViewById(R.id.tv_photoweight8);
            tv_photoweight9 = (TextView) view.findViewById(R.id.tv_photoweight9);
            tv_photoweight10 = (TextView) view.findViewById(R.id.tv_photoweight10);
            tv_singleweight1 = (TextView) view.findViewById(R.id.tv_singleweight1);
            tv_photodate1 = (TextView) view.findViewById(R.id.tv_photodate1);
            tv_photodate2 = (TextView) view.findViewById(R.id.tv_photodate2);
            tv_photodate3 = (TextView) view.findViewById(R.id.tv_photodate3);
            tv_photodate4 = (TextView) view.findViewById(R.id.tv_photodate4);
            tv_photodate5 = (TextView) view.findViewById(R.id.tv_photodate5);
            tv_photodate6 = (TextView) view.findViewById(R.id.tv_photodate6);
            tv_photodate7 = (TextView) view.findViewById(R.id.tv_photodate7);
            tv_photodate8 = (TextView) view.findViewById(R.id.tv_photodate8);
            tv_photodate9 = (TextView) view.findViewById(R.id.tv_photodate9);
            tv_photodate10 = (TextView) view.findViewById(R.id.tv_photodate10);
            tv_singledate1 = (TextView) view.findViewById(R.id.tv_singledate1);
            im_pic1 = (ImageView) view.findViewById(R.id.im_pic1);
            im_pic2 = (ImageView) view.findViewById(R.id.im_pic2);
            im_pic3 = (ImageView) view.findViewById(R.id.im_pic3);
            im_pic4 = (ImageView) view.findViewById(R.id.im_pic4);
            im_pic5 = (ImageView) view.findViewById(R.id.im_pic5);
            im_pic6 = (ImageView) view.findViewById(R.id.im_pic6);
            im_pic7 = (ImageView) view.findViewById(R.id.im_pic7);
            im_pic8 = (ImageView) view.findViewById(R.id.im_pic8);
            im_pic9 = (ImageView) view.findViewById(R.id.im_pic9);
            im_pic10 = (ImageView) view.findViewById(R.id.im_pic10);
            im_single = (ImageView) view.findViewById(R.id.im_single);
        }
    }

    public String getDate(String date, int type) {
        String Time;
        if (type == 0) {
            String[] YearAndMon = date.split("-");
            String[] Day = YearAndMon[2].split(" ");
            Time = YearAndMon[1] + "月" + Day[0] + "日";
        } else {
            String[] YearAndMon = date.split("-");
            String[] Day = YearAndMon[2].split(" ");
            Time = YearAndMon[0] + "年" + YearAndMon[1] + "月" + Day[0] + "日";
        }
        return Time;
    }
}