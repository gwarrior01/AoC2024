package tech.relialab.day4;

import tech.relialab.Common;

public class Main {

    public static void main(String[] args) {
        var letters = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day4/input");
        var grid = new char[140][140];
        for (int i = 0; i < letters.size(); i++) {
            var curr = letters.get(i);
            for (int j = 0; j < curr.length(); j++) {
                grid[i][j] = curr.charAt(j);
            }
        }
        var res1 = 0;
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] == 'X') {
                    for (Direction dir : Direction.values()) {
                        res1 += bfs(grid, i, j, dir);
                    }
                }
            }
        }
        System.out.println("[1] countXMAS = " + res1);

        var res2 = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'A') {
                    res2 += isX_MAS(grid, i, j);
                }
            }
        }
        System.out.println("[2] countX_MAS = " + res2);
    }

    private static int bfs(char[][] grid, int i, int j, Direction direction) {
        char[] target = {'X', 'M', 'A', 'S'};
        int x = i, y = j;

        for (char letter : target) {
            if (!inBound(grid, x, y) || grid[x][y] != letter) {
                return 0;
            }
            x += direction.dx;
            y += direction.dy;
        }
        return 1;
    }

    private static int isX_MAS(char[][] grid, int i, int j) {
        var direct = new Direction[][]{
                {Direction.LEFT_UP, Direction.RIGHT_DOWN},
                {Direction.RIGHT_DOWN, Direction.LEFT_UP}
        };
        var reverse = new Direction[][]{
                {Direction.RIGHT_UP, Direction.LEFT_DOWN},
                {Direction.LEFT_DOWN, Direction.RIGHT_UP}
        };
        var directMatch = isMatch(grid, i, j, direct);
        var reverseMatch = isMatch(grid, i, j, reverse);
        return directMatch && reverseMatch ? 1 : 0;
    }

    private static boolean isMatch(char[][] grid, int i, int j, Direction[][] directions) {
        var res = false;
        char[] target = {'M', 'S'};
        for (int l = 0; l < directions.length && !res; l++) {
            var direction = directions[l];
            var currRes = 0;
            for (int k = 0; k < direction.length; k++) {
                int x = i + direction[k].dx;
                int y = j + direction[k].dy;
                if (inBound(grid, x, y) && grid[x][y] == target[k]) {
                    currRes += 1;
                }
            }
            if (currRes == 2) {
                res = true;
            }
        }
        return res;
    }

    private static boolean inBound(char[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length;
    }

    enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1),
        LEFT_UP(-1, -1),
        LEFT_DOWN(1, -1),
        RIGHT_UP(-1, 1),
        RIGHT_DOWN(1, 1);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
