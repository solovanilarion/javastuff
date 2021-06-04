package concurs.network.objectprotocol;

public class GetIdProbaResponse implements Response{
    private int idProba;
    public GetIdProbaResponse(int idProba){
        this.idProba = idProba;
    }
    public int getIdProba(){return idProba;}
}
