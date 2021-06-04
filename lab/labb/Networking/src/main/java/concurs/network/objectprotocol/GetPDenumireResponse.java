package concurs.network.objectprotocol;
import java.util.List;

public class GetPDenumireResponse implements Response{
    List<String> denumiri;
    public GetPDenumireResponse(List<String> denumiri){
        this.denumiri = denumiri;
    }
    public List<String> getDenumiri(){return denumiri;}
}
