/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.prism.impl.BufferUtil;
import entity.Signalisation;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

/**
 *
 * @author User
 */
public class SignalisationService implements SignalisationServiceInterface{
    
    private Connection connection;
    private PreparedStatement ps;
    User u = new User();
    UserService serviceUser= new UserService();
    public SignalisationService () {
                
        connection = MyConnection.getInstance();

    }
    public boolean aSgnale(int user, int user2){
        PreparedStatement ps = null;
        String req = "SELECT * from signalisation where user_id=? and user2_id=?";
        try {
            
            
            ps = connection.prepareStatement(req);
            ps.setInt(1, u.getId());
            ps.setInt(2, u.getId());
            ps.execute();
            ResultSet res= ps.getResultSet();
           if (res.next()){
               return true;
           }
        }
        catch (SQLException ex) {
        }
        return false;

    }

    @Override
    public void ajouterSignalisation(Signalisation s) {
        
        Signalisation Signal = new Signalisation();
        PreparedStatement ps = null;
        User user2 = serviceUser.get(s.getUser2_id());
        
        String req = "INSERT INTO Signalisation(user_id,motif,objet,user2_id) VALUES (?,?,?,?)";
        try 
        {
            ps = connection.prepareStatement(req);
            
            ps.setInt(1, s.getUser_id());
            ps.setString(2, s.getMotif());
            ps.setString(3, s.getObjet());
            ps.setInt(4, s.getUser2_id());
            
            
            
            
            user2.setNombreSignalisations(user2.getNombreSignalisations()+1);
            int nbSignals = user2.getNombreSignalisations();
            PreparedStatement ps2=  connection.prepareStatement("update fos_user set nombre_signalisations=? where id=?");
            ps2.setInt(1, nbSignals);
            ps2.setInt(2, s.getUser2_id());
            System.out.println(nbSignals);
            if (nbSignals==10){
                user2.setBanned(1);
                serviceUser.banUser(s.getUser2_id());
            }
            //EXECUTION
            ps.executeUpdate();
            ps2.executeUpdate();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    
    
}
        
}
