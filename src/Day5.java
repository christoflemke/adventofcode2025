import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class Day5 {
  final List<String> example = Files.readAllLines(Path.of("input/5/example"));
  final List<String> input = Files.readAllLines(Path.of("input/5/input"));

  public Day5() throws IOException {
  }

  class Content {
    final List<Integer[]> rules;
    final List<List<Integer>> manuals;

    Content(List<String> in) {
      int i = in.indexOf("");

      List<String> ruleLines = in.subList(0, i);
      this.rules = ruleLines.stream().map(line -> {
        String[] split = line.split("\\|");
        return new Integer[]{parseInt(split[0]), parseInt(split[1])};
      }).toList();

      List<String> manualLines = in.subList(i+1, in.size());
      this.manuals =
        manualLines.stream()
          .map(line -> Arrays.stream(line.split(",")).map(Integer::parseInt).toList()).toList();
    }
  }

  @Test
  void testParse() {
    Content content = new Content(example);
    assertThat(content.rules.size()).isEqualTo(21);
    assertThat(content.manuals.size()).isEqualTo(6);
  }

  List<List<Integer>> validManuals(Content content) {
    return content.manuals.stream().filter(manual -> validateManual(manual, content.rules)).toList();
  }

  List<List<Integer>> invalidManuals(Content content) {
    return content.manuals.stream().filter(manual -> !validateManual(manual, content.rules)).toList();
  }

  private boolean validateManual(List<Integer> manual, List<Integer[]> rules) {
    for (Integer[] rule : rules) {
      int first = manual.indexOf(rule[0]);
      int second = manual.indexOf(rule[1]);
      if( first == -1 || second == -1) {
        continue;
      }
      if (second > first) {
        continue;
      }
      return false;
    }
    return true;
  }

  List<Integer> findMiddle(List<List<Integer>> manuals) {
    return manuals.stream().map(manual -> manual.get(manual.size()/2)).toList();
  }

  @Test
  void part1Example() {
    Content content = new Content(example);
    List<List<Integer>> validManuals = validManuals(content);
    assertThat(validManuals).hasSize(3);

    List<Integer> middle = findMiddle(validManuals);
    int sum = middle.stream().mapToInt(i -> i).sum();
    assertThat(sum).isEqualTo(143);
  }

  @Test
  void part1() {
    Content content = new Content(input);
    List<List<Integer>> validManuals = validManuals(content);
    assertThat(validManuals).hasSize(114);

    List<Integer> middle = findMiddle(validManuals);
    int sum = middle.stream().mapToInt(i -> i).sum();
    assertThat(sum).isEqualTo(6242);
  }

  @Test void part2Example() {
    Content content = new Content(example);
    List<List<Integer>> invalid = invalidManuals(content);
    assertThat(invalid).hasSize(3);
    List<List<Integer>> reordered = reorderManuals(invalid, content.rules);
    List<Integer> middle = findMiddle(reordered);
    int sum = middle.stream().mapToInt(i -> i).sum();
    assertThat(sum).isEqualTo(123);
  }

  @Test void part2() {
    Content content = new Content(input);
    List<List<Integer>> invalid = invalidManuals(content);
    assertThat(invalid).hasSize(86);
    List<List<Integer>> reordered = reorderManuals(invalid, content.rules);
    List<Integer> middle = findMiddle(reordered);
    int sum = middle.stream().mapToInt(i -> i).sum();
    assertThat(sum).isEqualTo(5169);
  }

  private List<List<Integer>> reorderManuals(List<List<Integer>> invalid, List<Integer[]> rules) {
    return invalid.stream().map(m -> reorderManual(m, rules)).toList();
  }

  private List<Integer> reorderManual(List<Integer> m, List<Integer[]> rules) {
    var result = Lists.newArrayList(m);
    var changed = false;
    do {
      changed = false;
      for(Integer[] rule : rules) {
        int first = result.indexOf(rule[0]);
        int second = result.indexOf(rule[1]);
        if (first == -1 || second == -1) {
          continue;
        }
        if (first > second) {
          result.set(first, rule[1]);
          result.set(second, rule[0]);
          changed = true;
        }
      }
    } while (changed);
    return result;
  }

}
