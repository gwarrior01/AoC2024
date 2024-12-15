package tech.relialab.day15;

import tech.relialab.Common;
import tech.relialab.day6.Main.Direction;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day15/input");
        System.out.println("[1] GPS coordinates = " + part1(input));
    }

    private static long part1(List<String> input) {
        int m = input.getFirst().length();
        var grid = Common.getGrid(input.subList(0, m));
        var movements = input.subList(m + 1, input.size());
        simulateRobotMoves(grid, movements);
        return calculateCoordinates(grid);
    }

    private static void simulateRobotMoves(char[][] grid, List<String> movements) {
        var roborPos = findRobotPosition(grid);
        for (var line : movements) {
            for (int i = 0; i < line.length(); i++) {
                var direction = Direction.getDirection(line.charAt(i));
                moveBoxes(grid, roborPos, direction);
            }
        }
    }

    private static Pos findRobotPosition(char[][] grid) {
        var roborPos = new Pos(0, 0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '@') {
                    roborPos.x = i;
                    roborPos.y = j;
                }
            }
        }
        return roborPos;
    }

    private static void moveBoxes(char[][] grid, Pos pos, Direction direction) {
        int lx = pos.x;
        int ly = pos.y;
        while (!isWall(grid, lx, ly) && !isFreeSpace(grid, lx, ly)) {
            lx += direction.x;
            ly += direction.y;
        }
        if (isFreeSpace(grid, lx, ly)) {
            var opDir = direction.getOppositeDirection();
            var rx = lx;
            var ry = ly;
            while (rx != pos.x || ry != pos.y) {
                if (isBox(grid, rx, ry)) {
                    moveBox(grid, rx, ry, lx, ly);
                    lx += opDir.x;
                    ly += opDir.y;
                }
                rx += opDir.x;
                ry += opDir.y;
            }
            moveRobot(grid, pos, lx, ly);
        }
    }

    private static long calculateCoordinates(char[][] grid) {
        var coordinates = 0L;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (isBox(grid, i, j)) {
                    coordinates += 100L * i + j;
                }
            }
        }
        return coordinates;
    }

    private static void moveRobot(char[][] grid, Pos pos, int lx, int ly) {
        grid[lx][ly] = '@';
        grid[pos.x][pos.y] = '.';
        pos.x = lx;
        pos.y = ly;
    }

    private static void moveBox(char[][] grid, int rx, int ry, int lx, int ly) {
        var tmp = grid[rx][ry];
        grid[rx][ry] = grid[lx][ly];
        grid[lx][ly] = tmp;
    }

    private static boolean isWall(char[][] grid, int x, int y) {
        return grid[x][y] == '#';
    }

    private static boolean isFreeSpace(char[][] grid, int x, int y) {
        return grid[x][y] == '.';
    }

    private static boolean isBox(char[][] grid, int x, int y) {
        return grid[x][y] == 'O';
    }

    static class Pos {
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}