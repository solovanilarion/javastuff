package concurs.network.dto;

import java.io.Serializable;

public class ProbaDTO implements Serializable{
    String denumire;
    String categorie;
    int nrPart;


    public ProbaDTO(String denumire, String categorie, int nrPart) {
        this.denumire = denumire;
        this.categorie = categorie;
        this.nrPart = nrPart;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getNrPart() {
        return nrPart;
    }
}
