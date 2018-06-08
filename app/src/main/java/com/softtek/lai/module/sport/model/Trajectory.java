package com.softtek.lai.module.sport.model;

import java.util.List;

/**
 * Created by jerry.guan on 6/3/2016.
 */
public class Trajectory {

    private List<SportModel> Trajectory;
    private List<KilometrePace> kilometrePaces;

    public Trajectory(List<SportModel> trajectory, List<KilometrePace> kilometrePaces) {
        Trajectory = trajectory;
        this.kilometrePaces = kilometrePaces;
    }

    public Trajectory() {
    }

    public List<KilometrePace> getKilometrePaces() {
        return kilometrePaces;
    }

    public void setKilometrePaces(List<KilometrePace> kilometrePaces) {
        this.kilometrePaces = kilometrePaces;
    }

    public List<SportModel> getTrajectory() {
        return Trajectory;
    }

    public void setTrajectory(List<SportModel> trajectory) {
        Trajectory = trajectory;
    }

}
