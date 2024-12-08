package tech.relialab.day8;

import tech.relialab.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static tech.relialab.Common.inBound;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day8/input");
        var grid = Common.getGrid(input);
        System.out.println("[1] antinodes count = " + part1(grid));
        System.out.println("[2] antinodes count after update = " + part2(grid));
    }

    public static long part1(char[][] grid) {
        var antennaTypeToPos = new HashMap<Character, List<int[]>>();
        var seen = new HashSet<String>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                var currPos = grid[x][y];
                if (isAntenna(currPos)) {
                    antennaTypeToPos.computeIfAbsent(currPos, k -> new ArrayList<>());
                    var antennas = antennaTypeToPos.get(currPos);
                    for (int[] antenna : antennas) {
                        // check left side antinode
                        var leftDx = x - antenna[0];
                        var leftDy = y - antenna[1];
                        var leftX = x + leftDx;
                        var leftY = y + leftDy;
                        if (inBound(grid, leftX, leftY)) {
                            seen.add(leftX + "," + leftY);
                            if (grid[leftX][leftY] == '.') {
                                grid[leftX][leftY] = '#';
                            }
                        }
                        // check right side antinode
                        var rightDx = antenna[0] - x;
                        var rightDy = antenna[1] - y;
                        var rightX = antenna[0] + rightDx;
                        var rightY = antenna[1] + rightDy;
                        if (inBound(grid, rightX, rightY)) {
                            seen.add(rightX + "," + rightY);
                            if (grid[rightX][rightY] == '.') {
                                grid[rightX][rightY] = '#';
                            }
                        }
                    }
                    antennas.add(new int[]{x, y});
                }
            }
        }
        return seen.size();
    }

    public static long part2(char[][] grid) {
        var antennaTypeToPos = new HashMap<Character, List<int[]>>();
        var seen = new HashSet<String>();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                var currPos = grid[x][y];
                if (isAntenna(currPos)) {
                    antennaTypeToPos.computeIfAbsent(currPos, k -> new ArrayList<>());
                    var antennas = antennaTypeToPos.get(currPos);
                    seen.add(x + "," + y);
                    for (int[] antenna : antennas) {
                        // check left side antinode
                        var leftDx = x - antenna[0];
                        var leftDy = y - antenna[1];
                        var leftX = x + leftDx;
                        var leftY = y + leftDy;
                        while (inBound(grid, leftX, leftY)) {
                            seen.add(leftX + "," + leftY);
                            if (grid[leftX][leftY] == '.') {
                                grid[leftX][leftY] = '#';
                            }
                            leftX += leftDx;
                            leftY += leftDy;
                        }
                        // check right side antinode
                        var rightDx = antenna[0] - x;
                        var rightDy = antenna[1] - y;
                        var rightX = antenna[0] + rightDx;
                        var rightY = antenna[1] + rightDy;
                        while (inBound(grid, rightX, rightY)) {
                            seen.add(rightX + "," + rightY);
                            if (grid[rightX][rightY] == '.') {
                                grid[rightX][rightY] = '#';
                            }
                            rightX += rightDx;
                            rightY += rightDy;
                        }
                    }
                    antennas.add(new int[]{x, y});
                }
            }
        }
        return seen.size();
    }

    private static boolean isAntenna(char currPos) {
        return currPos != '.' && currPos != '#';
    }
}