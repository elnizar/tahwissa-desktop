/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin.ajout;

import entity.Bannir;
import entity.User;
import tahwissa.desktop.admin.AfficheController;
import tahwissa.desktop.admin.ajout.*;
import tahwissa.desktop.admin.supprimer.BannedController;
import tahwissa.desktop.user.ProfileController;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class AprouverController implements Initializable {

    @FXML
    private TableView<User> tableUser;
    @FXML
    private TextField recherche;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateNaissance;
    @FXML
    private TextField password;
    @FXML
    private RadioButton homme;
    @FXML
    private ToggleGroup groupe;
    @FXML
    private RadioButton femme;
    @FXML
    private Button approuver;
    @FXML
    private TableColumn<?, ?> identifiant;
    @FXML
    private Button charger;
    @FXML
    private TableColumn<?, ?> Nom;
    @FXML
    private TableColumn<?, ?> Prenom;
    @FXML
    private TableColumn<?, ?> Email;
    @FXML
    private TableColumn<?, ?> Banned;
    @FXML
    private Button banner;
    @FXML
    private Button deconnecter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UserService userservice = new UserService();
        ObservableList data = userservice.getUserbanned();
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
        sortedData.comparatorProperty().bind(tableUser.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableUser.setItems(sortedData);

        });
        
        tableUser.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    int id = tableUser.getSelectionModel().getSelectedItem().getId();
                    User user2 = userservice.getUserByID(id);
                    Bannir banned = userservice.getBannedByID(id);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/gestion/admin/ajout/AprouverFinal.fxml"));
                    loader.load();
                    AprouverFinalController display = loader.getController();
                    display.setText(user2,banned);
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
        tableUser.setItems(userservice.getUserbanned());
    }

    @FXML
    private void bannerButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) deconnecter.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gestion/admin/affiche.fxml"));
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
    private void deconnecterButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) deconnecter.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gestion/user/login.fxml"));
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
