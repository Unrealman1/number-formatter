import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.NumberFormatter;
import ru.formatter.stack.NumberToWords;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFormatTest {

    private final NumberFormatter numberFormatter = new NumberToWords();
    private final Pattern Only_Russian_Symbols = Pattern.compile("^[а-яА-Я ]+$");

    @Test
    public void testTrailingZeros() {
        assertEquals("один", numberFormatter.format("1.000"));
        assertEquals("одна целая одна десятая", numberFormatter.format("1.1000"));
    }


    @Test
    public void testConvertZero() {
        assertEquals("ноль", numberFormatter.format("0"));
    }

    @Test
    public void testConvertOne() {
        assertEquals("один", numberFormatter.format("1"));
    }

    @Test
    public void testConvertEighteen() {
        assertEquals("восемнадцать", numberFormatter.format("18"));
    }

    @Test
    public void testConvertTwenty() {
        assertEquals("двадцать", numberFormatter.format("20"));
    }

    @Test
    public void testConvertOneHundred() {
        assertEquals("сто", numberFormatter.format("100"));
    }

    @Test
    public void testConvertThreeHundred() {
        assertEquals("триста", numberFormatter.format("300"));
    }


    @Test
    public void testConvertOneThousand() {
        assertEquals("одна тысяча", numberFormatter.format("1000"));
    }

    @Test
    public void testConvertOneMillion() {
        assertEquals("один миллион", numberFormatter.format("1000000"));
    }

    @Test
    public void testConvertOneBillion() {
        assertEquals("один миллиард", numberFormatter.format("1000000000"));
    }

    @Test
    public void testFormatLargeNumber() {

        assertEquals("один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот " +
                        "девяносто целых один квинтиллион двести тридцать четыре квадриллиона пятьсот шестьдесят семь триллионов " +
                        "восемьсот девяносто миллиардов сто двадцать три миллиона",
                numberFormatter.format("1234567890.12345678901234567890"));
    }

    @Test
    public void testFormatSmallNumber() {

//        assertEquals("ноль целых одна миллиардная", numberFormatter.format("0.000000001"));
        assertEquals("ноль целых одна квинтиллионая", numberFormatter.format("0.11111111111111111111"));
    }

    @Test
    public void testFormatTwoDecimals() {

        assertEquals("одна целая двадцать три сотых", numberFormatter.format("1.23"));
    }


    @Test
    public void testZero() {
        String expected = "ноль";
        String actual = numberFormatter.format("0");
        assertEquals(expected, actual);
    }

    @Test
    public void testInteger() {
        String number = "12345678901234567890";
        String expected = "двенадцать квинтиллионов триста сорок пять квадриллионов шестьсот семьдесят восемь триллионов девятьсот одиннадцать миллиардов двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFraction() {
        String number = "1234567890.12345678901234567890";
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целыхсто двадцать три миллиона четыреста пятьдесят шесть тысяч семьсот восемьдесят девять десятимиллионныхдвенадцать триллионов четыреста пятьдесят шесть миллиардов семьсот восемьдесят девять миллионов двенадцать тысяч триста сорок пять десятимиллиардных";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testNegative() {
        String number = "-1234567890.12345678901234567890";
        String expected = "минус один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целыхсто двадцать три миллиона четыреста пятьдесят шесть тысяч семьсот восемьдесят девять десятимиллионныхдвенадцать триллионов четыреста пятьдесят шесть миллиардов семьсот восемьдесят девять миллионов двенадцать тысяч триста сорок пять стомиллиардных";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFractionZero() {
        String number = "1234567890.00000000000000000000";
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целых";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFractionOne() {
        String number = "1234567890.10000000000000000000";
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целых одна десятая";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFractionTwo() {
        String number = "1234567890.12000000000000000000";
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целых двенадцать стотысячных";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFractionThree() {
        String number = "1234567890.12300000000000000000";
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целых сто двадцать три тысячных";
        String actual = numberFormatter.format(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testFractionFour() {
        String expected = "один миллиард двести тридцать четыре миллиона пятьсот шестьдесят семь тысяч восемьсот девяносто целых";
        String actual = numberFormatter.format("1234567890.12340000000000000000");
        assertEquals(expected, actual);
    }

    @Test
    public void testlowNumbers() {
        String[][] qas = {
                {"0.36312040047022176026",
                        "ноль тридцать шесть квинтиллионов триста двенадцать квадриллионов сорок триллионов сорок семь миллиардов двадцать два миллиона сто семьдесят шесть тысяч двадцать шесть стоквинтиллионных"},
                {"0.85882031235964539917",
                        "ноль восемьдесят пять квинтиллионов восемьсот восемьдесят два квадриллиона тридцать один триллион двести тридцать пять миллиардов девятьсот шестьдесят четыре миллиона пятьсот тридцать девять тысяч девятьсот семнадцать"},
                {"0.15644331981133396958",
                        "ноль пятнадцать квинтиллионов шестьсот сорок четыре квадриллиона триста тридцать один триллион девятьсот восемьдесят один миллиард сто тридцать три миллиона триста девяносто шесть тысяч девятьсот пятьдесят восемь"},
                {"0.12027167394518656351",
                        "ноль двенадцать квинтиллионов двадцать семь квадриллионов сто шестьдесят семь триллионов триста девяносто четыре миллиарда пятьсот восемнадцать миллионов шестьсот пятьдесят шесть тысяч триста пятьдесят одна"},
                {"0.34007673048821462913",
                        "ноль тридцать четыре квинтиллиона семь квадриллионов шестьсот семьдесят три триллиона сорок восемь миллиардов восемьсот двадцать один миллион четыреста шестьдесят две тысячи девятьсот тринадцать"},
                {"0.97670265464023864332",
                        "ноль девяносто семь квинтиллионов шестьсот семьдесят квадриллионов двести шестьдесят пять триллионов четыреста шестьдесят четыре миллиарда двадцать три миллиона восемьсот шестьдесят четыре тысячи триста тридцать две"},
                {"0.18897469220718310368",
                        "ноль восемнадцать квинтиллионов восемьсот девяносто семь квадриллионов четыреста шестьдесят девять триллионов двести двадцать миллиардов семьсот восемнадцать миллионов триста десять тысяч триста шестьдесят восемь"},
                {"0.78642141969434486981",
                        "ноль семьдесят восемь квинтиллионов шестьсот сорок два квадриллиона сто сорок один триллион девятьсот шестьдесят девять миллиардов четыреста тридцать четыре миллиона четыреста восемьдесят шесть тысяч девятьсот восемьдесят одна"},
                {"0.79755153745354312456",
                        "ноль семьдесят девять квинтиллионов семьсот пятьдесят пять квадриллионов сто пятьдесят три триллиона семьсот сорок пять миллиардов триста пятьдесят четыре миллиона триста двенадцать тысяч четыреста пятьдесят шесть"},
                {"0.37118415277531591201",
                        "ноль тридцать семь квинтиллионов сто восемнадцать квадриллионов четыреста пятнадцать триллионов двести семьдесят семь миллиардов пятьсот тридцать один миллион пятьсот девяносто одна тысяча двести одна"}
        };
        for (String[] qa : qas) {
            assertEquals(qa[1], numberFormatter.format(qa[0]));
        }
    }

    @Test
    public void testPluralization() {
        assertEquals("один миллион одна тысяча один", numberFormatter.format("1001001"));
        assertEquals("один миллион одна тысяча один целых один миллион одна тысяча одна десятимиллионная",
                numberFormatter.format("1001001.1001001"));
        assertEquals("два миллиона две тысячи двe", numberFormatter.format("2002002"));
        assertEquals("три миллиона три тысячи три", numberFormatter.format("3003003"));
    }

    @Test
    public void testStability() {
        Assertions.assertDoesNotThrow(() -> {
            new Random().doubles(10000L)
                    .forEach(value -> {
                        String formatted = numberFormatter.format(new BigDecimal(value)
                                .setScale(20, RoundingMode.DOWN).toString());
                        boolean condition = Only_Russian_Symbols.matcher(formatted).find();
                        if (!condition) {
                            System.out.println(formatted);
                        }
                        Assertions.assertTrue(condition);
                    });
        });
    }

}
