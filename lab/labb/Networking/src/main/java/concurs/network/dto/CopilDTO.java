package concurs.network.dto;

import java.io.Serializable;

public class CopilDTO implements Serializable{
    private String nume;
    private String prenume;
    private int varsta;


    public CopilDTO(String nume, String prenume, int varsta) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public int getVarsta() {
        return varsta;
    }
}
