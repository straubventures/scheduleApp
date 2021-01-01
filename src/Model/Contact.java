package Model;

/**
 * Stores contact information.
 *
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /** This method is the constructor of the Contact class.
     * @param id is the id of the new object.
     * @param name is the name.
     * @param email is the email.

     *  */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the id.
     *
     * @return the id of the contact.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id.
     *
     * @param id sets the id of the contact.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the name.
     *
     * @return the name of the contact.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name.
     *
     * @param name sets the name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the email.
     *
     * @return the email of the contact.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email.
     *
     * @param email sets the id of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the string return.
     *
     * @return name to actual name in a string.
     */
    @Override
    public String toString() {
        return name;
    }
}
