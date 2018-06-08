package com.softtek.lai.module.sport.model;

/**
 * Created by jerry.guan on 6/23/2016.
 */
public class Weather {

    private String city;//城市
    private String wenDu;//温度
    private String aqi;//空气指数
    private String quality;//空气质量
    private String sport;//运动指数

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWenDu() {
        return wenDu;
    }

    public void setWenDu(String wenDu) {
        this.wenDu = wenDu;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", wenDu='" + wenDu + '\'' +
                ", aqi='" + aqi + '\'' +
                ", quality='" + quality + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
