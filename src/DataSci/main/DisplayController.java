package DataSci.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DisplayController {
    
    @FXML
    private TextField yearInput;
    
    @FXML
    private TextField monthInput;
    
    @FXML
    private ComboBox<String> regionInput;
    
    @FXML
    private ChoiceBox<Label> numOfRoomsInput;
    
    @FXML
    private ChoiceBox<Label> flatModelInput;
    
    @FXML
    private TextField flatAreaInput;
    
    @FXML
    private TextField leaseCommencementInput;
    
    @FXML
    private Button submitButton;
    
    private ObservableList<String> regionInputChoices = FXCollections.observableArrayList("Woolands", "Tampines", "Jurong West", "Jurong East");
    
    public DisplayController() {
       // initialiseChoiceBox(regionInput, regionInputChoices);
       regionInput = new ComboBox<String>(regionInputChoices);
       regionInput.getItems().addAll(
               "Aaaa",
               "Baaa",
               "sss"
               );
       System.out.println("Display Controller Initialised");
    }
    
    private void initialiseChoiceBox(ChoiceBox<String> choiceBox,
            ObservableList<String> choices) {
            choiceBox = new ChoiceBox<String>();
            choiceBox.setItems(choices);
        
    }

    @FXML
    public void handleSubmitEvent(MouseEvent event) throws Exception {
        System.out.println("DDJD");
    }
}
