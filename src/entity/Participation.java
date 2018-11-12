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
 * @author Meedoch
 */
public class Participation {

    private int event_id;
    private int user_id;
    private Timestamp dateHeureParticipation = Timestamp.valueOf(LocalDateTime.now());
    private String statutPaiement = "En attente";
    private Evenement e = new Evenement();
    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getDateHeureParticipation() {
        return dateHeureParticipation;
    }

    public void setDateHeureParticipation(Timestamp dateHeureParticipation) {
        this.dateHeureParticipation = dateHeureParticipation;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public Evenement getE() {
        return e;
    }

    public void setE(Evenement e) {
        this.e = e;
    }
    
}
