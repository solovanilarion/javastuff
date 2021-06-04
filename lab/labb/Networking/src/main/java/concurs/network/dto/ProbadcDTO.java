package concurs.network.dto;

import java.io.Serializable;

public class ProbadcDTO implements Serializable{
    String denumire;
    String categorie;

    public ProbadcDTO(String denumire, String categorie) {
        this.denumire = denumire;
        this.categorie = categorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getCategorie() {
        return categorie;
    }
}
