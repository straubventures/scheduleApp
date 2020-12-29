package Controller;

import Model.Appointment;
import Model.Customer;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Utils.DBDAO.UserDaoImpl.allAppointments;
import static Utils.DBDAO.UserDaoImpl.allCustomers;

public class AppointmentsByCustomerController implements Initializable {

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
    private ComboBox<Customer> customerList;

    @FXML
    private Label apptsScheduled;

    @FXML
    void onActChooseCustomer(ActionEvent event) {
        ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
            if (customerList.getSelectionModel().getSelectedItem().getId() == a.getCustomerId()) {
                return true;
            } else return false;
        });

        apptsScheduled.setText(String.valueOf(filteredAppointments.size()));
    }

    @FXML
    void onActGoBack(ActionEvent event) throws IOException {
        sceneManage("/View/Reports.fxml", event);
    }

    public void initialize(URL url, ResourceBundle rb) {
        customerList.setItems(allCustomers);
    }
}
