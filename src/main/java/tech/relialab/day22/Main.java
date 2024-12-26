package tech.relialab.day22;

import tech.relialab.Common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day22/input");
        int steps = 2000;
        System.out.println("[1] sum of secret numbers = " + part1(input, steps));
        System.out.println("[2] most bananas count = " + part2(input, steps));
    }

    private static long part1(List<String> input, int steps) {
        return input.stream()
                .mapToLong(Long::parseLong)
                .map(initial -> generateSecret(initial, steps))
                .sum();
    }

    private static long part2(List<String> input, int steps) {
        var secrets = input.stream()
                .mapToLong(Long::parseLong)
                .toArray();
        var cache = new HashMap<Sequence, Long>();
        for (var currSecret : secrets) {
            var changes = new int[steps];
            var processed = new HashSet<Sequence>();
            for (int i = 0; i < steps; i++) {
                var nextSecret = generateIteration(currSecret);
                long nextPrice = nextSecret % 10;
                long currPrice = currSecret % 10;
                changes[i] = (int) (nextPrice - currPrice);
                if (i >= 3) {
                    var sequence = new Sequence(changes[i - 3], changes[i - 2], changes[i - 1], changes[i]);
                    if (!processed.contains(sequence)) {
                        processed.add(sequence);
                        cache.merge(sequence, nextPrice, Long::sum);
                    }
                }
                currSecret = nextSecret;
            }
        }
        return cache.values().stream().mapToLong(k -> k).reduce(0L, Long::max);
    }

    private static long generateSecret(long initial, int steps) {
        var secretNum = initial;
        for (int i = 0; i < steps; i++) {
            secretNum = generateIteration(secretNum);
        }
        return secretNum;
    }

    private static long generateIteration(long secretNum) {
        long res = secretNum;
        res = ((res << 6) ^ res) % 16777216;
        res = ((res >> 5) ^ res) % 16777216;
        res = ((res << 11) ^ res) % 16777216;
        return res;
    }

    record Sequence(int a, int b, int c, int d) {
    }
}