/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evenement;
import entity.Image;
import entity.Publication;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Checksum;
import util.MyConnection;

/**
 *
 * @author Meedoch
 */
public class ImageService implements ImageServiceInterface {

    Connection c = MyConnection.getInstance();
    public final static String imageUpload = "C:\\xampp\\htdocs\\tahwissa\\web\\uploads\\images\\publications\\";
    public final static String evenementUpload = "C:\\xampp\\htdocs\\tahwissa\\web\\images\\evenements\\";
    @Override
    public void saveImage(File t, Integer eventId) throws Exception {
        String s = Checksum.createChecksum(t.getAbsolutePath());
        String extension = t.getName().substring(t.getName().lastIndexOf("."), t.getName().length());
        String filePath =  evenementUpload + s + extension;
        File dest = new File(filePath);
        Files.copy(t.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Image i = new Image(eventId, s + extension);
        System.out.println("persisting " + i.getChemin() + " id : " + i.getEvenement_id());
        this.persist(i);
    }
    
    public void saveImagePub(File t, Integer eventId) throws Exception {
        String s = Checksum.createChecksum(t.getAbsolutePath());
        String extension = t.getName().substring(t.getName().lastIndexOf("."), t.getName().length());
        String filePath = imageUpload + s + extension;
        File dest = new File(filePath);
        Files.copy(t.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Image i = new Image();
        i.setChemin("uploads/images/publications/"+s + extension);
        i.setPublication_id(eventId);
        System.out.println("Publication ID: "+eventId);
        System.out.println("persisting " + i.getChemin() + " id : " + i.getPublication_id());
        this.persistPub(i);
    }
    
    @Override
    public void supprimerImagesEvenement(Integer id) {
        Evenement e = new Evenement();
        e.setId(id);
        List<Image> images = this.getImagesByEvent(e);
        images.forEach((t) -> {
            this.remove(t);
        });
    }
    
    public void supprimerImagesPublication(Integer id) {
        Publication e = new Publication();
        e.setId(id);
        List<Image> images = this.getImagesByPub(e);
        images.forEach((t) -> {
            this.remove(t);
        });
    }
    @Override
    public List<Image> getImagesByEvenement(Integer id) {
        Evenement e = new Evenement();
        e.setId(id);
        List<Image> images = this.getImagesByEvent(e);
        return images;
    }
    
    public List<Image> getImagesByPublication(Integer id) {
        Publication e = new Publication();
        e.setId(id);
        List<Image> images = this.getImagesByPub(e);
        return images;
    }

    public void persist(Image i) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO image (evenement_id,chemin) values (?,?)");
            ps.setLong(1, i.getEvenement_id());
            ps.setString(2, i.getChemin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void persistPub(Image i) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO image (publication_id,chemin) values (?,?)");
            ps.setLong(1, i.getPublication_id());
            ps.setString(2, i.getChemin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Image i) {
        try {
            PreparedStatement ps = c.prepareStatement("DELETE FROM image where id=?");
            ps.setLong(1, i.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Image> getImagesByEvent(Evenement e) {
        List<Image> images = new ArrayList<Image>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * from image where evenement_id=?");
            ps.setLong(1, e.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Image i = new Image(e.getId(), rs.getString("chemin"));
                i.setId(rs.getInt("id"));
                images.add(i);
            }
            System.out.println(images.size());
            return images;
        } catch (SQLException ex) {
            // Logger.getLogger(ImageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private List<Image> getImagesByPub(Publication e) {
         List<Image> images = new ArrayList<Image>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * from image where publication_id=?");
            ps.setLong(1, e.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Image i = new Image(e.getId(), rs.getString("chemin"));
                i.setId(rs.getInt("id"));
                images.add(i);
            }
            System.out.println(images.size());
            return images;
        } catch (SQLException ex) {
            // Logger.getLogger(ImageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
