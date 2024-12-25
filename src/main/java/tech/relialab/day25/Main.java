package tech.relialab.day25;

import tech.relialab.Common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day25/input");
        System.out.println("[1] pairs = " + part1(input));
    }

    private static long part1(List<String> input) {
        var keys = new HashSet<int[]>();
        var locks = new HashSet<int[]>();
        int left = 0;
        int right = 7;
        while (right <= input.size()) {
            var grid = Common.getGrid(input.subList(left, right));
            if (isKey(grid)) {
                keys.add(getPins(grid));
            } else {
                locks.add(getPins(grid));
            }
            left = right + 1;
            right += 8;
        }

        return countPairs(keys, locks);
    }

    private static boolean isKey(char[][] grid) {
        return IntStream.range(0, grid[0].length).allMatch(i -> grid[0][i] == '.');
    }

    private static int[] getPins(char[][] grid) {
        return IntStream.range(0, grid[0].length)
                .map(i -> (int) IntStream.range(0, grid.length)
                        .filter(j -> grid[j][i] == '#')
                        .count() - 1)
                .toArray();
    }

    private static long countPairs(Set<int[]> keys, Set<int[]> locks) {
        var count = 0L;
        for (var key : keys) {
            for (var lock : locks) {
                if (isFit(key, lock)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isFit(int[] key, int[] lock) {
        return IntStream.range(0, key.length).allMatch(i -> key[i] + lock[i] <= 5);
    }

}