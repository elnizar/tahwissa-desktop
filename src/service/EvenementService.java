/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Creneau;
import entity.Evenement;
import entity.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class EvenementService implements EvenementServiceInterface {

    ImageService serviceImage = new ImageService();
    CreneauService serviceCreneau = new CreneauService();
    private final Connection connection = MyConnection.getInstance();

    @Override
    public void ajouterEvenement(Evenement e, List<File> images, List<Creneau> planning) {
        Integer id = null;
        try {
            id = this.persist(e);
           
            Iterator<File> it = images.iterator();
            while (it.hasNext()) {
                File t = it.next();
                serviceImage.saveImage(t, id);

            }
            Iterator<Creneau> itC = planning.iterator();
            while (itC.hasNext()) {
                Creneau c = itC.next();
                serviceCreneau.ajouterCreneau(c, id);
             
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            if (id != null) {
                serviceImage.supprimerImagesEvenement(id);
                serviceCreneau.supprimerCreneauxEvenement(id);
            }
        }
    }

    public void modifierEvenement(Evenement e, List<File> images, List<Creneau> planning) {
        Integer id = null;
        try {

            id = this.update(e);
            //// System.out.println(id);
            Iterator<File> it = images.iterator();
            while (it.hasNext()) {
                File t = it.next();
                serviceImage.saveImage(t, id);
            }
            serviceCreneau.supprimerCreneauxEvenement(id);
            Iterator<Creneau> itC = planning.iterator();
            while (itC.hasNext()) {
                Creneau c = itC.next();
                serviceCreneau.ajouterCreneau(c, id);
               // // System.out.println("persisting " + c.getDescription() + " id : " + id);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            if (id != null) {
                serviceImage.supprimerImagesEvenement(id);
                serviceCreneau.supprimerCreneauxEvenement(id);
            }
        }
    }

    @Override
    public void supprimerEvenement(Evenement e) {
        // // System.out.println(e.getId());
        serviceCreneau.supprimerCreneauxEvenement(e.getId());
        serviceImage.supprimerImagesEvenement(e.getId());

        try {
            this.remove(e);
            // System.out.println("DELETED");
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public List<Evenement> getAllEvents() {
        List<Evenement> events;
        try {
            events = this.getAll();
            events.forEach((e) -> {
                e.setImages(serviceImage.getImagesByEvenement(e.getId()));
                e.setPlanning(serviceCreneau.getPlanningByEvent(e.getId()));
            });
            return events;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public Integer persist(Evenement evenement) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO evenement (organisateur_id,guide_id,boosted,dateheuredepart,datecreation,description,destination,frais,lieurassemblement,nombreplaces,nombreplacesprises,nombrerates,rating,reglement,statut,evenement_type,duree,difficulte,distanceparcourue,type,permalink,is_commentable,num_comments,last_comment_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        );
        ps.setInt(1, evenement.getOrganisateur_id());
        ps.setInt(2, evenement.getGuide_id());
        ps.setBoolean(3, evenement.isBoosted());
        ps.setTimestamp(4, evenement.getDateHeureDepart());
        ps.setTimestamp(5, evenement.getDateCreation());
        ps.setString(6, evenement.getDescription());
        ps.setString(7, evenement.getDestination());
        ps.setDouble(8, evenement.getFrais());
        ps.setString(9, evenement.getLieuRassemblement());
        ps.setInt(10, evenement.getNombrePlaces());
        ps.setInt(11, evenement.getNombrePlacesPrises());
        ps.setInt(12, evenement.getNombreRates());
        ps.setDouble(13, evenement.getRating());
        ps.setString(14, evenement.getReglement());
        ps.setString(15, evenement.getStatut());
        ps.setString(16, evenement.getEvenementType());
        ps.setInt(17, evenement.getDuree());
        ps.setString(18, evenement.getDifficulte());

        ps.setDouble(19, evenement.getDistanceParcourue());
        ps.setString(20, evenement.getType());
        ps.setString(21, "test");
        ps.setInt(22, 1);
        ps.setInt(23, 0);
        ps.setTimestamp(24, Timestamp.valueOf(LocalDateTime.now()));
        ps.executeUpdate();
        PreparedStatement ps2 = connection.prepareStatement("SELECT id from evenement where organisateur_id=? order by id DESC");
        ps2.setLong(1, evenement.getOrganisateur_id());
        ps2.execute();

        ResultSet rs = ps2.getResultSet();
        rs.next();
        // System.out.println(rs.getInt("id"));
        return rs.getInt("id");

    }

    public void remove(Evenement e) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE from evenement where id=?");
            ps.setInt(1, e.getId());
            ps.executeUpdate();
        } catch (Exception ex) {

        }
    }

    public int update(Evenement evenement) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(
                "UPDATE  evenement set organisateur_id=?,guide_id=?,boosted=?,dateheuredepart=?,datecreation=?,description=?,destination=?,frais=?,lieurassemblement=?,nombreplaces=?,nombreplacesprises=?,nombrerates=?,rating=?,reglement=?,statut=?,evenement_type=?,duree=?,difficulte=?,distanceparcourue=?,type=? WHERE id=?"
        );
        // System.out.println(evenement.getGuide_id());
        if (evenement.getGuide_id() == 0) {
            evenement.setGuide_id(evenement.getOrganisateur_id());
        }
        ps.setInt(1, evenement.getOrganisateur_id());
        ps.setInt(2, evenement.getGuide_id());
        ps.setBoolean(3, evenement.isBoosted());
        ps.setTimestamp(4, evenement.getDateHeureDepart());
        ps.setTimestamp(5, evenement.getDateCreation());
        ps.setString(6, evenement.getDescription());
        ps.setString(7, evenement.getDestination());
        ps.setDouble(8, evenement.getFrais());
        ps.setString(9, evenement.getLieuRassemblement());
        ps.setInt(10, evenement.getNombrePlaces());
        ps.setInt(11, evenement.getNombrePlacesPrises());
        ps.setInt(12, evenement.getNombreRates());
        ps.setDouble(13, evenement.getRating());
        ps.setString(14, evenement.getReglement());
        ps.setString(15, evenement.getStatut());
        ps.setString(16, evenement.getEvenementType());
        ps.setInt(17, evenement.getDuree());
        ps.setString(18, evenement.getDifficulte());
        ps.setDouble(19, evenement.getDistanceParcourue());
        ps.setString(20, evenement.getType());
        ps.setInt(21, evenement.getId());
        ps.executeUpdate();

        PreparedStatement ps2 = connection.prepareStatement("SELECT id from evenement where organisateur_id=? order by id DESC");
        ps2.setLong(1, evenement.getOrganisateur_id());
        ps2.execute();

        ResultSet rs = ps2.getResultSet();
        rs.next();
        // System.out.println(rs.getInt("id"));

        return rs.getInt("id");

    }

    public List<Evenement> getAll() throws SQLException {

        List<Evenement> events = new ArrayList<Evenement>();

        Statement s = connection.createStatement();
        s.execute("SELECT * from evenement");
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));
            e.setOrganisateur_id(rs.getInt("organisateur_id"));
            e.setGuide_id(rs.getInt("guide_id"));
            e.setBoosted(rs.getBoolean("boosted"));
            e.setDateHeureDepart(rs.getTimestamp("dateHeureDepart"));
            e.setDateCreation(rs.getTimestamp("dateCreation"));
            e.setDescription(rs.getString("description"));
            e.setDestination(rs.getString("destination"));
            e.setFrais(rs.getDouble("frais"));
            e.setLieuRassemblement(rs.getString("lieuRassemblement"));
            e.setNombrePlaces(rs.getInt("nombrePlaces"));
            e.setNombrePlacesPrises(rs.getInt("nombrePlacesPrises"));
            e.setNombreRates(rs.getInt("nombreRates"));
            e.setRating(rs.getDouble("rating"));
            e.setReglement(rs.getString("reglement"));
            e.setStatut(rs.getString("statut"));
            e.setEvenementType(rs.getString("evenement_type"));
            e.setDuree(rs.getInt("duree"));
            e.setDistanceParcourue(rs.getDouble("distanceParcourue"));
            e.setType(rs.getString("type"));
            e.setDifficulte(rs.getString("difficulte"));
            List<Image> images = serviceImage.getImagesByEvenement(e.getId());
            e.setImages(images);
            List<Creneau> planning = serviceCreneau.getCreneauxByEvent(e);
            e.setPlanning(planning);
            events.add(e);

        }
        return events;
    }

    @Override
    public List<Evenement> getBoosted() throws SQLException {
        List<Evenement> events = new ArrayList<Evenement>();

        PreparedStatement s = connection.prepareStatement("SELECT * from evenement where boosted=? and dateHeureDepart>? and nombrePlacesPrises!=nombrePlaces and statut=? order by dateCreation desc");
        s.setBoolean(1, true);
        s.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        s.setString(3, "Accepté");
        s.execute();
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));
            e.setOrganisateur_id(rs.getInt("organisateur_id"));
            e.setGuide_id(rs.getInt("guide_id"));
            e.setBoosted(rs.getBoolean("boosted"));
            e.setDateHeureDepart(rs.getTimestamp("dateHeureDepart"));
            e.setDateCreation(rs.getTimestamp("dateCreation"));
            e.setDescription(rs.getString("description"));
            e.setDestination(rs.getString("destination"));
            e.setFrais(rs.getDouble("frais"));
            e.setLieuRassemblement(rs.getString("lieuRassemblement"));
            e.setNombrePlaces(rs.getInt("nombrePlaces"));
            e.setNombrePlacesPrises(rs.getInt("nombrePlacesPrises"));
            e.setNombreRates(rs.getInt("nombreRates"));
            e.setRating(rs.getDouble("rating"));
            e.setReglement(rs.getString("reglement"));
            e.setStatut(rs.getString("statut"));
            e.setEvenementType(rs.getString("evenement_type"));
            e.setDuree(rs.getInt("duree"));
            e.setDistanceParcourue(rs.getDouble("distanceParcourue"));
            e.setType(rs.getString("type"));
            e.setDifficulte(rs.getString("difficulte"));
            List<Creneau> planning = serviceCreneau.getCreneauxByEvent(e);
            e.setPlanning(planning);
            List<Image> images = serviceImage.getImagesByEvenement(e.getId());
            e.setImages(images);
            events.add(e);

        }
        return events;
    }

    @Override
    public List<Evenement> getByUser(Integer user_id) throws SQLException {
        List<Evenement> events = new ArrayList<Evenement>();

        PreparedStatement s = connection.prepareStatement("SELECT * from evenement where organisateur_id=? order by dateCreation desc");
        s.setInt(1, user_id);

        s.execute();
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));
            e.setOrganisateur_id(rs.getInt("organisateur_id"));
            e.setGuide_id(rs.getInt("guide_id"));
            e.setBoosted(rs.getBoolean("boosted"));
            e.setDateHeureDepart(rs.getTimestamp("dateHeureDepart"));
            e.setDateCreation(rs.getTimestamp("dateCreation"));
            e.setDescription(rs.getString("description"));
            e.setDestination(rs.getString("destination"));
            e.setFrais(rs.getDouble("frais"));
            e.setLieuRassemblement(rs.getString("lieuRassemblement"));
            e.setNombrePlaces(rs.getInt("nombrePlaces"));
            e.setNombrePlacesPrises(rs.getInt("nombrePlacesPrises"));
            e.setNombreRates(rs.getInt("nombreRates"));
            e.setRating(rs.getDouble("rating"));
            e.setReglement(rs.getString("reglement"));
            e.setStatut(rs.getString("statut"));
            e.setEvenementType(rs.getString("evenement_type"));
            e.setDuree(rs.getInt("duree"));
            e.setDistanceParcourue(rs.getDouble("distanceParcourue"));
            e.setType(rs.getString("type"));
            e.setDifficulte(rs.getString("difficulte"));
            List<Creneau> planning = serviceCreneau.getCreneauxByEvent(e);
            e.setPlanning(planning);
            List<Image> images = serviceImage.getImagesByEvenement(e.getId());
            e.setImages(images);
            events.add(e);

        }
        return events;
    }

    @Override
    public List<Evenement> getAvailable() throws SQLException {
        List<Evenement> events = new ArrayList<Evenement>();

        PreparedStatement s = connection.prepareStatement("SELECT * from evenement where boosted=? and dateHeureDepart>? and nombrePlacesPrises!=nombrePlaces and statut=? order by dateCreation desc");
        s.setBoolean(1, false);
        s.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        s.setString(3, "Accepté");
        s.execute();
        ResultSet rs = s.getResultSet();
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));
            e.setOrganisateur_id(rs.getInt("organisateur_id"));
            e.setGuide_id(rs.getInt("guide_id"));
            e.setBoosted(rs.getBoolean("boosted"));
            e.setDateHeureDepart(rs.getTimestamp("dateHeureDepart"));
            e.setDateCreation(rs.getTimestamp("dateCreation"));
            e.setDescription(rs.getString("description"));
            e.setDestination(rs.getString("destination"));
            e.setFrais(rs.getDouble("frais"));
            e.setLieuRassemblement(rs.getString("lieuRassemblement"));
            e.setNombrePlaces(rs.getInt("nombrePlaces"));
            e.setNombrePlacesPrises(rs.getInt("nombrePlacesPrises"));
            e.setNombreRates(rs.getInt("nombreRates"));
            e.setRating(rs.getDouble("rating"));
            e.setReglement(rs.getString("reglement"));
            e.setStatut(rs.getString("statut"));
            e.setEvenementType(rs.getString("evenement_type"));
            e.setDuree(rs.getInt("duree"));
            e.setDistanceParcourue(rs.getDouble("distanceParcourue"));
            e.setType(rs.getString("type"));
            e.setDifficulte(rs.getString("difficulte"));
            List<Creneau> planning = serviceCreneau.getCreneauxByEvent(e);
            e.setPlanning(planning);
            List<Image> images = serviceImage.getImagesByEvenement(e.getId());
            e.setImages(images);
            events.add(e);

        }
        return events;
    }

    @Override
    public List<Evenement> getEnAttente() {
        try {
            List<Evenement> events = new ArrayList<Evenement>();

            PreparedStatement s = connection.prepareStatement("SELECT * from evenement where statut=? order by dateCreation desc");
            s.setString(1, "En attente");

            s.execute();
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId(rs.getInt("id"));
                e.setOrganisateur_id(rs.getInt("organisateur_id"));
                e.setGuide_id(rs.getInt("guide_id"));
                e.setBoosted(rs.getBoolean("boosted"));
                e.setDateHeureDepart(rs.getTimestamp("dateHeureDepart"));
                e.setDateCreation(rs.getTimestamp("dateCreation"));
                e.setDescription(rs.getString("description"));
                e.setDestination(rs.getString("destination"));
                e.setFrais(rs.getDouble("frais"));
                e.setLieuRassemblement(rs.getString("lieuRassemblement"));
                e.setNombrePlaces(rs.getInt("nombrePlaces"));
                e.setNombrePlacesPrises(rs.getInt("nombrePlacesPrises"));
                e.setNombreRates(rs.getInt("nombreRates"));
                e.setRating(rs.getDouble("rating"));
                e.setReglement(rs.getString("reglement"));
                e.setStatut(rs.getString("statut"));
                e.setEvenementType(rs.getString("evenement_type"));
                e.setDuree(rs.getInt("duree"));
                e.setDistanceParcourue(rs.getDouble("distanceParcourue"));
                e.setType(rs.getString("type"));
                e.setDifficulte(rs.getString("difficulte"));
                List<Creneau> planning = serviceCreneau.getCreneauxByEvent(e);
                e.setPlanning(planning);
                List<Image> images = serviceImage.getImagesByEvenement(e.getId());
                e.setImages(images);
                events.add(e);

            }
            return events;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
