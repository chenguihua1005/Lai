package com.softtek.lai.module.retest.eventModel;

import com.softtek.lai.module.retest.model.RetestAuditModel;

import java.util.List;

/**
 * Created by lareina.qiao on 4/5/2016.
 */
public class RetestAuditModelEvent {

    private List<RetestAuditModel> retestAuditModels;

    public RetestAuditModelEvent(List<RetestAuditModel> retestAuditModels) {
        this.retestAuditModels = retestAuditModels;
    }

    public List<RetestAuditModel> getRetestAuditModels() {
        return retestAuditModels;
    }

    public void setRetestAuditModels(List<RetestAuditModel> retestAuditModels) {
        this.retestAuditModels = retestAuditModels;
    }
}
