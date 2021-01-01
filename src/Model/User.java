package Model;

import javafx.collections.ObservableList;

/**
 * Stores user information.
 *
 *
 */
public class User {
    static ObservableList<Appointment> allAppointments;
    private int id;
    private String username;
    private String password;

    /** This method is the constructor of the User class.
     * @param id is the id of the new object.
     * @param username is the title.
     * @param password is the description.
     *  */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    /**
     * Gets the list of appointments.
     *
     * @return the list of appointments for the user.
     *
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }
    /**
     * Sets the list of appointments.
     *
     * @param allAppointments sets the list of appointments of the user.
     *
     */

    public static void setAllAppointments(ObservableList<Appointment> allAppointments) {
        User.allAppointments = allAppointments;
    }
    /**
     * Gets the id.
     *
     * @return the id of the user.
     *
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id.
     *
     * @param id sets the id of the user.
     *
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the username.
     *
     * @return the username of the user.
     *
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username.
     *
     * @param username sets the username of the user.
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the password.
     *
     * @return the password of the user.
     *
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password.
     *
     * @param password sets the password of the user.
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
