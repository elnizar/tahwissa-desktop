/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import entity.User;
import tahwissa.desktop.admin.supprimer.BannedController;
import tahwissa.desktop.user.ProfileController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class AfficheController implements Initializable {

    @FXML
    private TableColumn<?, ?> identifiant;
    @FXML
    private TableColumn<?, ?> Nom;
    @FXML
    private TableColumn<?, ?> Prenom;
    @FXML
    private TableColumn<?, ?> Email;
    @FXML
    private TableView<User> tableview;
    @FXML
    private Button deconnecter;
    @FXML
    private JFXTextField recherche;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXRadioButton femme;
    @FXML
    private JFXRadioButton homme;
    @FXML
    private JFXButton approuver;
    @FXML
    private JFXButton charger;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXDatePicker dateNaissance;
    @FXML
    private Button iapprouver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                UserService userservice = new UserService();
        identifiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
                ObservableList data = userservice.getAllUser();
        FilteredList<User> filtredData = new FilteredList<>(data, e -> true);
        recherche.setOnKeyReleased(e -> {
            recherche.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filtredData.setPredicate(user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (user.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                    return false;
                });
            });
                    SortedList<User> sortedData = new SortedList<>(filtredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableview.setItems(sortedData);

        });
        
        tableview.setItems(userservice.getAllUser());
        tableview.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    int id = tableview.getSelectionModel().getSelectedItem().getId();
                    User user2 = userservice.getUserByID(id);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/tahwissa/desktop/admin/supprimer/Banned.fxml"));
                    loader.load();
                    BannedController display = loader.getController();
                    display.setText(user2);
                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AfficheController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    private void deconnecterButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) deconnecter.getScene().getWindow();
            stage1.close();
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

    @FXML
    private void approuverButton(ActionEvent event) throws ParseException {
         String nomb = nom.getText();
        String prenomb = prenom.getText();
        String emailb = email.getText();
        LocalDate local = dateNaissance.getValue();
        String mdpb = password.getText();
        //String s = dateb;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ss = dateNaissance.getValue();
        String test = ss.format(formatter);
        java.util.Date jdate = sdf.parse(test);
        java.sql.Date sqldate = new java.sql.Date(jdate.getTime());
        User u = new User(nomb, prenomb, emailb, emailb, mdpb, sqldate);
        UserService usersrvice = new UserService();
        usersrvice.ajouterPersonne(u);
    }

    @FXML
    private void chargerButton(ActionEvent event) {
        identifiant.setCellValueFactory(new PropertyValueFactory<>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));

        UserService userservice = new UserService();
        tableview.setItems(userservice.getAllUser());
    }

    @FXML
    private void iapprouverButton(ActionEvent event) {
                try {
            Stage stage1 = (Stage) deconnecter.getScene().getWindow();
            stage1.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tahwissa/desktop/admin/ajout/Aprouver.fxml"));
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
