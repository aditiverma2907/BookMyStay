import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Thread-safe Room Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
    }

    // Critical Section (synchronized)
    public synchronized boolean bookRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(guestName + " is booking " + roomType + " room...");
            inventory.put(roomType, available - 1);

            System.out.println("Booking SUCCESS for " + guestName +
                    " | Remaining " + roomType + ": " + (available - 1));
            return true;
        } else {
            System.out.println("Booking FAILED for " + guestName +
                    " | No " + roomType + " rooms available");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {

            BookingRequest request;

            // synchronized access to queue
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break; // No more requests
            }

            // Process booking (thread-safe)
            inventory.bookRoom(request.roomType, request.guestName);

            try {
                Thread.sleep(100); // simulate processing delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple booking requests
        queue.addRequest(new BookingRequest("Aditi", "Standard"));
        queue.addRequest(new BookingRequest("Rahul", "Standard"));
        queue.addRequest(new BookingRequest("Sneha", "Standard")); // extra
        queue.addRequest(new BookingRequest("Arjun", "Deluxe"));
        queue.addRequest(new BookingRequest("Kiran", "Deluxe"));
        queue.addRequest(new BookingRequest("Meera", "Deluxe")); // extra

        // Create multiple threads (guests processing)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory state
        inventory.displayInventory();
    }
}
