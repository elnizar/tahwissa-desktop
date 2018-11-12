/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Creneau;
import entity.Evenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class CreneauService implements CreneauServiceInterface{

    private final Connection c = MyConnection.getInstance();
    @Override
    public void ajouterCreneau(Creneau c, Integer id) {
        c.setEvenement_id(id);
        this.persist(c);
    }
    @Override
    public void supprimerCreneauxEvenement(Integer id) {
        Evenement e = new Evenement();
        e.setId(id);
        List<Creneau> planning = this.getCreneauxByEvent(e);
        planning.forEach((t) -> {
            this.remove(t);
        });
    }
    @Override
    public List<Creneau> getPlanningByEvent(Integer id) {
        Evenement e = new Evenement();
        e.setId(id);
        List<Creneau> planning = this.getCreneauxByEvent(e);
        return planning;
    }

    public void persist(Creneau cr) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT into creneau (evenement_id,debut,fin,description) values (?,?,?,?)");
            ps.setLong(1, cr.getEvenement_id());
            ps.setTimestamp(2, cr.getDateDebut());
            ps.setTimestamp(3, cr.getDateFin());
            ps.setString(4, cr.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(CreneauDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remove(Creneau cr) {
        try {
            PreparedStatement ps = c.prepareStatement("DELETE from creneau where id=?");
            ps.setLong(1, cr.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(CreneauDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Creneau> getCreneauxByEvent(Evenement e) {
        List<Creneau> creneaux = new ArrayList<Creneau>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * from creneau where evenement_id=?");
            ps.setLong(1, e.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Creneau creneau = new Creneau();
                creneau.setEvenement_id(e.getId());
                creneau.setId(rs.getLong("id"));
                creneau.setDateDebut(rs.getTimestamp("debut"));
                creneau.setDateFin(rs.getTimestamp("fin"));
                creneau.setDescription(rs.getString("description"));
                creneaux.add(creneau);
            }

            return creneaux;
        } catch (SQLException ex) {
            //Logger.getLogger(ImageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
