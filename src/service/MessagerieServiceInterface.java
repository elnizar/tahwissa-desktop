/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Messagerie;
import java.util.List;

/**
 *
 * @author User
 */
public interface MessagerieServiceInterface<T,R> {
    
    public void ajouterMessage (Messagerie m);
    public void supprimerMessage(Messagerie m,int user_id);
    List<T> getAll();
    public List<Messagerie> getConversation(int user_id);
    
}
