package com.softtek.lai.module.bodygame3.more.view;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggx.widgets.adapter.EasyAdapter;
import com.ggx.widgets.adapter.ViewHolder;
import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.widgets.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_create_class)
public class CreateClassActivity extends BaseActivity {

    @InjectView(R.id.rl_area)
    RelativeLayout rl_area;
    BottomSheetDialog dialog;

    @Override
    protected void initViews() {
        dialog=new BottomSheetDialog(this);

        View view=getLayoutInflater().inflate(R.layout.activity_editor_text,null);
        final ListView lv= (ListView) view.findViewById(R.id.lv);
        lv.setOnTouchListener(new View.OnTouchListener() {
            int lastY;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastY= (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int y= (int) motionEvent.getY();
                        if(y-lastY>0){
                            if(lv.getFirstVisiblePosition()!=0){
                                lv.getParent().requestDisallowInterceptTouchEvent(true);

                            }
                        }
                        break;
                }
                return false;
            }
        });
        final List<String> data=new ArrayList<>();
        for (int i=0;i<20;i++){
            data.add("测试数据+"+i);
        }
        lv.setAdapter(new EasyAdapter<String>(this,data,R.layout.spinner_list_item) {
            @Override
            public void convert(ViewHolder holder, String data, int position) {
                TextView tv=holder.getView(R.id.tv_tinted_spinner);
                tv.setText(data);
            }
        });
        dialog.setContentView(view);
        rl_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
