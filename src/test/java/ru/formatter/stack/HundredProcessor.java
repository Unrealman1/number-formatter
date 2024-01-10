package ru.formatter.stack;

public class HundredProcessor extends AbstractProcessor {
    static protected final String SEPARATOR = " ";

    private int EXPONENT = 2;

    private UnitProcessor unitProcessor = new UnitProcessor();
    private TensProcessor tensProcessor = new TensProcessor();
    private final String[] hundreds = {"сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот",
            "восемьсот", "девятьсот"};

    @Override
    public String getName(String value) {
        StringBuilder buffer = new StringBuilder();

        int number;
        if (value.isEmpty()) {
            number = 0;
        } else if (value.length() > 4) {
            number = Integer.valueOf(value.substring(value.length() - 4), 10);
        } else {
            number = Integer.valueOf(value, 10);
        }
        number %= 1000;  // keep at least three digits

        if (number >= 100) {
            int unit = number / 100;
            buffer.append(hundreds[unit - 1]);
         /*
            if (unit > 2) {
                buffer.append(unitProcessor.getName(unit));
                buffer.append(SEPARATOR);
            }
            buffer.append(NumberToWords.SCALE.getName(EXPONENT));*/
        }

        String tensName = tensProcessor.getName(number % 100);

        if (!tensName.isEmpty() && (number >= 100)) {
            buffer.append(SEPARATOR);
        }
        buffer.append(tensName);

        return buffer.toString();
    }
}
