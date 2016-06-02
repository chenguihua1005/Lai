package com.softtek.lai;

import com.softtek.lai.utils.DateUtil;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(new DecimalFormat("#0.00").format(1245.78787878));
        assertEquals(4, 2 + 2);
    }
}