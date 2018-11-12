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
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Article extends RecursiveTreeObject<Article> {
	private int id;
	private String description;
	private String libelle;
	private boolean boosted = false;
	private boolean livre = false;
	private double prix = 0;
	private String etat;
	private boolean vendu = false;
	private User membre;
	private Timestamp updateAt;
	private int idImage;
	private List<ImageShop> images;
	private User liker;

    public Article() {
        this.updateAt = new Timestamp(new Date().getTime());
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public boolean isBoosted() {
		return boosted;
	}
	public void setBoosted(boolean boosted) {
		this.boosted = boosted;
	}
	public boolean isLivre() {
		return livre;
	}
	public void setLivre(boolean livre) {
		this.livre = livre;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public boolean isVendu() {
		return vendu;
	}
	public void setVendu(boolean vendu) {
		this.vendu = vendu;
	}
	public User getMembre() {
		return membre;
	}
	public void setMembre(User membre) {
		this.membre = membre;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}
	public int getIdImage() {
		return idImage;
	}
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	public List<ImageShop> getImages() {
		return images;
	}
	public void setImages(List<ImageShop> images) {
		this.images = images;
	}
        
        public void addImage(ImageShop image) {
            this.images.add(image);
        }
        
        public void deleteImage(ImageShop image) {
            this.images.remove(image);
        }
        
	public User getLiker() {
		return liker;
	}
	public void setLiker(User liker) {
		this.liker = liker;
	}
	
	
}
