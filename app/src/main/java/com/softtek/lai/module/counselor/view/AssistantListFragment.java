package com.softtek.lai.module.counselor.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseFragment;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.counselor.model.AssistantClassInfo;
import com.softtek.lai.module.counselor.model.AssistantInfo;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.login.model.User;
import com.softtek.lai.utils.ACache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.InjectView;
import zilla.libcore.ui.InjectLayout;

/**
 * Created by jarvis.liu on 3/24/2016.
 * 助教管理，助教页面
 */
@InjectLayout(R.layout.fragment_assiatant_list)
public class AssistantListFragment extends BaseFragment implements View.OnClickListener{
    private IAssistantPresenter ssistantPresenter;
    private ACache aCache;
    private boolean isShow=false;
    User user;
    List<AssistantInfo> list;

    @InjectView(R.id.rel_all_class_more)
    RelativeLayout rel_all_class_more;

    @InjectView(R.id.list_assistant)
    ListView list_assistant;

    @InjectView(R.id.list_class)
    ListView list_class;

    @InjectView(R.id.lin_class)
    LinearLayout lin_class;

    @Override
    protected void initViews() {
        rel_all_class_more.setOnClickListener(this);

        lin_class.setOnClickListener(this);

        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position:" + position);
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.img_selceted));
                AssistantClassInfo assistantClassInfo = (AssistantClassInfo) list_class.getAdapter().getItem(position);
                ssistantPresenter.showAssistantByClass(user.getUserid(), assistantClassInfo.getClassId(), list_assistant);
            }
        });
        list_assistant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AssistantInfo assistantInfo=list.get(position);
                Intent intent=new Intent(getContext(),AssistantDetailActivity.class);
                intent.putExtra("assistantId",assistantInfo.getAccountId().toString());
                intent.putExtra("classId",assistantInfo.getClassId().toString());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void onEvent(List<AssistantInfo> list){
        System.out.println("list:" + list);
        this.list=list;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initDatas() {
        ssistantPresenter=new AssistantImpl(getContext());
        aCache= ACache.get(getContext(), Constants.USER_ACACHE_DATA_DIR);

        user=(User)aCache.getAsObject(Constants.USER_ACACHE_KEY);
        ssistantPresenter.showAllClassList(user.getUserid(),list_class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_all_class_more:
                if(isShow){
                    isShow=false;
                    lin_class.setVisibility(View.GONE);
                }else {
                    isShow=true;
                    lin_class.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.lin_class:

                break;
        }
    }
}