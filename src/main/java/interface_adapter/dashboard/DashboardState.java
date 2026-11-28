package interface_adapter.dashboard;

import java.util.List;

public class DashboardState {
    private List<PantryRow> pantryRows = List.of();

    public List<PantryRow> getPantryRows() {
        return pantryRows;
    }

    public void setPantryRows(List<PantryRow> pantryRows) {
        this.pantryRows = List.copyOf(pantryRows);
    }

    public static final class PantryRow {
        private final String itemId;
        private final String name;
        private final String quantityText;
        private final String expiryText;

        public PantryRow(String itemId, String name, String quantityText,
                         String expiryText) {
            this.itemId = itemId;
            this.name = name;
            this.quantityText = quantityText;
            this.expiryText = expiryText;
        }

        public String getItemId() {
            return itemId;
        }

        public String getName() {
            return name;
        }

        public String getQuantityText() {
            return quantityText;
        }

        public String getExpiryText() {
            return expiryText;
        }
    }
}
