import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    private static RentalSystem instance;

    private RentalSystem() {
        loadData(); 
    }


    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }


    public boolean addVehicle(Vehicle vehicle) {
        if (findVehicleByPlate(vehicle.getLicensePlate()) != null) {
            System.out.println("Vehicle with plate " + vehicle.getLicensePlate() + " already exists.");
            return false;
        }
        vehicles.add(vehicle);
        saveVehicle(vehicle);
        return true;
    }


    public boolean addCustomer(Customer customer) {
        if (findCustomerById(String.valueOf(customer.getCustomerId())) != null) {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists.");
            return false;
        }
        customers.add(customer);
        saveCustomer(customer);
        return true;
    }


    public boolean rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle rented to " + customer.getCustomerName());
            return true;
        } else {
            System.out.println("Vehicle is not available for renting.");
            return false;
        }
    }


    public boolean returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
            return true;
        } else {
            System.out.println("Vehicle is not rented.");
            return false;
        }
    }    


    public void displayVehicles(boolean onlyAvailable) {
        System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
        System.out.println("---------------------------------------------------------------------------------");
        
        for (Vehicle v : vehicles) {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                String type = (v instanceof Car) ? "Car" 
                              : (v instanceof Motorcycle) ? "Motorcycle" 
                              : (v instanceof Truck) ? "Truck" 
                              : "Unknown";
                System.out.println("| " + type + "\t|\t" 
                                   + v.getLicensePlate() + "\t|\t" 
                                   + v.getMake() + "\t|\t" 
                                   + v.getModel() + "\t|\t" 
                                   + v.getYear() + "\t|");
            }
        }
        System.out.println();
    }
    

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    

    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    

    public Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == Integer.parseInt(id))
                return c;
        }
        return null;
    }
    
        public void saveVehicle(Vehicle vehicle) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("vehicles.txt", true))) {
            bw.write(vehicle.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void saveCustomer(Customer customer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt", true))) {
            bw.write(customer.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void saveRecord(RentalRecord record) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rental_records.txt", true))) {
            bw.write(record.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void loadData() {
     
        try (BufferedReader br = new BufferedReader(new FileReader("vehicles.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
            }
        } catch (IOException e) {
     
           }
    }
}
