package Model;

/**
 * Stores customer data.
 *
 *
 */
public class Customer {
    private String name;
    private String phone;
    private String address;
    private int id;
    private String postalCode;
    private int divisionId;

    /** This method is the constructor of the Customer class.
     * @param id is the id of the new object.
     * @param name is the title.
     * @param zip is the postal code.
     * @param address is the location.
     * @param phone is the phone number.
     * @param dId is the division id.

     *  */
    public Customer(int id, String name, String zip, String address, String phone, int dId) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.postalCode = zip;
        this.divisionId = dId;
    }
    /**
     * Gets the division id.
     *
     * @return the division id of the customer.
     *
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * Gets the name.
     *
     * @return the name of the customer.
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name sets the name of the customer.
     *
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the phone number.
     *
     * @return the phone number of the customer.
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number.
     *
     * @param phone sets the phone number of the customer.
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Gets the address.
     *
     * @return the address of the customer.
     *
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the address.
     *
     * @param address sets the address of the customer.
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Gets the id.
     *
     * @return the id of the customer.
     *
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id.
     *
     * @param id sets the id of the customer.
     *
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the postal code.
     *
     * @return the postal code of the customer.
     *
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * Sets the zip code.
     *
     * @param postalCode sets the zip code of the customer.
     *
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * Sets the return name.
     *
     * @return name returns the actual name of the customer.
     *
     */
    @Override
    public String toString() {
        return name;
    }

}
