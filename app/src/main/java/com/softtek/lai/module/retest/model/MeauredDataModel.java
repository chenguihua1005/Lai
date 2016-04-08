package com.softtek.lai.module.retest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lareina.qiao on 4/6/2016.
 */
public class MeauredDataModel implements Serializable{

    private List<ItemsModel> items;
    private String totalcount;
    private String pageindex;
    private String pagesize;
    private String totalpage;

    @Override
    public String toString() {
        return "MeauredDataModel{" +
                "items=" + items +
                ", totalcount='" + totalcount + '\'' +
                ", pageindex='" + pageindex + '\'' +
                ", pagesize='" + pagesize + '\'' +
                ", totalpage='" + totalpage + '\'' +
                '}';
    }

    public List<ItemsModel> getItems() {
        return items;
    }

    public void setItems(List<ItemsModel> items) {
        this.items = items;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getPageindex() {
        return pageindex;
    }

    public void setPageindex(String pageindex) {
        this.pageindex = pageindex;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
}
