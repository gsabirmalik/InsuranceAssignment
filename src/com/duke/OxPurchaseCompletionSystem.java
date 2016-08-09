package com.duke;

import com.duke.insurance.ProductionPurchaseCompletionSystem;
import com.duke.insurance.Purchase;

/**
 * Created by ghulam on 09/08/2016.
 */
public class OxPurchaseCompletionSystem implements PurchaseService {

    PurchaseService purchaseService = ProductionPurchaseCompletionSystem.getInstance();

    @Override
    public void process(Purchase purchase) {
        purchaseService.process(purchase);
    }
}
