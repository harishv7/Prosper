package DataSci.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private Label futurePrice;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private LineChart<Number, Number> growthChart;
    
    @FXML
    private TextField growthRate;
    
    @FXML
    private Button analyseButton;
    
    @FXML
    private Label analysisArea;

    private XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    
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
                "Type S2"
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
    public void handleAnalyseGrowth(MouseEvent event) {
        Parameters parameters = getAllParams();
        String growthRateStr = growthRate.getText();
        Double growthRate = Double.parseDouble(growthRateStr);
    }

    @FXML
    public void handleSubmitEvent(MouseEvent event) throws IOException {
        Parameters parameters = getAllParams();
        JsonRequestResponseParser jsonRequestResponseParser = new JsonRequestResponseParser();
        
        String jsonString = jsonRequestResponseParser.parseInputParameters(parameters);
        
        System.out.println(jsonString);
        
        File file = new File("jsonFile.json");
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(jsonString);
        
        bufferedWriter.close();
        
        JsonRequestResponseController jsonController = new JsonRequestResponseController();
        jsonController.readJson(file.getName());
        
        System.out.println("Controller works");
        
        jsonController.readApiInfo("apiInfo.txt");
        
        String response = jsonController.rrsHttpPost();
        
        System.out.println(response);
        
        String substr1 = response.substring(0, response.lastIndexOf("\""));
        String substr2 = substr1.substring(substr1.lastIndexOf("\"") + 1);
        
        System.out.println(substr2);

        int price = (int) Double.parseDouble(substr2);
        System.out.println(price);
        
        futurePrice.setText(Integer.toString(price));
    }
    
    private Parameters getAllParams() {
        String yearStr = yearInput.getText();
        int year;
        if (yearStr == null || yearStr.equals("")) {
            yearStr = "";
            year = 0;
        } else {
            year = Integer.parseInt(yearStr);
        }
        
        String monthStr = monthInput.getText();
        int month;
        if (monthStr == null || monthStr.equals("")) {
            monthStr = "";
            month = 0;
        } else {
            month = Integer.parseInt(monthStr);
        }
        
        String region = regionInput.getValue();
        if (region == null) {
            region = "";
        }
        region = region.toUpperCase();
        
        String flatType = flatTypeInput.getValue();
        if (flatType == null) {
            flatType = "";
        }
        flatType = flatType.toUpperCase();
        
        String flatModel = flatModelInput.getValue();
        if (flatModel == null) {
            flatModel = "";
        }
        
        String flatAreaStr = flatAreaInput.getText();
        int flatArea;
        if (flatAreaStr == null || flatAreaStr.equals("")) {
            flatAreaStr = "";
            flatArea = 0;
        } else {
            flatArea = Integer.parseInt(flatAreaStr);
        }
        
        String leaseCommencementDateStr = leaseCommencementInput.getText();
        int leaseCommencementYear;
        if (leaseCommencementDateStr == null || leaseCommencementDateStr.equals("")) {
            leaseCommencementDateStr = "";
            leaseCommencementYear = 0;
        } else {
            leaseCommencementYear = Integer.parseInt(leaseCommencementDateStr);
        }
        
        System.out.println("Year: " + yearStr);
        System.out.println("month: " + monthStr);
        System.out.println("region: " + region);
        System.out.println("flat type: " + flatType);
        System.out.println("flat model: " + flatModel);
        System.out.println("flat area: " + flatArea);
        System.out.println("LCY: " + leaseCommencementYear);
        
        Parameters parameters = new Parameters(year, month, region, flatType, 
                flatArea, flatModel, leaseCommencementYear);
        
        return parameters;
    }

    public void initiateGraph(){
        growthChart.getData().add(series);
    }
    
    public void initialiseAll() {
        System.out.println("INIT");
        regionInput.setItems(regionInputChoices);
        flatModelInput.setItems(flatModelInputChoices);
        flatTypeInput.setItems(flatTypeInputChoices);
    }
    
    public void addData(Number x, Number y) {
    	series.getData().add(new XYChart.Data<Number, Number>(x, y));
    }
}