package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

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
    private Button backButton;

    @FXML
    private Button apptsByMonth;

    @FXML
    private Button apptsByContact;

    @FXML
    private Button apptsByCustomer;

    /**
     * Navigates to contact report.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActByContact(ActionEvent event) throws IOException {
        sceneManage("/View/AppointmentsByContact.fxml", event);
    }

    /**
     * Navigates to customer report.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActByCustomer(ActionEvent event) throws IOException {
        sceneManage("/View/AppointmentsByCustomer.fxml", event);
    }

    /**
     * Navigates to month/type report.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActByMonth(ActionEvent event) throws IOException {
        sceneManage("/View/AppointmentsByMonth.fxml", event);

    }

    @FXML
    void onActGoBack(ActionEvent event) throws IOException {
        sceneManage("/View/Dashboard.fxml", event);
    }
}
