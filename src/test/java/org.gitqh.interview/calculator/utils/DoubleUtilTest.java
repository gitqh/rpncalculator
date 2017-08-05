package org.gitqh.interview.calculator.utils;

import org.gitqh.interview.util.DoubleUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2017/8/4.
 */
public class DoubleUtilTest {

    @Test
    public void checkRealNumberTest() {
        String real1 = "12389.2348";
        String real2 = "12389.2a348";

        Double aDouble1 = DoubleUtil.tryParseDouble(real1);
        assertTrue("correct input check fail", aDouble1 != null);
        Double aDouble2 = DoubleUtil.tryParseDouble(real2);
        assertTrue("wrong input check fail", aDouble2 == null);
    }
}
