package com.softtek.lai.module.bodygame3.home.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.ggx.widgets.adapter.ViewHolder;
import com.ggx.widgets.nicespinner.ArrowSpinner2;
import com.ggx.widgets.nicespinner.ArrowSpinnerAdapter;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame3.more.view.CreateClassActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment {

    @InjectView(R.id.tv_title)
    ArrowSpinner2 tv_title;

    @InjectView(R.id.tv)
    Button btn;

    public MoreFragment() {
        // Required empty public constructor
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

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateClassActivity.class));
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
