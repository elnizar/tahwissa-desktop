package entity;
// Generated 24 f�vr. 2017 12:18:34 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.Objects;


/**
 * FosUser generated by hbm2java
 */

public class User  implements java.io.Serializable {


     private Integer id;
     private Compte compte;
     private String username;
     private String usernameCanonical;
     private String email;
     private String emailCanonical;
     private boolean enabled;
     private String salt;
     private String password;
     private Date lastLogin;
     private String confirmationToken;
     private Date passwordRequestedAt;
     private String roles;
     private String adresse;
     private int banned;
     private Date dateInscription;
     private Date dateNaissance;
     private String informationPersonnel;
     private String nom;
     private String prenom;
     private int nombreOrganisations;
     private int nombreSignalisations;
     private String numeroTelephone;
     private String profession;
     private Double ratingFlobal;
     private String sexe;
     private Double solde;
    private String image;
    private int approuver;
     public static String test;

   
     public User(){
         
     }
	
    public User(String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String password, String roles, int nombreOrganisations, int nombreSignalisations) {
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
        this.nombreOrganisations = nombreOrganisations;
        this.nombreSignalisations = nombreSignalisations;
    }
    
    public User(String email, String password, String informationPersonnel, String nom, String prenom, String image) {
        
        test = email;
        this.email = email;
        this.password = password;
        this.informationPersonnel = informationPersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
    }

    public User(String mail, int banned, int approuver, String role) {
        
        this.email = mail;
        this.banned = this.banned;
        this.approuver = approuver;
        this.roles = role;
    }

    public User(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;

    }

    public User(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(String nom, String prenom, String informationPersonnel, String password, String mail) {
        this.nom = nom;
        this.prenom = prenom;
        this.informationPersonnel = informationPersonnel;
        this.password = password;
    }

    public User(String nom, String prenom, String informationPersonnel, String email, Date date, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.informationPersonnel = informationPersonnel;
        this.email = email;
        this.dateNaissance = date;
        this.image = image;
    }

    public User(String nom, String prenom, String usernameCanonical, String emailCanonical, String password, Date date) {
        this.nom = nom;
        this.prenom = prenom;
        this.usernameCanonical = usernameCanonical;
        this.emailCanonical = emailCanonical;
        this.email = usernameCanonical;
        this.password = password;
        roles = "a:0:{}";
        this.dateNaissance = date;
    }
   
  
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }


    public Compte getCompte() {
        return this.compte;
    }
    
    public void setCompte(Compte compte) {
        this.compte = compte;
    }

 
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
   
    public String getUsernameCanonical() {
        return this.usernameCanonical;
    }
    
    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    
   
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
  
    public String getEmailCanonical() {
        return this.emailCanonical;
    }
    
    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }

    
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    
   
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

   
    public Date getLastLogin() {
        return this.lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    
   
    public String getConfirmationToken() {
        return this.confirmationToken;
    }
    
    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    
    public Date getPasswordRequestedAt() {
        return this.passwordRequestedAt;
    }
    
    public void setPasswordRequestedAt(Date passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    
  
    public String getRoles() {
        return this.roles;
    }
    
    public void setRoles(String roles) {
        this.roles = roles;
    }

    
   
    public String getAdresse() {
        return this.adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    
    
    public int getBanned() {
        return this.banned;
    }
    
    public void setBanned(int banned) {
        this.banned = banned;
    }

   
    public Date getDateInscription() {
        return this.dateInscription;
    }
    
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    
    public Date getDateNaissance() {
        return this.dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    
   
    public String getInformationPersonnel() {
        return this.informationPersonnel;
    }
    
    public void setInformationPersonnel(String informationPersonnel) {
        this.informationPersonnel = informationPersonnel;
    }

    
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    
   
    public int getNombreOrganisations() {
        return this.nombreOrganisations;
    }
    
    public void setNombreOrganisations(int nombreOrganisations) {
        this.nombreOrganisations = nombreOrganisations;
    }

    
   
    public int getNombreSignalisations() {
        return this.nombreSignalisations;
    }
    
    public void setNombreSignalisations(int nombreSignalisations) {
        this.nombreSignalisations = nombreSignalisations;
    }

    
    
    public String getNumeroTelephone() {
        return this.numeroTelephone;
    }
    
    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    
  
    public String getProfession() {
        return this.profession;
    }
    
    public void setProfession(String profession) {
        this.profession = profession;
    }

    
  
    public Double getRatingFlobal() {
        return this.ratingFlobal;
    }
    
    public void setRatingFlobal(Double ratingFlobal) {
        this.ratingFlobal = ratingFlobal;
    }

    
   
    public String getSexe() {
        return this.sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    
    
    public Double getSolde() {
        return this.solde;
    }
    
    public void setSolde(Double solde) {
        this.solde = solde;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return true;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getApprouver() {
        return approuver;
    }

    public void setApprouver(int approuver) {
        this.approuver = approuver;
    }



    

}


