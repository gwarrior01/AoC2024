package tech.relialab.day3;

import tech.relialab.Common;

import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        var instructions = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day3/input");

        var multiPattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        var pattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
        long res1 = instructions.stream()
                .mapToLong(it -> processLine(it, multiPattern))
                .sum();

        var enabled = true;
        long res2 = 0;
        for (var line : instructions) {
            var matcher = pattern.matcher(line);
            while (matcher.find()) {
                var candidate = matcher.group();
                if (candidate.equals("do()")) {
                    enabled = true;
                } else if (candidate.equals("don't()")) {
                    enabled = false;
                } else if (candidate.startsWith("mul")) {
                    if (enabled) {
                        res2 += getMultiplication(candidate);
                    }
                }
            }
        }

        System.out.println("[1] sum of multiplication = " + res1);
        System.out.println("[2] sum of multiplication = " + res2);
    }

    private static long processLine(String line, Pattern pattern) {
        var matcher = pattern.matcher(line);
        long acc = 0L;
        while (matcher.find()) {
            acc += getMultiplication(line.substring(matcher.start(), matcher.end()));
        }
        return acc;
    }

    private static long getMultiplication(String instr) {
        var arr = instr.split(",");
        return Long.parseLong(arr[0].substring(4)) * Long.parseLong(arr[1].substring(0, arr[1].length() - 1));
    }
}