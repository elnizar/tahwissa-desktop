/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author User
 */
public class Signalisation {
    private Integer id;
    private int user_id;
    private String motif;
    private String objet;
    
    public Signalisation() {
    }

    public Signalisation(Integer id, int user_id, String motif, String objet, int user2_id) {
        this.id = id;
        this.user_id = user_id;
        this.motif = motif;
        this.objet = objet;
        this.user2_id = user2_id;
    }

    public Signalisation(int user_id, String motif, String objet, int user2_id) {
        this.user_id = user_id;
        this.motif = motif;
        this.objet = objet;
        this.user2_id = user2_id;
    }
    private int user2_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }
    
    
    
}
