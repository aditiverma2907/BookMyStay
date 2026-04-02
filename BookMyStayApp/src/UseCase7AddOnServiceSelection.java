
import java.util.*;

// Class representing an Add-On Service
class Service {
    private final String serviceName;
    private final double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Class representing a Reservation
class Reservation {
    private final String reservationId;
    private final String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Manager class for Add-On Services
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private final Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of add-on services
    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            System.out.println("Add-On Services:");
            for (Service s : services) {
                System.out.println("- " + s);
            }
        }
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Sample reservation
        Reservation reservation = new Reservation("R101", "Aditi");

        // Service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Available services
        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 300);
        Service airportPickup = new Service("Airport Pickup", 800);

        // Guest selects services
        manager.addService(reservation.getReservationId(), wifi);
        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), airportPickup);

        // Display services
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest Name: " + reservation.getGuestName());

        manager.displayServices(reservation.getReservationId());

        // Calculate total cost
        double totalCost = manager.calculateTotalServiceCost(reservation.getReservationId());
        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}
