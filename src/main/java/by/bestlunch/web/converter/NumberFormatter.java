package by.bestlunch.web.converter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;


public class NumberFormatter {
    public static class DemicalFormatter implements Formatter<Double> {

        @Override
        public Double parse(String text, Locale locale) throws ParseException {
            return parseDecimal(text);
        }

        @Override
        public String print(Double aDouble, Locale locale) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            decimalFormat.setMaximumFractionDigits(2);
            decimalFormat.setMinimumFractionDigits(2);
            return decimalFormat.format(aDouble);
        }
    }

    private static Double parseDecimal(String str) {
        return StringUtils.isEmpty(str) ? null : Double.parseDouble(str);
    }
}
