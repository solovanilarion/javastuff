package concurs.network.objectprotocol;
import concurs.network.dto.ProbadcDTO;

public class GetIdProbaRequest implements Request{
    ProbadcDTO probadcDTO;
    public GetIdProbaRequest(ProbadcDTO probadcDTO){
        this.probadcDTO = probadcDTO;
    }
    public ProbadcDTO getProbadcDTO(){
        return probadcDTO;
    }
}
