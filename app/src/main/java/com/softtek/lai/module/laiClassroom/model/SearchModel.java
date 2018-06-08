package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by jia.lu on 2017/3/10.
 */

public class SearchModel {

    private List<ArticleListBean> ArticleList;
    private String TotalPage;

    public String getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(String totalPage) {
        TotalPage = totalPage;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class ArticleListBean {
        /**
         * ArticleId : 8d36cbea-219c-4010-beee-324f298d930d
         * MediaType : 1
         * Title : 康宝莱健康产品
         * IsMultiPic : 1
         * ArticImg : ["201605061545548587113464.jpg","201605061522222697247565.png","201605061545544596534444.jpg"]
         * ArticUrl : null
         * VideoTime : null
         * Clicks : 0
         * TopicId : 4644171e-08f6-4ce9-b81c-562d0082cbec
         * Topic : 健康产品
         * CreateDate : 2017-03-10 12:00:00
         */

        private String ArticleId;
        private int MediaType;
        private String Title;
        private int IsMultiPic;
        private String ArticUrl;
        private String VideoTime;
        private int Clicks;
        private String TopicId;
        private String Topic;
        private String CreateDate;
        private List<String> ArticImg;

        public String getArticleId() {
            return ArticleId;
        }

        public void setArticleId(String ArticleId) {
            this.ArticleId = ArticleId;
        }

        public int getMediaType() {
            return MediaType;
        }

        public void setMediaType(int MediaType) {
            this.MediaType = MediaType;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getIsMultiPic() {
            return IsMultiPic;
        }

        public void setIsMultiPic(int IsMultiPic) {
            this.IsMultiPic = IsMultiPic;
        }

        public String getArticUrl() {
            return ArticUrl;
        }

        public void setArticUrl(String ArticUrl) {
            this.ArticUrl = ArticUrl;
        }

        public String getVideoTime() {
            return VideoTime;
        }

        public void setVideoTime(String VideoTime) {
            this.VideoTime = VideoTime;
        }

        public int getClicks() {
            return Clicks;
        }

        public void setClicks(int Clicks) {
            this.Clicks = Clicks;
        }

        public String getTopicId() {
            return TopicId;
        }

        public void setTopicId(String TopicId) {
            this.TopicId = TopicId;
        }

        public String getTopic() {
            return Topic;
        }

        public void setTopic(String Topic) {
            this.Topic = Topic;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public List<String> getArticImg() {
            return ArticImg;
        }

        public void setArticImg(List<String> ArticImg) {
            this.ArticImg = ArticImg;
        }
    }
}
