/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import service.UserService;
import util.MyConnection;
//import utils.ConnextionSingleton;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class SignupFacebbokController implements Initializable {

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton envoyer;
    @FXML
    private JFXTextField prenom;
    public static int i = 0;
    @FXML
    private JFXDatePicker dd;
    @FXML
    private JFXButton retour;
    Connection con = MyConnection.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        dd.setDayCellFactory(dayCellFactory);
    }
    Task<User> task = new Task<User>() {
        @Override
        protected User call() throws Exception {
            System.out.println("houniii");
            String domain = "http://localhost/";
            String appId = "1578414245504369";
            String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + appId + "&redirect_uri=" + domain + "&scope=user_about_me,"
                    + "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
                    + "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
                    + "manage_pages,publish_actions,read_insights,read_page_mailboxes,rsvp_event";

            System.setProperty("webdirver.chrome.driver", "chromedriver.exe");

            WebDriver driver = new ChromeDriver();
            driver.get(authUrl);
            String accessToken;

            while (true) {

                if (!driver.getCurrentUrl().contains("facebook.com")) {

                    String url1 = driver.getCurrentUrl();
                    accessToken = url1.replaceAll(".*#access_token=(.+)&.*", "$1");

                    driver.quit();

                    FacebookClient fbClient = new DefaultFacebookClient(accessToken);

                    com.restfb.types.User user1 = fbClient.fetchObject("me", com.restfb.types.User.class, Parameter.with("fields", "email"));
                    Thread.sleep(5000);
                    dd.setValue(LocalDate.of(1993, 9, 30));
                    email.setText(user1.getEmail());
                    nom.setText("Nizar");
                    prenom.setText("Elhraiech");
                }

            }
        }

    };

    @FXML
    private void envoyerButton(ActionEvent event) throws ParseException, IOException {
        if (i == 0) {
            Thread t = new Thread(task);
            t.run();
            i++;
        } else {
            try {
                String vemail = null;
                Statement ste = con.createStatement();
                String req1 = "SELECT * FROM `fos_user` WHERE email_canonical = '" + email.getText() + "'";
                ResultSet res = ste.executeQuery(req1);
                while (res.next()) {
                    vemail = res.getString("email_canonical");
                }
                if (vemail == null) {
                    UserService userservice = new UserService();
                    LocalDate local = dd.getValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate ss = dd.getValue();
                    String test = ss.format(formatter);
                    java.util.Date jdate = sdf.parse(test);
                    java.sql.Date sqldate = new java.sql.Date(jdate.getTime());
                    entity.User user = new entity.User(nom.getText(), prenom.getText(), email.getText(), email.getText(), password.getText(), sqldate);
                    userservice.ajouterPersonne(user);
                    userservice.approuverMembre(email.getText());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("connecter avec succès");
                    alert.showAndWait();
                    Stage stage1 = (Stage) retour.getScene().getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("login.fxml"));
                    loader.load();
                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Votre email existe déja");
                    alert.showAndWait();
                }

            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        i++;
    }

    @FXML
    private void retourButton(ActionEvent event) {
        try {
            Stage stage1 = (Stage) retour.getScene().getWindow();
            stage1.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignupFacebbokController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
