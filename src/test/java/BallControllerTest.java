import com.example.java_pong.controller.BallController;
import com.example.java_pong.model.BallModel;
import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.model.PlayerModel;
import com.example.java_pong.view.BallView;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BallControllerTest {

    @Test
    public void testPaddleCollision() {
        Platform.startup(() -> {
            // Create a dummy scene
            Scene scene = new Scene(new Group(), 800, 600);

            // Create instances of BallModel, BallView, BallController, PaddleModel, and PlayerModel
            BallModel ballModel = new BallModel(20, 20); // Ball size 20x20
            BallView ballView = new BallView(null, 0, 0, scene); // Pass null for parameters not used in collision test
            BallController ballController = new BallController(ballModel, ballView);
            PaddleModel paddleModel = new PaddleModel(0, 0, 100, 20, Color.RED); // Create a paddle model for testing
            PlayerModel playerModel = new PlayerModel("TestPlayer"); // Create a player model for testing

            // Set the ball position near the paddle
            ballModel.setX(90);
            ballModel.setY(10);

            // Set the ball velocity towards the paddle
            ballModel.setVelocityX(-1); // Negative X velocity to move left

            // Move the ball towards the paddle
            ballController.moveBall(paddleModel, paddleModel, playerModel, playerModel);

            // Check if the ball's horizontal velocity is reversed after colliding with the paddle
            assertTrue(ballModel.getVelocityX() > 0); // Ball's X velocity should be positive after collision
        });
    }
}
