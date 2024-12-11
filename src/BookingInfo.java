import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class BookingInfo {
    private String brand;
    private String type;
    private double price;
    private boolean available;
    private String rentalDate;

    public BookingInfo(String brand, String type, double price, boolean available) {
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.available = available;
        this.rentalDate = "";
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    @Override
    public String toString() {
        return brand + "," + type + "," + price + "," + available + "," + rentalDate;
    }

    public static BookingInfo fromString(String line) {
        String[] parts = line.split(",");
        BookingInfo booking = new BookingInfo(parts[0], parts[1], Double.parseDouble(parts[2]), Boolean.parseBoolean(parts[3]));
        if (parts.length > 4) {
            booking.setRentalDate(parts[4]);
        }
        return booking;
    }
}
