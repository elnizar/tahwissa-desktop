/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.ajouterguide;

import tahwissa.desktop.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import entity.Evenement;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import service.EvenementService;
import service.EvenementServiceInterface;
import service.UserService;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class GuideController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTextField input;

    private List<User> users;
    private List<String> userNames = new ArrayList<>();
    private UserService serviceUser = new UserService();
    private EvenementServiceInterface serviceEvent= new EvenementService();
    public static Evenement e;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDrawer();
        System.out.println("here");
        users = serviceUser.findAll();
        users.forEach((t) -> {
            userNames.add(t.getNom() + " " + t.getPrenom() + " ( " + t.getEmail() + " )");
            if (t.getId().equals(e.getGuide_id())){
                input.setText(t.getNom() + " " + t.getPrenom() + " ( " + t.getEmail() + " )");
            }
            //System.out.println("here");
        });

        TextFields.bindAutoCompletion(input, (param) -> {
            return userNames.stream().filter((t) -> {
                return t.toLowerCase().contains(param.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });

    }

    @FXML
    void submit(ActionEvent event) {
        int index= userNames.indexOf(input.getText());
        if (index!=-1){
            User guide = users.get(index);
            e.setGuide_id(guide.getId());
            try {
                serviceEvent.update(e);
                Notification.createNotification("Succès", "Le guide "+guide.getNom()+" "+guide.getPrenom()+" a bien été affecté à votre évènement");
                goTo(event, "/tahwissa/desktop/myevents/View.fxml");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    public void initDrawer() {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));
            box.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(GuideController.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(GuideController.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawer.open();
            }
        });
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
