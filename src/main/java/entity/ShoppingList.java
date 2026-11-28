package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A named shopping list with at most one entry per item name. */
public class ShoppingList {
    private final String name;
    private final List<ShoppingListEntry> entries;

    public ShoppingList(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("List name must not be blank.");
        }
        this.name = name.trim();
        this.entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<ShoppingListEntry> getEntries() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    public void addEntry(ShoppingListEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Entry must not be null.");
        }
        for (ShoppingListEntry existing : entries) {
            if (existing.getItemName().equalsIgnoreCase(entry.getItemName())) {
                throw new IllegalArgumentException("Item is already on the list.");
            }
        }
        entries.add(entry);
    }

    public void removeEntry(String itemName) {
        entries.remove(findEntry(itemName));
    }

    public void markPurchased(String itemName) {
        findEntry(itemName).markPurchased();
    }

    public void clearList() {
        entries.clear();
    }

    private ShoppingListEntry findEntry(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name must not be blank.");
        }
        for (ShoppingListEntry entry : entries) {
            if (entry.getItemName().equalsIgnoreCase(itemName.trim())) {
                return entry;
            }
        }
        throw new IllegalArgumentException("Item is not on the list.");
    }
}
