/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.mapDisplay;

import java.net.URL;
import util.MyConnection;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Meedoch
 */
public class MapPlace extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
        
        //URL css = getClass().getResource("/jfoenix-design.css"); 
        Scene scene = new Scene(root);
        //System.out.println(css.toExternalForm());
        //scene.getStylesheets().add(css.toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       // Connection c = MyConnection.getConnection();
    }
    
}
