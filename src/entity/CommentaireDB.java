/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.MyConnection;

/**
 *
 * @author BRAHMI
 */
public class CommentaireDB {
        private final static Connection connection = MyConnection.getInstance();

    
    
    public static List<Commentaire> getCommentaires(int publicationId) throws SQLException
    {
        List<Commentaire> listCommentaires = new ArrayList<>();

        String sql = "SELECT * FROM commentaire WHERE commentaire.publication_id = ? ";
      
        PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
        preparedStatement.setInt(1, publicationId);

       //ResultSet  
       ResultSet rs = preparedStatement.executeQuery();  
       while(rs.next())
       {
           Commentaire commentaire = new Commentaire();
           commentaire.setId(rs.getInt("id"));
           commentaire.setContenu(rs.getString("contenu"));
           commentaire.setDateHeureCommentaire(rs.getTimestamp("dateHeureCommentaire"));
           listCommentaires.add(commentaire);       
       }  
        return listCommentaires;
    }
    
    public static int addCommentaire(Commentaire commentaire)
    {
        int result = 0;
        
        try
        {
           String sql = "INSERT INTO commentaire(publication_id, contenu, dateHeureCommentaire, commentateur_id)VALUES(?, ?, ?, ?) ";     
        
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setInt(1, commentaire.getPublication_id());
           preparedStatement.setString(2, commentaire.getContenu());
           preparedStatement.setTimestamp(3, commentaire.getDateHeureCommentaire());
           preparedStatement.setInt(4, commentaire.getCommentateur_id());
           
           result = preparedStatement.executeUpdate();

           

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return result;
       
    }
    
    public static int editCommentaire(Commentaire commentaire)
    {
        
        int result = 0;
        try
        {
            String sql = "UPDATE commentaire SET contenu = ? WHERE commentaire.id = ?";
   
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);

           preparedStatement.setString(1, commentaire.getContenu());
           preparedStatement.setInt(2, commentaire.getId());

           result = preparedStatement.executeUpdate();

        

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static void deleteCommentaire(Commentaire commentaire)
    {
        int result = 0;
        try
        {
           String sql = "DELETE FROM commentaire WHERE commentaire.id = ?";      
          
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);

           preparedStatement.setInt(1, commentaire.getId());

           result = preparedStatement.executeUpdate();


        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    

    
}
