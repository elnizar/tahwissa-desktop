/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Creneau;
import entity.Evenement;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Meedoch
 */
public interface EvenementServiceInterface {

    public void ajouterEvenement(Evenement e, List<File> images, List<Creneau> planning);

    public void supprimerEvenement(Evenement e);

    public List<Evenement> getBoosted() throws SQLException;

    public List<Evenement> getByUser(Integer user_id) throws SQLException;

    public List<Evenement> getAvailable() throws SQLException;

    public List<Evenement> getAllEvents();

    public void modifierEvenement(Evenement e, List<File> images, List<Creneau> planning);

    public Integer persist(Evenement evenement) throws SQLException;

    public void remove(Evenement e);

    public int update(Evenement evenement) throws SQLException;

    public List<Evenement> getAll() throws SQLException;
    
    
    public List<Evenement> getEnAttente();
}
