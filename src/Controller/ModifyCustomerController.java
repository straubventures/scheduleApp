package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static Controller.DashboardController.idCount;
import static Utils.DBDAO.UserDaoImpl.*;


public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    /** This method changes windows when called.
     @param address is the location of the new window
     @param event is for the event handler that triggers the switch */
    public void sceneManage(String address, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(address));
        stage.setScene(new Scene(scene));
        stage.show();
    }



    @FXML
    private TextField custNameTxt;

    @FXML
    private TextField custPhoneTxt;

    @FXML
    private TextField custStreetAddressTxt;

    @FXML
    private TextField custZipCodeTxt;

    @FXML
    private Label custNameLbl;

    @FXML
    private Label custPhoneLbl;

    @FXML
    private Label custAddressLbl;

    @FXML
    private Label custZipCodeLbl;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private ComboBox<Country> country;

    @FXML
    private ComboBox<Division> fLDivision;

    /**
     * Autopopulates divisions based on chosen country.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActChooseCountry(ActionEvent event) throws SQLException, IOException {
        Country selectedCountry = country.getSelectionModel().getSelectedItem();
        fLDivision.setItems(selectedCountry.getAllDivisions());
    }

    /**
     * Navigates back to main dashbaord.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActCancelCust(ActionEvent event) throws IOException {
        sceneManage("/View/Dashboard.fxml", event);
    }


    /**
     * Saves customer and adds them to MySQL and lists.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActSaveCust(ActionEvent event) throws Exception {
        int id = Integer.parseInt(idTxt.getText());
        String name = custNameTxt.getText();
        String phone = custPhoneTxt.getText();
        String address = custStreetAddressTxt.getText();
        String postalCode = custZipCodeTxt.getText();

        int divisionNum = fLDivision.getSelectionModel().getSelectedItem().getDivisionId();

        Connection conn = DBConnection.startConnection();
        String sqlStatement = "UPDATE customers SET Customer_Id = '" + id + "', Customer_Name = '" + name + "', Address = '" + address + "', Postal_Code = '" + postalCode + "', Phone = '" + phone + "', Create_Date = NOW(), Created_By = 'admin', Last_Update = NOW(), Last_Updated_By = 'admin', Division_ID = '" + divisionNum + "' WHERE Customer_Id = " + id + ";";

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();
        statement.execute(sqlStatement);
        DBConnection.closeConnection();
        Customer newCust = new Customer(id, name, postalCode, address, phone, divisionNum);

        for (int i = 0; i < getAllCustomers().size(); i++) {
            if (allCustomers.get(i).getId() == newCust.getId())
                allCustomers.remove(i);
            break;
        }




        allCustomers.add(newCust);

        sceneManage("/View/Dashboard.fxml", event);
    }

    /** This method is called from the main menu when a user modifies a part. It sends the data to the Modify Part page so that
     * the user is able to easily alter their data with pre-filled inputs.
     * @param customer is the part which is sent over */
    public void sendCustomer(Customer customer) throws IOException {
            try {

                idTxt.setText(String.valueOf(customer.getId()));
                custNameTxt.setText(String.valueOf(customer.getName()));
                custPhoneTxt.setText(String.valueOf(customer.getPhone()));
                custStreetAddressTxt.setText(String.valueOf(customer.getAddress()));



                custZipCodeTxt.setText(String.valueOf(customer.getPostalCode()));
                for (Country country1 : allCountries) {
                    country1.setAllDivisions();
                    for (Division div : country1.getAllDivisions()) {
                        if (customer.getDivisionId() == div.getDivisionId()) {
                            fLDivision.setItems(country1.getAllDivisions());
                            fLDivision.getSelectionModel().select(div);
                            for (Country country3 : allCountries) {
                                if (country3.getCountryId() == div.getCountryId()) {
                                    country.getSelectionModel().select(country1);
                                }
                            }
                        }

                    }

                    }

            } catch (NullPointerException | IOException | SQLException ex) {
                System.out.println("Exception " + ex);
            }
        }

        public void initialize(URL url, ResourceBundle rb) {

                 country.setItems(allCountries);

        }
    }

