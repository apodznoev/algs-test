package de.avpod;

import java.util.Arrays;

public class StackCalculator {

    public static void main(String[] args) {
        String argLine = String.join("", args);

        if (argLine.isEmpty() || Operation.valueOf(argLine.charAt(0)) != Operation.MINUS) {
            throw new RuntimeException("Wrong input, number expected first");
        }

        Stack<String> stack = new Stack<>();

        StringBuilder numberBuilder = new StringBuilder();
        for (char nextChar : argLine.toCharArray()) {
            Operation nextOperation = Operation.valueOf(nextChar);

            if (nextOperation == null) {
                if (notNumber(nextChar)) {
                    throw new RuntimeException("Only decimal number supported");
                }

                numberBuilder.append(nextChar);
                continue;
            }

            if (numberBuilder.length() == 0) {
                if (nextOperation == Operation.MINUS) {
                    numberBuilder.append(nextChar);
                    continue;
                } else {
                    throw new RuntimeException("Double operation not supported");
                }
            }

            popTopOperation(stack, numberBuilder, nextOperation);
            numberBuilder = new StringBuilder();
        }
        popTopOperation(stack, numberBuilder, null);

        System.out.println(sumStack(stack));
    }

    private static void popTopOperation(Stack<String> stack, StringBuilder numberBuilder, Operation nextOperation) {
        String number = numberBuilder.toString();

        if (stack.isEmpty()) {
            stack.push(number);
            if (nextOperation != null)
                stack.push(String.valueOf(nextOperation.ch));
            return;
        }

        Operation previousOperation = Operation.valueOf(stack.peek().charAt(0));
        if (previousOperation == Operation.PLUS) {
            stack.push(number);
            if (nextOperation != null)
                stack.push(String.valueOf(nextOperation.ch));
            return;
        }

        if (previousOperation == Operation.MINUS) {
            stack.pop();
            stack.push(String.valueOf(Operation.PLUS.ch));
            stack.push(String.valueOf(Integer.parseInt(number) * -1));
            if (nextOperation != null)
                stack.push(String.valueOf(nextOperation.ch));
            return;
        }

        previousOperation = Operation.valueOf(stack.pop().charAt(0));
        String previousNumber = stack.pop();
        stack.push(String.valueOf(
                previousOperation.perform(Integer.parseInt(previousNumber), Integer.parseInt(number))
        ));

        if (nextOperation != null)
            stack.push(String.valueOf(nextOperation.ch));
    }

    private static int sumStack(Stack<String> stack) {
        while (true) {
            int number2 = Integer.parseInt(stack.pop());

            if (stack.isEmpty()) {
                return number2;
            }

            Operation operation = Operation.valueOf(stack.pop().charAt(0));
            int number1 = Integer.parseInt(stack.pop());
            stack.push(String.valueOf(operation.perform(number1, number2)));
        }
    }

    private static boolean notNumber(char nextChar) {
        try {
            Integer.parseInt(String.valueOf(nextChar));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public enum Operation {
        PLUS('+') {
            @Override
            public int perform(int a, int b) {
                return a + b;
            }
        },
        MINUS('-') {
            @Override
            public int perform(int a, int b) {
                return a - b;
            }
        },
        MULTIPLY('*') {
            @Override
            public int perform(int a, int b) {
                return a * b;
            }
        },
        DIVIDE('/') {
            @Override
            public int perform(int a, int b) {
                return a / b;
            }
        };

        private final char ch;

        Operation(char s) {
            this.ch = s;
        }

        public static Operation valueOf(char c) {
            return Arrays.stream(Operation.values())
                    .filter((op) -> op.ch == c)
                    .findFirst()
                    .orElse(null);
        }

        public abstract int perform(int a, int b);
    }

}
