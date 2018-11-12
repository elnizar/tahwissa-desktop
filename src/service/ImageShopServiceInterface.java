/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.ImageShop;
import java.io.File;
import java.util.List;

/**
 *
 * @author esprit
 */
public interface ImageShopServiceInterface {
    public void saveImage(File file, int articleId) throws Exception;
    public void supprimerImagesArticle(int id);
    public List<ImageShop> getImagesByArticle(int id);
}
