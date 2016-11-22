package com.softtek.lai.module.bodygame3.more.view;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggx.widgets.view.ChooseView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygame3.more.adapter.ContactExpandableAdapter;
import com.softtek.lai.module.bodygame3.more.model.Contact;
import com.softtek.lai.module.bodygame3.more.net.MoreService;
import com.softtek.lai.utils.RequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_contacts)
public class ContactsActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ExpandableListView>{

    @InjectView(R.id.ll_left)
    LinearLayout ll_left;
    @InjectView(R.id.tv_title)
    TextView tv_title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.choose)
    ChooseView chooseView;
    @InjectView(R.id.tv_perview)
    TextView tv_perview;

    @InjectView(R.id.elv)
    PullToRefreshExpandableListView elv;
    private ContactExpandableAdapter adapter;

    private int pageIndex=1;

    @Override
    protected void initViews() {
        tv_title.setText("邀请小伙伴");
        ll_left.setOnClickListener(this);
        elv.setOnRefreshListener(this);
        elv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        View head= LayoutInflater.from(this).inflate(R.layout.expandable_head_contact,null);
        elv.getRefreshableView().addHeaderView(head);
        adapter=new ContactExpandableAdapter(this,datas,groups);
        elv.getRefreshableView().setAdapter(adapter);
        elv.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    protected void initDatas() {
        chooseView.buildCharaset("A");
        chooseView.buildCharaset("B");
        chooseView.buildCharaset("C");
        chooseView.buildCharaset("D");
        chooseView.buildCharaset("E");
        chooseView.buildCharaset("F");
        chooseView.buildCharaset("G");
        chooseView.buildCharaset("H");
        chooseView.buildCharaset("I");
        chooseView.buildCharaset("J");
        chooseView.buildCharaset("K");
        chooseView.buildCharaset("L");
        chooseView.buildCharaset("M");
        chooseView.buildCharaset("N");
        chooseView.buildCharaset("O");
        chooseView.buildCharaset("P");
        chooseView.buildCharaset("Q");
        chooseView.buildCharaset("R");
        chooseView.buildCharaset("S");
        chooseView.buildCharaset("T");
        chooseView.buildCharaset("U");
        chooseView.buildCharaset("V");
        chooseView.buildCharaset("W");
        chooseView.buildCharaset("X");
        chooseView.buildCharaset("Y");
        chooseView.buildCharaset("Z");
        chooseView.buildCharaset("#");
        chooseView.setChooseListener(new ChooseView.OnChooseListener() {

            @Override
            public void onDown() {
                tv_perview.setVisibility(View.VISIBLE);
            }

            @Override
            public void chooseView(String text) {
                tv_perview.setText(text);
            }

            @Override
            public void onUp() {
                tv_perview.setVisibility(View.GONE);
            }
        });

        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getContactsList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        20, 1,
                        new RequestCallback<ResponseData<List<Contact>>>() {
                            @Override
                            public void success(ResponseData<List<Contact>> data, Response response) {
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }
                            }
                        });
    }

    private Map<String,List<Contact>> datas=new HashMap<>();
    private List<String> groups=new ArrayList<>();
    private void onResult(List<Contact> models){

        for (Contact contact:models){
            String groupName;
            if(TextUtils.isEmpty(contact.getUserEn())){
                groupName="#";
            }else {
                groupName=contact.getUserEn().substring(0,1);

            }
            if(!groups.contains(groupName)){
                groups.add(groupName);
                List<Contact> invitatedContacts=new ArrayList<>();
                datas.put(groupName,invitatedContacts);
            }
            datas.get(groupName).add(contact);
        }
        if(groups.contains("#")){
            groups.remove(groups.indexOf("#"));
            groups.add("#");
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < groups.size(); i++) {
            elv.getRefreshableView().expandGroup(i);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        pageIndex++;
        ZillaApi.NormalRestAdapter.create(MoreService.class)
                .getContactsList(UserInfoModel.getInstance().getToken(),
                        UserInfoModel.getInstance().getUserId(),
                        20, pageIndex,
                        new RequestCallback<ResponseData<List<Contact>>>() {
                            @Override
                            public void success(ResponseData<List<Contact>> data, Response response) {
                                elv.onRefreshComplete();
                                if(data.getStatus()==200){
                                    onResult(data.getData());
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                elv.onRefreshComplete();
                                super.failure(error);
                            }
                        });
    }
}
