package concurs.client.gui;

import concurs.model.Copil;
import concurs.model.Proba;
import concurs.services.ConcursException;
import concurs.services.IClient;
import concurs.services.IServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;

public class CautareViewController extends AbstractViewController{
    @FXML
    ComboBox<String> comboboxProba;
    @FXML
    ComboBox<String> comboboxCategoria;
    @FXML
    Button buttonCauta;
    @FXML
    TableView<Copil> tableviewCopii;
    @FXML
    Label labelEmptyTable;
    @FXML
    TableColumn<Copil,String> columnNume;
    @FXML
    TableColumn<Copil,String> columnPrenume;
    @FXML
    TableColumn<Copil,Integer> columnVarsta;

    ObservableList<Copil> modelCopil;
    IServer server;

    //constructor
    public CautareViewController(){}

    public void setService(IServer server){
        this.server = server;
    }

    public void initData() throws ConcursException {
        initCombobox();
        initTableview();
    }

    private void initTableview() {
        labelEmptyTable.setText("Momentan nu exista participanti inscrisi");
        tableviewCopii.setPlaceholder(labelEmptyTable);
        columnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        columnPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        columnVarsta.setCellValueFactory(new PropertyValueFactory<>("varsta"));
    }

    private void initCombobox() throws ConcursException {
        try{
            server.getProbe().forEach(denumire -> comboboxProba.getItems().add(denumire));
            server.getCategorii().forEach(categorie -> comboboxCategoria.getItems().addAll(categorie));
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonCauta(){
        try{
            String denumire = comboboxProba.getSelectionModel().getSelectedItem();
            String categorie = comboboxCategoria.getSelectionModel().getSelectedItem();
            Proba proba = new Proba(denumire, categorie);
            tableviewCopii.getItems().clear();
            this.modelCopil = FXCollections.observableArrayList((Collection<? extends Copil>) server.getCopii(proba));
            tableviewCopii.setItems(modelCopil);
        } catch(NullPointerException e){
            showErrorMessage("Introduceti date inainte de adaugare");
        } catch (ConcursException e) {
            e.printStackTrace();
        }
    }
}
