package com.softtek.lai.picture.bean;

import java.io.Serializable;

/**
 * Created by jerry.guan on 2/20/2017.
 */

public class EaluationPicBean implements Serializable {
    public int height;
    public int width;
    public int x;
    public int y;
    public int attachmentId;
    public String imageId;
    //原图
    public String imageUrl;
    //缩略图
    public String smallImageUrl;

    @Override
    public String toString() {
        return "EaluationPicBean{" +
                "attachmentId=" + attachmentId +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                '}';
    }
}
