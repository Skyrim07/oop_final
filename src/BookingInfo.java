import java.io.Serializable;

class BookingInfo {
    private String brand;
    private String type;
    private double price;
    private boolean available;

    public BookingInfo(String brand, String type, double price, boolean available) {
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.available = available;
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

    @Override
    public String toString() {
        return brand + "," + type + "," + price + "," + available;
    }

    public static BookingInfo fromString(String line) {
        String[] parts = line.split(",");
        return new BookingInfo(parts[0], parts[1], Double.parseDouble(parts[2]), Boolean.parseBoolean(parts[3]));
    }
}

