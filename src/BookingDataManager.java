import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDataManager {
    private static final String FILE_PATH = "bookingData.txt";
    private static BookingDataManager instance;
    private List<BookingInfo> bookingList;

    private BookingDataManager() {
        bookingList = loadBookingData();

        // Add default data if the booking list is empty
        if (bookingList.isEmpty()) {
            addDefaultData();
            saveBookingData();
        }
    }

    public static BookingDataManager getInstance() {
        if (instance == null) {
            instance = new BookingDataManager();
        }
        return instance;
    }

    public void addBooking(BookingInfo booking) {
        bookingList.add(booking);
        saveBookingData();
    }

    public List<BookingInfo> getBookings() {
        return bookingList;
    }

    public void saveBookingData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BookingInfo booking : bookingList) {
                writer.write(booking.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<BookingInfo> loadBookingData() {
        List<BookingInfo> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(BookingInfo.fromString(line));
            }
        } catch (FileNotFoundException e) {
            // No existing file, return an empty list
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private void addDefaultData() {
        bookingList.add(new BookingInfo("Alice", "Trek Domane", "2024-12-15", 3, true));
        bookingList.add(new BookingInfo("Bob", "Giant Defy", "2024-12-16", 5, false));
        bookingList.add(new BookingInfo("Charlie", "Specialized Allez", "2024-12-17", 2, true));
    }
}
