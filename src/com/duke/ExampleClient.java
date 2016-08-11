package com.duke;

import com.duke.search.Policy;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ghulam on 08/08/2016.
 */
public class ExampleClient {
    public static void main(String[] args) throws Exception
    {
        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new StandardAdminChargeCalculator(), new StandardQuoteAgeLimitProvider());
        String userAuthToken = "tom@example.com";
        List<Policy> searchResults = insuranceBroker.searchForCarInsurance("Audi", "A1", 2014);
        // try also Toyota Prius, Tesla Model S, etc

        if (searchResults.isEmpty()) {
            System.out.println("No search results found");
        } else
        {
            Policy policy = searchResults.get(0);
            // some time may pass...
            Thread.sleep(5 * 1000);
            if (priceAcceptable(policy.premium))
            {
                insuranceBroker.confirmPurchase(policy.id, userAuthToken);
            }
        }
    }
    private static boolean priceAcceptable(BigDecimal price)
    {
        return true;
    }
}
