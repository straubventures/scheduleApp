package Controller;

import Model.Appointment;
import Model.Contact;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static Utils.DBDAO.UserDaoImpl.allAppointments;
import static Utils.DBDAO.UserDaoImpl.allContacts;

public class AppointmentsByContactController implements Initializable {

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
    private ComboBox<Contact> contactList;

    @FXML
    private TableView<Appointment> contactApptTbl;

    @FXML
    private TableColumn<Appointment, Integer> idCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    void onActChooseContact(ActionEvent event) {
        ObservableList<Appointment> filteredAppointments = allAppointments.filtered(a -> {
            if(contactList.getSelectionModel().getSelectedItem().getId() == a.getContactId())
                return true;
            return false;

        });

        contactApptTbl.setItems(filteredAppointments);
    }

    @FXML
    void onActGoBack(ActionEvent event) throws IOException {
        sceneManage("/View/Reports.fxml", event);
    }


    public void initialize(URL url, ResourceBundle rb) {
        contactList.setItems(allContacts);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
}
