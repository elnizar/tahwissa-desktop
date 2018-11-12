/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author Elhraiech Nizar
 */
public class Bannir {

    private int id;
    private String motif;
    private Date date;
    private String nomadmin;
    private String prenomadmin;
    private int idadmin;
    private String message;
    private int iduser;
    public Bannir() {

    }
    
    public Bannir(int id, String motif, Date date, String nomadmin, String prenomadmin, int idadmin, int iduser ,String message) {
        this.id = id;
        this.motif = motif;
        this.date = date;
        this.nomadmin = nomadmin;
        this.prenomadmin = prenomadmin;
        this.idadmin = idadmin;
        this.message = message;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNomadmin() {
        return nomadmin;
    }

    public void setNomadmin(String nomadmin) {
        this.nomadmin = nomadmin;
    }

    public String getPrenomadmin() {
        return prenomadmin;
    }

    public void setPrenomadmin(String prenomadmin) {
        this.prenomadmin = prenomadmin;
    }

    public int getIdadmin() {
        return idadmin;
    }

    public void setIdadmin(int idadmin) {
        this.idadmin = idadmin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

}
