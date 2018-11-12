/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.CommentEvent;
import java.util.List;

/**
 *
 * @author User
 */
public interface CommentEventServiceInterface<T,R> {
    
    public void ajouterCommentEvent (CommentEvent c);
    public void supprimerCommentEvent(CommentEvent c);
    public int modifierCommentEvent(CommentEvent c);
    List<T> getAll();
    
}
