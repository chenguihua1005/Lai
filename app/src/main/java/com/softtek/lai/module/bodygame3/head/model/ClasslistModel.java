package com.softtek.lai.module.bodygame3.head.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ClasslistModel implements Parcelable {
    private String ClassId;
    private String ClassName;
    private String ClassCode;//班级编号
//    private int ClassMasterId;//班级总教练id
//    private String ClassMasterPhoto;//总教练照片
//    private String ClassMasterName;//总教练名称
//    private String ClassStart;
//    private int ClassMemberNum;//学员人数

    protected ClasslistModel(Parcel in) {
        ClassId = in.readString();
        ClassName = in.readString();
        ClassCode = in.readString();
//        ClassMasterId = in.readInt();
//        ClassMasterPhoto = in.readString();
//        ClassMasterName = in.readString();
//        ClassStart = in.readString();
//        ClassMemberNum = in.readInt();
    }

    public static final Creator<ClasslistModel> CREATOR = new Creator<ClasslistModel>() {
        @Override
        public ClasslistModel createFromParcel(Parcel in) {
            return new ClasslistModel(in);
        }

        @Override
        public ClasslistModel[] newArray(int size) {
            return new ClasslistModel[size];
        }
    };

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

//    public int getClassMasterId() {
//        return ClassMasterId;
//    }
//
//    public void setClassMasterId(int classMasterId) {
//        ClassMasterId = classMasterId;
//    }
//
//    public String getClassMasterPhoto() {
//        return ClassMasterPhoto;
//    }
//
//    public void setClassMasterPhoto(String classMasterPhoto) {
//        ClassMasterPhoto = classMasterPhoto;
//    }
//
//    public String getClassMasterName() {
//        return ClassMasterName;
//    }
//
//    public void setClassMasterName(String classMasterName) {
//        ClassMasterName = classMasterName;
//    }
//
//    public String getClassStart() {
//        return ClassStart;
//    }
//
//    public void setClassStart(String classStart) {
//        ClassStart = classStart;
//    }
//
//    public int getClassMemberNum() {
//        return ClassMemberNum;
//    }
//
//    public void setClassMemberNum(int classMemberNum) {
//        ClassMemberNum = classMemberNum;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ClassId);
        parcel.writeString(ClassName);
        parcel.writeString(ClassCode);
//        parcel.writeInt(ClassMasterId);
//        parcel.writeString(ClassMasterPhoto);
//        parcel.writeString(ClassMasterName);
//        parcel.writeString(ClassStart);
//        parcel.writeInt(ClassMemberNum);
    }
}
