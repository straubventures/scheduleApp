package Controller;

import Utils.DBConnection;
import Utils.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpController {

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
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField confirmPasswordTxt;

    @FXML
    private Button createUserCancelBtn;

    @FXML
    private Button createUserSubmit;

    @FXML
    private Label userUsernameLbl;

    @FXML
    private Label userPasswordLbl;

    @FXML
    private Label userConfirmPassword;

    /**
     * Navigates to main dashboard.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onActCancel(ActionEvent event) throws IOException {
        sceneManage("/view/Login.fxml", event);
    }

    /**
     * Saves user.
     *
     * @param event navigates to the Add Customer page.
     */
    @FXML
    void onSubmitCreateUser(ActionEvent event) throws SQLException, IOException {
        Connection conn =  DBConnection.startConnection();

        DBQuery.setStatement(conn); //Create Statement object
        Statement statement = DBQuery.getStatement(); // Get Statement reference

        // Raw SQL insert statement
        String insertStatement = "INSERT INTO users(user_name, password) VALUES ('"+ usernameTxt.getText() + "', '" + passwordTxt.getText() + "');";



        //Execute statement
        statement.execute(insertStatement);
        if (statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " row(s) affected!");
        else
            System.out.println("No change.");

        DBConnection.closeConnection();

        sceneManage("/view/Login.fxml", event);

    }
}
