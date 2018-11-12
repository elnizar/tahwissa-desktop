/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import service.ImageService;
import util.MyConnection;

/**
 *
 * @author BRAHMI
 */
public class PublicationDB 
{
    private final static Connection connection = MyConnection.getInstance();
    
    public static List<Publication> getPublications() throws SQLException
    {
        ImageService imgService = new ImageService();
        List<Publication> listPublications = new ArrayList<>();
       
        
        String sql = "SELECT * FROM publication ";
        
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);


       //ResultSet  
       ResultSet rs = preparedStatement.executeQuery();  
       while(rs.next())
       {
           Publication publication = new Publication();
           System.out.println("Id Publication: "+rs.getInt("id"));
           publication.setId(rs.getInt("id"));
           List<Image> images = imgService.getImagesByPublication(rs.getInt("id"));
           
           publication.setImages(images);
           publication.setTitre(rs.getString("titre"));
           publication.setContenu(rs.getString("contenu"));
           publication.setDateHeurePublication(rs.getTimestamp("dateHeurePublication"));
           
           listPublications.add(publication);       
       }  
       
        return listPublications;
    }
    
    
    public static int addPublication(Publication publication)
    {
        int result = 0;
        
        try
        {
           String sql = "INSERT INTO publication(image_id,titre,contenu)values(?,?,?) ";     
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setInt(1, publication.getImageId());
           preparedStatement.setString(2, publication.getTitre());
           preparedStatement.setString(3, publication.getContenu());
           
           result = preparedStatement.executeUpdate();
           
          
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    public static boolean ajouterPublication(Publication e, File image) {
        Integer id = null;
        ImageService serviceImage = new ImageService();
        try {
            id = persist(e);
            System.out.println("L'id du publication: "+id);
           
                serviceImage.saveImagePub(image, id);
                return true;
           
        } catch (Exception exception) {
            exception.printStackTrace();
            if (id != null) {
                serviceImage.supprimerImagesPublication(id);
            }
            return false;
        }
    }
    
    private static Integer persist(Publication pub) throws SQLException {

        java.sql.PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO publication (titre, contenu, dateHeurePublication, publicitaire_id, video_url) VALUES (?, ?, ?, ?, ?)"
        );
        java.util.Date date = new java.util.Date();
        pub.setDateHeurePublication(new Timestamp(date.getTime()));
        ps.setString(1, pub.getTitre());
        ps.setString(2, pub.getContenu());
        ps.setTimestamp(3, pub.getDateHeurePublication());
        ps.setInt(4, pub.getPublicitaire_id());
        ps.setString(5, "https://www.youtube.com/watch?v=h92BLrfkm3k");
        ps.executeUpdate();
        
        java.sql.PreparedStatement ps2 = connection.prepareStatement("SELECT id from publication where publicitaire_id=? order by id DESC");
        ps2.setLong(1, pub.getPublicitaire_id());
        ps2.execute();

        ResultSet rs = ps2.getResultSet();
        rs.next();
        System.out.println(rs.getInt("id"));
        return rs.getInt("id");

    }

    
    public static void remove(Publication p) {
        try {
            java.sql.PreparedStatement ps = connection.prepareStatement("DELETE from publication where id=?");
            ps.setInt(1, p.getId());
            
            ps.executeUpdate();
        } catch (Exception ex) {

        }
    }
    
    public static Publication getPublication(int id)
    {
        Publication publication = null;
        try
        {
           
           String sql = "SELECT * FROM publication WHERE publication.id = ?";     
      
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setInt(1, id);
               
           ResultSet resultSet = preparedStatement.executeQuery();  
           if(resultSet.next())
           {
                publication = new Publication();
                publication.setImageId(resultSet.getInt("image_id"));
                publication.setTitre(resultSet.getString("titre"));
                publication.setContenu(resultSet.getString("contenu"));
                publication.setDateHeurePublication(resultSet.getTimestamp("dateHeurePublication"));
                
           }
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return publication;
    }
    
    public static int updatePublication(Publication publication)
    {
        
        int result = 0;
        try
        {
            String sql = "UPDATE publication SET titre = ?, contenu = ?, dateHeurePublication = ?, publicitaire_id = ?, video_url = ? WHERE publication.id = ?";
   
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);

           preparedStatement.setString(1, publication.getTitre());
           preparedStatement.setString(2, publication.getContenu());
           preparedStatement.setInt(3, publication.getId());

           result = preparedStatement.executeUpdate();

          
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
     public static boolean modifierPublication(Publication e, File image) {
        Integer id = null;
         ImageService serviceImage = new ImageService();
        try {
            
            id = update(e);
            //System.out.println(id);

                serviceImage.saveImagePub(image, e.getId());
                
          return true;
           
        } catch (Exception exception) {
            exception.printStackTrace();
            if (id != null) {
                serviceImage.supprimerImagesPublication(id);
                
            }
            return false;
        }
    }
    
    public static int deletePublication(Publication publication)
    {
        int result = 0;
        ImageService imgService = new ImageService();
        imgService.supprimerImagesPublication(publication.getId());
        try
        {
           String sql = "DELETE FROM publication WHERE publication.id = ? ";      
      
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);

           preparedStatement.setInt(1, publication.getId());

           result = preparedStatement.executeUpdate();

           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private static Integer update(Publication pub) throws SQLException {
        java.sql.PreparedStatement ps = connection.prepareStatement(
                "UPDATE  publication SET titre = ?, contenu = ?, dateHeurePublication = ?, publicitaire_id = ?, video_url = ? WHERE id=?"
        );
      
         ps.setString(1, pub.getTitre());
        ps.setString(2, pub.getContenu());
        ps.setTimestamp(3, pub.getDateHeurePublication());
        ps.setInt(4, pub.getPublicitaire_id());
        ps.setString(5, "https://www.youtube.com/watch?v=h92BLrfkm3k");
        ps.setInt(6, pub.getId());
      
        ps.executeUpdate();
        
        java.sql.PreparedStatement ps2 = connection.prepareStatement("SELECT id from publication where publicitaire_id=? order by id DESC");
        ps2.setLong(1, pub.getPublicitaire_id());
        ps2.execute();

        ResultSet rs = ps2.getResultSet();
        rs.next();
        System.out.println(rs.getInt("id"));
        
        return rs.getInt("id");

    }
    
}
