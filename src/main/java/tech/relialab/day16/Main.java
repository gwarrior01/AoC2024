package tech.relialab.day16;

import tech.relialab.Common;
import tech.relialab.day6.Main.Direction;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day16/input");
        var grid = Common.getGrid(input);
        System.out.println("[1] min score = " + part1(grid));
    }

    private static int part1(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'S') {
                    return findMinScore(grid, i, j);
                }
            }
        }
        return -1;
    }

    private static int findMinScore(char[][] grid, int x, int y) {
        int n = grid.length;
        int m = grid[0].length;
        var pq = new PriorityQueue<State>(Comparator.comparingInt(a -> a.score));
        boolean[][][] visited = new boolean[n][m][4];
        var directions = Direction.values();

        for (var direction : directions) {
            pq.add(new State(x, y, 1000, direction));
        }

        while (!pq.isEmpty()) {
            var curr = pq.poll();
            if (grid[curr.x][curr.y] == 'E') {
                return curr.score;
            }
            int directionId = curr.direction.ordinal();
            if (visited[curr.x][curr.y][directionId]) {
                continue;
            }
            visited[curr.x][curr.y][directionId] = true;

            for (var direction : directions) {
                int nx = curr.x + direction.x;
                int ny = curr.y + direction.y;

                if (grid[nx][ny] != '#') {
                    int turnCost = curr.direction == direction ? 1 : 1001;
                    int newScore = curr.score + turnCost;
                    pq.add(new State(nx, ny, newScore, direction));
                }
            }
        }
        return -1;
    }

    static class State {
        int x, y, score;
        Direction direction;

        State(int x, int y, int score, Direction direction) {
            this.x = x;
            this.y = y;
            this.score = score;
            this.direction = direction;
        }
    }
}