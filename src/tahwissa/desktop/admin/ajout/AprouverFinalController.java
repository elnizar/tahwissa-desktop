/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin.ajout;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entity.Bannir;
import entity.User;
import tahwissa.desktop.user.ProfileController;
import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class AprouverFinalController implements Initializable {

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton valider;
    @FXML
    private JFXTextField motif;
    @FXML
    private JFXTextField date;
    @FXML
    private JFXRadioButton ecrire;
    @FXML
    private JFXTextArea message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ecrire.setOnAction((event) -> {
            message.setVisible(true);
        });

    }

    @FXML
    private void validerButton(ActionEvent event) {
        UserService userservice = new UserService();
        userservice.approuverMembre(email.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le membre est approuver avec succées");
        alert.showAndWait();
        try {
            Stage stage1 = (Stage) valider.getScene().getWindow();
            stage1.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gestion/admin/ajout/Aprouver.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setText(User user, Bannir b) {
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        email.setText(user.getEmail());
        motif.setText(b.getMotif());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String str = form.format(b.getDate()); // or if you want to save it in String str
        date.setText(str);
        System.out.println(b.getMotif());

    }

}
