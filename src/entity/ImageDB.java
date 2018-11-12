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
import java.sql.Statement;
import util.MyConnection;

/**
 *
 * @author MUSTAPHA GHLISSI
 */
public class ImageDB 
        
{
    private final static Connection connection = MyConnection.getInstance();
    public static int addImage(Image image)
    {
        int result = 0;
        try
        {
           String sql = "INSERT INTO image(chemin, publication_id)values(?) ";     
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

           preparedStatement.setString(1, image.getChemin());
          
           preparedStatement.executeUpdate();
           
           ResultSet rs = preparedStatement.getGeneratedKeys();
           
            if(rs.next())
            {
                result = rs.getInt(1);
            }
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public static void updateImage(Image image)
    {
        int result = 0;
        try
        {
           String sql = "UPDATE image SET path=? WHERE image.id = ?";
          
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setString(1, image.getChemin());
           preparedStatement.setLong(2, image.getId());
           result = preparedStatement.executeUpdate();
           connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
                 
    }
    public static void deleteImage(Image image)
    {
        int result = 0;
        try
        {
           String sql = "DELETE FROM image WHERE image.id = ?";

           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setLong(1, image.getId());
           result = preparedStatement.executeUpdate();
           connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }    
    }
    
    
    public static Image getImage(int id)
    {
        Image image = null;
        try
        {
           String sql = "SELECT * FROM image WHERE image.id = ?";     
    
           
           PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
           preparedStatement.setInt(1, id);
               
           ResultSet resultSet = preparedStatement.executeQuery();  
           if(resultSet.next())
           {
                image = new Image();
                image.setChemin(resultSet.getString("path"));
           }
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return image;
    }
    
    
}
