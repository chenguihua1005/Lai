package com.softtek.lai.utils;

import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by jarvis.liu on 4/11/2016.
 */
public class ShareUtils {
    public Context context;
    private UMSocialService controller;

    public ShareUtils(Context c) {
        context = c;
        controller = UMServiceFactory.getUMSocialService("com.umeng.share");
        setController(controller);
        addSinaPlatform();
        addWXPlatform();
        controller.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);
    }

    public UMSocialService getController() {
        return controller;
    }

    public void setController(UMSocialService controller) {
        this.controller = controller;
    }

    //添加新浪微博平台
    private void addSinaPlatform() {
        controller.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    private void addWXPlatform() {
        String appId = "wxd282ca7dfe25dd68";
        String appSecret = "b65fc1262a4c030e44be9e258fa22072";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

    }

    /**
     * @param title  标题
     * @param url   微信，朋友圈 分享链接
     * @param img   图片本地地址，eg：R.drawable.ic_launcher
     * @param weiXinShareContent  微信分享正文
     * @param sinaShareContent    新浪微博分享正文，请自行添加链接
     */
    public void setShareContent(String title, String url, int img, String weiXinShareContent, String sinaShareContent) {
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent(weiXinShareContent);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        weixinContent
                .setShareMedia(new UMImage(context, img));
        controller.setShareMedia(weixinContent);

        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent(weiXinShareContent);
        circleMedia.setTitle(title);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl(url);
        controller.setShareMedia(circleMedia);

        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent
                .setShareContent(sinaShareContent);
        sinaContent
                .setShareMedia(new UMImage(context, img));
        controller.setShareMedia(sinaContent);

    }

}
