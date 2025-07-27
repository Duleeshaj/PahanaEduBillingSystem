package com.pahana.edu;

import com.pahana.edu.exception.ServiceException;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ItemTestApp {

    public static void main(String[] args) {
        ItemService service = new ItemService();

        try {
            // 1) ADD
            Item newItem = new Item();
            newItem.setName("Blue Pen");
            newItem.setDescription("0.7mm tip ballpoint pen");
            newItem.setPrice(50.00);
            newItem.setStock(100);

            boolean added = service.addItem(newItem);
            System.out.println("Add item: " + added);

            // 2) GET ALL (to find the last inserted item's id)
            List<Item> all = service.getAllItems();
            System.out.println("\nAll items after insert:");
            all.forEach(i -> System.out.println(row(i)));

            // Safely get the newest item's id (assuming highest id is newest)
            int latestId = all.stream()
                    .max(Comparator.comparingInt(Item::getItemId))
                    .map(Item::getItemId)
                    .orElseThrow(() -> new RuntimeException("No items found after insert!"));

            // 3) GET BY ID
            Optional<Item> fetched = service.getItemById(latestId);
            if (fetched.isPresent()) {
                System.out.println("\nFetched item by id " + latestId + ": " + row(fetched.get()));
            } else {
                System.out.println("\nItem not found by id: " + latestId);
            }

            // 4) UPDATE (price + stock)
            if (fetched.isPresent()) {
                Item toUpdate = fetched.get();
                toUpdate.setPrice(60.00);
                toUpdate.setStock(150);
                boolean updated = service.updateItem(toUpdate);
                System.out.println("\nUpdate item: " + updated);

                // Verify update
                Optional<Item> afterUpdate = service.getItemById(latestId);
                afterUpdate.ifPresent(item -> System.out.println("After update: " + row(item)));
            }

            // 5) DELETE
            boolean deleted = service.deleteItem(latestId);
            System.out.println("\nDelete item: " + deleted);

            // 6) Confirm deletion
            Optional<Item> afterDelete = service.getItemById(latestId);
            System.out.println("Exists after delete? " + afterDelete.isPresent());

            // Final list
            System.out.println("\nAll items at the end:");
            service.getAllItems().forEach(i -> System.out.println(row(i)));

        } catch (ServiceException e) {
            System.out.println("Service error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String row(Item i) {
        return String.format("ID=%d | Name=%s | Price=%.2f | Stock=%d | Desc=%s",
                i.getItemId(), i.getName(), i.getPrice(), i.getStock(), i.getDescription());
    }
}
