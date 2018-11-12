/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class CommentEvent {
    private Integer id;
    private int evenement_id;
    private String contenu;
    private Timestamp dateHeureCommentEvent = Timestamp.valueOf(LocalDateTime.now());
    private int user_id;

    public CommentEvent(Integer id, int evenement_id, String contenu, int user_id) {
        this.id = id;
        this.evenement_id = evenement_id;
        this.contenu = contenu;
        this.user_id = user_id;
    }

    public CommentEvent(int evenement_id, String contenu, int user_id) {
        this.evenement_id = evenement_id;
        this.contenu = contenu;
        this.user_id = user_id;
    }

    public CommentEvent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDateHeureCommentEvent() {
        return dateHeureCommentEvent;
    }

    public void setDateHeureCommentEvent(Timestamp dateHeureCommentEvent) {
        this.dateHeureCommentEvent = dateHeureCommentEvent;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    
 
    
}
