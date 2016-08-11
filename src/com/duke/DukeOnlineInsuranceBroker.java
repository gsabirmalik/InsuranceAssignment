package com.duke;

import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.Quote;

import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private final QuotingSystem quotingSystem;
    private final PurchaseService purchaseCompletionService;
    private final StandardAdminChargeCalculator adminChargeCalculator;
    private final StandardQuoteAgeLimitProvider maxQuoteAgeLimitProvider;

    private Map<UUID, Quote> quotes = new HashMap<UUID, Quote>();

    public DukeOnlineInsuranceBroker() {

        quotingSystem = new OxQuotingSystem();
        purchaseCompletionService = new OxPurchaseCompletionSystem();
        adminChargeCalculator = new StandardAdminChargeCalculator();
        maxQuoteAgeLimitProvider = new StandardQuoteAgeLimitProvider();
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
        long maxAgeMillis = maxQuoteAgeLimitProvider.GetMaxQuoteAgeMillis();
        BigDecimal adminFee = adminChargeCalculator.GetAdminFee();

        if (timeNow - quote.timestamp > maxAgeMillis) {
            throw new IllegalStateException("Quote expired, please search again.");
        }

        BigDecimal premiumWithAdminFee = quote.policy.premium.add(adminFee);
        Purchase completePurchase = new Purchase(premiumWithAdminFee, quote, timeNow, userAuthToken);

        purchaseCompletionService.process(completePurchase);
    }
}