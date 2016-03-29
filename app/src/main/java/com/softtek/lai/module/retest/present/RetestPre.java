package com.softtek.lai.module.retest.present;


import com.softtek.lai.module.retest.model.RetestAudit;
import com.softtek.lai.module.retest.model.RetestWrite;

/**
 * Created by lareina.qiao on 3/22/2016.
 */
public interface RetestPre {
    void doGetRetestclass(long id);
    void doGetqueryResult(String str);
    void doGetBanjiStudent(long classId);
    void doGetAudit(long accountId, long classId, String typeDate);
    void doGetWrite(long accountId, long loginId, RetestWrite retestWrite);
    void doPostWrite(String loginId,String accountId,String typeDate,RetestAudit retestAudit);
    void goGetPicture(String filePath);
    void doGetMeasure(String accesstoken,String phone);

}
