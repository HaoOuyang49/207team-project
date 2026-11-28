package use_case.load_pantry;

import entity.Unit;

import java.time.LocalDate;
import java.util.List;

public class LoadPantryOutputData {
    private final List<ItemData> items;

    public LoadPantryOutputData(List<ItemData> items) {
        this.items = List.copyOf(items);
    }

    public List<ItemData> getItems() {
        return items;
    }

    public static final class ItemData {
        private final String itemId;
        private final String name;
        private final int amount;
        private final Unit unit;
        private final LocalDate expiryDate;

        public ItemData(String itemId, String name, int amount,
                        Unit unit, LocalDate expiryDate) {
            this.itemId = itemId;
            this.name = name;
            this.amount = amount;
            this.unit = unit;
            this.expiryDate = expiryDate;
        }

        public String getItemId() {
            return itemId;
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }

        public Unit getUnit() {
            return unit;
        }

        public LocalDate getExpiryDate() {
            return expiryDate;
        }
    }
}
