/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Timestamp;

/**
 *
 * @author Meedoch
 */
public class Creneau extends RecursiveTreeObject<Creneau>{
    private long id;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String description;
    private long evenement_id;

    

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(long evenement_id) {
        this.evenement_id = evenement_id;
    }

   

    
    
    
    
    
}
