package concurs.server;

import concurs.model.Copil;
import concurs.model.Inscriere;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;
import concurs.network.dto.DTOUtils;
import concurs.network.dto.ProbaDTO;
import concurs.repository.ICopilRepository;
import concurs.repository.IPersoanaOficialaRepository;
import concurs.repository.IProbaRepository;
import concurs.repository.IRepository;
import concurs.services.ConcursException;
import concurs.services.IClient;
import concurs.services.IServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements IServer {
    private IPersoanaOficialaRepository repoPers;
    private IProbaRepository repoProba;
    private ICopilRepository repoCopil;
    private IRepository<Integer, Inscriere> repoInscriere;
    private Map<String, IClient> loggedPers;

    public ServerImpl(IPersoanaOficialaRepository repoPers, IProbaRepository repoProba, IRepository<Integer, Inscriere> repoInscriere,ICopilRepository repoCopil){
        this.repoPers = repoPers;
        this.repoProba = repoProba;
        this.repoInscriere = repoInscriere;
        this.repoCopil = repoCopil;
        loggedPers = new ConcurrentHashMap<>();
    }
    @Override
    public synchronized void login(PersoanaOficiala pers, IClient client) throws ConcursException {
        PersoanaOficiala persoanaOficiala = repoPers.findBy(pers.getUsername(), pers.getPassword());
        if (persoanaOficiala!=null){
            if(loggedPers.get(persoanaOficiala.getUsername())!=null)
                throw new ConcursException("Persoana deja logata.");
            loggedPers.put(pers.getUsername(), client);
        }
        else {
            throw new ConcursException("Eroare la autentificare");
        }
    }
    @Override
    public synchronized void logout(PersoanaOficiala pers) throws ConcursException {
        loggedPers.remove(pers.getUsername());
    }

    @Override
    public Iterable<Proba> getAll() throws ConcursException {
        return repoProba.findAll();
    }
    @Override
    public Iterable<String> getProbe() throws ConcursException {
        return repoProba.getDenumiri();
    }

    @Override
    public Iterable<String> getCategorii() throws ConcursException {
        return repoProba.getCategorii();
    }

    @Override
    public Iterable<Copil> getCopii(Proba proba) throws ConcursException {
        String denumire = proba.getDenumire();
        String categorie = proba.getCategorie();
        int idProba = -1;
        for(Proba p : repoProba.findAll()){
            if(p.getDenumire().equals(denumire) && p.getCategorie().equals(categorie)){
                idProba = p.getIdProba();
            }
        }
        List<Copil> rez = new ArrayList<>();
        for(Inscriere inscriere : repoInscriere.findAll()){
            if(inscriere.getIdProba1()==idProba || inscriere.getIdProba2()==idProba)
                rez.add(repoCopil.findOne(inscriere.getIdCopil()));
        }
        return rez;
    }

    @Override
    public void inscriere (String numeC, String prenumeC, int varstaC, String den1, String cat1, String den2, String cat2, String username) throws ConcursException {
        int id1 = repoProba.getIDProba(den1, cat1);
        int id2 = repoProba.getIDProba(den2, cat2);

        Proba proba1 = repoProba.findOne(id1);
        Proba proba2 = repoProba.findOne(id2);

        if (proba1.equals(proba2)){
            throw  new ConcursException("Probele trebuie sa fie diferite.");
        }
        if (id1==-1 &&id2==-1){
            throw  new ConcursException("E necesara inscrierea la cel putin o proba.");
        }

        Copil copil = new Copil(numeC, prenumeC, varstaC);
        repoCopil.save(copil);
        int idCopil = repoCopil.getId(numeC, prenumeC, varstaC);

        Inscriere inscriere = new Inscriere(idCopil, id1, id2);
        repoInscriere.save(inscriere);

        if (id1!=-1){
            //increase
            proba1.setNrParticipanti(proba1.getNrParticipanti()+1);
            repoProba.update(id1, proba1);
            notify(username, proba1);
        }

        if (id2!=-1){
            proba2.setNrParticipanti(proba2.getNrParticipanti()+1);
            repoProba.update(id2, proba2);
            notify(username, proba2);
        }

    }
    private void notify(String username, Proba proba) throws ConcursException {
        for (String usernamePers : loggedPers.keySet()){
            this.loggedPers.get(usernamePers).increasedNrPart(proba);
            System.out.println("Am notificat pe " + usernamePers);
        }
    }

}
