/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.myevents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entity.Evenement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JTextField;
import service.EvenementService;
import service.EvenementServiceInterface;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;
import tahwissa.desktop.ajouterguide.GuideController;
import tahwissa.desktop.detailsevent.DetailsController;
import tahwissa.desktop.modifierrando.ModifierController;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class MyEventsController implements Initializable {

    private List<Evenement> events;
    private EvenementServiceInterface service = new EvenementService();
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXListView<VBox> listView;

    @FXML
    private StackPane stackPane;

 

    private final String imageSource = "http://localhost/tahwissa/web/images/evenements/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDrawer();
        try {
            events = service.getByUser(LoginManager.getUser().getId());
        } catch (SQLException ex) {
            Logger.getLogger(MyEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshList();
    }

    public void refreshList() {

        listView.getItems().clear();
        events.forEach((t) -> {
            if (t.getImages().isEmpty() == false) {
                HBox item = new HBox();
                VBox details = new VBox();
                details.setAlignment(Pos.CENTER_LEFT);
                details.setSpacing(10);
                item.setSpacing(40);
                Label dateDepart = new Label(t.getDateHeureDepart().toString());
                dateDepart.setFont(new Font("Calibri", 20));
                ImageView dateIcon = new ImageView(new Image("/resources/img/calendar (1).png"));
                dateDepart.setGraphicTextGap(10);
                dateIcon.setFitWidth(20);
                dateIcon.setFitHeight(20);
                dateDepart.setGraphic(dateIcon);
                details.getChildren().add(dateDepart);

                Label destination = new Label(t.getDestination());
                destination.setFont(new Font("Calibri", 20));
                ImageView destinationIcon = new ImageView(new Image("/resources/img/target.png"));
                destination.setGraphicTextGap(10);
                destinationIcon.setFitWidth(20);
                destinationIcon.setFitHeight(20);
                destination.setGraphic(destinationIcon);
                details.getChildren().add(destination);

                Label places = new Label(String.valueOf(t.getNombrePlaces() - t.getNombrePlacesPrises()) + " places disponibles");
                places.setFont(new Font("Calibri", 20));
                ImageView placesIcon = new ImageView(new Image("/resources/img/hiker.png"));
                places.setGraphicTextGap(10);
                placesIcon.setFitWidth(20);
                placesIcon.setFitHeight(20);
                places.setGraphic(placesIcon);
                details.getChildren().add(places);

                Label boosted = new Label();
                if (t.isBoosted()) {
                    boosted.setText("Boosté");
                } else {
                    boosted.setText("Pas encore boosté");
                }
                boosted.setFont(new Font("Calibri", 20));
                ImageView boostedIcon = new ImageView(new Image("/resources/img/star.png"));
                boosted.setGraphicTextGap(10);
                boostedIcon.setFitWidth(20);
                boostedIcon.setFitHeight(20);
                boosted.setGraphic(boostedIcon);
                details.getChildren().add(boosted);

                Label statut = new Label(t.getStatut());
                statut.setFont(new Font("Calibri", 20));
                ImageView statutIcon = new ImageView(new Image("/resources/img/handshake.png"));
                statut.setGraphicTextGap(10);
                statutIcon.setFitWidth(20);
                statutIcon.setFitHeight(20);
                statut.setGraphic(statutIcon);
                details.getChildren().add(statut);

                VBox vb = new VBox();

                ImageView imgv = new ImageView(new Image(imageSource + t.getImages().get(0).getChemin()));
                imgv.setFitWidth(175);
                imgv.setFitHeight(175);
                item.getChildren().add(imgv);
                item.getChildren().add(details);
                vb.getChildren().add(item);
                HBox hb = new HBox();
                ImageView modImage = new ImageView(new Image("/resources/img/edit.png"));

                JFXButton mod = new JFXButton("Modifier");
                modImage.setFitWidth(20);
                modImage.setFitHeight(20);
                mod.setGraphic(modImage);

                ImageView guideImage = new ImageView(new Image("/resources/img/guide.png"));

                JFXButton guideButton = new JFXButton("Guide");
                guideImage.setFitWidth(20);
                guideImage.setFitHeight(20);
                guideButton.setGraphic(guideImage);

                ImageView delImage = new ImageView(new Image("/resources/img/delete.png"));
                JFXButton supp = new JFXButton("Supprimer");
                delImage.setFitWidth(20);
                delImage.setFitHeight(20);
                supp.setGraphic(delImage);
                ImageView detImage = new ImageView(new Image("/resources/img/next.png"));
                JFXButton det = new JFXButton("Détails");
                detImage.setFitWidth(20);
                detImage.setFitHeight(20);
                det.setGraphic(detImage);

                hb.setSpacing(15);
                supp.getStyleClass().add("red-300");
                det.getStyleClass().add("blue-300");
                mod.getStyleClass().add("amber-400");
                guideButton.getStyleClass().add("blue-grey-200");
                JFXButton boost = new JFXButton("Booster");
                ImageView boostImage = new ImageView(new Image("/resources/img/promotion.png"));
                boostImage.setFitHeight(22);
                boostImage.setFitWidth(22);
                boost.setGraphic(boostImage);
                boost.getStyleClass().add("yellow-400");
                hb.getChildren().add(mod);
                hb.getChildren().add(guideButton);
                hb.getChildren().add(boost);
                hb.getChildren().add(supp);
                
                hb.getChildren().add(det);
                
                hb.setAlignment(Pos.TOP_RIGHT);
                details.getChildren().add(hb);
                listView.getItems().add(vb);

                boost.setOnAction((event) -> {
                    Evenement evenement = t;
                    handleBoost(evenement);
                });
                supp.setOnAction((event) -> {
                    Evenement evenement = t;
                    handleDelete(evenement);
                    refreshList();
                });
                mod.setOnAction((event) -> {
                    Evenement evenement = t;
                    handleEdit(event, evenement);
                });
                det.setOnAction((event) -> {
                    Evenement evenement = t;
                    handleDetails(event, evenement);
                });
                guideButton.setOnAction((event) -> {
                    Evenement e = t;
                    GuideController.e = e;
                    goTo(event, "/tahwissa/desktop/ajouterguide/View.fxml");
                });
            }
        });
    }

    public void handleBoost(Evenement evenement) {
        if (evenement.isBoostable()) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Paiement de 20 dinars"));
            VBox box = new VBox();
            JFXTextField passCode = new JFXTextField();
            passCode.setPromptText("Passcode");
            passCode.setLabelFloat(true);
            Label l = new Label();
            box.getChildren().add(passCode);
            box.getChildren().add(l);
            content.setBody(box);
            JFXButton okButton = new JFXButton("Booster");

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
                    if (LoginManager.getUser().getCompte().getSolde() < 20) {
                        l.setText("Solde insuffisant");
                    } else {
                        System.out.println("Evenement boosté");
                        stackPane.setMouseTransparent(true);

                        evenement.setBoosted(true);
                        try {
                            service.update(evenement);
                            Notification.createNotification("Succès", "Votre évènement a été boosté");
                        } catch (SQLException ex) {
                            Logger.getLogger(MyEventsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        dialog.close();
                    }
                } else {
                    l.setText("Passcode invalide");
                }
            });
            dialog.show();
        } else {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));
            content.setBody(new Text("Vous ne pouvez pas booster cet évènement"));
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

    public void handleEdit(ActionEvent event, Evenement evenement) {

        if (evenement.getStatut().equals("Accepté")) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));
            content.setBody(new Text("Vous ne pouvez pas modifier cet évènement"));
            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);
            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });
            dialog.show();
        } else {
            ModifierController.event = evenement;
            goTo(event, "/tahwissa/desktop/modifierrando/View.fxml");
        }
    }

    public void handleDetails(ActionEvent event, Evenement evenement) {

        DetailsController.event = evenement;
        goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
    }

    public void handleDelete(Evenement evenement) {

        if (evenement.getStatut().equals("Accepté")) {
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Erreur"));
            content.setBody(new Text("Vous ne pouvez pas supprimer cet évènement"));
            JFXButton delButton = new JFXButton("Fermer");
            content.getActions().add(delButton);
            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });
            dialog.show();
        } else {
            // System.out.println("test");
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Confirmation"));
            content.setBody(new Text("Voulez-vous supprimer cet évènement ?"));
            JFXButton delButton = new JFXButton("Supprimer");
            JFXButton closeButton = new JFXButton("Annuler");
            content.getActions().add(delButton);
            content.getActions().add(closeButton);
            stackPane.setMouseTransparent(false);
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            delButton.setOnAction((e) -> {
                Evenement ev = evenement;
                service.supprimerEvenement(ev);
                stackPane.setMouseTransparent(true);
                Notification.createNotification("Succès", "Votre évènement a été supprimé");
                events.remove(ev);
                dialog.close();
            });

            closeButton.setOnAction((e) -> {
                stackPane.setMouseTransparent(true);
                dialog.close();
            });

            dialog.show();
        }
    }

    public void goTo(ActionEvent ev, String location) {
        try {

            Parent test = FXMLLoader.load(getClass().getResource(location));
            StackPane splashScreen = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/splashScreen.fxml"));

            if (test instanceof Pane) {
                Pane p = (Pane) test;
                splashScreen.setPrefSize(p.getPrefWidth(), p.getPrefHeight());
                ImageView bg = (ImageView) splashScreen.getChildren().get(0);
                bg.setFitWidth(p.getPrefWidth());
                bg.setFitHeight(p.getPrefHeight());
            } else {
                ScrollPane p = (ScrollPane) test;
                splashScreen.setPrefSize(p.getPrefWidth(), p.getPrefHeight());
                ImageView bg = (ImageView) splashScreen.getChildren().get(0);
                bg.setFitWidth(p.getPrefWidth());
                bg.setFitHeight(p.getPrefHeight());
            }

            splashScreen.setAlignment(Pos.CENTER);
            Node node = (Node) ev.getSource();
            Scene scenee = new Scene(splashScreen);
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scenee);

            FadeTransition ft = new FadeTransition(Duration.millis(1500), splashScreen.getChildren().get(1));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.setAutoReverse(true);

            FadeTransition ft2 = new FadeTransition(Duration.millis(1500), splashScreen.getChildren().get(1));
            ft2.setFromValue(0);
            ft2.setToValue(1);
            ft2.setCycleCount(1);
            ft2.setAutoReverse(true);
            ft.setOnFinished((event) -> {
                ft2.play();
            });
            ft2.setOnFinished((event) -> {

                Scene scene = new Scene(test);

                stage.setScene(scene);

            });
            ft.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
