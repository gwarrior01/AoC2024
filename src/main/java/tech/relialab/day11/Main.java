package tech.relialab.day11;

import tech.relialab.Common;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day11/input");
        var stones = Common.getIntegerArray(input.getFirst());
        System.out.println("[1] Stone count after 25 blinks = " + countStones(stones, 25));
        System.out.println("[2] Stone count after 75 blinks = " + countStones(stones, 75));
    }

    private static long countStones(int[] stones, int blinks) {
        var stoneToCount = new HashMap<String, Long>();
        for (int stone : stones) {
            stoneToCount.merge(String.valueOf(stone), 1L, Long::sum);
        }

        for (int i = 0; i < blinks; i++) {
            var currStoneToCount = new HashMap<String, Long>();
            for (var entry : stoneToCount.entrySet()) {
                var stone = entry.getKey();
                var count = entry.getValue();
                if (stone.equals("0")) {
                    currStoneToCount.merge("1", count, Long::sum);
                } else if (stone.length() % 2 == 0) {
                    int mid = stone.length() / 2;
                    var left = stone.substring(0, mid);
                    var right = stone.substring(mid).replaceFirst("^0+", "");
                    currStoneToCount.merge(left, count, Long::sum);
                    currStoneToCount.merge(right.isEmpty() ? "0" : right, count, Long::sum);
                } else {
                    currStoneToCount.merge(String.valueOf(Long.parseLong(stone) * 2024), count, Long::sum);
                }
            }
            stoneToCount = currStoneToCount;
        }

        return stoneToCount.values().stream().reduce(0L, Long::sum);
    }
}