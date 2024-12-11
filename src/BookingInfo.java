import java.io.Serializable;

public class BookingInfo implements Serializable {
    private String customerName;
    private String bikeModel;
    private String rentalDate;
    private int rentalDuration;
    private boolean available;

    public BookingInfo(String customerName, String bikeModel, String rentalDate, int rentalDuration, boolean available) {
        this.customerName = customerName;
        this.bikeModel = bikeModel;
        this.rentalDate = rentalDate;
        this.rentalDuration = rentalDuration;
        this.available = available;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public int getRentalDuration() {
        return rentalDuration;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return customerName + "," + bikeModel + "," + rentalDate + "," + rentalDuration + "," + available;
    }

    public static BookingInfo fromString(String line) {
        String[] parts = line.split(",");
        return new BookingInfo(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Boolean.parseBoolean(parts[4]));
    }
}
