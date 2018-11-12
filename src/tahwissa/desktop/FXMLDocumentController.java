/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import entity.Evenement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.EvenementService;
import service.EvenementServiceInterface;
import tahwissa.desktop.detailsevent.DetailsController;
import tahwissa.desktop.mapDisplay.MapController;
import tahwissa.desktop.mapDisplay.MapPlace;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    protected JFXHamburger hamburger;

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    protected JFXDrawer drawer;
    
    
    @FXML
    private GridPane gridPane;

    
     EvenementServiceInterface serviceEvent = new EvenementService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            initDrawer();
            List<Evenement> events = serviceEvent.getAvailable();
            List<Evenement> toDelete = new ArrayList<Evenement>();

            events.forEach((t) -> {
                if (t.getImages().isEmpty()) {
                    toDelete.add(t);
                }
            });
            toDelete.forEach((t) -> {
                events.remove(t);
            });
            for (int i = 0; i == -1; i++) {
                Evenement e = events.get(i);
                HBox box = new HBox();
                box.setSpacing(10);
                box.setPrefHeight(150);
                box.setPrefWidth(157);
                ImageView imgView = new ImageView(new Image("http://localhost/tahwissa-web/web/images/evenements/" + e.getImages().get(0).getChemin()));
                imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, (evt) -> {
                    DetailsController.event= e;
                    goTo(evt, "/tahwissa/desktop/detailsevent/View.fxml");
                });
                imgView.setFitHeight(150);
                imgView.setFitWidth(157);
                box.getChildren().add(imgView);
                VBox infos = new VBox();

                ImageView destGraphic = new ImageView(new Image("/resources/img/marker.png"));
                destGraphic.setFitHeight(25);
                destGraphic.setFitWidth(25);
                Label destination = new Label(e.getDestination());
                destination.setGraphic(destGraphic);
                destination.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                    try {
                        MapController.e= e;
                        MapPlace m = new MapPlace();
                        m.start(new Stage());
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                infos.getChildren().add(new Label(e.getEvenementType()));

                infos.getChildren().add(destination);

                ImageView priceGraphic = new ImageView(new Image("/resources/img/price-tag.png"));
                priceGraphic.setFitHeight(25);
                priceGraphic.setFitWidth(25);
                Label priceLabel = new Label(e.getFrais() + " dinars");
                priceLabel.setGraphic(priceGraphic);

                infos.getChildren().add(priceLabel);

                ImageView placesGraphic = new ImageView(new Image("/resources/img/hikingman.png"));
                placesGraphic.setFitHeight(25);
                placesGraphic.setFitWidth(25);
                Label placesLabel = new Label((e.getNombrePlaces() - e.getNombrePlacesPrises()) + " places");
                placesLabel.setGraphic(placesGraphic);

                infos.getChildren().add(placesLabel);

                infos.setSpacing(5);
                infos.setAlignment(Pos.CENTER);
                infos.getChildren().add(new Label(e.getDateHeureDepart().toString()));
                box.getChildren().add(infos);
                gridPane.add(box, 0, i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initDrawer() {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));
            box.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setSidePane(box);
            System.out.println("Drawer Lancé");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawer.open();
            }
        });

    }
    
     public void goTo(MouseEvent ev, String location) {
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
