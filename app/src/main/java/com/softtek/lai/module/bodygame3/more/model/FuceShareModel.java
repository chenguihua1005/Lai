package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jessica.zhang on 3/14/2017.
 */

public class FuceShareModel {
    private String ShareContent;//分享内容
    private String ShareLinkUrl;//分享链接

    public FuceShareModel(String shareContent, String shareLinkUrl) {
        ShareContent = shareContent;
        ShareLinkUrl = shareLinkUrl;
    }

    public String getShareContent() {
        return ShareContent;
    }

    public void setShareContent(String shareContent) {
        ShareContent = shareContent;
    }

    public String getShareLinkUrl() {
        return ShareLinkUrl;
    }

    public void setShareLinkUrl(String shareLinkUrl) {
        ShareLinkUrl = shareLinkUrl;
    }
}
