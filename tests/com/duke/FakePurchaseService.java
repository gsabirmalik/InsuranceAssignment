package com.duke;

import com.duke.insurance.Purchase;

public class FakePurchaseService implements PurchaseService {

    Purchase _processedPurchase = null;

    public Purchase getProcessedPurchase(){
        return _processedPurchase;
    }

    @Override
    public void process(Purchase purchase) {
        _processedPurchase = purchase;
    }
}