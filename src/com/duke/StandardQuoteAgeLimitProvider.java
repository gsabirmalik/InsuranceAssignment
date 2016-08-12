package com.duke;

public class StandardQuoteAgeLimitProvider implements QuoteAgeLimitProvider {
    private final static long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    @Override
    public long GetMaxQuoteAgeMillis() {
        return MAX_QUOTE_AGE_MILLIS;
    }
}
