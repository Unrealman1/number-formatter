package ru;

import pl.allegro.finance.tradukisto.internal.IntegerToStringConverter;
import pl.allegro.finance.tradukisto.internal.LongToStringConverter;
import pl.allegro.finance.tradukisto.internal.converters.BigDecimalToBankingMoneyConverter;

import java.math.BigDecimal;

public class MyBigDecimalToBankingMoneyConverter extends BigDecimalToBankingMoneyConverter {
    private LongToStringConverter converter;
    public MyBigDecimalToBankingMoneyConverter(IntegerToStringConverter converter, String currencySymbol) {
        super(converter, currencySymbol);
    }

    @Override
    public String asWords(BigDecimal value, String currencySymbol) {
        return this.converter.asWords(value.longValue());
    }

    public void setConverter(LongToStringConverter converter) {
        this.converter = converter;
    }
}
