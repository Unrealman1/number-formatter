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
            return processor.getName(number).replaceFirst("один целых", "одна целая");
        }
        return processor.getName(number);
    }

    static public class ScaleUnit {
        private int exponent;
        private String[] names;

        private ScaleUnit(int exponent, String... names) {
            this.exponent = exponent;
            this.names = names;
        }

        public int getExponent() {
            return exponent;
        }

        public String getName(int index) {
            return names[index];
        }
    }

    /**
     * See <a href="http://www.wordiq.com/definition/Names_of_large_numbers">...</a>
     */
    static public final ScaleUnit[] SCALE_UNITS = new ScaleUnit[]{
            new ScaleUnit(63, "vigintillion", "decilliard"),
            new ScaleUnit(60, "novemdecillion", "decillion"),
            new ScaleUnit(57, "octodecillion", "nonilliard"),
            new ScaleUnit(54, "septendecillion", "nonillion"),
            new ScaleUnit(51, "sexdecillion", "octilliard"),
            new ScaleUnit(48, "quindecillion", "octillion"),
            new ScaleUnit(45, "quattuordecillion", "septilliard"),
            new ScaleUnit(42, "tredecillion", "septillion"),
            new ScaleUnit(39, "duodecillion", "sextilliard"),
            new ScaleUnit(36, "undecillion", "sextillion"),
            new ScaleUnit(33, "decillion", "quintilliard"),
            new ScaleUnit(30, "nonillion", "quintillion"),
            new ScaleUnit(27, "octillion", "quadrilliard"),
            new ScaleUnit(24, "септиллион", "септиллиона"),
            new ScaleUnit(21, "секстеллион", "секстеллиона"),
            new ScaleUnit(18, "квинтиллион", "квинтиллиона"),
            new ScaleUnit(15, "квадриллион", "квадриллиона"),
            new ScaleUnit(12, "триллион", "триллиона"),
            new ScaleUnit(9, "миллиард", "миллиарда"),
            new ScaleUnit(6, "миллион", "миллиона"),
            new ScaleUnit(3, "тысяч", "тысяча"),
            new ScaleUnit(2, "сто", "сто"),
            //new ScaleUnit(1, "ten", "ten"),
            //new ScaleUnit(0, "one", "one"),
            new ScaleUnit(-1, "десятых", "tenth"),
            new ScaleUnit(-2, "сотых", "hundredth"),
            new ScaleUnit(-3, "тысячных", "thousandth"),
            new ScaleUnit(-4, "десятитысячных", "ten-thousandth"),
            new ScaleUnit(-5, "стотысячных", "hundred-thousandth"),
            new ScaleUnit(-6, "миллионных", "millionth"),
            new ScaleUnit(-7, "десятимиллионных", "ten-millionth"),
            new ScaleUnit(-8, "стомиллионных", "hundred-millionth"),
            new ScaleUnit(-9, "миллиардных", "milliardth"),
            new ScaleUnit(-10, "десятимиллиардных", "ten-milliardth"),
            new ScaleUnit(-11, "стомиллиардных", "hundred-milliardth"),
            new ScaleUnit(-12, "триллионных", "billionth"),
            new ScaleUnit(-13, "десятитриллионных", "ten-billionth"),
            new ScaleUnit(-14, "стотриллионных", "hundred-billionth"),
            new ScaleUnit(-15, "квадриллионных", "billiardth"),
            new ScaleUnit(-16, "десятиквадриллионных", "ten-billiardth"),
            new ScaleUnit(-17, "стоквадриллионных", "hundred-billiardth"),
            new ScaleUnit(-18, "квинтиллионных", "trillionth"),
            new ScaleUnit(-19, "десятиквинтиллионных", "ten-trillionth"),
            new ScaleUnit(-20, "стоквинтиллионных", "hundred-trillionth"),
            new ScaleUnit(-21, "секстиллионных", "trilliardth"),
            new ScaleUnit(-22, "ten-sextillionth", "ten-trilliardth"),
            new ScaleUnit(-23, "hundred-sextillionth", "hundred-trilliardth"),
            new ScaleUnit(-24, "septillionth", "quadrillionth"),
            new ScaleUnit(-25, "ten-septillionth", "ten-quadrillionth"),
            new ScaleUnit(-26, "hundred-septillionth", "hundred-quadrillionth"),
    };

    static public enum Scale {
        SHORT,
        LONG;

        public String getName(int exponent) {
            for (ScaleUnit unit : SCALE_UNITS) {
                if (unit.getExponent() == exponent) {
                    return unit.getName(this.ordinal());
                }
            }
            return "";
        }
    }

    /**
     * Change this scale to support American and modern British value (short scale)
     * or Traditional British value (long scale)
     */
    static public Scale SCALE = Scale.SHORT;


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
