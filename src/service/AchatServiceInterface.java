/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import entity.Achat;
import java.util.ArrayList;

/**
 *
 * @author esprit
 */
public interface AchatServiceInterface {
    
    public void addAchat(Achat achat);
    public void updateAchat(Achat achat);
    public void deleteAchat(Achat achat);
    public void deleteAchat(int id);
    public Achat findById(int id);
    public ArrayList<Achat> findByMembre(int id);
    public Achat findByArticle(int id);
    public ArrayList<Achat> findByStatut(String statut);
}
