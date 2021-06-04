package concurs.network.dto;

import java.io.Serializable;

public class InscriereDTO implements Serializable{
    private CopilDTO copil;
    private ProbadcDTO proba1;
    private ProbadcDTO proba2;

    public InscriereDTO(CopilDTO copil, ProbadcDTO proba1, ProbadcDTO proba2) {
        this.copil = copil;
        this.proba1 = proba1;
        this.proba2 = proba2;
    }

    public CopilDTO getCopil() {
        return copil;
    }

    public ProbadcDTO getProba1() {
        return proba1;
    }

    public ProbadcDTO getProba2() {
        return proba2;
    }
}
