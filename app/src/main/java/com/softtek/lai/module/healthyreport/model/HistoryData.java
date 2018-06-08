package com.softtek.lai.module.healthyreport.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public class HistoryData implements Parcelable{

    private String AcInfoId;
    private String Weight;
    private String Pysical;
    private String Fat;
    private String Circum;
    private String Waistline;
    private String Hiplie;
    private String UpArmGirth;
    private String UpLegGirth;
    private String DoLegGirth;
    private String CreateDate;
    private String Sourcetype;

    public HistoryData() {
    }

    protected HistoryData(Parcel in) {
        AcInfoId = in.readString();
        Weight = in.readString();
        Pysical = in.readString();
        Fat = in.readString();
        Circum = in.readString();
        Waistline = in.readString();
        Hiplie = in.readString();
        UpArmGirth = in.readString();
        UpLegGirth = in.readString();
        DoLegGirth = in.readString();
        CreateDate = in.readString();
        Sourcetype = in.readString();
    }

    public static final Creator<HistoryData> CREATOR = new Creator<HistoryData>() {
        @Override
        public HistoryData createFromParcel(Parcel in) {
            return new HistoryData(in);
        }

        @Override
        public HistoryData[] newArray(int size) {
            return new HistoryData[size];
        }
    };

    public String getAcInfoId() {
        return AcInfoId;
    }

    public void setAcInfoId(String acInfoId) {
        AcInfoId = acInfoId;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPysical() {
        return Pysical;
    }

    public void setPysical(String pysical) {
        Pysical = pysical;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getCircum() {
        return Circum;
    }

    public void setCircum(String circum) {
        Circum = circum;
    }

    public String getWaistline() {
        return Waistline;
    }

    public void setWaistline(String waistline) {
        Waistline = waistline;
    }

    public String getHiplie() {
        return Hiplie;
    }

    public void setHiplie(String hiplie) {
        Hiplie = hiplie;
    }

    public String getUpArmGirth() {
        return UpArmGirth;
    }

    public void setUpArmGirth(String upArmGirth) {
        UpArmGirth = upArmGirth;
    }

    public String getUpLegGirth() {
        return UpLegGirth;
    }

    public void setUpLegGirth(String upLegGirth) {
        UpLegGirth = upLegGirth;
    }

    public String getDoLegGirth() {
        return DoLegGirth;
    }

    public void setDoLegGirth(String doLegGirth) {
        DoLegGirth = doLegGirth;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getSourcetype() {
        return Sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        Sourcetype = sourcetype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AcInfoId);
        dest.writeString(Weight);
        dest.writeString(Pysical);
        dest.writeString(Fat);
        dest.writeString(Circum);
        dest.writeString(Waistline);
        dest.writeString(Hiplie);
        dest.writeString(UpArmGirth);
        dest.writeString(UpLegGirth);
        dest.writeString(DoLegGirth);
        dest.writeString(CreateDate);
        dest.writeString(Sourcetype);
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "AcInfoId='" + AcInfoId + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Pysical='" + Pysical + '\'' +
                ", Fat='" + Fat + '\'' +
                ", Circum='" + Circum + '\'' +
                ", Waistline='" + Waistline + '\'' +
                ", Hiplie='" + Hiplie + '\'' +
                ", UpArmGirth='" + UpArmGirth + '\'' +
                ", UpLegGirth='" + UpLegGirth + '\'' +
                ", DoLegGirth='" + DoLegGirth + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", Sourcetype='" + Sourcetype + '\'' +
                '}';
    }
}
