package Model;


/**
 * Stores Division data.
 *
 *
 */
public class Division {
    private int countryId;
    private int divisionId;
    private String division;
    /** This method is the constructor of the Division class.
     * @param countryId is the country id of the new object.
     * @param divisionId is the division id.
     * @param division is the name of the division.

     *  */
    public Division(int countryId, int divisionId, String division) {
        this.countryId = countryId;
        this.divisionId = divisionId;
        this.division = division;
    }
    /**
     * Gets the country id.
     *
     * @return the country id of the division.
     *
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * Sets the country id.
     *
     * @param countryId sets the country id of the division.
     *
     */

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /**
     * Gets the division id.
     *
     * @return the division id of the division.
     *
     */
    public int getDivisionId() {
        return divisionId;
    }


    /**
     * Sets the division id.
     *
     * @param divisionId sets the division id of the division.
     *
     */

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**
     * Gets the division.
     *
     * @return the name of the division.
     *
     */
    public String getDivision() {
        return division;
    }
    /**
     * Sets the division name.
     *
     * @param division sets the division name of the division.
     *
     */

    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * Gets the division name.
     *
     * @return the division name of the division.
     *
     */
    @Override
    public String toString() {
        return division;
    }
}
