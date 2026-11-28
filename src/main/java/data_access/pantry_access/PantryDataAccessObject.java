package data_access.pantry_access;

import entity.PantryItem;
import entity.PantryItemFactory;
import entity.Quantity;
import entity.Unit;
import use_case.PantryDataAccessInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PantryDataAccessObject implements PantryDataAccessInterface {
    private static final String HEADER = "id,name,amount,unit,purchasedDate,expiryDate";

    private final Path csvFile;
    private final Map<String, PantryItem> items = new LinkedHashMap<>();

    public PantryDataAccessObject(String csvPath, PantryItemFactory itemFactory) {
        csvFile = Path.of(csvPath);

        try {
            if (Files.notExists(csvFile) || Files.size(csvFile) == 0) {
                writeCsv();
                return;
            }

            try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
                if (!HEADER.equals(reader.readLine())) {
                    throw new IllegalStateException("Unexpected pantry CSV header");
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    // Keep the empty final field when the item has no expiry date.
                    String[] columns = row.split(",", -1);
                    if (columns.length != 6) {
                        throw new IllegalStateException("Invalid pantry CSV row: " + row);
                    }

                    Quantity quantity = new Quantity(
                            Integer.parseInt(columns[2]), Unit.valueOf(columns[3]));
                    LocalDate purchasedDate = LocalDate.parse(columns[4]);
                    LocalDate expiryDate = columns[5].isEmpty()
                            ? null : LocalDate.parse(columns[5]);

                    PantryItem item = itemFactory.restore(
                            columns[0], columns[1], quantity, purchasedDate, expiryDate);
                    items.put(item.getId(), item);
                }
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read pantry CSV", exception);
        }
    }

    @Override
    public void save(PantryItem item) {
        String name = item.getName();
        if (name.contains(",") || name.contains("\"")
                || name.contains("\n") || name.contains("\r")) {
            throw new IllegalArgumentException(
                    "Item names cannot contain commas, double quotes, or line breaks");
        }

        items.put(item.getId(), item);
        writeCsv();
    }

    @Override
    public void deleteById(String itemId) {
        if (items.remove(itemId) == null) {
            throw new NoSuchElementException("No pantry item with ID " + itemId);
        }
        writeCsv();
    }

    @Override
    public boolean existsById(String itemId) {
        return items.containsKey(itemId);
    }

    @Override
    public PantryItem getById(String itemId) {
        PantryItem item = items.get(itemId);
        if (item == null) {
            throw new NoSuchElementException("No pantry item with ID " + itemId);
        }
        return item;
    }

    @Override
    public List<PantryItem> getItemsInPantry() {
        return new ArrayList<>(items.values());
    }

    private void writeCsv() {
        try (BufferedWriter writer = Files.newBufferedWriter(csvFile)) {
            writer.write(HEADER);
            writer.newLine();

            for (PantryItem item : items.values()) {
                Quantity quantity = item.getQuantity();
                LocalDate expiryDate = item.getExpiryDate();

                writer.write(String.join(",",
                        item.getId(),
                        item.getName(),
                        Integer.toString(quantity.getAmount()),
                        quantity.getUnit().name(),
                        item.getPurchasedDate().toString(),
                        expiryDate == null ? "" : expiryDate.toString()));
                writer.newLine();
            }
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not write pantry CSV", exception);
        }
    }
}
