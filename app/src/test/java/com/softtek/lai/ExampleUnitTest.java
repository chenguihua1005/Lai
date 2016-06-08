package com.softtek.lai;

import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.utils.MD5;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int time=7200;
        int hour=time/3600;
        int minutes=time%3600/60;
        int second=time%3600%60;
        System.out.println(hour+":"+minutes+":"+second);
        assertEquals(4, 2 + 2);
    }
}