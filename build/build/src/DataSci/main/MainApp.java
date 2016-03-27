package DataSci.main;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {

    private static final String STAGE_TITLE = "DataSci";

    private static final String PATH_STYLESHEET = "view/style.css";

    private static final String PATH_FXML = "layout.fxml";

    // Opacities for Fade Animation
    private static final double OPACITY_FULL = 1.0;
    private static final double OPACITY_ZERO = 0.0;

    // Duration for Fade Animations
    private static final int FADE_DURATION = 1500;


    private DisplayController displayController;
    private Stage primaryStage;
    private static Scene scene;
    private GridPane parent = null;
    private GridPane main = null;

    /* ***********************************
     * METHODS
     * ***********************************/ 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {		
        primaryStage = stage;
        setUpPrimaryStage();		
    }

    private void loadStylesheet() {
        // scene.getStylesheets().add(getClass().getResource(PATH_STYLESHEET).toExternalForm());
    }

    private void setUpPrimaryStage() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_FXML));
    	main = (GridPane) loader.load();

    	Scene scene = new Scene(main);
    	// scene.getStylesheets().add(getClass().getResource(PATH_STYLESHEET).toExternalForm());

    	fadeIn(main);

    	displayController = loader.getController();
    	displayController.initialiseAll();
//    	displayController.initiateGraph();
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	primaryStage.setTitle("Prosper");
    	primaryStage.setResizable(false);
    }
    

//    private void fadeOut(Node element) {
//        FadeTransition fadeOut = new FadeTransition(Duration.millis(FADE_DURATION), element);
//        fadeOut.setFromValue(1.0);
//        fadeOut.setToValue(0.0);
//        fadeOut.play();
//    }
//
    private void fadeIn(Node element) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(FADE_DURATION), element);
        fadeIn.setFromValue(OPACITY_ZERO);
        fadeIn.setToValue(OPACITY_FULL);
        fadeIn.play();
    }
//    
//    // @@author A0121298E
//    private void initializeBrain() {
//        brain = Brain.getInstance();
//        brain.setDisplayController(this.displayController);
//        brain.initDisplay();
//    }
}