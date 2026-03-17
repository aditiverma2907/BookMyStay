/**
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates object-oriented design using abstraction,
 * inheritance, encapsulation, and polymorphism in a Hotel Booking System.
 *
 * Different room types are modeled as classes, and availability is
 * maintained using simple variables.
 *
 * @author Aditi
 * @version 2.1
 */

// ----------- ABSTRACT CLASS -----------
abstract class Room {

    // Encapsulated attributes
    private String roomType;
    private int beds;
    private double price;
    private int size;

    // Constructor
    public Room(String roomType, int beds, double price, int size) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
        this.size = size;
    }

    // Getter methods (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    // Abstract method (Abstraction)
    public abstract void displayDetails();
}

// ----------- SINGLE ROOM -----------
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000.0, 200);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sq.ft");
        System.out.println("Price: ₹" + getPrice());
    }
}

// ----------- DOUBLE ROOM -----------
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500.0, 350);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sq.ft");
        System.out.println("Price: ₹" + getPrice());
    }
}

// ----------- SUITE ROOM -----------
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 7000.0, 600);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sq.ft");
        System.out.println("Price: ₹" + getPrice());
    }
}

// ----------- MAIN APPLICATION -----------
public class UseCase2RoomInitialization {

    /**
     * Entry point of the application
     */
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Book My Stay - Hotel System   ");
        System.out.println("=================================");
        System.out.println("Version: 2.1\n");

        // ----------- STATIC AVAILABILITY -----------
        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        // ----------- OBJECT CREATION (POLYMORPHISM) -----------
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // ----------- DISPLAY DETAILS -----------
        System.out.println("----- Room Details -----\n");

        r1.displayDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application executed successfully!");
    }
}