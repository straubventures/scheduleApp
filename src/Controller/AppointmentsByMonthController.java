package Controller;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Utils.DBDAO.UserDaoImpl.allAppointments;


/** This class controls the report generator for appointments by month/type.  This method uses two lambda expression to create
 * filtered lists of appointments that involve a given month, and then contains a specific text.
  */
public class AppointmentsByMonthController implements Initializable {

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
    private Button backBtn;

    @FXML
    private ComboBox<String> monthList;

    @FXML
    private Label numberOfAppts;

    @FXML
    private TextField apptTypeTxt;



    @FXML
    void onActChooseMonth(ActionEvent event) {
            ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
                if (a.getStart().getMonth().toString().equals(monthList.getSelectionModel().getSelectedItem().toUpperCase()) ) {
                    System.out.println("Yay!");
                    return true;
                }
                System.out.println(a.getStart().getMonth().toString());
                return false;
            });
            numberOfAppts.setText(String.valueOf(filteredAppointments.size()));
            }

    @FXML
    void onKeyFilter(KeyEvent event) {
        ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
            if (a.getStart().getMonth().toString().equals(monthList.getSelectionModel().getSelectedItem().toUpperCase()) && a.getType().contains(apptTypeTxt.getText()) ) {
                System.out.println("Yay!");
                return true;
            }
            System.out.println(a.getStart().getMonth().toString());
            return false;
        });
        numberOfAppts.setText(String.valueOf(filteredAppointments.size()));
    }


    @FXML
    void onActGoBack(ActionEvent event) throws IOException {
        sceneManage("/View/Reports.fxml", event);
    }

    /**
     * This method loads a list of months for the user to choose from.
     *
     * @param url   is for the locating resources.
     * @param rb   is for the accessing resources.
     */
    public void initialize(URL url, ResourceBundle rb){
        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        monthList.setItems(months);
    }
}
