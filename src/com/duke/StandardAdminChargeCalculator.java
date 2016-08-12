package com.duke;

import java.math.BigDecimal;

public class StandardAdminChargeCalculator implements AdminChargeCalculator {

    public static final BigDecimal STANDARD_ADMIN_CHARGE = new BigDecimal(10);
    public BigDecimal GetAdminFee() {
        return StandardAdminChargeCalculator.STANDARD_ADMIN_CHARGE;
    }

}