public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUTOFSERVICE }


    public Vehicle(String make, String model, int year) {
        this.make = (make == null || make.isEmpty()) ? null : capitalize(make);
        this.model = (model == null || model.isEmpty()) ? null : capitalize(model);
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }

    public Vehicle() {
        this(null, null, 0);
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public void setLicensePlate(String plate) {

        this.licensePlate = (plate == null) ? null : plate.toUpperCase();
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model; }

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }
    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

    public String toString() {

        return getInfo();
    }
}
