package Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import Utils.DBConnection;
import Utils.DBDAO;
import Utils.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utils.DBDAO.UserDaoImpl.*;

public class DashboardController implements Initializable {

    public static int idCount = 10;
    Stage stage;
    Parent scene;

    /**
     * This method changes windows when called.
     *
     * @param address is the location of the new window
     * @param event   is for the event handler that triggers the switch
     */
    public void sceneManage(String address, ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(address));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private Label uIText;

    @FXML
    private Button reports;

    @FXML
    private TableView<Appointment> apptTbl;

    @FXML
    private Button addCustBtn;

    @FXML
    private Button updateCustBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private Button addApptBtn;

    @FXML
    private Button updateApptBtn;

    @FXML
    private Button deleteApptBtn;

    @FXML
    private TableView<Customer> custTbl;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> custPhoneCol;

    @FXML
    private TableColumn<Customer, String> custAddressCol;

    @FXML
    private TableColumn<Customer, String> custZipCodeCol;

    @FXML
    private TableColumn<Customer, Integer> custDivisionIdCol;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptDescCol;

    @FXML
    private TableColumn<Appointment, String> apptLocCol;

    @FXML
    private TableColumn<Appointment, Integer> apptContactCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, java.sql.Date> apptStartCol;

    @FXML
    private TableColumn<Appointment, java.sql.Date> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIdCol;

    @FXML
    private RadioButton allAppts;

    @FXML
    private RadioButton apptsThisMonth;

    @FXML
    private RadioButton apptsThisWeek;


    /**
     *  Navigates to the reports menu.
     *
     * @param event navigates to the Add Appointment page.
     */
    @FXML
    void onActReports(ActionEvent event) throws IOException {
        sceneManage("/View/Reports.fxml", event);
    }


    /**
     * This handles the add appointment button.
     *
     * @param event navigates to the Add Appointment page.
     */
    @FXML
    void onActAddAppt(ActionEvent event) throws IOException {
        sceneManage("/View/AddAppointment.fxml", event);
    }

    /**
     * Shows all apppointments on Appointments table. Uses a lambda to create a filtered list.
     *
     * @param event navigates to the Add Appointment page.
     */
    @FXML
    void onActShowAll(ActionEvent event) {
        try {
            allAppointments.clear();
            getAllAppointments();
            apptTbl.setItems(allAppointments);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Shows all appointments this month. Uses a lambda to create a filtered list.
     *
     * @param event navigates to the Add Appointment page.
     */
    @FXML
    void onActThisMonth(ActionEvent event) {
        try {
            ObservableList<Appointment> filteredMonthlyAppointments = allAppointments.filtered(a -> {
                if (a.getStart().getMonth().equals(LocalDateTime.now().getMonth()) && a.getStart().isBefore(LocalDateTime.now().plusDays(32)) )
                    return true;
                return false;
            });

            allAppointments.clear();
            getAllAppointments();
            apptTbl.setItems(filteredMonthlyAppointments);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Shows all events this week. Uses a lambda to create a filtered list.
     *
     * @param event navigates to the Add Appointment page.
     */
    @FXML
    void onActThisWeek(ActionEvent event) {
        try {
            ObservableList<Appointment> filteredWeeklyAppointments = allAppointments.filtered(a -> {
                if (a.getStart().isBefore(LocalDateTime.now().plusDays(7)) && a.getStart().isAfter(LocalDateTime.now()) || a.getStart().isEqual(LocalDateTime.now().plusDays(7)) && a.getStart().isAfter(LocalDateTime.now()))
                    return true;
                return false;
            });

            allAppointments.clear();
            getAllAppointments();
            apptTbl.setItems(filteredWeeklyAppointments);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    /**
     * This handles the add customer button.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActAddCust(ActionEvent event) throws IOException {
        sceneManage("/View/AddCustomer.fxml", event);
    }

    /**
     * Deletes selected appointment.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActDeleteAppt(ActionEvent event) {
        try {
            if ((apptTbl.getSelectionModel().getSelectedItem() == null)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select an appointment first");
                alert.showAndWait();
                return;
            } else {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setContentText("Are you sure you wish to remove this appointment? This action cannot be undone.");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Connection conn = DBConnection.startConnection();
                    Appointment selected = apptTbl.getSelectionModel().getSelectedItem();
                    String sqlStatement = "delete from appointments where appointment_id = " + selected.getId() + ";";

                    DBQuery.setStatement(conn);
                    Statement statement = DBQuery.getStatement();

                    statement.execute(sqlStatement);
                    DBConnection.closeConnection();

           for (Appointment appt : allAppointments) {
                        if (appt.getId() == selected.getId()) {
                            allAppointments.remove(appt);
                            uIText.setText("Appointment ID: " + selected.getId() + " Appointment Type: " + selected.getType());
                            break;
                        } else System.out.println("Nothing found");

                    }

                }
            }
        } catch (NullPointerException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Exception: " + ex);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes selected customer.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActDeleteCust(ActionEvent event) {
        try {
            if ((custTbl.getSelectionModel().getSelectedItem() == null)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer first by clicking on their row in the Customer Table.");
                alert.showAndWait();
                return;
            } else {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setContentText("Are you sure you wish to remove this customer? This action cannot be undone.");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Connection conn = DBConnection.startConnection();
                    Customer selected = custTbl.getSelectionModel().getSelectedItem();
                    String sqlStatement = "delete from customers where customer_id = " + selected.getId() + ";";
                    DBQuery.setStatement(conn);
                    Statement statement = DBQuery.getStatement();
                    statement.execute(sqlStatement);
                    for (Customer cust : allCustomers) { //NEEDS WORK
                        if (cust.getId() == selected.getId()) {
                            allCustomers.remove(cust);
                            uIText.setText(cust.getName() + " has been deleted.");


                        } else System.out.println("Failed to remove from list");

                    }
                    DBConnection.closeConnection();
                }
            }
        } catch (NullPointerException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Exception: " + ex.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This handles the modify customer.
     *
     * @param event navigates to the modify part page. It also calls the sendPart() method in the Modify Part Controller.
     */
    @FXML
    void onActUpdateCust(ActionEvent event) throws SQLException, Exception {


        if ((custTbl.getSelectionModel().getSelectedItem() == null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select a customer first");
            alert.showAndWait();
            return;
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ModifyCustomerController MCController = loader.getController();
            MCController.sendCustomer(custTbl.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }



    /** This handles the exit button.
     @param event navigates to exit the entire application.  */
    @FXML
    void onActExit(ActionEvent event) throws IOException {
        System.exit(0);
    }

    /** This handles the modify appointment button.
     @param event navigates to the modify Product page. This uses the sendProduct method, which is sourced from the ModifyProduct
     Controller. */
    @FXML
    void onActUpdateAppt(ActionEvent event) throws Exception {

        if ((apptTbl.getSelectionModel().getSelectedItem() == null)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select an appointment first.");
            alert.showAndWait();
            return;
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            try {
                loader.load();
                System.out.println(loader);
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ModifyAppointmentController MAController = loader.getController();
            MAController.sendAppt(apptTbl.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
    /**
     * Navigates to the login page.
     *
     * @param event navigates to the Add Customer page.
     */
    public void onActSignOut (ActionEvent event) throws IOException {
        sceneManage("/View/Login.fxml", event);
    }

    /** This method is called when the window is first loaded. Within, it sets the id TextField with the current counter. A new object is created that can then be called upon
     * later.
     @param url is the location where this class is found.
     @param rb helps facilitate actions with objects.
     */

    @Override
    public void initialize (URL url, ResourceBundle rb) {

        ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
            if (a.getStart().isEqual(LocalDateTime.now().plusMinutes(15)) || a.getStart().isEqual(LocalDateTime.now()) || a.getStart().isBefore(LocalDateTime.now().plusMinutes(15)) && a.getStart().isAfter(LocalDateTime.now())) {
                uIText.setText("ID: " + a.getId() + " Date: " + a.getStart().toLocalDate() + "Time: " + a.getStart().atZone(ZoneId.systemDefault()));
                return true;
            }
            return false;

        });

        if (!filteredAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("You have an appointment within 15 minutes. Please confirm");
            alert.showAndWait();

        } else {
            uIText.setText("No upcoming appointments.");


        }

            try {
                allCustomers.clear();
                getAllCustomers();
                custTbl.setItems(allCustomers);
            } catch (Exception e) {
                e.printStackTrace();
            }

            custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            custZipCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            custDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

            try {
                allAppointments.clear();
                getAllAppointments();
                apptTbl.setItems(allAppointments);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Appointment Table issue: " + e.getMessage());
                alert.showAndWait();
            }

            apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        }


}
