package concurs.network.objectprotocol;
import concurs.network.dto.ProbaDTO;

public class GetAllProbaResponse implements Response{
    private ProbaDTO[] probe;
    protected GetAllProbaResponse(ProbaDTO[] probe){
        this.probe = probe;
    }
    public ProbaDTO[] getProbe(){
        return probe;
    }
}
