/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Ensures:
 * - FIFO request processing
 * - Unique room allocation (no double booking)
 * - Immediate inventory update
 *
 * @author Aditi
 * @version 6.0
 */

import java.util.*;

// ----------- RESERVATION CLASS -----------
class ReservationV6 {
    private String guestName;
    private String roomType;

    public ReservationV6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// ----------- INVENTORY SERVICE -----------
class InventoryServiceV6 {

    private Map<String, Integer> inventory;

    public InventoryServiceV6() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// ----------- BOOKING QUEUE -----------
class BookingQueueV6 {

    private Queue<ReservationV6> queue = new LinkedList<>();

    public void addRequest(ReservationV6 r) {
        queue.offer(r);
    }

    public ReservationV6 getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ----------- BOOKING SERVICE (CORE LOGIC) -----------
class BookingServiceV6 {

    private InventoryServiceV6 inventory;

    // Track all allocated room IDs (NO DUPLICATES)
    private Set<String> allocatedRoomIds;

    // Map room type -> assigned room IDs
    private Map<String, Set<String>> roomAllocations;

    private int roomCounter = 1;

    public BookingServiceV6(InventoryServiceV6 inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase() + "-" + (roomCounter++);
    }

    // Process booking
    public void processBooking(ReservationV6 reservation) {

        String roomType = reservation.getRoomType();

        System.out.println("\nProcessing request for: " + reservation.getGuestName());

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("❌ No rooms available for " + roomType);
            return;
        }

        // Generate unique ID
        String roomId = generateRoomId(roomType);

        // Ensure uniqueness using Set
        if (allocatedRoomIds.contains(roomId)) {
            System.out.println("❌ Duplicate Room ID detected!");
            return;
        }

        // Add to global set
        allocatedRoomIds.add(roomId);

        // Add to room type mapping
        roomAllocations.putIfAbsent(roomType, new HashSet<>());
        roomAllocations.get(roomType).add(roomId);

        // Reduce inventory (IMPORTANT - atomic step)
        inventory.reduceRoom(roomType);

        // Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + roomId);
    }
}

// ----------- MAIN APPLICATION -----------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Book My Stay - Hotel System   ");
        System.out.println("=================================");
        System.out.println("Version: 6.0\n");

        // Initialize services
        InventoryServiceV6 inventory = new InventoryServiceV6();
        BookingQueueV6 queue = new BookingQueueV6();
        BookingServiceV6 bookingService = new BookingServiceV6(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new ReservationV6("Aditi", "Single Room"));
        queue.addRequest(new ReservationV6("Rahul", "Single Room"));
        queue.addRequest(new ReservationV6("Sneha", "Single Room")); // should fail

        // Process all requests
        while (!queue.isEmpty()) {
            ReservationV6 r = queue.getNextRequest();
            bookingService.processBooking(r);
        }

        System.out.println("\nAll bookings processed.");
    }
}
