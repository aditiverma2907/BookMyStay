import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Active");
    }
}

// Inventory Management
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Stack<String>> availableRooms = new HashMap<>();

    public RoomInventory() {
        // Initialize inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);

        // Initialize room IDs (stack for LIFO)
        availableRooms.put("Standard", new Stack<>());
        availableRooms.put("Deluxe", new Stack<>());

        availableRooms.get("Standard").push("S1");
        availableRooms.get("Standard").push("S2");

        availableRooms.get("Deluxe").push("D1");
        availableRooms.get("Deluxe").push("D2");
    }

    public String allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            throw new Exception("No rooms available for " + roomType);
        }

        String roomId = availableRooms.get(roomType).pop();
        inventory.put(roomType, inventory.get(roomType) - 1);
        return roomId;
    }

    public void releaseRoom(String roomType, String roomId) {
        availableRooms.get(roomType).push(roomId); // LIFO rollback
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nInventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void displayAll() {
        System.out.println("\n--- Booking Records ---");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

// Cancellation Service
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String reservationId) throws CancellationException {

        // Step 1: Validate
        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            throw new CancellationException("Reservation does not exist.");
        }

        if (r.isCancelled()) {
            throw new CancellationException("Booking already cancelled.");
        }

        // Step 2: Rollback (controlled mutation)
        inventory.releaseRoom(r.getRoomType(), r.getRoomId());

        // Step 3: Update booking state
        r.cancel();

        System.out.println("Cancellation successful for Reservation ID: " + reservationId);
    }
}

// Main Class
public class BookingCancellationInventoryRollback {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        try {
            // Create bookings
            String room1 = inventory.allocateRoom("Standard");
            Reservation r1 = new Reservation("R101", "Aditi", "Standard", room1);

            String room2 = inventory.allocateRoom("Deluxe");
            Reservation r2 = new Reservation("R102", "Rahul", "Deluxe", room2);

            history.addReservation(r1);
            history.addReservation(r2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Display before cancellation
        history.displayAll();
        inventory.displayInventory();

        // Cancellation Service
        CancellationService service = new CancellationService(inventory, history);

        // Test cancellations
        try {
            service.cancelBooking("R101"); // valid
            service.cancelBooking("R101"); // duplicate
        } catch (CancellationException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            service.cancelBooking("R999"); // invalid
        } catch (CancellationException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display after cancellation
        history.displayAll();
        inventory.displayInventory();
    }
}
