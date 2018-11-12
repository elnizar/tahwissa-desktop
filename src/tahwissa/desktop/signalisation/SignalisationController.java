/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.signalisation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.Signalisation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.SignalisationService;
import tahwissa.desktop.LoginManager;
import util.Notification;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SignalisationController implements Initializable {

    @FXML
    private JFXButton signaler;
        @FXML
    private JFXTextArea motif;

    @FXML
    private JFXTextField objet;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void ajouterSignal(ActionEvent event){
        
        Signalisation S = new Signalisation();
        
        Signalisation p = new Signalisation(LoginManager.getUser().getId(),objet.getText(),motif.getText(),2);
            SignalisationService ps = new SignalisationService();
            
            ps.ajouterSignalisation(p);
            Notification.createNotification("Succès", "Votre Signalisation a bien été envoyé");
            redirection(event);
}
    public void redirection(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            app_stage.setScene(scene);
            
            app_stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignalisationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
