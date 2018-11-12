/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.inbox;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.mysql.jdbc.MySQLConnection;
import com.sun.jndi.ldap.Connection;
import entity.Messagerie;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.MessagerieService;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;
import tahwissa.desktop.envoi.Envoie;
import util.MyConnection;

/**
 * FXML Controller class
 *
 * @author User
 */
public class View_InboxController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
//    @FXML
//    private JFXButton nouveau;
    private User destinataire;
    @FXML
    private TableView<Messagerie> table;
    @FXML
    private TableColumn<Messagerie, Timestamp> date;
    @FXML
    private TableColumn<Messagerie, String> messages;
    @FXML
    private JFXButton supprimer;
    @FXML
    private JFXButton repondre;
    @FXML
    private JFXButton redi;
    private MessagerieService serviceMessagerie = new MessagerieService();
    @FXML
    private JFXListView<HBox> listView;
    @FXML
    private JFXListView<HBox> conversation;

    @FXML
    private JFXTextField contenuMsg;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXButton envoyer;
    List<Messagerie> messagesList;
    private int user_id ;
    Map<User, Messagerie> messagesMap;

   

    private void handleButtonAction(ActionEvent event) {
        // System.out.println("You clicked me!");
//        label.setText("Hello World!");
    }

    public void threadTest() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user_id = LoginManager.getUser().getId();
        initDrawer();
        repondre.setOnAction((e) -> {
            contenuMsg.setVisible(true);
            envoyer.setVisible(true);
        });
        Cn = new MyConnection();
        loadMessages();

        new Timer().schedule(
                new TimerTask() {

            @Override
            public void run() {
                System.out.println("running");
//                loadMessages();
                if (destinataire == null) {
                    return;
                }
                //conversation.getItems().clear();
                // System.out.println(messagesList.size());
                List<Messagerie> newmessagesList = serviceMessagerie.getConversation(user_id, destinataire.getId());
                //  // System.out.println(newmessagesList.size());
                for (int cmpt = 0; cmpt < newmessagesList.size(); cmpt++) {
                    Messagerie msg = newmessagesList.get(cmpt);
                    // // System.out.println(msg.isDeletedSender());
                    if ((msg.isDeletedReciever()) && (msg.getReciever_id() == user_id)) {
                        continue;
                    }
                    if ((msg.isDeletedSender()) && (msg.getSender_id() == user_id)) {
                        continue;
                    }
                    //System.out.println(msg);
                    if (messagesList.contains(msg)) {
                        continue;
                    }
                    HBox hb = new HBox();
                    StackPane p = new StackPane();
                    ImageView i = new ImageView(new Image("/resources/img/messageRecieved.png"));
                    i.setFitWidth(257);
                    i.setFitHeight(102);

                    Text text = new Text(msg.getContenuMsg());
                    HBox hb2 = new HBox();
                    if (msg.getSender_id() == 1) {
                        hb.setAlignment(Pos.TOP_LEFT);
                        i.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                        i.setEffect(new ColorAdjust(0, 0, 0.42, -1));
                        hb2.setAlignment(Pos.BOTTOM_LEFT);
                    } else {
                        hb.setAlignment(Pos.TOP_RIGHT);
                        i.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                        hb2.setAlignment(Pos.BOTTOM_RIGHT);
                    }

                    p.getChildren().add(i);
                    LocalDateTime datetime = msg.getDateHeureEnvoi().toLocalDateTime();
                    Text dateEnvoi = new Text("Le " + datetime.toLocalDate() + " à " + datetime.toLocalTime());

                    p.getChildren().add(text);

                    hb2.getChildren().add(dateEnvoi);
                    p.getChildren().add(hb2);

                    hb.getChildren().add(p);
                    System.out.println("new message");
                    conversation.getItems().add(0, hb);
                    messagesList.add(msg);
                }
            }
        }, 0, 5000);
    }

    public void initDrawer() {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));
            box.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setPrefHeight(anchorPane.getPrefHeight());
            drawer.setSidePane(box);

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            //// System.out.println(drawer);
            if (drawer.isShown()) {
                drawer.close();
                drawer.setSidePane(new VBox());
            } else {
                try {
                    VBox box = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/DrawerContent.fxml"));

                    drawer.setSidePane(box);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                drawer.open();
            }
        });
    }

    public void loadMessages() {
        listView.getItems().clear();
        envoyer.setVisible(false);
        contenuMsg.setVisible(false);
        repondre.setVisible(false);
        supprimer.setVisible(false);
        messagesMap = serviceMessagerie.getDiscussions(user_id);
        for (Map.Entry<User, Messagerie> e : messagesMap.entrySet()) {
            // // System.out.println(e.getKey().getId() + " : " + e.getValue().isDeletedSender());
            if ((e.getValue().isDeletedReciever()) && (e.getValue().getReciever_id() == user_id)) {
                continue;
            }
            if ((e.getValue().isDeletedSender()) && (e.getValue().getSender_id() == user_id)) {
                continue;
            }
            HBox item = new HBox();
            VBox userInfo = new VBox();
            userInfo.setAlignment(Pos.CENTER);
            ImageView image = new ImageView("/resources/img/profile.png");
            Separator sp = new Separator(Orientation.VERTICAL);
            image.setFitWidth(50);
            image.setFitHeight(50);
            Label userName = new Label(e.getKey().getNom() + " " + e.getKey().getPrenom());
            userInfo.getChildren().add(image);
            userInfo.getChildren().add(userName);

            VBox messageInfo = new VBox();
            messageInfo.setAlignment(Pos.CENTER);
            messageInfo.setSpacing(20);
            Label dateHeure = new Label(e.getValue().getDateHeureEnvoi().toString());
            Text contenu = new Text(e.getValue().getContenuMsg());
            messageInfo.getChildren().add(dateHeure);
            messageInfo.getChildren().add(contenu);

            item.getChildren().add(userInfo);
            item.getChildren().add(sp);
            item.getChildren().add(messageInfo);

            listView.getItems().add(item);
        }

        listView.setOnMouseClicked((t) -> {
            if (conversation.getItems() != null) {
                conversation.getItems().clear();
            }
            repondre.setVisible(true);
            supprimer.setVisible(true);
            envoyer.setVisible(false);
            contenuMsg.setText("");
            contenuMsg.setVisible(false);
            User u = (User) messagesMap.keySet().toArray()[listView.getSelectionModel().getSelectedIndex()];
            destinataire = u;
            messagesList = serviceMessagerie.getConversation(user_id, u.getId());
            // // System.out.println(messagesList.size());
            messagesList.forEach((msg) -> {
                // // System.out.println(msg.isDeletedSender());
                if ((msg.isDeletedReciever()) && (msg.getReciever_id() == user_id)) {
                    return;
                }
                if ((msg.isDeletedSender()) && (msg.getSender_id() == user_id)) {
                    return;
                }
                HBox hb = new HBox();
                StackPane p = new StackPane();
                ImageView i = new ImageView(new Image("/resources/img/messageRecieved.png"));
                i.setFitWidth(257);
                i.setFitHeight(102);

                Text text = new Text(msg.getContenuMsg());
                HBox hb2 = new HBox();
                if (msg.getSender_id() == 1) {
                    hb.setAlignment(Pos.TOP_LEFT);
                    i.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    i.setEffect(new ColorAdjust(0, 0, 0.42, -1));
                    hb2.setAlignment(Pos.BOTTOM_LEFT);
                } else {
                    hb.setAlignment(Pos.TOP_RIGHT);
                    i.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                    hb2.setAlignment(Pos.BOTTOM_RIGHT);
                }

                p.getChildren().add(i);
                LocalDateTime datetime = msg.getDateHeureEnvoi().toLocalDateTime();
                Text dateEnvoi = new Text("Le " + datetime.toLocalDate() + " à " + datetime.toLocalTime());

                p.getChildren().add(text);

                hb2.getChildren().add(dateEnvoi);
                p.getChildren().add(hb2);

                hb.getChildren().add(p);
                conversation.getItems().add(hb);
            });
        });

    }

    public void refreshDiscussion() {

    }
    private ObservableList<Messagerie> data;
    private MyConnection Cn;

    @FXML
    public void redirection(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/envoi/Envoi.fxml"));
        Scene scene = new Scene(root);

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.setScene(scene);

        app_stage.show();
         */
        Envoie e = new Envoie();
        try {
            e.start(new Stage());
        } catch (Exception ex) {
            Logger.getLogger(View_InboxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LoadMessage(ActionEvent event) {
        loadMessages();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        Messagerie m = new Messagerie();
        m.setSender_id(user_id);
        m.setReciever_id(destinataire.getId());
        m.setDateHeureEnvoi(Timestamp.valueOf(LocalDateTime.now()));
        m.setContenuMsg(contenuMsg.getText());
        serviceMessagerie.ajouterMessage(m);

        contenuMsg.setText("");
    }

    @FXML
    public void deleteConversation(ActionEvent event) {
        //  // System.out.println("deleting");
        serviceMessagerie.supprimerConversation(user_id, destinataire.getId(), user_id);
        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
        listView.getSelectionModel().selectFirst();
        messagesMap.remove(destinataire);
        conversation.getItems().clear();

    }
}
