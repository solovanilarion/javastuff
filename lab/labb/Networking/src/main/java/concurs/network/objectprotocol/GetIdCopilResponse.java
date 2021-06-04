package concurs.network.objectprotocol;

public class GetIdCopilResponse implements Response {
    private int idCopil;
    public GetIdCopilResponse(int idCopil){
        this.idCopil = idCopil;
    }
    public int getIdCopil(){return idCopil;}
}
