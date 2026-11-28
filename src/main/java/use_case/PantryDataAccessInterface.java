package use_case;

import entity.PantryItem;

import java.util.List;

public interface PantryDataAccessInterface {

    void save(PantryItem item);

    void deleteById(String itemId);

    boolean existsById(String itemId);

    PantryItem getById(String itemId);

    List<PantryItem> getItemsInPantry();
}
