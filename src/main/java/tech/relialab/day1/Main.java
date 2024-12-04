package tech.relialab.day1;

import tech.relialab.Common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day1/input");
        input.forEach(pair -> {
            var arr = pair.split("   ");
            left.add(Integer.parseInt(arr[0]));
            right.add(Integer.parseInt(arr[1]));
        });
        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());
        var rightLocationToCount = right.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long distance = 0;
        long similarity = 0;
        for (int i = 0; i < left.size(); i++) {
            distance += Math.abs(left.get(i) - right.get(i));
            similarity += (long) left.get(i) * rightLocationToCount.getOrDefault(left.get(i), 0L);
        }
        System.out.println("[1] total distance = " + distance);
        System.out.println("[2] similarity = " + similarity);
    }

}