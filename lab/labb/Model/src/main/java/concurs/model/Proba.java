package concurs.model;

import java.io.Serializable;

public class Proba implements Serializable{
    private int idProba;
    private String denumire;
    private String categorie;
    private int nrParticipanti;

    public Proba(int idProba, String denumire, String categorie,int nrParticipanti) {
        this.idProba = idProba;
        this.denumire = denumire;
        this.categorie = categorie;
        this.nrParticipanti=nrParticipanti;
    }

    public Proba(String denumire, String categorie,int nrParticipanti) {
        this.denumire = denumire;
        this.categorie = categorie;
        this.nrParticipanti=nrParticipanti;
    }

    public Proba(String denumire, String categorie){
        this.denumire = denumire;
        this.categorie = categorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getNrParticipanti(){
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public int getIdProba(){
        return idProba;
    }


    @Override
    public String toString() {
        return "concurs.model.Proba{" +
                "idProba= " + idProba +
                ", denumire= " + denumire + '\'' +
                ", categorie= " + categorie + '\'' +
                ", nrParticipanti= " + nrParticipanti +
                '}';
    }
}
