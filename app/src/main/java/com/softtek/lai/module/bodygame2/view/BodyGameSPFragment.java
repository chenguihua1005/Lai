package com.softtek.lai.module.bodygame2.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.common.LazyBaseFragment;
import com.softtek.lai.module.bodygame2.model.SPBodyGameInfo;
import com.softtek.lai.module.bodygame2.model.SPPCMoldel;
import com.softtek.lai.utils.DisplayUtil;
import com.softtek.lai.widgets.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.fragment_bodygame_sp)
public class BodyGameSPFragment extends LazyBaseFragment implements View.OnClickListener{

    @InjectView(R.id.toolbar)
    RelativeLayout relativeLayout;
    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.iv_banner)
    ImageView iv_banner;
    @InjectView(R.id.iv_refresh)
    ImageView iv_refresh;

    @InjectView(R.id.tv_person_num)
    TextView tv_person_num;
    @InjectView(R.id.tv_loss_weight)
    TextView tv_loss_weight;
    @InjectView(R.id.tv_fuce_per)
    TextView tv_fuce_per;
    @InjectView(R.id.tv_new_class)
    TextView tv_new_class;
    @InjectView(R.id.tv_server_rank)
    TextView tv_server_rank;
    @InjectView(R.id.tv_loss_rank)
    TextView tv_loss_rank;
    @InjectView(R.id.tv_fuce_rank)
    TextView tv_fuce_rank;
    @InjectView(R.id.tv_new_student)
    TextView tv_new_student;

    @InjectView(R.id.rl_student_more)
    RelativeLayout rl_student_more;
    @InjectView(R.id.mlv)
    MyListView mlv;
    private List<SPPCMoldel> pcModels=new ArrayList<>();
    //adapter






    @Override
    protected void initViews() {
        int status=DisplayUtil.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        params.topMargin=status;
        relativeLayout.setLayoutParams(params);
        ll_left.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        rl_student_more.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }


    @Override
    protected void lazyLoad() {
        Log.i("BodyGameSPFragment 加载数据");
    }

    public void onloadCompleted(SPBodyGameInfo info){
        if(info!=null){

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
//                getActivity().finish();
                startActivity(new Intent(getContext(),PersonalDataActivity.class));
                break;
            case R.id.iv_refresh:
                //刷新
                break;
            case R.id.rl_student_more:
                //我的学员 更多
                break;

        }
    }
}
