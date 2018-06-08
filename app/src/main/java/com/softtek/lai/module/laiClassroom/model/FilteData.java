package com.softtek.lai.module.laiClassroom.model;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class FilteData {

    private List<Filter> CategoryList;
    private List<Filter> OrderByList;
    private List<Filter> MediaTypeList;

    public List<Filter> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<Filter> categoryList) {
        CategoryList = categoryList;
    }

    public List<Filter> getOrderByList() {
        return OrderByList;
    }

    public void setOrderByList(List<Filter> orderByList) {
        OrderByList = orderByList;
    }

    public List<Filter> getMediaTypeList() {
        return MediaTypeList;
    }

    public void setMediaTypeList(List<Filter> mediaTypeList) {
        MediaTypeList = mediaTypeList;
    }

    public class Filter{
        private String ID;
        private String Name;
        private int selected;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }
    }

}
