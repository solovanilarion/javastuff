package concurs.model;

public class Inscriere {
    private int idCopil;
    private int idProba1;
    private int IdProba2;

    public Inscriere(int idCopil, int idProba1, int idProba2) {
        this.idCopil = idCopil;
        this.idProba1 = idProba1;
        IdProba2 = idProba2;
    }

    @Override
    public String toString() {
        return "concurs.model.Inscriere{" +
                "idCopil=" + idCopil +
                ", idProba1=" + idProba1 +
                ", IdProba2=" + IdProba2 +
                '}';
    }

    public int getIdCopil() {
        return idCopil;
    }

    public int getIdProba1() {
        return idProba1;
    }

    public int getIdProba2() {
        return IdProba2;
    }
}
