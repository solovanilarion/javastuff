package concurs.network.objectprotocol;
import concurs.network.dto.CopilDTO;

public class GetIdCopilRequest implements Request{
    private CopilDTO copilDTO;
    public GetIdCopilRequest(CopilDTO copilDTO){
        this.copilDTO = copilDTO;
    }
    public CopilDTO getCopilDTO(){return copilDTO;}
}
