/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.mesparticipations;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.MonthView;
import com.calendarfx.view.WeekView;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entity.Evenement;
import entity.Participation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import service.EvenementService;
import service.EvenementServiceInterface;
import service.ParticipationService;
import service.ParticipationServiceInterface;
import tahwissa.desktop.FXMLDocumentController;
import tahwissa.desktop.LoginManager;

/**
 * FXML Controller class
 *
 * @author Meedoch
 */
public class ParticipationController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private BorderPane borderPane;
    private ParticipationServiceInterface service = new ParticipationService();
    private EvenementServiceInterface serviceEvent = new EvenementService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCalendar();
        initDrawer();
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

    public void initCalendar() {
        try {
            List<Participation> listePart = service.getParticipationsByUser(LoginManager.getUser().getId());
            CalendarView calendar = new CalendarView();
            //MonthView calendar = new MonthView();

            Calendar participations = new Calendar("Participations");
            participations.setStyle(Calendar.Style.STYLE1);
            listePart.forEach((t) -> {
                Evenement e = t.getE();
                Entry<String> entry = new Entry<>(t.getE().getEvenementType() + " à " + t.getE().getDestination());
                entry.setLocation(e.getDestination());
                entry.setFullDay(true);
                if (e.getEvenementType().equals("randonnee")) {
                    entry.setInterval(e.getDateHeureDepart().toLocalDateTime().toLocalDate(), e.getDateHeureDepart().toLocalDateTime().toLocalDate());
                } else {
                   // System.out.println(e.getEvenementType());
                    entry.setInterval(e.getDateHeureDepart().toLocalDateTime().toLocalDate(), e.getDateHeureDepart().toLocalDateTime().toLocalDate().plusDays(e.getDuree() - 1));
                }
                participations.addEntry(entry);
            });

            List<Evenement> listeEvents = serviceEvent.getByUser(LoginManager.getUser().getId());
            Calendar evenementsOrganisés = new Calendar("Evenements organisés");
            evenementsOrganisés.setStyle(Calendar.Style.STYLE4);
            listeEvents.forEach((t) -> {
                Evenement e = t;
                Entry<String> entry = new Entry<>(t.getEvenementType() + " à " + t.getDestination());
                entry.setLocation(e.getDestination());
                entry.setFullDay(true);

                if (e.getEvenementType().equals("randonnee")) {
                    entry.setInterval(e.getDateHeureDepart().toLocalDateTime().toLocalDate(), e.getDateHeureDepart().toLocalDateTime().toLocalDate());
                } else {
                    //System.out.println(e.getEvenementType());
                    entry.setInterval(e.getDateHeureDepart().toLocalDateTime().toLocalDate(), e.getDateHeureDepart().toLocalDateTime().toLocalDate().plusDays(e.getDuree() - 1));
                }
                evenementsOrganisés.addEntry(entry);
            });

            CalendarSource myCalendarSource = new CalendarSource("Mon calendrier");
            myCalendarSource.getCalendars().add(participations);
            myCalendarSource.getCalendars().add(evenementsOrganisés);
            calendar.getCalendarSources().clear();
            calendar.getCalendarSources().add(myCalendarSource);
            borderPane.setCenter(calendar);
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
