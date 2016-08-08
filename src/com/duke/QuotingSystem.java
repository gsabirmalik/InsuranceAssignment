package com.duke;

import com.duke.search.Policy;

import java.util.List;

public interface QuotingSystem {
    List<Policy> searchFor(String manufacturer, String model, int yearManufactured);
}
