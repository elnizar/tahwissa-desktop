/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.mesArticles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.Article;
import entity.Compte;
import entity.EtatArticle;
import entity.User;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import service.ArticleService;
import service.ImageShopService;
import service.TestService;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.MyNotifications;
import tahwissa.desktop.vendre_article.VenteController;
import util.StripeConfiguration;
import util.Tahwissa;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class MesArticlesController extends FXMLDocumentController {

    @FXML
    private HBox box1;
    @FXML
    private VBox boxArticle1;
      @FXML
    private ScrollPane MesArticles;
    @FXML
    private ImageView imgArticle1;
    @FXML
    private Label libelleArticle1;
    @FXML
    private JFXTextField libelleInput1;
    @FXML
    private Text descriptionBox1;
    @FXML
    private JFXTextArea textAreaBox1;
    @FXML
    private Label prixBox1;
    @FXML
    private JFXTextField inputPrixBox1;
    @FXML
    private Label etatBox1;
    @FXML
    private JFXComboBox<String> inputEtatBox1;
    @FXML
    private Label venduBox1;
    @FXML
    private Label boostedBox1;
    @FXML
    private JFXButton venduBtnBox1;
    @FXML
    private JFXButton suppBtnBox1;
    @FXML
    private JFXButton boosterBtnBox1;
    @FXML
    private HBox box2;
    @FXML
    private ImageView imgBox2;
    @FXML
    private Label libelleBox2;
    @FXML
    private JFXTextField inputLibelleBox2;
    @FXML
    private Text descriptionBox2;
    @FXML
    private JFXTextArea textAreaBox2;
    @FXML
    private Label prixBox2;
    @FXML
    private JFXTextField InputPrixBox2;
    @FXML
    private Label etatBox2;
    @FXML
    private JFXComboBox<String> inputEtatBox2;
    @FXML
    private Label venduBox2;
    @FXML
    private Label boostedBox2;
    @FXML
    private JFXButton venduBtnBox2;
    @FXML
    private JFXButton suppBtnBox2;
    @FXML
    private JFXButton boosterBtnBox2;
    @FXML
    private HBox box3;
    @FXML
    private ImageView imgBox3;
    @FXML
    private Label libelleBox3;
    @FXML
    private JFXTextField inputLibelleBox3;
    @FXML
    private Text descriptionBox3;
    @FXML
    private JFXTextArea textAreaBox3;
    @FXML
    private Label prixBox3;
    @FXML
    private JFXTextField inputPrixBo3;
    private JFXTextField etatBox3;
    @FXML
    private JFXComboBox<String> inputEtatBox3;
    @FXML
    private Label venduBox3;
    @FXML
    private Label boostedBox3;
    @FXML
    private JFXButton venduBtnBox3;
    @FXML
    private JFXButton suppBtnBox3;
    @FXML
    private JFXButton boosterBtnBox3;
    @FXML
    private Pagination paginator;
    @FXML
    private StackPane stackPane;

    private List<Article> mesArticles;
    private User user;

    @FXML
    private Label EtatBox3;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private StackPane paymentPane;
    @FXML
    private JFXDialogLayout paymentLayout;
    @FXML
    private JFXTextField cardNumberInput;
    @FXML
    private JFXTextField yearInput;
    @FXML
    private JFXTextField monthInput;
    @FXML
    private JFXButton payStripe;
    @FXML
    private JFXButton cancelStripe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Mes Articles controller :");
       

        // TODO
        super.initDrawer();
        user = TestService.getMembre();
        mesArticles = ArticleService.getArticleService().getArticlesByOwner(user.getId());
        System.out.println("Drawer est initialisé --Mes Articles");
        if (mesArticles.size() == 0) {
            MesArticles.setVisible(false);
            stackPane.setVisible(true);
        } else {
             MesArticles.setVisible(true);
             stackPane.setVisible(false);
                try {
                mesArticlesInit(0);
               } catch (MalformedURLException ex) {
                //Logger.getLogger(MesArticlesController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
               }
               paginator.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
                try {
                    mesArticlesInit(newIndex.intValue());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MesArticlesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
    }

    public void mesArticlesInit(int index) throws MalformedURLException {
        int nbrArticles = mesArticles.size();
        System.out.println("le nombre des articles : " + nbrArticles);
        int nbPages = nbrArticles / 3;
        nbPages = (nbrArticles % 3 == 0) ? nbPages : nbPages + 1;
        paginator.setPageCount(nbPages);

        List<Article> articleToShow = getNArticles(index);

        try {
            afficheArticle1(articleToShow.get(0));
            afficheArticle2(articleToShow.get(1));
            afficheArticle3(articleToShow.get(2));
        } catch (IndexOutOfBoundsException ex) {

        }

    }

    public void afficheArticle1(Article article) throws MalformedURLException {
        Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE
                + ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
        imgArticle1.setImage(img);
        libelleArticle1.setText(article.getLibelle());
        descriptionBox1.setText(article.getDescription());
        prixBox1.setText(String.valueOf(article.getPrix()));
        etatBox1.setText(article.getEtat());
        String vendu = (article.isVendu()) ? "vendu" : "Non vendu";
        venduBox1.setText(vendu);
        if (article.isBoosted()) {
            boostedBox1.setVisible(true);
            boosterBtnBox1.setDisable(true);
        } else {
            boostedBox1.setVisible(false);
            boosterBtnBox1.setDisable(false);
        }

        box1.setVisible(true);
        editLibelle(libelleArticle1, libelleInput1, article);
        editDescription(descriptionBox1, textAreaBox1, article);
        editEtat(etatBox1, inputEtatBox1, article);
        editPrix(prixBox1, inputPrixBox1, article);
        venduAction(venduBtnBox1, venduBox3, article);
        supprAction(suppBtnBox1, article);
        boosterAction(boosterBtnBox1, article);
    }

    public void afficheArticle2(Article article) throws MalformedURLException {
        Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE
                + ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
        imgBox2.setImage(img);
        libelleBox2.setText(article.getLibelle());
        descriptionBox2.setText(article.getDescription());
        prixBox2.setText(String.valueOf(article.getPrix()));
        etatBox2.setText(article.getEtat());
        String vendu = (article.isVendu()) ? "vendu" : "Non vendu";
        venduBox2.setText(vendu);
        if (article.isBoosted()) {
            boostedBox2.setVisible(true);
            boosterBtnBox2.setDisable(true);
        } else {
            boostedBox2.setVisible(false);
            boosterBtnBox2.setDisable(false);
        }
        box2.setVisible(true);

        editLibelle(libelleBox2, inputLibelleBox2, article);
        editDescription(descriptionBox2, textAreaBox2, article);
        editEtat(etatBox2, inputEtatBox2, article);
        editPrix(prixBox2, InputPrixBox2, article);
        venduAction(venduBtnBox2, venduBox2, article);
        supprAction(suppBtnBox2, article);
        boosterAction(boosterBtnBox2, article);
    }

    public void afficheArticle3(Article article) throws MalformedURLException {
        Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE
                + ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
        imgBox3.setImage(img);
        libelleBox3.setText(article.getLibelle());
        descriptionBox3.setText(article.getDescription());
        prixBox3.setText(String.valueOf(article.getPrix()));
        EtatBox3.setText(article.getEtat());
        String vendu = (article.isVendu()) ? "vendu" : "Non vendu";
        venduBox3.setText(vendu);
        if (article.isBoosted()) {
            boostedBox3.setVisible(true);
            boosterBtnBox3.setDisable(true);
        } else {
            boostedBox3.setVisible(false);
            boosterBtnBox3.setDisable(false);
        }
        box3.setVisible(true);

        editLibelle(libelleBox3, inputLibelleBox3, article);
        editDescription(descriptionBox3, textAreaBox3, article);
        editEtat(EtatBox3, inputEtatBox3, article);
        editPrix(prixBox3, inputPrixBo3, article);
        venduAction(venduBtnBox3, venduBox3, article);
        supprAction(suppBtnBox3, article);
        boosterAction(boosterBtnBox3, article);
    }

    public List<Article> getNArticles(int index) {
        return mesArticles.stream().skip(index * 3).limit(3).collect(Collectors.toList());
    }

    public void venduAction(JFXButton vendu, Label venduLabel, Article article) {
        if (article.isVendu()) {
            vendu.setDisable(true);
        } else {
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
                    ArticleService.getArticleService().updateArticle(article);
                    dialog.close();
                    stackPane.setVisible(false);
                    vendu.setDisable(true);
                    venduLabel.setText("Vendu");
                    MyNotifications.infoNotification("Mise à Jour d'un article", "Vous avez mis votre article : " + article.getLibelle() + " à l'état vendu!");
                   
                });

                btn_non.setOnAction(event -> {
                    dialog.close();
                    stackPane.setVisible(false);
                });
            });
        }

    }

    public void boosterAction(JFXButton boost, Article article) {
        if (article.isBoosted() || article.isVendu()) {
            boost.setDisable(true);
        } else {
            boost.setOnAction((ev) -> {
                boost.setOnAction(event -> {
                    Compte compte = user.getCompte();

                    if (compte.getSolde() < 20) {
                        JFXDialogLayout layout = new JFXDialogLayout();
                        layout.setHeading(new Text("SOLDE INSUFFISANT"));
                        layout.setBody(new Text("Désolé! Votre solde est insuffisant pour compléter cette opération! \n"
                                + "Vous avez un autre moyen de payer ?"));
                         
                        JFXButton btn_oui = new JFXButton("OUI");
                        btn_oui.setDisable(false);
                        JFXButton btn_non = new JFXButton("NON");
                        btn_oui.setStyle("-fx-background-color:#7EFFDB;");
                        
                        btn_non.setStyle("-fx-background-color:#FF9DE2");
                        
                        layout.getActions().add(btn_oui);
                        Separator sep = new Separator();
                        sep.setVisible(false);
                        layout.getActions().add(sep);
                        layout.getActions().add(btn_non);
           
                        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
                        stackPane.setVisible(true);
                        dialog.show();

                        btn_oui.setOnAction(action -> {
                            payer_stripe(article);
                            
                            dialog.close();
                            stackPane.setVisible(false);
                        });

                        btn_non.setOnAction(action -> {
                            dialog.close();
                            stackPane.setVisible(false);
                            MyNotifications.ErrorNotification("Booster un article", "Votre solde est insuffisant pour payer le frais de boost!");

                        });
                        
                        dialog.setOnDialogClosed((e) -> {
                            stackPane.setVisible(false);
                        });
                    } else {
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
                                ArticleService.getArticleService().updateArticle(article);
                                MyNotifications.infoNotification("Booster un article", "Vous avez booster votre article : " + article.getLibelle() + " pour 20 DT");

                            } catch (Exception ex) {
                                Logger.getLogger(VenteController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            dialog.close();
                            stackPane.setVisible(false);
                            boost.setDisable(true);
                            try {
                                mesArticlesInit(0);
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(MesArticlesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        btn_non.setOnAction(action -> {
                            dialog.close();
                            stackPane.setVisible(false);
                        });
                    }
                });
            });
        }

    }

    public void supprAction(JFXButton suppr, Article article) {
        suppr.setOnAction((event) -> {
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
                    ArticleService.getArticleService().deleteArticle(article);
                    stackPane.setMouseTransparent(true);
                    dialog.close();
                    stackPane.setVisible(false);
                    MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez supprimer votre article de la boutique!!");
                    try {
                        mesArticles = ArticleService.getArticleService().getArticlesByOwner(1);
                        mesArticlesInit(paginator.getCurrentPageIndex());
                    } catch (MalformedURLException ex) {
                    }
                });

                closeButton.setOnAction((e) -> {
                    stackPane.setMouseTransparent(true);
                    dialog.close();
                    stackPane.setVisible(false);
                });

                dialog.show();
            }
        });
    }

    public void editLibelle(Label label, JFXTextField input, Article article) {
        label.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                label.setVisible(false);
                input.setVisible(true);
                input.requestFocus();
                RequiredFieldValidator required = new RequiredFieldValidator();
                required.setMessage("SVP tapez un libelle!");
                input.setOnKeyPressed((action) -> {
                    if (action.getCode().equals(KeyCode.ENTER)) {
                        input.setValidators(required);
                        article.setLibelle(input.getText());
                        label.setText(input.getText());
                        input.setVisible(false);
                        ArticleService.getArticleService().updateArticle(article);
                        label.setVisible(true);
                        MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez changé le libelle de votre article");
                    }
                });
            }
        });
    }

    public void editDescription(Text text, JFXTextArea area, Article article) {
        text.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                text.setVisible(false);
                area.setVisible(true);
                area.requestFocus();
                RequiredFieldValidator required = new RequiredFieldValidator();
                required.setMessage("SVP tapez un libelle!");
                area.setOnKeyPressed((action) -> {
                    if (action.getCode().equals(KeyCode.ENTER)) {
                        area.setValidators(required);
                        article.setDescription(area.getText());
                        text.setText(area.getText());
                        area.setVisible(false);
                        ArticleService.getArticleService().updateArticle(article);
                        text.setVisible(true);
                        MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez edité la description de votre article : " + article.getLibelle());
                    }
                });
            }
        });
    }

    public void editEtat(Label label, JFXComboBox<String> etatsCombo, Article article) {
        ObservableList etats = FXCollections.observableArrayList();
        etats.add(EtatArticle.BONNE_OCCASION.toString());
        etats.add(EtatArticle.BON_ETAT.toString());
        etats.add(EtatArticle.ETAT_MOYEN.toString());
        etats.add(EtatArticle.NOUVEAU.toString());
        etatsCombo.setItems(etats);

        label.setOnMouseClicked(ev -> {
            label.setVisible(false);
            etatsCombo.setVisible(true);
            etatsCombo.getSelectionModel().select(article.getEtat());
            etatsCombo.requestFocus();
            etatsCombo.setOnKeyPressed((event) -> {
                if (KeyCode.ENTER.equals(event.getCode())) {
                    if (etatsCombo.getSelectionModel().getSelectedItem().equals(article.getEtat()) == false) {
                        article.setEtat(etatsCombo.getSelectionModel().getSelectedItem());
                        ArticleService.getArticleService().updateArticle(article);
                        etatsCombo.setVisible(false);
                        label.setText(etatsCombo.getSelectionModel().getSelectedItem());
                        label.setVisible(true);
                        MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez changé l'état de votre article : " + article.getLibelle()
                                + " à " + article.getEtat());
                    }
                }
            });

        });
    }

    public void editPrix(Label label, JFXTextField input, Article article) {
        label.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                label.setVisible(false);
                input.setVisible(true);
                input.requestFocus();
                RequiredFieldValidator required = new RequiredFieldValidator();
                required.setMessage("SVP tapez un prix!");
                input.setOnKeyPressed((action) -> {
                    if (action.getCode().equals(KeyCode.ENTER)) {
                        input.setValidators(required);
                        article.setPrix(Double.valueOf(input.getText()));
                        label.setText(input.getText());
                        input.setVisible(false);
                        ArticleService.getArticleService().updateArticle(article);
                        label.setVisible(true);
                        MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez changé le prix de votre article " + article.getLibelle() + " à " + String.valueOf(article.getPrix()));

                    }
                });
            }
        });
    }

    private void payer_stripe(Article article) {
        JFXDialog dialog = new JFXDialog(paymentPane, paymentLayout, JFXDialog.DialogTransition.CENTER);
        paymentPane.setVisible(true);
        dialog.show();
        payStripe.setOnAction((event) -> {

            StripeConfiguration.payerStripe(cardNumberInput, monthInput, yearInput, 20);
            article.setBoosted(true);
            ArticleService.getArticleService().updateArticle(article);
            MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez boosté votre article " + article.getLibelle() + " à " + String.valueOf(20));
            
            try {
                mesArticles = ArticleService.getArticleService().getArticlesByOwner(1);
                mesArticlesInit(paginator.getCurrentPageIndex());
            } catch (MalformedURLException ex) {
                Logger.getLogger(MesArticlesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            dialog.close();
        });
        cancelStripe.setOnAction((event) -> {
            dialog.close();
        });
        dialog.setOnDialogClosed((event) -> {
            paymentPane.setVisible(false);
        });
    }
}
