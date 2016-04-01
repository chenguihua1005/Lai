package com.softtek.lai.module.bodygamest.eventModel;

import com.softtek.lai.module.bodygamest.model.DownPhoto;


import java.util.List;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public class PhotoListEvent {
    private List<DownPhoto> downPhotos;

    public List<DownPhoto> getDownPhotos() {
        return downPhotos;
    }

    public void setDownPhotos(List<DownPhoto> downPhotos) {
        this.downPhotos = downPhotos;
    }

    public PhotoListEvent(List<DownPhoto> downPhotos) {
        this.downPhotos = downPhotos;
    }
}
