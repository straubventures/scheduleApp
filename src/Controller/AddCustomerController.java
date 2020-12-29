package Controller;

import Model.Appointment;
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
import java.sql.*;
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
    private TextField custStreetAddressTxt;

    @FXML
    private TextField custZipCodeTxt;

    @FXML
    private Label custNameLbl;

    @FXML
    private Label custPhoneLbl;

    @FXML
    private Label custStreetAddressLbl;

    @FXML
    private Label custZipCodeLbl;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Country> country;

    @FXML
    private ComboBox<Division> fLDivision;




    /**
     * This method causes the divisions to auto-populate.
     *
     * @param event   is for the event handler.
     */
    @FXML
    void onActChooseCountry(ActionEvent event) throws SQLException, IOException {

        Country selectedCountry = country.getSelectionModel().getSelectedItem();
        selectedCountry.setAllDivisions();
        fLDivision.setItems(selectedCountry.getAllDivisions());

    }


    /**
     * This method exits to the main Dashboard.
     *
     * @param event   is for the event handler.
     */
        @FXML
        void onActCancelCust (ActionEvent event) throws IOException {

            sceneManage("/View/Dashboard.fxml", event);

        }


    /**
     * This method adds the customer information to MySQL and a list.
     *
     * @param event   is for the event handler.
     */
        @FXML
        void onActSaveCust (ActionEvent event) throws SQLException, IOException {




            int id = idCount * 40 + Math.toIntExact(Math.round(Math.random() * 1000));
            idCount++;
            String name = custNameTxt.getText();
            String phone = custPhoneTxt.getText();
            String address = custStreetAddressTxt.getText();
            String postalCode = custZipCodeTxt.getText();
            int divisionNum = fLDivision.getSelectionModel().getSelectedItem().getDivisionId();


                Connection conn = DBConnection.startConnection();
                String sqlStatement = "INSERT INTO customers VALUES(?,?,?,?,?, NOW(), 'admin', NOW(), 'admin',?);";

                System.out.println(divisionNum);
                PreparedStatement pSqlStatement = DBConnection.startConnection().prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

                pSqlStatement.setInt(1, id);
                pSqlStatement.setString(2, name);
                pSqlStatement.setString(3, address);
                pSqlStatement.setString(4, postalCode);
                pSqlStatement.setString(5, phone);
                pSqlStatement.setInt(6, divisionNum);

                DBQuery.setStatement(conn);
                pSqlStatement.execute();

                Customer newCust = new Customer(id, name, postalCode, address, phone, divisionNum);

                allCustomers.add(newCust);

                DBConnection.closeConnection();

                sceneManage("/View/Dashboard.fxml", event);

            }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        country.setItems(allCountries);

    }
}
