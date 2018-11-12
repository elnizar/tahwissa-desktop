/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evenement;
import entity.Participation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class ParticipationService implements ParticipationServiceInterface{
    
    private Connection connection = MyConnection.getInstance();
    private EvenementService serviceEvent= new EvenementService();
    public boolean participer(int user_id, Evenement ev) {
        try {

            Participation p = new Participation();
            p.setUser_id(user_id);
            p.setEvent_id(ev.getId());
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO "
                    + "participation (participant_id,evenement_id,dateHeureParticipation,statutPaiement)"
                    + "values (?,?,?,?)");
            ps2.setInt(1, p.getUser_id());
            ps2.setInt(2, p.getEvent_id());
            ps2.setTimestamp(3, p.getDateHeureParticipation());
            ps2.setString(4, p.getStatutPaiement());
            ps2.execute();
            ev.setNombrePlacesPrises(ev.getNombrePlacesPrises()+1);
            serviceEvent.update(ev);
            return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean aParticipe(int user_id, int event_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from participation where participant_id=? and evenement_id=?");
            ps.setInt(1, user_id);
            ps.setInt(2, event_id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                // // System.out.println("déja participé");
                return true;
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return false;
    }
    
    public List<Participation> getParticipationsByUser(int user_id){
         try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from participation where participant_id=?");
            ps.setInt(1, user_id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            List<Participation> participations = new ArrayList<Participation>();
            while (rs.next()) {
                Participation p = new Participation();
                p.setDateHeureParticipation(rs.getTimestamp("dateHeureParticipation"));
                p.setEvent_id(rs.getInt("evenement_id"));
                p.setUser_id(user_id);
                p.setStatutPaiement(rs.getString("statutPaiement"));
                
                Evenement e = serviceEvent.getAllEvents().stream().filter((t) -> {
                    return (t.getId().equals(p.getEvent_id()));
                }).findFirst().get();
                p.setE(e);
                participations.add(p);
            }
            return participations;
        } catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }
    
    
}
