package com.example.plant;

import com.google.firebase.Timestamp;

public class PlantModel {

    String nameOfPlant;
    String nameOfDiseases;

    public PlantModel(String nameOfPlant, String nameOfDiseases, String img, String cause, String treatment) {
        this.nameOfPlant = nameOfPlant;
        this.nameOfDiseases = nameOfDiseases;
        this.img = img;
        this.cause = cause;
        this.treatment = treatment;
    }

    public PlantModel() {

    }

    String img;
    String cause;
    String treatment;

/*    Timestamp time;*/

    public String getNameOfPlant() {
        return nameOfPlant;
    }

    public void setNameOfPlant(String nameOfPlant) {
        this.nameOfPlant = nameOfPlant;
    }

    public String getNameOfDiseases() {
        return nameOfDiseases;
    }

    public void setNameOfDiseases(String nameOfDiseases) {
        this.nameOfDiseases = nameOfDiseases;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

}
