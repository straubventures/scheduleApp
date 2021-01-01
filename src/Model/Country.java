package Model;

import Utils.DBConnection;
import Utils.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Stores country data.
 *
 *
 */
public class Country {
    private int countryId;
    private String country;
    private ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private ObservableList<String> allDivisionNames = FXCollections.observableArrayList();

    /** This method is the constructor of the Country class.
     * @param countryId is the id of the new object.
     * @param country is the name of the country.
     *  */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;

    }
    /**
     * Gets the id.
     *
     * @return the id of the country.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the id.
     *
     * @param countryId sets the id of the country.
     */

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the country name.
     *
     * @return the name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name.
     *
     * @param country sets the name of the country.
     */

    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Sets the division list.
     *
     * @return list of all Divisions.
     */

    public ObservableList<Division> setAllDivisions() throws SQLException {
        Connection conn = DBConnection.startConnection();
        DBQuery.setStatement(conn);

        String sqlStatement = "select * from first_level_divisions where country_id = " + countryId + ";";

        Statement statement = DBQuery.getStatement();
        statement.execute(sqlStatement);
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            int divId = rs.getInt("division_id");
            int countryId = rs.getInt("country_id");
            String division = rs.getString("division");
            Division newDiv = new Division(countryId, divId, division);
            allDivisions.add(newDiv);
        }
        DBConnection.closeConnection();
        return allDivisions;
    }
    /**
     * Gets the list of division names.
     *
     * @return the division names of the country.
     */
    public ObservableList<String> getAllDivisionNames() throws IOException {
        for (Division div : allDivisions) {
            allDivisionNames.add(div.getDivision());
        }
        return allDivisionNames;
    }
    /**
     * Gets the divisions.
     *
     * @return the divisions of the country.
     */
    public ObservableList<Division> getAllDivisions() throws IOException {
        return allDivisions;
    }
    /**
     * Gets the string name.
     *
     * @return the string name of the country.
     */
    @Override
    public String toString() {
        return country;
    }
}