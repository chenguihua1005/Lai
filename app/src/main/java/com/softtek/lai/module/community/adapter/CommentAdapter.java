package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.community.model.Comment;

import java.util.List;

/**
 * Created by jerry.guan on 2/7/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        Comment comment=commentList.get(position);
        holder.rl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv_comment.setHighlightColor(ContextCompat.getColor(context,android.R.color.transparent));
        holder.tv_comment.setText(getClickableSpan(comment.CommentUserName,comment.Comment));
        holder.tv_comment.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getItemCount() {
        return commentList==null?0:commentList.size();
    }
    private SpannableStringBuilder getClickableSpan(String name,String comment){
        SpannableStringBuilder ssb=new SpannableStringBuilder();
        SpannableString ss=new SpannableString(name);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.i("测试成功","富文本点击了");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xFF576A80);
                ds.setUnderlineText(false);//去除超链接的下划线
            }
        }, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.append(ss);
        ssb.append("："+comment);
        return ssb;
    }

    public class CommentHolder extends RecyclerView.ViewHolder{

        RelativeLayout rl_comment;
        TextView tv_comment;

        public CommentHolder(View itemView) {
            super(itemView);
            rl_comment= (RelativeLayout) itemView.findViewById(R.id.rl_comment);
            tv_comment= (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }
}
