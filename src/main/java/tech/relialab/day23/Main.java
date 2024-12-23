package tech.relialab.day23;

import tech.relialab.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day23/input");
        System.out.println("[1] sets of three inter-connected computers = " + part1(input));
        System.out.println("[2] password = " + part2(input));
    }

    private static int part1(List<String> input) {
        var graph = computeGraph(input);
        var computers = new HashSet<Set<String>>();
        for (var node : graph.keySet()) {
            for (var a : graph.get(node)) {
                for (var b : graph.get(node)) {
                    if (!a.equals(b) && graph.get(a).contains(b) && startsWithT(a, b, node)) {
                        computers.add(new HashSet<>(Arrays.asList(node, a, b)));
                    }
                }
            }
        }
        return computers.size();
    }

    private static String part2(List<String> input) {
        var graph = computeGraph(input);
        return findLargestConnectedSet(graph).stream().sorted().collect(Collectors.joining(","));
    }

    private static HashMap<String, List<String>> computeGraph(List<String> input) {
        var graph = new HashMap<String, List<String>>();
        for (var line : input) {
            var arr = line.split("-");
            var from = arr[0];
            var to = arr[1];
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
        }
        return graph;
    }

    private static Set<String> findLargestConnectedSet(Map<String, List<String>> graph) {
        var largestClique = new HashSet<String>();
        bronKerbosch(new HashSet<>(), new HashSet<>(graph.keySet()), new HashSet<>(), graph, largestClique);
        return largestClique;
    }

    private static void bronKerbosch(Set<String> r, Set<String> p, Set<String> x, Map<String, List<String>> graph, Set<String> largestClique) {
        if (p.isEmpty() && x.isEmpty()) {
            if (r.size() > largestClique.size()) {
                largestClique.clear();
                largestClique.addAll(r);
            }
            return;
        }

        for (var v : new HashSet<>(p)) {
            var newR = new HashSet<>(r);
            newR.add(v);

            var newP = new HashSet<>(p);
            newP.retainAll(graph.get(v));

            var newX = new HashSet<>(x);
            newX.retainAll(graph.get(v));

            bronKerbosch(newR, newP, newX, graph, largestClique);

            p.remove(v);
            x.add(v);
        }
    }

    private static boolean startsWithT(String a, String b, String c) {
        return a.startsWith("t") || b.startsWith("t") || c.startsWith("t");
    }
}