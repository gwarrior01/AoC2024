package tech.relialab.day14;

import tech.relialab.Common;

import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day14/input");
        System.out.println("[1] safety factor: " + part1(input));
        System.out.println("[2] christmas tree after: " + part2(input));
    }

    private static long part1(List<String> input) {
        var robots = input.stream()
                .map(Main::parseRobot)
                .toList();
        int seconds = 100;
        int wide = 101;
        int tall = 103;
        var space = new int[tall][wide];
        for (var robot : robots) {
            moveRobot(robot, space, wide, tall, seconds);
        }
        return calculateSafetyFactor(space);
    }

    private static long part2(List<String> input) {
        var robots = input.stream()
                .map(Main::parseRobot)
                .toList();
        int wide = 101;
        int tall = 103;
        int step = 0;
        var isChristmasTree = false;

        while (!isChristmasTree) {
            step++;
            isChristmasTree = true;
            var points = new HashSet<Pos>();
            for (var robot : robots) {
                int x = (robot.initialX + step * (robot.velocityX + wide)) % wide;
                int y = (robot.initialY + step * (robot.velocityY + tall)) % tall;
                var pos = new Pos(x, y);
                if (points.contains(pos)) {
                    isChristmasTree = false;
                    break;
                }
                points.add(pos);
            }
        }
        return step;
    }

    private static void moveRobot(Robot robot, int[][] space, int wide, int tall, int seconds) {
        var newX = (robot.initialX + seconds * (robot.velocityX + wide)) % wide;
        var newY = (robot.initialY + seconds * (robot.velocityY + tall)) % tall;
        space[newY][newX]++;
    }

    private static long calculateSafetyFactor(int[][] space) {
        int midX = space[0].length / 2;
        int midY = space.length / 2;
        var leftTopQuadrant = countQuadrant(space, 0, midX, 0, midY);
        var leftBottomQuadrant = countQuadrant(space, 0, midX, midY + 1, space.length);
        var rightTopQuadrant = countQuadrant(space, midX + 1, space[0].length, 0, midY);
        var rightBottomQuadrant = countQuadrant(space, midX + 1, space[0].length, midY + 1, space.length);
        return leftTopQuadrant * leftBottomQuadrant * rightTopQuadrant * rightBottomQuadrant;
    }

    private static long countQuadrant(int[][] space, int startX, int endX, int startY, int endY) {
        var count = 0L;
        for (var i = startY; i < endY; i++) {
            for (var j = startX; j < endX; j++) {
                count += space[i][j];
            }
        }
        return count;
    }

    private static Robot parseRobot(String line) {
        int x = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
        int y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(" ")));
        int velocityX = Integer.parseInt(line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf(",")));
        int velocityY = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1));
        return new Robot(x, y, velocityX, velocityY);
    }

    static class Robot {
        int initialX;
        int initialY;
        int velocityX;
        int velocityY;

        public Robot(int x, int y, int velocityX, int velocityY) {
            this.initialX = x;
            this.initialY = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }
    }

    record Pos(int x, int y) {
    }
}