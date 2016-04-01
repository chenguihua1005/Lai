package com.softtek.lai.module.bodygamest.eventModel;

import com.softtek.lai.module.bodygamest.model.DownPhotoModel;


import java.util.List;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class PhotoListEvent {
    private List<DownPhotoModel> downPhotoModels;

    public List<DownPhotoModel> getDownPhotoModels() {
        return downPhotoModels;
    }

    public void setDownPhotoModels(List<DownPhotoModel> downPhotoModels) {
        this.downPhotoModels = downPhotoModels;
    }

    public PhotoListEvent(List<DownPhotoModel> downPhotoModels) {
        this.downPhotoModels = downPhotoModels;
    }
}
