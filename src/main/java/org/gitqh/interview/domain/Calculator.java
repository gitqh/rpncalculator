package org.gitqh.interview.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.gitqh.interview.exception.CalculatorException;
import org.gitqh.interview.operation.Instruction;
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
    private Stack<Double> valueStack = new Stack<Double>();
    private Stack<Instruction> instructionStack = new Stack<Instruction>();
    private int currentTokenIndex = 0;

    /**
     * Processes a RPN string token
     *
     * @param token           RPN token
     * @param isUndoOperator  indicates if the operation is an undo operation
     * @throws CalculatorException
     */
    private void processToken(String token, boolean isUndoOperator) throws CalculatorException {
        Double value = DoubleUtil.tryParseDouble(token);
        if (null == value) {
            processOperator(token, isUndoOperator);
        } else {
            //it's a digit
            valueStack.push(value);
            if (!isUndoOperator) {
                instructionStack.push(null);
            }
        }
    }

    /**
     * Executes an operation on the stack
     *
     * @param operatorString        RPN valid operator
     * @param isUndoOperator  indicates if the operation is an undo operation
     * @throws CalculatorException
     */
    private void processOperator(String operatorString, boolean isUndoOperator) throws CalculatorException {
        // check if there is an empty stack
        if (valueStack.isEmpty()) {
            throw new CalculatorException("empty stack");
        }

        // searching for the operator
        Operator operator = Operator.getEnum(operatorString);
        if (null == operator) {
            throw new CalculatorException("invalid operator");
        }

        // clear value stack and instructions stack
        if (Operator.CLEAR == operator) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (Operator.UNDO == operator) {
            undoLastInstruction();
            return;
        }

        // checking there are enough operand for the operation
        if (operator.getOperandsNumber() > valueStack.size()) {
            throw new CalculatorException(
                    String.format("operator %s (position: %d): insufficient parameters", operator, currentTokenIndex));
        }

        // getting operands
        Double firstOperand = valueStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? valueStack.pop() : null;

        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);
        if (null != result) {
            valueStack.push(result);
            if (!isUndoOperator) {
                instructionStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }

    }

    private void clearStacks() {
        valueStack.clear();
        instructionStack.clear();
    }

    /**
     * Evaluate a RPN expression and pushes the result into the valueStack
     *
     * @param input             valid RPN expression
     * @param isUndoOperation   indicates if the operation is an undo operation
     * @throws CalculatorException
     */
    public void evaluate(String input, boolean isUndoOperation) throws CalculatorException {
        if (StringUtils.isBlank(input)) {
            throw new CalculatorException("Input cannot be null.");
        }
        currentTokenIndex = 0;
        for (String s : input.split(input)) {
            currentTokenIndex++;
            processToken(s, isUndoOperation);
        }
    }

    private void undoLastInstruction() throws CalculatorException {
        if (instructionStack.isEmpty()) {
            throw new CalculatorException("no operations to undo");
        }
        Instruction lastInstruction = instructionStack.pop();
        if (null == lastInstruction) {
            valueStack.pop();
        } else {
            evaluate(lastInstruction.getReverseInstruction(), true);
        }
    }

    public String getValueStack() {
        DecimalFormat fmt = new DecimalFormat("0.#########");
        fmt.setRoundingMode(RoundingMode.FLOOR);
        StringBuilder sb = new StringBuilder("stack: ");
        for (Double value: valueStack) {
            sb.append(fmt.format(value)).append(" ");
        }
        return sb.toString().trim();
    }


}
