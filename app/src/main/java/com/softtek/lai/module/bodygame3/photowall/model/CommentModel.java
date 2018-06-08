package com.softtek.lai.module.bodygame3.photowall.model;

/**
 * Created by lareina.qiao on 12/3/2016.
 */
public class CommentModel {
    private String Commnets;
    private String CommentUserName;

    @Override
    public String toString() {
        return "CommentModel{" +
                "Commnets='" + Commnets + '\'' +
                ", CommentUserName='" + CommentUserName + '\'' +
                '}';
    }

    public String getCommnets() {
        return Commnets;
    }

    public void setCommnets(String commnets) {
        Commnets = commnets;
    }

    public String getCommentUserName() {
        return CommentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        CommentUserName = commentUserName;
    }
}
