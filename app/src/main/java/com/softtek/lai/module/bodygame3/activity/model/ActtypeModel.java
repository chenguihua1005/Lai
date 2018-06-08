package com.softtek.lai.module.bodygame3.activity.model;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public class ActtypeModel {
    //    "ActivityTypeId": 2,
//            "ActivityTypeName": "跑步",
//            "ActivityTypeIcon": "sdsdfdf"
    private int ActivityTypeId;
    private String ActivityTypeName;
    private String ActivityTypeIcon;

    public ActtypeModel(int activityTypeId, String activityTypeName, String activityTypeIcon) {
        ActivityTypeId = activityTypeId;
        ActivityTypeName = activityTypeName;
        ActivityTypeIcon = activityTypeIcon;
    }

    public int getActivityTypeId() {
        return ActivityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        ActivityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return ActivityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        ActivityTypeName = activityTypeName;
    }

    public String getActivityTypeIcon() {
        return ActivityTypeIcon;
    }

    public void setActivityTypeIcon(String activityTypeIcon) {
        ActivityTypeIcon = activityTypeIcon;
    }
}
