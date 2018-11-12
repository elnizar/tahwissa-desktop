/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.vendre_article;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tahwissa.desktop.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import de.svenjacobs.loremipsum.LoremIpsum;

import entity.Article;
import entity.Compte;
import entity.EtatArticle;
import entity.Evenement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FormatStringConverter;
import javafx.util.converter.NumberStringConverter;
import service.ArticleService;
import service.TestService;
import tahwissa.desktop.MyNotifications;
import tahwissa.desktop.myevents.MyEventsController;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class VenteController extends FXMLDocumentController {
    
    private Article article;
    private ArticleService articleService;
    private List<ValidatorBase> addFormValidators;
    private List<File> files = new ArrayList<>();
    private List<Article> myArticles;
    private Task<List<Article>> getArticles = new Task() {
        @Override
        protected List<Article> call() throws Exception {
            articleService = ArticleService.getArticleService();
            return articleService.getArticlesByOwner(1);
        }
        
    };
    private boolean error = false;
    @FXML
    private AnchorPane anchorPane0;
    @FXML
    private JFXTabPane contentPane;

    @FXML
    private Tab favTab;

    @FXML
    private Tab myArticleTab;

    @FXML
    private Tab vendreTab;

    @FXML
    private AnchorPane vendreContent;

    @FXML
    private Label libelleLab;

    @FXML
    private Label prixLab;

    @FXML
    private Label etatLab;

    @FXML
    private Label descriptionLab;

    @FXML
    private Label imageLab;

    @FXML
    private JFXTextField libelle;

    @FXML
    private JFXTextField prix;

    @FXML
    private JFXComboBox<String> etat;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton upload;

    @FXML
    private JFXButton ajouter;
    
    @FXML
    private JFXTreeTableView<Article> articlesTableView;
    
     @FXML
    private TreeTableColumn<Article, Number> my_id;

    @FXML
    private TreeTableColumn<Article, String> my_libelle;

    @FXML
    private TreeTableColumn<Article, String> my_description;

    @FXML
    private TreeTableColumn<Article, Number> my_prix;

    @FXML
    private TreeTableColumn<Article, String> my_etat;

    @FXML
    private TreeTableColumn<Article, JFXButton> my_boost;

    @FXML
    private TreeTableColumn<Article, JFXButton> my_vendu;

    @FXML
    private TreeTableColumn<Article, JFXButton> my_images;
    
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXButton lorem;
    private LoremIpsum loremIpsum;      
            
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb);
        myArticles = ArticleService.getArticleService().getArticlesByOwner(TestService.getMembre().getId());
        ObservableList<String> items = FXCollections.observableArrayList(
                EtatArticle.NOUVEAU.toString(),
                EtatArticle.BON_ETAT.toString(),
                EtatArticle.BONNE_OCCASION.toString(),
                EtatArticle.ETAT_MOYEN.toString()
        );
        etat.setItems(items);
        initValidators();
        initTreeTableView();
        loremIpsum = new LoremIpsum();
    }   
    
    
    void initValidators() {
        addFormValidators = new ArrayList<>();
        DoubleValidator prixArticle = new DoubleValidator();
        prixArticle.setMessage("Prix de votre article est invalide");
        prix.getValidators().add(prixArticle);
        prix.focusedProperty().addListener(observable -> {
            prix.validate();
        });
        RequiredFieldValidator libelleReq = new RequiredFieldValidator();
        libelleReq.setMessage("Libelle est requis");
        libelle.getValidators().add(libelleReq);
        
        RequiredFieldValidator prixReq = new RequiredFieldValidator();
        prixReq.setMessage("Prix est requis");
        prix.getValidators().add(libelleReq);
    }
    
    @FXML
    void ajouterArticle(ActionEvent event) {
        //if libelle is not set a dialog box will show up -> Okei -> focus on libelle input text
        if ("".equals(libelle.getText())) {
            JFXButton okei = new JFXButton("OK");
            System.out.println("dd");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vous avez oublié de remplir un champs : Libelle", ButtonType.CLOSE);
            alert.show();
            libelle.requestFocus();
            return;
        } 
        
        if ("".equals(description.getText())) {
            JFXButton okei = new JFXButton("OK");
            System.out.println("dd");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vous avez oublié de remplir un champs : Description", ButtonType.CLOSE);
            alert.show();
            description.requestFocus();
            return;
        } 
      
        if (files.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.WARNING, "Vous avez oublié de télécharger une image", ButtonType.CLOSE);
           alert.show();
           upload.requestFocus();
           return;
        }
        article = new Article();
        articleService = ArticleService.getArticleService();
        System.out.println("Creating new Entity  ..");
        System.out.println("Injecting Libelle  .."+libelle.getText());
        article.setLibelle(libelle.getText());
        System.out.println("Injecting Description  .."+description.getText());
        article.setDescription(description.getText());
        System.out.println("Injecting Etat  .."+etat.getValue());
        article.setEtat(etat.getValue());
        System.out.println("Injecting Membre  ..");
        article.setMembre(TestService.getMembre());
        System.out.println("Injecting Prix  .."+Double.valueOf(prix.getText()));
        article.setPrix(Double.valueOf(prix.getText()));
        System.out.println("Persisting ..");
        articleService.addArticle(article, files);
        MyNotifications.infoNotification("Ajout d'un nouveau article", "Vous avez ajouté un nouveau article dans la boutique!");
    }
    
   
    @FXML
    void fileChoosing(ActionEvent event) {
         System.out.println("test");
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une image");
        files = fileChoser.showOpenMultipleDialog(theStage);
        checkFiles();
        if (error) {
            imageLab.setText("Fichiers invalides");
            files = new ArrayList<>();
            error = false;
            System.out.println("Fichier(s) invalides");
        } else {
            imageLab.setText(files.size() + " fichier(s) choisi(s)");
        }
    }
    
    public void checkFiles() {
        if (files == null) {
            files = new ArrayList<>();
            error = false;
            return;
        }
        files.forEach((t) -> {
            Path srcPath = t.toPath();
            try {
                if (!Files.probeContentType(srcPath).contains("image")) {
                    System.out.println("Image NON OK ..");
                    MyNotifications.ErrorNotification("Ajout d'un article", "Type de fichier que vous avez choisi n'est une image!");
                    error = true;
                }
                System.out.println("Image OK ..");
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    void initTreeTableView() {
        
        articlesTableView.setEditable(true);
        
        articleService = ArticleService.getArticleService();
        
        my_id.setCellValueFactory(param -> {
            SimpleIntegerProperty property = new SimpleIntegerProperty();
            Article article = (Article) param.getValue().getValue();
            property.setValue(article.getId());
            return property;
        });
        
        my_libelle.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Article article = (Article) param.getValue().getValue();
            property.set(article.getLibelle());
            return property;
        });
        
        my_description.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Article article = (Article) param.getValue().getValue();
            property.set(article.getDescription());
            return property;
        });
        
        my_prix.setCellValueFactory(param -> {
            SimpleDoubleProperty property = new SimpleDoubleProperty();
            Article article = (Article) param.getValue().getValue();
            property.set(article.getPrix());
            return property;
        });
        
        my_etat.setCellValueFactory(param -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Article article = (Article) param.getValue().getValue();
            property.set(article.getEtat());
            return property;
        });
        
        my_boost.setCellValueFactory(param -> {
            Article article = (Article) param.getValue().getValue();
            SimpleObjectProperty<JFXButton> property = new SimpleObjectProperty<>();
            JFXButton boost;
            if (article.isBoosted()) {
                 boost = new JFXButton("Boosté");
                 boost.setDisable(true);
            } else {
               boost = new JFXButton("Booster");
               boost.setOnAction(event -> {
               Compte compte = TestService.getCompteByUser(1);
               
               if (compte.getSolde() < 20) {
                   JFXDialogLayout layout = new JFXDialogLayout();
                   layout.setHeading(new Text("SOLDE INSUFFISANT"));
                   layout.setBody(new Text("Désolé! Votre solde est insuffisant pour compléter cette opération! \n"
                           + "Vous avez un autre moyen de payer ?"));
                   JFXButton btn_oui = new JFXButton("OUI");
                   JFXButton btn_non = new JFXButton("NON");
                   
                   layout.getActions().add(btn_oui);
                   layout.getActions().add(btn_non);
                   
                   JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                   stackPane.setVisible(true);
                   dialog.show();
                   
                   btn_oui.setOnAction(action -> {
                       payer_stripe();
                       dialog.close();
                       stackPane.setVisible(false);
                   });
                   
                   btn_non.setOnAction(action -> {
                       dialog.close();
                       stackPane.setVisible(false);
                   });
               }else {
                   JFXDialogLayout layout = new JFXDialogLayout();
                   layout.setHeading(new Text("Confirmation!"));
                   layout.setBody(new Text("Voulez-vous vraiment compléter cette opération! \n"
                           + "Vous êtes entrain de booster un article pour 20 DNT  ?"));
                   JFXButton btn_oui = new JFXButton("OUI");
                   JFXButton btn_non = new JFXButton("NON");
                   
                   layout.getActions().add(btn_oui);
                   layout.getActions().add(btn_non);
                   
                   JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                   stackPane.setVisible(true);
                   dialog.show();
                   
                   btn_oui.setOnAction(action -> {
                       try {
                           service.TestService.transaction(compte.getId(), 20);
                           article.setBoosted(true);
                           articleService.updateArticle(article);
                           
                       } catch (Exception ex) {
                           Logger.getLogger(VenteController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       
                       dialog.close();
                       stackPane.setVisible(false);
                       refreshTreeTable();
                   });
                   
                   btn_non.setOnAction(action -> {
                       dialog.close();
                       stackPane.setVisible(false);
                   });
               }
           });
            }

           property.set(boost);
            return property;
        });
        
        my_vendu.setCellValueFactory(param -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            Article article = (Article) param.getValue().getValue();
            JFXButton vendu;
            if (article.isVendu()) {
                vendu = new JFXButton("Vendu");
                vendu.setDisable(true);
            } else {
                vendu = new JFXButton("Vendu");
                vendu.setOnAction(action -> {
                    
                    JFXDialogLayout layout = new JFXDialogLayout();
                   layout.setHeading(new Text("Confirmation!"));
                   layout.setBody(new Text("Voulez-vous vraiment compléter cette opération! \n"
                           + "Vous êtes entrain de passer cet article à VEDNU  ?"));
                   
                   JFXButton btn_oui = new JFXButton("OUI");
                   JFXButton btn_non = new JFXButton("NON");
                   
                   layout.getActions().add(btn_oui);
               
                   layout.getActions().add(btn_non);
                   
                   JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                   stackPane.setVisible(true);
                   dialog.show();
                   
                   btn_oui.setOnAction(event -> {
                       article.setVendu(true);
                       articleService.updateArticle(article);
                       dialog.close();
                       stackPane.setVisible(false);
                       refreshTreeTable();
                   });
                   
                   btn_non.setOnAction(event -> {
                       dialog.close();
                       stackPane.setVisible(false);
                   });
                });
            }
            
            property.set(vendu);
            return property;
        });
        
        my_images.setCellValueFactory(param -> {
            SimpleObjectProperty<JFXButton> property = new SimpleObjectProperty<>();
            JFXButton button = new JFXButton("Upload images");
            property.set(button);
            return property;
        });
        
         ObservableList<Article> articles = FXCollections.observableArrayList(myArticles);
        TreeItem<Article> root = new RecursiveTreeItem<Article>(articles, RecursiveTreeObject::getChildren);
        articlesTableView.setRoot(root);
        articlesTableView.setShowRoot(false);

        articlesTableView.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
            if (event.getCode().toString().equals("DELETE")) {
                Article article = articlesTableView.getSelectionModel().getSelectedItem().getValue();
                if (article.isVendu() == true) {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Erreur"));
                    content.setBody(new Text("Vous ne pouvez pas supprimer cet Article"));
                    JFXButton delButton = new JFXButton("Fermer");
                    content.getActions().add(delButton);
                    stackPane.setVisible(true);
                    stackPane.setMouseTransparent(false);
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    delButton.setOnAction((e) -> {
                        stackPane.setMouseTransparent(true);
                        dialog.close();
                        stackPane.setVisible(false);
                    });
                    dialog.show();
                } else {
                    // System.out.println("test");
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Confirmation"));
                    content.setBody(new Text("Voulez-vous supprimer cet Article ?"));
                    JFXButton delButton = new JFXButton("Supprimer");
                    JFXButton closeButton = new JFXButton("Annuler");
                    content.getActions().add(delButton);
                    content.getActions().add(closeButton);
                    stackPane.setVisible(true);
                    stackPane.setMouseTransparent(false);
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    delButton.setOnAction((e) -> {
                        Article art = articlesTableView.getSelectionModel().getSelectedItem().getValue();
                        System.out.println(art.getId());
                        articleService.deleteArticle(art);
                        stackPane.setMouseTransparent(true);

                        dialog.close();
                        stackPane.setVisible(false);
                        refreshTreeTable();

                    });

                    closeButton.setOnAction((e) -> {
                        stackPane.setMouseTransparent(true);
                        dialog.close();
                        stackPane.setVisible(false);
                    });

                    dialog.show();
                }
            }
            
            else if (event.getCode().toString().equals("E")){
                System.out.println("Modification");
            }
            
            else if (event.getCode().toString().equals("ENTER")){
                System.out.println("Détails");
            }
            
    });
        editiableColumns();
    }
    
    public void editiableColumns() {
        articleService = ArticleService.getArticleService();
        my_libelle.setCellFactory(
             (param) -> {
                    return new TextFieldTreeTableCell<>();
                }
        );
        my_libelle.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        my_description.setCellFactory(
             (param) -> {
                    return new TextFieldTreeTableCell<>();
                }
        );
        
        my_description.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        
        my_prix.setCellFactory(
              (param) -> {
                    return new TextFieldTreeTableCell<>(new NumberStringConverter());
                }
        );
        ObservableList<String> les_etats = FXCollections.observableArrayList();
        les_etats.add(EtatArticle.BONNE_OCCASION.toString());
        les_etats.add(EtatArticle.BON_ETAT.toString());
        les_etats.add(EtatArticle.ETAT_MOYEN.toString());
        les_etats.add(EtatArticle.NOUVEAU.toString());
        
        my_etat.setCellFactory(
             (param) -> {
                    return new ComboBoxTreeTableCell<>();
                }
        );
        
        my_etat.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(les_etats));
    
        my_images.setCellValueFactory(param -> {
            SimpleObjectProperty<JFXButton> property = new SimpleObjectProperty<>();
            JFXButton button = new JFXButton("Upload images");
            property.set(button);
            return property;
        });
        
        my_libelle.setOnEditCommit(action -> {
            TreeItem<Article> currentArticle = articlesTableView.getTreeItem(action.getTreeTablePosition().getRow());
            Article article = currentArticle.getValue();
            if (! action.getNewValue().toString().equals("")) {
                article.setLibelle(action.getNewValue().toString());
                articleService.updateArticle(article);
            } else {
                article.setLibelle(action.getOldValue().toString());
                articleService.updateArticle(article);
            }
        });
        
        my_description.setOnEditCommit(action -> {
            TreeItem<Article> currentArticle = articlesTableView.getTreeItem(action.getTreeTablePosition().getRow());
            Article article = currentArticle.getValue();
            if (! action.getNewValue().toString().equals("")) {
                article.setDescription(action.getNewValue().toString());
                articleService.updateArticle(article);
            } else {
                article.setDescription(action.getOldValue().toString());
                articleService.updateArticle(article);
            }
        });
        
        my_prix.setOnEditCommit(action -> {
            TreeItem<Article> currentArticle = articlesTableView.getTreeItem(action.getTreeTablePosition().getRow());
            Article article = currentArticle.getValue();
            if (! action.getNewValue().toString().equals("")) {
                article.setPrix(action.getNewValue().doubleValue());
                System.out.println(action.getNewValue().doubleValue());
                articleService.updateArticle(article);
            } else {
                article.setPrix(action.getOldValue().doubleValue());
                System.out.println(action.getOldValue().doubleValue());
                articleService.updateArticle(article);
            }
        });
        
        my_etat.setOnEditCommit(action -> {
            TreeItem<Article> currentArticle = articlesTableView.getTreeItem(action.getTreeTablePosition().getRow());
            Article article = currentArticle.getValue();
            if (! action.getNewValue().toString().equals("")) {
                article.setEtat(action.getNewValue().toString());
                articleService.updateArticle(article);
            }
        });
        
    }
    
    public void refreshTreeTable() {
        myArticles = articleService.getArticlesByOwner(1);
        ObservableList<Article> articles = FXCollections.observableArrayList(myArticles);
        TreeItem<Article> root = new RecursiveTreeItem<Article>(articles, RecursiveTreeObject::getChildren);
        articlesTableView.setRoot(root);
        articlesTableView.setShowRoot(false);
    }
    
    public void payer_stripe() {
        
    }  
    
    @FXML
    void loremIpsum(ActionEvent event) {
           libelle.setText(loremIpsum.getWords(5));
           prix.setText(String.valueOf(Math.random()+1000));
           description.setText(loremIpsum.getParagraphs());
           etat.getSelectionModel().select(0);
    }
}
