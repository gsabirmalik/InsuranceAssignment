package com.duke;

import com.duke.search.Quote;

import java.math.BigDecimal;

public interface AdminChargeCalculator {
    BigDecimal GetAdminFee(Quote quote, TimeKeeper timeKeeper);
}
