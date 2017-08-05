package org.gitqh.interview.calculator.domain;

import org.gitqh.interview.domain.Calculator;
import org.gitqh.interview.exception.CalculatorException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Assert.assertTrue("testCase9 failed", "input could not be null\nstack:"
                .equals(calculator.getCurrentResultWithExpression(null)));
        calculator.clearStacks();

        Assert.assertTrue("testCase10 failed", "input could not be null\nstack:"
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

    @Test
    public void aritmeticOperatorsTest() throws Exception {
        calculator.evaluate("5 2");
        assertEquals(5, calculator.getValueStack().get(0), 0);
        assertEquals(2, calculator.getStackItem(1), 0);

        // substraction
        calculator.evaluate("clear");
        calculator.evaluate("5 2 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
        calculator.evaluate("3 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(0, calculator.getStackItem(0), 0);

        // negative
        calculator.evaluate("clear");
        calculator.evaluate("1 2 3 4 5 *");
        assertEquals(4, calculator.getValueStack().size());
        calculator.evaluate("clear 3 4 -");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(-1, calculator.getStackItem(0), 0);

        // division
        calculator.evaluate("clear");
        calculator.evaluate("7 12 2 /");
        assertEquals(7, calculator.getStackItem(0), 0);
        assertEquals(6, calculator.getStackItem(1), 0);
        calculator.evaluate("*");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(42, calculator.getStackItem(0), 0);
        calculator.evaluate("4 /");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(10.5, calculator.getStackItem(0), 0);

        //multiplication
        calculator.evaluate("clear");
        calculator.evaluate("1 2 3 4 5");
        calculator.evaluate("* * * *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(120, calculator.getStackItem(0), 0);
    }

    @Test
    public void sqrtTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("2 sqrt");
        calculator.evaluate("clear 9 sqrt");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
    }

    @Test
    public void insuficientParametersTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.evaluate("1 2 3 * 5 + * * 6 5");
        } catch (CalculatorException e) {
            assertEquals("operator * (position: 8): insufficient parameters", e.getMessage());
        }
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(11, calculator.getStackItem(0), 0);
    }

    @Test
    public void undoTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("5 4 3 2");
        assertEquals(4, calculator.getValueStack().size());
        calculator.evaluate("undo undo *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        calculator.evaluate("5 *");
        assertEquals(1, calculator.getValueStack().size());
        assertEquals(100, calculator.getStackItem(0), 0);
        calculator.evaluate("undo");
        assertEquals(2, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        assertEquals(5, calculator.getStackItem(1), 0);
        calculator.evaluate("+ undo - undo / undo * undo sqrt undo pow undo");
        assertEquals(2, calculator.getValueStack().size());
        assertEquals(20, calculator.getStackItem(0), 0.0000000001);
        assertEquals(5, calculator.getStackItem(1), 0.0000000001);
    }

    @Test(expected = CalculatorException.class)
    public void onlyOperatorsTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("+ +");
    }

    @Test(expected = CalculatorException.class)
    public void invalidCharactersTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("2 a +");
    }

    @Test(expected = CalculatorException.class)
    public void noSpacesTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("22+");
    }

    @Test(expected = CalculatorException.class)
    public void noSpaces2Test() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("2 2+ 3");
    }

    @Test(expected = CalculatorException.class)
    public void divideByZeroTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate("1 0 /");
    }

    @Test(expected = CalculatorException.class)
    public void nullInputTest() throws Exception {
        Calculator calculator = new Calculator();
        calculator.evaluate(null);
    }
}
