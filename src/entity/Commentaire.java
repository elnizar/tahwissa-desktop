/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author BRAHMI
 */
public class Commentaire 
{
    private int id;
    private int publication_id;
    private String contenu;
    private Timestamp dateHeureCommentaire;
    private int commentateur_id;

    public Commentaire() {
    }

    public Commentaire(int publication_id, String contenu, Timestamp dateHeureCommentaire, int commentateur_id) {
        this.publication_id = publication_id;
        this.contenu = contenu;
        this.dateHeureCommentaire = dateHeureCommentaire;
        this.commentateur_id = commentateur_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(int publication_id) {
        this.publication_id = publication_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDateHeureCommentaire() {
        return dateHeureCommentaire;
    }

    public void setDateHeureCommentaire(Timestamp dateHeureCommentaire) {
        this.dateHeureCommentaire = dateHeureCommentaire;
    }

    public int getCommentateur_id() {
        return commentateur_id;
    }

    public void setCommentateur_id(int commentateur_id) {
        this.commentateur_id = commentateur_id;
    }
   
}