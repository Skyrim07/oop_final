import java.io.*;
import java.util.ArrayList;
import java.util.List;

class BookingDataManager {
    private static final String FILE_PATH = "bikesData.txt";
    private static BookingDataManager instance;
    private List<BookingInfo> bookingList;

    private BookingDataManager() {
        bookingList = loadBookingData();
        if (bookingList.isEmpty() || bookingList.get(0) == null) {
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
                if(booking == null) continue;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private void addDefaultData() {
        System.out.println("Adding default data");
        bookingList.add(new BookingInfo("Trek", "Mountain Bike", 20.0, true));
        bookingList.add(new BookingInfo("Specialized", "Electric Bike", 30.0, true));
        bookingList.add(new BookingInfo("Giant", "Gearless Bike", 15.0, false));
        bookingList.add(new BookingInfo("Cannondale", "Off-Roading Bike", 25.0, true));
        bookingList.add(new BookingInfo("Scott", "Mountain Bike", 22.0, true));
        bookingList.add(new BookingInfo("Bianchi", "Road Bike", 35.0, true));
        bookingList.add(new BookingInfo("Merida", "Electric Bike", 40.0, false));
        bookingList.add(new BookingInfo("Fuji", "Gearless Bike", 18.0, true));
        bookingList.add(new BookingInfo("Kona", "Off-Roading Bike", 27.0, true));
        bookingList.add(new BookingInfo("Cube", "Mountain Bike", 24.0, false));
        bookingList.add(new BookingInfo("GT", "Road Bike", 33.0, true));
        bookingList.add(new BookingInfo("Norco", "Electric Bike", 37.0, true));
        bookingList.add(new BookingInfo("Santa Cruz", "Off-Roading Bike", 45.0, false));
        bookingList.add(new BookingInfo("Yeti", "Mountain Bike", 28.0, true));
        bookingList.add(new BookingInfo("Pivot", "Road Bike", 38.0, false));
        bookingList.add(new BookingInfo("Cervelo", "Gearless Bike", 16.0, true));
        bookingList.add(new BookingInfo("Orbea", "Electric Bike", 42.0, true));
        bookingList.add(new BookingInfo("Raleigh", "Road Bike", 29.0, false));
        bookingList.add(new BookingInfo("Surly", "Off-Roading Bike", 32.0, true));
        bookingList.add(new BookingInfo("Marin", "Mountain Bike", 26.0, true));
        bookingList.add(new BookingInfo("Salsa", "Road Bike", 34.0, false));
        bookingList.add(new BookingInfo("Trek FX", "Hybrid Bike", 20.0, true));
        bookingList.add(new BookingInfo("Specialized Diverge", "Gravel Bike", 35.0, true));
    }

}
