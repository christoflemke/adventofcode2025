import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.naturalOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1 {

    List<Integer> leftExample = Lists.newArrayList(3, 4, 2, 1, 3, 3);
    List<Integer> rightExample = Lists.newArrayList(4, 3, 5, 3, 9, 3);

    @Test
    public void part1Example() {
        var sum = sumDistance(leftExample, rightExample);
        assertEquals(11, sum);
    }

    private static int sumDistance(List<Integer> left, List<Integer> right) {
        left.sort(naturalOrder());
        right.sort(naturalOrder());
        var sum = 0;
        while (!left.isEmpty() && !right.isEmpty()) {
            sum += Math.abs(left.removeFirst() - right.removeFirst());
        }
        return sum;
    }

    @Test
    public void part1() throws IOException {
        String input = Files.readString(Path.of("input/1/in"));
        List<Integer> right = Lists.newArrayList();
        List<Integer> left = Lists.newArrayList();
        Arrays.stream(input.split("\n")).forEach(i -> {
            if (i.isBlank()) {
                return;
            }
            String[] split = i.split("   ");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        });
        var distance = sumDistance(left, right);
        assertEquals(2378066, distance);
    }

    @Test
    public void part2Example() {
        var sum = similarity(leftExample, rightExample);
        assertEquals(31, sum);
    }

    @Test
    public void part2() throws IOException {
        String input = Files.readString(Path.of("input/1/in"));
        List<Integer> right = Lists.newArrayList();
        List<Integer> left = Lists.newArrayList();
        Arrays.stream(input.split("\n")).forEach(i -> {
            if (i.isBlank()) {
                return;
            }
            String[] split = i.split("   ");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        });
        int similarity = similarity(left, right);
        assertEquals(18934359, similarity);
    }

    private int similarity(List<Integer> left, List<Integer> right) {
        var sum = 0;
        for(int l : left) {
          sum += l * right.stream().filter(r -> l == r).count();
        }
        return sum;
    }
}
