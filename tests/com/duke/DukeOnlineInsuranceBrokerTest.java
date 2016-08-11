package com.duke;

import com.duke.search.Policy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ghulam on 10/08/2016.
 */
public class DukeOnlineInsuranceBrokerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void searchForCarInsurance() throws Exception {

        assertTrue(true);
    }

    @Test
    public void searchForCarInsuranceForAnUnknownVehicleShouldReturnEmptyListOfPolicies() {

        DukeOnlineInsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new StandardAdminChargeCalculator(), new StandardQuoteAgeLimitProvider());
        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("unknown", "A1", 2014);

        assertEquals("Search for invalid information should return empty list of policies", 0, searchResults.size());
    }


    @Test
    public void confirmPurchase() throws Exception {

    }

}