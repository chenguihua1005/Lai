package com.softtek.lai.module.sport.model;

import java.util.List;

/**
 * Created by jerry.guan on 6/3/2016.
 */
public class Trajectory {

    //private List<LatLon> Trajectory;
    private List<SportModel> Trajectory;

    public Trajectory(List<SportModel> trajectory) {
        Trajectory = trajectory;
    }

    public Trajectory() {
    }

    public List<SportModel> getTrajectory() {
        return Trajectory;
    }

    public void setTrajectory(List<SportModel> trajectory) {
        Trajectory = trajectory;
    }

    /* public List<LatLon> getTrajectory() {
        return Trajectory;
    }

    public void setTrajectory(List<LatLon> trajectory) {
        Trajectory = trajectory;
    }*/
}
