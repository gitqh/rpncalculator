package org.gitqh.interview;

import org.gitqh.interview.domain.Calculator;
import org.gitqh.interview.exception.CalculatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by gitqh on 05/08/2017.
 */
public class Application {
    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {
        while (!Thread.interrupted()) {
            Calculator calculator = new Calculator();
            InputStreamReader fileData;
            try {
                fileData = new InputStreamReader(System.in, "utf-8");
                BufferedReader br = new BufferedReader(fileData);
                System.out.println("please input: ");
                String read = null;
                String result = null;
                try {
                    read = br.readLine();
                    calculator.evaluate(read, false);
                    result = calculator.getValueStack();
                } catch (IOException e) {
                    System.out.println("input should not be null");
                } catch (CalculatorException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(result);
            } catch (UnsupportedEncodingException e) {
                System.out.println("input encoding should be utf-8");
            }
        }
    }
}
