package com.duke;

import com.duke.search.Policy;
import java.math.BigDecimal;
import java.util.*;

public class ExampleClient {
    public static void main(String[] args) throws Exception
    {
        InsuranceBroker insuranceBroker = new DukeOnlineInsuranceBroker(new OxQuotingSystem(), new OxPurchaseCompletionSystem(), new OxAdminChargeCalculator(), new StandardQuoteAgeLimitProvider(), new SystemTimeKeeper());
        String userAuthToken = ApplicationConfig.AuthenticationToken();// "tom@example.com";
//        ApplicationConfig
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
