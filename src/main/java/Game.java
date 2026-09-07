import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/** The Game class represents the game logic and control flow using JavaFX. */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    
    // JavaFX Components
    private final Stage stage;
    private final Canvas canvas;
    private final GraphicsContext gc;
    
    // Input Handling
    private final Set<String> activeKeys = new HashSet<>();
    private final List<Ball> activeBalls = new ArrayList<>();
    private final int currentLevel;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter score;
    private Image bgImage;
    private Color bgColor;
    private final Counter lives;
    private Paddle paddle;
    private LevelConfig config;

    /** Constructs a new {@code Game} instance with the specified stage, width, and height.
     * @param stage the JavaFX stage to display the game
     * @param width the width of the game window
     * @param height the height of the game window */
    public Game(Stage stage, int width, int height, int level) {
        this.stage = stage;
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        
        this.currentLevel = level;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
        this.lives = new Counter();
        this.lives.increase(3);
    }

    /** Adds a collidable object (such as blocks or walls) to the game environment.
     * @param c the collidable object to add */
    public void addCollidable(Collidable c) {
        if (c != null) {
            environment.addCollidable(c);
        }
    }

    /** Adds a sprite (such as the ball or paddle) to the game.
     * @param s the sprite to add */
    public void addSprite(Sprite s) {
        if (s != null) {
            sprites.addSprite(s);
        }
    }

    public LevelConfig getLevelConfig() {
        return this.config;
    }

    /** Initializes the game by setting up the borders, blocks, ball, and paddle.
     * @param width the width of the game window
     * @param height the height of the game window */
    public void initialize(int width, int height) {
        createBorders(width, height);
        
        this.config = loadLevelConfig(this.currentLevel);
        if (config == null) return;

        if (config.background != null) {
            if (config.background.endsWith(".jpg") || config.background.endsWith(".png")) {
                try {
                    this.bgImage = new Image(getClass().getResourceAsStream("/images/" + config.background));
                } catch (Exception e) {
                    System.out.println("Could not load background image: " + config.background);
                    this.bgColor = Color.BLACK; 
                }
            } else {
                this.bgColor = Color.web(config.background);
            }
        } else {
            this.bgColor = Color.LIGHTBLUE; 
        }
        
        // Reset the player (paddle and ball) for the new level
        resetPlayer(); 

        PowerUpSpawner spawner = new PowerUpSpawner(this, paddle, environment, remainingBalls, activeBalls);
        createBlocks(config, spawner);
        
        // Add the information panel to display score, lives, and level name
        InformationPanel info = new InformationPanel(this.score, this.lives, this.config.levelName);
        info.addToGame(this);
    }

    private void createBlocks(LevelConfig config, PowerUpSpawner spawner) {
        int blockWidth = 50;
        int blockHeight = 20;
        int startY = 100;
        
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);

        List<String> layout = config.blocksLayout;
        
        for (int row = 0; row < layout.size(); row++) {
            String line = layout.get(row);
            int rowWidth = line.length() * blockWidth;
            int startX = (800 - rowWidth) / 2; 
            
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                if (c == '-') {
                    continue; 
                }
                
                Color blockColor = getColorFromChar(c);
                if (blockColor == null) continue;
                
                // initialize the block's rectangle based on its position in the grid
                Rectangle rect = new Rectangle(
                        new Point(startX + col * blockWidth, startY + row * blockHeight),
                        blockWidth,
                        blockHeight
                );
                
                Block block = new Block(rect, blockColor);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(new ScoreTrackingListener(this.score));
                block.addHitListener(spawner);
                
                this.remainingBlocks.increase(1);
            }
        }
    }

    private void createBorders(int width, int height) {
        Rectangle left = new Rectangle(new Point(0, 0), 10, height);
        Rectangle right = new Rectangle(new Point(width - 10, 0), 10, height);
        Rectangle top = new Rectangle(new Point(5, 0), width - 20, 20);
        Rectangle bottom = new Rectangle(new Point(10, height - 10), width - 20, 10);
        
        Block leftBorder = new Block(left, Color.CYAN);
        Block rightBorder = new Block(right, Color.CYAN);
        Block topBorder = new Block(top, Color.CYAN);
        Block bottomBorder = new Block(bottom, Color.RED);
        
        leftBorder.setBorder();
        rightBorder.setBorder();
        topBorder.setBorder();
        bottomBorder.addHitListener(new BallRemover(this, this.remainingBalls));
        leftBorder.addToGame(this);
        rightBorder.addToGame(this);
        topBorder.addToGame(this);
        bottomBorder.addToGame(this);
    }
    
    /** Starts the JavaFX AnimationTimer loop. */
    public void run() {
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // Event Listeners for Keyboard Input
        scene.setOnKeyPressed(e -> activeKeys.add(e.getCode().toString()));
        scene.setOnKeyReleased(e -> activeKeys.remove(e.getCode().toString()));

        stage.setScene(scene);
        stage.setTitle("Arkanoid - JavaFX Edition");
        stage.show();

        // The new Game Loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Check end conditions
                if (remainingBlocks.getValue() <= 0) {
                    this.stop(); 
                    score.increase(100);
                    showEndScreen(true); 
                    return;
                }
                if (remainingBalls.getValue() <= 0) {
                    lives.decrease(1);
                    
                    if (lives.getValue() <= 0) {
                        this.stop();
                        showEndScreen(false);
                        return;
                    } else {
                        activeBalls.clear();
                        resetPlayer();
                    }
                }

                // 1. Update logic
                sprites.notifyAllTimePassed();

                // 2. Clear screen (Draw background)
                if (bgImage != null) {
                    gc.drawImage(bgImage, 0, 0, canvas.getWidth(), canvas.getHeight());
                } else {
                    gc.setFill(bgColor != null ? bgColor : Color.LIGHTBLUE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }

                // 3. Draw everything
                // (We will need to change drawAllOn to accept GraphicsContext gc)
                sprites.drawAllOn(gc); 
            }
        };
        
        timer.start();
    }

    void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);

    }
    void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    private void showEndScreen(boolean isWin) {
        VBox endLayout = new VBox(30);
        endLayout.setAlignment(Pos.CENTER);

        // Green for win, Red for lose
        String bgColor = isWin ? "#2ecc71" : "#e74c3c"; 
        String message = isWin ? "You Win!" : "Game Over";
        endLayout.setStyle("-fx-background-color: " + bgColor + ";");

        Label title = new Label(message);
        title.setFont(new Font("Arial", 60));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 5);");

        Label scoreLabel = new Label("Final Score: " + this.score.getValue());
        scoreLabel.setFont(new Font("Arial", 30));
        scoreLabel.setStyle("-fx-text-fill: white;");

        Button menuButton = new Button("Back to Main Menu");
        menuButton.setStyle("-fx-font-size: 18px; -fx-background-color: white; -fx-text-fill: " + bgColor + "; -fx-background-radius: 20; -fx-padding: 10 30; -fx-font-weight: bold;");

        menuButton.setOnAction(e -> {
            Ass5Game menu = new Ass5Game();
            try {
                menu.start(this.stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        endLayout.getChildren().addAll(title, scoreLabel, menuButton);
        Scene endScene = new Scene(endLayout, 800, 600);
        this.stage.setScene(endScene);
    }

    private LevelConfig loadLevelConfig(int levelNumber) {
        try {
            // Load the level configuration from a JSON file located in the resources folder
            String path = "/levels/level" + levelNumber + ".json";
            Reader reader = new InputStreamReader(getClass().getResourceAsStream(path));
            Gson gson = new Gson();
            // Parse the JSON into a LevelConfig object and return it
            return gson.fromJson(reader, LevelConfig.class);
        } catch (Exception e) {
            System.out.println("Error loading level: " + e.getMessage());
            return null;
        }
    }

    /**
     * Helper method to map characters from the JSON layout to JavaFX colors.
     */
    private Color getColorFromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'R' -> Color.RED;
            case 'B' -> Color.BLUE;
            case 'G' -> Color.GREEN;
            case 'Y' -> Color.YELLOW;
            case 'O' -> Color.ORANGE;
            case 'P' -> Color.PINK;
            case 'C' -> Color.CYAN;
            case 'W' -> Color.WHITE;
            default -> null;
        };
    }

    private void resetPlayer() {
        if (this.paddle == null) {
            Rectangle rec = new Rectangle(new Point((canvas.getWidth() / 2.0) - (this.config.paddleWidth / 2.0), canvas.getHeight() - 30), this.config.paddleWidth, 20);
            this.paddle = new Paddle(this.activeKeys, rec, "LEFT", "RIGHT");
            this.paddle.addToGame(this);
        } else {
            this.paddle.resetWidth(this.config.paddleWidth);
        }

        Ball ball = new Ball(canvas.getWidth() / 2.0, canvas.getHeight() / 2.0, 6, Color.BLACK, environment);
        ball.setVelocity(0, this.config.ballSpeed);
        ball.addToGame(this);
        
        this.remainingBalls.increase(1);
        this.activeBalls.add(ball);
    }
}
