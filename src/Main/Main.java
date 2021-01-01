package Main;

import Utils.DBConnection;
import Utils.DBDAO;
import Utils.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;
/**
 *Main application that initiates the rest of the program.
 *
 *
 */
public class Main extends Application {

    /**
     * Loads up all the lists necessary to run the application smoothly.
     *
     * @param primaryStage is the first file location.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        DBDAO.UserDaoImpl.setAllCountries();
        DBDAO.UserDaoImpl.getAllCustomers();
        DBDAO.UserDaoImpl.setAllContacts();
        DBDAO.UserDaoImpl.getAllAppointments();

        Locale currentLocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", currentLocale);
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            primaryStage.setTitle(rb.getString("Hello") + " " + rb.getString("World") + "!");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, IOException {

        //Log application start time
        String filename = "login_activity.txt", login;
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.println("Application started: " + LocalDateTime.now());
        outputFile.close();

        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
        Connection conn =  DBConnection.startConnection();

        DBQuery.setStatement(conn); //Create Statement object
        Statement statement = DBQuery.getStatement(); // Get Statement reference

        launch(args);

        DBConnection.closeConnection();

    }
}
