package com.softtek.lai.module.bodygame3.home.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ggx.widgets.drop.PopupMenu;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_more)
public class MoreFragment extends LazyBaseFragment {

    @InjectView(R.id.tv_title)
    TextView tv_title;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViews() {
        List<String> data=new ArrayList<>();
        data.add("测试数据1测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        final PopupMenu pop=new PopupMenu(getContext(), DisplayUtil.dip2px(getContext(),130));
        pop.setAdapter(new ArrayAdapter(getContext(),R.layout.spinner_list_item,data));
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pop.isShowing()){
                    pop.dismiss();
                }else
                    pop.show(view, (int) (view.getWidth() - 0 ), (int) 0);
                    //pop.show(view);
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
