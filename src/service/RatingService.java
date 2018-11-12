/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evenement;
import entity.Rating;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class RatingService implements RatingServiceInterface{
    Connection c = MyConnection.getInstance();
    private EvenementService eventService = new EvenementService();
    public Evenement rate(Evenement e, int id_user, double value){
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * from rating where user_id=? and event_id=?");
            ps.setInt(1, id_user);
            ps.setInt(2, e.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                double oldRating = rs.getDouble("value");
                double newOverall = e.getRating()*e.getNombreRates()-oldRating+value;
                newOverall= newOverall/e.getNombreRates();
                e.setRating(newOverall);
                PreparedStatement ratingUpdateStatement = c.prepareStatement("UPDATE rating set value=? where user_id=? and event_id=?");
                ratingUpdateStatement.setDouble(1, value);
                ratingUpdateStatement.setInt(2, id_user);
                ratingUpdateStatement.setInt(3, e.getId());
                ratingUpdateStatement.execute();
                eventService.update(e);
                
            }
            else{
                double newRating= e.getRating()*e.getNombreRates()+value;
                e.setNombreRates(e.getNombreRates()+1);
                newRating= newRating/e.getNombreRates();
                e.setRating(newRating);
                PreparedStatement ratingAddStatement = c.prepareCall("INSERT into rating (user_id,event_id,value) values (?,?,?)");
                ratingAddStatement.setInt(1, id_user);
                ratingAddStatement.setInt(2, e.getId());
                ratingAddStatement.setDouble(3, value);
                ratingAddStatement.execute();
                eventService.update(e);
               
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RatingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }
}
