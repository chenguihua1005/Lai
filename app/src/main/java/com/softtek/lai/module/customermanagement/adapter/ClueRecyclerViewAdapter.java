
package com.softtek.lai.module.customermanagement.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.PersonnelModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;


public class ClueRecyclerViewAdapter extends RecyclerView.Adapter<ClueRecyclerViewAdapter.ViewHolder> {

    private List<PersonnelModel> myItems;
    private ItemListener myListener;
    private Context mContext;

    public ClueRecyclerViewAdapter(List<PersonnelModel> items, ItemListener listener,Context context) {
        myItems = items;
        myListener = listener;
        mContext = context;
    }

    public void setListener(ItemListener listener) {
        myListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_club, parent, false)); // TODO
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
        void onItemClick(PersonnelModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mHeadView;
        private TextView mPersonnelName;
        private TextView mCustomerSum;
        private TextView mCustomerToday;
        private TextView mMarketSum;
        private TextView mMarketToady;
        private ImageView mDelete;
        private String path = AddressManager.get("photoHost");

        // TODO - Your view members
        public PersonnelModel item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mHeadView = itemView.findViewById(R.id.civ_head);
            mPersonnelName = itemView.findViewById(R.id.tv_personnel_name);
            mCustomerSum = itemView.findViewById(R.id.tv_customer_sum);
            mCustomerToday = itemView.findViewById(R.id.tv_customer_today);
            mMarketSum = itemView.findViewById(R.id.tv_market_sum);
            mMarketToady = itemView.findViewById(R.id.tv_market_today);
            mDelete = itemView.findViewById(R.id.iv_delete);
            // TODO instantiate/assign view members
        }

        @SuppressLint("SetTextI18n")
        public void setData(final PersonnelModel item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getUserPhoto())) {
                Picasso.with(mContext).load(path + item.getUserPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeadView);
            }
            mPersonnelName.setText(item.getPersonnelName() + "(" + item.getPersonnelPhone() + ")");
            mCustomerSum.setText(item.getCustomerSum() +"");
            mCustomerToday.setText(item.getCustomerToday() + "");
            mMarketSum.setText(item.getMarketSum() + "");
            mMarketToady.setText(item.getMarketToday() + "");
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItems.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

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
                                