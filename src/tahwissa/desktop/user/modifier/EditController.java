/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.user.modifier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.User;
import tahwissa.desktop.user.ProfileController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import service.UserService;
import sun.plugin2.util.BrowserType;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class EditController implements Initializable {

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXPasswordField mdp;
    @FXML
    private JFXPasswordField mdp1;
    @FXML
    private JFXButton edit;
    @FXML
    private JFXTextArea interet;
    @FXML
    private JFXButton browse;
    @FXML
    private ImageView imageView;
    @FXML
    private Label filesLabel;
    private FileChooser fileChooser;
    private Image image;
    private static File file;
    private static String path;
    @FXML
    private JFXButton retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    @FXML
    private void editButton(ActionEvent event) throws FileNotFoundException {
        UserService userservice = new UserService();
        if ((mdp.getText()).equals(mdp1.getText()) == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Mot de Pass different");
            alert.showAndWait();
        } else {
            User user = new User(User.test, mdp.getText(), interet.getText(), nom.getText(), prenom.getText(), path);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("voulez vous confirmer la modification");
            alert.showAndWait();
            userservice.modifierUser(user);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("modifiction avec succés");
            alert1.showAndWait();
            retourButton(event);

        }

    }

    public void setText(String mail) {
        UserService userservice = new UserService();
        User user = userservice.getUsers(mail);
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        mdp.setText(user.getPassword());
        mdp1.setText(user.getPassword());
        interet.setText(user.getInformationPersonnel());
        path = user.getImage();
        System.out.println(path + "mahyéch nulll");
    }
    private boolean error = false;

    @FXML
    private void browseButton(ActionEvent event) {
        System.out.println("test");
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une image");
        file = fileChoser.showOpenDialog(theStage);
        checkFiles();
        if (error) {
            error = false;
        } else {
            image = new Image(file.toURI().toString(), 100, 150, true, true);
            imageView.setImage(image);
            path = file.toURI().toString();
            System.out.println(path);
        }
    }

    public void checkFiles() {
        if (file == null) {
            error = false;
            return;
        }
        Path srcPath = file.toPath();
        try {
            if (!Files.probeContentType(srcPath).contains("image")) {
                error = true;
            }
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void retourButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) retour.getScene().getWindow();
            stage1.close();
            Parent root = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/user/Profile.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/tahwissa/desktop/user/Profile.fxml"));
//            loader.load();
//          
//            ProfileController display = loader.getController();
//            display.setText(User.test);
//            Parent p = loader.getRoot();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(p));
//            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
