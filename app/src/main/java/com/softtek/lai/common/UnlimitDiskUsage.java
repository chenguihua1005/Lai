package com.softtek.lai.common;

import com.danikula.videocache.file.LruDiskUsage;

import java.io.File;

/**
 * Created by jerry.guan on 6/1/2017.
 */

public class UnlimitDiskUsage extends LruDiskUsage{
    @Override
    protected boolean accept(File file, long l, int i) {
        return true;
    }
}
