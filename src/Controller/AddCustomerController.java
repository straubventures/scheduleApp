package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Controller.DashboardController.idCount;
import static Utils.DBDAO.UserDaoImpl.*;

public class AddCustomerController implements Initializable {

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
    private TextField custAddressTxt;

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
    private ComboBox<String> country;

    @FXML
    private ComboBox<String> fLDivision;

    @FXML
    void onActChooseCountry(ActionEvent event) throws SQLException, IOException {
        String countryName =  country.getSelectionModel().getSelectedItem();
        for (Country selectedCountry : allCountries) {
            if (selectedCountry.getCountry().equals(countryName)) {
                System.out.println("Divisions found: " + selectedCountry.setAllDivisions());
                fLDivision.setItems(selectedCountry.getAllDivisionNames());
//                selectedCountry.getAllDivisionNames().clear();
//                selectedCountry.getAllDivisions().clear();
            } else System.out.println("No Divisions found");
        }
    }

    @FXML
    void onActCancelCust(ActionEvent event) throws IOException {

        sceneManage("/View/Dashboard.fxml", event);

    }

    @FXML
    void onActSaveCust(ActionEvent event) throws SQLException, IOException {


        int id = idCount * 40 + Math.toIntExact(Math.round(Math.random() * 1000));
        idCount++;
        String name = custNameTxt.getText();
        String phone = custPhoneTxt.getText();
        String address = custAddressTxt.getText();
        String postalCode = custZipCodeTxt.getText();
        String countryChoice = country.getSelectionModel().getSelectedItem();
        int divisionNum = 0;


        for (Country country : allCountries) {
            if (countryChoice.equals(country.getCountry())) {
                for (Division division : country.getAllDivisions()) {
                    if (fLDivision.getSelectionModel().getSelectedItem().equals(division.getDivision())) {
                        divisionNum = division.getDivisionId();
                    }

                    //get division id
                }

            }


            Connection conn = DBConnection.startConnection();
            String sqlStatement = "INSERT INTO customers VALUES('" + id + "', '" + name + "', '" + address + "', '" + postalCode + "', '" + phone + "', NOW(), 'admin', NOW(), 'admin', '" + divisionNum + "');";

            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            statement.execute(sqlStatement);

            Customer newCust = new Customer(id, name, postalCode, address, phone, 12);

            allCustomers.add(newCust);

            DBConnection.closeConnection();

            sceneManage("/View/Dashboard.fxml", event);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getAllCountryNames();
            country.setItems(allCountryNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
