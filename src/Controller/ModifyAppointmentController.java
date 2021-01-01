package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
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
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Controller.DashboardController.idCount;
import static Utils.DBDAO.UserDaoImpl.*;

public class ModifyAppointmentController implements Initializable {

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
    private TextField apptIdTxt;

    @FXML
    private TextField apptTitleTxt;

    @FXML
    private Label apptIdLbl;

    @FXML
    private Label apptTitleLbl;

    @FXML
    private Label apptDescLbl;

    @FXML
    private Label apptLocationLbl;

    @FXML
    private Label apptContactLbl;

    @FXML
    private TextArea apptDescTxt;

    @FXML
    private TextField apptLocationTxt;

    @FXML
    private Label apptTypeLbl;

    @FXML
    private Label apptStartLbl;

    @FXML
    private Label apptEndLbl;

    @FXML
    private Label apptCustIdLbl;

    @FXML
    private Label apptUserIdLbl;

    @FXML
    private TextField apptContactTxt;

    @FXML
    private TextField apptTypeTxt;


    @FXML
    private TextField apptCustIdTxt;

    @FXML
    private TextField apptUserIdTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Contact> contactList;

    @FXML
    private TextField startHr;

    @FXML
    private TextField startMin;

    @FXML
    private TextField startSec;

    @FXML
    private TextField endHr;

    @FXML
    private TextField endSec;

    @FXML
    private TextField endMin;

    @FXML
    private DatePicker startDate;

    /**
     * Choose contact.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActChooseContact(ActionEvent event) {

    }

    /**
     * Navigates back to main dashboard.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActCancelCust(ActionEvent event) throws IOException {
        sceneManage("/View/Dashboard.fxml", event);
    }

    /**
     * Saves appointment to MySQL and lists.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActSaveAppt(ActionEvent event) {
        try {
            int id = Integer.parseInt(apptIdTxt.getText());
            String title = apptTitleTxt.getText();
            String description = apptDescTxt.getText();
            String location = apptLocationTxt.getText();
            String type = apptTypeTxt.getText();

            int startMinute = Integer.parseInt(startMin.getText());
            int startHour = Integer.parseInt(startHr.getText());
            int startSecond = Integer.parseInt(startSec.getText());
            int endHour = Integer.parseInt(endHr.getText());
            int endMinute = Integer.parseInt(endMin.getText());
            int endSecond = Integer.parseInt(endSec.getText());

            if (startMinute > 59 || startHour > 23 || startSecond > 59 || startMinute < 0 || startHour < 0 || startSecond < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Time Entries");
                alert.setContentText("Please make sure your time inputs are valid and based on a 24 hour clock.");
                alert.showAndWait();
            } else {

                int customerId = Integer.parseInt(apptCustIdTxt.getText());
                int userId = Integer.parseInt(apptUserIdTxt.getText());
                int contactId = contactList.getSelectionModel().getSelectedItem().getId();


                //Local Appt Time
                ZoneId zoneIdLocal = ZoneId.systemDefault();
                LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
                LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);

                // Local Appt day
                LocalDate startDay = LocalDate.of(startDate.getValue().getYear(), startDate.getValue().getMonthValue(), startDate.getValue().getDayOfMonth());

                //Appt in LocalDateTime object
                LocalDateTime start1 = LocalDateTime.of(startDay, startTime);
                LocalDateTime end1 = LocalDateTime.of(startDay, endTime);

                if (start1.isAfter(end1) || start1.equals(end1)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Time Entries");
                    alert.setContentText("Please make sure your start time is before your end time.");
                    alert.showAndWait();
                } else {

                    //Business hours in LocalDateTime object
                    ZoneId estZoneId = ZoneId.of("America/New_York");
                    LocalDateTime estBStart = LocalDateTime.of(startDay, LocalTime.of(8, 0)); //Business hours in EST
                    LocalDateTime estBEnd = LocalDateTime.of(startDay, LocalTime.of(22, 0)); //Business hours in EST


                    ZonedDateTime bStartZDT = estBStart.atZone(estZoneId); //Business hours start converted to Zoned Date Time object
                    ZonedDateTime lStartZDT = ZonedDateTime.of(startDay, startTime, zoneIdLocal);

                    ZonedDateTime bEndZDT = estBEnd.atZone(estZoneId);
                    ZonedDateTime lEndZDT = ZonedDateTime.of(startDay, endTime, zoneIdLocal);

                    Timestamp start = Timestamp.valueOf(start1);
                    Timestamp end = Timestamp.valueOf(end1);

                    if (lStartZDT.isBefore(bStartZDT) || lStartZDT.isAfter(bEndZDT) || lEndZDT.isAfter(bEndZDT) || lEndZDT.isBefore(bStartZDT)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please place your time within the business hours of 8am-10pm EST.");
                        alert.showAndWait();
                    } else {

                        ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
                            if (a.getStart().isBefore(start1) && a.getEnd().isAfter(end1) || a.getStart().isAfter(start1) && a.getStart().isBefore(end1) || end1.isAfter(a.getStart()) && end1.isBefore(a.getEnd()) || a.getStart().equals(start1) || a.getStart().equals(end1) || a.getEnd().equals(start1) || a.getEnd().equals(end1))
                                return true;
                            return false;

                        });

                        if (!filteredAppointments.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This time is taken. Please choose another.");
                            alert.showAndWait();
                        } else {


                            String sqlStatement = "UPDATE appointments SET Appointment_Id = ?, title = ?, description = ?, Location = ?, type = ?, start = ?, end = ?, Create_Date = NOW(), Created_By = 'admin', Last_Update = NOW(), Last_Updated_By = 'admin', Customer_ID = ?, User_Id = ?, contact_id = ? WHERE Appointment_ID = ?;";
                            PreparedStatement pSqlStatement = DBConnection.startConnection().prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

                            pSqlStatement.setInt(1, id);
                            pSqlStatement.setString(2, title);
                            pSqlStatement.setString(3, description);
                            pSqlStatement.setString(4, location);
                            pSqlStatement.setString(5, type);
                            pSqlStatement.setTimestamp(6, start);
                            pSqlStatement.setTimestamp(7, end);
                            pSqlStatement.setInt(8, customerId);
                            pSqlStatement.setInt(9, userId);
                            pSqlStatement.setInt(10, contactList.getSelectionModel().getSelectedItem().getId());
                            pSqlStatement.setInt(11, id);

                            pSqlStatement.execute();
                            DBConnection.closeConnection();


                            Appointment newAppt = new Appointment(id, title, description, location, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId, userId, contactList.getSelectionModel().getSelectedItem());

                            for (int i = 0; i < getAllAppointments().size(); i++) {
                                if (allAppointments.get(i).getId() == newAppt.getId())
                                    allAppointments.remove(i);
                                break;
                            }

                            DBConnection.closeConnection();
                            sceneManage("/View/Dashboard.fxml", event);
                        }
                    }
                }
            }
        } catch (NumberFormatException | SQLException | IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Enables the modify appt page to be autopopulated by sending the appointment through this method.
     *
     * @param appt is the appointment sent from the main dashboard.
     */
    public void  sendAppt(Appointment appt) {
        try {
            Contact contactChoice = null;
            for (Contact contact : allContacts) {
                if (appt.getContact().getId() == contact.getId()) {
                    contactChoice = contact;
                }
            }

            apptIdTxt.setText(String.valueOf(appt.getId()));
            apptTitleTxt.setText(String.valueOf(appt.getTitle()));
            apptDescTxt.setText(String.valueOf(appt.getDescription()));
            apptLocationTxt.setText(String.valueOf(appt.getLocation()));
            contactList.getSelectionModel().select(contactChoice);
            apptTypeTxt.setText(String.valueOf(appt.getType()));
            startHr.setText(String.valueOf(appt.getStart().getHour()));
            startMin.setText(String.valueOf(appt.getStart().getMinute()));
            startSec.setText(String.valueOf(appt.getStart().getSecond()));
            startDate.setValue(appt.getStart().toLocalDate());
            endHr.setText(String.valueOf(appt.getEnd().getHour()));
            endMin.setText(String.valueOf(appt.getEnd().getMinute()));
            endSec.setText(String.valueOf(appt.getEnd().getSecond()));
            startDate.setValue(appt.getEnd().toLocalDate());
            apptCustIdTxt.setText(String.valueOf(appt.getCustomerId()));
            apptUserIdTxt.setText(String.valueOf(appt.getUserId()));


        } catch (NullPointerException ex) {
            System.out.println("Exception " + ex);
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        contactList.setItems(allContacts);
    }
    }