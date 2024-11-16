import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Интерфейсы
interface HotelServiceInterface {
    List<Hotel> searchHotels(String location, String classType);
    Hotel getHotel(int id);
}

interface BookingServiceInterface {
    boolean bookRoom(int userId, int hotelId, String checkIn, String checkOut);
    List<Booking> getUserBookings(int userId);
}

interface PaymentServiceInterface {
    boolean processPayment(int bookingId);
}

interface NotificationServiceInterface {
    void sendBookingConfirmation(int bookingId);
}

interface UserManagementServiceInterface {
    int registerUser(String username, String password);
    boolean loginUser(String username, String password);
}


// Классы, реализующие интерфейсы (упрощенные)
class Hotel {
    int id;
    String name;
    String location;
    String classType;

    public Hotel(int id, String name, String location, String classType) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.classType = classType;
    }
}

class Booking {
    int id;
    int userId;
    int hotelId;
    String checkIn;
    String checkOut;
}

class HotelServiceImpl implements HotelServiceInterface {
    List<Hotel> hotels = new ArrayList<>();

    public HotelServiceImpl(){
        hotels.add(new Hotel(1, "Hilton", "New York", "Luxury"));
        hotels.add(new Hotel(2, "Marriott", "London", "Standard"));
    }

    @Override
    public List<Hotel> searchHotels(String location, String classType) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel h : hotels){
            if (h.location.equals(location) && h.classType.equals(classType)){
                result.add(h);
            }
        }
        return result;
    }

    @Override
    public Hotel getHotel(int id) {
        for (Hotel h: hotels){
            if (h.id == id){
                return h;
            }
        }
        return null;
    }
}

class BookingServiceImpl implements BookingServiceInterface {
    List<Booking> bookings = new ArrayList<>();

    @Override
    public boolean bookRoom(int userId, int hotelId, String checkIn, String checkOut) {
        bookings.add(new Booking()); // упрощенная логика
        return true;
    }

    @Override
    public List<Booking> getUserBookings(int userId) {
        return null;
    }
}


class PaymentServiceImpl implements PaymentServiceInterface {
    @Override
    public boolean processPayment(int bookingId) {
        return true; // упрощенная логика
    }
}

class NotificationServiceImpl implements NotificationServiceInterface {
    @Override
    public void sendBookingConfirmation(int bookingId) {
        System.out.println("Booking confirmation sent for booking ID: " + bookingId);
    }
}

class UserManagementServiceImpl implements UserManagementServiceInterface {
    @Override
    public int registerUser(String username, String password) {
        return 1; // упрощенная логика
    }

    @Override
    public boolean loginUser(String username, String password) {
        return true; // упрощенная логика
    }
}


public class HomeTask11_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HotelServiceInterface hotelService = new HotelServiceImpl();
        BookingServiceInterface bookingService = new BookingServiceImpl();
        PaymentServiceInterface paymentService = new PaymentServiceImpl();
        NotificationServiceInterface notificationService = new NotificationServiceImpl();
        UserManagementServiceInterface userManagementService = new UserManagementServiceImpl();

        int userId = 0; // Инициализация ID пользователя

        System.out.println("Welcome to Hotel Booking System!");

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Search hotels");
            System.out.println("4. Book a room");
            System.out.println("5. View my bookings");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    userId = userManagementService.registerUser(username, password);
                    System.out.println("User registered successfully. Your User ID is: " + userId);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (userManagementService.loginUser(username, password)) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 3:
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();
                    System.out.print("Enter class type (e.g., Luxury, Standard): ");
                    String classType = scanner.nextLine();
                    List<Hotel> hotels = hotelService.searchHotels(location, classType);
                    if (hotels.isEmpty()) {
                        System.out.println("No hotels found.");
                    } else {
                        for (Hotel hotel : hotels) {
                            System.out.println(hotel.id + ". " + hotel.name + " (" + hotel.location + ", " + hotel.classType + ")");
                        }
                    }
                    break;
                case 4:
                    if (userId == 0) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.print("Enter hotel ID: ");
                    int hotelId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String checkIn = scanner.nextLine();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String checkOut = scanner.nextLine();
                    if (bookingService.bookRoom(userId, hotelId, checkIn, checkOut)) {
                        System.out.println("Room booked successfully!");
                        // В реальном приложении здесь нужно было бы получить ID бронирования
                    } else {
                        System.out.println("Room booking failed.");
                    }
                    break;
                case 5:
                    if (userId == 0) {
                        System.out.println("Please login first.");
                        break;
                    }
                    // В реальном приложении здесь нужно было бы вывести список бронирований пользователя
                    System.out.println("View my bookings (not implemented yet)");
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}