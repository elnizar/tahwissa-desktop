/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin.supprimer;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import entity.Bannir;
import entity.User;
import tahwissa.desktop.user.ProfileController;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class BannedController implements Initializable {

    @FXML
    private JFXDatePicker dateChoix;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private Button valider;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label email;
    @FXML
    private JFXTextArea motifText;
    @FXML
    private Label tt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         DatePicker checkInDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(
                                checkInDatePicker.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        dateChoix.setDayCellFactory(dayCellFactory);
        motifText.setText(null);
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("mauvais comportement ");
        list.add("probléme avec les membres");
        list.add("probléme de boisson ");
        list.add("Other");
        select.setValue("rien de rien");
        tt.setText("rien de rien");
        select.setItems(list);
        select.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    tt.setText(newValue);
                    if (newValue == "Other") {
                        motifText.setVisible(true);
                        tt.setText("Autre");
                    } else {
                        motifText.setVisible(false);
                        tt.setText(newValue);
                    }
                });

        select.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(tt.getText());
            }
        });

    }

    @FXML
    private void validerButton(ActionEvent event) throws ParseException {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateobj = new java.util.Date(System.currentTimeMillis());
        String datestr = df.format(dateobj);
        java.util.Date startDate = df.parse(datestr);
        java.util.Date jdate = null;
        java.sql.Date sqldate = null;
        try {
            LocalDate local = dateChoix.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ss = dateChoix.getValue();
            String test = ss.format(formatter);
            jdate = sdf.parse(test);
            sqldate = new java.sql.Date(jdate.getTime());
        } catch (Exception e) {

        }
        if (jdate == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur Champ date manquant");
                alert.show();
        }
        else if (jdate.before(startDate) || jdate.equals(startDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("éoo vérifier la date");
                alert.show();
            }
            else if ((tt.getText() != "Autre") || (motifText.getText() != null)) {
                System.out.println(motifText.getText());
                System.out.println(tt.getText());
                UserService userservice = new UserService();
                User u = userservice.getUserByID(2);
                int ident = userservice.getIdUSer(email.getText());
                System.out.println(email.getText());
                Bannir b = new Bannir(ident, tt.getText(), sqldate, u.getNom(), u.getPrenom(),2,1,"test");
                userservice.ajouterBanned(2, b);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setContentText("Le membre est banni avec succées");
                al.showAndWait();
                try {
            Stage stage1 = (Stage) valider.getScene().getWindow();
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
         else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erreur Chmap manquant");
                alert.show();
            }
    }

    public void setText(User user) {
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        email.setText(user.getEmail());

    }

}
