package tech.relialab.day9;

import tech.relialab.Common;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static final String EMPTY_SPACE = ".";

    public static void main(String[] args) {
        var input = Common.getInput("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/day9/input");
        System.out.println("[1] checksum: " + part1(input));
        System.out.println("[2] checksum: " + part2(input));
    }

    private static long part1(List<String> input) {
        var decodeRules = input.getFirst();
        var disk  = decodeDisk(decodeRules);
        fragmentDisk1(disk);
        // 00...111...2...333.44.5555.6666.777.888899
        // 0099811188827773336446555566..............
        for (String s : disk) {
            System.out.print(s);
        }
        System.out.println();
        return countChecksum(disk);
    }

    private static long part2(List<String> input) {
        var decodeRules = input.getFirst();
        var disk  = decodeDisk(decodeRules);
        fragmentDisk2(disk);
        // 00...111...2...333.44.5555.6666.777.888899
        // 00992111777.44.333....5555.6666.....8888..
        for (String s : disk) {
            System.out.print(s);
        }
        System.out.println();
        return countChecksum(disk);
    }

    private static String[] decodeDisk(String diskMap) {
        // 2333133121414131402
        // 00...111...2...333.44.5555.6666.777.888899
        // 0099811188827773336446555566..............

        // 6359213660505
        var disk = new String[300_000];
        int m = 0;
        int fileId = 0;
        for (int i = 0; i < diskMap.length(); i++) {
            var isFile = i % 2 == 0;
            var freq = Character.getNumericValue(diskMap.charAt(i));
            for (int j = 0; j < freq; j++) {
                if (isFile) {
                    disk[m++] = String.valueOf(fileId);
                } else {
                    disk[m++] = EMPTY_SPACE;
                }
            }
            if (isFile) {
                fileId++;
            }
        }
        return Arrays.copyOf(disk, m);
    }

    private static void fragmentDisk1(String[] disk) {
        int left = 0;
        int right = disk.length - 1;
        while (left < right) {
            while (left < right && !disk[left].equals(".")) {
                left++;
            }
            while (left < right && disk[right].equals(".")) {
                right--;
            }
            var tmp = disk[left];
            disk[left] = disk[right];
            disk[right] = tmp;
            left++;
            right--;
        }
    }

    private static long countChecksum(String[] disk) {
        var checksum = 0L;
        for (int n = 0; n < disk.length; n++) {
            if (!disk[n].equals(EMPTY_SPACE)) {
                checksum += Long.parseLong(String.valueOf(disk[n])) * n;
            }
        }
        return checksum;
    }

    // 00...111...2...333.44.5555.6666.777.888899
    // 00992111777.44.333....5555.6666.....8888..
    private static void fragmentDisk2(String[] disk) {
        int left = 0;
        int right = disk.length - 1;
        while (left < right) {
            while (left < right && !disk[left].equals(EMPTY_SPACE)) {
                left++;
            }
            while (left < right && disk[right].equals(EMPTY_SPACE)) {
                right--;
            }

            var requiredSpace = countRequiredSpace(disk, left, right);
            if (requiredSpace == -1) {
                return;
            }
            int startEmptySpace = findEmptySpace(disk, left, right, requiredSpace);
            if (startEmptySpace != -1) {
                swap(disk, startEmptySpace, right, requiredSpace);
                if (startEmptySpace == left) {
                    left += requiredSpace;
                }
            }
            right -= requiredSpace;
        }
    }

    private static int countRequiredSpace(String[] disk, int leftBound, int start) {
        var end = start;
        while (start - 1 > leftBound && disk[start].equals(disk[start - 1])) {
            start--;
        }
        return start == leftBound ? -1 : end - start + 1;
    }

    private static int findEmptySpace(String[] disk, int leftBound, int rightBound, int needSpace) {
        while (leftBound < rightBound) {
            while (leftBound < rightBound && !disk[leftBound].equals(EMPTY_SPACE)) {
                leftBound++;
            }
            if (leftBound < rightBound) {
                int start = leftBound;
                int end = leftBound;
                while (end < rightBound && disk[end].equals(disk[start])) {
                    end++;
                }
                if (end - start + 1 > needSpace) {
                    return start;
                }
                leftBound = end + 1;
            } else {
                leftBound++;
            }
        }
        return -1;
    }

    private static void swap(String[] disk, int left, int right, int space) {
        var fileId = disk[right];
        while (space > 0) {
            disk[left++] = fileId;
            disk[right--] = EMPTY_SPACE;
            space--;
        }
    }
}