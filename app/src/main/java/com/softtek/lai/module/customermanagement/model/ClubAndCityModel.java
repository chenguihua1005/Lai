package com.softtek.lai.module.customermanagement.model;

import java.util.List;

/**
 * Created by jia.lu on 1/2/2018.
 */

public class ClubAndCityModel {

    private List<RegionalCitiesBean> regionalCities;
    private List<ClubsBean> clubs;

    public List<RegionalCitiesBean> getRegionalCities() {
        return regionalCities;
    }

    public void setRegionalCities(List<RegionalCitiesBean> regionalCities) {
        this.regionalCities = regionalCities;
    }

    public List<ClubsBean> getClubs() {
        return clubs;
    }

    public void setClubs(List<ClubsBean> clubs) {
        this.clubs = clubs;
    }

    public static class RegionalCitiesBean {
        /**
         * RegionalName : 大东北
         * RegionalId : 4
         * RegionalCityList : [{"CityId":1,"CityName":"沈阳"},{"CityId":2,"CityName":"大连"},{"CityId":3,"CityName":"长春"},{"CityId":4,"CityName":"哈尔滨"},{"CityId":5,"CityName":"大庆"}]
         */

        private String RegionalName;
        private int RegionalId;
        private List<RegionalCityListBean> RegionalCityList;

        public String getRegionalName() {
            return RegionalName;
        }

        public void setRegionalName(String RegionalName) {
            this.RegionalName = RegionalName;
        }

        public int getRegionalId() {
            return RegionalId;
        }

        public void setRegionalId(int RegionalId) {
            this.RegionalId = RegionalId;
        }

        public List<RegionalCityListBean> getRegionalCityList() {
            return RegionalCityList;
        }

        public void setRegionalCityList(List<RegionalCityListBean> RegionalCityList) {
            this.RegionalCityList = RegionalCityList;
        }

        public static class RegionalCityListBean {
            /**
             * CityId : 1
             * CityName : 沈阳
             */

            private int CityId;
            private String CityName;

            public int getCityId() {
                return CityId;
            }

            public void setCityId(int CityId) {
                this.CityId = CityId;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }
        }
    }

    public static class ClubsBean {
        /**
         * clubId : ece4c2f6-6cce-4c5b-84bf-9dc79a75aae3
         * clubName : aaaa
         */

        private String clubId;
        private String clubName;

        public String getClubId() {
            return clubId;
        }

        public void setClubId(String clubId) {
            this.clubId = clubId;
        }

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }
    }
}
