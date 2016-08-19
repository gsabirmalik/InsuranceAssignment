package com.duke;

import com.duke.search.Policy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class FakeQuotingSystem implements QuotingSystem {

    List<Policy> _fixedPolicies = null;

    public FakeQuotingSystem(BigDecimal fakePremium) {
        _fixedPolicies = Arrays.asList(new Policy("Test Description", fakePremium));
    }

    @Override
    public List<Policy> searchFor(String manufacturer, String model, int yearManufactured) {
        return _fixedPolicies;
    }

    public Policy GetFixedPlicy(){
        return _fixedPolicies.get(0);
    }
}