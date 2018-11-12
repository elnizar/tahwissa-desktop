/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterPublication;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import entity.SendMail;
import javafx.stage.Stage;
import tahwissa.desktop.FXMLDocumentController;
import util.Notification;
/**
 * FXML Controller class
 *
 * @author BRAHMI
 */


  
public class EmailController extends FXMLDocumentController {

    Stage stage = new Stage();
    

    private TextField nametxt;
    @FXML
    private AnchorPane sujettxt;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburger;
    private TextArea txtemail;
    private TextField adressetxt;
    @FXML
    private Button envoyer;
    SendMail sendMail = new SendMail();
    @FXML
    private TextArea message;
    @FXML
    private TextField mail;
    @FXML
    private TextField sub;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("brahmi med amine");
        super.initDrawer();
        // TODO
    }    

    @FXML
    private void envoyer(ActionEvent event) {
        
         
        
        
        
        sendMail.envoyé(mail.getText(), message.getText(),  sub.getText());
        
         Notification.createNotification("Succès", "votre Email a été envoyer avec succés");
    }
    
}
