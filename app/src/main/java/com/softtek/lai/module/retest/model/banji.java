package com.softtek.lai.module.retest.model;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class Banji {
    private String classname;
    private String classtitle;
    private int personum;

    public Banji(String classname, String classtitle, int personum) {
        this.classname = classname;
        this.classtitle = classtitle;
        this.personum = personum;
    }

    public int getPersonum() {
        return personum;
    }

    public void setPersonum(int personum) {
        this.personum = personum;
    }

    public String getClasstitle() {
        return classtitle;
    }

    public void setClasstitle(String classtitle) {
        this.classtitle = classtitle;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


}
