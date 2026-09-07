import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/** The Main class launches the game using JavaFX. */
public class Ass5Game extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Load sound effects
        SoundManager.loadSounds();
        // Set up the main menu layout
        VBox menuLayout = new VBox(25);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #141E30, #243B55);");

        // Create and style the title label
        Label titleLabel = new Label("ARKANOID");
        titleLabel.setFont(new Font("Arial", 60));
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 5);");

        // Create and style the username input field
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your username...");
        nameInput.setMaxWidth(250);
        nameInput.setStyle("-fx-font-size: 16px; -fx-background-radius: 10; -fx-padding: 10;");

        // Create and style the level selector
        ComboBox<String> levelSelector = new ComboBox<>();
        levelSelector.getItems().addAll(
            "Level 1: The Beginning", "Level 2: The Pyramid", "Level 3: Space Invader",
            "Level 4: The Smiley", "Level 5: The Chessboard", "Level 6: Heartbeat",
            "Level 7: Twin Towers", "Level 8: ZigZag Alley", "Level 9: The Diamond",
            "Level 10: Final Fortress"
        );
        levelSelector.setValue("Level 1: The Beginning");
        levelSelector.setStyle("-fx-font-size: 16px; -fx-background-radius: 10;");

        // Create and style the start button
        Button startButton = new Button("START GAME");
        String btnNormalStyle = "-fx-font-size: 20px; -fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 30; -fx-padding: 10 40;";
        String btnHoverStyle  = "-fx-font-size: 20px; -fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 30; -fx-padding: 10 40;";
        startButton.setStyle(btnNormalStyle);
        startButton.setOnMouseEntered(e -> startButton.setStyle(btnHoverStyle));
        startButton.setOnMouseExited(e -> startButton.setStyle(btnNormalStyle));

        // Add all components to the layout
        startButton.setOnAction(e -> {
            String selectedLevelStr = levelSelector.getValue();
            int level = 1;
            
            // Determine the level based on the selected string
            level = Integer.parseInt(selectedLevelStr.split(":")[0].replace("Level ", ""));
            
            // Initialize and run the game with the selected level
            Game game = new Game(primaryStage, 800, 600, level);
            game.initialize(800, 600);
            game.run();
        });
        
        menuLayout.getChildren().addAll(titleLabel, nameInput, levelSelector, startButton);
        Scene menuScene = new Scene(menuLayout, 800, 600);
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }


    /** 
     * The main method creates and runs a new JavaFX application.
     * @param args command-line arguments (not used) 
     */
    public static void main(String[] args) {
        launch(args);
    }
}