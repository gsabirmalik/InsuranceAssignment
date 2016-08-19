package com.duke;

public class FakeQuoteAgeLimitProvider implements QuoteAgeLimitProvider {
    @Override
    public long GetMaxQuoteAgeMillis() {
        return 15 *60 * 1000;
    }
}
