package concurs.client.gui;

import concurs.services.IServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

abstract class AbstractViewController {
    void showErrorMessage(String msg){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(msg);
        message.showAndWait();
    }

    static void showMessage(Alert.AlertType type, String header, String msg){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(msg);
        message.showAndWait();
    }

//    void handleDeconectare(ActionEvent e){
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginPageView.fxml"));
//        try {
//            Parent root1 = fxmlLoader.load();
//            Stage stage = new Stage();
//            ((Node)(e.getSource())).getScene().getWindow().hide();
//            LoginPageViewController loginPageViewController = fxmlLoader.getController();
//            loginPageViewController.setService(server);
//            stage.setTitle("Autentificare");
//            stage.setScene(new Scene(root1));
//            stage.show();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//    }
}
