package org.gitqh.interview.model;

import lombok.AllArgsConstructor;
import org.gitqh.interview.exception.CalculatorException;
import org.gitqh.interview.operation.Operator;

/**
 * Created by gitqh on 05/08/2017.
 */
@AllArgsConstructor
public class Instruction {
    private Operator operator;
    private Double value;

    public String getReverseInstruction() throws CalculatorException {
        if (operator.getOperandsNumber() < 1) {
            throw new CalculatorException(String.format("invalid operation for operator %s", operator.getSymbol()));
        }

        return (operator.getOperandsNumber() < 2) ?
        String.format("%s", operator.getOpposite()) :
                String.format("%f %s %f", value, operator.getOpposite(), value);
    }
}
