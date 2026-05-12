import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2 {
    List<List<Integer>> exampleInput = readInput(Path.of("input/2/example"));
    List<List<Integer>> input = readInput(Path.of("input/2/in"));

    private List<List<Integer>> readInput(Path path) throws IOException {
        return Files.readAllLines(path)
                .stream()
                .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
                .toList();
    }

    public Day2() throws IOException {
    }

    public static boolean lineSafe(List<Integer> line) {
        for (int i = 0; i < line.size() - 1; i++) {
            var current = line.get(i);
            var next = line.get(i + 1);
            if (next > current && next <= current + 3) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean lineSafe2(List<Integer> line) {
        if(lineSafe(line)) {
            return true;
        }
        for (int i = 0; i < line.size(); i++) {
            var modified = Lists.newArrayList(line);
            modified.remove(i);
            if(lineSafe(modified)) {
                return true;
            }
        }
        return false;
    }

    int countSafe(List<List<Integer>> input, Predicate<List<Integer>> pred) {
        var safeIncreasing = input.stream().filter(pred).count();
        var safeDecreasing = input.stream().map(l -> l.reversed()).filter(pred).count();
        return (int) (safeDecreasing + safeIncreasing);
    }

    @Test
    public void part1Example() {
        assertEquals(2, countSafe(exampleInput, Day2::lineSafe));
    }

    @Test
    public void part1() {
        assertEquals(624, countSafe(input, Day2::lineSafe));
    }

    @Test
    public void part2Example() {
        assertEquals(4, countSafe(exampleInput, Day2::lineSafe2));
    }

    @Test
    public void part2() {
        assertEquals(658, countSafe(input, Day2::lineSafe2));
    }
}
