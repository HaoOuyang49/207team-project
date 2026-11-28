package use_case.add_item;

import entity.Unit;

public class AddItemInputData {
    private final String name;
    private final String amount;
    private final Unit unit;
    private final String expiryDate;

    public AddItemInputData(String name, String amount, Unit unit, String expiryDate) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
