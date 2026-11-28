package use_case.load_pantry;

import entity.PantryItem;
import entity.Quantity;
import use_case.PantryDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class LoadPantryInteractor implements LoadPantryInputBoundary {
    private final PantryDataAccessInterface pantryDataAccessObject;
    private final LoadPantryOutputBoundary loadPantryPresenter;

    public LoadPantryInteractor(PantryDataAccessInterface pantryDataAccessInterface,
                                LoadPantryOutputBoundary loadPantryOutputBoundary) {
        this.pantryDataAccessObject = pantryDataAccessInterface;
        this.loadPantryPresenter = loadPantryOutputBoundary;
    }

    @Override
    public void execute() {
        final List<LoadPantryOutputData.ItemData> items = new ArrayList<>();

        for (PantryItem item : pantryDataAccessObject.getItemsInPantry()) {
            final Quantity quantity = item.getQuantity();
            items.add(new LoadPantryOutputData.ItemData(
                    item.getId(),
                    item.getName(),
                    quantity.getAmount(),
                    quantity.getUnit(),
                    item.getExpiryDate()));
        }

        final LoadPantryOutputData outputData = new LoadPantryOutputData(items);
        loadPantryPresenter.prepareSuccessView(outputData);
    }
}
