package Controller;

import Model.User;
import Utils.DBDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.security.auth.login.LoginContext;
import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
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
    private TextField userNameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private Text titleLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Text captionLbl;

    @FXML
    void onActGoToSignUpPage(ActionEvent event) throws IOException {
        sceneManage("/view/SignUp.fxml", event);
    }

    @FXML
    void onActLogin(ActionEvent event) throws Exception, SQLException {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle rbund = ResourceBundle.getBundle("Main/Nat", currentLocale);
        boolean userFound = false;
        for (User user : DBDAO.UserDaoImpl.getAllUsers()) {
            if (String.valueOf(user.getPassword()).equals(passwordTxt.getText()) && String.valueOf(user.getUsername()).equals(String.valueOf(userNameTxt.getText()))) {
                System.out.println("Login Successful");
                sceneManage("/View/Dashboard.fxml", event);
                return;

            }
        }
            if (userFound == false) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(rbund.getString("Error"));
                alert.setTitle(rbund.getString("Error"));
                alert.setContentText(rbund.getString("User") + " " + rbund.getString("not") + " " + rbund.getString("found") + ".");
                alert.showAndWait();

            }
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            Locale currentLocale = Locale.getDefault();
            ResourceBundle rbund = ResourceBundle.getBundle("Main/Nat", currentLocale);

            LocalDateTime ldt = LocalDateTime.now();
            ZonedDateTime zdt = ZonedDateTime.now();

            captionLbl.setText(rbund.getString("Where") + " " + rbund.getString("magic") + " " + rbund.getString("happens"));
            loginBtn.setText(rbund.getString("Login"));
            signUpBtn.setText(rbund.getString("Sign") + rbund.getString("up"));
            passwordTxt.setPromptText(rbund.getString("Password"));
            userNameTxt.setPromptText(rbund.getString("Username"));
            locationLbl.setText(zdt.getZone().toString());



            System.out.println(currentLocale.getDisplayLanguage());
            System.out.println(currentLocale.getDisplayCountry());

            System.out.println(currentLocale.getLanguage());
            System.out.println(currentLocale.getCountry());





            System.out.println(System.getProperty("user.country"));
            System.out.println(System.getProperty("user.language"));

        }

}
