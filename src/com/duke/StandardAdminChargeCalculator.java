package com.duke;

import com.duke.search.Quote;

import java.math.BigDecimal;

@Deprecated
public class StandardAdminChargeCalculator implements AdminChargeCalculator {

    public static final BigDecimal STANDARD_ADMIN_CHARGE = new BigDecimal(10);
    public BigDecimal GetAdminFee(Quote quote, TimeKeeper timeKeeper) {
        return StandardAdminChargeCalculator.STANDARD_ADMIN_CHARGE;
    }

}