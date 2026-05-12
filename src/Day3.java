import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3 {
    final String example = Files.readString(Path.of("input/3example"));
    final String example2 = Files.readString(Path.of("input/3example2"));
    final String input = Files.readString(Path.of("input/3"));
    final Pattern mulPattern = Pattern.compile("mul\\((?<left>\\d{1,3}),(?<right>\\d{1,3})\\).*");
    final Pattern doPattern = Pattern.compile("do\\(\\).*");
    final Pattern dontPattern = Pattern.compile("don't\\(\\).*");

    public Day3() throws IOException {
    }

    @Test
    void patternTest() {
        assertThat(mulPattern.matcher("mul(1,2)")).matches();
        assertThat(mulPattern.matcher("mul(123,234)")).matches();
        assertThat(mulPattern.matcher("mul(123,234)zxczx")).matches();
        assertThat(mulPattern.matcher("mul(371,776)/%how()")).matches();
        assertThat(mulPattern.matcher("mul(371,776)/%how()'~+:how()mul(977,266)@$@mul(749,170)how()<;$^#what()select()mul(338,975)>from()+,{select()!!mul(626,938)%/>^{((}]mul(733,977)>)() +$)mul(695,376)!%#how()mul(767,788)<(mul(876,501)mul(18,72)[*when(625,65)<mul(515,137)>*('what()mul(491,884)from()?{>how()mul(645,385)/[when(){what()why()mul(802,476)#'select()who(327,719)+-,##mul(103,807)#(mul(192,909):@'^where(247,392)!who()</mul(305,182)[@[;!;mul(553,402)<%mul(247,89)!mul(91,152)$@mul(502,543)+why()what()[when()@>^mul(875,344)why()mul(205,505)?;?when()!],mul(165,670),#$++'>)'mul(503,815):mul(483,302)~+*}!$where()why()&mul(785,794)/,{why();where(218,722)}when()don't();!mul(405,577)where()@mul(521,472){how()what()'what() where(55,484)mul(360,384)%mul(176,384)}%when()-$%what(436,501)mul(652,387)mul(758,52)-~@,*-@mul(343,835)when()select()](;#mul(76,425)from()>})]mul(720,464)#~)why()why()mul(619,682))mul(15,67)mul(569,569)]where()++&@:[#mul(844,620)select()+^*what()why(197,509) mul(280,807)%^mul(233,435)}?*:+^^what()!when()mul(69,464)& )]$mul(393,827)@]^mul(64,552):^)*<&where()?#mul(51,727)mul(159,242),+what())>+mul(140,116)do()~?mul(590,950)};[*;;when()mul(186,556)]&;#&##*!mul(806,651)^select()who()[mul(820,386);?from()mul(771,404)-$+#]&where()}mul(473,823)don't()when(874,250)'}mul(916,835)mul(432,92)how(), :mul(2,546)who(61,440), -mul(486,858)where(869,462)do()'select()&?+&why()where()>>mul(796,512){~(when():^>*&'mul(927,22)%;where()who()^$mul(203,851)%how()mul(820,954)from())~#<don't()select();@??]?mul(693,878)from()when()@/mul(202,438)mul(266,957)from()])[/how()select()don't()+>mul(741,967)select()what()+[[why()&]mul(42,517)>]why()<mul(577,659)&['<where()>,select(297,821):mul(324,191);%{->:!#mul(567,73)don't()?why():^{$*]why()mul(504,229)'mul(40,787)/select()?who();[who()where();$mul(537,42)[#@&*~<@mul(418,58);{}how()*-don't()[<'@from() mul(38,329)-*mul(381,685)what()</:)^(',when()mul(850,565)@,,%select(95,745)#mul(99,343),]^who()[mul(46,377)mul(788,562)?<mul(101,829)]$#-]select()mul(536,292)who(419,754)mul(933:[>?+,,*%)mul(740,7)#)<how()mul(44,988)mul(693,770)why()-;[mul(76,905)where()];mul(457,100)what()mul(63,750)where()!&who()$>#)?^mul(211,355){~#who()<]who()@>~mul(135,667)-^)]'mul(165,161)<;,mul(350,311);%?~}$how(768,325)*mul(773,359)~!(select()?{why()mul(269,149)when()}*who()}mul(536,90)don't()>$ &!where()*who()&mul(405,425)!+mul(304,694)'<#$<<,#select()<mulselect()<?mul(448,840)!]-;mul(79,274)>):%:* -why()#mul[who()when(918,883){ ^''mul(907,968)what()when()[who()>mul(81'how()+what()select()<mul(66,242)mul(32,790)why()why()mul(707,867);{+[@select()@,mul(183,895)~mul(570)],:select(),%{%from()mul(568,881)[{@/}*mul(989,413)^($who()!}<&;mul(380,574)+why()?select()#mul(812,587)why()when()mul(772,647)>;&'']+how()mul(745,379)]^-mul(403,82)mul(840,830)when()+what()how()[!select()mul(827,564)?:]mul(602,333){what()[[mul(606,272)"));
    }

    @Test
    void doDontPattern() {
        assertThat(doPattern.matcher("do()")).matches();
        assertThat(doPattern.matcher("do()asdasda")).matches();
        assertThat(dontPattern.matcher("don't()")).matches();
        assertThat(dontPattern.matcher("don't()asdasda")).matches();
    }

    @Test
    void part1Example() {
        assertThat(computeSum(example)).isEqualTo(161);
    }

    private int computeSum(String in) {
        var sum = 0;
        for (int i = 0; i < in.length(); i++) {
            String substring = in.substring(i).lines().findFirst().get();
            Matcher matcher = mulPattern.matcher(substring);
            if(matcher.matches()) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        return sum;
    }

    @Test
    void part1() {
        assertThat(computeSum(input)).isEqualTo(161);
    }

    private int computeSum2(String in) {
        var sum = 0;
        var enabled = true;
        for (int i = 0; i < in.length(); i++) {
            Optional<String> first = in.substring(i).lines().findFirst();
            if(first.isEmpty()) {
                continue;
            }
            String substring = first.get();
            if(doPattern.matcher(substring).matches()) {
                enabled = true;
                continue;
            }
            if(dontPattern.matcher(substring).matches()) {
                enabled = false;
                continue;
            }
            Matcher matcher = mulPattern.matcher(substring);
            if(matcher.matches() && enabled) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        return sum;
    }

    @Test
    void part2Example() {
        assertThat(computeSum2(example2)).isEqualTo(48);
    }

    @Test
    void part2() {
        assertThat(computeSum2(input)).isEqualTo(0);
    }
}
