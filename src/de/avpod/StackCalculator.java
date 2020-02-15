package de.avpod;

import java.util.Arrays;

public class StackCalculator {

    public static void main(String[] args) {
        String argLine = String.join("", args);

        if (argLine.isEmpty() || Operation.valueOf(argLine.charAt(0)) != null) {
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
                throw new RuntimeException("Double operations");
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
            if(nextOperation != null)
                stack.push(String.valueOf(nextOperation.ch));
            return;
        }

        Operation previousOperation = Operation.valueOf(stack.peek().charAt(0));
        if (previousOperation == Operation.PLUS || previousOperation == Operation.MINUS) {
            stack.push(number);
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

    public static class Stack<T> {

        private StackNode<T> top;

        public void push(T value) {
            this.top = new StackNode<T>(value, top);
        }

        public T peek() {
            return this.top.value;
        }

        public T pop() {
            if (top == null) {
                throw new RuntimeException("Stack is empty");
            }

            T value = top.value;
            this.top = top.previous;
            return value;
        }

        public boolean isEmpty() {
            return this.top == null;
        }


        private class StackNode<T> {
            private final T value;
            private final StackNode<T> previous;

            private StackNode(T value, StackNode<T> previous) {
                this.value = value;
                this.previous = previous;
            }
        }
    }
}
