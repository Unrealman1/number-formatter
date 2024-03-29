package ru.formatter.stack;

import static ru.formatter.stack.RuLocale.SCALE;

public class DefaultProcessor extends AbstractProcessor {

    static private String MINUS = "минус";
    static private String UNION_AND = "целых";

    static private String ZERO_TOKEN = "ноль";

    private AbstractProcessor processor = new CompositeBigProcessor(63);

    @Override
    public String getName(String value) {
        boolean negative = false;
        if (value.startsWith("-")) {
            negative = true;
            value = value.substring(1);
        }

        int decimals = value.indexOf(".");
        String decimalValue = null;
        if (0 <= decimals) {
            decimalValue = value.substring(decimals + 1);
            value = value.substring(0, decimals);
        }

        String name = processor.getName(value);

        if (name.isEmpty()) {
            name = ZERO_TOKEN;
        } else if (negative) {
            name = MINUS.concat(SEPARATOR).concat(name);
        }

        if (!(null == decimalValue || decimalValue.isEmpty())) {
            int lastDigit = Integer.parseInt(decimalValue.substring(decimalValue.length() - 1));
            int tokenI = 0;
            if (lastDigit == 1) {
                tokenI = 1;
            }
            name = name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR)
                    .concat(processor.getName(decimalValue))
                    .concat(SEPARATOR).concat(RuLocale.Scale.values()[tokenI].getName(-decimalValue.length()));
        }

        return name;
    }

}
