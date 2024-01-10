package ru.formatter.stack;

public class UnitProcessor extends AbstractProcessor {

    static private final String[] TOKENS = new String[]{
            "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять",
            "десять", "одинадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать",
            "семнадцать", "восемнадцать", "девятнадцать"
    };

    @Override
    public String getName(String value) {
        StringBuilder buffer = new StringBuilder();

        int offset = NO_VALUE;
        int number;
        if (value.length() > 3) {
            number = Integer.valueOf(value.substring(value.length() - 3), 10);
        } else {
            number = Integer.valueOf(value, 10);
        }
        number %= 100;
        if (number < 10) {
            offset = (number % 10) - 1;
            //number /= 10;
        } else if (number < 20) {
            offset = (number % 20) - 1;
            //number /= 100;
        }

        if (offset != NO_VALUE && offset < TOKENS.length) {
            buffer.append(TOKENS[offset]);
        }

        return buffer.toString();
    }

}
