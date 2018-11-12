/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterPublication;

import entity.Image;
import entity.ImageDB;
import entity.Publication;
import entity.PublicationDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.vianett.sms.Sms;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;
import util.Notification;
import util.SmsSender;


/**
 *
 * @author BRAHMI
 */
public class NewPublicationController extends FXMLDocumentController{

    Stage stage = new Stage();
    File file;
    private Publication publication;
    @FXML
    private TextField txtImage;

    @FXML
    private TextField txtTitre;

    @FXML
    private TextArea txtContenu;
    
    public static Boolean added = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        super.initDrawer();
    }
   
    @FXML
    private void savePublication(ActionEvent event) {

        if (file != null) {
             Publication publication = new Publication();
                     publication.setTitre(txtTitre.getText());
                     publication.setContenu(txtContenu.getText());
                     publication.setPublicitaire_id(LoginManager.getUser().getId());
                     
                     Notification.createNotification("Succès", "Publication Ajouter");
             
             PublicationDB.ajouterPublication(publication, file);
             SmsSender smssender=new SmsSender();
            Sms sms = new Sms();
            sms.setMessage("votre publication a été ajouter avec succés");
        sms.setSender("0021624928412");
        sms.setRecipient("0021652004104");
        smssender.transceiver.send(sms);
        
             
//            if (result != 0) {
//                File destFile = new File(filePath);
//                FileChannel source = null;
//                FileChannel destination = null;
//
//                try {
//                    source = new FileInputStream(file).getChannel();
//                    destination = new FileOutputStream(destFile).getChannel();
//
//                    if (destination != null && source != null) {
//                        destination.transferFrom(source, 0, source.size());
//
//                        Publication publication = new Publication();
//                        publication.setTitre(txtTitre.getText());
//                        publication.setContenu(txtContenu.getText());
//                        publication.setImageId(result);
//                        if (PublicationDB.addPublication(publication) != 0) {
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                            alert.setTitle("Nouvelle Publication");
//                            alert.setHeaderText("Information");
//                            alert.setContentText("Nouvelle publication sauvegardée avec succès !");
//                            alert.showAndWait();
//
//                            resetPublication();
//                            ((Node)(event.getSource())).getScene().getWindow().hide();
//                            
//                            
//                           
//                        }
//                    }
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (source != null) {
//                        try {
//                            source.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (destination != null) {
//                        try {
//                            destination.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }

            /**
             * *********************************************************************************
             */
            /*
             */
        }

    }

    @FXML
    private void cancelPublication(ActionEvent event) {

        resetPublication();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }

    public void resetPublication() {
        txtTitre.setText("");
        txtContenu.setText("");
        txtImage.setText("");
    }
    @FXML
    private void getFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPEG, extFilterJPG, extFilterPNG);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            txtImage.setText(file.getName());
        }
    }
}