package tech.relialab.day10;

import tech.relialab.Common;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day10/input");
        var grid = Common.getIntGrid(input);
        System.out.println("[1] trailheads score = " + part1(grid));
        System.out.println("[2] trailheads rating = " + part2(grid));
    }

    private static long part1(int[][] grid) {
        var trailheadsScore = 0L;
        int n = grid.length, m = grid[0].length;
        var visitedTops = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    trailheadsScore += countTrailheads(grid, i, j, visitedTops);
                }
            }
        }
        return trailheadsScore;
    }

    private static long part2(int[][] grid) {
        var trailheadsScore = 0L;
        int n = grid.length, m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    trailheadsScore += dfs2(grid, i, j, 0);
                }
            }
        }
        return trailheadsScore;
    }

    private static int countTrailheads(int[][] grid, int x, int y, boolean[][] visitedTops) {
        int n = grid.length, m = grid[0].length;
        dfs1(grid, x, y, 0, visitedTops);
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 9 && visitedTops[i][j]) {
                    visitedTops[i][j] = false;
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfs1(int[][] grid, int x, int y, int curr, boolean[][] visitedTops) {
        if (!Common.inBoundIntGrid(grid, x, y) || grid[x][y] != curr) {
            return;
        }
        if (grid[x][y] == 9) {
            visitedTops[x][y] = true;
            return;
        }
        dfs1(grid, x + 1, y, curr + 1, visitedTops);
        dfs1(grid, x - 1, y, curr + 1, visitedTops);
        dfs1(grid, x, y + 1, curr + 1, visitedTops);
        dfs1(grid, x, y - 1, curr + 1, visitedTops);
    }

    private static int dfs2(int[][] grid, int x, int y, int curr) {
        if (!Common.inBoundIntGrid(grid, x, y) || grid[x][y] != curr) {
            return 0;
        }
        if (grid[x][y] == 9) {
            return 1;
        }
        return dfs2(grid, x + 1, y, curr + 1) +
                dfs2(grid, x - 1, y, curr + 1) +
                dfs2(grid, x, y + 1, curr + 1) +
                dfs2(grid, x, y - 1, curr + 1);
    }
}
