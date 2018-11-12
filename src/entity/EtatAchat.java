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
public enum EtatAchat {
    EN_ATTENTE("EN_ATTENTE"),
    REMBOURSE("REMBOURSE"),
    EFFECTUE("EFFECTUE");
    
     
    private String name = "";

    private EtatAchat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
