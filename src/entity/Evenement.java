/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Meedoch
 */
public class Evenement extends RecursiveTreeObject<Evenement>{
    
    private Integer id;
    private Integer organisateur_id;
    private Integer guide_id=organisateur_id;
    private boolean boosted;
    private Timestamp dateHeureDepart;
    public Timestamp dateCreation;
    private String description;
    private String destination="";
    private double frais;
    private String lieuRassemblement;
    private int nombrePlaces;
    private int nombrePlacesPrises=0;
    private int nombreRates=0;
    private double rating=0.0;
    private String reglement;
    private String statut="En attente";
    private String evenementType;
    private Integer duree=0;
    private String difficulte;
    private Double distanceParcourue = 0.0;
    private String type;
    private StringProperty datecr;
    private List<Image> images=  new ArrayList<Image>();
    private List<Creneau> planning=  new ArrayList<Creneau>();
  
    public boolean isBoostable(){
        if (boosted)
            return false;
        if (statut.equals("Accepté")==false)
            return false;
        if (dateHeureDepart.toLocalDateTime().isBefore(LocalDateTime.now()))
            return false;
        return true;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public Timestamp getDateHeureDepart() {
        return dateHeureDepart;
    }

    public void setDateHeureDepart(Timestamp dateHeureDepart) {
        this.dateHeureDepart = dateHeureDepart;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public String getLieuRassemblement() {
        return lieuRassemblement;
    }

    public void setLieuRassemblement(String lieuRassemblement) {
        this.lieuRassemblement = lieuRassemblement;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public int getNombrePlacesPrises() {
        return nombrePlacesPrises;
    }

    public void setNombrePlacesPrises(int nombrePlacesPrises) {
        this.nombrePlacesPrises = nombrePlacesPrises;
    }

    public int getNombreRates() {
        return nombreRates;
    }

    public void setNombreRates(int nombreRates) {
        this.nombreRates = nombreRates;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReglement() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement = reglement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getEvenementType() {
        return evenementType;
    }

    public void setEvenementType(String evenementType) {
        this.evenementType = evenementType;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public Double getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(Double distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrganisateur_id() {
        return organisateur_id;
    }

    public void setOrganisateur_id(Integer organisateur_id) {
        this.organisateur_id = organisateur_id;
    }

    public Integer getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(Integer guide_id) {
        this.guide_id = guide_id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Creneau> getPlanning() {
        return planning;
    }

    public void setPlanning(List<Creneau> planning) {
        this.planning = planning;
    }
    
}
