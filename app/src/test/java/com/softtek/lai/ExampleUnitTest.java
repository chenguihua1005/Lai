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
        System.out.println(MD5.md5WithEncoder("000000"));
        assertEquals(4, 2 + 2);
    }
}