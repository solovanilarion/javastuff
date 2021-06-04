package concurs.network.objectprotocol;

import concurs.model.Copil;
import concurs.model.Inscriere;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;
import concurs.network.dto.*;
import concurs.services.ConcursException;
import concurs.services.IClient;
import concurs.services.IServer;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;

public class ClientObjectWorker implements Runnable, IClient {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private String username;

    public ClientObjectWorker(IServer server, Socket connection){
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void run(){
        while (connected){
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if(response!=null){
                    sendResponse((Response) response);
                }
            }catch (IOException| ConcursException |ClassNotFoundException e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request)throws ConcursException{
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq = (LoginRequest)request;
            PersoanaOficialaDTO pdto = logReq.getPersoana();
            PersoanaOficiala pers = DTOUtils.getFromDTO(pdto);
            try {
                server.login(pers,this);
                username = pers.getUsername();
                return new OkResponse();
            }catch (ConcursException e){
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq = (LogoutRequest)request;
            PersoanaOficialaDTO pdto =logReq.getPersoana();
            PersoanaOficiala pers = DTOUtils.getFromDTO(pdto);
            try {
                server.logout(pers);
                connected = false;
                return new OkResponse();
            }catch (ConcursException e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof SaveInscriereRequest){
            SaveInscriereRequest saveInscriereRequest = (SaveInscriereRequest) request;
            InscriereDTO inscriereDTO = saveInscriereRequest.getInscriereDTO();
            try {
                server.inscriere(inscriereDTO.getCopil().getNume(),inscriereDTO.getCopil().getPrenume(),inscriereDTO.getCopil().getVarsta(), inscriereDTO.getProba1().getDenumire(), inscriereDTO.getProba1().getCategorie(),inscriereDTO.getProba2().getDenumire(),inscriereDTO.getProba2().getCategorie(),username);
                return new OkResponse();
            }catch (ConcursException e){
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetAllProbaRequest){
            List<Proba> list = (List<Proba>) server.getAll();
            ProbaDTO[] listadto  = new ProbaDTO[list.size()];
            for(Proba proba: list){
                ProbaDTO probaDTO = DTOUtils.getDTO(proba);
                listadto[list.indexOf(proba)] = probaDTO;
            }
            return new GetAllProbaResponse(listadto);
        }
        if(request instanceof GetPDenumireRequest){
            List<String> list = (List<String>)server.getProbe();
            return new GetPDenumireResponse(list);
        }
        if(request instanceof GetPCategoriiRequest){
            List<String> list = (List<String>)server.getCategorii();
            return new GetPCategoriiResponse(list);
        }

        if(request instanceof GetCopiiRequest){
            GetCopiiRequest getCopiiRequest = (GetCopiiRequest)request;
            ProbadcDTO probadcDTO = getCopiiRequest.getProbadcDTO();
            Proba proba = DTOUtils.getFromDTO(probadcDTO);
            List<Copil>list = (List<Copil>)server.getCopii(proba);
            CopilDTO[] listdto = new CopilDTO[list.size()];
            for(Copil copil:list){
                CopilDTO copilDTO = DTOUtils.getDTO(copil);
                listdto[list.indexOf(copil)] = copilDTO;
            }
            return new GetCopiiResponse(listdto);
        }
        return null;
    }

    private void sendResponse(Response response)throws IOException{
        System.out.println("Sending response "+ response);
        output.writeObject(response);
        output.flush();
    }
    @Override
    public void increasedNrPart(Proba proba)throws ConcursException{
        System.out.println("Actualizare nr participanti");
        try {
            sendResponse(new IncreaseNrPartResponse(proba));
        }catch (IOException e){
            throw new ConcursException("Sending error " + e);
        }
    }
}
