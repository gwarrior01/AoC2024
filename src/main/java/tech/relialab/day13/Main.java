package tech.relialab.day13;

import tech.relialab.Common;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day13/input");
        System.out.println("[1] token count = " + findMinimumTokens(input, 0));
        System.out.println("[2] token count = " + findMinimumTokens(input, 10000000000000L));
    }

    private static long findMinimumTokens(List<String> input, long offset) {
        int n = 0;
        long total = 0L;
        while (n < input.size()) {
            var buttonA = getButton(input.get(n));
            var buttonB = getButton(input.get(n + 1));
            var tmpPrize = getPrize(input.get(n + 2));
            var prize = new Prize(tmpPrize.x + offset, tmpPrize.y + offset);
            var tokens = getTokens(buttonA, buttonB, prize);
            if (tokens.success) {
                total += tokens.count;
            }
            n += 4;
        }
        return total;
    }

    private static Token getTokens(Button a, Button b, Prize prize) {
        long numerator = prize.x * a.y - prize.y * a.x;
        long bPress = numerator / (b.x * a.y - b.y * a.x);
        long remX = prize.x - bPress * b.x;
        long l = a.x == 0 ? prize.y : remX;
        long r = a.x == 0 ? a.y : a.x;
        long aPress = l / r;
        return (aPress * a.y + bPress * b.y == prize.y && l % r == 0)
                ? new Token(3 * aPress + bPress, true)
                : new Token(0, false);
    }

    private static Token getTokensBruteforce(Button a, Button b, Prize prize) {
        var count = Long.MAX_VALUE;
        var success = false;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (prize.x == a.x * i + b.x * j && prize.y == a.y * i + b.y * j) {
                    count = Math.min(count, i * 3 + j);
                    success = true;
                }
            }
        }
        return new Token(count, success);
    }

    private static Button getButton(String button) {
        var x = Long.parseLong(button.substring(button.indexOf("+") + 1, button.indexOf(",")));
        var y = Long.parseLong(button.substring(button.lastIndexOf("+") + 1));
        return new Button(x, y);
    }

    private static Prize getPrize(String prize) {
        var x = Long.parseLong(prize.substring(prize.indexOf("=") + 1, prize.indexOf(",")));
        var y = Long.parseLong(prize.substring(prize.lastIndexOf("=") + 1));
        return new Prize(x, y);
    }

    record Button(long x, long y) {
    }

    record Prize(long x, long y) {
    }

    record Token(long count, boolean success) {
    }
}