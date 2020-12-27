package Main;

import Utils.DBConnection;
import Utils.DBDAO;
import Utils.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {





        DBDAO.UserDaoImpl.setAllCountries();
        DBDAO.UserDaoImpl.getAllCustomers();
        DBDAO.UserDaoImpl.getAllAppointments();


        Locale currentLocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", currentLocale);
            Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            primaryStage.setTitle(rb.getString("Hello") + " " + rb.getString("World"));
            primaryStage.setScene(new Scene(root, 1227, 550));
            primaryStage.show();
    }



    public static void main(String[] args) throws SQLException {

        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
        Connection conn =  DBConnection.startConnection();

        DBQuery.setStatement(conn); //Create Statement object
        Statement statement = DBQuery.getStatement(); // Get Statement reference


        launch(args);

        DBConnection.closeConnection();

    }
}
