package concurs.network.objectprotocol;

public class IncreaseNrPartRequest implements Request {
    private int idProba;
    public IncreaseNrPartRequest(int idProba){
        this.idProba = idProba;
    }
    public int getIdProba(){
        return idProba;
    }
}
