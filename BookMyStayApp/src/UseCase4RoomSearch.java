/**
 * Use Case 4: Room Search & Availability Check
 *
 * Renamed classes to avoid duplication conflicts with previous use cases.
 *
 * @author Aditi
 * @version 4.1
 */

import java.util.*;

// ----------- ABSTRACT ROOM CLASS (RENAMED) -----------
abstract class RoomV4 {
    private String type;
    private int beds;
    private double price;

    public RoomV4(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public double getPrice() { return price; }

    public abstract void displayDetails();
}

// ----------- ROOM TYPES (RENAMED) -----------
class SingleRoomV4 extends RoomV4 {
    public SingleRoomV4() {
        super("Single Room", 1, 2000);
    }

    public void displayDetails() {
        System.out.println("Room: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

class DoubleRoomV4 extends RoomV4 {
    public DoubleRoomV4() {
        super("Double Room", 2, 3500);
    }

    public void displayDetails() {
        System.out.println("Room: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

class SuiteRoomV4 extends RoomV4 {
    public SuiteRoomV4() {
        super("Suite Room", 3, 7000);
    }

    public void displayDetails() {
        System.out.println("Room: " + getType() +
                ", Beds: " + getBeds() +
                ", Price: ₹" + getPrice());
    }
}

// ----------- INVENTORY CLASS -----------
class RoomInventoryV4 {

    private Map<String, Integer> inventory;

    public RoomInventoryV4() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

// ----------- SEARCH SERVICE -----------
class SearchServiceV4 {

    private RoomInventoryV4 inventory;
    private Map<String, RoomV4> roomCatalog;

    public SearchServiceV4(RoomInventoryV4 inventory) {
        this.inventory = inventory;

        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoomV4());
        roomCatalog.put("Double Room", new DoubleRoomV4());
        roomCatalog.put("Suite Room", new SuiteRoomV4());
    }

    public void searchAvailableRooms() {

        System.out.println("----- Available Rooms -----\n");

        for (String roomType : inventory.getRoomTypes()) {

            int available = inventory.getAvailability(roomType);

            if (available > 0) {
                RoomV4 room = roomCatalog.get(roomType);

                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// ----------- MAIN CLASS -----------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Book My Stay - Hotel System   ");
        System.out.println("=================================");
        System.out.println("Version: 4.1\n");

        RoomInventoryV4 inventory = new RoomInventoryV4();
        SearchServiceV4 searchService = new SearchServiceV4(inventory);

        searchService.searchAvailableRooms();

        System.out.println("Search completed successfully!");
    }
}