package concurs.repository;

import concurs.model.Proba;


public interface IProbaRepository extends IRepository<Integer,Proba> {
    Iterable<String> getDenumiri();
    Iterable<String> getCategorii();
    int getIDProba(String denumire, String categorie);
}
