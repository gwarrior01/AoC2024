package tech.relialab.day20;

import tech.relialab.Common;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day20/input");
        var grid = Common.getGrid(input);
        Common.printGrid(grid);
        System.out.println("[1] cheats count = " + part1(grid));
    }

    private static int part1(char[][] grid) {
        var cheats = new HashMap<Integer, Integer>();
        var start = findStartPosition(grid);
        var picoseconds = bfs(grid, start);
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (isWall(grid, i, j)) {
                    removeWall(grid, i, j);
                    var curr = bfs(grid, start);
                    cheats.merge(picoseconds - curr, 1, Integer::sum);
                    addWall(grid, i, j);
                }
            }
        }

        return cheats.entrySet().stream()
                .filter(e -> e.getKey() >= 100)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private static Pos findStartPosition(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'S') {
                    return new Pos(i, j);
                }
            }
        }
        return new Pos(-1, -1);
    }

    private static int bfs(char[][] grid, Pos pos) {
        int n = grid.length;
        int m = grid[0].length;
        var visited = new boolean[n][m];
        var queue = new ArrayDeque<Pos>();
        int picosecond = 0;
        queue.add(pos);
        while (!queue.isEmpty()) {
            var size = queue.size();
            for (int i = 0; i < size; i++) {
                var currPos = queue.poll();
                if (visited[currPos.x][currPos.y]) {
                    continue;
                }
                visited[currPos.x][currPos.y] = true;
                if (isEnd(grid, currPos.x, currPos.y)) {
                    return picosecond;
                }
                if (!isWall(grid, currPos.x, currPos.y)) {
                    queue.add(new Pos(currPos.x + 1, currPos.y));
                    queue.add(new Pos(currPos.x - 1, currPos.y));
                    queue.add(new Pos(currPos.x, currPos.y + 1));
                    queue.add(new Pos(currPos.x, currPos.y - 1));
                }
            }

            picosecond++;
        }
        return picosecond;
    }

    private static boolean isEnd(char[][] grid, int x, int y) {
        return grid[x][y] == 'E';
    }

    private static boolean isWall(char[][] grid, int x, int y) {
        return grid[x][y] == '#';
    }

    private static void removeWall(char[][] grid, int x, int y) {
        grid[x][y] = '.';
    }

    private static void addWall(char[][] grid, int x, int y) {
        grid[x][y] = '#';
    }

    record Pos(int x, int y) {
    }
}
