/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.mes_achats;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Achat;
import entity.EtatAchat;
import entity.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.AchatService;
import service.ArticleService;
import service.TestService;
import tahwissa.desktop.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class AchatsController extends FXMLDocumentController {

    @FXML
    private JFXTreeTableView<Achat> achatsTableView;


    @FXML
    private TreeTableColumn<Achat, Label> libelle_col;

    @FXML
    private TreeTableColumn<Achat, Label> prix_col;

    @FXML
    private TreeTableColumn<Achat, Label> statut_col;

    @FXML
    private TreeTableColumn<Achat, Label> date_col;

    @FXML
    private TreeTableColumn<Achat, JFXButton> reclamer_col;

    @FXML
    private TreeTableColumn<Achat, JFXButton> accuser_col;
    
        @FXML
    private StackPane dialogPane;
    
    private AchatService serviceAchat;
    private User user;
    private List<Achat> list_achats;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        super.initDrawer();
        
        serviceAchat = AchatService.getAchatService();
        user = TestService.getMembre();
        System.out.println("ID Memebre: "+user.getId());
        list_achats = serviceAchat.findByMembre(user.getId());
        initAchatsTable();
    }  
    
    public void initAchatsTable() {
       
       
            
            libelle_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<Label> property = new SimpleObjectProperty<>();
                Label label = new Label(achat.getArticle().getLibelle());
                
                property.set(label);
                return property;
            });
            
            prix_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<Label> property = new SimpleObjectProperty<>();
                Label label = new Label(String.valueOf(achat.getArticle().getPrix()));
                
                
                property.set(label);
                return property;
            });
            
            date_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<Label> property = new SimpleObjectProperty<>();
                Label label = new Label(achat.getDateHeureAchat().toString());
                
                label.setId("date_label");
                label.getStylesheets().add("date_label");
                 label.setTextFill(Color.web("#ECF0F1"));
                property.set(label);
                return property;
            });
            
            statut_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<Label> property = new SimpleObjectProperty<>();
                Label label = new Label(achat.getStatut());
                
                label.setId("statut_label");
                 label.getStylesheets().add("statut_label");
                  label.setTextFill(Color.web("#ECF0F1"));
                property.set(label);
                return property;
            });
            
            reclamer_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<JFXButton> property = new SimpleObjectProperty<>();
                JFXButton btn = new JFXButton("Reclamer");
                if (achat.getStatut().equals(EtatAchat.EFFECTUE.toString())) {
                    btn.setDisable(true);
                    btn.setId("reclamer_dis");
                     btn.setTextFill(Color.web("#ECF0F1"));
                    btn.getStylesheets().add("reclamer_dis");
                }else {
                    btn.setId("reclamer_btn");
                    btn.getStylesheets().add("reclamer_btn");
                     btn.setTextFill(Color.web("#ECF0F1"));
                }
                property.set(btn);
                return property;
            });
            
            accuser_col.setCellValueFactory((param) -> {
                Achat achat = param.getValue().getValue();
                SimpleObjectProperty<JFXButton> property = new SimpleObjectProperty<>();
                JFXButton btn;
                if (achat.getStatut().equals(EtatAchat.EFFECTUE.toString())) {
                    btn = new JFXButton("Accusé");
                    btn.setDisable(true);
                    btn.setId("accuser_dis");
                    btn.getStylesheets().add("accuse_dis");
                    
                    btn.setTextFill(Color.web("#ECF0F1"));
                } else {
                     btn = new JFXButton("Accuser");
                     btn.setId("accuser_btn");
                      btn.setTextFill(Color.web("#ECF0F1"));
                     btn.getStylesheets().add("accuse_btn");
                     btn.setOnAction((event) -> {
                         confirmation_acquisition(achat);
                     });
                }
                property.set(btn);
                return property;
            });
            
            ObservableList<Achat> mes_achats = FXCollections.observableArrayList(list_achats);
            TreeItem<Achat> root = new RecursiveTreeItem<Achat>(mes_achats, RecursiveTreeObject::getChildren);
            achatsTableView.setRoot(root);
            achatsTableView.setShowRoot(false);
            
        }

    private void confirmation_acquisition(Achat achat) {
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(new Text("Confirmation .."));
        lay.setBody(new Text("Vous êtes entrain d'accuser la récèption de votre article: "+achat.getArticle().getLibelle()+"\n"
                + "Voulez-vous vraiment continuer ?"));
        JFXButton ok_btn = new JFXButton("OUI");
        JFXButton ko_btn = new JFXButton("NON");
        
        lay.getActions().addAll(ok_btn, ko_btn);
        JFXDialog dial = new JFXDialog(dialogPane, lay, JFXDialog.DialogTransition.CENTER);
        dialogPane.setVisible(true);
        dial.show();
        ok_btn.setOnAction((event) -> {
            accuser_reception(achat);
            dialogPane.setVisible(false);
            dial.close();
        });
        
        ko_btn.setOnAction((event) -> {
            dialogPane.setVisible(false);
            dial.close();
        });
        
        dial.setOnDialogClosed((event) -> {
            dialogPane.setVisible(false);
        });
        
    }

    private void accuser_reception(Achat achat) {
        achat.setStatut(EtatAchat.EFFECTUE.toString());
        serviceAchat.updateAchat(achat);
        initAchatsTable();
    }
    
}
