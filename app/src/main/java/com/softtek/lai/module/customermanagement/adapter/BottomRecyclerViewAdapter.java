
package com.softtek.lai.module.customermanagement.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;


public class BottomRecyclerViewAdapter extends RecyclerView.Adapter<BottomRecyclerViewAdapter.ViewHolder> {

    private List<String> myItems;
    private ItemListener myListener;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPostion) {
        this.selectPosition = selectPostion;
    }

    private int selectPosition;

    public List<String> getMyItems() {
        return myItems;
    }

    public void setMyItems(List<String> myItems) {
        this.myItems = myItems;
    }

    public BottomRecyclerViewAdapter(List<String> items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_bottom_sheet, parent, false)); // TODO
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
        void onItemClick(String item, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mName;
        private TextView mSupName;
        private RadioButton mRadioButton;

        // TODO - Your view members
        public String item;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_main_name);
            mSupName = itemView.findViewById(R.id.tv_sup_name);
            mRadioButton = itemView.findViewById(R.id.rbtn_check);
            itemView.setOnClickListener(this);
            // TODO instantiate/assign view members
        }

        public void setData(String item) {
            this.item = item;
            mName.setText(item);
            if (getAdapterPosition() == getSelectPosition()){
                mRadioButton.setChecked(true);
            }else {
                mRadioButton.setChecked(false);
            }
            switch (item) {
                case "教练":
                    mSupName.setText("(线下体馆赛班级中的小组长)");
                    break;
                case "助教":
                    mSupName.setText("(线下体馆赛班级中的助教及复测,摄影灯工作人员)");
                    break;
                case "学员":
                    mSupName.setText("(线下体观赛班级中的学员)");
                    break;
                default:
                    mSupName.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item, getAdapterPosition());
            }
        }
    }
}
                                