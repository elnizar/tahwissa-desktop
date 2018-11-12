/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Image;
import java.io.File;
import java.util.List;

/**
 *
 * @author Meedoch
 */
public interface ImageServiceInterface {

    public void saveImage(File t, Integer eventId) throws Exception;

    public void supprimerImagesEvenement(Integer id);

    public List<Image> getImagesByEvenement(Integer id);
}
