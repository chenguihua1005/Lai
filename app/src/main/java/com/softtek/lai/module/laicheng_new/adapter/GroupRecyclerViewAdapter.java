
package com.softtek.lai.module.laicheng_new.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.LaiApplication;
import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng_new.model.GroupModel;

import java.util.List;


public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {

    private List<GroupModel> myItems;
    private FinishListener myListener;
    private Context mContext;

    public GroupRecyclerViewAdapter(List<GroupModel> items, Context context,FinishListener listener) {
        myItems = items;
        myListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_group_name, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
    }

    public interface FinishListener {
        void setOnFinishListener();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO - Your view members
        public GroupModel item;
        private TextView mGroupName;
        private View mSpace;
        private RecyclerView mMumbers;
//        private RecyclerView mClubs;
        private GroupNumberAdapter numberAdapter;
        private ClubRecyclerViewAdapter clubAdapter;
//        private Button mShowClub;
        private RelativeLayout mClassContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mGroupName = itemView.findViewById(R.id.tv_name);
            mSpace = itemView.findViewById(R.id.space);
            mMumbers = itemView.findViewById(R.id.rcv_content);
//            mClubs = itemView.findViewById(R.id.rcv_clubs);
//            mShowClub = itemView.findViewById(R.id.btn_clubs);
            mClassContent = itemView.findViewById(R.id.rl_class_content);
            itemView.setOnClickListener(this);
            mClassContent.setOnClickListener(this);
//            mShowClub.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        @SuppressLint("SetTextI18n")
        public void setData(GroupModel item) {
            this.item = item;
            if (getAdapterPosition() == myItems.size() - 1){
                mSpace.setVisibility(View.GONE);
            }
            mGroupName.setText(item.getClassName() + "   "+ item.getClassCode());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            mMumbers.setLayoutManager(manager);
            mMumbers.setVisibility(View.GONE);
            RecyclerView.LayoutManager clubManager = new LinearLayoutManager(mContext);
//            mClubs.setLayoutManager(clubManager);
            numberAdapter = new GroupNumberAdapter(item.getMembers(), new GroupNumberAdapter.ItemListener() {
                @Override
                public void onItemClick(GroupModel.MembersBean item) {
                    VisitorModel model = new VisitorModel();
                    model.setBirthDate(item.getBirthDay());
                    model.setGender(item.getGender());
                    model.setName(item.getUserName());
                    model.setPhoneNo(item.getMobile());
                    model.setHeight(item.getHeight());
                    model.setAge(item.getAge());
                    model.setSuperior(false);
                    model.setVisitorId(item.getAccountId());
                    model.setSource(2);//从体管班进入
                    LocalBroadcastManager.getInstance(LaiApplication.getInstance().getApplicationContext()).
                            sendBroadcast(new Intent().setAction("visitorinfo").putExtra("visitorModel", model));
                    myListener.setOnFinishListener();
                }
            }, mContext);
            mMumbers.setAdapter(numberAdapter);

//            clubAdapter = new ClubRecyclerViewAdapter(item.getClubs(), new ClubRecyclerViewAdapter.ItemListener() {
//                @Override
//                public void onItemClick(GroupModel.ClubsBean item) {
//
//                }
//            });
//            mClubs.setAdapter(clubAdapter);
//            mClubs.setVisibility(View.GONE);
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rl_class_content:
                    if (mMumbers.getVisibility() == View.GONE){
                        mMumbers.setVisibility(View.VISIBLE);
                        mSpace.setVisibility(View.GONE);
                    }else {
                        mMumbers.setVisibility(View.GONE);
//                        mClubs.setVisibility(View.GONE);
                        if (getAdapterPosition() == myItems.size() - 1){
                            mSpace.setVisibility(View.GONE);
                        }else {
                            mSpace.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
//                case R.id.btn_clubs:
//                    if (mClubs.getVisibility() == View.GONE) {
//                        mClubs.setVisibility(View.VISIBLE);
//                    }else {
//                        mClubs.setVisibility(View.GONE);
//                    }
//                    break;
            }
        }

    }
}
                                