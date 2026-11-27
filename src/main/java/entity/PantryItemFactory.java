package entity;

import java.time.LocalDate;
import java.util.UUID;

public class PantryItemFactory {

    //Note UUID collisions should not be a relevant consideration for the scope of this project
    public PantryItem create(String name, Quantity quantity,
                             LocalDate purchasedDate, LocalDate expiryDate) {
        String itemId = UUID.randomUUID().toString();
        return new PantryItem(itemId, name, quantity, purchasedDate, expiryDate);
    }

    public PantryItem create(String name, Quantity quantity, LocalDate purchasedDate) {
        return create(name, quantity, purchasedDate, null);
    }
}
