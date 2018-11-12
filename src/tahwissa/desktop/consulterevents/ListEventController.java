/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.consulterevents;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entity.Creneau;
import entity.Evenement;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.EvenementService;
import service.EvenementServiceInterface;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.detailsevent.DetailsController;

/**
 *
 * @author Meedoch
 */
public class ListEventController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private Evenement currentlyBoosted;
    @FXML
    private JFXDrawer drawer;
    EvenementServiceInterface service = new EvenementService();
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private StackPane slide;
    @FXML
    private ScrollPane scrollPane;

    private List<Evenement> boostedEvents = new ArrayList<Evenement>();
    private List<Evenement> otherEvents = new ArrayList<Evenement>();
    public static Evenement event;
    private final String imageSource = "http://localhost/tahwissa/web/images/evenements/";
    @FXML
    private ImageView imageDisplay;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    private List<Image> boostedImages = new ArrayList<Image>();
    @FXML
    private Text description;
    private int first = 0;
    @FXML
    private Text reglement;

    @FXML
    private Label datedepart;

    @FXML
    private Label frais;

    @FXML
    private Label distance;
    private Image currentImage;
    @FXML
    private Label nbplaces;

    @FXML
    private Label duree;

    @FXML
    private Label title;

    @FXML
    private ImageView camping;

    @FXML
    private ImageView randonnee;

    @FXML
    private Text titre1;

    @FXML
    private Text date1;

    @FXML
    private Label prix1;

    @FXML
    private Label places1;

    @FXML
    private Text titre2;

    @FXML
    private Text date2;

    @FXML
    private Label prix2;

    @FXML
    private Label places2;

    @FXML
    private Text titre3;

    @FXML
    private Text date3;

    @FXML
    private Label prix3;

    @FXML
    private Text titreB;

    @FXML
    private Text nbPlacesB;

    @FXML
    private Label places3;
    private List<ScrollBar> scrollBars;
    Evenement e1;
    Evenement e2;
    Evenement e3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("finding them");

        initDrawer();
        initSlideShow();
        loadEvents();

    }

    public void loadEvents() {
        try {

            List<Evenement> otherEventss = service.getAvailable();
            if (otherEventss != null) {
                otherEventss.forEach((t) -> {
                    if (t.getImages().size() != 0) {
                        otherEvents.add(t);
                    } else {

                    }
                });
                if (otherEvents.isEmpty() == false) {

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListEventController.class.getName()).log(Level.SEVERE, null, ex);
            //imageDisplay.setImage(new Image(imageSource + boostedEvents.get(5).getImages().get(0).getChemin()));
        }

        if (otherEvents.isEmpty() == false) {
            for (int i = 0; i < otherEvents.size(); i++) {
                Evenement e = otherEvents.get(i);
                if (i % 3 == 0) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre1.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre1.setText("Camping à " + e.getDestination());
                    }
                    date1.setText(e.getDateHeureDepart().toString());
                    prix1.setText(String.valueOf(e.getFrais()));
                    places1.setText(String.valueOf(e.getNombrePlaces()));
                    FadeTransition ft = new FadeTransition(Duration.millis(1500), image1);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.setCycleCount(1);

                    image1.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    ft.play();
                    e1 = e;
                    image1.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                        DetailsController.event = e1;
                        goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
                    });

                } else if (i % 3 == 1) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre2.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre2.setText("Camping à " + e.getDestination());
                    }
                    date2.setText(e.getDateHeureDepart().toString());
                    prix2.setText(String.valueOf(e.getFrais()));
                    places2.setText(String.valueOf(e.getNombrePlaces()));

                    FadeTransition ft = new FadeTransition(Duration.millis(1500), image2);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.setCycleCount(1);

                    image2.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    ft.play();
                    e2 = e;
                    image2.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                        DetailsController.event = e2;
                        goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
                    });
                } else if (i % 3 == 2) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre3.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre3.setText("Camping à " + e.getDestination());
                    }
                    date3.setText(e.getDateHeureDepart().toString());
                    prix3.setText(String.valueOf(e.getFrais()));
                    places3.setText(String.valueOf(e.getNombrePlaces()));

                    FadeTransition ft = new FadeTransition(Duration.millis(3000), image3);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.setCycleCount(1);
                    e3 = e;
                    image3.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    ft.play();
                    image3.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                        DetailsController.event = e3;
                        goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
                    });
                }
                if (i >= 3) {
                    break;
                }
            }
        }

    }

    public void initSlideShow() {
        try {

            List<Evenement> boostedEventss = service.getBoosted();
            if (boostedEventss != null) {
                boostedEventss.forEach((t) -> {
                    if (t.getImages().size() != 0) {
                        Image i = new Image(imageSource + t.getImages().get(0).getChemin());
                        boostedImages.add(i);
                        boostedEvents.add(t);
                    } else {
                        //boostedEvents.remove(t);
                    }
                });
                if (boostedImages.isEmpty() == false) {
                    imageDisplay.setImage(boostedImages.get(0));
                    currentImage = boostedImages.get(0);
                    currentlyBoosted = boostedEvents.get(0);
                    animate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListEventController.class.getName()).log(Level.SEVERE, null, ex);
            //imageDisplay.setImage(new Image(imageSource + boostedEvents.get(5).getImages().get(0).getChemin()));
        }
    }

    public void animate() {
        FadeTransition ft = new FadeTransition(Duration.millis(1500), slide);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.setOnFinished((event) -> {
            nextImage(null);

            ft.setFromValue(0);
            ft.setToValue(1.0);
            ft.play();
            ft.setOnFinished((e) -> {
                ft.setFromValue(1.0);
                ft.setToValue(1.0);
                ft.setDuration(Duration.millis(6000));
                ft.play();
                ft.setOnFinished((e2) -> {
                    animate();
                    if (scrollBars == null) {
                        Set<Node> nodes = scrollPane.lookupAll(".scroll-bar");
                        //System.out.println(nodes.size() + " scrollbars");
                        scrollBars = new ArrayList<ScrollBar>();
                        nodes.forEach((t) -> {
                            scrollBars.add((ScrollBar) t);

                        });
                        scrollBars.forEach((t) -> {
                            t.setVisible(false);
                            t.valueProperty().addListener((observable, oldValue, newValue) -> {
                                if (oldValue != newValue) {
                                    FadeTransition ft2 = new FadeTransition(Duration.millis(1000), t);
                                    t.setVisible(true);

                                    ft2.setFromValue(1.0);
                                    ft2.setToValue(0);
                                    ft2.setDuration(Duration.millis(6000));
                                    ft2.play();
                                    ft2.setOnFinished((re) -> {
                                        t.setVisible(false);
                                    });
                                } else {
                                    t.setVisible(false);
                                }
                            });
                        });
                    } else {

                    }
                });

            });
        });

        ft.play();
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
            //System.out.println(drawer);
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

    @FXML
    void nextImage(MouseEvent ev) {
        int next = boostedImages.indexOf(currentImage) + 1;
        if (next < boostedImages.size()) {
            // System.out.println(imageSource+boostedImages.get(next));
            currentlyBoosted = boostedEvents.get(next);
            imageDisplay.setImage(boostedImages.get(next));
            currentImage = boostedImages.get(next);
            
            imageDisplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                DetailsController.event = currentlyBoosted;
                goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
            });
            
            titreB.setText(currentlyBoosted.getEvenementType()+" à "+currentlyBoosted.getDestination());
            nbPlacesB.setText("Plus que "+(currentlyBoosted.getNombrePlaces()-currentlyBoosted.getNombrePlacesPrises())+" places disponibles");
        } else {
            imageDisplay.setImage(boostedImages.get(0));
            currentImage = boostedImages.get(0);
            currentlyBoosted = boostedEvents.get(0);
            titreB.setText(currentlyBoosted.getEvenementType()+" à "+currentlyBoosted.getDestination());
            nbPlacesB.setText("Plus que "+(currentlyBoosted.getNombrePlaces()-currentlyBoosted.getNombrePlacesPrises())+" places disponibles");
            imageDisplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
                DetailsController.event = currentlyBoosted;
                goTo(event, "/tahwissa/desktop/detailsevent/View.fxml");
            });
        }
    }

    @FXML
    void previousImage(MouseEvent ev) {
        int next = boostedImages.indexOf(currentImage) - 1;
        if (next > 0) {
            // System.out.println(imageSource+boostedImages.get(next));
            imageDisplay.setImage(boostedImages.get(next));
            currentImage = boostedImages.get(next);
            currentlyBoosted = boostedEvents.get(next);
        } else {
            imageDisplay.setImage(boostedImages.get(0));
            currentImage = boostedImages.get(boostedImages.size() - 1);
            currentlyBoosted = boostedEvents.get(boostedImages.size() - 1);
        }
    }

    @FXML
    void prec(ActionEvent event) {
        if (first > 0) {
            first--;
            for (int i = first; i < first + 3; i++) {
                Evenement e = otherEvents.get(i);
                if ((i - first) % 3 == 0) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre1.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre1.setText("Camping à " + e.getDestination());
                    }
                    date1.setText(e.getDateHeureDepart().toString());
                    prix1.setText(String.valueOf(e.getFrais()));
                    places1.setText(String.valueOf(e.getNombrePlaces()));
                    image1.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    e1 = e;
                } else if ((i - first) % 3 == 1) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre2.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre2.setText("Camping à " + e.getDestination());
                    }
                    date2.setText(e.getDateHeureDepart().toString());
                    prix2.setText(String.valueOf(e.getFrais()));
                    places2.setText(String.valueOf(e.getNombrePlaces()));
                    image2.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    e2 = e;
                } else if ((i - first) % 3 == 2) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre3.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre3.setText("Camping à " + e.getDestination());
                    }
                    date3.setText(e.getDateHeureDepart().toString());
                    prix3.setText(String.valueOf(e.getFrais()));
                    places3.setText(String.valueOf(e.getNombrePlaces()));
                    image3.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    e3 = e;
                }

            }
        }
    }

    @FXML
    void suivant(ActionEvent event) {
        if (first + 3 < otherEvents.size()) {
            first++;
            for (int i = first; i < first + 3; i++) {
                Evenement e = otherEvents.get(i);
                if ((i - first) % 3 == 0) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre1.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre1.setText("Camping à " + e.getDestination());
                    }
                    date1.setText(e.getDateHeureDepart().toString());
                    prix1.setText(String.valueOf(e.getFrais()));
                    places1.setText(String.valueOf(e.getNombrePlaces()));
                    image1.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    FadeTransition ft = new FadeTransition(Duration.millis(1500), image1);

                    e1 = e;
                    System.out.println("Image 1 : " + e.getDestination());
                } else if ((i - first) % 3 == 1) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre2.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre2.setText("Camping à " + e.getDestination());
                    }
                    date2.setText(e.getDateHeureDepart().toString());
                    prix2.setText(String.valueOf(e.getFrais()));
                    places2.setText(String.valueOf(e.getNombrePlaces()));
                    image2.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    e2 = e;
                    System.out.println("Image 2 : " + e.getDestination());
                } else if ((i - first) % 3 == 2) {
                    if (e.getEvenementType().equals("randonnee")) {
                        titre3.setText("Randonnée " + e.getType() + " à " + e.getDestination());
                    } else {
                        titre3.setText("Camping à " + e.getDestination());
                    }
                    date3.setText(e.getDateHeureDepart().toString());
                    prix3.setText(String.valueOf(e.getFrais()));
                    places3.setText(String.valueOf(e.getNombrePlaces()));
                    e3 = e;
                    image3.setImage(new Image(imageSource + e.getImages().get(0).getChemin()));
                    System.out.println("Image 3 " + e.getDestination());
                }

            }
        }
    }

    public void goTo(MouseEvent ev, String location) {
        try {

            Parent test = FXMLLoader.load(getClass().getResource(location));
            StackPane splashScreen = FXMLLoader.load(getClass().getResource("/tahwissa/desktop/splashScreen.fxml"));

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
