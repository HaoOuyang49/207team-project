package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class PantryItem {
    private final String itemId;
    private String name;
    private Quantity quantity;
    private final LocalDate purchasedDate;
    private LocalDate expiryDate;

    PantryItem(String itemId, String name, Quantity quantity,
               LocalDate purchasedDate, LocalDate expiryDate) {
        this.itemId = Objects.requireNonNull(itemId, "itemId");
        this.purchasedDate = Objects.requireNonNull(purchasedDate, "purchasedDate");
        editPantryItem(name, quantity, expiryDate);
    }

    // Note that expiryDate does not requireNonNull, if expiryDate isn't specified, it is null
    public void editPantryItem(String name, Quantity quantity, LocalDate expiryDate) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(quantity, "quantity");
        this.name = name;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public long daysUntilExpiry(LocalDate today) {
        if (expiryDate == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(today, expiryDate);
    }

    public boolean isExpired(LocalDate today) {
        return expiryDate != null && expiryDate.isBefore(today);
    }
}
