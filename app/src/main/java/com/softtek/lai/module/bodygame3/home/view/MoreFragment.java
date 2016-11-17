package com.softtek.lai.module.bodygame3.home.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.more.view.AssistantFragment;
import com.softtek.lai.module.bodygame3.more.view.CoachFragment;
import com.softtek.lai.module.bodygame3.more.view.HeadCoachFragment;
import com.softtek.lai.module.bodygame3.more.view.StudentFragment;
import com.softtek.lai.widgets.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment {

    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;

    @InjectView(R.id.container)
    FrameLayout container;
    @InjectView(R.id.head_image)
    CircleImageView head_image;
    @InjectView(R.id.tv_name)
    TextView tv_name;

    public MoreFragment() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        final List<String> data=new ArrayList<>();
        data.add("测试数据1测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        tv_title.attachCustomSource(new ArrowSpinnerAdapter<String>(getContext(),data,R.layout.selector_class_item) {


            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv_class_name=holder.getView(R.id.tv_class_name);
                tv_class_name.setText(data);
            }

            @Override
            public String getText(int position) {
                return data.get(position);
            }
        });
        tv_title.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,new StudentFragment()).commit();
                }else if(i==1){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,new HeadCoachFragment()).commit();
                }else if(i==2){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,new CoachFragment()).commit();
                }else if(i==3){
                    getChildFragmentManager().beginTransaction().replace(R.id.container,new AssistantFragment()).commit();
                }


            }
        });

    }

    @Override
    protected void initDatas() {

    }
}
