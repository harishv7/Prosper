package DataSci.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

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

    private DecimalFormat format = new DecimalFormat("#,###");

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
    public void handleAnalyseGrowth(MouseEvent event) throws IOException {
        series = new XYChart.Series<Number, Number>();
        growthChart.getData().clear();

        Parameters parameters = getAllParams();
        String growthRateStr = growthRate.getText();
        Double growthRate = Double.parseDouble(growthRateStr);
        growthRate /= 100.0;
        double monthlyGrowthRate = Math.pow(growthRate + 1, 1.0 / 12) - 1.0;

        int minPrice = 99999999;
        int maxPrice = 0;
        int maxYear = 0;

        int initialNormalizedYearMonth = parameters.getNormalizedYearMonth();
        int currentNormalizedYearMonth = initialNormalizedYearMonth;
        int initialPrice = getPredictedPrice(parameters);
        minPrice = Math.min(minPrice, initialPrice);
        maxPrice = Math.max(maxPrice, initialPrice);
        maxYear = Math.max(maxYear, initialNormalizedYearMonth);

        addData(parameters.getNormalizedYearMonth() - initialNormalizedYearMonth, initialPrice / 1000);
        System.out.println("HERE");
        int monthIncrement = 60;

        final int NUM_ITERATIONS = 4;

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            System.out.println("ITERATION " + i);
            currentNormalizedYearMonth += monthIncrement;
            parameters.setNormalizedYearMonth(currentNormalizedYearMonth);
            parameters.setYear(currentNormalizedYearMonth / 12 + 1950);
            parameters.setMonth(currentNormalizedYearMonth % 12);
            int currentPrice = getPredictedPrice(parameters);
            minPrice = Math.min(minPrice, currentPrice);
            maxPrice = Math.min(maxPrice, currentPrice);
            maxYear = Math.max(maxYear, currentNormalizedYearMonth / 12);
            if (currentPrice < initialPrice * Math.pow(1 + monthlyGrowthRate, 
                    currentNormalizedYearMonth - initialNormalizedYearMonth)) {
                monthIncrement = - Math.abs(monthIncrement / 2);
            } else {
                monthIncrement = Math.abs(monthIncrement / 2);
            }
            addData(parameters.getNormalizedYearMonth() - initialNormalizedYearMonth, currentPrice / 1000);
        }
        growthChart.getYAxis().setMaxHeight(maxPrice - minPrice + 50000);
        growthChart.getXAxis().setMaxWidth(maxYear - initialNormalizedYearMonth + 6);
        analysisArea.setText("You are recommended to sell the flat on " + new DecimalFormat("00").format(parameters.getMonth()) + "/" + parameters.getYear());
    }

    @FXML
    public void handleSubmitEvent(MouseEvent event) throws IOException {
        Parameters parameters = getAllParams();
        int predictedPrice = getPredictedPrice(parameters);
        futurePrice.setText(format.format(predictedPrice));
    }

    private int getPredictedPrice(Parameters parameters) throws IOException {
        JsonRequestResponseParser jsonRequestResponseParser = new JsonRequestResponseParser();
        String jsonString = jsonRequestResponseParser.parseInputParameters(parameters);

        File file = new File("jsonFile.json");
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(jsonString);

        bufferedWriter.close();

        JsonRequestResponseController jsonController = new JsonRequestResponseController();
        jsonController.readJson(file.getName());

        jsonController.readApiInfo("apiInfo.txt");

        String response = jsonController.rrsHttpPost();

        String substr1 = response.substring(0, response.lastIndexOf("\""));
        String substr2 = substr1.substring(substr1.lastIndexOf("\"") + 1);

        int price = (int) Double.parseDouble(substr2);
        return price;
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
        parameters.setNormalizedYearMonth((year - 1950) * 12 + month);

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
        futurePrice.setText(format.format(0.0000));
    }

    public void addData(Number x, Number y) {
        series.getData().add(new XYChart.Data<Number, Number>(x, y));
        if (series.getData().size() == 1) {
            initiateGraph();
        }
        System.out.println(series.getData().size());
        for (int i = 0; i < series.getData().size(); i++) {
            System.out.print(series.getData().get(i) + " ");
        }
        System.out.println();
    }
}