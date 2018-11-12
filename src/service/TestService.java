/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Compte;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.codegen.CompilerConstants;
import util.MyConnection;

/**
 *
 * @author esprit
 */
public class TestService {
    
    //private static Compte compte;
    //private static User user;
    
    private static final Connection connection = MyConnection.getInstance();
    private static Compte account;
    private static User membre;
    
    public static User getMembre(int id) {
        if (membre == null) {
            membre = getUser(id);
        }

        return membre;
    }
    
    public static User getMembre() {
        if (membre == null) return getMembre(1);
        return membre;
    }
    
    public static Compte getAccount(int id) {
        if (account == null) {
            account = getCompteWithoutUser(id);
            System.out.println("instanciating account! ");
        }
        System.out.println("returning account! ");
        return account;
    }
    
    public static User getUser(int id) {
        String sql = "SELECT * FROM `fos_user` WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            User user = new User();
            
            result.next();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setUsernameCanonical(result.getString("username_canonical"));
                user.setEmail(result.getString("email"));
                user.setEmailCanonical(result.getString("email_canonical"));
                user.setEnabled(result.getBoolean("enabled"));
                user.setSalt(result.getString("salt"));
                user.setPassword(result.getString("password"));
                user.setLastLogin(result.getDate("last_login"));
                user.setConfirmationToken(result.getString("confirmation_token"));
                user.setPasswordRequestedAt(result.getDate("password_requested_at"));
                user.setRoles(result.getString("roles"));
                user.setAdresse(result.getString("adresse"));
                user.setBanned(result.getInt("banned"));
                user.setDateInscription(result.getDate("date_inscription"));
                user.setDateNaissance(result.getDate("date_naissance"));
                user.setInformationPersonnel(result.getString("information_personnel"));
                user.setNom(result.getString("nom"));
                user.setPrenom(result.getString("prenom"));
                user.setNombreOrganisations(result.getInt("nombre_organisations"));
                user.setNombreSignalisations(result.getInt("nombre_signalisations"));
                user.setNumeroTelephone(result.getString("numero_telephone"));
                user.setProfession(result.getString("profession"));
                user.setRatingFlobal(result.getDouble("rating_flobal"));
                user.setSexe(result.getString("sexe"));
                user.setSolde(result.getDouble("solde"));
                System.out.println("compte id "+result.getInt("compte_id"));
                user.setCompte(getAccount(result.getInt("compte_id")));
            
            user.getCompte().setUser(user);
           
            return user;
            
        } catch (SQLException ex) {
           // Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
           return null;
        } 
    }
    
       public static User getUserWithoutCompte(int id) {
        String sql = "SELECT * FROM `fos_user` WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            User user = new User();
            result.next() ;
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setUsernameCanonical(result.getString("username_canonical"));
                user.setEmail(result.getString("email"));
                user.setEmailCanonical(result.getString("email_canonical"));
                user.setEnabled(result.getBoolean("enabled"));
                user.setSalt(result.getString("salt"));
                user.setPassword(result.getString("password"));
                user.setLastLogin(result.getDate("last_login"));
                user.setConfirmationToken(result.getString("confirmation_token"));
                user.setPasswordRequestedAt(result.getDate("password_requested_at"));
                user.setRoles(result.getString("roles"));
                user.setAdresse(result.getString("adresse"));
                user.setBanned(result.getInt("banned"));
                user.setDateInscription(result.getDate("date_inscription"));
                user.setDateNaissance(result.getDate("date_naissance"));
                user.setInformationPersonnel(result.getString("information_personnel"));
                user.setNom(result.getString("nom"));
                user.setPrenom(result.getString("prenom"));
                user.setNombreOrganisations(result.getInt("nombre_organisations"));
                user.setNombreSignalisations(result.getInt("nombre_signalisations"));
                user.setNumeroTelephone(result.getString("numero_telephone"));
                user.setProfession(result.getString("profession"));
                user.setRatingFlobal(result.getDouble("rating_flobal"));
                user.setSexe(result.getString("sexe"));
                user.setSolde(result.getDouble("solde"));
                //compte = (compte == null) ? getCompte(result.getInt("compte_id")): compte;
                //user.setCompte(getCompteWithoutUser(result.getInt("compte_id")));
            
            return user;
            
        } catch (SQLException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    public static Compte getCompte(int id) {
        String sql = "SELECT * FROM `compte` WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Compte compte = new Compte();
             result.next();
                // compte.setDateCreation(result.getDate("date_creation"));
                 compte.setDateModification(result.getDate("date_modification"));
                 compte.setSolde(result.getDouble("solde"));
                 compte.setIdentifiant(result.getString("identifiant"));
                 compte.setId(result.getInt("id"));
                 compte.setUser(getUserWithoutCompte(result.getInt("user_id")));
                 compte.setPasscode(result.getString("passcode"));
             
             compte.getUser().setCompte(compte);
             
             return compte;
        } catch (SQLException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Compte getCompteWithoutUser(int id) {
        String sql = "SELECT * FROM `compte` WHERE id = ?";
        System.out.println("code compte : "+id);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Compte compte = new Compte();
            result.next();
                 compte.setDateCreation(result.getDate("date_creation"));
                 compte.setDateModification(result.getDate("date_modification"));
                 compte.setSolde(result.getDouble("solde"));
                 compte.setIdentifiant(result.getString("identifiant"));
                 compte.setId(result.getInt("id"));
                 //compte.setUser(getUserWithoutCompte(result.getInt("user_id")));
                 compte.setPasscode(result.getString("passcode"));
            
             //compte.getUser().setCompte(compte);
             
             return compte;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Compte getCompteByUser(int id) {
        String sql = "SELECT * FROM `compte` WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Compte compte = new Compte();
            result.next();
                 compte.setDateCreation(result.getDate("date_creation"));
                 compte.setDateModification(result.getDate("date_modification"));
                 compte.setSolde(result.getDouble("solde"));
                 compte.setIdentifiant(result.getString("identifiant"));
                 compte.setId(result.getInt("id"));
                 compte.setUser(getUser(result.getInt("user_id")));
                 compte.setPasscode(result.getString("passcode"));
             
             return compte;
        } catch (SQLException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void transaction(int id_compte, double montant) throws Exception {
        Compte compte = getCompte(id_compte);
        if ( compte.getSolde()< 20 ) {
            throw new Exception("Solde insuffisant");
        } else {
            double solde = compte.getSolde() - montant;
            String sql = "UPDATE `compte` SET solde = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, solde);
            ps.setInt(2, id_compte);
            ps.executeUpdate();
        }
    }
    
        public static void transaction(Compte compte, double montant) throws Exception {
       
        if ( compte.getSolde()< montant ) {
            throw new Exception("Solde insuffisant");
        } else {
            double solde = compte.getSolde() - montant;
            String sql = "UPDATE `compte` SET solde = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, solde);
            ps.setInt(2, compte.getId());
            ps.executeUpdate();
        }
    }
        
        public static User getUserByMail(String mail, String password) {
        String sql = "SELECT * FROM `fos_user` WHERE username = ? and password= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mail);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            User user = new User();
            System.out.println("From GetUserByMai:l"+mail);
            result.next();
                user.setId(result.getInt("id"));
                user.setUsername(result.getString("username"));
                user.setUsernameCanonical(result.getString("username_canonical"));
                user.setEmail(result.getString("email"));
                user.setEmailCanonical(result.getString("email_canonical"));
                user.setEnabled(result.getBoolean("enabled"));
                user.setSalt(result.getString("salt"));
                user.setPassword(result.getString("password"));
                user.setLastLogin(result.getDate("last_login"));
                user.setConfirmationToken(result.getString("confirmation_token"));
                user.setPasswordRequestedAt(result.getDate("password_requested_at"));
                user.setRoles(result.getString("roles"));
                user.setAdresse(result.getString("adresse"));
                user.setBanned(result.getInt("banned"));
                user.setDateInscription(result.getDate("date_inscription"));
                user.setDateNaissance(result.getDate("date_naissance"));
                user.setInformationPersonnel(result.getString("information_personnel"));
                user.setNom(result.getString("nom"));
                user.setPrenom(result.getString("prenom"));
                user.setNombreOrganisations(result.getInt("nombre_organisations"));
                user.setNombreSignalisations(result.getInt("nombre_signalisations"));
                user.setNumeroTelephone(result.getString("numero_telephone"));
                user.setProfession(result.getString("profession"));
                user.setRatingFlobal(result.getDouble("rating_flobal"));
                user.setSexe(result.getString("sexe"));
                user.setSolde(result.getDouble("solde"));
                user.setImage(result.getString("chemin"));
                user.setApprouver(result.getInt("approuver"));
                int compte_id = result.getInt("compte_id");
                if(compte_id != 0) {
                    user.setCompte(getAccount(result.getInt("compte_id")));
                    user.getCompte().setUser(user);
                } else {
                    System.out.println("Creating Default Account :");
                    Compte compte = createDefaultCompte(user);
                    user.setCompte(compte);
                }
                    
                
            System.out.println("User id: "+ user.getId());
           
           TestService.membre = user;
            return user;
            
        } catch (SQLException ex) {
           // Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
           return null;
        } 
    }
    
    public static Compte createDefaultCompte(User user)  {
        String sql = "INSERT INTO compte(user_id, identifiant, passcode, date_creation, date_modification , solde) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getId());
            ps.setString(2, String.valueOf(user.getId()));
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDouble(6, 0.0);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int compteId =  rs.getInt(1);
            sql = "UPDATE fos_user SET compte_id=? WHERE id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, compteId);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            Compte compte =  new Compte();
            compte.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
            compte.setDateModification(Timestamp.valueOf(LocalDateTime.now()));
            compte.setPasscode(user.getPassword());
            compte.setSolde(0.0);
            compte.setId(compteId);
            compte.setIdentifiant(String.valueOf(user.getId()));
            compte.setUser(user);
            return compte;
        } catch (SQLException ex) {
            Logger.getLogger(TestService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
