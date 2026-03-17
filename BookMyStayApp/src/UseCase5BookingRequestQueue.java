/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates queue-based booking request handling using FIFO.
 * No inventory update or room allocation is performed here.
 *
 * @author Aditi
 * @version 5.0
 */

import java.util.*;

// ----------- RESERVATION CLASS -----------
class ReservationV5 {
    private String guestName;
    private String roomType;

    public ReservationV5(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// ----------- BOOKING REQUEST QUEUE -----------
class BookingQueueV5 {

    private Queue<ReservationV5> requestQueue;

    public BookingQueueV5() {
        requestQueue = new LinkedList<>();
    }

    // Add request (FIFO)
    public void addRequest(ReservationV5 reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\n----- Booking Request Queue -----\n");

        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }

        for (ReservationV5 r : requestQueue) {
            r.display();
        }
    }

    // Peek first request (FIFO order check)
    public void peekNextRequest() {
        System.out.println("\nNext request to process:");

        ReservationV5 r = requestQueue.peek();
        if (r != null) {
            r.display();
        } else {
            System.out.println("Queue is empty.");
        }
    }
}

// ----------- MAIN APPLICATION -----------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Book My Stay - Hotel System   ");
        System.out.println("=================================");
        System.out.println("Version: 5.0\n");

        // Initialize queue
        BookingQueueV5 bookingQueue = new BookingQueueV5();

        // Simulating booking requests (FIFO order)
        bookingQueue.addRequest(new ReservationV5("Aditi", "Single Room"));
        bookingQueue.addRequest(new ReservationV5("Rahul", "Double Room"));
        bookingQueue.addRequest(new ReservationV5("Sneha", "Suite Room"));

        // View all requests
        bookingQueue.viewRequests();

        // Show next request (FIFO)
        bookingQueue.peekNextRequest();

        System.out.println("\nAll requests are stored in FIFO order.");
        System.out.println("No rooms allocated yet (read-only stage).");
    }
}
