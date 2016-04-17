package com.softtek.lai.module.confirmInfo.EventModel;

import com.softtek.lai.module.confirmInfo.model.GetConfirmInfoModel;

/**
 * Created by julie.zhu on 4/16/2016.
 */
public class ConinfoEvent {
        private GetConfirmInfoModel getConfirmInfoModel;

        public GetConfirmInfoModel getConfirmInfoModel() {
            return getConfirmInfoModel;
        }

        public ConinfoEvent(GetConfirmInfoModel getConfirmInfoModel) {
            this.getConfirmInfoModel = getConfirmInfoModel;
        }
        public void setgetConfirmInfoModels(GetConfirmInfoModel getConfirmInfoModel) {
            this.getConfirmInfoModel = getConfirmInfoModel;
        }
}
