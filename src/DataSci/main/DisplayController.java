package DataSci.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DisplayController {
    
    @FXML
    private TextField yearInput;
    
    @FXML
    private TextField monthInput;
    
    @FXML
    private ComboBox<String> regionInput = new ComboBox<String>();
    
    @FXML
    private ComboBox<String> flatTypeInput = new ComboBox<String>();
    
    @FXML
    private ComboBox<String> flatModelInput = new ComboBox<String>();
    
    @FXML
    private TextField flatAreaInput;
    
    @FXML
    private TextField leaseCommencementInput;
    
    @FXML
    private Button submitButton;
    
    
    private ObservableList<String> regionInputChoices = FXCollections.observableArrayList();
    private ObservableList<String> flatTypeInputChoices = FXCollections.observableArrayList();
    private ObservableList<String> flatModelInputChoices = FXCollections.observableArrayList();
    
    public DisplayController() {
        fillRegionInputChoices();
        fillFlatTypeInputChoices();
        fillFlatModelInputChoices();
    }

    private void fillFlatModelInputChoices() {
        flatModelInputChoices.addAll("New Generation",
                "Improved",
                "Model A",
                "Model A-Maisonette",
                "Model A2",
                "Standard",
                "Apartment",
                "Maisonette",
                "Simplified",
                "Multi Generation",
                "Adjoined flat",
                "Premium Apartment",
                "Terrace",
                "Improved-Maisonette",
                "Premium Maisonette",
                "2-room",
                "DBSS",
                "Type S1",
                "Type S2",
                "na"
                );
    }

    private void fillFlatTypeInputChoices() {
        flatTypeInputChoices.addAll("1 Room",
                "2 Room",
                "3 Room",
                "4 Room",
                "5 Room",
                "Executive",
                "Multi-Generation"
                );
    }

    private void fillRegionInputChoices() {
        regionInputChoices.addAll("Ang Mo Kio", 
                "Bedok", 
                "Bishan", 
                "Bukit Batok", 
                "Bukit Merah",
                "Bukit Panjang",
                "Bukit Timah",
                "Central Area",
                "Choa Chu Kang",
                "Clementi",
                "Geylang",
                "Hougang",
                "Jurong East",
                "Jurong West",
                "Kallang/Whampoa",
                "Marine Parade",
                "Pasir Ris",
                "Queenstown",
                "Sengkang",
                "Serangoon",
                "Tampines",
                "Toa Payoh",
                "Woodlands",
                "Yishun",
                "Sembawang",
                "Punggol"  
                );    
    }

    @FXML
    public void handleSubmitEvent(MouseEvent event) throws Exception {
        getAllParams();
    }
    
    private void getAllParams() {
        String yearStr = yearInput.getText();
        int year = Integer.parseInt(yearStr);
        
        String monthStr = monthInput.getText();
        int month = Integer.parseInt(monthStr);
        
        String region = regionInput.getValue();
        
        String flatType = flatTypeInput.getValue();
        
        String flatModel = flatModelInput.getValue();
        
        String flatAreaStr = flatAreaInput.getText();
        int flatArea = Integer.parseInt(flatAreaStr);
        
        String leaseCommencementDateStr = leaseCommencementInput.getText();
        int leaseCommencementYear = Integer.parseInt(leaseCommencementDateStr);
        
        System.out.println("Year: " + yearStr);
        System.out.println("month: " + monthStr);
        System.out.println("region: " + region);
        System.out.println("flat type: " + flatType);
        System.out.println("flat model: " + flatModel);
        System.out.println("flat area: " + flatArea);
        System.out.println("LCY: " + leaseCommencementYear);
    }

    @FXML
    public void mouseClicked() {
        System.out.println("MOUSE");
    }

    public void initialiseAll() {
        System.out.println("INIT");
        regionInput.setItems(regionInputChoices);
        flatModelInput.setItems(flatModelInputChoices);
        flatTypeInput.setItems(flatTypeInputChoices);
    }
}
