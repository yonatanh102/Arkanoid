import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class PowerUp implements Sprite {
    private Rectangle rect;
    private final Velocity velocity;
    protected final Game game;
    protected final Paddle paddle;
    private final Color color;
    
    public PowerUp(Point center, Game game, Paddle paddle, Color color) {
        this.rect = new Rectangle(center, 15, 15);
        this.velocity = new Velocity(0, 3);
        this.game = game;
        this.paddle = paddle;
        this.color = color;
    }

    public abstract void applyEffect();

    @Override
    public void drawOn(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillOval(rect.getUpperLeft().getX(), rect.getUpperLeft().getY(), rect.getWidth(), rect.getHeight());
        gc.setStroke(Color.WHITE);
        gc.strokeOval(rect.getUpperLeft().getX(), rect.getUpperLeft().getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public void timePassed() {
        // 1. Update the position of the power-up based on its velocity
        Point newPos = this.velocity.applyToPoint(this.rect.getUpperLeft());
        this.rect = new Rectangle(newPos, rect.getWidth(), rect.getHeight());

        // 2. Check for collision with the paddle or if it goes out of bounds
        if (isIntersecting(this.rect, this.paddle.getCollisionRectangle())) {
            SoundManager.playPowerUp();
            applyEffect();
            removeFromGame();
        } 
        else if (this.rect.getUpperLeft().getY() > 600) {
            removeFromGame();
        }
    }

    private boolean isIntersecting(Rectangle r1, Rectangle r2) {
        return r1.getUpperLeft().getX() < r2.getUpperLeft().getX() + r2.getWidth() &&
               r1.getUpperLeft().getX() + r1.getWidth() > r2.getUpperLeft().getX() &&
               r1.getUpperLeft().getY() < r2.getUpperLeft().getY() + r2.getHeight() &&
               r1.getUpperLeft().getY() + r1.getHeight() > r2.getUpperLeft().getY();
    }

    public void addToGame() {
        this.game.addSprite(this);
    }

    private void removeFromGame() {
        this.game.removeSprite(this);
    }
}
