package concurs.repository;


import concurs.model.PersoanaOficiala;


public interface IPersoanaOficialaRepository extends IRepository<Integer,PersoanaOficiala> {
    PersoanaOficiala validareDate(String username, String password);
    PersoanaOficiala findBy(String username, String parola);
}
