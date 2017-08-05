package org.gitqh.interview.calculator.domain;

import org.gitqh.interview.domain.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator;

    @Before
    public void initial() {
        calculator = new Calculator();
    }

    @After
    public void release() {
        calculator.clearStacks();
        calculator = null;
    }

    @Test
    public void calculatorTest(){
        //<editor-fold desc="basic testing cases">
        Assert.assertTrue("testCase1 failed", "stack: 5 2"
                .equals(calculator.getCurrentResultWithExpression("5 2")));
        calculator.clearStacks();

        Assert.assertTrue("testCase2 failed", "stack: 3"
                .equals(calculator.getCurrentResultWithExpression("2 sqrt clear 9 sqrt")));
        calculator.clearStacks();

        Assert.assertTrue("testCase3 failed", "stack:"
                .equals(calculator.getCurrentResultWithExpression("5 2 - clear")));
        calculator.clearStacks();

        Assert.assertTrue("testCase4 failed", "stack: 20 5"
                .equals(calculator.getCurrentResultWithExpression("5 4 3 2 undo undo * 5 * undo")));
        calculator.clearStacks();

        Assert.assertTrue("testCase5 failed", "stack: 10.5"
                .equals(calculator.getCurrentResultWithExpression("7 12 2 / * 4 /")));
        calculator.clearStacks();

        Assert.assertTrue("testCase6 failed", "stack: -1"
                .equals(calculator.getCurrentResultWithExpression("1 2 3 4 5 * clear 3 4 -")));
        calculator.clearStacks();

        Assert.assertTrue("testCase7 failed", "stack: 120"
                .equals(calculator.getCurrentResultWithExpression("1 2 3 4 5 * * * *")));
        calculator.clearStacks();

        Assert.assertTrue("testCase8 failed", "operator * (position: 15): insufficient parameters\nstack: 11"
                .equals(calculator.getCurrentResultWithExpression("1 2 3 * 5 + * * 6 5")));
        calculator.clearStacks();
        //</editor-fold>

        //<editor-fold desc="boundary value testing cases">
        Assert.assertTrue("testCase9 failed", "input could not be null"
                .equals(calculator.getCurrentResultWithExpression(null)));
        calculator.clearStacks();

        Assert.assertTrue("testCase10 failed", "input could not be null"
                .equals(calculator.getCurrentResultWithExpression("")));
        calculator.clearStacks();

        Assert.assertTrue("testCase11 failed", "stack:"
                .equals(calculator.getCurrentResultWithExpression("undo undo")));
        calculator.clearStacks();

        Assert.assertTrue("testCase12 failed", "operator sqrt (position: 1): insufficient parameters\nstack:"
                .equals(calculator.getCurrentResultWithExpression("sqrt")));
        calculator.clearStacks();

        Assert.assertTrue("testCase13 failed", "operator sqrt (position: 7): insufficient parameters\nstack:"
                .equals(calculator.getCurrentResultWithExpression("2 sqrt clear sqrt")));
        calculator.clearStacks();
        //</editor-fold>

        //<editor-fold desc="decimal places testing cases">
        Assert.assertTrue("testCase14 failed", "stack: 1.4142135623"
                .equals(calculator.getCurrentResultWithExpression("2 sqrt")));
        calculator.clearStacks();
        //</editor-fold>

    }
}
