/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author esprit
 */
public enum EtatArticle {
    NOUVEAU("Nouveau"),
    BONNE_OCCASION("Bonne Occasion"),
    BON_ETAT("Bon Etat"),
    ETAT_MOYEN("Etat Moyen")
    ;
    
    private String name = "";

    private EtatArticle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    
    
}
