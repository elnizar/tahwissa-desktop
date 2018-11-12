/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.boutique;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import entity.Achat;
import entity.Article;
import entity.EtatAchat;
import entity.ImageShop;
import entity.User;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import service.AchatService;
import service.ArticleService;
import service.ImageShopService;
import service.TestService;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.MyNotifications;
import util.StripeConfiguration;
import util.Tahwissa;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class BoutiqueController extends FXMLDocumentController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane div11;
    @FXML
    private Label prix_11;
    @FXML
    private Label libelle_11;
    @FXML
    private JFXButton btn_detail;
    @FXML
    private JFXButton btn_buy;
    @FXML
    private Pane pane_img;
    @FXML
    private ImageView image;
    @FXML
    private Pane div21;
    @FXML
    private Pane div13;
    @FXML
    private Pane div12;
    @FXML
    private Pane div23;
    @FXML
    private Pane div22;
    @FXML
    private Pagination paginator;
    @FXML
    private Pane div_articles;
    private StackPane stackPane;
    
    private List<Article> all_articles;
    private ArticleService articleService;
    private int nbr_page;
        @FXML
    private HBox detailBox;
    @FXML
    private StackPane dialogPane;
    @FXML
    private JFXDialogLayout layout;
    @FXML
    private ImageView image_id;
    @FXML
    private Label libelle_detail;
    @FXML
    private Label prix_detail;
    @FXML
    private Text description_detail;
    @FXML
    private JFXButton acheter;
    @FXML
    private JFXButton favoris;
    @FXML
    private JFXButton fermer;
    @FXML
    private ImageView image_1;
    @FXML
    private ImageView image_2;
    @FXML
    private ImageView image_3;
    @FXML
    private Label up;
    @FXML
    private Label down;
    @FXML
    private Label etat_detail;
    private int posDebImages;
    private int posFinImages;
    
    private User user = TestService.getMembre();

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
        @FXML
    private VBox imageBox;
    private int initImage;
    private int endImage;
  
    @FXML
    private StackPane paneSponsorise;
    @FXML
    private ImageView closeBtn;
    @FXML
    private ImageView imgSponsorise;
    @FXML
    private Label libelleSponsorise;
    @FXML
    private Label prixSponsorise;
    @FXML
    private Text descriptionSponsorise;
    @FXML
    private Label etatSponsorise;
    @FXML
    private Label venduSponsorise;
    @FXML
    private JFXButton acheterSponsorise;
    List<Article> articlesBoostes;
    int pos = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        articleService = ArticleService.getArticleService();
        all_articles = articleService.getNonVendu();
        System.out.println("Articles number: "+all_articles.size());
        super.initDrawer();
       
        setNbPages();
        initBoutiquePage(0);
        showMeBoost();
    } 
    
    public void showMeBoost() {
         articlesBoostes = ArticleService.getArticleService().getBoosted();
         class Boost extends TimerTask {
             
            @Override
            public void run() {
                pos = (pos%articlesBoostes.size());
                Article article = articlesBoostes.get(pos);
                
             
                //ImageView image_j = new ImageView(local);
                Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
                imgSponsorise.setImage(img);
                //img_div.getChildren().add(0, image_n);
            libelleSponsorise.setText(article.getLibelle());
            prixSponsorise.setText(String.valueOf(article.getPrix()));
            etatSponsorise.setText(article.getEtat());
            descriptionSponsorise.setText(article.getDescription());
            if (article.isVendu()) venduSponsorise.setText("Vendu"); else venduSponsorise.setText("Non Vendu");
            paneSponsorise.setVisible(true);
            closeBtn.setOnMouseClicked((event) -> {
                paneSponsorise.setVisible(false);
                this.cancel();
            });
            acheterSponsorise.setOnAction((event) -> {
                 if (solvable(user, article)) {
                confirmation_dialog(user, article);
            } else {
                nonSolvableDialog(user,article);
            }
            });
            pos++;
            
            }
         }
         
         Timer timer = new Timer();
         timer.scheduleAtFixedRate(new Boost(),0 , 10000);
         //timer.cancel();
         timer.purge();
    }
    
    
    public void setNbPages() {
        if (all_articles.size() % 6 != 0) {
             nbr_page = (all_articles.size()/6)+1;
        } else {
             nbr_page = all_articles.size()/6;
        }
         paginator.setPageCount(nbr_page);
         
         paginator.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
             initBoutiquePage(newIndex.intValue());
         });
    }
    
    public void initBoutiquePage(int index) {
        paginator.setCurrentPageIndex(index);
        ImageShopService imagesService = ImageShopService.getImageShopService();
          
//         paginator.setOnMouseClicked(value -> {
//             initBoutiquePage(paginator.getCurrentPageIndex());
//         });
         
         List<Article> article_page = getArticlesPage(index);
         System.out.println("nombre articles in this page "+article_page.size());
         int num_div = 0;
         Pane parent = (Pane) anchorPane.getChildren().get(1);
         
         for (Article article : article_page) {
            num_div++;
            Pane div = (Pane)parent.getChildren().get(num_div);
            Label libelle = (Label) div.getChildren().get(1);
            Label prix = (Label) div.getChildren().get(0);
            JFXButton btn_buy = (JFXButton) div.getChildren().get(3);
            libelle.setTooltip(new Tooltip(article.getLibelle()));
            
            if (article.getMembre().getId() == user.getId()) {
                System.out.println("Article.Membre "+article.getMembre().getId() +" USER ID: "+ user.getId());
                btn_buy.setDisable(true);
            } else {
                btn_buy.setDisable(false);
                 btn_buy.setOnAction((event) -> {
                    if (solvable(user, article)) {
                    confirmation_dialog(user, article);
                    } else {
                    nonSolvableDialog(user,article);
                    }
                });
            }
            
            JFXButton btn_details = (JFXButton) div.getChildren().get(2);
            
            btn_details.setOnAction((event) -> {
                showDialogDetail(article);
            });
            
             System.out.println("id image "+article.getId());
            
            Pane img_div = (Pane) div.getChildren().get(4);
            ImageView image_n = (ImageView) img_div.getChildren().get(0);
             System.out.println(imagesService.getImageArticle(article.getIdImage()).getImageName());
            
             //ImageView image_j = new ImageView(local);
             Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+imagesService.getImageArticle(article.getIdImage()).getImageName());
             image_n.setImage(img);
             //img_div.getChildren().add(0, image_n);
            image_n.setOnMouseClicked(value -> {
                showDialogDetail(article);
            });
            
            libelle.setText(article.getLibelle());
            prix.setText(String.valueOf(article.getPrix()));
        }
    }
    
    public List<Article> getArticlesPage(int index) {
        int start = 6*index;
        int fin = start + 6;
        if (all_articles.size() >  start) {
            if (all_articles.size() > fin) {
                 return all_articles.subList(start, fin);
            } else {
               return all_articles.subList(start, all_articles.size());
            }
        }
        return all_articles.subList(0, 5);
    }
    
    
    public void showDialogDetail(Article article) {
        libelle_detail.setText(article.getLibelle());
        description_detail.setText(article.getDescription());
        etat_detail.setText(article.getEtat());
        prix_detail.setText(String.valueOf(article.getPrix())+" TND");
        
        JFXDialog dialog = new JFXDialog(dialogPane, layout, JFXDialog.DialogTransition.CENTER);
        fermer.setOnAction((event) -> {
            dialog.close();
            dialogPane.setVisible(false);
        });
        
        System.out.println("Compte is null "+String.valueOf(user.getCompte().getId()));
        
        if (article.getMembre().getId() == user.getId()) {
            acheter.setDisable(true);
            favoris.setDisable(true);
        } else {
            acheter.setDisable(false);
            favoris.setDisable(false);
        }
        
        
        acheter.setOnAction((event) -> {
            if (solvable(user, article)) {
                confirmation_dialog(user, article);
            } else {
                nonSolvableDialog(user,article);
            }
        });
        
        favoris.setOnAction((event) -> {
            System.out.println("Favoris");
        });
        
        
        
        Image img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE
                +ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
        image_id.setImage(img);
        List<ImageShop> imagesArticle = ImageShopService.getImageShopService().getImagesByProduit(article);
        List<ImageShop> three_img;
        System.out.println("Image article ="+imagesArticle == null);
        if (imagesArticle.size()>=3) {
            imageBox.setDisable(false);
            posDebImages = 0;
            posFinImages = 2;
            System.out.println("nombre d'images dans cet article :"+imagesArticle.size());
            three_img = imagesArticle.subList(0, 3);
            

            img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE
                    +ImageShopService.getImageShopService().getImageArticle(article.getIdImage()).getImageName());
            
            image_1.setImage(img);
            
            image_1.setVisible(true);
            
            image_1.setOnMouseClicked((event) -> {
                ImageView imgTemp = new ImageView();
                imgTemp.setImage(image_id.getImage());
                image_id.setImage(image_1.getImage());
                image_1.setImage(imgTemp.getImage());
            });
            
            img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+three_img.get(1).getImageName());
            image_2.setImage(img);
            image_2.setVisible(true);
            image_2.setOnMouseClicked((event) -> {
                ImageView imgTemp = new ImageView();
                imgTemp.setImage(image_id.getImage());
                image_id.setImage(image_2.getImage());
                image_2.setImage(imgTemp.getImage());
                image_2.setVisible(true);
            });
            
            
            img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+three_img.get(2).getImageName());
            image_3.setImage(img);
            image_3.setVisible(true);
            image_3.setOnMouseClicked((event) -> {
                ImageView imgTemp = new ImageView();
                imgTemp.setImage(image_id.getImage());
                image_id.setImage(image_3.getImage());
                image_3.setImage(imgTemp.getImage());
                image_3.setVisible(true);
            });
            
            
            down.setOnMouseClicked((event) -> {
                posDebImages = (posDebImages-1)%imagesArticle.size();
                posFinImages = (posFinImages-1)%imagesArticle.size();
                posDebImages = (posDebImages<0) ? imagesArticle.size()+posDebImages : posDebImages;
                posFinImages = (posFinImages<0) ? imagesArticle.size()+posFinImages : posFinImages;
                
                System.out.println("nombre des articles: "+imagesArticle.size());
                System.out.println("Pos Deb: (up) "+posDebImages);
                System.out.println("Pos Fin: (up) "+posFinImages);
                
                Image imgg = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+imagesArticle.get(posDebImages).getImageName());
                ImageView imgTmp = new ImageView();
                image_3.setImage(image_2.getImage());
                image_2.setImage(image_1.getImage());
                image_1.setImage(imgg);
                image_1.setVisible(true);
                
            });
            up.setOnMouseClicked((event) -> {
                posDebImages = (posDebImages+1)%imagesArticle.size();
                posFinImages = (posFinImages+1)%imagesArticle.size();
                System.out.println("Pos Deb: (down) "+posDebImages);
                System.out.println("Pos Fin: (down) "+posFinImages);
                
                Image imgg = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+imagesArticle.get(posFinImages).getImageName());
                image_1.setImage(image_2.getImage());
                image_2.setImage(image_3.getImage());
                image_3.setImage(imgg);
                image_3.setVisible(true);
                
            });
        }
        else if (imagesArticle.isEmpty() == false) {
            imageBox.setDisable(false);
            three_img = imagesArticle;
            System.out.println("ArrayList is null "+three_img == null);
            
            img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+three_img.get(0).getImageName());
            
            image_1.setImage(img);
            
            image_1.setVisible(true);
            
            image_1.setOnMouseClicked((event) -> {
                ImageView imgTemp = new ImageView();
                imgTemp.setImage(image_id.getImage());
                image_id.setImage(image_1.getImage());
                image_1.setImage(imgTemp.getImage());
            });
            
            try {
                
                img = new Image(Tahwissa.URL_IMAGE_BOUTIQUE+three_img.get(1).getImageName());
                
                image_2.setImage(img);
                image_2.setVisible(true);
                
                image_2.setOnMouseClicked((event) -> {
                    ImageView imgTemp = new ImageView();
                    imgTemp.setImage(image_id.getImage());
                    image_id.setImage(image_1.getImage());
                    image_1.setImage(imgTemp.getImage());
                });
            } catch (IndexOutOfBoundsException e) {
            }
            
        } else {
            imageBox.setDisable(true);
            image_1.setVisible(false);
            image_2.setVisible(false);
            image_3.setVisible(false);
        }
        
        dialogPane.setVisible(true);
        dialog.show();
        
        dialog.setOnDialogClosed((event) -> {
            dialogPane.setVisible(false);
        });
        
    }

    private boolean solvable(User user, Article article) {
        return user.getCompte().getSolde()>= article.getPrix();
    }

    private void acheter_article(Article article, User user) {
        Achat achat = new Achat();
        achat.setArticle(article);
        achat.setMembre(user);
        achat.setStatut(EtatAchat.EN_ATTENTE.toString());
        achat.setDateHeureAchat(new Timestamp(new Date().getTime()));
        try {
            TestService.transaction(user.getCompte(), article.getPrix());
            AchatService.getAchatService().addAchat(achat);
            article.setVendu(true);
            articleService.updateArticle(article);
            message_dialog(article, user);
        } catch (Exception ex) {
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void nonSolvableDialog(User user, Article article) {
       JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(new Text("Désolé!"));
        lay.setBody(new Text("Votre achat ne peut pas être effectué car votre solde est insuffisant! \n votre solde actuelle: "+
                String.valueOf(user.getCompte().getSolde())+" \n avez-vous un autre moyen de paiement?")
        );
        JFXButton btn_non = new JFXButton("NON");
         JFXButton btn_oui = new JFXButton("OUI");
         
         lay.getActions().addAll(btn_oui, btn_non);
        JFXDialog dial = new JFXDialog(dialogPane, lay, JFXDialog.DialogTransition.CENTER);
        
        dial.setOnDialogOpened((event) -> {
            lay.setVisible(true);
        });
        
        dial.setOnDialogClosed((event) -> {
            dialogPane.setVisible(false);
            lay.setVisible(false);
        });
        
        btn_non.setOnAction((event) -> {
            dial.close();
        });
        
        btn_oui.setOnAction((event) -> {
            stripe_pay(article, user);
            dial.close();
        });
        
        dialogPane.setVisible(true);
        dial.show();
    }
    
    private void message_dialog(Article article, User user) {
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(new Text("Félicitation!"));
        lay.setBody(new Text("Votre achat a été effectué avec succès! \n votre solde actuelle: "+
                String.valueOf(user.getCompte().getSolde() - article.getPrix())));
        JFXButton btn_fermer = new JFXButton("Fermer");
        lay.getActions().add(btn_fermer);
        JFXDialog dial = new JFXDialog(dialogPane, lay, JFXDialog.DialogTransition.CENTER);
        
        dial.setOnDialogClosed((event) -> {
            lay.setVisible(false);
            dialogPane.setVisible(false);
        });
        
        btn_fermer.setOnAction((event) -> {
            dial.close();
        });
        
        dialogPane.setVisible(true);
        dial.show();
    }

    private void confirmation_dialog(User user, Article article) {
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(new Text("Confirmation .."));
        lay.setBody(new Text("Vous êtes entrain d'acheter un article avec un prix "+String.valueOf(article.getPrix())+"\n"
                + "Voulez-vous vraiment continuer ?"));
        JFXButton ok_btn = new JFXButton("OUI");
        JFXButton ko_btn = new JFXButton("NON");
        
        lay.getActions().addAll(ok_btn, ko_btn);
        JFXDialog dial = new JFXDialog(dialogPane, lay, JFXDialog.DialogTransition.CENTER);
        detailBox.setVisible(false);
        dialogPane.setVisible(true);
        
        dial.show();
        ok_btn.setOnAction((event) -> {
            acheter_article(article, user);
             all_articles = articleService.getNonVendu();
            setNbPages();
            initBoutiquePage(0);
            dialogPane.setVisible(false);
            dial.close();
        });
        
        ko_btn.setOnAction((event) -> {
            dialogPane.setVisible(false);
            dial.close();
        });
        
        dial.setOnDialogClosed((event) -> {
            dialogPane.setVisible(false);
            detailBox.setVisible(true);
        });
        
    }
    
    public void stripe_pay(Article article, User user) {
        JFXDialog dialog = new JFXDialog(paymentPane, paymentLayout, JFXDialog.DialogTransition.CENTER);
        paymentPane.setVisible(true);
        dialog.show();
        payStripe.setOnAction((event) -> {
            StripeConfiguration.payerStripe(cardNumberInput, monthInput, yearInput, article.getPrix());
            Achat achat = new Achat();
            achat.setArticle(article);
            achat.setMembre(user);
            achat.setDateHeureAchat(new Timestamp(new Date().getTime()));
            achat.setStatut(EtatAchat.EN_ATTENTE.toString());
            AchatService.getAchatService().addAchat(achat);
            article.setVendu(true);
            ArticleService.getArticleService().updateArticle(article);
            MyNotifications.infoNotification("Opération est faite avec succès", "Vous avez acheté l'article " + article.getLibelle() + " à " + String.valueOf(article.getPrix()));
             all_articles = articleService.getNonVendu();
            initBoutiquePage(0);
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
