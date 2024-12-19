package tech.relialab.day19;

import tech.relialab.Common;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day19/input");
        System.out.println("[1] possible designs = " + part1(input));
        System.out.println("[2] combinations count = " + part2(input));
    }

    private static int part1(List<String> input) {
        var towels = Arrays.stream(input.getFirst().split(",")).map(String::trim).collect(Collectors.toSet());
        var designs = input.subList(2, input.size());
        int count = 0;
        for (var design : designs) {
            if (isPossibleDesign(design, towels, 0, new StringBuilder())) {
                count++;
            }
        }
        return count;
    }

    private static boolean isPossibleDesign(String design, Set<String> towels, int start, StringBuilder current) {
        if (design.contentEquals(current)) {
            return true;
        }
        int right = start + 1;
        while (right <= design.length()) {
            var curr = design.substring(start, right);
            if (towels.contains(curr)) {
                current.append(curr);
                if (isPossibleDesign(design, towels, right, current)) {
                    return true;
                }
                current.delete(start, right);
            }
            right++;
        }
        return false;
    }

    public static long part2(List<String> input) {
        var towels = Arrays.stream(input.getFirst().split(",")).map(String::trim).collect(Collectors.toSet());
        var designs = input.subList(2, input.size());
        long totalCount = 0;

        for (var design : designs) {
            totalCount += countWays(design, towels);
        }

        return totalCount;
    }

    private static long countWays(String design, Set<String> towels) {
        int n = design.length();
        var dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                String substring = design.substring(j, i);
                if (towels.contains(substring)) {
                    dp[i] += dp[j];
                }
            }
        }
        return dp[n];
    }
}