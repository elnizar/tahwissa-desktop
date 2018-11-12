/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Achat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

/**
 *
 * @author esprit
 */
public class AchatService implements AchatServiceInterface{

    private final Connection connection = MyConnection.getInstance();
    private String fields = "membre, date_heure_achat, statut, article_id";
    private static AchatService serviceAchat;
    
    public static AchatService getAchatService() {
        if (serviceAchat == null) {
            serviceAchat = new AchatService();
        }
        return serviceAchat;
    }
    
    @Override
    public void addAchat(Achat achat) {
        String sql = "INSERT INTO `achat` ("+fields+") VALUES (?, ?, ?, ?)";
        
         try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setInt(1, achat.getMembre().getId());
             statement.setTimestamp(2, achat.getDateHeureAchat());
             statement.setString(3, achat.getStatut());
             statement.setInt(4, achat.getArticle().getId());
             statement.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void updateAchat(Achat achat) {
        String champs = this.fields;
        champs = champs.replaceAll(",", " = ?, ");
        champs += " = ?";
        String sql = "UPDATE achat SET "+champs+" WHERE id = ?";
         try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setInt(1, achat.getMembre().getId());
             statement.setTimestamp(2, achat.getDateHeureAchat());
             statement.setString(3, achat.getStatut());
             statement.setInt(4, achat.getArticle().getId());
              statement.setInt(5, achat.getId());
             statement.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void deleteAchat(Achat achat) {
         String sql = "DELETE FROM `achat` WHERE id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(0, achat.getId());
             statement.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void deleteAchat(int id) {
         String sql = "DELETE FROM `achat` WHERE id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(0, id);
             statement.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public Achat findById(int id) {
         String sql = "SELECT * FROM `achat` WHERE id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet result = statement.executeQuery();
             Achat achat = new Achat();
             ArticleService articleService = ArticleService.getArticleService();
             while (result.next()) {                 
                 achat.setArticle(articleService.findById(result.getInt("article_id")));
                 achat.setDateHeureAchat(result.getTimestamp("date_heure_achat"));
                 achat.setId(result.getInt("id"));
                 achat.setMembre(TestService.getUser(result.getInt("membre")));
                 achat.setStatut(result.getString("statut"));
             }
             return achat;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Achat> findByMembre(int id) {
        String sql = "SELECT * FROM `achat` WHERE membre = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet result = statement.executeQuery();
             Achat achat;
             ArrayList<Achat> achats = new ArrayList<>();
             ArticleService articleService = ArticleService.getArticleService();
             while (result.next()) {    
                 achat = new Achat();
                 achat.setArticle(articleService.findById(result.getInt("article_id")));
                 achat.setDateHeureAchat(result.getTimestamp("date_heure_achat"));
                 achat.setId(result.getInt("id"));
                 achat.setMembre(TestService.getUser(result.getInt("membre")));
                 achat.setStatut(result.getString("statut"));
                 achats.add(achat);
             }
             return achats;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public Achat findByArticle(int id) {
        String sql = "SELECT * FROM `achat` WHERE article_id = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setInt(1, id);
             ResultSet result = statement.executeQuery();
             Achat achat = new Achat();
             ArticleService articleService = ArticleService.getArticleService();
             while (result.next()) {                 
                 achat.setArticle(articleService.findById(result.getInt("article_id")));
                 achat.setDateHeureAchat(result.getTimestamp("date_heure_achat"));
                 achat.setId(result.getInt("id"));
                 achat.setMembre(TestService.getUser(result.getInt("membre")));
                 achat.setStatut(result.getString("statut"));
             }
             return achat;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }

    @Override
    public ArrayList<Achat> findByStatut(String statut) {
        String sql = "SELECT * FROM `achat` WHERE statut = ?";
         try {
             PreparedStatement statement = this.connection.prepareStatement(sql);
             statement.setString(1, statut);
             ResultSet result = statement.executeQuery();
             Achat achat;
             ArrayList<Achat> achats = new ArrayList<>();
             ArticleService articleService = ArticleService.getArticleService();
             while (result.next()) {    
                 achat = new Achat();
                 achat.setArticle(articleService.findById(result.getInt("article_id")));
                 achat.setDateHeureAchat(result.getTimestamp("date_heure_achat"));
                 achat.setId(result.getInt("id"));
                 achat.setMembre(TestService.getUser(result.getInt("membre")));
                 achat.setStatut(result.getString("statut"));
                 achats.add(achat);
             }
             return achats;
         } catch (SQLException ex) {
             Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
}

