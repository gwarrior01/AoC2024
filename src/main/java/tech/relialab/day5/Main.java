package tech.relialab.day5;

import tech.relialab.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day5/input");
        var rules = new HashMap<String, List<String>>();
        int index = 0;
        while (!input.get(index).isBlank()) {
            var curr = input.get(index++);
            var arr = curr.split("\\|");
            rules.computeIfAbsent(arr[0], k -> new ArrayList<>()).add(arr[1]);
        }
        var updates = input.subList(index + 1, input.size());
        var res1 = 0L;
        var res2 = 0L;
        for (var update : updates) {
            var pages = update.split(",");
            var isWright = true;
            for (int i = 0; i < pages.length && isWright; i++) {
                var currPage = pages[i];
                for (int j = i + 1; j < pages.length && isWright; j++) {
                    var nextPage = pages[j];
                    if (!rules.containsKey(currPage) || !rules.get(currPage).contains(nextPage)) {
                        isWright = false;
                    }
                }
            }
            if (isWright) {
                res1 += Long.parseLong(pages[pages.length / 2]);
            } else {
                var correctUpdate = rearrange(update, rules);
                res2 += Long.parseLong(correctUpdate[correctUpdate.length / 2]);
            }
        }
        System.out.println("[1] updates = " + res1);
        System.out.println("[2] updates after rearrange = " + res2);
    }

    private static String[] rearrange(String update, HashMap<String, List<String>> rules) {
        var pages = update.split(",");
        int n = pages.length;
        var correctUpdate = new String[n];
        for (int i = 0; i < n; i++) {
            var isWright = false;
            for (int j = i; j < n && !isWright; j++) {
                var page = pages[j];
                var restPages = new ArrayList<String>();
                restPages.addAll(Arrays.asList(pages).subList(i, j));
                restPages.addAll(Arrays.asList(pages).subList(j + 1, n));
                if (rules.containsKey(page) && new HashSet<>(rules.get(page)).containsAll(restPages)) {
                    correctUpdate[i] = pages[j];
                    swapPages(pages, j, i);
                    isWright = true;
                }
            }
        }
        return correctUpdate;
    }

    private static void swapPages(String[] pages, int j, int i) {
        var tmp = pages[j];
        pages[j] = pages[i];
        pages[i] = tmp;
    }
}
