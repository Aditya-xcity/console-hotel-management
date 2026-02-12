import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Food item ordered by a customer
 */
class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int itemNo;
    private int quantity;
    private float price;
    
    public Food(int itemNo, int quantity) {
        this.itemNo = itemNo;
        this.quantity = quantity;
        
        // Calculate price based on item number
        switch (itemNo) {
            case 1: this.price = quantity * 50; break; // Sandwich
            case 2: this.price = quantity * 60; break; // Pasta
            case 3: this.price = quantity * 70; break; // Noodles
            case 4: this.price = quantity * 30; break; // Coke
            default: this.price = 0; break;
        }
    }
    
    public int getItemNo() { return itemNo; }
    public int getQuantity() { return quantity; }
    public float getPrice() { return price; }
}

/**
 * Base class for single room occupancy
 */
class SingleRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String guestName;
    protected String contactNumber;
    protected String gender;
    protected ArrayList<Food> foodOrders = new ArrayList<>();
    
    public SingleRoom() {
        this.guestName = "";
        this.contactNumber = "";
        this.gender = "";
    }
    
    public SingleRoom(String guestName, String contactNumber, String gender) {
        this.guestName = guestName;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }
    
    public String getGuestName() { return guestName; }
    public String getContactNumber() { return contactNumber; }
    public String getGender() { return gender; }
    public ArrayList<Food> getFoodOrders() { return foodOrders; }
}

/**
 * Double room occupancy extends SingleRoom with second guest details
 */
class DoubleRoom extends SingleRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String secondGuestName;
    private String secondGuestContact;
    private String secondGuestGender;
    
    public DoubleRoom() {
        super();
        this.secondGuestName = "";
        this.secondGuestContact = "";
        this.secondGuestGender = "";
    }
    
    public DoubleRoom(String guestName, String contactNumber, String gender,
                     String secondGuestName, String secondGuestContact, String secondGuestGender) {
        super(guestName, contactNumber, gender);
        this.secondGuestName = secondGuestName;
        this.secondGuestContact = secondGuestContact;
        this.secondGuestGender = secondGuestGender;
    }
    
    public String getSecondGuestName() { return secondGuestName; }
    public String getSecondGuestContact() { return secondGuestContact; }
    public String getSecondGuestGender() { return secondGuestGender; }
}

/**
 * Custom exception for unavailable rooms
 */
class RoomNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;
    
    @Override
    public String toString() {
        return "Room not available!";
    }
}

/**
 * Data holder for all hotel rooms
 */
class HotelData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final int LUXURY_DOUBLE_ROOMS = 10;
    public static final int DELUXE_DOUBLE_ROOMS = 20;
    public static final int LUXURY_SINGLE_ROOMS = 10;
    public static final int DELUXE_SINGLE_ROOMS = 20;
    
    // Room arrays
    private DoubleRoom[] luxuryDoubleRooms = new DoubleRoom[LUXURY_DOUBLE_ROOMS];
    private DoubleRoom[] deluxeDoubleRooms = new DoubleRoom[DELUXE_DOUBLE_ROOMS];
    private SingleRoom[] luxurySingleRooms = new SingleRoom[LUXURY_SINGLE_ROOMS];
    private SingleRoom[] deluxeSingleRooms = new SingleRoom[DELUXE_SINGLE_ROOMS];
    
    // Getters
    public DoubleRoom[] getLuxuryDoubleRooms() { return luxuryDoubleRooms; }
    public DoubleRoom[] getDeluxeDoubleRooms() { return deluxeDoubleRooms; }
    public SingleRoom[] getLuxurySingleRooms() { return luxurySingleRooms; }
    public SingleRoom[] getDeluxeSingleRooms() { return deluxeSingleRooms; }
}

/**
 * Main hotel management logic
 */
class Hotel {
    static HotelData hotelData = new HotelData();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] FOOD_ITEMS = {"Sandwich", "Pasta", "Noodles", "Coke"};
    
    /**
     * Collect customer details for room booking
     */
    private static void collectCustomerDetails(int roomType, int roomIndex) {
        System.out.print("\nEnter customer name: ");
        String name = scanner.next().trim();
        
        System.out.print("Enter contact number: ");
        String contact = scanner.next().trim();
        
        System.out.print("Enter gender: ");
        String gender = scanner.next().trim();
        
        // Validate inputs
        if (name.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
            System.out.println("Invalid input. All fields are required.");
            return;
        }
        
        // Double room requires second guest details
        if (roomType < 3) {
            System.out.print("Enter second customer name: ");
            String name2 = scanner.next().trim();
            System.out.print("Enter contact number: ");
            String contact2 = scanner.next().trim();
            System.out.print("Enter gender: ");
            String gender2 = scanner.next().trim();
            
            if (name2.isEmpty() || contact2.isEmpty() || gender2.isEmpty()) {
                System.out.println("Invalid input. All fields are required.");
                return;
            }
            
            switch (roomType) {
                case 1:
                    hotelData.getLuxuryDoubleRooms()[roomIndex] = 
                        new DoubleRoom(name, contact, gender, name2, contact2, gender2);
                    break;
                case 2:
                    hotelData.getDeluxeDoubleRooms()[roomIndex] = 
                        new DoubleRoom(name, contact, gender, name2, contact2, gender2);
                    break;
            }
        } else {
            switch (roomType) {
                case 3:
                    hotelData.getLuxurySingleRooms()[roomIndex] = 
                        new SingleRoom(name, contact, gender);
                    break;
                case 4:
                    hotelData.getDeluxeSingleRooms()[roomIndex] = 
                        new SingleRoom(name, contact, gender);
                    break;
            }
        }
        System.out.println("Room booked successfully!");
    }
    
    /**
     * Book a room
     */
    static void bookRoom(int roomType) {
        try {
            int roomNumber;
            int roomIndex;
            
            switch (roomType) {
                case 1: // Luxury Double
                    displayAvailableRooms(hotelData.getLuxuryDoubleRooms(), 1);
                    roomNumber = getValidRoomNumber(1, hotelData.getLuxuryDoubleRooms().length);
                    roomIndex = roomNumber - 1;
                    
                    if (hotelData.getLuxuryDoubleRooms()[roomIndex] != null) {
                        throw new RoomNotAvailableException();
                    }
                    collectCustomerDetails(roomType, roomIndex);
                    break;
                    
                case 2: // Deluxe Double
                    displayAvailableRooms(hotelData.getDeluxeDoubleRooms(), 11);
                    roomNumber = getValidRoomNumber(11, 30);
                    roomIndex = roomNumber - 11;
                    
                    if (hotelData.getDeluxeDoubleRooms()[roomIndex] != null) {
                        throw new RoomNotAvailableException();
                    }
                    collectCustomerDetails(roomType, roomIndex);
                    break;
                    
                case 3: // Luxury Single
                    displayAvailableRooms(hotelData.getLuxurySingleRooms(), 31);
                    roomNumber = getValidRoomNumber(31, 40);
                    roomIndex = roomNumber - 31;
                    
                    if (hotelData.getLuxurySingleRooms()[roomIndex] != null) {
                        throw new RoomNotAvailableException();
                    }
                    collectCustomerDetails(roomType, roomIndex);
                    break;
                    
                case 4: // Deluxe Single
                    displayAvailableRooms(hotelData.getDeluxeSingleRooms(), 41);
                    roomNumber = getValidRoomNumber(41, 60);
                    roomIndex = roomNumber - 41;
                    
                    if (hotelData.getDeluxeSingleRooms()[roomIndex] != null) {
                        throw new RoomNotAvailableException();
                    }
                    collectCustomerDetails(roomType, roomIndex);
                    break;
                    
                default:
                    System.out.println("Invalid room type option");
            }
        } catch (RoomNotAvailableException e) {
            System.out.println(e);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
        }
    }
    
    /**
     * Display available room numbers
     */
    private static void displayAvailableRooms(Object[] rooms, int startNumber) {
        System.out.print("Available rooms: ");
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] == null) {
                System.out.print((i + startNumber) + " ");
            }
        }
        System.out.println();
    }
    
    /**
     * Get and validate room number input
     */
    private static int getValidRoomNumber(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter room number (" + min + "-" + max + "): ");
                int roomNumber = scanner.nextInt();
                if (roomNumber >= min && roomNumber <= max) {
                    return roomNumber;
                }
                System.out.println("Room number must be between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }
    
    /**
     * Display room features
     */
    static void displayFeatures(int roomType) {
        switch (roomType) {
            case 1:
                System.out.println("Luxury Double Room:\n" +
                                 "- 1 Double Bed\n- AC Available\n- Free Breakfast\n- Charge per day: ₹4000");
                break;
            case 2:
                System.out.println("Deluxe Double Room:\n" +
                                 "- 1 Double Bed\n- Non-AC\n- Free Breakfast\n- Charge per day: ₹3000");
                break;
            case 3:
                System.out.println("Luxury Single Room:\n" +
                                 "- 1 Single Bed\n- AC Available\n- Free Breakfast\n- Charge per day: ₹2200");
                break;
            case 4:
                System.out.println("Deluxe Single Room:\n" +
                                 "- 1 Single Bed\n- Non-AC\n- Free Breakfast\n- Charge per day: ₹1200");
                break;
            default:
                System.out.println("Invalid room type option");
        }
    }
    
    /**
     * Check room availability count
     */
    static void checkAvailability(int roomType) {
        int count = 0;
        Object[] rooms = null;
        
        switch (roomType) {
            case 1:
                rooms = hotelData.getLuxuryDoubleRooms();
                break;
            case 2:
                rooms = hotelData.getDeluxeDoubleRooms();
                break;
            case 3:
                rooms = hotelData.getLuxurySingleRooms();
                break;
            case 4:
                rooms = hotelData.getDeluxeSingleRooms();
                break;
            default:
                System.out.println("Invalid room type option");
                return;
        }
        
        for (Object room : rooms) {
            if (room == null) {
                count++;
            }
        }
        System.out.println("Number of rooms available: " + count);
    }
    
    /**
     * Generate and display bill
     */
    static void generateBill(int roomIndex, int roomType) {
        double totalAmount = 0;
        double roomCharge = 0;
        Object room = null;
        ArrayList<Food> foodOrders = null;
        
        switch (roomType) {
            case 1:
                roomCharge = 4000;
                room = hotelData.getLuxuryDoubleRooms()[roomIndex];
                if (room != null) {
                    foodOrders = ((DoubleRoom) room).getFoodOrders();
                }
                break;
            case 2:
                roomCharge = 3000;
                room = hotelData.getDeluxeDoubleRooms()[roomIndex];
                if (room != null) {
                    foodOrders = ((DoubleRoom) room).getFoodOrders();
                }
                break;
            case 3:
                roomCharge = 2200;
                room = hotelData.getLuxurySingleRooms()[roomIndex];
                if (room != null) {
                    foodOrders = ((SingleRoom) room).getFoodOrders();
                }
                break;
            case 4:
                roomCharge = 1200;
                room = hotelData.getDeluxeSingleRooms()[roomIndex];
                if (room != null) {
                    foodOrders = ((SingleRoom) room).getFoodOrders();
                }
                break;
        }
        
        if (room == null) {
            System.out.println("Room is not occupied");
            return;
        }
        
        totalAmount += roomCharge;
        
        System.out.println("\n===============");
        System.out.println("      BILL");
        System.out.println("===============");
        System.out.printf("Room Charge: ₹%.2f%n", roomCharge);
        
        if (foodOrders != null && !foodOrders.isEmpty()) {
            System.out.println("\nFood Charges:");
            System.out.println("-------------------------");
            System.out.printf("%-15s %-10s %-10s%n", "Item", "Quantity", "Price");
            System.out.println("-------------------------");
            
            for (Food food : foodOrders) {
                totalAmount += food.getPrice();
                System.out.printf("%-15s %-10d ₹%-10.2f%n", 
                    FOOD_ITEMS[food.getItemNo() - 1], 
                    food.getQuantity(), 
                    food.getPrice());
            }
        }
        
        System.out.println("-------------------------");
        System.out.printf("Total Amount: ₹%.2f%n", totalAmount);
    }
    
    /**
     * Checkout and deallocate room
     */
    static void checkoutRoom(int roomIndex, int roomType) {
        try {
            String guestName = null;
            Object[] rooms = null;
            
            switch (roomType) {
                case 1:
                    rooms = hotelData.getLuxuryDoubleRooms();
                    if (rooms[roomIndex] != null) {
                        guestName = ((DoubleRoom) rooms[roomIndex]).getGuestName();
                    }
                    break;
                case 2:
                    rooms = hotelData.getDeluxeDoubleRooms();
                    if (rooms[roomIndex] != null) {
                        guestName = ((DoubleRoom) rooms[roomIndex]).getGuestName();
                    }
                    break;
                case 3:
                    rooms = hotelData.getLuxurySingleRooms();
                    if (rooms[roomIndex] != null) {
                        guestName = ((SingleRoom) rooms[roomIndex]).getGuestName();
                    }
                    break;
                case 4:
                    rooms = hotelData.getDeluxeSingleRooms();
                    if (rooms[roomIndex] != null) {
                        guestName = ((SingleRoom) rooms[roomIndex]).getGuestName();
                    }
                    break;
            }
            
            if (guestName == null) {
                System.out.println("Room is already empty");
                return;
            }
            
            System.out.println("Room occupied by: " + guestName);
            System.out.print("Do you want to checkout? (y/n): ");
            char wish = Character.toLowerCase(scanner.next().charAt(0));
            
            if (wish == 'y') {
                generateBill(roomIndex, roomType);
                rooms[roomIndex] = null;
                System.out.println("Checkout successful! Room deallocated.");
            }
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid room number");
        } catch (NullPointerException e) {
            System.out.println("Room not found");
        }
    }
    
    /**
     * Order food for a room
     */
    static void orderFood(int roomIndex, int roomType) {
        try {
            Object room = null;
            
            switch (roomType) {
                case 1:
                    room = hotelData.getLuxuryDoubleRooms()[roomIndex];
                    break;
                case 2:
                    room = hotelData.getDeluxeDoubleRooms()[roomIndex];
                    break;
                case 3:
                    room = hotelData.getLuxurySingleRooms()[roomIndex];
                    break;
                case 4:
                    room = hotelData.getDeluxeSingleRooms()[roomIndex];
                    break;
            }
            
            if (room == null) {
                System.out.println("Room is not booked. Please book the room first.");
                return;
            }
            
            System.out.println("\n===========");
            System.out.println("    MENU");
            System.out.println("===========");
            System.out.println("1. Sandwich  - ₹50");
            System.out.println("2. Pasta     - ₹60");
            System.out.println("3. Noodles   - ₹70");
            System.out.println("4. Coke      - ₹30");
            System.out.println("===========");
            
            do {
                System.out.print("Enter item number: ");
                int itemNo = scanner.nextInt();
                
                if (itemNo < 1 || itemNo > 4) {
                    System.out.println("Invalid item number. Please choose 1-4.");
                    continue;
                }
                
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                
                if (quantity <= 0) {
                    System.out.println("Quantity must be positive.");
                    continue;
                }
                
                Food food = new Food(itemNo, quantity);
                
                switch (roomType) {
                    case 1:
                    case 2:
                        ((DoubleRoom) room).getFoodOrders().add(food);
                        break;
                    case 3:
                    case 4:
                        ((SingleRoom) room).getFoodOrders().add(food);
                        break;
                }
                
                System.out.print("Order more items? (y/n): ");
            } while (Character.toLowerCase(scanner.next().charAt(0)) == 'y');
            
            System.out.println("Order placed successfully!");
            
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers.");
            scanner.next(); // Clear invalid input
        } catch (NullPointerException e) {
            System.out.println("Error placing order. Room not properly initialized.");
        }
    }
}

/**
 * Background thread for saving hotel data
 */
class DataSaver implements Runnable {
    private final HotelData hotelData;
    
    public DataSaver(HotelData hotelData) {
        this.hotelData = hotelData;
    }
    
    @Override
    public void run() {
        try (FileOutputStream fileOut = new FileOutputStream("hotel_backup.dat");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(hotelData);
        } catch (IOException e) {
            System.err.println("Error saving hotel data: " + e.getMessage());
        }
    }
}

/**
 * Main application class
 */
public class HotelManagementSystem {
    
    public static void main(String[] args) {
        // Load existing data
        loadHotelData();
        
        Scanner scanner = new Scanner(System.in);
        int choice, roomType;
        
        try {
            mainLoop:
            while (true) {
                displayMainMenu();
                
                try {
                    choice = scanner.nextInt();
                    
                    switch (choice) {
                        case 1: // Display room details
                            displayRoomTypeMenu("Display Features");
                            roomType = scanner.nextInt();
                            Hotel.displayFeatures(roomType);
                            break;
                            
                        case 2: // Display room availability
                            displayRoomTypeMenu("Check Availability");
                            roomType = scanner.nextInt();
                            Hotel.checkAvailability(roomType);
                            break;
                            
                        case 3: // Book room
                            displayRoomTypeMenu("Book Room");
                            roomType = scanner.nextInt();
                            Hotel.bookRoom(roomType);
                            break;
                            
                        case 4: // Order food
                            System.out.print("Enter Room Number: ");
                            int roomNumber = scanner.nextInt();
                            processRoomAction(roomNumber, false);
                            break;
                            
                        case 5: // Checkout
                            System.out.print("Enter Room Number: ");
                            roomNumber = scanner.nextInt();
                            processRoomAction(roomNumber, true);
                            break;
                            
                        case 6: // Exit
                            System.out.println("Thank you for using Hotel Management System!");
                            break mainLoop;
                            
                        default:
                            System.out.println("Invalid option. Please choose 1-6.");
                    }
                    
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear invalid input
                    continue;
                }
                
                System.out.print("\nContinue? (y/n): ");
                String continueChoice = scanner.next().toLowerCase();
                if (!continueChoice.equals("y")) {
                    break;
                }
            }
            
            // Save data before exit
            Thread saveThread = new Thread(new DataSaver(Hotel.hotelData));
            saveThread.start();
            
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Load hotel data from file
     */
    private static void loadHotelData() {
        File backupFile = new File("hotel_backup.dat");
        if (backupFile.exists()) {
            try (FileInputStream fileIn = new FileInputStream(backupFile);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                Hotel.hotelData = (HotelData) objectIn.readObject();
                System.out.println("Previous hotel data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No previous data found. Starting fresh.");
            }
        }
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n=========================");
        System.out.println("  HOTEL MANAGEMENT SYSTEM");
        System.out.println("=========================");
        System.out.println("1. Display Room Details");
        System.out.println("2. Check Room Availability");
        System.out.println("3. Book Room");
        System.out.println("4. Order Food");
        System.out.println("5. Checkout");
        System.out.println("6. Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Display room type selection menu
     */
    private static void displayRoomTypeMenu(String action) {
        System.out.println("\n" + action);
        System.out.println("1. Luxury Double Room (1-10)");
        System.out.println("2. Deluxe Double Room (11-30)");
        System.out.println("3. Luxury Single Room (31-40)");
        System.out.println("4. Deluxe Single Room (41-60)");
        System.out.print("Choose room type: ");
    }
    
    /**
     * Process room-related actions (order food or checkout)
     */
    private static void processRoomAction(int roomNumber, boolean isCheckout) {
        if (roomNumber < 1 || roomNumber > 60) {
            System.out.println("Room number must be between 1 and 60");
            return;
        }
        
        int roomIndex;
        int roomType;
        
        if (roomNumber > 40) {
            roomType = 4; // Deluxe Single
            roomIndex = roomNumber - 41;
        } else if (roomNumber > 30) {
            roomType = 3; // Luxury Single
            roomIndex = roomNumber - 31;
        } else if (roomNumber > 10) {
            roomType = 2; // Deluxe Double
            roomIndex = roomNumber - 11;
        } else {
            roomType = 1; // Luxury Double
            roomIndex = roomNumber - 1;
        }
        
        if (isCheckout) {
            Hotel.checkoutRoom(roomIndex, roomType);
        } else {
            Hotel.orderFood(roomIndex, roomType);
        }
    }
}