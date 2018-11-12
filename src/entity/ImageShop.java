/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author esprit
 */
public class ImageShop {
    protected int id;
    protected String imageName;
    protected Timestamp updateAt = new Timestamp(new Date().getTime());
    protected Article article;

    public ImageShop(String imageName, Timestamp updateAt, Article article) {
        this.imageName = imageName;
        this.updateAt = updateAt;
        this.article = article;
    }

    public ImageShop(String imageName, Article article) {
        this.imageName = imageName;
        this.article = article;
    }

    public ImageShop(String imageName) {
        this.imageName = imageName;
    }
    
    

    public ImageShop() {
    }

    public ImageShop(Timestamp updateAt, Article article) {
        this.updateAt = updateAt;
        this.article = article;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    
}
