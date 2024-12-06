package tech.relialab.day6;

import tech.relialab.Common;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day6/input");
        var n = input.size();
        var grid = new char[n][n];
        int startX = 0, startY = 0;
        for (int i = 0; i < n; i++) {
            var curr = input.get(i);
            for (int j = 0; j < n; j++) {
                if (curr.charAt(j) == '^') {
                    startX = i;
                    startY = j;
                }
                grid[i][j] = curr.charAt(j);
            }
        }
        var uniquePosCount = findUniquePosCount(grid, startX, startY);
        System.out.println("[1] unique pos count = " + uniquePosCount);
        var loopsCount = findLoopsCount(grid, startX, startY);
        System.out.println("[2] loops count = " + loopsCount);
    }

    private static int findUniquePosCount(char[][] grid, int startX, int startY) {
        var direction = Direction.UP;
        int uniquePos = 0;
        int x = startX, y = startY;
        while (inBound(grid, x, y)) {
            if (isObstacle(grid, x, y)) {
                // step back
                x = x + direction.stepBackX;
                y = y + direction.stepBackY;
                // change direction
                direction = direction.turnRight();
            }
            if (grid[x][y] != 'X') {
                uniquePos++;
            }
            grid[x][y] = 'X';
            x += direction.x;
            y += direction.y;
        }
        return uniquePos;
    }

    private static int findLoopsCount(char[][] grid, int startX, int startY) {
        var loopsCount = 0;
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'X') {
                    createObstacle(grid, i, j);
                    if (hasLoop(grid, startX, startY)) {
                        loopsCount++;
                    }
                    removeObstacle(grid, i, j);
                }
            }
        }
        return loopsCount;
    }

    private static void createObstacle(char[][] grid, int i, int j) {
        grid[i][j] = '#';
    }

    private static void removeObstacle(char[][] grid, int i, int j) {
        grid[i][j] = 'X';
    }

    private static boolean hasLoop(char[][] grid, int startX, int startY) {
        var direction = Direction.UP;
        int x = startX;
        int y = startY;
        var seen = new HashSet<String>();
        while (inBound(grid, x, y)) {
            var state = x + "," + y + direction;
            if (seen.contains(state)) {
                return true;
            }
            seen.add(state);
            if (isObstacle(grid, x, y)) {
                // step back
                x = x + direction.stepBackX;
                y = y + direction.stepBackY;
                // change direction
                direction = direction.turnRight();
            }
            x += direction.x;
            y += direction.y;
        }
        return false;
    }

    private static boolean inBound(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }

    private static boolean isObstacle(char[][] grid, int x, int y) {
        return grid[x][y] == '#';
    }

    enum Direction {
        UP(-1,0, 1, 0),
        RIGHT(0,1, 0, -1),
        DOWN(1,0, -1, 0),
        LEFT(0, -1, 0, 1);

        final int x, y, stepBackX, stepBackY;

        Direction(int x, int y, int stepBackX, int stepBackY) {
            this.x = x;
            this.y = y;
            this.stepBackX = stepBackX;
            this.stepBackY = stepBackY;
        }

        Direction turnRight() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }
}