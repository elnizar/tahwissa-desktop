/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entity.User;
import tahwissa.desktop.admin.supprimer.BannedController;
import tahwissa.desktop.user.modifier.EditController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class ProfileController implements Initializable {

    @FXML
    private Text email;
    @FXML
    private Text dateNaissance;
    @FXML
    private ImageView photo;
    @FXML
    private Text interet;
    @FXML
    private JFXButton editer;
    @FXML
    private Text name;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;
    public static String emailParam;
    public static int compteur = 0;
    @FXML
    private JFXButton deconnecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDrawer();
        setText(emailParam);

    }

    public void initDrawer() {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));
            box.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setSidePane(box);

        } catch (IOException ex) {
            ex.printStackTrace();
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
                    ex.printStackTrace();
                }
                drawer.open();
            }
        });
    }

    @FXML
    private void editerButton(ActionEvent event) throws IOException {
        String mail = email.getText();
        UserService userservice = new UserService();
        Stage stage1 = (Stage) editer.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/tahwissa/desktop/user/modifier/edit.fxml"));
        loader.load();
        EditController display = loader.getController();
        display.setText(mail);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
    }

    public void setText(String mail) {
        mail = emailParam;
        System.out.println(mail + "emaiiil");
        UserService userservice = new UserService();
        User user = userservice.getUser(mail);
        name.setText(user.getNom());
        interet.setText(user.getInformationPersonnel());
        email.setText(user.getEmail());
        System.out.println(user.getImage());
        File file = new File(user.getImage());
        Image image = new Image("http://localhost/tahwissa/web/images/profilepics/"+user.getImage());
        photo.setImage(image);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(user.getDateNaissance());
        dateNaissance.setText(s);
    }

    @FXML
    private void deconnecterButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) deconnecter.getScene().getWindow();
            stage1.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tahwissa/desktop/user/login.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
