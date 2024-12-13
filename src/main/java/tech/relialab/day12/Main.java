package tech.relialab.day12;

import tech.relialab.Common;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day12/inputTest5");
        var grid = Common.getGrid(input);
//        System.out.println("[1] total price = " + part1(grid));
        System.out.println("[2] total price = " + part2(grid));
    }

    private static long part1(char[][] grid) {
        long totalPrice = 0L;
        int n = grid.length;
        int m = grid[0].length;
        var visited = new boolean[n][m];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!visited[i][j]) {
                    var res = dfs(grid, i, j, visited, grid[i][j]);
                    totalPrice += res[0] * res[1];
                }
            }
        }
        return totalPrice;
    }

    // new int    []       []
    //         perimeter  area
    private static long[] dfs(char[][] grid, int x, int y, boolean[][] visited, char plant) {
        if (!Common.inBoundCharGrid(grid, x, y) || grid[x][y] != plant) {
            return new long[] {1,0};
        }
        if (visited[x][y]) {
            return new long[] {0,0};
        }
        visited[x][y] = true;
        var res = new long[] {0, 1};
        var up = dfs(grid, x + 1, y, visited, plant);
        var down = dfs(grid, x - 1, y, visited, plant);
        var left = dfs(grid, x, y - 1, visited, plant);
        var right = dfs(grid, x, y + 1, visited, plant);
        res[0] += up[0] + down[0] + left[0] + right[0];
        res[1] += up[1] + down[1] + left[1] + right[1];
        return res;
    }

    //RRRRIICCFF
    //RRRRIICCCF
    //VVRRRCCFFF
    //VVRCCCJFFF
    //VVVVCJJCFE
    //VVIVCCJJEE
    //VVIIICJJEE
    //MIIIIIJJEE
    //MIIISIJEEE
    //MMMISSJEEE

    //A region of R plants with price 12 * 10 = 120.
    //A region of I plants with price 4 * 4 = 16.
    //A region of C plants with price 14 * 22 = 308.
    //A region of F plants with price 10 * 12 = 120.
    //A region of V plants with price 13 * 10 = 130.
    //A region of J plants with price 11 * 12 = 132.
    //A region of C plants with price 1 * 4 = 4.
    //A region of E plants with price 13 * 8 = 104.
    //A region of I plants with price 14 * 16 = 224.
    //A region of M plants with price 5 * 6 = 30.
    //A region of S plants with price 3 * 6 = 18.

    private static long part2(char[][] grid) {
        long totalPrice = 0L;
        int n = grid.length;
        int m = grid[0].length;
        var visited = new boolean[n][m];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!visited[i][j]) {
                    var area = dfs2(grid, i, j, visited, grid[i][j]);
                    var per = countSides(grid, i, j);
                    totalPrice += area * per;
                }
            }
        }
        return totalPrice;
    }


    private static long countSides(char[][] grid, int x, int y) {
        var sides = 0L;
        var current = grid[x][y];
        var direction = Direction.RIGHT;
        int currX = x;
        int currY = y;
        var copy = new char[grid.length][grid[0].length];
        for (char[] chars : copy) {
            Arrays.fill(chars, '.');
        }
        while (direction != Direction.UP || currX != x || currY != y) {

            // check left side
            var prevDirection = direction;
            direction = direction.turnLeft();
            currX += direction.x;
            currY += direction.y;
            if (Common.inBoundCharGrid(grid, currX, currY) && grid[currX][currY] == current) {
                sides++;
                copy[currX][currY] = 'X';
                if (direction == Direction.UP && currX == x && currY == y) {
                    break;
                }
            } else {
                currX += direction.stepBackX;
                currY += direction.stepBackY;
                direction = prevDirection;
            }

            // go ahead in current direction
            currX += direction.x;
            currY += direction.y;

            if (!Common.inBoundCharGrid(grid, currX, currY) || grid[currX][currY] != current) {
                currX += direction.stepBackX;
                currY += direction.stepBackY;
                sides++;
                direction = direction.turnRight();
            }
            copy[currX][currY] = 'X';
        }

        sides += 1;
        var visited2 = new boolean[grid.length][grid[0].length];
        dfs(grid, currX, currY, visited2, grid[currX][currY]);
        var visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                copy[i][j] = visited2[i][j] ? '5' : '.';
            }
        }
        for (int i = 0; i < copy.length; i++) {
            var chars = copy[i];
            int left = 0;
            int right = chars.length - 1;
            while (left < right) {
                while (left < right && chars[left] != '5') {
                    left++;
                }
                while (left < right && chars[right] != '5') {
                    right--;
                }
//                while (left < right && chars[left] == '5') {
//                    left++;
//                }
//                int start = left;
//                while (left < right && chars[left] != '5') {
//                    left++;
//                }
//                int end = left;

                while (left < right && !visited[i][left] && (chars[left] == '5' || chars[right] == current)) {
                    left++;
                }
                if (left < right && chars[left] != current && !visited[i][left]) {
//                    var up = i - 1;
//                    var bottom = i + 1;
//                    while (up >= 0) {
//                        if (grid[up][left] == current || copy[up][left] == '5') {
//                            break;
//                        }
//                        up--;
//                    }
//                    while (bottom < grid.length) {
//                        if (grid[bottom][left] == current || copy[bottom][left] == '5') {
//                            break;
//                        }
//                        bottom++;
//                    }

//                    if (bottom < grid.length && grid[bottom][left] == current && up >= 0 && grid[up][left] == current && !visited[i][left]) {
////                        Common.printGrid(copy);
//                        dfs(grid, i, left, visited, grid[i][left]);
//                        sides += countSides(grid, i, left);
//                    }

                    if (!dfs3(grid, i, left, visited, current, grid[i][left])) {
                        sides += countSides(grid, i, left);
                        while (left < right && chars[left] != '5') {
                            left++;
                        }
                    }
                }
            }
        }

        return sides;

    }



    private static boolean dfs3(char[][] grid, int x, int y, boolean[][] visited, char border, char current) {
        if (!Common.inBoundCharGrid(grid, x, y) || visited[x][y] || grid[x][y] == border) {
            return false;
        }
        if (grid[x][y] == current) {
            visited[x][y] = true;
        }
        if (grid[x][y] != current && grid[x][y] != border) {
            return true;
        }

        var res = false;

        return dfs3(grid, x + 1, y, visited, border, current) || dfs3(grid, x - 1, y, visited, border, current) ||
        dfs3(grid, x, y - 1, visited, border, current) || dfs3(grid, x, y + 1, visited, border, current);
    }

    private static int dfs2(char[][] grid, int x, int y, boolean[][] visited, char plant) {
        if (!Common.inBoundCharGrid(grid, x, y) || grid[x][y] != plant || visited[x][y]) {
            return 0;
        }
        visited[x][y] = true;

        var area = 1;

        area +=  dfs2(grid, x + 1, y, visited, plant);
        area +=  dfs2(grid, x - 1, y, visited, plant);
        area +=  dfs2(grid, x, y - 1, visited, plant);
        area +=  dfs2(grid, x, y + 1, visited, plant);
        return area;
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

        Direction turnLeft() {
            return this.turnRight().turnRight().turnRight();
        }
    }
}