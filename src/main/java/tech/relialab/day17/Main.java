package tech.relialab.day17;

import tech.relialab.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day17/input");
        System.out.println("[1] output = " + part1(input));
    }

    private static String part1(List<String> input) {
        var aRegister = Long.parseLong(getRegister(input.getFirst()));
        var bRegister = Long.parseLong(getRegister(input.get(1)));
        var cRegister = Long.parseLong(getRegister(input.get(2)));
        var program = getProgram(input.get(4));
        var out = getOutput(program, aRegister, bRegister, cRegister);
        return out.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private static ArrayList<Integer> getOutput(int[] program, long aRegister, long bRegister, long cRegister) {
        var out = new ArrayList<Integer>();
        long A = aRegister;
        long B = bRegister;
        long C = cRegister;
        int n = program.length;
        int left = 0;
        while (left < n - 1) {
            var opcode = Opcode.valueOf(program[left]);
            int operand = program[left + 1];
            switch (opcode) {
                case adv -> A = performAdv(A, B, C, operand);
                case bxl -> B ^= operand;
                case bst -> B = performBst(A, B, C, operand);
                case jnz -> {
                    if (A != 0) {
                        left = operand;
                        continue;
                    }
                }
                case bxc -> B = B ^ C;
                case out -> out.add(performOut(A, B, C, operand));
                case bdv -> B = performBdv(A, B, C, operand);
                case cdv -> C = performCdv(A, B, C, operand);
            }
            left += 2;
        }
        return out;
    }

    private static String getRegister(String input) {
        return input.substring(input.lastIndexOf(':') + 2);
    }

    private static int[] getProgram(String input) {
        return Arrays.stream(input.substring(input.lastIndexOf(':') + 2).split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int performAdv(long a, long b, long c, int operand) {
        var combo = getCombo(a, b, c, operand);
        return (int) (a / Math.pow(2.0, combo));
    }

    private static long performBst(long a, long b, long c, int operand) {
        var combo = getCombo(a, b, c, operand);
        return combo % 8;
    }

    private static int performOut(long a, long b, long c, int operand) {
        var combo = getCombo(a, b, c, operand);
        return (int) combo % 8;
    }

    private static int performBdv(long a, long b, long c, int operand) {
        return performAdv(a, b, c, operand);
    }

    private static int performCdv(long a, long b, long c, int operand) {
        return performAdv(a, b, c, operand);
    }

    private static long getCombo(long a, long b, long c, int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalStateException("Unexpected value: " + operand);
        };
    }

    enum Opcode {
        adv(0),
        bxl(1),
        bst(2),
        jnz(3),
        bxc(4),
        out(5),
        bdv(6),
        cdv(7);

        private final int value;
        private static final Map<Integer, Opcode> map = Arrays.stream(values())
                .collect(Collectors.toMap(opcode -> opcode.value, Function.identity()));

        Opcode(int value) {
            this.value = value;
        }

        public static Opcode valueOf(int value) {
            return map.get(value);
        }
    }
}