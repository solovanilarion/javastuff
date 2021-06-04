package concurs.client.gui;

import concurs.model.PersoanaOficiala;
import concurs.services.ConcursException;
import concurs.services.IServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ToateViewController extends AbstractViewController{
    @FXML
    TabPane tabPaneToate;
    @FXML
    Tab tabProbe;
    @FXML
    Tab tabInscriere;
    @FXML
    Tab tabCautare;

    private IServer server;
    private PersoanaOficiala pers;
    private ProbeViewController pvc;

    public ToateViewController(){}

    void setService(IServer server, PersoanaOficiala pers){
        this.server = server;
        this.pers = pers;
    }

    void initData() throws IOException, ConcursException {
        @SuppressWarnings("resource")
        FXMLLoader loaderProbe = new FXMLLoader();
        loaderProbe.setLocation(ClassLoader.getSystemResource("view/probeView.fxml"));
        BorderPane bpProbe = loaderProbe.load();
        ProbeViewController probeViewController=loaderProbe.getController();
        probeViewController.setService(server);
        pvc = probeViewController;
        tabProbe.setContent(bpProbe);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("view/cautareView.fxml"));
        BorderPane bpCautare = loader.load();
        CautareViewController cautareViewController = loader.getController();
        cautareViewController.setService(server);
        cautareViewController.initData();
        tabCautare.setContent(bpCautare);

        FXMLLoader loaderInscriere = new FXMLLoader();
        loaderInscriere.setLocation(ClassLoader.getSystemResource("view/inscriereView.fxml"));
        BorderPane bpInscriere = loaderInscriere.load();
        InscriereViewController inscriereViewController = loaderInscriere.getController();
        inscriereViewController.setService(server);
        inscriereViewController.initData(pers.getUsername(), probeViewController);
        tabInscriere.setContent(bpInscriere);
    }

    @FXML
    public void handleDeconectare(ActionEvent e){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginPageView.fxml"));
        try {
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            ((Node)(e.getSource())).getScene().getWindow().hide();
            LoginPageViewController loginPageViewController = fxmlLoader.getController();
            loginPageViewController.setService(server);
            server.logout(pers);
            stage.setTitle("Autentificare");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ConcursException e1) {
            showErrorMessage(e1.getMessage());
        }
    }

    public ProbeViewController getPvc(){
        return  pvc;
    }
}
