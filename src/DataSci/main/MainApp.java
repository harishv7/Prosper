package DataSci.main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {

    private static final String STAGE_TITLE = "DataSci";

    private static final String PATH_STYLESHEET = "view/style.css";

    private static final String PATH_FXML = "layout.fxml";


    private DisplayController displayController;
    private Stage primaryStage;
    private static Scene scene;
    private AnchorPane parent = null;
    private AnchorPane main = null;

    /* ***********************************
     * METHODS
     * ***********************************/ 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {		
        primaryStage = stage;
        setUpPrimaryStage();		
    }

    private void loadStylesheet() {
        //scene.getStylesheets().add(getClass().getResource(PATH_STYLESHEET).toExternalForm());
    }

    private void setUpPrimaryStage() {
        try {
            parent = FXMLLoader.load(getClass().getResource(PATH_FXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(parent);
        loadStylesheet();

        primaryStage.setTitle(STAGE_TITLE);
//        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(PATH_FINI_ICON)));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /*
     * This method transits Fini from the Welcome Scene to the main display
     * of tasks. It loads the respective FXML and employs a fade transition.
     */
    

//    private void fadeOut(Node element) {
//        FadeTransition fadeOut = new FadeTransition(Duration.millis(FADE_DURATION), element);
//        fadeOut.setFromValue(1.0);
//        fadeOut.setToValue(0.0);
//        fadeOut.play();
//    }
//
//    private void fadeIn(Node element) {
//        FadeTransition fadeIn = new FadeTransition(Duration.millis(FADE_DURATION), element);
//        fadeIn.setFromValue(OPACITY_ZERO);
//        fadeIn.setToValue(OPACITY_FULL);
//        fadeIn.play();
//    }
//    
//    // @@author A0121298E
//    private void initializeBrain() {
//        brain = Brain.getInstance();
//        brain.setDisplayController(this.displayController);
//        brain.initDisplay();
//    }
}