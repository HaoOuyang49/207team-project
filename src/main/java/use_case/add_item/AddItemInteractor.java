package use_case.add_item;

import entity.PantryItem;
import entity.PantryItemFactory;
import entity.Quantity;
import entity.Unit;
import use_case.PantryDataAccessInterface;

import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddItemInteractor implements AddItemInputBoundary {
    private final PantryDataAccessInterface pantryDataAccessObject;
    private final AddItemOutputBoundary addItemPresenter;
    private final PantryItemFactory pantryItemFactory;

    public AddItemInteractor(PantryDataAccessInterface pantryDataAccessObject,
                             AddItemOutputBoundary addItemPresenter,
                             PantryItemFactory pantryItemFactory) {
        this.pantryDataAccessObject = pantryDataAccessObject;
        this.addItemPresenter = addItemPresenter;
        this.pantryItemFactory = pantryItemFactory;
    }

    @Override
    public void execute(AddItemInputData inputData) {
        final String name = inputData.getName().trim();
        if (name.isEmpty()) {
            addItemPresenter.prepareFailView("Enter an item name.");
            return;
        }

        if (name.contains(",") || name.contains("\"")
                || name.contains("\n") || name.contains("\r")) {
            addItemPresenter.prepareFailView(
                    "Item names cannot contain commas, double quotes, or line breaks.");
            return;
        }

        final int amount;
        try {
            amount = Integer.parseInt(inputData.getAmount().trim());
        } catch (NumberFormatException exception) {
            addItemPresenter.prepareFailView("Enter a valid whole number for the quantity.");
            return;
        }

        if (amount <= 0) {
            addItemPresenter.prepareFailView("Quantity must be greater than zero.");
            return;
        }

        final Unit unit = inputData.getUnit();
        if (unit == null) {
            addItemPresenter.prepareFailView("Select a unit.");
            return;
        }

        final String expiryText = inputData.getExpiryDate().trim();
        final LocalDate expiryDate;
        try {
            expiryDate = expiryText.isEmpty() ? null
                    : LocalDate.parse(expiryText, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException exception) {
            addItemPresenter.prepareFailView(
                    "Enter a valid expiry date in YYYY-MM-DD format, or leave it blank.");
            return;
        }

        final Quantity quantity = new Quantity(amount, unit);
        final PantryItem item =
                pantryItemFactory.create(name, quantity, LocalDate.now(), expiryDate);

        try {
            pantryDataAccessObject.save(item);
        } catch (UncheckedIOException exception) {
            addItemPresenter.prepareFailView("Could not save the item to the pantry file.");
            return;
        }

        addItemPresenter.prepareSuccessView();
    }
}
