package com.duke;

/**
 * Created by ghulam on 10/08/2016.
 */
public class StandardQuoteAgeLimitProvider {
    private static final long MAX_QUOTE_AGE_MILLIS = 15 * 60 * 1000;

    public long GetMaxQuoteAgeMillis() {
        return MAX_QUOTE_AGE_MILLIS;
    }
}
