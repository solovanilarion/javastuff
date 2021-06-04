package concurs.network.objectprotocol;
import concurs.network.dto.PersoanaOficialaDTO;

public class LogoutRequest implements Request{
    PersoanaOficialaDTO pers;
    public LogoutRequest(PersoanaOficialaDTO pers){
        this.pers = pers;
    }
    public PersoanaOficialaDTO getPersoana(){return pers;}
}
