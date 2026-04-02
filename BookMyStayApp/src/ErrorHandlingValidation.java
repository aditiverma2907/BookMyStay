import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Inventory class (manages room availability)
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return rooms.containsKey(roomType);
    }

    public int getAvailableRooms(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = rooms.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // Update inventory safely
        rooms.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking Validator class
class BookingValidator {

    public static void validate(String reservationId, String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("Selected room is not available.");
        }
    }
}

// Main class
public class ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Test cases (valid + invalid)
        String[][] bookingInputs = {
                {"R101", "Aditi", "Deluxe"},     // valid
                {"", "Rahul", "Suite"},          // invalid ID
                {"R103", "", "Standard"},        // invalid name
                {"R104", "Sneha", "Premium"},    // invalid room type
                {"R105", "Arjun", "Suite"},      // valid
                {"R106", "Kiran", "Suite"}       // no availability
        };

        for (String[] input : bookingInputs) {
            String id = input[0];
            String name = input[1];
            String room = input[2];

            try {
                System.out.println("\nProcessing booking...");

                // Step 1: Validate (Fail-Fast)
                BookingValidator.validate(id, name, room, inventory);

                // Step 2: Book room
                inventory.bookRoom(room);

                // Step 3: Create reservation
                Reservation reservation = new Reservation(id, name, room);

                System.out.println("Booking Successful!");
                System.out.println(reservation);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // Display final inventory (system still stable)
        inventory.displayInventory();
    }
}