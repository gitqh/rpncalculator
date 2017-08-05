package org.gitqh.interview.calculator.operation;

import org.gitqh.interview.operation.Operator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2017/8/4.
 */
class OperatorTest {
    @Test
    public void checkOperatorTest() {
        String s1 = "+";
        String s2 = "-";
        String s3 = "*";
        String s4 = "/";
        String s5 = "undo";
        String s6 = "sqrt";
        String s7 = "clear";
        String s8 = "no";
        assertTrue("+ check fail", Operator.getEnum(s1) != null);
        assertTrue("- check fail", Operator.getEnum(s2) != null);
        assertTrue("* check fail", Operator.getEnum(s3) != null);
        assertTrue("/ check fail", Operator.getEnum(s4) != null);
        assertTrue("undo check fail", Operator.getEnum(s5) != null);
        assertTrue("sqrt check fail", Operator.getEnum(s6) != null);
        assertTrue("clear check fail", Operator.getEnum(s7) != null);
        assertTrue("no check fail", Operator.getEnum(s8) == null);
    }
}
