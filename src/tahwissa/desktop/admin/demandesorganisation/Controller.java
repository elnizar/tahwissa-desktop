/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin.demandesorganisation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import tahwissa.desktop.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import entity.Evenement;
import entity.Messagerie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;
import service.EvenementService;
import service.EvenementServiceInterface;
import service.MessagerieService;
import tahwissa.desktop.detailsevent.DetailsController;
import tahwissa.desktop.modifierrando.ModifierController;
import tahwissa.desktop.myevents.MyEventsController1;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class Controller implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private GridPane gridPane;

    @FXML
    private JFXTreeTableView<Evenement> treeView;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private ImageView image;

    @FXML
    private Text description;

    @FXML
    private Text reglement;

    @FXML
    private Text datedepart;

    @FXML
    private Text frais;

    @FXML
    private Text infos;

    @FXML
    private Text places;

    @FXML
    private ImageView campImage;

    @FXML
    private ImageView randoImage;

    private final String imageSource = "http://localhost/tahwissa/web/images/evenements/";

    @FXML
    private ImageView infosImage;

    private List<Evenement> events;
    public List<Color> list = new ArrayList<>();
    private EvenementServiceInterface service = new EvenementService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTreeTableView();
    }

    public void initTreeTableView() {
        events = service.getEnAttente();
        //  treeView= new JFXTreeTableView<Evenement>();
        JFXTreeTableColumn<Evenement, String> dateCreationColumn = new JFXTreeTableColumn<>("Date création");
        dateCreationColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(param.getValue().getValue().dateCreation.toString());
            return property;
        });
        dateCreationColumn.setPrefWidth(160);

        JFXTreeTableColumn<Evenement, String> destinationColumn = new JFXTreeTableColumn<>("Destination");
        destinationColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(param.getValue().getValue().getDestination());
            return property;
        });
        destinationColumn.setPrefWidth(160);

        JFXTreeTableColumn<Evenement, String> nbPlacesColumn = new JFXTreeTableColumn<>("Nombre de places");
        nbPlacesColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Evenement e = (Evenement) param.getValue().getValue();
            property.setValue(String.valueOf(e.getNombrePlaces()));
            return property;
        });
        nbPlacesColumn.setPrefWidth(160);

        JFXTreeTableColumn<Evenement, String> statutColumn = new JFXTreeTableColumn<>("Statut");
        statutColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Evenement e = (Evenement) param.getValue().getValue();
            property.setValue(e.getStatut());
            return property;
        });
        statutColumn.setPrefWidth(100);

        JFXTreeTableColumn<Evenement, String> typeColumn = new JFXTreeTableColumn<>("Type");
        typeColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Evenement e = (Evenement) param.getValue().getValue();
            property.setValue(e.getEvenementType());
            return property;
        });
        typeColumn.setPrefWidth(80);

        treeView.getColumns().add(dateCreationColumn);
        treeView.getColumns().add(typeColumn);
        treeView.getColumns().add(destinationColumn);
        treeView.getColumns().add(nbPlacesColumn);
        treeView.getColumns().add(statutColumn);

        ObservableList<Evenement> evenements = FXCollections.observableArrayList(events);
        TreeItem<Evenement> root = new RecursiveTreeItem<Evenement>(evenements, RecursiveTreeObject::getChildren);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        treeView.setOnMouseClicked((event) -> {
            if (treeView.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            Evenement e = treeView.getSelectionModel().getSelectedItem().getValue();

            if (e.getImages().isEmpty() == false) {
                image.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
            }
            description.setText(e.getDescription());
            reglement.setText(e.getReglement());
            datedepart.setText(e.getDateHeureDepart().toString());
            frais.setText(String.valueOf(e.getFrais()));
            if (e.getEvenementType().equals("randonnee")) {
                infosImage.setImage(randoImage.getImage());
                infos.setText(e.getType());
            } else {
                infosImage.setImage(campImage.getImage());
                infos.setText(e.getDuree() + " kilomètres");
            }
            places.setText((e.getNombrePlaces() - e.getNombrePlacesPrises()) + " places");
            detailsPane.setVisible(true);
        });

    }

    public void refreshTreeTable() {
        try {
            events = service.getByUser(LoginManager.getUser().getId());
            ObservableList<Evenement> evenements = FXCollections.observableArrayList(events);
            TreeItem<Evenement> root = new RecursiveTreeItem<Evenement>(evenements, RecursiveTreeObject::getChildren);
            treeView.setRoot(root);
            treeView.setShowRoot(false);
        } catch (SQLException ex) {
            Logger.getLogger(MyEventsController1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void accepter(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Confirmation"));
        content.setBody(new Text("Vous êtes sur le point d'accepter cette demande d'organisation"));
        JFXButton okButton = new JFXButton("Continuer");
        content.getActions().add(okButton);
        JFXButton delButton = new JFXButton("Fermer");
        content.getActions().add(delButton);
        stackPane.setMouseTransparent(false);
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        delButton.setOnAction((e) -> {
            stackPane.setMouseTransparent(true);
            dialog.close();
        });
        okButton.setOnAction((e) -> {
            Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
            evenement.setStatut("Accepté");
            try {
                service.update(evenement);
                Messagerie m = new Messagerie();
                m.setSender_id(3);
                m.setReciever_id(evenement.getOrganisateur_id());
                m.setContenuMsg("Votre demande d'organisation de " + evenement.getEvenementType() + " à " + evenement.getDestination() + " à été acceptée.");
                MessagerieService service = new MessagerieService();
                service.ajouterMessage(m);
                Notification.createNotification("Succès", "Demande d'organisation acceptée.");
                initTreeTableView();
            } catch (SQLException ex) {

            }
            stackPane.setMouseTransparent(true);
            dialog.close();
        });

        dialog.show();
    }

    @FXML
    void refuser(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Confirmation"));
        HBox hb = new HBox();

        //Text t = new Text("Veuillez indiquer le motif de refus");
        JFXTextArea input = new JFXTextArea();
        input.setPadding(new Insets(100, 0, 0, 0));
        input.setPromptText("Motif de refus");
        input.setLabelFloat(true);
        content.setBody(input);
        JFXButton okButton = new JFXButton("Valider");
        content.getActions().add(okButton);
        JFXButton delButton = new JFXButton("Fermer");
        content.getActions().add(delButton);
        stackPane.setMouseTransparent(false);
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        delButton.setOnAction((e) -> {
            stackPane.setMouseTransparent(true);
            dialog.close();
        });
        okButton.setOnAction((e) -> {
            Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
            evenement.setStatut("Refusé");
            try {
                service.update(evenement);
                Messagerie m = new Messagerie();
                m.setSender_id(3);
                m.setReciever_id(evenement.getOrganisateur_id());
                m.setContenuMsg("Votre demande d'organisation de " + evenement.getEvenementType() + " à " + evenement.getDestination() + " à été rejetée. Motif : " + input.getText());
                MessagerieService service = new MessagerieService();
                service.ajouterMessage(m);
                Notification.createNotification("Succès", "Demande d'organisation refusée.");
                initTreeTableView();
            } catch (SQLException ex) {

            }
            stackPane.setMouseTransparent(true);
            dialog.close();
        });

        dialog.show();
    }
}
