package tech.relialab;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Common {

    public static List<String> getInput(String path) {
        try (var input = Files.lines(Paths.get(path))) {
            return input.toList();
        } catch (Exception ex) {
            return List.of();
        }
    }

    public static char[][] getGrid(List<String> input) {
        var grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            var curr = input.get(i);
            for (int j = 0; j < curr.length(); j++) {
                grid[i][j] = curr.charAt(j);
            }
        }
        return grid;
    }

    public static boolean inBound(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[x].length;
    }

    public static void printGrid(char[][] grid) {
        for (var curr: grid) {
            System.out.println(Arrays.toString(curr));
        }
    }
}
