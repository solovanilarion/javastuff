package concurs.client;

import concurs.client.gui.LoginPageViewController;
import concurs.network.objectprotocol.ServerObjectProxy;
import concurs.services.IServer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    private static IServer server;

    public static void main(String[] args) {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        server=new ServerObjectProxy(serverIP, serverPort);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("view/loginPageView.fxml"));
        BorderPane root = new BorderPane();
        root = loader.load();

        LoginPageViewController loginPageViewController = loader.getController();
        loginPageViewController.setService(server);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Autentificare");
        primaryStage.show();
    }
}

