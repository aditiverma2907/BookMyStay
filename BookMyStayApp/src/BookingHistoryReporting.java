import java.util.*;

// Reservation class
class Reservation1 {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalAmount;

    public void Reservation(String reservationId, String guestName, String roomType, double totalAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalAmount = totalAmount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Amount: ₹" + totalAmount;
    }
}

// Booking History class (stores confirmed bookings)
class BookingHistory {
    private final List<Reservation> reservations = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary Report ---");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getTotalAmount();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class BookingHistoryReporting {

    public static void main(String[] args) {

        // Booking history instance
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R101", "Aditi", "Deluxe", 3000);
        Reservation r2 = new Reservation("R102", "Rahul", "Suite", 5000);
        Reservation r3 = new Reservation("R103", "Sneha", "Standard", 2000);

        // Add to booking history (in order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Report service
        BookingReportService reportService = new BookingReportService();

        // Admin views booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Admin generates summary report
        reportService.generateSummaryReport(history.getAllReservations());
    }
}
