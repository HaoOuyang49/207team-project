package entity;
import entity.Unit;

public final class Quantity {
    private final int amount;
    private final Unit unit;

    public Quantity(int amount, Unit unit) {
        this.amount = amount;
        this.unit = java.util.Objects.requireNonNull(unit);
    }

    public int getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }
}
