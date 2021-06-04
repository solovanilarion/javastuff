package concurs.network.objectprotocol;
import concurs.network.dto.CopilDTO;

public class GetCopiiResponse implements Response{
    private CopilDTO[] copii;
    public GetCopiiResponse(CopilDTO[] copii){
        this.copii = copii;
    }
    public CopilDTO[] getCopii(){return copii;}
}
