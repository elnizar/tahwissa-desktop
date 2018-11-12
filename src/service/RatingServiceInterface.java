/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Evenement;

/**
 *
 * @author Meedoch
 */
public interface RatingServiceInterface {
    public Evenement rate(Evenement e, int id_user, double value);
}
