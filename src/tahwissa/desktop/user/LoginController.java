/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import entity.Bannir;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import service.TestService;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField mdp;
    @FXML
    private JFXButton connexion;
    @FXML
    private JFXButton signup;
    @FXML
    private ImageView sinsfb;
    @FXML
    private JFXButton fb;
    private Task<User> task = new Task<User>() {
        @Override
        protected User call() throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void connexionButton(ActionEvent event) throws IOException, ParseException {
        String utilisateur = user.getText();
        String motdePass = mdp.getText();
        String s = "a:1:{i:0;s:10:\"ROLE_ADMIN\";}";
        UserService userservice = new UserService();
     //   User user = userservice.testConnexion(utilisateur, motdePass);
     
        User user = TestService.getUserByMail(utilisateur, motdePass);
        if (user != null) {

            int id = userservice.getIdUSer(utilisateur);
            Bannir banned = userservice.getBannedByID(id);
            if (banned != null) {
                System.out.println("manich houni");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dateobj = new java.util.Date(System.currentTimeMillis());
                String datestr = df.format(dateobj);
                java.util.Date d = df.parse(datestr);

                java.util.Date dateobjj = banned.getDate();
                String datestrr = df.format(dateobjj);
                java.util.Date dd = df.parse(datestrr);
                System.out.println(user.getRoles() + " test");

                if (d.after(dd) || d.equals(dd)) {
                    userservice.approuverMembre(user.getEmail());
                    user = userservice.testConnexion(utilisateur, motdePass);
                    userservice.supprimerBann(id);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("mrigel");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Vous etes banni jusqu au " + banned.getDate() + " merci de patientez");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("login.fxml"));
                    loader.load();
                    ProfileController display = loader.getController();
                    display.setText(utilisateur);
                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
                }

            }
            if (user.getApprouver() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Veuillez confirmez votre inscripton");
                alert.showAndWait();
            }
            if (user.getRoles().equals(s)) {
                FXMLLoader loader = new FXMLLoader();
                System.out.println("éni houni");
                loader.setLocation(getClass().getResource("/tahwissa/desktop/admin/affiche.fxml"));
                loader.load();
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
                Stage stage1 = (Stage) connexion.getScene().getWindow();
                stage1.close();

            }
            if (user.getBanned() == 0 && user.getApprouver() == 1 && !user.getRoles().equals(s)) {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("Profile.fxml"));
//                loader.load();
//                ProfileController display = loader.getController();
//                display.setText(utilisateur);
//                Parent p = loader.getRoot();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(p));
//                stage.show();

                ProfileController.emailParam = user.getEmail();
                goTo(event, "/tahwissa/desktop/user/Profile.fxml");
         //       Stage stage1 = (Stage) connexion.getScene().getWindow();
//                stage1.hide();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Mot de Pass ou Email Incorrect");
            alert.showAndWait();
        }
    }

    @FXML
    private void signupButton(ActionEvent event) throws Exception {
        Stage stage1 = (Stage) signup.getScene().getWindow();
        stage1.close();
        Parent root = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/user/sinscrire/sinscrire.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void fbButton(ActionEvent event) throws IOException, InterruptedException {
        Stage stage1 = (Stage) fb.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("signupFacebbok.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();

    }

    public void appel(String nn, String emm, String dtt) throws IOException {

    }

    public void goTo(ActionEvent ev, String location) {
        try {

            Parent test = FXMLLoader.load(getClass().getResource(location));
            StackPane splashScreen = FXMLLoader.load(getClass().getResource("splashScreen.fxml"));

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
