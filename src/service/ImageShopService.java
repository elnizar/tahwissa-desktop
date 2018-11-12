/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Article;
import entity.ImageShop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Checksum;
import util.MyConnection;

/**
 *
 * @author esprit
 */
public class ImageShopService implements ImageShopServiceInterface{
    
    private final Connection connection = MyConnection.getInstance();
    
    private static ImageShopService imageService;
    
    private ImageShopService() {
        
    }
    
    public static ImageShopService getImageShopService() {
        if (null == imageService) {
            imageService = new ImageShopService();
        }
        return imageService;
    }

    @Override
    public void saveImage(File file, int articleId) throws Exception {
        ArticleService articleService = ArticleService.getArticleService();
         String imageName = Checksum.createChecksum(file.getAbsolutePath());
        String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
        String filePath = "C:\\xampp\\htdocs\\tahwissa\\web\\images\\articles\\" + imageName + extension;
        File dest = new File(filePath);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Article article = articleService.findById(articleId);
        System.out.println("Article ID "+article.getId()+" "+article.getLibelle());
        ImageShop image = new ImageShop(imageName + extension, article);
        System.out.println("persisting " + image.getImageName() + " id : " + image.getArticle().getId());
        this.persist(image);
    }
    
    public int imageSave(File file) throws Exception {
        ArticleService articleService = ArticleService.getArticleService();
         String imageName = Checksum.createChecksum(file.getAbsolutePath());
        String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
        String filePath = "C:\\xampp\\htdocs\\tahwissa\\web\\images\\articles\\" + imageName + extension;
        File dest = new File(filePath);
        Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    
        ImageShop image = new ImageShop(imageName + extension);
        System.out.println("persisting " + image.getImageName());
        return this.persist(image);
    }

    @Override
    public void supprimerImagesArticle(int id) {
        Article article = new Article();
        article.setId(id);
        List<ImageShop> images = this.getImagesByProduit(article);
        images.forEach((image) -> {
            this.remove(image);
        });
    }

    @Override
    public List<ImageShop> getImagesByArticle(int id) {
        Article article = new Article();
        article.setId(id);
        List<ImageShop> images = this.getImagesByProduit(article);
        return images;
    }
    
    public Integer persist(ImageShop image) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO image_shop (article, image_name, update_at) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            if (image.getArticle() != null ) {
                ps.setLong(1, image.getArticle().getId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, image.getImageName());
            ps.setTimestamp(3, image.getUpdateAt());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(ImageShop image) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM image_shop where id=?");
            ps.setLong(1, image.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ImageShop> getImagesByProduit(Article article) {
        ArticleService articleService = ArticleService.getArticleService();
        List<ImageShop> images = new ArrayList<ImageShop>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from image_shop where article = ?");
            ps.setLong(1, article.getId());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                ImageShop image = new ImageShop(rs.getString("image_name"), rs.getTimestamp("update_at"),articleService.findById(article.getId()));
                image.setId(rs.getInt("id"));
                images.add(image);
            }
            System.out.println(images.size());
            return images;
        } catch (SQLException ex) {
            // Logger.getLogger(ImageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        }

    }
    
    public ImageShop getImageArticle(int id) {
        String sql = "SELECT * FROM `image_shop` WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ImageShop image = new ImageShop();
            image.setId(id);
            image.setImageName(rs.getString("image_name"));
            image.setUpdateAt(rs.getTimestamp("update_at"));
            
            return image;
            
        } catch (SQLException ex) {
            //Logger.getLogger(ImageShopService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
}
