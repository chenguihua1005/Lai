package com.softtek.lai.module.bodygame3.more.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.model.HistoryClassModel;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_past_review)
public class PastReviewActivity extends BaseActivity {

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.lv)
    ListView lv;
    private List<HistoryClassModel> datas=new ArrayList<>();
    EasyAdapter<HistoryClassModel> adapter;

    @Override
    protected void initViews() {
        tv_title.setText("往期回顾");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter=new EasyAdapter<HistoryClassModel>(this,datas,R.layout.item_history_class) {
            @Override
            public void convert(ViewHolder holder, HistoryClassModel data, int position) {
                TextView tv_className=holder.getView(R.id.tv_class_name);
                tv_className.setText(data.ClassName);
                TextView tv_classCycle=holder.getView(R.id.tv_class_cycle);
                tv_classCycle.setText(data.ClassStart);
                tv_classCycle.append("~");
                tv_classCycle.append(data.ClassEnd);
                TextView tv_headName=holder.getView(R.id.tv_head_name);
                tv_headName.setText(data.MasterName);
                CircleImageView head_image=holder.getView(R.id.head_image);
                if (TextUtils.isEmpty(data.MasterPhoto)) {
                    Picasso.with(PastReviewActivity.this).load(R.drawable.img_default).into(head_image);
                } else {
                    Picasso.with(PastReviewActivity.this).load(R.drawable.img_default).fit()
                            .error(R.drawable.img_default)
                            .placeholder(R.drawable.img_default).into(head_image);
                }
            }
        };
        lv.setAdapter(adapter);

    }

    @Override
    protected void initDatas() {
        dialogShow("获取数据");
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getHistoryClasses(
                        UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        new RequestCallback<ResponseData<List<HistoryClassModel>>>() {
                            @Override
                            public void success(ResponseData<List<HistoryClassModel>> data, Response response) {
                                dialogDissmiss();
                                if(data.getStatus()==200){
                                    datas.clear();
                                    datas.addAll(data.getData());
                                    adapter.notifyDataSetChanged();
                                }else {
                                    Util.toastMsg(data.getMsg());
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dialogDissmiss();
                                super.failure(error);
                            }
                        }
                );
    }
}
