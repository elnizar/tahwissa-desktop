/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.organiser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import entity.Creneau;
import entity.Evenement;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import service.EvenementService;
import service.EvenementServiceInterface;
import service.TestService;
import tahwissa.desktop.FXMLDocumentController;
import static tahwissa.desktop.modifierrando.ModifierController.event;
import util.Notification;

/**
 *
 * @author Meedoch
 */
public class OrganiserController implements Initializable, MapComponentInitializedListener {

    private EvenementServiceInterface service = new EvenementService();
    private Evenement event = new Evenement();
    private List<Label> debutsLe = new ArrayList<>();
    private List<Label> debutsA = new ArrayList<>();
    private List<Label> finsLe = new ArrayList<>();
    private List<Label> finsA = new ArrayList<>();
    private List<JFXDatePicker> datesDebut;
    private List<JFXDatePicker> heuresDebut;
    private List<JFXDatePicker> datesFin;
    private List<JFXDatePicker> heuresFin;
    private List<JFXTextArea> descriptions;
    private List<ValidatorBase> firstTabValidators;
    @FXML
    private GoogleMapView mapView;
    private List<Creneau> planning;
    private GoogleMap map;
    private List<File> files = new ArrayList<File>();
    @FXML
    private AnchorPane anchorPane;
    private int nbCreneaux = 1;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTabPane tabpane;

    @FXML
    private AnchorPane creneaux;

    @FXML
    private JFXTextField nbPlaces;

    @FXML
    private JFXTextField frais;

    @FXML
    private JFXTextField lieuRassemblement;

    @FXML
    private JFXDatePicker dateDepart;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextArea reglement;

    @FXML
    private JFXDatePicker heureDepart;

    @FXML
    private JFXButton submit;

    @FXML
    private AnchorPane campingform;

    @FXML
    private JFXTextField nbj;

    @FXML
    private JFXTextField typeRando;

    @FXML
    private JFXCheckBox randonnee;

    private Marker marker = null;

    @FXML
    private JFXCheckBox camping;

    @FXML
    private JFXTextField distanceParcourue;

    @FXML
    private Label planningError;

    @FXML
    private Label dateDepartError;

    @FXML
    private JFXComboBox<Label> difficulte;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private ImageView next;

    @FXML
    private ImageView prev;

    @FXML
    private Separator separator;
    private Separator lastSep = separator;
    @FXML
    private Label debutLe0;

    @FXML
    private JFXDatePicker dateDebut0;

    @FXML
    private Label debutA0;

    @FXML
    private JFXDatePicker heureDebut0;

    @FXML
    private Label finLe0;

    @FXML
    private JFXDatePicker dateFin0;

    @FXML
    private Label finA0;

    @FXML
    private JFXDatePicker heureFin0;

    @FXML
    private JFXTextArea description0;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private Label filesLabel;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDrawer();
        mapView.addMapInializedListener(this);

        difficulte.getItems().add(new Label("Facile"));
        difficulte.getItems().add(new Label("Moyenne"));
        difficulte.getItems().add(new Label("Difficile"));
        HBox hb = new HBox();
        Label labelDebut = new Label("Debut");
        Label labelFin = new Label("Fin");
        Label labelDescription = new Label("Description");
        hb.getChildren().add(labelDebut);
        hb.getChildren().add(labelFin);
        hb.getChildren().add(labelDescription);
        lastSep = separator;
        user = TestService.getMembre();
        datesDebut = new ArrayList<JFXDatePicker>();
        heuresDebut = new ArrayList<JFXDatePicker>();
        datesFin = new ArrayList<JFXDatePicker>();
        heuresFin = new ArrayList<JFXDatePicker>();
        descriptions = new ArrayList<JFXTextArea>();
        datesDebut.add(dateDebut0);
        heuresDebut.add(heureDebut0);
        datesFin.add(dateFin0);
        heuresFin.add(heureFin0);
        descriptions.add(description0);
        initValidators();
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
    void back(MouseEvent event) {
        SingleSelectionModel<Tab> s = tabpane.getSelectionModel();
        s.getSelectedItem().setDisable(true);

        s.selectPrevious();
        s.getSelectedItem().setDisable(false);

    }

    @FXML
    void next(MouseEvent event) {
        SingleSelectionModel<Tab> s = tabpane.getSelectionModel();
        s.getSelectedItem().setDisable(true);
        s.selectNext();
        System.out.println(heureDepart);
        s.getSelectedItem().setDisable(false);
    }

    @FXML
    void campingSelected(ActionEvent event) {
        randonnee.setSelected(false);
        nbj.setDisable(false);
        typeRando.setDisable(true);
        distanceParcourue.setDisable(true);
        difficulte.setDisable(true);
    }

    @FXML
    void randonneeSelected(ActionEvent event) {
        camping.setSelected(false);
        nbj.setDisable(true);
        typeRando.setDisable(false);
        distanceParcourue.setDisable(false);
        difficulte.setDisable(false);
    }

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.8869, 9.5375))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(6);

        map = mapView.createMap(mapOptions);
        if (marker != null) {
            map.addMarker(marker);
        }
        map.addUIEventHandler(UIEventType.click, (jso) -> {
            JSObject ob = (JSObject) jso.getMember("latLng");
            Double lat = Double.valueOf(ob.call("lat").toString());
            Double lng = Double.valueOf(ob.call("lng").toString());
            GeocodingService s = new GeocodingService();
            s.reverseGeocode(lat, lng, (grs, gs) -> {
                if (gs.equals(gs.OK)) {
                    LatLong clickedLocation = new LatLong(lat, lng);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(clickedLocation);
                    markerOptions.animation(Animation.DROP);
                    event.setDestination(grs[0].getAddressComponents().get(1).getLongName() + ", " + grs[0].getAddressComponents().get(2).getLongName());
                    System.out.println(event.getDestination());
                    if (marker != null) {
                        map.removeMarker(marker);
                        marker = new Marker(markerOptions);
                        map.addMarker(marker);
                        InfoWindow window = new InfoWindow();
                        InfoWindowOptions infoOpt = new InfoWindowOptions();
                        infoOpt.content(event.getDestination());
                        window.setOptions(infoOpt);

                        window.open(map, marker);
                    } else {
                        marker = new Marker(markerOptions);
                        map.addMarker(marker);
                        InfoWindow window = new InfoWindow();
                        InfoWindowOptions infoOpt = new InfoWindowOptions();
                        infoOpt.content(event.getDestination());
                        window.setOptions(infoOpt);

                        window.open(map, marker);
                    }
                    System.out.println(marker);

                }
            });

        });

    }

    void initValidators() {
        firstTabValidators = new ArrayList<>();
        NumberValidator nbPlacesValidator = new NumberValidator();
        nbPlacesValidator.setMessage("Nombre invalide");
        nbPlaces.getValidators().add(nbPlacesValidator);
        nbPlaces.focusedProperty().addListener((observable) -> {

            nbPlaces.validate();
        });
        firstTabValidators.add(nbPlacesValidator);
        DoubleValidator fraisValidator = new DoubleValidator();
        fraisValidator.setMessage("Frais invalides");
        frais.getValidators().add(fraisValidator);
        frais.focusedProperty().addListener((observable) -> {
            frais.validate();
        });
        firstTabValidators.add(fraisValidator);

        RequiredFieldValidator lieuRassemblementValidator = new RequiredFieldValidator();
        lieuRassemblementValidator.setMessage("Champs requis");
        lieuRassemblement.getValidators().add(lieuRassemblementValidator);
        lieuRassemblementValidator.focusedProperty().addListener((observable) -> {
            lieuRassemblement.validate();
        });
        firstTabValidators.add(lieuRassemblementValidator);

        NumberValidator nbJoursValidator = new NumberValidator();
        nbJoursValidator.setMessage("Nombre invalide");
        nbj.getValidators().add(nbJoursValidator);
        nbj.focusedProperty().addListener((observable) -> {
            nbj.validate();
        });
        firstTabValidators.add(nbJoursValidator);

        RequiredFieldValidator typeRandoValidator = new RequiredFieldValidator();
        typeRando.getValidators().add(typeRandoValidator);
        typeRandoValidator.setMessage("Type requis");
        typeRando.focusedProperty().addListener((observable) -> {
            typeRando.validate();
        });
        firstTabValidators.add(typeRandoValidator);

        DoubleValidator distanceParcourueValidator = new DoubleValidator();
        distanceParcourue.getValidators().add(distanceParcourueValidator);
        distanceParcourueValidator.setMessage("Distance invalide");
        distanceParcourue.focusedProperty().addListener((observable) -> {
            distanceParcourue.validate();
        });
        firstTabValidators.add(distanceParcourueValidator);

        RequiredFieldValidator descriptionValidator = new RequiredFieldValidator();
        descriptionValidator.setMessage("Description requise");
        description.getValidators().add(descriptionValidator);
        description.focusedProperty().addListener((observable) -> {
            description.validate();
        });
        firstTabValidators.add(descriptionValidator);

        RequiredFieldValidator reglementValidator = new RequiredFieldValidator();
        reglementValidator.setMessage("Réglement requis");
        reglement.getValidators().add(reglementValidator);
        reglement.focusedProperty().addListener((observable) -> {
            reglement.validate();
        });
        firstTabValidators.add(reglementValidator);

    }

    @FXML
    void addCreneau(MouseEvent event) {

        Label debutLe = new Label("Le");
        debutLe.setPrefWidth(debutLe0.getPrefWidth());
        debutLe.setPrefHeight(debutLe0.getPrefHeight());
        debutLe.setLayoutX(debutLe0.getLayoutX());
        debutLe.setLayoutY(debutLe0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(debutLe);

        Label debutA = new Label("à");
        debutA.setPrefWidth(debutA0.getPrefWidth());
        debutA.setPrefHeight(debutA0.getPrefHeight());
        debutA.setLayoutX(debutA0.getLayoutX());
        debutA.setLayoutY(debutA0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(debutA);

        JFXDatePicker dateDebut = new JFXDatePicker();
        dateDebut.setPrefWidth(dateDebut0.getPrefWidth());
        dateDebut.setPrefHeight(dateDebut0.getPrefHeight());
        dateDebut.setLayoutX(dateDebut0.getLayoutX());
        dateDebut.setLayoutY(dateDebut0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(dateDebut);

        JFXDatePicker heureDebut = new JFXDatePicker();
        heureDebut.setPrefWidth(heureDebut0.getPrefWidth());
        heureDebut.setPrefHeight(heureDebut0.getPrefHeight());
        heureDebut.setLayoutX(heureDebut0.getLayoutX());
        heureDebut.setLayoutY(heureDebut0.getLayoutY() + 70 * nbCreneaux);
        heureDebut.setShowTime(true);
        creneaux.getChildren().add(heureDebut);

        Label finLe = new Label("Le");
        finLe.setPrefWidth(finLe0.getPrefWidth());
        finLe.setPrefHeight(finLe0.getPrefHeight());
        finLe.setLayoutX(finLe0.getLayoutX());
        finLe.setLayoutY(finLe0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(finLe);

        Label finA = new Label("à");
        finA.setPrefWidth(finA0.getPrefWidth());
        finA.setPrefHeight(finA0.getPrefHeight());
        finA.setLayoutX(finA0.getLayoutX());
        finA.setLayoutY(finA0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(finA);

        JFXDatePicker dateFin = new JFXDatePicker();
        dateFin.setPrefWidth(dateFin0.getPrefWidth());
        dateFin.setPrefHeight(dateFin0.getPrefHeight());
        dateFin.setLayoutX(dateFin0.getLayoutX());
        dateFin.setLayoutY(dateFin0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(dateFin);

        JFXDatePicker heureFin = new JFXDatePicker();
        heureFin.setPrefWidth(heureFin0.getPrefWidth());
        heureFin.setPrefHeight(heureFin0.getPrefHeight());
        heureFin.setLayoutX(heureFin0.getLayoutX());
        heureFin.setLayoutY(heureFin0.getLayoutY() + 70 * nbCreneaux);
        heureFin.setShowTime(true);
        creneaux.getChildren().add(heureFin);

        JFXTextArea description = new JFXTextArea();
        description.setPrefWidth(description0.getPrefWidth());
        description.setPrefHeight(description0.getPrefHeight());
        description.setLayoutX(description0.getLayoutX());
        description.setLayoutY(description0.getLayoutY() + 70 * nbCreneaux);
        creneaux.getChildren().add(description);

        Separator s = new Separator(Orientation.HORIZONTAL);
        s.setPrefWidth(separator.getPrefWidth());
        s.setLayoutX(separator.getLayoutX());
        s.setLayoutY(separator.getLayoutY() + 70 * nbCreneaux);

        creneaux.getChildren().add(s);

        datesDebut.add(dateDebut);
        heuresDebut.add(heureDebut);
        datesFin.add(dateFin);
        heuresFin.add(heureFin);
        descriptions.add(description);
        debutsLe.add(debutLe);
        debutsA.add(debutA);
        finsLe.add(finLe);
        finsA.add(finA);
        nbCreneaux++;
    }

    @FXML
    void deleteCreneau(MouseEvent event) {
        if (nbCreneaux != 1) {
            List<Node> toDelete = new ArrayList<>();
            for (int i = creneaux.getChildren().size() - 10; i < creneaux.getChildren().size(); i++) {

                Node n = creneaux.getChildren().get(i);
                //System.out.println(n);
                //creneaux.getChildren().remove(n);
                toDelete.add(n);
            }
            toDelete.forEach((e) -> {
                creneaux.getChildren().remove(e);
            });
            nbCreneaux--;
            datesDebut.remove(nbCreneaux);
            heuresDebut.remove(nbCreneaux);
            datesFin.remove(nbCreneaux);
            heuresFin.remove(nbCreneaux);
            descriptions.remove(nbCreneaux);
            debutsLe.remove(nbCreneaux);
            debutsA.remove(nbCreneaux);
            finsLe.remove(nbCreneaux);
            finsA.remove(nbCreneaux);
        }
    }
    private boolean error = false;

    @FXML
    void openFileDialog(ActionEvent event) {
        System.out.println("test");
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une image");
        files = fileChoser.showOpenMultipleDialog(theStage);
        checkFiles();
        if (error) {
            filesLabel.setText("Fichiers invalides");
            files = new ArrayList<File>();
            error = false;
        } else {
            filesLabel.setText(files.size() + " fichier(s) choisi(s)");
        }
    }

    public void checkFiles() {
        if (files == null) {
            files = new ArrayList<File>();
            error = false;
            return;
        }
        files.forEach((t) -> {
            Path srcPath = t.toPath();
            try {
                if (!Files.probeContentType(srcPath).contains("image")) {
                    error = true;
                }
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public LocalDateTime computeTime(JFXDatePicker date, JFXDatePicker time) {
        return date.getValue().atTime(time.getTime());
    }

    @FXML
    void handleForm(ActionEvent e) {
        if ((dateDepart.getValue() == null) || (heureDepart.getTime() == null)) {
            tabpane.getSelectionModel().select(0);
            tabpane.getTabs().get(0).setDisable(false);
            tabpane.getTabs().get(2).setDisable(true);
            dateDepartError.setText("Date et heure départ requis");
            return;
        }
        LocalDateTime depart = computeTime(dateDepart, heureDepart);
        if (depart.isBefore(LocalDateTime.now())) {
            tabpane.getSelectionModel().select(0);
            tabpane.getTabs().get(0).setDisable(false);
            tabpane.getTabs().get(2).setDisable(true);
            dateDepartError.setText("Date départ invalide");
            return;
        } else {
            Timestamp t = Timestamp.valueOf(depart);
            this.event.setDateHeureDepart(t);
        }
        dateDepartError.setText("");
        for (int i = 0; i < firstTabValidators.size(); i++) {
            if (firstTabValidators.get(i).getHasErrors()) {
                tabpane.getSelectionModel().select(0);
                tabpane.getTabs().get(0).setDisable(false);
                tabpane.getTabs().get(2).setDisable(true);
                // System.out.println("error in " + firstTabValidators.get(i));
                return;
            }
        }
        if (files.isEmpty()) {
            tabpane.getSelectionModel().select(0);
            tabpane.getTabs().get(0).setDisable(false);
            tabpane.getTabs().get(2).setDisable(true);
            filesLabel.setText("Veuillez télécharger des images");
            return;
        }

        if (this.event.getDestination().isEmpty()) {
            tabpane.getSelectionModel().select(1);
            tabpane.getTabs().get(1).setDisable(false);
            tabpane.getTabs().get(2).setDisable(true);
            return;
        }
        planning = new ArrayList<>();

        for (int i = 0; i < datesDebut.size(); i++) {
            LocalDate dDebut = datesDebut.get(i).getValue();
            LocalTime hDebut = heuresDebut.get(i).getTime();
            LocalDate dFin = datesFin.get(i).getValue();
            LocalTime hFin = heuresFin.get(i).getTime();
            if ((dDebut != null) && (hDebut != null) && (dFin != null) && (hFin != null)) {
                LocalDateTime debut = computeTime(datesDebut.get(i), heuresDebut.get(i));
                LocalDateTime fin = computeTime(datesFin.get(i), heuresFin.get(i));
                if (i == 0) {
                    if (debut.isBefore(depart)) {
                        planningError.setText("Date début du premier creneau invalide");
                        return;
                    }
                }
                if (debut.isAfter(fin)) {
                    planningError.setText("Dates du creneau " + (i + 1) + "invalides.");
                    return;
                }
                if (i != 0) {
                    if (Timestamp.valueOf(debut).before(planning.get(i - 1).getDateFin())) {
                        planningError.setText("Dates début du creneau " + (i + 1) + "< Date fin du creneau " + (i));
                        return;
                    }
                }
                if (descriptions.get(i).getText().equals("")) {
                    planningError.setText("Description du planning " + (i + 1) + " requise");
                    return;
                }
                Creneau c = new Creneau();
                c.setDescription(descriptions.get(i).getText());
                c.setDateDebut(Timestamp.valueOf(debut));
                c.setDateFin(Timestamp.valueOf(fin));
                planning.add(c);
            } else {
                planningError.setText("Veuillez remplir le planning");
                return;
            }
        }
        planningError.setText("");
        planning.forEach((t) -> {
            System.out.println(t.getDateDebut() + " => " + t.getDateFin() + " : " + t.getDescription());
        });

        event.setNombrePlaces(Integer.valueOf(nbPlaces.getText()));
        event.setFrais(Double.valueOf(frais.getText()));
        event.setLieuRassemblement(lieuRassemblement.getText());
        if (camping.isSelected()) {
            event.setEvenementType("camping");
            event.setDuree(Integer.valueOf(nbj.getText()));
        } else {
            event.setEvenementType("randonnee");
            event.setType(typeRando.getText());
            event.setDistanceParcourue(Double.valueOf(distanceParcourue.getText()));
            event.setDifficulte(difficulte.getSelectionModel().selectedItemProperty().get().getText());
        }
        event.setDescription(description.getText());
        event.setReglement(reglement.getText());
        event.setOrganisateur_id(user.getId());
        event.setGuide_id(1);
        event.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
        event.setStatut("En attente");
        event.setBoosted(false);

        service.ajouterEvenement(event, files, planning);
        Notification.createNotification("Succès", "Votre demande d'organisation a été enregistrée");
        goTo(e, "/tahwissa/desktop/myevents/View.fxml");

    }

    public void goTo(ActionEvent ev, String location) {
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
