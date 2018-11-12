/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
/**
 *
 * @author User
 */
public class Messagerie extends User{
    private Integer id;
    private String ContenuMsg;
    private Timestamp dateHeureEnvoi = Timestamp.valueOf(LocalDateTime.now());
    private int sender_id;
    private int reciever_id;
    private boolean deletedSender;
    private boolean deletedReciever;
    
    public Messagerie(Integer id, String ContenuMsg, int sender_id, int reciever_id, boolean deletedSender, boolean deletedReciever) {
        this.id = id;
        this.ContenuMsg = ContenuMsg;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.deletedSender = deletedSender;
        this.deletedReciever = deletedReciever;
    }

    public Messagerie(String ContenuMsg, int sender_id, int reciever_id, boolean deletedSender, boolean deletedReciever) {
        this.ContenuMsg = ContenuMsg;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.deletedSender = deletedSender;
        this.deletedReciever = deletedReciever;
    }
    public Messagerie(){}
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenuMsg() {
        return ContenuMsg;
    }

    public void setContenuMsg(String ContenuMsg) {
        this.ContenuMsg = ContenuMsg;
    }

    public Timestamp getDateHeureEnvoi() {
        return dateHeureEnvoi;
    }

    public void setDateHeureEnvoi(Timestamp dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(int reciever_id) {
        this.reciever_id = reciever_id;
    }

    public boolean isDeletedSender() {
        return deletedSender;
    }

    public void setDeletedSender(boolean deletedSender) {
        this.deletedSender = deletedSender;
    }

    public boolean isDeletedReciever() {
        return deletedReciever;
    }

    public void setDeletedReciever(boolean deletedReciever) {
        this.deletedReciever = deletedReciever;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final Messagerie other = (Messagerie) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    
    
    
    
    
    
}
