package concurs.network.objectprotocol;
import concurs.network.dto.CopilDTO;

public class SaveCopilRequest implements Request{
    private CopilDTO copilDTO;
    public SaveCopilRequest(CopilDTO copilDTO){
        this.copilDTO = copilDTO;
    }
    public CopilDTO getCopilDTO(){return copilDTO;}
}
