package ru.formatter.stack;

public class RuLocale {
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
            new ScaleUnit(24, "септиллион", "септиллиона", "септиллионов"),
            new ScaleUnit(21, "секстеллион", "секстеллиона", "секстеллионов"),
            new ScaleUnit(18, "квинтиллион", "квинтиллиона", "квинтиллионов"),
            new ScaleUnit(15, "квадриллион", "квадриллиона", "квадриллионов"),
            new ScaleUnit(12, "триллион", "триллиона", "триллионов"),
            new ScaleUnit(9, "миллиард", "миллиарда", "миллиардов"),
            new ScaleUnit(6, "миллион", "миллиона", "миллионов"),
            new ScaleUnit(3, "тысяча", "тысяч", "тысяч"),
            new ScaleUnit(2, "сто", "сто", "сотен"),
            //new ScaleUnit(1, "ten", "ten"),
            //new ScaleUnit(0, "one", "one"),
            new ScaleUnit(-1, "десятых", "десятая"),
            new ScaleUnit(-2, "сотых", "сотая"),
            new ScaleUnit(-3, "тысячных", "тысячная"),
            new ScaleUnit(-4, "десятитысячных", "десятитысячная"),
            new ScaleUnit(-5, "стотысячных", "стотысячная"),
            new ScaleUnit(-6, "миллионных", "миллионная"),
            new ScaleUnit(-7, "десятимиллионных", "десятимиллионная"),
            new ScaleUnit(-8, "стомиллионных", "стомиллионная"),
            new ScaleUnit(-9, "миллиардных", "миллиардная"),
            new ScaleUnit(-10, "десятимиллиардных", "десятимиллиардная"),
            new ScaleUnit(-11, "стомиллиардных", "стомиллиардная"),
            new ScaleUnit(-12, "триллионных", "триллионная"),
            new ScaleUnit(-13, "десятитриллионных", "десятитриллионная"),
            new ScaleUnit(-14, "стотриллионных", "стотриллионная"),
            new ScaleUnit(-15, "квадриллионных", "квадриллионная"),
            new ScaleUnit(-16, "десятиквадриллионных", "десятиквадриллионная"),
            new ScaleUnit(-17, "стоквадриллионных", "стоквадриллионная"),
            new ScaleUnit(-18, "квинтиллионных", "квинтиллионная"),
            new ScaleUnit(-19, "десятиквинтиллионных", "десятиквинтиллионная"),
            new ScaleUnit(-20, "стоквинтиллионных", "стоквинтиллионная"),
            new ScaleUnit(-21, "секстиллионных", "секстиллионная"),
            new ScaleUnit(-22, "ten-sextillionth", "ten-trilliardth"),
            new ScaleUnit(-23, "hundred-sextillionth", "hundred-trilliardth"),
            new ScaleUnit(-24, "septillionth", "quadrillionth"),
            new ScaleUnit(-25, "ten-septillionth", "ten-quadrillionth"),
            new ScaleUnit(-26, "hundred-septillionth", "hundred-quadrillionth"),
    };

    static public enum Scale {
        He,
        She,
        Them;

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
    static public Scale SCALE = Scale.He;

}
