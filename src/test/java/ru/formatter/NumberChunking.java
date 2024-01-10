package ru.formatter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class NumberChunking {
    private static final int SPLIT_FACTOR = 1000;
    private final BigDecimal divisor = BigDecimal.valueOf(SPLIT_FACTOR);

    public List<Integer> chunk(BigDecimal value) {
        LinkedList<Integer> result = new LinkedList<>();

        while (value.compareTo(BigDecimal.ZERO)>0) {
            result.addFirst(value.remainder(divisor).intValue());
            value = value.divide(divisor, new MathContext(20));
        }

        return result;
    }
}

