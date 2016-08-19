package com.duke;

import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.internal.ExpectationBuilder;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DukeOnlineInsuranceBrokerPurchaseTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    PurchaseService _fakePurchaseService = context.mock(PurchaseService.class);

    @Test
    public void purchaseCanBeConfirmedBefore15Minutes() {

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new FakeQuotingSystem(new BigDecimal(100)), _fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), new FakeTimeKeeper());
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        ExpectationBuilder expectations = new Expectations() {{
            exactly(1).of(_fakePurchaseService).process(with(any(Purchase.class)));
        }};
        context.checking(expectations);

        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");
    }

    @Test
    public void purchaseShouldNotConfirmAfter15MinutesOfQuotationProduction() {
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new FakeQuotingSystem(new BigDecimal(100)), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(16));
        try {
            insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");
        } catch (IllegalStateException ex) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void purchaseConfirmedWithin3MinutesShouldBeProcessedWithZeroAdminCharge() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakePurchaseService fakePurchaseService = new FakePurchaseService();
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeQuotingSystem, fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(2));
        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");

        assertEquals("Purchase Confirmed within 3 minutes should add zero admin fee. ", insurancePolicy.premium, fakePurchaseService.getProcessedPurchase().totalPrice);
    }

    @Test
    public void purchaseConfirmedWithin10MinutesAfter3MinutesShouldBeProcessedWith15PoundsAdminChargeWhen5PercentOfPremiumIsLessThan15() {

        FakeQuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakePurchaseService fakePurchaseService = new FakePurchaseService();
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();
        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeQuotingSystem, fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(5));
        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");

        assertEquals("Purchase Confirmed After 3 Minutes but Within 10 Minutes Should Be Processed With £15 Admin Charge When 5% Of Premium is Less Than 15.", new BigDecimal(115), fakePurchaseService.getProcessedPurchase().totalPrice);
    }

    @Test
    public void purchaseConfirmedAfter3MinutesButWithin10MinutesShouldBeProcessedWith5PercentAdminChargeWhen5PercentOfPremiumIsMoreThan15() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(500));
        FakePurchaseService fakePurchaseService = new FakePurchaseService();
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeQuotingSystem, fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(5));
        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");

        assertEquals("Purchase Confirmed After 3 Minutes but Within 10 Minutes Should Be Processed With Admin Charge of 5% of Premium When 5% Of Premium is more Than 15.", new BigDecimal(525), fakePurchaseService.getProcessedPurchase().totalPrice);
    }

    @Test
    public void purchaseConfirmedAfter10MinutesButWithin15MinutesShouldBeProcessedWith40PoundsAdminChargeWhen10PercentOfPremiumIsLessThan40() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(100));
        FakePurchaseService fakePurchaseService = new FakePurchaseService();
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeQuotingSystem, fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(11));
        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");

        assertEquals("Purchase Confirmed After 10 Minutes but Within 15 Minutes Should Be Processed With £40 Admin Charge When 10% Of Premium is Less Than 40.", new BigDecimal(140), fakePurchaseService.getProcessedPurchase().totalPrice);
    }

    @Test
    public void purchaseConfirmedAfter10MinutesButWithin15MinutesShouldBeProcessedWith10PercentAdminChargeWhen10PercentOfPremiumIsMoreThan40() {

        QuotingSystem fakeQuotingSystem = new FakeQuotingSystem(new BigDecimal(500));
        FakePurchaseService fakePurchaseService = new FakePurchaseService();
        FakeTimeKeeper fakeTimeKeeper = new FakeTimeKeeper();

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(fakeQuotingSystem, fakePurchaseService, new OxAdminChargeCalculator(), new FakeQuoteAgeLimitProvider(), fakeTimeKeeper);
        Policy insurancePolicy = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014).get(0);

        fakeTimeKeeper.addFakeElapsedMilliSecs(getMilliSecsFromMinutes(11));
        insuranceBroker.confirmPurchase(insurancePolicy.id, "sabir@hotmail.com");

        assertEquals("Purchase Confirmed After 10 Minutes but Within 15 Minutes Should Be Processed With Admin Charge of 10% of Premium When 10% Of Premium is more Than 40.", new BigDecimal(550), fakePurchaseService.getProcessedPurchase().totalPrice);
    }

    private long getMilliSecsFromMinutes(long minutes) {
        return minutes * 60 * 1000;
    }
}