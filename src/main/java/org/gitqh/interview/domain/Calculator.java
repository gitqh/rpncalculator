package org.gitqh.interview.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.gitqh.interview.exception.CalculatorException;
import org.gitqh.interview.model.Instruction;
import org.gitqh.interview.operation.Operator;
import org.gitqh.interview.util.DoubleUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Created by gitqh on 05/08/2017.
 */
public class Calculator {

    @Getter
    private Stack<Double> valueStack = new Stack<>();
    private Stack<Instruction> instructionStack = new Stack<>();
    private boolean undoFlag = false;

    /**
     * Evaluate a RPN expression and pushes the result into the valueStack
     *
     * @param input                     valid RPN expression
     * @throws CalculatorException      CalculatorException
     */
    public void evaluate(String input) throws CalculatorException {
        if (StringUtils.isBlank(input)) {
            throw new CalculatorException("input could not be null");
        }
        int currentTokenIndex = 0;
        for (String s : input.toLowerCase().split(" ")) {
            currentTokenIndex++;
            Operator operator = Operator.getEnum(s);
            if (operator == null) {
                Double value = DoubleUtil.tryParseDouble(s);
                if (value == null) {
                    throw new CalculatorException("can not parse input");
                } else {
                    valueStack.push(value);
                    if (!undoFlag) {
                        instructionStack.push(null);
                    }
                }
            } else {
                process(operator, currentTokenIndex);
            }
        }
    }

    /**
     * process a RPN operator
     *
     * @param operator                 RPN operator
     * @param index                    operator position
     * @throws CalculatorException     CalculatorException
     */
    private void process(Operator operator, int index) throws CalculatorException {
        // checking there are enough operand for the operation
        if (operator.getOperandsNumber() > valueStack.size()) {
            throw new CalculatorException(
                    String.format("operator %s (position: %d): insufficient parameters", operator.getSymbol(), index*2-1));
        }

        // clear value stack and instructions stack
        if (Operator.CLEAR == operator) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (Operator.UNDO == operator) {
            undoFlag = true;
            undoLastInstruction();
            return;
        }

        // getting operands
        Double firstOperand = valueStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? valueStack.pop() : null;

        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);
        if (result != null) {
            valueStack.push(result);
            if (!undoFlag) {
                instructionStack.push(new Instruction(Operator.getEnum(operator.toString()), firstOperand));
            } else {
                undoFlag = false;
            }
        }
    }

    public void clearStacks() {
        valueStack.clear();
        instructionStack.clear();
    }

    /**
     * undo the last operation
     *
     * @throws CalculatorException      CalculatorException
     */
    private void undoLastInstruction() throws CalculatorException {
        if (instructionStack.isEmpty()) {
            return;
        }
        Instruction lastInstruction = instructionStack.pop();
        if (lastInstruction == null) {
            valueStack.pop();
        } else {
            evaluate(lastInstruction.getReverseInstruction());
        }
    }

    public String getValueStackToString() {
        DecimalFormat fmt = new DecimalFormat("0.##########");
        fmt.setRoundingMode(RoundingMode.FLOOR);
        StringBuilder sb = new StringBuilder("stack: ");
        for (Double value: valueStack) {
            sb.append(fmt.format(value)).append(" ");
        }
        return sb.toString().trim();
    }

    public String getCurrentResultWithExpression(String inputExpression) {
        try {
            evaluate(inputExpression);
        } catch (CalculatorException e) {
            return e.getMessage() + "\n" + getValueStackToString();
        }
        return getValueStackToString();
    }

    public Double getStackItem(int index) {
        return valueStack.get(index);
    }
}
