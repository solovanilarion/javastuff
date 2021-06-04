package concurs.network.objectprotocol;
import concurs.network.dto.InscriereDTO;

public class SaveInscriereRequest implements Request{
    private InscriereDTO inscriereDTO;
    public SaveInscriereRequest(InscriereDTO inscriereDTO){
        this.inscriereDTO = inscriereDTO;
    }
    public InscriereDTO getInscriereDTO(){return inscriereDTO;}
}
