package concurs.network.objectprotocol;
import concurs.model.Proba;

public class IncreaseNrPartResponse implements UpdateResponse{
    private Proba proba;
    public IncreaseNrPartResponse(Proba proba){
        this.proba = proba;
    }
    public Proba getProba(){return proba;}
}
