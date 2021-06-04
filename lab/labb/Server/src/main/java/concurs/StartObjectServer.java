package concurs;


import concurs.model.Copil;
import concurs.model.Inscriere;
import concurs.network.utils.AbstractServer;
import concurs.network.utils.ObjectConcurrentServer;
import concurs.network.utils.ServerException;
import concurs.repository.*;
import concurs.server.ServerImpl;
import concurs.services.IServer;

import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort = 55555;
    public static void main(String[] args){
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set");
            serverProps.list(System.out);
        }catch (IOException e){
            System.err.println("Cannot find chatserver.properties"+e);
            return;
        }
        IPersoanaOficialaRepository repoPers = new PersoanaOficialaRepository(serverProps);
        IProbaRepository repoProba = new ProbaRepository(serverProps);
        IRepository<Integer, Inscriere> repoInscriere = new InscriereRepository(serverProps);
        ICopilRepository repoCopil = new CopilRepository(serverProps);
        IServer serverImpl = new ServerImpl(repoPers, repoProba, repoInscriere, repoCopil);

        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("concurs.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong Port Number "+ nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: "+ serverPort);
        AbstractServer server = new ObjectConcurrentServer(serverPort,serverImpl);
        try {
            server.start();
        }catch (ServerException e){
            System.err.println("Error starting the server "+e.getMessage());
        }
    }


}
