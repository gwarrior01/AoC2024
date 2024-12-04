package tech.relialab.day2;

import tech.relialab.Common;

public class Main {

    public static void main(String[] args) {
        var reports = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day2/input");
        var safeReports = 0;
        for (var report : reports) {
            var levels = report.split(" ");
            if (isSafe(levels)) {
                safeReports++;
            }
        }
        System.out.println("[1] Safe reports = " + safeReports);

        var toleratedSafeReports = 0;
        for (var report : reports) {
            var levels = report.split(" ");
            if (isSafeWithOneRemoval(levels)) {
                toleratedSafeReports++;
            }
        }
        System.out.println("[2] Tolerated safe reports = " + toleratedSafeReports);
    }

    private static boolean isSafeWithOneRemoval(String[] levels) {
        if (isSafe(levels)) {
            return true;
        }
        for (int i = 0; i < levels.length; i++) {
            if (isSafe(removeLevel(levels, i))) {
                return true;
            }
        }
        return false;
    }

    private static String[] removeLevel(String[] levels, int index) {
        int n = levels.length;
        var attempt = new String[n - 1];
        for (int i = 0, j = 0; i < n; i++) {
            if (i != index) {
                attempt[j++] = levels[i];
            }
        }
        return attempt;
    }

    private static boolean isSafe(String[] levels) {
        var currAttempt = true;
        var inc = Integer.parseInt(levels[1]) > Integer.parseInt(levels[0]);
        for (int i = 1; i < levels.length && currAttempt; i++) {
            var curr = Integer.parseInt(levels[i]);
            var prev = Integer.parseInt(levels[i - 1]);
            int diff = curr - prev;
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                currAttempt = false;
            }
            if (inc && diff <= 0) {
                currAttempt = false;
            }
            if (!inc && diff >= 0) {
                currAttempt = false;
            }
        }
        return currAttempt;
    }
}