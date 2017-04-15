
package com.softtek.lai.module.laicheng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.model.HistoryModel;

import java.util.List;


public class HistoryTestRecyclerView extends RecyclerView.Adapter<HistoryTestRecyclerView.ViewHolder> {

    private List<HistoryModel> myItems;
    private ItemListener myListener;

    public HistoryTestRecyclerView(List<HistoryModel> items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_history_item_list, parent, false)); // TODO
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
    }

    public interface ItemListener {
        void onItemClick(HistoryModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_visittime;
        TextView tv_visitor;
        TextView tv_phoneNo;
        TextView tv_gender;
        TextView tv_age;
        TextView tv_height;
        LinearLayout ll_item_click;

        // TODO - Your view members
        public HistoryModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_visittime = (TextView) itemView.findViewById(R.id.tv_visittime);
            tv_visitor = (TextView) itemView.findViewById(R.id.tv_visitor);
            tv_phoneNo = (TextView) itemView.findViewById(R.id.tv_phoneNo);
            tv_gender = (TextView) itemView.findViewById(R.id.tv_gender);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
            tv_height = (TextView) itemView.findViewById(R.id.tv_height);
            ll_item_click= (LinearLayout) itemView.findViewById(R.id.ll_item_click);
            // TODO instantiate/assign view members
        }

        public void setData(HistoryModel item) {
            this.item = item;
            tv_visittime.setText(item.getMeasuredTime());
            tv_visitor.setText(item.getVisitor().getName());
            tv_phoneNo.setText(item.getVisitor().getPhoneNo());
            tv_age.setText(item.getVisitor().getAge());
            tv_gender.setText(item.getVisitor().getGender());
            tv_height.setText(item.getVisitor().getHeight());
            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


}
                                