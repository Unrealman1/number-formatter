package ru.formatter.stack;

public class CompositeBigProcessor extends AbstractProcessor {

    private HundredProcessor hundredProcessor = new HundredProcessor();
    private AbstractProcessor lowProcessor;
    private int exponent;

    public CompositeBigProcessor(int exponent) {
        if (exponent <= 3) {
            lowProcessor = hundredProcessor;
        } else {
            lowProcessor = new CompositeBigProcessor(exponent - 3);
        }
        this.exponent = exponent;
    }

    public String getToken(int i) {
        return RuLocale.Scale.values()[i].getName(getPartDivider());
    }

    protected AbstractProcessor getHighProcessor() {
        return hundredProcessor;
    }

    protected AbstractProcessor getLowProcessor() {
        return lowProcessor;
    }

    public int getPartDivider() {
        return exponent;
    }

    @Override
    public String getName(String value) {
        StringBuilder buffer = new StringBuilder();

        String high, low;
        if (value.length() < getPartDivider()) {
            high = "";
            low = value;
        } else {
            int index = value.length() - getPartDivider();
            high = value.substring(0, index);
            low = value.substring(index);
        }

        String highName = getHighProcessor().getName(high);
        String lowName = getLowProcessor().getName(low);

        if (!highName.isEmpty()) {
            buffer.append(highName);
            buffer.append(SEPARATOR);
            // Определить ПАДЕЖ названия единиц измерения (рубль/рубля/рублей)
            // Если цифра в разряде единиц от 1 до 4
            int lastHighDigit = Integer.parseInt(high);
            if (lastHighDigit >= 1 && lastHighDigit <= 4) {
                // Если цифра в разряде единиц "1"
                if (lastHighDigit == 1) {
                    // Получиться "рубль"
                    buffer.append(getToken(0));
                }
                if (lastHighDigit >= 2) {
                    // Получиться "рубля"
                    buffer.append(getToken(1));
                }
            } else {
                buffer.append(getToken(2));
            }

            if (!lowName.isEmpty()) {
                buffer.append(SEPARATOR);
            }
        }

        if (!lowName.isEmpty()) {
            buffer.append(lowName);
        }

        return buffer.toString();
    }
}
