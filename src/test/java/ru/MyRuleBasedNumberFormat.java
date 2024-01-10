package ru;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.RuleBasedNumberFormat;

import java.text.FieldPosition;
import java.util.Locale;

public class MyRuleBasedNumberFormat extends RuleBasedNumberFormat {
    public MyRuleBasedNumberFormat(Locale locale, int format) {
        super(locale, format);
    }

    @Override
    public StringBuffer format(BigDecimal number, StringBuffer toAppendTo, FieldPosition pos) {
        return number.scale() == 0 ? this.format(number.longValue(), toAppendTo, pos) :
                this.format(number.doubleValue(), toAppendTo, pos);
    }
}
