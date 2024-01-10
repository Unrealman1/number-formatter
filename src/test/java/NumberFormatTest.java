import org.junit.jupiter.api.Test;
import pl.allegro.finance.tradukisto.internal.Container;
import pl.allegro.finance.tradukisto.internal.converters.HundredsToWordsConverter;
import pl.allegro.finance.tradukisto.internal.languages.PluralForms;
import pl.allegro.finance.tradukisto.internal.languages.russian.RussianValues;
import ru.MyBigDecimalToBankingMoneyConverter;
import ru.NumberFormatter;
import ru.formatter.NumberChunking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFormatTest {

    private final NumberFormatter numberFormatter = new NumberFormatter() {
        private final HundredsToWordsConverter hundredsToWordsConverter = new HundredsToWordsConverter(
                new RussianValues().baseNumbers(), ' ');
        private final List<PluralForms> pluralForms = new RussianValues().pluralForms();
        private final NumberChunking numberChunking = new NumberChunking();

        @Override
        public String format(String number) {
            BigDecimal bigDecimal = new BigDecimal(number);

            List<Integer> valueChunks = this.numberChunking.chunk(bigDecimal);
            List<PluralForms> formsToUse = this.getRequiredFormsInReversedOrder(valueChunks.size());
            return this.joinValueChunksWithForms(valueChunks.iterator(), formsToUse.iterator());
/*
            String integerPart = bigDecimal.toBigInteger().toString();
            String integerPart1 = formatIntegerPart(integerPart);
            *//*if (integerPart.equals("0")) {
                return "ноль";
            }*//*
            String fractionalPart = bigDecimal.remainder(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(Math.pow(10, 20)))
                    .toBigInteger().toString();
            if (fractionalPart.equals("0")) {
                return integerPart1;
            }
            return integerPart1 + " целых " + formatFractionalPart(fractionalPart);*/
        }

        protected String joinValueChunksWithForms(Iterator<Integer> chunks, Iterator<PluralForms> formsToUse) {
            List<String> result = new ArrayList<>();

            while (chunks.hasNext() && formsToUse.hasNext()) {
                Integer currentChunkValue = chunks.next();
                PluralForms currentForms = formsToUse.next();
                if (currentChunkValue > 0) {
                    result.add(this.hundredsToWordsConverter.asWords(currentChunkValue, currentForms.genderType()));
                    result.add(currentForms.formFor(currentChunkValue));
                }
            }

            return this.joinParts(result);
        }

        protected String joinParts(List<String> result) {
            return result.isEmpty()
                    ? hundredsToWordsConverter.asWords(0, pluralForms.get(0).genderType())
                    : String.join(" ", result).trim();
        }

        protected List<PluralForms> getRequiredFormsInReversedOrder(int chunks) {
            List<PluralForms> formsToUse = new ArrayList(this.pluralForms.subList(0, chunks));
            Collections.reverse(formsToUse);
            return formsToUse;
        }


        /*private String formatFractionalPart(String fractionalPart) {

            return longConverter.asWords(new BigDecimal(fractionalPart));
        }*/

        /*private String formatIntegerPart(String integerPart) {
            return longConverter.asWords(new BigDecimal(integerPart));
        }*/
    }

            /*new NumberFormatter() {
        private final String[] units = {"ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь",
                "девят",};
        private final String[] decades = {"десять", "одиннадцать", "двадцать", "тридцать", "сорок", "пятьдесят", "шестидесят",
                "семидесят", "восемьдесят", "девяноста"};

        private final Pattern leadingZeros = Pattern.compile("\\^0+");
        private final Pattern trailingZeros = Pattern.compile("0+$");

        private static final String[] ones = {"", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};
        private static final String[] tens = {"", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто"};
        private static final String[] mul = {"", "", "", "сто", "тысяча", "миллион", "миллиард", "триллион", "триллион", "квантимиллион"};

        @Override
        public String format(String number) {
            number = number.replace(",", ".");
//            number = number.replaceAll("0+$", "");
            if (number.endsWith(".")) {
                number = number.substring(0, number.length() - 1);
            }
            final BigDecimal bigDecimal = new BigDecimal(number).stripTrailingZeros();
//            long thousands = Long.parseLong(number) % 1000;
//            double hundreds = Double.parseDouble(number) % 100;
//            double decades = Double.parseDouble(number) % 10;
//            double unit = Double.parseDouble(number) / 10;

            *//*if (number.contains(".")) {
                int start = trailingZeros.matcher(number).regionStart();
                if (start != -1) {
                    number = number.substring(0, start);
                }
                String[] parts = number.split("\\.");

                return number;
//                return cfg[parts[0].length() - 1][0] + " целых " + formatFractions(parts[1]);
            }*//*

            String integerPart = bigDecimal.toBigInteger().toString();
            String fractionalPart = bigDecimal.remainder(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(Math.pow(10, 20)))
                    .toBigInteger().toString();

            String integerPart1 = formatIntegerPart(integerPart);
            if (integerPart.equals("0")) {
                return "ноль";
            }
            if (fractionalPart.equals("0")) {
                return integerPart1;
            }
            return integerPart1 + " целых " + formatFractionalPart(fractionalPart);
        }

        private String formatIntegerPart(String integerPart) {
            String result = "";
            int length = integerPart.length();

            for (int i = 0; i < length; i++) {
                char digit = integerPart.charAt(i);

                if (digit != '0') {
                    result += ones[digit - '0'] + " ";

                    if ((length - i) % 3 == 0 && i != 0) {
                        result += "тысяч ";
                    } else if ((length - i) % 3 == 1 && length != 1) {
                        result += "сотни ";
                    } else if ((length - i) % 3 == 2) {
                        result += "десятки ";
                    }
                }
            }

            return result.trim();
        }

        private String formatFractionalPart(String fractionalPart) {
            // Преобразуем дробную часть числа в слова
            return fractionalPart;
        }


    }*/;


   /*         new NumberFormatter() {
                private final RuleBasedNumberFormat numberFormat = new MyRuleBasedNumberFormat(Locale.forLanguageTag("ru"), RuleBasedNumberFormat.SPELLOUT);

                {
                    numberFormat.setRoundingMode(BigDecimal.ROUND_UNNECESSARY);

                }

                @Override
                public String format(String number) {
                    numberFormat.setMaximumFractionDigits(20);
                    if (number.contains(".")) {
                        String[] parts = number.split("\\.");
                        return numberFormat.format(new BigInteger(parts[0])) + " целых" +
                                numberFormat.format(new BigDecimal("1." + parts[1])
//                                                .setScale(20, RoundingMode.UNNECESSARY)
                                        )
                                        .substring(10);
                    }
                    return numberFormat.format(Long.parseLong(number));
                }
            };*/

    /*new NumberFormatter() {
@Override
public String format(String number) {
    return formatter.format(Double.parseDouble(number));
}

private final NumberFormat formatter = NumberFormat.getInstance(Locale.US);

{
    formatter.setRoundingMode(RoundingMode.UNNECESSARY);
    formatter.setMaximumFractionDigits(20); // Устанавливаем максимальное количество знаков после запятой
}


};*/
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
        assertEquals("одна сотня", numberFormatter.format("100"));
    }

    @Test
    public void testConvertOneThousand() {
        assertEquals("one thousand", numberFormatter.format("1000"));
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

        assertEquals("1234567890.12345678901234567890", numberFormatter.format("1234567890.12345678901234567890"));
    }

    @Test
    public void testFormatSmallNumber() {

//        assertEquals("ноль целых одна миллиардная", numberFormatter.format("0.000000001"));
        assertEquals("ноль целых одна квинтиллионая", numberFormatter.format("0.11111111111111111111"));
    }

    @Test
    public void testFormatTwoDecimals() {

        assertEquals("один целых двадцать три сотых", numberFormatter.format("1.23"));
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

}
