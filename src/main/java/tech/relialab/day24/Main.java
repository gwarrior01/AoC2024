package tech.relialab.day24;

import tech.relialab.Common;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day24/input");
        System.out.println("[1] = " + part1(input));
    }

    private static long part1(List<String> input) {
        var wires = input.stream()
                .takeWhile(line -> !line.isEmpty())
                .map(line -> line.split(": "))
                .collect(Collectors.toMap( arr -> arr[0], arr -> arr[1].equals("1")));

        var gates = input.stream()
                .dropWhile(line -> !line.contains("->"))
                .map(Main::createGate)
                .collect(Collectors.toMap(Gate::outWire, Function.identity()));

        gates.values()
                .forEach(gate -> compute(wires, gates, gate));
        var binary = wires.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .filter(it -> it.getKey().startsWith("z"))
                .map(it -> it.getValue() ? "1" : "0")
                .collect(Collectors.joining());
        return Long.parseLong(binary, 2);
    }

    private static void compute(Map<String, Boolean> wires, Map<String, Gate> outWireToGates, Gate gate) {
        var left = gate.leftWire;
        var right = gate.rightWire;
        if (wires.containsKey(left) && wires.containsKey(right)) {
            addOutWireValue(wires, gate);
            return;
        }
        if (!wires.containsKey(left)) {
            compute(wires, outWireToGates, outWireToGates.get(left));
        }
        if (!wires.containsKey(right)) {
            compute(wires, outWireToGates, outWireToGates.get(right));
        }
        addOutWireValue(wires, gate);
    }

    private static void addOutWireValue(Map<String, Boolean> wires, Gate gate) {
        var left = gate.leftWire;
        var right = gate.rightWire;
        var value = switch (gate.operand) {
            case OR -> wires.get(left) | wires.get(right);
            case AND -> wires.get(left) & wires.get(right);
            case XOR -> wires.get(left) ^ wires.get(right);
        };
        wires.put(gate.outWire, value);
    }

    private static Gate createGate(String line) {
        var arr =  line.split(" -> ");
        var out = arr[1];
        var command = arr[0].split(" ");
        var leftInWire = command[0];
        var rightInWire = command[2];
        var operand = Operand.valueOf(command[1]);
        return new Gate(out, leftInWire, rightInWire, operand);
    }

    enum Operand {
        AND,
        OR,
        XOR
    }

    record Gate(String outWire, String leftWire, String rightWire, Operand operand) {}
}