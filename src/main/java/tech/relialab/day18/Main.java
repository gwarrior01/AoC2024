package tech.relialab.day18;

import tech.relialab.Common;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

import static tech.relialab.Common.inBoundCharGrid;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day18/input");
        int size = 71;
        int bytes = 1024;
        System.out.println("[1] minimum number steps = " + part1(input, size, bytes));
        System.out.println("[2] bytes coordinates = " + part2(input, size));
    }

    private static long part1(List<String> input, int size, int bytes) {
        var grid = new char[size][size];
        initializeGrid(grid);
        input.stream()
                .limit(bytes)
                .forEach(byteCoordinate -> addCorruptedByte(byteCoordinate, grid));
        return bfs(grid);
    }

    private static String part2(List<String> input, int size) {
        var grid = new char[size][size];
        initializeGrid(grid);
        for (var byteCoordinate : input) {
            addCorruptedByte(byteCoordinate, grid);
            if (bfs(grid) == -1) {
                return byteCoordinate;
            }
        }
        return "";
    }

    private static void addCorruptedByte(String line, char[][] grid) {
        var arr = line.split(",");
        grid[Integer.parseInt(arr[1])][Integer.parseInt(arr[0])] = '#';
    }

    private static void initializeGrid(char[][] grid) {
        for (var line : grid) {
            Arrays.fill(line, '.');
        }
    }

    private static long bfs(char[][] grid) {
        var queue = new ArrayDeque<Pos>();
        int n = grid.length;
        queue.add(new Pos(0, 0));
        var visited = new boolean[n][n];
        var count = -1L;
        while (!queue.isEmpty()) {
            var size = queue.size();
            count++;
            for (int i = 0; i < size; i++) {
                var pos = queue.poll();
                int x = pos.x, y = pos.y;
                if (!inBoundCharGrid(grid, x, y) || grid[x][y] == '#' || visited[x][y]) {
                    continue;
                }
                if (x == grid.length - 1 && y == grid[0].length - 1) {
                    return count;
                }
                visited[x][y] = true;
                queue.add(new Pos(x + 1, y));
                queue.add(new Pos(x - 1, y));
                queue.add(new Pos(x, y + 1));
                queue.add(new Pos(x, y - 1));
            }
        }
        return -1;
    }

    record Pos(int x, int y) {
    }
}