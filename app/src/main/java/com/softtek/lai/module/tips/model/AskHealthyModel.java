package com.softtek.lai.module.tips.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 4/27/2016.
 * 健康咨询
 */
public class AskHealthyModel implements Parcelable{

    private String Tips_Id;
    private String Tips_Type;
    private String Tips_Title;
    private String Tips_TagTitle;
    private String Tips_Content;
    private String Tips_Addr;
    private String Tips_Link;
    private String Tips_video_backPicture;


    public AskHealthyModel() {
    }


    protected AskHealthyModel(Parcel in) {
        Tips_Id = in.readString();
        Tips_Type = in.readString();
        Tips_Title = in.readString();
        Tips_TagTitle = in.readString();
        Tips_Content = in.readString();
        Tips_Addr = in.readString();
        Tips_Link = in.readString();
        Tips_video_backPicture=in.readString();
    }

    public static final Creator<AskHealthyModel> CREATOR = new Creator<AskHealthyModel>() {
        @Override
        public AskHealthyModel createFromParcel(Parcel in) {
            return new AskHealthyModel(in);
        }

        @Override
        public AskHealthyModel[] newArray(int size) {
            return new AskHealthyModel[size];
        }
    };

    public String getTips_TagTitle() {
        return Tips_TagTitle;
    }

    public void setTips_TagTitle(String tips_TagTitle) {
        Tips_TagTitle = tips_TagTitle;
    }

    public String getTips_video_backPicture() {
        return Tips_video_backPicture;
    }

    public void setTips_video_backPicture(String tips_video_backPicture) {
        Tips_video_backPicture = tips_video_backPicture;
    }

    public String getTips_Link() {
        return Tips_Link;
    }

    public void setTips_Link(String tips_Link) {
        Tips_Link = tips_Link;
    }

    public String getTips_Id() {
        return Tips_Id;
    }

    public void setTips_Id(String tips_Id) {
        Tips_Id = tips_Id;
    }

    public String getTips_Type() {
        return Tips_Type;
    }

    public void setTips_Type(String tips_Type) {
        Tips_Type = tips_Type;
    }

    public String getTips_Title() {
        return Tips_Title;
    }

    public void setTips_Title(String tips_Title) {
        Tips_Title = tips_Title;
    }

    public String getTips_Content() {
        return Tips_Content;
    }

    public void setTips_Content(String tips_Content) {
        Tips_Content = tips_Content;
    }

    public String getTips_Addr() {
        return Tips_Addr;
    }

    public void setTips_Addr(String tips_Addr) {
        Tips_Addr = tips_Addr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Tips_Id);
        dest.writeString(Tips_Type);
        dest.writeString(Tips_Title);
        dest.writeString(Tips_TagTitle);
        dest.writeString(Tips_Content);
        dest.writeString(Tips_Addr);
        dest.writeString(Tips_Link);
        dest.writeString(Tips_video_backPicture);
    }
}
