package com.softtek.lai;

import com.softtek.lai.utils.DateUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(DateUtil.weeHours(1));
        assertEquals(4, 2 + 2);
    }
}