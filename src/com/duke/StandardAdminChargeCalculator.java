package com.duke;

import java.math.BigDecimal;


/**
 * Created by ghulam on 10/08/2016.
 */
public class StandardAdminChargeCalculator {

    public static final BigDecimal STANDARD_ADMIN_CHARGE = new BigDecimal(10);
    public BigDecimal GetAdminFee() {
        return STANDARD_ADMIN_CHARGE;
    }
}