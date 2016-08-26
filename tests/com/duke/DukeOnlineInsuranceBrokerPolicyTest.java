package com.duke;

import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.internal.ExpectationBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DukeOnlineInsuranceBrokerPolicyTest {

    @Test
    public void searchForCarInsuranceForAnUnknownMakeShouldReturnEmptyListOfPolicies() {

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new StandardQuoteAgeLimitProvider(), new SystemTimeKeeper());
        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("unknown", "A1", 2014);

        assertEquals("Search for invalid information should return empty list of policies", 0, searchResults.size());
    }

    @Test
    public void searchForCarInsuranceForAnUnknownModelShouldReturnEmptyListOfPolicies() {

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new StandardQuoteAgeLimitProvider(), new SystemTimeKeeper());
        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "Unknown", 2014);

        assertEquals("Search for invalid information should return empty list of policies", 0, searchResults.size());
    }

    @Test
    public void carInsuranceForOlderYearShouldBeMoreThanNewYear() {

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new StandardQuoteAgeLimitProvider(), new SystemTimeKeeper());
        List<Policy> audiA12012 = insuranceBroker.searchForCarInsurance("Audi", "A1", 2012);
        List<Policy> audiA12014 = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);

        assertTrue("Car Insurance For Older Year Should Be More Than New Year", audiA12014.get(0).premium.compareTo(audiA12012.get(0).premium) == 1);
    }

    @Test
    public void aQuotationShouldBeCreatedForVehicleInTheSystem() {
        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new StandardQuoteAgeLimitProvider(), new SystemTimeKeeper());
        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);

        assertTrue("Search for Audi A1 should return a few policies", searchResults.size() > 0);
    }
}