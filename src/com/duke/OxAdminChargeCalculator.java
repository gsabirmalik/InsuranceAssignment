package com.duke;

import com.duke.search.Quote;

import java.math.BigDecimal;

public class OxAdminChargeCalculator implements AdminChargeCalculator {

    private static final long THREE_MINUTES = 3 * 60 * 1000;
    private static final long TEN_MINUTES = 10 * 60 * 1000;
    private static final long FIFTEEN_MINUTES = 15 * 60 * 1000;

    @Override
    public BigDecimal GetAdminFee(Quote quote, TimeKeeper timeKeeper) {

        long quotationCreationMillis = quote.timestamp;
        long currentMillis = timeKeeper.getCurrentTimeMillis();

        long elapsedMillis = currentMillis - quotationCreationMillis;

        if (elapsedMillis < THREE_MINUTES)
            return new BigDecimal(0);

        double fixedValue = 0;
        double percentValue = 0;

        if (elapsedMillis < TEN_MINUTES) {
            fixedValue = 15;
            percentValue = GetXPercentOfValue(quote.policy.premium, 5);
        } else if (elapsedMillis < FIFTEEN_MINUTES) {
            fixedValue = 40;
            percentValue = GetXPercentOfValue(quote.policy.premium, 10);
        } else
            throw new IllegalStateException("Quote expired, please search again.");

        return new BigDecimal(fixedValue > percentValue ? fixedValue : percentValue);
    }

    private double GetXPercentOfValue(BigDecimal value, int percent) {

        return (value.doubleValue() / 100) * percent;
    }
}
