/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterPublication;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import entity.Publication;
import entity.PublicationDB;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.consulterPublication.DetailsPublicationController;


/**
 *
 * @author BRAHMI
 */
public class MainController extends FXMLDocumentController {

    Stage stage = new Stage();
    File file;
    ObservableList<Publication> observPubs;

    @FXML
    ListView<Publication> listPubs;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private TextField recherche;
    
    
   
    
    @FXML
    private void newPublication(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/consulterPublication/NewPublication.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       
    }
    
    private void detailsPublication(ActionEvent event) throws IOException {

        Publication publication = PublicationDB.getPublication(13);

        if (publication != null) {
            
            DetailsPublicationController controller = new DetailsPublicationController();
            controller.setPublication(publication);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tahwissa/desktop/consulterPublication/DetailsPublication.fxml"));
            loader.setController(controller);

            Parent page = (Parent) loader.load();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.show();
            
        }

    }


    public MainController() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initDrawer();
        
        List publications = null;
        try {
            publications = PublicationDB.getPublications();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        observPubs = FXCollections.observableArrayList(publications);
        this.displayListView();

        
        
recherche.textProperty().addListener(new ChangeListener() {
    @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                recherche((String) oldValue, (String) newValue);

            }
            
        });
    }
    
    
    
    public void displayListView()
    {
        if (observPubs != null) {
            listPubs.setItems(this.observPubs);

            listPubs.setCellFactory(studentListView -> new PublicationListViewCell());

            listPubs.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    Publication publication = listPubs.getSelectionModel().getSelectedItem();
                    
                    if (publication != null) {
                        DetailsPublicationController controller = new DetailsPublicationController();
                        controller.setPublication(publication);
                        System.out.println("Produit ID: "+publication.getId());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tahwissa/desktop/consulterPublication/DetailsPublication.fxml"));
                        loader.setController(controller);
                        
                        Parent page = null;
                        try {
                            page = (Parent) loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Scene scene = new Scene(page);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });
        }
    }
    
    
    
    public void recherche(String oldValue, String newValue) {
        ObservableList<Publication> filteredList = FXCollections.observableArrayList();
        if (recherche == null || (newValue.length() < oldValue.length()) || newValue == null) {

            displayListView();

        } else {
            newValue = newValue.toUpperCase();
            for (Publication t : listPubs.getItems()) {
                String filterType = t.getTitre();

                if (filterType.toUpperCase().contains(newValue)) {
                    filteredList.add(t);
                }
            }
            listPubs.setItems(filteredList);
        }
    }

}
