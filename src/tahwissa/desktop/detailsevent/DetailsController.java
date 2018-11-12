/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.detailsevent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entity.Creneau;
import entity.Evenement;
import entity.Participation;
import entity.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import service.ParticipationService;
import service.ParticipationServiceInterface;
import service.RatingService;
import service.RatingServiceInterface;
import service.UserService;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;
import tahwissa.desktop.myevents.MyEventsController;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class DetailsController implements Initializable {

    private ParticipationServiceInterface serviceParticipation = new ParticipationService();
    private RatingServiceInterface serviceRating = new RatingService();
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Text nomO;

    @FXML
    private Text telO;

    @FXML
    private Text mailO;

    @FXML
    private Text nomG;

    @FXML
    private Text telG;

    @FXML
    private Text mailG;

    @FXML
    private JFXHamburger hamburger;

    public static Evenement event;
    private final String imageSource = "http://localhost/tahwissa/web/images/evenements/";

    @FXML
    private ImageView imageDisplay;

    @FXML
    private Text description;

    @FXML
    private Text reglement;

    @FXML
    private Label datedepart;

    @FXML
    private Label frais;

    @FXML
    private Label distance;
    private entity.Image currentImage;
    @FXML
    private Label nbplaces;

    @FXML
    private Label duree;

    @FXML
    private Label title;

    @FXML
    private ImageView camping;

    @FXML
    private ImageView randonnee;

    @FXML
    private JFXTreeTableView<Creneau> treeView;

    @FXML
    private ImageView star1;

    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    private Image fullStar;
    private Image emptyStar;
    private List<ImageView> stars;

    @FXML
    private Text note;

    @FXML
    private StackPane stackPane;
    UserService serviceUser = new UserService();
    private User organisateur;
    private User guide;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDrawer();
        currentImage = event.getImages().get(0);
        organisateur = serviceUser.find(event.getOrganisateur_id());
        guide = serviceUser.find(event.getGuide_id());
        imageDisplay.setImage(new Image(imageSource + currentImage.getChemin()));
        //System.out.println(event.getImages().size());
        initValues();
        initTreeTableView();
        animate();
        initStars();
    }

    public void initStars() {
        stars = new ArrayList<ImageView>();
        stars.add(star1);
        stars.add(star2);
        stars.add(star3);
        stars.add(star4);
        stars.add(star5);
        fullStar = star1.getImage();
        emptyStar = star2.getImage();
        long nbStars = Math.round(event.getRating());
        for (int i = 0; i < nbStars; i++) {
            stars.get(i).setImage(fullStar);
        }
        for (int i = (int) nbStars; i < stars.size(); i++) {
            stars.get(i).setImage(emptyStar);
        }
        note.setText("Note : " + event.getRating() + "/5 (" + event.getNombreRates() + " votes)");
    }

    public void animate() {
        FadeTransition ft = new FadeTransition(Duration.millis(1500), imageDisplay);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.setOnFinished((event) -> {
            nextImage(null);
            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
            ft.setOnFinished((e) -> {
                ft.setFromValue(1.0);
                ft.setToValue(1.0);
                ft.setDuration(Duration.millis(3000));
                ft.play();
                ft.setOnFinished((e2) -> {
                    animate();
                });

            });
        });

        ft.play();
    }

    public void initDrawer() {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));
            box.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setSidePane(box);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            //System.out.println(drawer);
            if (drawer.isShown()) {
                drawer.close();
                drawer.setSidePane(new VBox());
            } else {
                try {
                    VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));

                    drawer.setSidePane(box);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                drawer.open();
            }
        });
    }

    public void initValues() {
        description.setText(event.getDescription());
        reglement.setText(event.getReglement());
        if (event.getEvenementType().equals("randonnee")) {
            title.setText("Randonéée " + event.getType() + " à " + event.getDestination());
            distance.setText(String.valueOf(event.getDistanceParcourue()) + " kilomètres");
            camping.setImage(null);
            duree.setText("");
        } else {
            title.setText("Camping à " + event.getDestination());
            duree.setText("");
            distance.setText(String.valueOf(event.getDuree()) + " jours");
            randonnee.setImage(camping.getImage());
            camping.setImage(null);
        }
        nbplaces.setText(String.valueOf(event.getNombrePlaces() - event.getNombrePlacesPrises()) + " place(s) disponible(s)");
        datedepart.setText(event.getDateHeureDepart().toString());
        frais.setText(event.getFrais() + " dinars");

        nomO.setText(organisateur.getNom() + " " + organisateur.getPrenom());
        if ((organisateur.getNumeroTelephone() == null) || (organisateur.getNumeroTelephone().equals(""))) {
            telO.setText("Non défini");
        } else {
            telO.setText(organisateur.getNumeroTelephone());
        }
        mailO.setText(organisateur.getEmail());
        
        nomG.setText(guide.getNom() + " " + guide.getPrenom());
        if ((guide.getNumeroTelephone() == null) || (guide.getNumeroTelephone().equals(""))) {
            telG.setText("Non défini");
        } else {
            telG.setText(guide.getNumeroTelephone());
        }
        mailG.setText(guide.getEmail());

    }

    @FXML
    void nextImage(MouseEvent ev) {
        int next = event.getImages().indexOf(currentImage) + 1;
        if (next < event.getImages().size()) {
            // System.out.println(imageSource+event.getImages().get(next));
            imageDisplay.setImage(new Image(imageSource + event.getImages().get(next).getChemin()));
            currentImage = event.getImages().get(next);
        } else {
            imageDisplay.setImage(new Image(imageSource + event.getImages().get(0).getChemin()));
            currentImage = event.getImages().get(0);
        }
    }

    @FXML
    void previousImage(MouseEvent ev) {
        int next = event.getImages().indexOf(currentImage) - 1;
        if (next > 0) {
            // System.out.println(imageSource+event.getImages().get(next));
            imageDisplay.setImage(new Image(imageSource + event.getImages().get(next).getChemin()));
            currentImage = event.getImages().get(next);
        } else {
            imageDisplay.setImage(new Image(imageSource + event.getImages().get(0).getChemin()));
            currentImage = event.getImages().get(event.getImages().size() - 1);
        }
    }

    public void initTreeTableView() {

        JFXTreeTableColumn<Creneau, String> dateCreationColumn = new JFXTreeTableColumn<>("Date début");
        dateCreationColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(param.getValue().getValue().getDateDebut().toString());
            return property;
        });
        dateCreationColumn.setPrefWidth(treeView.getPrefWidth() / 3);

        JFXTreeTableColumn<Creneau, String> destinationColumn = new JFXTreeTableColumn<>("Date fin");
        destinationColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(param.getValue().getValue().getDateFin().toString());
            return property;
        });
        destinationColumn.setPrefWidth(treeView.getPrefWidth() / 3);

        JFXTreeTableColumn<Creneau, String> nbPlacesColumn = new JFXTreeTableColumn<>("Description");
        nbPlacesColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Creneau e = (Creneau) param.getValue().getValue();
            property.setValue(String.valueOf(e.getDescription()));
            return property;
        });
        nbPlacesColumn.setPrefWidth(treeView.getPrefWidth() / 3);

        treeView.getColumns().add(dateCreationColumn);
        treeView.getColumns().add(destinationColumn);
        treeView.getColumns().add(nbPlacesColumn);
        ObservableList<Creneau> planning = FXCollections.observableArrayList(event.getPlanning());
        TreeItem<Creneau> root = new RecursiveTreeItem<Creneau>(planning, RecursiveTreeObject::getChildren);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    @FXML
    void updateStars(MouseEvent event) {
        if (!(event.getSource() instanceof ImageView)) {
            return;
        }
        ImageView currentStar = (ImageView) event.getSource();
        int currentStarIndex = stars.indexOf(currentStar);
        for (int i = 0; i <= currentStarIndex; i++) {
            stars.get(i).setImage(fullStar);
        }
        for (int i = currentStarIndex + 1; i < stars.size(); i++) {
            stars.get(i).setImage(emptyStar);
        }
    }

    @FXML
    void onStarClick(MouseEvent e) {
        if (!(e.getSource() instanceof ImageView)) {
            return;
        }
        if (event.getOrganisateur_id().equals(LoginManager.getUser().getId())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Vous ne pouvez pas évaluer votre propre évènement"));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((ev) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else if (!serviceParticipation.aParticipe(LoginManager.getUser().getId(), event.getId())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Vous ne pouvez pas évaluer un évènement auquel vous n'avez pas participé"));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((ev) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else if (event.getDateHeureDepart().after(Timestamp.valueOf(LocalDateTime.now()))) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("L'evenement ne s'est pas encore déroulé"));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((ev) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else {
            ImageView i = (ImageView) e.getSource();
            double newRating = stars.indexOf(i) + 1;
            serviceRating.rate(DetailsController.event, 1, newRating);
            note.setText("Note : " + event.getRating() + "/5 (" + event.getNombreRates() + " votes)");
            Notification.createNotification("Succès", "Merci d'avoir évalué l'évènement");
        }
    }

    @FXML
    void participer(ActionEvent event) {
        Evenement evenement = DetailsController.event;
        if (evenement.getNombrePlaces() - evenement.getNombrePlacesPrises() == 0) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Cet évènement est complet."));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else if (evenement.getDateHeureDepart().toLocalDateTime().isBefore(LocalDateTime.now())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Cet évènement est achevé."));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else if (evenement.getOrganisateur_id().equals(LoginManager.getUser().getId())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Vous ne pouvez pas participer à votre propre évènement"));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        } else if (!serviceParticipation.aParticipe(LoginManager.getUser().getId(), evenement.getId())) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Participation"));
            VBox box = new VBox();
            JFXTextField passCode = new JFXTextField();
            passCode.setPromptText("Passcode");
            passCode.setLabelFloat(true);
            Label l = new Label();
            box.getChildren().add(passCode);
            box.getChildren().add(l);
            content.setBody(box);
            JFXButton okButton = new JFXButton("Payer");

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);
            content.getActions().add(okButton);
            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });
            okButton.setOnAction((e) -> {
                if (passCode.getText().equals(LoginManager.getUser().getCompte().getPasscode())) {
                    if (LoginManager.getUser().getCompte().getSolde() < evenement.getFrais()) {
                        l.setText("Solde insuffisant");
                    } else if (serviceParticipation.participer(LoginManager.getUser().getId(), evenement)) {
                        nbplaces.setText((evenement.getNombrePlaces() - evenement.getNombrePlacesPrises()) + " place(s) displonible(s)");
                        Notification.createNotification("Succès", "Votre réservation a été effectuée avec succès");
                        stackPane.setMouseTransparent(true);
                        dialog.close();
                    } else {
                        l.setText("Une erreur s'est produite, veuillez reessayer utlérieurement");
                    }
                } else {
                    l.setText("Passcode invalide");
                }
            });
            dialog.show();
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));

            content.setBody(new Text("Vous avez déja participé à cet évènement"));

            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);

            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        }
    }

}
