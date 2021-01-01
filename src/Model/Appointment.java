package Model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * Stores event information.
 *
 *
 */
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
    private Contact contact;
    private static ObservableList<Customer> allApptCustomers;
    private static ObservableList<Contact> allApptContacts;

    /** This method is the constructor of the Appointment class.
     * @param id is the id of the new object.
     * @param title is the title.
     * @param description is the description.
     * @param location is the location.
     * @param type is the type of event.
     * @param start is the start time.
     * @param end  is the end time.
     * @param customerId  is the customer id.
     * @param userId  is the user id.
     * @param contact  is the contact id.
     *  */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, Contact contact) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contact = contact;
    }
    /**
     * Gets the location.
     *
     * @return the location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location sets the location of the appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets all the contacts for the appointment.
     *
     * @return a list of contacts.
     */
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

    /**
     * Gets the id.
     *
     * @return the appointment id.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id.
     *
     * @param id sets the id of the appointment.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the title.
     *
     * @return the title of the appointment.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title.
     *
     * @param title sets the title of the appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description.
     *
     * @return the description of the appointment.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description.
     *
     * @param description sets the description of the appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the type.
     *
     * @return the type of the appointment.
     */
    public String getType() {
        return type;
    }
    /**
     * Sets the type.
     *
     * @param type sets the type of the appointment.
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Gets the start time.
     *
     * @return the start time of the appointment.
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * Sets the start time..
     *
     * @param start sets the start time of the appointment.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * Gets the end time.
     *
     * @return the end time of the appointment.
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * Sets the end time.
     *
     * @param end sets the end time of the appointment.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /**
     * Gets the Customer ID.
     *
     * @return the customer id of the appointment.
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * Sets the customer id.
     *
     * @param customerId sets the customer id of the appointment.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Gets the user id.
     *
     * @return the user id of the appointment.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Sets the user id.
     *
     * @param userId sets the user id of the appointment.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the contact id.
     *
     * @return the contact id of the appointment.
     */
    public Contact getContact() {
        return contact;
    }
    /**
     * Sets the contact id.
     *
     * @param contact sets the contact id of the appointment.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }


}
