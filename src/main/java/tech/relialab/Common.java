package tech.relialab;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Common {

    public static List<String> getInput(String path) {
        try (var input = Files.lines(Paths.get(path))) {
            return input.toList();
        } catch (Exception ex) {
            return List.of();
        }
    }
}
