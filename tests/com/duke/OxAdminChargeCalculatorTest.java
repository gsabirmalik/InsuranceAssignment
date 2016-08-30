package com.duke;

import com.duke.search.Policy;
import com.duke.search.Quote;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OxAdminChargeCalculatorTest {

    @Test
    public void purchaseConfirmedWithin3MinutesShouldBeProcessedWithZeroAdminCharge() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        Quote quotation = new Quote(fakeQuotingSystem.searchFor("Audi", "A1", 2014).get(0), fakeTimeKeeper.getCurrentTimeMillis());

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(2));
        OxAdminChargeCalculator adminChargesCalculator = new OxAdminChargeCalculator();
        BigDecimal adminFee = adminChargesCalculator.GetAdminFee(quotation, fakeTimeKeeper);

        assertEquals("Purchase Confirmed within 3 minutes should add zero admin fee. ", new BigDecimal(0), adminFee);
    }

    @Test
    public void purchaseConfirmedWithin10MinutesAfter3MinutesShouldBeProcessedWith15PoundsAdminChargeWhen5PercentOfPremiumIsLessThan15() {

        FakeQuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        Quote quotation = new Quote(fakeQuotingSystem.searchFor("Audi", "A1", 2014).get(0), fakeTimeKeeper.getCurrentTimeMillis());

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(5));
        OxAdminChargeCalculator adminChargesCalculator = new OxAdminChargeCalculator();
        BigDecimal adminFee = adminChargesCalculator.GetAdminFee(quotation, fakeTimeKeeper);
        assertEquals("Purchase Confirmed After 3 Minutes but Within 10 Minutes Should Be Processed With £15 Admin Charge When 5% Of Premium is Less Than 15.", new BigDecimal(15), adminFee);
    }

    @Test
    public void purchaseConfirmedAfter3MinutesButWithin10MinutesShouldBeProcessedWith5PercentAdminChargeWhen5PercentOfPremiumIsMoreThan15() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(500));
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        Quote quotation = new Quote(fakeQuotingSystem.searchFor("Audi", "A1", 2014).get(0), fakeTimeKeeper.getCurrentTimeMillis());

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(5));
        OxAdminChargeCalculator adminChargesCalculator = new OxAdminChargeCalculator();
        BigDecimal adminFee = adminChargesCalculator.GetAdminFee(quotation, fakeTimeKeeper);

        assertEquals("Purchase Confirmed After 3 Minutes but Within 10 Minutes Should Be Processed With Admin Charge of 5% of Premium When 5% Of Premium is more Than 15.", new BigDecimal(25), adminFee);
    }

    @Test
    public void purchaseConfirmedAfter10MinutesButWithin15MinutesShouldBeProcessedWith40PoundsAdminChargeWhen10PercentOfPremiumIsLessThan40() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        Quote quotation = new Quote(fakeQuotingSystem.searchFor("Audi", "A1", 2014).get(0), fakeTimeKeeper.getCurrentTimeMillis());

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(11));
        OxAdminChargeCalculator adminChargesCalculator = new OxAdminChargeCalculator();
        BigDecimal adminFee = adminChargesCalculator.GetAdminFee(quotation, fakeTimeKeeper);

        assertEquals("Purchase Confirmed After 10 Minutes but Within 15 Minutes Should Be Processed With £40 Admin Charge When 10% Of Premium is Less Than 40.", new BigDecimal(40), adminFee);
    }

    @Test
    public void purchaseConfirmedAfter10MinutesButWithin15MinutesShouldBeProcessedWith10PercentAdminChargeWhen10PercentOfPremiumIsMoreThan40() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(500));
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        Quote quotation = new Quote(fakeQuotingSystem.searchFor("Audi", "A1", 2014).get(0), fakeTimeKeeper.getCurrentTimeMillis());

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(11));
        OxAdminChargeCalculator adminChargesCalculator = new OxAdminChargeCalculator();
        BigDecimal adminFee = adminChargesCalculator.GetAdminFee(quotation, fakeTimeKeeper);

        assertEquals("Purchase Confirmed After 10 Minutes but Within 15 Minutes Should Be Processed With Admin Charge of 10% of Premium When 10% Of Premium is more Than 40.", new BigDecimal(50), adminFee);
    }

    private long getMilliSecsFromMinutes(long minutes) {
        return minutes * 60 * 1000;
    }
}