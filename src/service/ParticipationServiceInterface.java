/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evenement;
import entity.Participation;
import java.util.List;

/**
 *
 * @author Meedoch
 */
public interface ParticipationServiceInterface {
    public boolean participer(int user_id, Evenement ev) ;
     public boolean aParticipe(int user_id, int event_id) ;
     public List<Participation> getParticipationsByUser(int user_id);
}
