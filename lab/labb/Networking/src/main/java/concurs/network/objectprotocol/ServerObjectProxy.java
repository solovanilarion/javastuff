package concurs.network.objectprotocol;

import concurs.model.Copil;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;
import concurs.network.dto.*;
import concurs.services.ConcursException;
import concurs.services.IClient;
import concurs.services.IServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerObjectProxy implements IServer {
    private String host;
    private int port;
    private IClient client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerObjectProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(PersoanaOficiala pers, IClient client)throws ConcursException{
        initializeConnection();
        PersoanaOficialaDTO persoanaOficialaDTO = DTOUtils.getDTO(pers);
        sendRequest(new LoginRequest(persoanaOficialaDTO));
        Response response = readResponse();
        if(response instanceof OkResponse){
            this.client = client;
            return;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ConcursException(err.getMessage());
        }

    }

    @Override
    public void logout(PersoanaOficiala pers)throws ConcursException{
        PersoanaOficialaDTO persoanaOficialaDTO = DTOUtils.getDTO(pers);
        sendRequest(new LogoutRequest(persoanaOficialaDTO));
        Response response = readResponse();
        closeConnection();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new ConcursException(err.getMessage());
        }

    }

    @Override
    public Iterable<Proba>getAll()throws ConcursException{
        sendRequest(new GetAllProbaRequest());
        Response response = readResponse();
        if(response instanceof GetAllProbaResponse){
            List<Proba> rez = new ArrayList<>();
            for (ProbaDTO probaDTO:((GetAllProbaResponse)response).getProbe()){
                rez.add(DTOUtils.getFromDTO(probaDTO));
            }
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ConcursException(err.getMessage());

        }
        return null;
    }

    @Override
    public Iterable<String> getProbe()throws ConcursException{
        sendRequest(new GetPDenumireRequest());
        Response response = readResponse();
        if(response instanceof GetPDenumireResponse){
            List<String> rez = new ArrayList<>();
            for(String d:((GetPDenumireResponse)response).getDenumiri()){
                rez.add(d);
            }
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse)response;
            closeConnection();
            throw new ConcursException(err.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<String>getCategorii() throws ConcursException{
        sendRequest(new GetPCategoriiRequest());
        Response response = readResponse();
        if(response instanceof GetPCategoriiResponse){
            List<String> rez = new ArrayList<>();
            for(String c:((GetPCategoriiResponse)response).getCategorii()){
                rez.add(c);
            }
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse)response;
            closeConnection();
            throw new ConcursException(err.getMessage());
        }
        return null;
    }


    @Override
    public Iterable<Copil> getCopii(Proba proba)throws ConcursException{
        ProbadcDTO probadcDTO = DTOUtils.getDTOp(proba);
        sendRequest(new GetCopiiRequest(probadcDTO));
        Response response = readResponse();
        if(response instanceof GetCopiiResponse){
            List<Copil> rez = new ArrayList<>();
            for(CopilDTO c:((GetCopiiResponse)response).getCopii()){
                rez.add(DTOUtils.getFromDTO(c));
            }
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ConcursException(err.getMessage());
        }
        return null;
    }


    @Override
    public void inscriere(String numeC, String prenumeC, int varstaC, String den1, String cat1, String den2, String cat2, String username)throws ConcursException{
        CopilDTO copilDTO = new CopilDTO(numeC, prenumeC, varstaC);
        ProbadcDTO proba1 = new ProbadcDTO(den1, cat1);
        ProbadcDTO proba2 = new ProbadcDTO(den2, cat2);

        InscriereDTO inscriereDTO = new InscriereDTO(copilDTO, proba1, proba2);
        sendRequest(new SaveInscriereRequest(inscriereDTO));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ConcursException(err.getMessage());
        }
    }

    private void initializeConnection() throws ConcursException{
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input =  new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private void sendRequest(Request request)throws ConcursException{
        try {
            output.writeObject(request);
            output.flush();
        }catch (IOException e){
            throw new ConcursException("Error sending object "+ e);
        }
    }

    private Response readResponse() throws ConcursException{
        Response response = null;
        try {
            response = qresponses.take();
        }catch (InterruptedException| NullPointerException e){
            e.printStackTrace();
        }
        return response;
    }
    private void closeConnection(){
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while (!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received "+response);
                    if(response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else {
                        try {
                            qresponses.put((Response)response);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch (IOException | ClassNotFoundException e){
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private void handleUpdate(UpdateResponse response){
        if(response instanceof IncreaseNrPartResponse){
            IncreaseNrPartResponse increaseNrPartResponse = (IncreaseNrPartResponse)response;
            Proba proba = increaseNrPartResponse.getProba();
            try {
                client.increasedNrPart(proba);
                System.out.println("handleUpdateProxy ok");
            }catch (ConcursException e){
                e.printStackTrace();
            }
        }
    }

}
