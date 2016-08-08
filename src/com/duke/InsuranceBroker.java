package com.duke;

import com.duke.search.Policy;

import java.util.List;
import java.util.UUID;

public interface InsuranceBroker {

    List<Policy> searchForCarInsurance(String make, String model, int year);

    void confirmPurchase(UUID id, String userAuthToken);
}
