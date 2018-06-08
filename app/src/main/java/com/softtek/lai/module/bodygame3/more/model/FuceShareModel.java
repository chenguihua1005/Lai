package com.softtek.lai.module.bodygame3.more.model;

/**
 * Created by jessica.zhang on 3/14/2017.
 */

public class FuceShareModel {
    private String ShareContent;//分享内容
    private String ShareLinkUrl;//分享链接
    private String ShareTitle;// 分享标题

    public FuceShareModel(String shareContent, String shareLinkUrl, String shareTitle) {
        ShareContent = shareContent;
        ShareLinkUrl = shareLinkUrl;
        ShareTitle = shareTitle;
    }

    public String getShareTitle() {
        return ShareTitle;
    }

    public void setShareTitle(String shareTitle) {
        ShareTitle = shareTitle;
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
