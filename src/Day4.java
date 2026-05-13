import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4 {
  final List<String> input = Files.readAllLines(Path.of("input/4/input"));
  final List<String> example = Files.readAllLines(Path.of("input/4/example"));

  public Day4() throws IOException {
  }

  String[][] toArray(List<String> in) {
    String[][] out = new String[in.size()][in.get(0).length()];
    for (int i = 0; i < in.size(); i++) {
      String line = in.get(i);
      for (int j = 0; j < line.length(); j++) {
        out[i][j] = line.substring(j, j + 1);
      }
    }
    return out;
  }

  String[][] rotate(String[][] in) {
    var n = in[0].length;
    var m = in.length;
    String[][] out = new String[m][n];

    IntStream.range(0, m).forEach(i ->
      IntStream.range(0, n).forEach(j -> {
        out[j][m - 1 - i] = in[i][j];
      }));
    return out;
  }

  @Test
  void testToMatrix() {
    String[][] m = toArray(example);
    assertThat(m[0][0]).isEqualTo("M");
    assertThat(m[0][3]).isEqualTo("S");
    assertThat(m[2][0]).isEqualTo("A");
  }

  @Test
  void testRotate() {
    String[][] m = rotate(toArray(example));
    assertThat(m[0][0]).isEqualTo("M");
    assertThat(m[0][2]).isEqualTo("S");
    assertThat(m[2][0]).isEqualTo("M");

    m = rotate(rotate(rotate(rotate(toArray(example)))));
    assertThat(m[0][0]).isEqualTo("M");
    assertThat(m[0][3]).isEqualTo("S");
    assertThat(m[2][0]).isEqualTo("A");
  }

  int searchHorizontal(String[][] in) {
    var count = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length; j++) {
        String s = Arrays.stream(in[i]).collect(Collectors.joining("")).substring(j);
        if (s.matches("XMAS.*")) {
          count++;
        }
      }
    }
    return count;
  }

  int searchDiagonal(String[][] in) {
    var count = 0;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[0].length; j++) {
        if (in[i][j].equals("X")) {
          count += searchDiagonal(in, i, j);
        }
      }
    }
    return count;
  }

  int searchDiagonal(String[][] in, int x, int y) {
    String s = "";
    for (int i = 0; i < in.length; i++) {
      int row = x + i;
      int column = y + i;
      if (row >= in.length) {
        break;
      }
      if (column >= in[0].length) {
        break;
      }
      s += in[row][column];
    }
    if (s.matches("XMAS.*")) {
      return 1;
    }
    return 0;
  }

  @Test
  void testSearchHorizontal() {
    int count = searchHorizontal(toArray(example));
    assertThat(count).isEqualTo(3);
  }

  @Test
  void testSearchDiagonal() {
    var count = searchDiagonal(toArray(example));
    assertThat(count).isEqualTo(1);
  }

  int searchAll(String[][] in) {
    var count = 0;
    for (int i = 0; i < 4; i++) {
      count += searchHorizontal(in);
      count += searchDiagonal(in);
      in = rotate(in);
    }
    return count;
  }

  @Test
  void part1Example() {
    var count = searchAll(toArray(example));
    assertThat(count).isEqualTo(18);
  }

  @Test
  void part1() {
    var count = searchAll(toArray(input));
    assertThat(count).isEqualTo(2554);
  }

  int searchX(String[][] in) {
    var count = 0;
    for (int i = 1; i < in.length - 1; i++) {
      for (int j = 1; j < in[0].length - 1; j++) {
        count += searchX(in, i, j);
      }
    }
    return count;
  }

  int searchX(String[][] in, int x, int y) {
    if (
      in[x][y].equals("A") &&
        in[x - 1][y - 1].equals("M") &&
        in[x - 1][y + 1].equals("M") &&
        in[x + 1][y - 1].equals("S") &&
        in[x + 1][y + 1].equals("S")
    ) {
      return 1;
    }
    return 0;
  }

  int searchAllX(String[][] in) {
    var count = 0;
    for (int i = 0; i < 4; i++) {
      count += searchX(in);
      in = rotate(in);
    }
    return count;
  }

  @Test
  void testSearchXMinimal() {
    String[][] m = {
      {"M", ".", "M"},
      {".", "A", ","},
      {"S", ".", "S"},
    };
    assertThat(searchX(m)).isEqualTo(1);
    assertThat(searchX(m, 1, 1)).isEqualTo(1);
  }

  @Test
  void testSearchXBigger() {
    String[][] m = {
      {"M", ".", "M", "."},
      {".", "A", ",", "."},
      {"S", ".", "S", "."},
      {".", ".", ".", "."},
    };
    assertThat(searchX(m, 1, 1)).isEqualTo(1);
    assertThat(searchX(m)).isEqualTo(1);
  }

  @Test
  void testSearchX() {
    String[][] m = toArray(example);
    int count = searchX(m);
    assertThat(count).isEqualTo(1);

    m = rotate(m);
    count = searchX(m);
    assertThat(count).isEqualTo(2);

    m = rotate(m);
    count = searchX(m);
    assertThat(count).isEqualTo(5);

    m = rotate(m);
    count = searchX(m);
    assertThat(count).isEqualTo(1);
  }

  @Test
  void part2Example() {
    var count = searchAllX(toArray(example));
    assertThat(count).isEqualTo(9);
  }

  @Test
  void part2() {
    assertThat(searchAllX(toArray(input))).isEqualTo(1916);
  }
}
