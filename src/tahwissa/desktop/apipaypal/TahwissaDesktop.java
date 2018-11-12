package tahwissa.desktop.apipaypal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import tahwissa.desktop.admin.*;
import tahwissa.desktop.user.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Meedoch
 */
public class TahwissaDesktop extends Application {

    private static Stage pStage;

    @Override
    public void start(Stage stage) throws Exception {
        setPrimaryStage(stage);
        //Parent root = FXMLLoader.load(getClass().getResource("affiche.fxml"));
       //Parent root = FXMLLoader.load(getClass().getResource("/gestion/admin/aprouver/Aprouver.fxml"));
  Parent root = FXMLLoader.load(getClass().getResource("compte.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
     public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryStage(Stage pStage) {
        TahwissaDesktop.pStage = pStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
