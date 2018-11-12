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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JTextField;
import service.EvenementService;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;
import tahwissa.desktop.detailsevent.DetailsController;
import tahwissa.desktop.modifierrando.ModifierController;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class MyEventsController1 implements Initializable {

    private List<Evenement> events;
    private EvenementService service = new EvenementService();
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXTreeTableView<Evenement> treeView;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            events = service.getByUser(LoginManager.getUser().getId());
        } catch (SQLException ex) {
            Logger.getLogger(MyEventsController1.class.getName()).log(Level.SEVERE, null, ex);
        }
        initDrawer();
        System.out.println(treeView);
        initTreeTableView();
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

    public void initTreeTableView() {

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
        statutColumn.setPrefWidth(160);

        JFXTreeTableColumn<Evenement, String> typeColumn = new JFXTreeTableColumn<>("Type");
        typeColumn.setCellValueFactory((param) -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Evenement e = (Evenement) param.getValue().getValue();
            property.setValue(e.getEvenementType());
            return property;
        });
        typeColumn.setPrefWidth(160);

        treeView.getColumns().add(dateCreationColumn);
        treeView.getColumns().add(typeColumn);
        treeView.getColumns().add(destinationColumn);
        treeView.getColumns().add(nbPlacesColumn);
        treeView.getColumns().add(statutColumn);

        ObservableList<Evenement> evenements = FXCollections.observableArrayList(events);
        TreeItem<Evenement> root = new RecursiveTreeItem<Evenement>(evenements, RecursiveTreeObject::getChildren);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        treeView.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
            if (event.getCode().toString().equals("DELETE")) {
                Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
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
                        Evenement ev = treeView.getSelectionModel().getSelectedItem().getValue();
                        service.supprimerEvenement(ev);
                        stackPane.setMouseTransparent(true);
                        Notification.createNotification("Succès", "Votre évènement a été supprimé");
                        dialog.close();
                        refreshTreeTable();

                    });

                    closeButton.setOnAction((e) -> {
                        stackPane.setMouseTransparent(true);
                        dialog.close();
                    });

                    dialog.show();
                }
            } else if (event.getCode().toString().equals("E")) {
                Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
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
            } else if (event.getCode().toString().equals("ENTER")) {
                Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
                DetailsController.event = evenement;
                goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
            } else if (event.getCode().toString().equals("B")) {
                Evenement evenement = treeView.getSelectionModel().getSelectedItem().getValue();
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
                                    Logger.getLogger(MyEventsController1.class.getName()).log(Level.SEVERE, null, ex);
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

    public void goTo(KeyEvent ev, String location) {
       try {

            Parent test = FXMLLoader.load(getClass().getResource(location));
            StackPane splashScreen = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/splashScreen.fxml"));
            

            if (test instanceof Pane) {
                Pane p = (Pane) test;
                splashScreen.setPrefSize(p.getPrefWidth(), p.getPrefHeight());
                ImageView bg = (ImageView) splashScreen.getChildren().get(0);
                bg.setFitWidth(p.getPrefWidth()); bg.setFitHeight(p.getPrefHeight());
            } else {
                ScrollPane p = (ScrollPane) test;
                splashScreen.setPrefSize(p.getPrefWidth(), p.getPrefHeight());
                ImageView bg = (ImageView) splashScreen.getChildren().get(0);
                bg.setFitWidth(p.getPrefWidth()); bg.setFitHeight(p.getPrefHeight());
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

            FadeTransition ft2 = new FadeTransition(Duration.millis(1500),  splashScreen.getChildren().get(1));
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
