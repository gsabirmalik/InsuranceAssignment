package com.duke;

import com.duke.search.Policy;
import com.duke.search.ProductionQuotingSystem;

import java.util.List;

/**
 * Created by ghulam on 09/08/2016.
 */
public class OxQuotingSystem implements QuotingSystem {

    static QuotingSystem quotingSystem = ProductionQuotingSystem.getInstance();

    @Override
    public List<Policy> searchFor(String manufacturer, String model, int yearManufactured) {
        return quotingSystem.searchFor(manufacturer, model, yearManufactured);

    }
}
