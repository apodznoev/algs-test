package de.avpod.problems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenerateParenthesis {
    private static final String parenthesis = "()";

//    TODO: unsolved
    public List<String> generateParenthesis(int n) {
        if (n == 1) {
            return List.of(parenthesis);
        }

        List<String> subProblem = generateParenthesis(n - 1);
        return inductionStep(subProblem);
    }

    private List<String> inductionStep(final List<String> subProblemSolution) {
        List<String> result = new ArrayList<>();

        for (String parenthesisCombination : subProblemSolution) {
            result.addAll(placeParenthesisInAllWays(parenthesisCombination));
        }

        return result;
    }

    private List<String> placeParenthesisInAllWays(final String parenthesisCombination) {
        List<String> result = new ArrayList<>();
        boolean simpleParenthesisAhead = false;
        for (int i = 0; i < parenthesisCombination.length(); i++) {
            if(simpleParenthesisAhead) {
                simpleParenthesisAhead = false;
                continue;
            }

            char currentChar = parenthesisCombination.charAt(i);
            Character nextChar = i == parenthesisCombination.length() - 1 ? null : parenthesisCombination.charAt(i + 1);
            simpleParenthesisAhead = parenthesisCombination.length() != 2
                    && nextChar != null
                    && (String.valueOf(new char[]{currentChar, nextChar}).equals(parenthesis));
            
            result.add(parenthesisCombination.substring(0, i) + parenthesis + parenthesisCombination.substring(i));
        }
        return result;
    }
}
