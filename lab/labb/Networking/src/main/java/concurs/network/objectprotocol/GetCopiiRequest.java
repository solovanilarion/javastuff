package concurs.network.objectprotocol;
import concurs.network.dto.ProbadcDTO;

public class GetCopiiRequest implements Request{
    ProbadcDTO probadcDTO;
    public GetCopiiRequest(ProbadcDTO probadcDTO){
        this.probadcDTO = probadcDTO;
    }
    public ProbadcDTO getProbadcDTO() {
        return probadcDTO;
    }
}
