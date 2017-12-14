
package com.softtek.lai.module.customermanagement.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softtek.lai.R;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.customermanagement.model.PersonnelModel;
import com.softtek.lai.module.customermanagement.service.ClubService;
import com.softtek.lai.utils.RequestCallback;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.file.AddressManager;


public class ClueRecyclerViewAdapter extends RecyclerView.Adapter<ClueRecyclerViewAdapter.ViewHolder> {

    private List<PersonnelModel.WorkersBean> myItems;
//    private ItemListener myListener;
    private Context mContext;
    private AlertDialog.Builder deleteBuilder;
    private AlertDialog deleteDialog;
    private PersonnelModel.ClubsBean clubsBean;

    private PersonnelModel.ClubsBean getClubsBean() {
        return clubsBean;
    }

    public void setClubsBean(PersonnelModel.ClubsBean clubsBean) {
        this.clubsBean = clubsBean;
    }

    private boolean isHasRuler() {
        return hasRuler;
    }

    public void setHasRuler(boolean hasRuler) {
        this.hasRuler = hasRuler;
    }

    private boolean hasRuler;

    public ClueRecyclerViewAdapter(List<PersonnelModel.WorkersBean> items,Context context,PersonnelModel.ClubsBean clubsBean) {
        myItems = items;
//        myListener = listener;
        mContext = context;
        this.clubsBean = clubsBean;
    }

//    public void setListener(ItemListener listener) {
//        myListener = listener;
//    }

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
        void onItemClick(PersonnelModel.WorkersBean item);
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
        private ClubService service;


        // TODO - Your view members
        public PersonnelModel.WorkersBean item;

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
            service = ZillaApi.NormalRestAdapter.create(ClubService.class);
            // TODO instantiate/assign view members
        }

        @SuppressLint("SetTextI18n")
        public void setData(final PersonnelModel.WorkersBean item) {
            this.item = item;
            if (item == null){
                return;
            }
            if (!TextUtils.isEmpty(item.getPhoto())) {
                Picasso.with(mContext).load(path + item.getPhoto()).fit().error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default).into(mHeadView);
            }
            mPersonnelName.setText(item.getName() + "(" + item.getMobile() + ")");
            mCustomerSum.setText(item.getTotalCustomer() +"");
            mCustomerToday.setText(item.getTodayCustomer() + "");
            mMarketSum.setText(item.getTotalMarketingStaff() + "");
            mMarketToady.setText(item.getTodayMarketingStaff() + "");
            if (!isHasRuler()){
                mDelete.setVisibility(View.GONE);
            }else {
                mDelete.setVisibility(View.VISIBLE);
            }
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteBuilder = new AlertDialog.Builder(mContext);
                    deleteBuilder
                            .setMessage("确实要删除工作人员吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    service.deleteWorker(UserInfoModel.getInstance().getToken(), getClubsBean().getID(), item.getAccountId(), new RequestCallback<ResponseData>() {
                                        @Override
                                        public void success(ResponseData responseData, Response response) {
                                            if (responseData.getStatus() == 200) {
                                                Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                                            }
                                            deleteDialog.dismiss();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            super.failure(error);
                                            deleteDialog.dismiss();
                                            Toast.makeText(mContext,"删除失败" + error.toString(),Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    deleteDialog.dismiss();
                                    myItems.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteDialog.dismiss();
                                }
                            });
//                    if (deleteDialog == null){
                        deleteDialog = deleteBuilder.create();
//                    }
                    deleteDialog.show();

                }
            });

            // TODO set data to view
        }

        @Override
        public void onClick(View v) {
//            if (myListener != null) {
//                myListener.onItemClick(item);
//            }
        }
    }


}
                                