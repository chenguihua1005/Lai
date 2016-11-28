package com.softtek.lai.module.bodygame3.more.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jerry.guan on 11/17/2016.
 * 班级模型
 */

public class ClassModel implements Parcelable{

    private String ClassId;
    private String ClassCode;
    private String ClassName;
    private int ClassRole;//班级角色1:总教练,2:教练,3:助教,4:学员
    private String ClassMasterName;
    private String HXGroupId;
    private List<String> ClassMeasureDateList;

    protected ClassModel(Parcel in) {
        ClassId = in.readString();
        ClassCode = in.readString();
        ClassName = in.readString();
        ClassRole = in.readInt();
        ClassMasterName = in.readString();
        ClassMeasureDateList = in.createStringArrayList();
        HXGroupId=in.readString();
    }

    public static final Creator<ClassModel> CREATOR = new Creator<ClassModel>() {
        @Override
        public ClassModel createFromParcel(Parcel in) {
            return new ClassModel(in);
        }

        @Override
        public ClassModel[] newArray(int size) {
            return new ClassModel[size];
        }
    };

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getClassRole() {
        return ClassRole;
    }

    public void setClassRole(int classRole) {
        ClassRole = classRole;
    }

    public String getClassMasterName() {
        return ClassMasterName;
    }

    public void setClassMasterName(String classMasterName) {
        ClassMasterName = classMasterName;
    }

    public List<String> getClassMeasureDateList() {
        return ClassMeasureDateList;
    }

    public void setClassMeasureDateList(List<String> classMeasureDateList) {
        ClassMeasureDateList = classMeasureDateList;
    }

    public String getHXGroupId() {
        return HXGroupId;
    }

    public void setHXGroupId(String HXGroupId) {
        this.HXGroupId = HXGroupId;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "ClassId='" + ClassId + '\'' +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", ClassRole=" + ClassRole +
                ", ClassMasterName='" + ClassMasterName + '\'' +
                ", HXGroupId=" + HXGroupId +
                ", ClassMeasureDateList=" + ClassMeasureDateList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ClassId);
        parcel.writeString(ClassCode);
        parcel.writeString(ClassName);
        parcel.writeInt(ClassRole);
        parcel.writeString(ClassMasterName);
        parcel.writeStringList(ClassMeasureDateList);
        parcel.writeString(HXGroupId);
    }
}
