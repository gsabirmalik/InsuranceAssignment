package com.duke;

import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.insurance.Purchase;

public class OxPurchaseCompletionSystem implements PurchaseService {

    PurchaseService purchaseService = ProductionPurchaseCompletionSystem.getInstance();

    @Override
    public void process(Purchase purchase) {
        purchaseService.process(purchase);
    }
}
