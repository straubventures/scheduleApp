package Model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private static ObservableList<Customer> allApptCustomers;
    private static ObservableList<Contact> allApptContacts;

    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static ObservableList<Contact> getAllApptContacts(){
        return allApptContacts;
    }

    /** This method gets all customers on the allApptCustomers list.
     * @return the list of the appointment's list. */
    public static ObservableList<Customer> getAllApptCustomers() {
        return allApptCustomers;
    }

    /** This method adds a customer on the allApptCustomers list.
     * @param customer is the customer which the user wishes to add. */
    public static void addApptCustomer(Customer customer) {
        Appointment.allApptCustomers.add(customer);
    }

    /** This method removes a customer on the allApptCustomers list.
     * @param id is the id of the customer which the user wishes to remove.
     * @return the value of the removal's success. */
    public static boolean removeApptCustomer(int id) {
        for (Customer cust : Appointment.getAllApptCustomers()) {
            if (cust.getId() == id) {
               Appointment.allApptCustomers.remove(cust);
               return true;
            }
        } return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


}
