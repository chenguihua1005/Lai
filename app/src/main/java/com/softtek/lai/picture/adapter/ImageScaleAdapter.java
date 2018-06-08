package com.softtek.lai.picture.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.softtek.lai.R;
import com.softtek.lai.picture.LookBigPicActivity;
import com.softtek.lai.picture.bean.EaluationPicBean;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zilla.libcore.file.AddressManager;

/**
 * Created by mabeijianxi on 2015/1/5.
 */
public class ImageScaleAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener {
    private List<EaluationPicBean> mPicData;
    private Context mContext;

    private PhotoView imageView;
    private View mCurrentView;
    private SavePic savePic;

    public ImageScaleAdapter(Context mContext, SavePic savePic, List<EaluationPicBean> data) {
        super();
        this.mPicData = data;
        this.mContext = mContext;
        this.savePic=savePic;
    }

    @Override
    public int getCount() {
        if (mPicData != null) {
            return mPicData.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_photoview, container, false);
        imageView = (PhotoView) inflate.findViewById(R.id.pv);
        imageView.setOnPhotoTapListener(this);

        final ProgressBar pb = (ProgressBar) inflate.findViewById(R.id.pb);
        final EaluationPicBean ealuationPicBean = mPicData.get(position);

        if (ealuationPicBean != null && ealuationPicBean.imageUrl != null && !"null".equals(ealuationPicBean.imageUrl)) {
            setupNetImage(pb, ealuationPicBean);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(savePic!=null){
                        savePic.onSave(ealuationPicBean);
                    }
                    return false;
                }
            });
        } else {
            imageView.setImageResource(R.drawable.default_icon_square);
        }

        container.addView(inflate);//将ImageView加入到ViewPager中
        return inflate;
    }

    /**
     * 设置网络图片加载规则
     * @param pb
     * @param ealuationPicBean
     */
    private void setupNetImage(final ProgressBar pb, final EaluationPicBean ealuationPicBean) {
        startLoad(pb);
        Picasso.with(mContext).load(AddressManager.get("photoHost")+ealuationPicBean.imageUrl)
                .placeholder(R.drawable.default_icon_square)
                .error(R.drawable.default_icon_square).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                overLoad(pb);
            }

            @Override
            public void onError() {
                overLoad( pb);
            }
        });
    }

    /**
     * 展示过度图片
     *
     * @param ealuationPicBean
     * @param imageView
     */
//    private void showExcessPic(EaluationListBean.EaluationPicBean ealuationPicBean, PhotoView imageView) {
//        Bitmap bitmap = ImageUtils.getBitmapFromCache(ealuationPicBean.smallImageUrl, mImageLoader);
//        if (bitmap != null) {
//            imageView.setImageBitmap(bitmap);
//        } else {
//            imageView.setImageResource(R.drawable.home_youpin);
//        }
//    }

    /**
     * 显示进度条
     *
     * @param pb
     */
    private void startLoad( ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
    }

    /**
     * 结束进度条
     *
     * @param pb
     */
    private void overLoad( ProgressBar pb) {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 单击屏幕关闭
     *
     * @param view - View the user tapped.
     * @param x    - where the user tapped from the of the Drawable, as
     *             percentage of the Drawable width.
     * @param y    - where the user tapped from the top of the Drawable, as
     */
    @Override
    public void onPhotoTap(View view, float x, float y) {
        ((LookBigPicActivity) mContext).startActivityAnim();
    }

    @Override
    public void onOutsidePhotoTap() {

    }

    public interface SavePic{
        void onSave(EaluationPicBean ealuationPicBean);
    }
}
