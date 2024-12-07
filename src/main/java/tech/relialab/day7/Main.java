package tech.relialab.day7;

import tech.relialab.Common;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class Main {
    static BinaryOperator<Long> add = (a, b) -> a + b;
    static BinaryOperator<Long> multiply = (a, b) -> a * b;
    static BinaryOperator<Long> concat = (a, b) -> Long.parseLong(a + "" + b);

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day7/input");
        System.out.println("[1] total calibration for 2 operators: " + part1(input));
        System.out.println("[2] total calibration for 3 operators: " + part2(input));
    }

    private static long part1(List<String> input) {
        var res = 0L;
        for (var equation : input) {
            var arr = equation.split(": ");
            var target = Long.parseLong(arr[0]);
            var numbers = Arrays.stream(arr[1].split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();
            res += isValidEquation(numbers, target, 0, numbers[0], List.of(add, multiply)) ? target : 0L;
        }
        return res;
    }

    private static long part2(List<String> input) {
        var res = 0L;
        for (var equation : input) {
            var arr = equation.split(": ");
            var target = Long.parseLong(arr[0]);
            var numbers = Arrays.stream(arr[1].split(" ")).mapToLong(Long::parseLong).toArray();
            res += isValidEquation(numbers, target, 0, numbers[0], List.of(add, multiply, concat)) ? target : 0L;
        }
        return res;
    }

    private static boolean isValidEquation(long[] numbers, long target, int i, long acc, List<BinaryOperator<Long>> operators) {
        if (acc > target) {
            return false;
        }
        if (i == numbers.length - 1) {
            return acc == target;
        }
        var current = numbers[i + 1];
        for (var operator : operators) {
            if (isValidEquation(numbers, target, i + 1, operator.apply(acc, current), operators)) {
                return true;
            }
        }
        return false;
    }
}