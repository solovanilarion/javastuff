package concurs.repository;

import concurs.model.Copil;

public interface ICopilRepository extends IRepository<Integer, Copil> {
    int getId(String nume, String prenume, int varsta);
}
