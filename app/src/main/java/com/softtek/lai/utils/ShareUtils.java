package com.softtek.lai.utils;

import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.handler.UMWXHandler;
import com.umeng.socialize.media.UMImage;


/**
 * Created by jarvis.liu on 4/11/2016.
 */
public class ShareUtils {
    public Context context;
    //private UMSocialService controller;

//    public ShareUtils(Context c) {
////        context = c;
////        controller = UMServiceFactory.getUMSocialService("com.umeng.share");
////        setController(controller);
////        addSinaPlatform();
////        addWXPlatform();
////        new
////        controller.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
////                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);
//    }
//    public ShareUtils(Context c,String type) {
//        context = c;
//        controller = UMServiceFactory.getUMSocialService("com.umeng.share");
//        setController(controller);
//        addSinaPlatform();
//        addWXPlatform();
//        if("sina".equals(type)){
//
//        }
//    }
//
//    public UMSocialService getController() {
//        return controller;
//    }
//
//    public void setController(UMSocialService controller) {
//        this.controller = controller;
//    }
//
//    //添加新浪微博平台
//    private void addSinaPlatform() {
//        controller.getConfig().setSsoHandler(new SinaSsoHandler());
//    }
//
//    private void addWXPlatform() {
//        String appId = "wxdef946afe85d49a2";
//        String appSecret = "8f2e4913b794a310dd6662014748c43d";
//// 添加微信平台
//
//        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
//        wxHandler.addToSocialSDK();
//// 添加微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//
//    }
//
//    /**
//     * @param title  标题
//     * @param url   微信，朋友圈 分享链接
//     * @param img   图片本地地址，eg：R.drawable.ic_launcher
//     * @param weiXinShareContent  微信分享正文
//     * @param sinaShareContent    新浪微博分享正文，请自行添加链接
//     */
//    public void setShareContent(String title, String url, int img, String weiXinShareContent, String sinaShareContent) {
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        weixinContent.setShareContent(weiXinShareContent);
//        weixinContent.setTitle(title);
//        weixinContent.setTargetUrl(url);
//        weixinContent.setShareMedia(new UMImage(context, img));
//        controller.setShareMedia(weixinContent);
//
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(weiXinShareContent);
//        circleMedia.setTitle(title);
//        circleMedia.setShareMedia(new UMImage(context, img));
//        // circleMedia.setShareMedia(uMusic);
//        // circleMedia.setShareMedia(video);
//        circleMedia.setTargetUrl(url);
//        controller.setShareMedia(circleMedia);
//
//        SinaShareContent sinaContent = new SinaShareContent();
//        sinaContent.setTitle(title);
//        sinaContent.setShareContent(sinaShareContent);
//        sinaContent.setShareMedia(new UMImage(context, img));
//        controller.setShareMedia(sinaContent);
//    }

}
