import java.io.*;
import java.util.ArrayList;
import java.util.List;

class BookingDataManager {
    private static final String FILE_PATH = "bikesData.txt";
    private static BookingDataManager instance;
    private List<BookingInfo> bookingList;

    private BookingDataManager() {
        bookingList = loadBookingData();
        if (bookingList.isEmpty()) {
            addDefaultData();
            saveBookingData();
        }
    }

    public static BookingDataManager getInstance() {
        if (instance == null) {
            synchronized (BookingDataManager.class) {
                if (instance == null) {
                    instance = new BookingDataManager();
                }
            }
        }
        return instance;
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
            // If the file doesn't exist, return an empty list
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private void addDefaultData() {
        bookingList.add(new BookingInfo("Trek", "Mountain Bike", 20.0, true));
        bookingList.add(new BookingInfo("Specialized", "Electric Bike", 30.0, true));
        bookingList.add(new BookingInfo("Giant", "Gearless Bike", 15.0, false));
    }
}
