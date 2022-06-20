package de.avpod.problems;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class EvaluationOfReversePolishNotation {

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (!isOperation(token)) {
                stack.push(Integer.valueOf(token));
            } else {
                int secondValue = stack.pop();
                int firstValue = stack.pop();
                Operation operation = Operation.parse(token);
                int result = switch (operation) {
                    case ADD -> firstValue + secondValue;
                    case DISTRACT -> firstValue - secondValue;
                    case DIVIDE -> firstValue / secondValue;
                    case MULTIPLY -> firstValue * secondValue;
                };
                stack.push(result);
            }
        }
        if (stack.size() != 1) {
            throw new IllegalStateException("Wrong stack size:" + stack);
        }

        return stack.pop();
    }

    private boolean isOperation(final String token) {
        return Operation.tokens.contains(token);
    }

    private enum Operation {
        ADD("+"),
        DISTRACT("-"),
        DIVIDE("/"),
        MULTIPLY("*");

        private static Set<String> tokens = Arrays.stream(Operation.values()).map(o -> o.op).collect(Collectors.toSet());
        private final String op;

        Operation(final String op) {
            this.op = op;
        }

        public static Operation parse(final String token) {
            return Arrays.stream(Operation.values()).filter(o -> o.op.equals(token)).findFirst().orElseThrow();
        }
    }
}
