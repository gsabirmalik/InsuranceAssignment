package com.duke;

import com.duke.insurance.Purchase;
import com.duke.search.Policy;
import com.duke.search.Quote;
import java.math.BigDecimal;
import java.util.*;

public class DukeOnlineInsuranceBroker implements InsuranceBroker {

    private final QuotingSystem quotingSystem;
    private final PurchaseService purchaseCompletionService;
    private final AdminChargeCalculator adminChargeCalculator;
    private final QuoteAgeLimitProvider maxQuoteAgeLimitProvider;
    private final TimeKeeper timeKeeper;
    private Map<UUID, Quote> quotes = new HashMap<UUID, Quote>();

    public DukeOnlineInsuranceBroker(QuotingSystem pQuotingSystem, PurchaseService pPurchaseService, AdminChargeCalculator pAdminChargeCalculator, QuoteAgeLimitProvider pMaxQuoteAgeLimitProvider, TimeKeeper pTimeKeeper) {
        quotingSystem = pQuotingSystem;
        purchaseCompletionService = pPurchaseService;
        adminChargeCalculator = pAdminChargeCalculator;
        maxQuoteAgeLimitProvider = pMaxQuoteAgeLimitProvider;
        timeKeeper = pTimeKeeper;
    }

    @Override
    public List<Policy> searchForCarInsurance(String make, String model, int year) {

        List<Policy> searchResults = quotingSystem.searchFor(make, model, year);
        for (Policy policy : searchResults) {
            quotes.put(policy.id, new Quote(policy, timeKeeper.getCurrentTimeMillis()));
        }
        return searchResults;
    }

    @Override
    public void confirmPurchase(UUID id, String userAuthToken) {

        if (!quotes.containsKey(id)) {
            throw new NoSuchElementException("Offer ID is invalid");
        }

        Quote quote = quotes.get(id);
        long timeNow = timeKeeper.getCurrentTimeMillis();
        long maxAgeMillis = maxQuoteAgeLimitProvider.GetMaxQuoteAgeMillis();

        if (timeNow - quote.timestamp > maxAgeMillis) {
            throw new IllegalStateException("Quote expired, please search again.");
        }
        BigDecimal adminFee = adminChargeCalculator.GetAdminFee(quote, timeKeeper);

        BigDecimal premiumWithAdminFee = quote.policy.premium.add(adminFee);
        Purchase completePurchase = new Purchase(premiumWithAdminFee, quote, timeNow, userAuthToken);

        purchaseCompletionService.process(completePurchase);
    }
}