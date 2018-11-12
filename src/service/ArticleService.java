/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Article;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import entity.Achat;
import java.io.File;
import java.sql.SQLType;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import util.MyConnection;

/**
 *
 * @author esprit
 */
public class ArticleService implements ArticleServiceInterface{
    
     private final Connection connection = MyConnection.getInstance();
     private final ImageShopService imageService = ImageShopService.getImageShopService();
     private static ArticleService articleService;
     
     private ArticleService(){
         
     }
     
     public static ArticleService getArticleService() {
         if (ArticleService.articleService == null) {
             articleService = new ArticleService();
         }
         return articleService;
     }
     
     private final String fields = "`description`,"
                + " `libelle`,"
                + " `boosted`,"
                + " `livre`,"
                + " `prix`,"
                + " `etat`,"
                + " `vendu`,"
                + " `membre_id`,"
                + " `update_at`,"
                + " `idImage`,"
                + " `liker_id`";
     
     public void test() {
         System.out.println("Ceci est un test micro!");
     }

    @Override
    public void addArticle(Article article, List<File> images) { 
        System.out.println("Adding Article ..");
        Integer id = null;
         try {
             System.out.println("Article is null ?"+ article == null );
             Iterator<File> it = images.iterator();
                System.out.println("Images is null "+images == null);
                System.out.println("Image Service is null "+imageService == null);
              File image = it.next();
              int idImage = imageService.imageSave(image);
              article.setIdImage(idImage);
              id = persist(article);
              System.out.println("[OK] id image injected ..");
              System.out.println("id article"+ id);
            while (it.hasNext()) {
                image = it.next();
                imageService.saveImage(image, id);
                System.out.println("[OK] Injection image ..");
            }
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
             if (id != null) {
                imageService.supprimerImagesArticle(id);
            }
         }
    }
    
    public int persist(Article article) throws SQLException {
        String sql = "INSERT INTO `article` ("+fields+") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
         PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             statement.setString(1, article.getDescription());
             statement.setString(2, article.getLibelle());
             statement.setBoolean(3, article.isBoosted());
             statement.setBoolean(4, article.isLivre());
             statement.setDouble(5, article.getPrix());
             statement.setString(6, article.getEtat());
             statement.setBoolean(7, article.isVendu());
             statement.setInt(8, article.getMembre().getId());
             statement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
             statement.setInt(10, article.getIdImage());
             if (article.getLiker() != null) {
                 statement.setInt(11, article.getLiker().getId());
             } else {
                 statement.setNull(11, Types.INTEGER);
             }
             statement.executeUpdate();
             ResultSet rs = statement.getGeneratedKeys();
             rs.next();
             return rs.getInt(1);
    }

    @Override
    public void updateArticle(Article article) {
        String champs = fields.replaceFirst("`membre_id`,", "");
        champs = champs.replaceAll(",", " = ?, ");
        champs += " = ?";
        
       
        
        String sql = "UPDATE article SET "+champs+" WHERE id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setString(1, article.getDescription());
             statement.setString(2, article.getLibelle());
             statement.setBoolean(3, article.isBoosted());
             statement.setBoolean(4, article.isLivre());
             statement.setDouble(5, article.getPrix());
             statement.setString(6, article.getEtat());
             statement.setBoolean(7, article.isVendu());
             statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
             statement.setInt(9, article.getIdImage());
             if (article.getLiker() != null) {
                 if (article.getLiker().getId() != null) {
                      statement.setInt(10, article.getLiker().getId());
                 } else {
                      statement.setNull(10, Types.INTEGER);
                 }
                
             } else {
                 statement.setNull(10, Types.INTEGER);
             }
             statement.setInt(11, article.getId());
             statement.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @Override
    public void deleteArticle(Article article) {
         try {
             remove(article.getId());
             imageService.supprimerImagesArticle(article.getId());
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void remove(int id) throws SQLException {
         String sql = "DELETE FROM `article` WHERE id = ?";
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             statement.execute();
    }

    @Override
    public void deleteArticle(int id) {
         
         try {
             remove(id);
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public ArrayList<Article> findAll() {
        String sql = "SELECT * FROM `article`";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results =  statement.executeQuery();
             ArrayList<Article> articles = new ArrayList<Article>();
             Article article;
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 articles.add(article);
             }
             return articles;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
    }

    @Override
    public Article findById(int id) {
        String sql = "SELECT * FROM `article` WHERE id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet result = statement.executeQuery();
             Article article = new Article();
             while ( result.next() ) {
                 article.setId(result.getInt("id"));
                article.setDescription(result.getString("description"));
                 article.setLibelle(result.getString("libelle"));
                 article.setLivre(result.getBoolean("livre"));
                 article.setPrix(result.getDouble("prix"));
                 article.setEtat(result.getString("etat"));
                 article.setVendu(result.getBoolean("vendu"));
                 article.setUpdateAt(result.getTimestamp("update_at"));
                 article.setIdImage(result.getInt("idImage"));
                 article.setMembre(TestService.getUser( result.getInt("membre_id") ));
                 article.setBoosted(result.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(result.getInt("liker_id")));
             }
             return article;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
    }

    @Override
    public ArrayList<Article> findBy(Map<String, String> critere) {
        String sql = "SELECT * FROM `article` WHERE ";
        
        sql += critere.entrySet().stream().map((entry) -> 
           entry.getKey()+" = "+entry.getValue()+" AND "
        )
                .collect(Collectors.joining(" "));
        
        sql = sql.substring(0, sql.length()-5);
        
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery();
             Article article;
             ArrayList<Article> articles = new ArrayList<>();
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 articles.add(article);
             }
             return articles;
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Article> getBoosted() {
        String sql = "SELECT * FROM `article` WHERE boosted = ?";
       
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setBoolean(1, true);
             ResultSet results = statement.executeQuery();
             Article article;
             ArrayList<Article> articles = new ArrayList<>();
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 articles.add(article);
             }
             return articles;
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
    public ArrayList<Article> getNonVendu() {
        String sql = "SELECT * FROM `article` WHERE vendu = ?";
       
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setBoolean(1, false);
             ResultSet results = statement.executeQuery();
             Article article;
             ArrayList<Article> articles = new ArrayList<>();
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 try {
                     article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 } catch(SQLException ex){
                   article.setLiker(null)   ;
                 }
                 
                 articles.add(article);
             }
             return articles;
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Article> getArticlesByOwner(int id) {
        String sql = "SELECT * FROM `article` WHERE membre_id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet results = statement.executeQuery();
             Article article;
             ArrayList<Article> articles = new ArrayList<>();
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 articles.add(article);
             }
             return articles;
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Article> getArticlesByLover(int id) {
         String sql = "SELECT * FROM `article` WHERE liker_id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet results = statement.executeQuery();
             Article article;
             ArrayList<Article> articles = new ArrayList<>();
             while (results.next()) {
                 article = new Article();
                 article.setId(results.getInt("id"));
                 article.setDescription(results.getString("description"));
                 article.setLibelle(results.getString("libelle"));
                 article.setLivre(results.getBoolean("livre"));
                 article.setPrix(results.getDouble("prix"));
                 article.setEtat(results.getString("etat"));
                 article.setVendu(results.getBoolean("vendu"));
                 article.setUpdateAt(results.getTimestamp("update_at"));
                 article.setIdImage(results.getInt("idImage"));
                 article.setMembre(TestService.getUser( results.getInt("membre_id") ));
                 article.setBoosted(results.getBoolean("boosted"));
                 article.setLiker(TestService.getUser(results.getInt("liker_id")));
                 articles.add(article);
             }
             return articles;
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Article> getArticleByAcheteur(int id) {
        AchatService achatService = new AchatService();
        ArrayList<Achat> achats = achatService.findByMembre(id);
        ArrayList<Article> articles = new ArrayList<>();
        if (achats != null) {
            articles = achats.stream().map(achat -> achat.getArticle()).collect(Collectors.toCollection(ArrayList<Article>::new));
        }
        return articles;
    }
}
