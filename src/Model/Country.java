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

public class Country {
    private int countryId;
    private String country;
    private ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private ObservableList<String> allDivisionNames = FXCollections.observableArrayList();


    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;

    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


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

    public ObservableList<String> getAllDivisionNames() throws IOException {
        for (Division div : allDivisions) {
            allDivisionNames.add(div.getDivision());
        }
        return allDivisionNames;
    }

    public ObservableList<Division> getAllDivisions() throws IOException {
        return allDivisions;
    }
}