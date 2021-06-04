package concurs.network.objectprotocol;
import java.util.List;

public class GetPCategoriiResponse implements Response{
    List<String> categorii;
    public GetPCategoriiResponse(List<String> categorii){
        this.categorii = categorii;
    }
    public List<String> getCategorii(){return categorii;}
}
