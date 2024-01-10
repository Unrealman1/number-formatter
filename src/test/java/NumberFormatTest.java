import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.NumberFormatter;
import ru.formatter.stack.NumberToWords;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
