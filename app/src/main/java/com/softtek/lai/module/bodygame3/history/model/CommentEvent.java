package com.softtek.lai.module.bodygame3.history.model;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jia.lu on 12/12/2016.
 */

public class CommentEvent {
    private String healthId;
    private long accountId;
    private String comments;
    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
