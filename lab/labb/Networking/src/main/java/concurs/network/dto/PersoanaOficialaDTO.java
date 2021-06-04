package concurs.network.dto;

import java.io.Serializable;

public class PersoanaOficialaDTO implements Serializable {
    String username;
    String password;


    public PersoanaOficialaDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Pers["+username+" "+password+"]";
    }
}
