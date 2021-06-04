package concurs.network.objectprotocol;
import concurs.network.dto.PersoanaOficialaDTO;

public class LoginRequest implements Request{
    PersoanaOficialaDTO pers;
    public LoginRequest(PersoanaOficialaDTO pers){
        this.pers = pers;
    }
    public PersoanaOficialaDTO getPersoana(){return pers;}
}
