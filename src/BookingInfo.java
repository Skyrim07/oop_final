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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid data format: " + line);
        }

        BookingInfo booking = new BookingInfo(parts[0], parts[1], Double.parseDouble(parts[2]), Boolean.parseBoolean(parts[3]));
        if (parts.length > 4) {
            booking.setRentalDate(parts[4]);
        }
        return booking;
    }
}
