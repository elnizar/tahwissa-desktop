/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.User;
import tahwissa.desktop.user.sinscrire.SinscrireController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.MyConnection;
import entity.Bannir;
import entity.User;
import java.text.ParseException;
import util.MailApi;

/**
 *
 * @author Meedoch
 */
public class UserService {
    Connection c = MyConnection.getInstance();
    public List<User> findAll(){
        try {
            List<User> users=  new ArrayList<User>();
            PreparedStatement ps = c.prepareStatement("SELECT * from fos_user");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                users.add(u);
                
            }
            System.out.println(users.size()+" users");
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public User get(int id){
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * from fos_user where id=?");
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setNombreSignalisations(rs.getInt("nombre_signalisations"));
                return u;
                
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void banUser(int user_id){
        try {
            PreparedStatement ps= c.prepareStatement("UPDATE fos_user set banned=1 where id=?");
            ps.setInt(1, user_id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public User find(int id){
        try {
           
            PreparedStatement ps = c.prepareStatement("SELECT * from fos_user where id=?");
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                return u;            
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
        Bannir b = new Bannir();

    public void ajoutToken(String email, String token) {
        try {
            Statement ste = c.createStatement();
            String req = "UPDATE `fos_user` SET confirmation_token = '" + token + "' WHERE email_canonical = '" + email + "'";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approuverMembre(String email) {
        try {
            Statement ste = c.createStatement();
            String req = "UPDATE `fos_user` SET banned = '" + 0 + "' WHERE email_canonical = '" + email + "'";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean validPassword(String s) {
        if (s == null) {
            return false;
        }
        

        if (s.length() < 5) {
            return false;
        }

//        int counter = 0;
//
//        for (int i = 0; i < s.length(); i++) {
//
//            if (!Character.isLetterOrDigit(s.charAt(i))) {
//
//                return false;
//
//            }
//
//            if (Character.isDigit(s.charAt(i))) {
//
//                counter++;
//
//            }
//
//        }
//
//        if (counter >= 2) {
//            return true;
//        }

        return true;

    }

    public void supprimerBann(int id) {
        try {
            Statement ste = c.createStatement();
            String req = "DELETE FROM `banir` WHERE iduser = '" + id + "' ";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Bannir getBannedByID(int id) {
        Bannir b = null;
        try {

            Statement ste = c.createStatement();
            String req = "SELECT * FROM `banir` WHERE iduser = '" + id + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            while (res.next()) {
                int identifiant = res.getInt("id");
                String motifadmin = res.getString("motif");
                System.out.println(res.getString("motif"));
                java.util.Date newDate = res.getTimestamp("date");
                System.out.println(newDate);
                String nom = res.getString("nomadmin");
                String prenom = res.getString("prenomadmin");
                int idadmin = res.getInt("idadmin");
                int iduser = res.getInt("iduser");
                String message = res.getString("message");
                Bannir bannir = new Bannir(identifiant, motifadmin, newDate, nom, prenom, idadmin, iduser, message);
                b = bannir;
            }
            return b;

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public int getIdUSer(String email) {
        int id = 0;
        try {
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` WHERE email_canonical = '" + email + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            while (res.next()) {
                id = res.getInt("id");
            }
            System.out.println(id);
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void ajouterBanned(int iduser, Bannir b) {
        try {
            System.out.println(b.getId());
            int i = 1;
            String req1 = "UPDATE `fos_user` SET banned = '" + i + "' WHERE id = '" + b.getId() + "'";
            String req = "INSERT INTO `banir` (`motif`, `date`, `nomadmin`,`prenomadmin`,`idadmin`,`iduser`) "
                    + "VALUES ('" + b.getMotif() + "', '" + b.getDate() + "', '" + b.getNomadmin() + "', '" + b.getPrenomadmin() + "', '" + b.getIdadmin() + "','" + b.getId() + "')";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            PreparedStatement preparedStmt2 = c.prepareStatement(req1);
            preparedStmt.execute();
            preparedStmt2.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUserByID(int id) {
        String nom = null;
        String prenom = null;
        String mail = null;
        String infoperso = null;
        Date date = null;
        try {
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` where id = '" + id + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            while (res.next()) {
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                mail = res.getString("email_canonical");
                infoperso = res.getString("information_personnel");
                date = res.getTimestamp("date_naissance");
            }
            System.out.println(date);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user = new User(nom, prenom, infoperso, mail, date, null);
        return user;
    }

    public ObservableList<User> getAllUser() {
        ObservableList<User> listUser = FXCollections.observableArrayList();
        try {
            int ap = 0;
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` where banned = '" + ap + "'";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                int id = res.getInt("id");
                String email = res.getString("email_canonical");
                User user = new User(id, nom, prenom, email);
                System.out.println(id);
                listUser.add(user);

            }
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;

    }

    public ObservableList<User> getUserbanned() {
        ObservableList<User> listUser = FXCollections.observableArrayList();
        try {
            int ap = 1;
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` where banned = '" + ap + "'";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                int id = res.getInt("id");
                String email = res.getString("email_canonical");
                User user = new User(id, nom, prenom, email);
                System.out.println(id);
                listUser.add(user);

            }
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;

    }

    public void ajouterUser(User u, ActionEvent event) throws IOException, SQLException, ParseException {
        System.out.println(u.getEmail());
        User user = getUser(u.getEmail());
                if (user.getEmail()==null){
                    user.setEmail("mouchmawjoud");
                }
        if (user.getEmail().equals(u.getEmail())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Adresse email existante SVP entrez une adresse e-mail valide");
            alert.showAndWait();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/tahwissa/desktop/user/sinscrire/sinscrire.fxml"));
            loader.load();
            SinscrireController display = loader.getController();
            display.setText(u);
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } else if (isValidEmailAddress(u.getEmail()) == false) {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(" Veuillez SVP entrez une adresse e-mail valide");
                alert.showAndWait();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/tahwissa/desktop/user/sinscrire/sinscrire.fxml"));
                loader.load();
                SinscrireController display = loader.getController();
                display.setText(u);
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SinscrireController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (validPassword(u.getPassword()) == false) {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Mot de pass Invalide vous devez écrire un mote de pass qui contient des caractées spéciaux ");
                alert.showAndWait();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/tahwissa/desktop/user/sinscrire/sinscrire.fxml"));
                loader.load();
                SinscrireController display = loader.getController();
                display.setText(u);
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(SinscrireController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                String req = "INSERT INTO `fos_user` (`nom`, `username`, `prenom`, `username_canonical`, `email`, `email_canonical`,`password`,`roles`,`date_naissance`, `enabled`, `nombre_organisations`, `nombre_signalisations`) "
                        + "VALUES ('" + u.getNom() + "', '" + u.getEmailCanonical()+ "', '" + u.getPrenom() + "', '" + u.getEmailCanonical() +  "', '" + u.getEmailCanonical() + "', '" + u.getEmailCanonical() + "', '" + u.getPassword() + "', '" + u.getRoles() + "', '" + u.getDateNaissance() + "', '1', '0', '0')";
                PreparedStatement preparedStmt = c.prepareStatement(req);
                preparedStmt.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Vous etes inscrit veuillez confirmer votre inscription via le mail");
                alert.showAndWait();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/tahwissa/desktop/user/login.fxml"));
                loader.load();
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
                MailApi mailapi = new MailApi();
                String s = generate(20);
                ajoutToken(u.getEmail(), s);
                mailapi.send(u.getEmail(), "Mail de confiramtion", "http://localhost/MailDeConfirmation/confirmail.php?token=" + s + "", "Your Email", "Your Password");
                System.out.println("cibon");
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
        String pass = "";
        for (int x = 0; x < length; x++) {
            int i = (int) Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
            pass += chars.charAt(i);
        }
        System.out.println(pass);
        return pass;
    }

    public void ajouterPersonne(User p) {
        try {
            String req = "INSERT INTO `fos_user` (`nom`, `prenom`, `username_canonical`, `email_canonical`,`password`,`roles`,`date_naissance`) "
                    + "VALUES ('" + p.getNom() + "', '" + p.getPrenom() + "', '" + p.getEmailCanonical() + "', '" + p.getEmailCanonical() + "', '" + p.getPassword() + "', '" + p.getRoles() + "', '" + p.getDateNaissance() + "')";

            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            System.out.println("cibon");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User testConnexion(String email, String password) {
        User user = null;
        user = null;
        try {
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` WHERE email_canonical = '" + email + "' and password = '" + password + "' ";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            System.out.println("ee");
            while (res.next()) {
                System.out.println("eeesssss");
                int approuver = res.getInt("approuver");
                int banned = res.getInt("banned");
                String role = res.getString("roles");
                System.out.println(res.getString("roles"));
                System.out.println(res.getInt("banned"));
                System.err.println(res.getString("email_canonical"));
                String mail = res.getString("email_canonical");
                User u = new User(mail, banned, approuver, role);
                user = u;
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public User getUser(String email) {
        String nom = null;
        String prenom = null;
        String mail = null;
        String infoperso = null;
        Date date = null;
        String image = null;
        try {
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` where username_canonical = '" + email + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            while (res.next()) {
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                mail = res.getString("email_canonical");
                infoperso = res.getString("information_personnel");
                date = res.getTimestamp("date_naissance");
                image = res.getString("chemin");
            }
            System.out.println(date);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("tawaa" + mail);
        User user = new User(nom, prenom, infoperso, mail, date, image);
        return user;
    }

    public User getUsers(String email) {
        String nom = null;
        String prenom = null;
        String mail = null;
        String infoperso = null;
        String pswd = null;
        String image = null;
        try {
            Statement ste = c.createStatement();
            String req = "SELECT * FROM `fos_user` where username_canonical = '" + email + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
            while (res.next()) {
                nom = res.getString("nom");
                prenom = res.getString("prenom");
                infoperso = res.getString("information_personnel");
                pswd = res.getString("password");
                image = res.getString("chemin");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user = new User(email, pswd, infoperso, nom, prenom, image);
        return user;
    }

    public void modifierUser(User user) throws FileNotFoundException {
        try {
            Statement ste = c.createStatement();
            String req = "UPDATE `fos_user` SET nom = '" + user.getNom() + "',prenom = '" + user.getPrenom() + "',password = '" + user.getPassword() + "',information_personnel = '" + user.getInformationPersonnel() + "',chemin = '" + user.getImage() + "' WHERE email_canonical = '" + user.getEmail() + "' ";
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void supprimerUser(int id) {
        try {
            Statement ste = c.createStatement();
            String req = "DELETE FROM `fos_user` WHERE  id = '" + id + "'";
            ResultSet res = ste.executeQuery(req);
            PreparedStatement preparedStmt = c.prepareStatement(req);
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
