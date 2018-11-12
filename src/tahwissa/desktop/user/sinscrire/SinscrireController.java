/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.user.sinscrire;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import entity.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import service.*;
import util.MailApi;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class SinscrireController implements Initializable {

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXDatePicker datenaissance;
    @FXML
    private JFXPasswordField mdp;
    @FXML
    private JFXButton sinscrire;
    @FXML
    private JFXRadioButton homme;
    @FXML
    private ToggleGroup group;
    @FXML
    private JFXRadioButton femme;
    @FXML
    private JFXButton retour;
    public static int compteur = 0;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nom.setText(null);
        prenom.setText(null);
        email.setText(null);
        mdp.setText(null);
        DatePicker checkInDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.of(2010, Month.MARCH, 1));
        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(
                                checkInDatePicker.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datenaissance.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void sinscrireButton(ActionEvent event) throws ParseException {
        System.out.println(mdp.getText());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        UserService use = new UserService();
        if (nom.getText() == null || prenom.getText() == null || email.getText() == null || mdp.getText() == null || datenaissance.getValue() == null) {
            alert.setContentText("Chmap Manquant veulliez remplir tout le formulaire");
            alert.showAndWait();

        } else {
            try {
                String nomb = nom.getText();
                String prenomb = prenom.getText();
                String emailb = email.getText();
                String mdpb = mdp.getText();
                //String s = dateb;

                LocalDate local = datenaissance.getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ss = datenaissance.getValue();
                String test = ss.format(formatter);
                java.util.Date jdate = sdf.parse(test);
                java.sql.Date sqldate = new java.sql.Date(jdate.getTime());
                System.out.println(emailb);
                User u = new User(nomb, prenomb, emailb, emailb, mdpb, sqldate);
                UserService usersrvice = new UserService();
                usersrvice.ajouterUser(u, event);
            } catch (IOException ex) {
                Logger.getLogger(SinscrireController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SinscrireController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }



    @FXML
    private void retourButton(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) retour.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gestion/user/login.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();

    }

    public void setText(User u) throws ParseException {

            nom.setText(u.getNom());
            prenom.setText(u.getPrenom());
            email.setText(u.getEmail());
            LocalDate local = datenaissance.getValue();
            datenaissance.setValue(local);

    }

}
