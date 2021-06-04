package concurs.services;

import concurs.model.Copil;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;

import java.util.List;

public interface IServer {
    //IPersoanaOficialaService
    void login(PersoanaOficiala pers, IClient client) throws ConcursException;

    void logout(PersoanaOficiala pers) throws ConcursException;

    //IProbaService
    Iterable<Proba> getAll() throws ConcursException;
    Iterable<String> getProbe() throws ConcursException;
    Iterable<String> getCategorii() throws ConcursException;

    //ICautareService
    Iterable<Copil> getCopii(Proba proba) throws ConcursException;

    //IInscriereService - save
    void inscriere(String numeC, String prenumeC, int varstaC, String den1, String cat1, String den2, String cat2, String username) throws ConcursException;


}
