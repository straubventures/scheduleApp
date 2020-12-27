package Controller;

import Model.Appointment;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

import static Controller.DashboardController.idCount;
import static Utils.DBDAO.UserDaoImpl.allAppointments;

public class AddAppointmentController {

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
    private TextField startHr;

    @FXML
    private TextField startSec;

    @FXML
    private TextField startMin;

    @FXML
    private TextField endHr;

    @FXML
    private TextField endMin;

    @FXML
    private TextField endSec;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField apptTitleTxt;

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
    void onActCancelAppt(ActionEvent event) throws IOException {
        sceneManage("/View/Dashboard.fxml", event);
    }

    @FXML
    void onActAddAppt(ActionEvent event) throws SQLException, IOException {

        try {



                int id = idCount * 40 * Math.toIntExact(Math.round((Math.random() * 1000)));
                idCount++;
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

                //Local Appt Time
                ZoneId zoneIdLocal = ZoneId.systemDefault();
                LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
                LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);

                // Local Appt day
                LocalDate startDay = LocalDate.of(startDate.getValue().getYear(), startDate.getValue().getMonthValue(), startDate.getValue().getDayOfMonth());
                LocalDate endDay = LocalDate.of(endDate.getValue().getYear(), endDate.getValue().getMonthValue(), endDate.getValue().getDayOfMonth());

                //Appt in LocalDateTime object
                LocalDateTime start1 = LocalDateTime.of(startDay, startTime);
                LocalDateTime end1 = LocalDateTime.of(startDay, endTime);

                //Business hours in LocalDateTime object
                ZoneId estZoneId = ZoneId.of("America/New_York");
                LocalDateTime estBStart = LocalDateTime.of(startDay, LocalTime.of(8, 0)); //Business hours in EST
                LocalDateTime estBEnd = LocalDateTime.of(startDay, LocalTime.of(22, 0)); //Business hours in EST


                ZonedDateTime bStartZDT = estBStart.atZone(estZoneId); //Business hours start converted to Zoned Date Time object
                ZonedDateTime lStartZDT = ZonedDateTime.of(startDay, startTime, zoneIdLocal); //Business hours in local time

                ZonedDateTime bEndZDT = estBEnd.atZone(estZoneId);
                ZonedDateTime lEndZDT = ZonedDateTime.of(startDay, endTime, zoneIdLocal);

                Timestamp start = Timestamp.valueOf(start1);
                Timestamp end = Timestamp.valueOf(end1);

            System.out.println(lStartZDT);
            System.out.println(lEndZDT);
            System.out.println(bStartZDT);
            System.out.println(bEndZDT);

            if (lStartZDT.isBefore(bStartZDT) || lStartZDT.isAfter(bEndZDT) || lEndZDT.isAfter(bEndZDT) || lEndZDT.isBefore(bStartZDT)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please place your time within the business hours of 8am-10pm EST.");
                alert.showAndWait();
            } else {

                ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
                    if (a.getStart().isBefore(start1) && a.getEnd().isAfter(end1) || a.getStart().isAfter(start1) && a.getStart().isBefore(end1) || end1.isAfter(a.getStart()) && end1.isBefore(a.getEnd())) {
                        System.out.println("Got one!");
                        return true;
                    }
                    System.out.println("Missed one!");
                    return false;

                });

                if (!filteredAppointments.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This time is taken. Please choose another.");
                    alert.showAndWait();
                } else {
                    System.out.println(lStartZDT);
                    System.out.println(lEndZDT);
                    System.out.println(bStartZDT);
                    System.out.println(bEndZDT);
                    //WebX

                    //One date
                    //Combobox for Customer IDs


                    int customerId = Integer.parseInt(apptCustIdTxt.getText());
                    int userId = Integer.parseInt(apptUserIdTxt.getText());
                    int contactId = Integer.parseInt(apptContactTxt.getText());


                    //Go from EST to system default

//            System.out.println("Local to UTC Start" + localStartToUTC);
//            System.out.println("Local to UTC End" + localEndToUTC);
//            System.out.println("Local to EST Start" + localStartToEST);
//            System.out.println("Local to EST end" + localEndToEST);
//            System.out.println("UTC to Local Start" + utcStartToLocal);
//            System.out.println("UTC to Local End" + utcEndToLocal);

                    Connection conn = DBConnection.startConnection();

                    //Key to keys for prepared statements

                    String sqlStatement = "INSERT INTO appointments VALUES(?,?,?,?,?,?,?,NOW(), 'script',NOW(), 'script',?,?,?);";
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
                    pSqlStatement.setInt(10, contactId);

                    DBQuery.setStatement(conn);

                    Statement statement = DBQuery.getStatement();
                    pSqlStatement.execute();

                    Appointment newAppt = new Appointment(id, title, description, location, type, start.toLocalDateTime(), end.toLocalDateTime(), customerId, userId, contactId);
                    allAppointments.add(newAppt);
                    DBConnection.closeConnection();
                    sceneManage("/View/Dashboard.fxml", event);
                }
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
