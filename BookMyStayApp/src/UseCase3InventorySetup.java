/**
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how to manage room availability using
 * a centralized HashMap instead of scattered variables.
 *
 * It ensures a single source of truth, better scalability,
 * and consistent state management across the system.
 *
 * @author Aditi
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// ----------- INVENTORY CLASS -----------
class RoomInventory {

    // HashMap to store room type -> availability
    private Map<String, Integer> inventory;

    /**
     * Constructor initializes inventory with room types
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability for a specific room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability (controlled update)
     */
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    /**
     * Add new room type (scalable design)
     */
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {
        System.out.println("----- Current Room Inventory -----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// ----------- MAIN APPLICATION -----------
public class UseCase3InventorySetup {

    /**
     * Entry point of the application
     */
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Book My Stay - Hotel System   ");
        System.out.println("=================================");
        System.out.println("Version: 3.1\n");

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nAvailable Single Rooms: " +
                inventory.getAvailability("Single Room"));

        // Update availability
        inventory.updateAvailability("Single Room", 8);

        System.out.println("\nAfter Updating Single Rooms:");
        inventory.displayInventory();

        // Add new room type (Scalability)
        inventory.addRoomType("Deluxe Room", 4);

        System.out.println("\nAfter Adding Deluxe Room:");
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully!");
    }
}