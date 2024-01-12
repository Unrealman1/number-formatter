package ru.formatter.stack;

import ru.NumberFormatter;

/**
 * <a href="https://stackoverflow.com/a/3911982">Оригинал для английского</a>
 */
public class NumberToWords implements NumberFormatter {

    private final AbstractProcessor processor = new DefaultProcessor();

    @Override
    public String format(String number) {
        if (number.contains(".")) {
            number = number.replaceFirst("0+$", "");
            if (number.endsWith(".")) {
                return processor.getName(number.substring(0, number.length() - 1));
            }
            return processor.getName(number);//.replaceFirst("один целых", "одна целая");
        }
        return processor.getName(number);
    }



    public static void main(String... args) {

        var processor = new DefaultProcessor();

        long[] values = new long[]{
                0,
                4,
                10,
                12,
                100,
                108,
                299,
                1000,
                1003,
                2040,
                45213,
                100000,
                100005,
                100010,
                202020,
                202022,
                999999,
                1000000,
                1000001,
                10000000,
                10000007,
                99999999,
                Long.MAX_VALUE,
                Long.MIN_VALUE
        };

        String[] strValues = new String[]{
                "0001.2",
                "3.141592"
        };

        for (long val : values) {
            System.out.println(val + " = " + processor.getName(val));
        }

        for (String strVal : strValues) {
            System.out.println(strVal + " = " + processor.getName(strVal));
        }

        // generate a very big number...
        StringBuilder bigNumber = new StringBuilder();
        for (int d = 0; d < 66; d++) {
            bigNumber.append((char) ((Math.random() * 10) + '0'));
        }
        bigNumber.append(".");
        for (int d = 0; d < 26; d++) {
            bigNumber.append((char) ((Math.random() * 10) + '0'));
        }

        System.out.println(bigNumber.toString() + " = " + processor.getName(bigNumber.toString()));

    }

}
