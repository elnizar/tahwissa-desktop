/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Meedoch
 */
public class Image {
    private long id;
    private long evenement_id;
    private long publication_id;
    private String chemin;

    public long getId() {
        return id;
    }

    public Image() {
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

    public long getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(long publication_id) {
        this.publication_id = publication_id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Image(long evenement_id, String chemin) {
        this.evenement_id = evenement_id;
        this.chemin = chemin;
    }
    
    
}
