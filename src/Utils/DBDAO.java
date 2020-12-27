package Utils;

import Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

import static Utils.DBDAO.UserDaoImpl.allCountries;
import static Utils.DBDAO.UserDaoImpl.allCountryNames;


public class DBDAO {


    public static class UserDaoImpl {
        static boolean act;

        public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
        public static ObservableList<String> allCountryNames = FXCollections.observableArrayList();


        public static User getUser(String userName) throws SQLException, Exception {

            Connection conn = DBConnection.startConnection();
            String sqlStatement = "select * FROM users WHERE userName  = '" + userName + "'";

            DBQuery.setStatement(conn);
            User userResult;
            ResultSet result = DBQuery.getStatement().getResultSet();
            while (result.next()) {
                int userId = result.getInt("user_id");
                String userNameG = result.getString("user_name");
                String password = result.getString("password");
                String createDate = result.getString("create_date");
                String createdBy = result.getString("created_by");
                String lastUpdate = result.getString("last_update");
                String lastUpdatedBy = result.getString("last_updated_by");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                userResult = new User(userId, userName, password);
                return userResult;
            }
            DBConnection.closeConnection();
            return null;
        }

        public static ObservableList<User> getAllUsers() throws SQLException, Exception {
            ObservableList<User> allUsers = FXCollections.observableArrayList();
            System.out.println("Before connection");
            Connection conn = DBConnection.startConnection();
            System.out.println("After connection");
            String sqlStatement = "select * from users";
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            statement.execute(sqlStatement);
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                int userid = result.getInt("user_id");
                String userName = result.getString("user_name");
                String password = result.getString("password");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                User userResult = new User(userid, userName, password);
                allUsers.add(userResult);
            }
            DBConnection.closeConnection();
            return allUsers;
        }

        public static Customer getCustomer(String customerName) throws SQLException, Exception {
            Connection conn = DBConnection.startConnection();
            String sqlStatement = "select * from customers where customer_name = " + customerName + ";";
            Customer customerResult;
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            statement.execute(sqlStatement);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String custName = rs.getString("customer_name");
                String zipCode = rs.getString("postal_code");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int divisionId = rs.getInt("division_id");

                customerResult = new Customer(customerId, custName, zipCode, address, phone, divisionId);
                return customerResult;

            }
            DBConnection.closeConnection();
            return null;
        }


        public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
            System.out.println("Before customer connection");
            Connection conn = DBConnection.startConnection();
            System.out.println("After customer connection");
            String sqlStatement = "select * from customers";
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            statement.execute(sqlStatement);
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                int custId = result.getInt("customer_id");
                String customerName = result.getString("customer_name");
                String zipCode = result.getString("postal_code");
                String address = result.getString("address");
                String phoneNumber = result.getString("phone");
                int divisionId = result.getInt("division_id");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                Customer custResult = new Customer(custId, customerName, zipCode, address, phoneNumber, divisionId);
                allCustomers.add(custResult);

            }
            DBConnection.closeConnection();
            return allCustomers;
        }


        public static ObservableList<Appointment> getAllAppointments() throws SQLException {
            System.out.println("before appointment connection");
            Connection conn = DBConnection.startConnection();
            System.out.println("After appt connection");
            String sqlStatement = "select * from appointments;";

            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();

            statement.execute(sqlStatement);

            ResultSet rs = statement.getResultSet();
//        Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID.
            while (rs.next()) {
                int appointmentId = rs.getInt("appointment_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String type = rs.getString("type");
                int contactId = rs.getInt("contact_id");
                LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
                int customerId = rs.getInt("customer_id");
                int userId = rs.getInt("user_id");

                Appointment appt = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                allAppointments.add(appt);
            }
            DBConnection.closeConnection();
            return allAppointments;


        }


        public static void setAllCountries() throws SQLException {

            Connection conn = DBConnection.startConnection();

            String sqlStatement = "select * from countries;";

            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();

            statement.execute(sqlStatement);

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int countryId = rs.getInt("country_id");
                String country = rs.getString("country");
                Country newCountry = new Country(countryId, country);
                allCountries.add(newCountry);

            }
            DBConnection.closeConnection();

        }


        public static ObservableList<Country> getAllCountries() throws IOException {
            return allCountries;
        }

        public static ObservableList<String> getAllCountryNames() throws IOException {
            for (Country country : allCountries) {
                allCountryNames.add(country.getCountry());
            }
            return allCountryNames;
        }
    }
}

