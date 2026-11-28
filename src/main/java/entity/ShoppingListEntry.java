package entity;

/** One item to buy on a shopping list. */
public class ShoppingListEntry {
    private final String itemName;
    private final Quantity quantity;
    private boolean purchased;

    public ShoppingListEntry(String itemName, Quantity quantity) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name must not be blank.");
        }
        if (quantity == null || quantity.getAmount() <= 0 || quantity.getUnit() == null) {
            throw new IllegalArgumentException("Quantity must have a positive amount and a unit.");
        }
        this.itemName = itemName.trim();
        this.quantity = quantity;
        this.purchased = false;
    }

    public String getItemName() {
        return itemName;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void markPurchased() {
        purchased = true;
    }
}
