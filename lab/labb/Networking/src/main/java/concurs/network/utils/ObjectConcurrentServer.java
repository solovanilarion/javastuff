package concurs.network.utils;
import concurs.network.objectprotocol.ClientObjectWorker;
import concurs.services.IServer;

import java.net.Socket;


public class ObjectConcurrentServer extends AbsConcurrentServer{
    public ObjectConcurrentServer(int port){super(port);}

    private IServer server;

    public ObjectConcurrentServer(int port, IServer server){
        super(port);
        this.server = server;
        System.out.println("ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client){
        ClientObjectWorker worker = new ClientObjectWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }

}
