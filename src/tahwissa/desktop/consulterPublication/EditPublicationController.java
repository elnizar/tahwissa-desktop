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
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tahwissa.desktop.LoginManager;


/**
 *
 * @author BRAHMI
 */
public class EditPublicationController implements Initializable{

    private Publication publication;
    @FXML
    private TextField txtTitre;
    
    @FXML
    private TextArea txtContenu;
   
    @FXML
    private TextField txtImage;
    
    File file;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        txtTitre.setText(this.publication.getTitre());
        txtContenu.setText(this.publication.getContenu());
        System.out.println("ID Produit : (EditController)"+publication.getId());
    }

    /**
     * @return the publication
     */
    public Publication getPublication() {
        return publication;
    }

    /**
     * @param publication the publication to set
     */
    public void setPublication(Publication publication) {
        this.publication = publication;
    }
    
    
    
    @FXML
    private void savePublication(ActionEvent event) {
        
        publication.setTitre(txtTitre.getText());
        publication.setContenu(txtContenu.getText());
        publication.setPublicitaire_id(LoginManager.getUser().getId());
        
        
        if(file != null)
        {
            Image image = publication.getImages().get(0);
            File oldFile = new File("C:\\wamp\\www\\tahwissa\\web\\"+image.getChemin());
            System.out.println("*************************"+image.getChemin());
            System.out.println("Publication ID from SavePublication: "+publication.getId());
        
         if(PublicationDB.modifierPublication(publication, file))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour");
            alert.setHeaderText("Information");
            alert.setContentText("Publication mise à jour avec succès !");
            alert.showAndWait();
            resetPublication();
            
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
       
    } 
    }
    
      @FXML
    private void cancelPublication(ActionEvent event) {
        resetPublication();  
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    public void resetPublication()
    {
        txtTitre.setText("");
        txtContenu.setText("");
        txtImage.setText("");
    }
     @FXML
    private void getFile()
    {
        FileChooser fileChooser= new FileChooser();
        FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPEG, extFilterJPG, extFilterPNG);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            
        file = fileChooser.showOpenDialog(null);
        if(file != null)
        {
            txtImage.setText(file.getName());
        }
    }
}
