package com.duke;

import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.ProductionQuotingSystem;
import com.duke.search.Quote;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private static final long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    public static final BigDecimal STANDARD_ADMIN_CHARGE = new BigDecimal(10);

    private final QuotingSystem quotingSystem;
    private final PurchaseService purchaseCompletionService;

    private Map<UUID, Quote> quotes = new HashMap<UUID, Quote>();

    public DukeOnlineInsuranceBroker() {

        quotingSystem = new OxQuotingSystem();
        purchaseCompletionService = new OxPurchaseCompletionSystem();
    }

    @Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults = quotingSystem.searchFor(make, model, year);
        for (Policy policy : searchResults) {
            quotes.put(policy.id, new Quote(policy, System.currentTimeMillis()));
        }
        return searchResults;
    }

    @Override
    public void confirmPurchase(UUID id, String userAuthToken) {

        if (!quotes.containsKey(id)) {
            throw new NoSuchElementException("Offer ID is invalid");
        }

        Quote quote = quotes.get(id);

        long timeNow = System.currentTimeMillis();

        if (timeNow - quote.timestamp > MAX_QUOTE_AGE_MILLIS) {
            throw new IllegalStateException("Quote expired, please search again.");
        }

        Purchase completePurchase = new Purchase(quote.policy.premium.add(STANDARD_ADMIN_CHARGE), quote, timeNow, userAuthToken);

        purchaseCompletionService.process(completePurchase);
    }
}