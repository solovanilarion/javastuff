package concurs.network.dto;

import concurs.model.Copil;
import concurs.model.Inscriere;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class DTOUtils {
    public static PersoanaOficiala getFromDTO(PersoanaOficialaDTO pdto){
        String username = pdto.getUsername();
        String pass = pdto.getPassword();
        return new PersoanaOficiala(username, pass);
    }

    public static PersoanaOficialaDTO getDTO(PersoanaOficiala user){
        String username = user.getUsername();
        String pass = user.getPassword();
        return new PersoanaOficialaDTO(username, pass);
    }

    public static Proba getFromDTO(ProbaDTO pdto){
        String denumire = pdto.getDenumire();
        String categorie = pdto.getCategorie();
        int nrPart = pdto.getNrPart();
        return new Proba(denumire, categorie, nrPart);
    }

    public static ProbaDTO getDTO(Proba proba){
        String denumire = proba.getDenumire();
        String categorie = proba.getCategorie();
        int nrPart = proba.getNrParticipanti();
        return new ProbaDTO(denumire, categorie, nrPart);
    }

    public static ProbadcDTO getDTOp(Proba proba){
        String denumire = proba.getDenumire();
        String categorie = proba.getCategorie();
        return new ProbadcDTO(denumire, categorie);
    }

    public static Proba getFromDTO(ProbadcDTO probadcDTO){
        return new Proba(probadcDTO.getDenumire(), probadcDTO.getCategorie());
    }

    public static Copil getFromDTO(CopilDTO copilDTO){
        return new Copil(copilDTO.getNume(), copilDTO.getPrenume(), copilDTO.getVarsta());
    }

    public static CopilDTO getDTO(Copil copil){
        return new CopilDTO(copil.getNume(), copil.getPrenume(), copil.getVarsta());
    }

}
