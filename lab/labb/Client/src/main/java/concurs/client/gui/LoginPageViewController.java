package concurs.client.gui;
import concurs.model.PersoanaOficiala;
import concurs.model.Proba;
import concurs.services.ConcursException;
import concurs.services.IClient;
import concurs.services.IServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginPageViewController extends AbstractViewController implements IClient{
    @FXML
    TextField textfieldUsername;
    @FXML
    TextField textfieldParola;
    @FXML
    Button buttonAutentificare;

    private IServer server;
    private ProbeViewController pvc;

    public LoginPageViewController(){}

    public void setService(IServer server){
        this.server = server;
    }

    @FXML
    public void handleButtonAutentificare(ActionEvent e){
        try{
            String username = textfieldUsername.getText();
            String parola = textfieldParola.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/toateView2.fxml"));
            try {
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                ((Node)(e.getSource())).getScene().getWindow().hide();

                PersoanaOficiala persoanaOficiala= new PersoanaOficiala(username,parola);

                stage.setTitle(persoanaOficiala.getUsername());
                stage.setScene(new Scene(root1));
                stage.show();

                server.login(persoanaOficiala, this);

                ToateViewController toateViewController = fxmlLoader.getController();
                toateViewController.setService(server, persoanaOficiala);
                toateViewController.initData();

                pvc = toateViewController.getPvc();
            } catch (IOException | ConcursException e1) {
                showErrorMessage(e1.getMessage());
            }
        } catch (NullPointerException ex){
            showErrorMessage("Username si parola incorecte");
        }
    }

    @Override
    public void increasedNrPart(Proba proba) throws ConcursException {
        pvc.increasedNrPart(proba);
    }
}
