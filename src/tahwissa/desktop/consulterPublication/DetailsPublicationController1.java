/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterPublication;

import entity.Commentaire;
import entity.CommentaireDB;
import entity.ImageDB;
import entity.Publication;
import entity.PublicationDB;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * FXML Controller class
 *
 * @author BRAHMI
 */
public class DetailsPublicationController1 implements Initializable {

    Stage stage = new Stage();
    
    @FXML
    private Label pubTitle;
    @FXML 
    private Label pubDate;
    @FXML 
    private Text pubContenu;
    
    @FXML
    private ImageView pubImage;
    File file;
    
    private Publication publication;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private ListView<String> listcomm;
    @FXML
    private Label id;
    @FXML
    private Button commenter;
    @FXML
    private Button deletComnt;
    @FXML
    private Button updateComnt;
    @FXML
    private TextArea txtfieldcmt;
    
    private List<Commentaire> commentaires;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        pubTitle.setText(this.publication.getTitre());
        pubDate.setText(this.publication.getDateHeurePublication().toString());
        pubContenu.setText(this.publication.getContenu());
        File file = new File("C:\\wamp\\www\\tahwissa-web\\web\\"+publication.getImages().get(publication.getImages().size()-1).getChemin());
            System.out.println("C:\\wamp\\www\\tahwissa-web\\web\\"+publication.getImages().get(publication.getImages().size()-1).getChemin());
            try {
                String local = file.toURI()
                        .toURL()
                        .toString();
                 Image image = new Image(local);
             pubImage.setImage(image);
            } catch (MalformedURLException ex) {
                Logger.getLogger(PublicationListViewCell.class.getName()).log(Level.SEVERE, null, ex);
            }
             addComment();
             showComments();
    }    

    
   @FXML
    private void deletePub(ActionEvent e)
    {
        entity.Image image = ImageDB.getImage(publication.getImageId());
        int result = PublicationDB.deletePublication(publication);
        
        if(result != 0)
        {
            File file = new File("C:\\wamp\\www\\tahwissa-web\\web\\"+image.getChemin());
            file.delete();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression d'une publication");
            alert.setHeaderText("Information");
            alert.setContentText("La publication a été supprimée avec succès !");
            alert.showAndWait();
            
            ((Node)(e.getSource())).getScene().getWindow().hide();

        }
    }
    
    
    
    @FXML
    private void updatePub() throws IOException
    {
        EditPublicationController controller = new EditPublicationController();
        controller.setPublication(this.publication);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tahwissa/desktop/consulterPublication/EditPublication.fxml"));
        loader.setController(controller);

        Parent page = (Parent)loader.load(); 
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();   
        
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
  
    public void addComment() {
        
        commenter.setOnAction((event) -> {
            System.out.println("Ceci est et le message ");
            Commentaire cmnt = new Commentaire();
            cmnt.setContenu(txtfieldcmt.getText());
            cmnt.setCommentateur_id(1);
            cmnt.setPublication_id(publication.getId());
            cmnt.setDateHeureCommentaire(new Timestamp(new Date().getTime()));
            CommentaireDB.addCommentaire(cmnt);
                   showComments();  
        });
       
        
    }
    
    public void showComments() {
         try {
            // TODO
            commentaires = CommentaireDB.getCommentaires(publication.getId());
        } catch (SQLException ex) {
            Logger.getLogger(DetailsPublicationController1.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList commentairesPub = FXCollections.observableList(commentaires.stream().map(val -> val.getContenu()).collect(Collectors.toList()));
        listcomm.setItems(commentairesPub);
        
        listcomm.setOnMouseClicked((event) -> {
            int indexComment = listcomm.getSelectionModel().getSelectedIndex();
        Commentaire comment = commentaires.get(indexComment);
            supprimerComment(comment);
        });  
    }
    
    public void supprimerComment(Commentaire comment) {
        deletComnt.setOnAction((event) -> {
            CommentaireDB.deleteCommentaire(comment);
            showComments();
        });
    }
    
    
}
