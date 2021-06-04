package concurs.services;

import concurs.model.Proba;


public interface IClient {
    void increasedNrPart(Proba proba) throws ConcursException;
}
