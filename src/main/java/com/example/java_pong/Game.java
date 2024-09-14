package com.example.java_pong;

import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.controller.BallController;
import com.example.java_pong.controller.PaddleController;
import com.example.java_pong.model.BallModel;
import com.example.java_pong.model.PlayerModel;
import com.example.java_pong.view.BallView;
import com.example.java_pong.view.PaddleView;
import com.example.java_pong.view.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Sets up the actual Game Scene where the game takes place
 */
public class Game {
    private boolean isPaused = false;
    private PaddleController paddleController1;
    private PaddleController paddleController2;
    BallController ballController;
    private AnimationTimer gameLoop;
    private Set<KeyCode> keysPressed = new HashSet<>();

    /**
     * Create game scene.
     *
     * @param stage             the stage
     * @param paddleView1       the paddle view 1
     * @param paddleView2       the paddle view 2
     * @param paddleController1 the paddle controller 1
     * @param paddleController2 the paddle controller 2
     * @param playerView1       the player view 1
     * @param playerView2       the player view 2
     * @param playersHBox       the players h box
     * @param ballModel         the ball model
     * @param ballController    the ball controller
     * @param ballView          the ball view
     * @param width             the width
     * @param height            the height
     */


    public void createGameScene(Stage stage, PaddleView paddleView1, PaddleView paddleView2,
                                PaddleController paddleController1, PaddleController paddleController2, PlayerView playerView1, PlayerView playerView2, HBox playersHBox,
                                BallModel ballModel,  BallController ballController, BallView ballView, double width, double height) {
        Group root = new Group(); //create a new group

        //initialise the passed in values
        this.paddleController1 = paddleController1;
        this.paddleController2 = paddleController2;
        this.ballController = ballController;
        //Hide the text input field for player name
        playerView1.hideInputField();
        playerView2.hideInputField();

        Scene gameScene = new Scene(root, width, height, Color.WHITESMOKE);//create a new scene to act as the game scene

        // Set the initial position of the ball to the center of the screen
        double centerX = width / 2;
        double centerY = height / 2;
        ballModel.setX(centerX);
        ballModel.setY(centerY);

        //Add the necessary elements to the stage

        stage.setScene(gameScene);
        stage.setResizable(false); //set the scene to be locked size when the game is playing
        stage.show();

        // Create a VBox for the save button
        VBox saveLoadButtonVBox = new VBox();

        // Create a button for saving the game
        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(event -> {
            // Call the saveGame method of the GameSaver singleton class
            GameSaver.getInstance().saveGame(ballModel, paddleController1.getPaddleModel(),
                    paddleController2.getPaddleModel(), playerView1.getModel(), playerView2.getModel());
        });


        Button loadButton = new Button("Load Game");
        loadButton.setOnAction(event -> {
            // gamestate singleton instance
            Object[] gameState = GameSaver.getInstance().loadGame();
            if (gameState != null)
            {
                root.getChildren().removeAll(paddleView1, paddleView2, ballView, playersHBox, saveLoadButtonVBox);

                // get loaded game state objects
                BallModel loadedBallModel = (BallModel) gameState[0];
                PaddleModel loadedPaddleModel1 = (PaddleModel) gameState[1];
                PaddleModel loadedPaddleModel2 = (PaddleModel) gameState[2];
                String loadedPlayer1Name = (String) gameState[3];
                int loadedPlayer1Score = (int) gameState[4];
                String loadedPlayer2Name = (String) gameState[5];
                int loadedPlayer2Score = (int) gameState[6];
                int winningScore = (int) gameState[7];

                // Update game state with the stuff from database
                ballController.setBallModel(loadedBallModel);
                playerView1.getModel().setName(loadedPlayer1Name);
                playerView1.getModel().setScore(loadedPlayer1Score);
                playerView2.getModel().setName(loadedPlayer2Name);
                playerView2.getModel().setScore(loadedPlayer2Score);
                PlayerModel.setWinningScore(winningScore);

                // Set the colors for paddleModel1 and paddleModel2
                loadedPaddleModel1.setColor(Color.RED);
                loadedPaddleModel2.setColor(Color.BLUE);

                // Update the paddle controllers with the loaded paddle models
                paddleController1.setPaddleModel(loadedPaddleModel1);
                paddleController2.setPaddleModel(loadedPaddleModel2);

                // Update the view to reflect the loaded game state
                paddleView1.setModel(loadedPaddleModel1);
                paddleView2.setModel(loadedPaddleModel2);
                paddleView1.updatePaddle();
                paddleView2.updatePaddle();
                ballView.setSize(loadedBallModel.getWidth(), loadedBallModel.getHeight());
                ballView.updateBallPosition(loadedBallModel.getX(), loadedBallModel.getY());

                root.getChildren().addAll(paddleView1, paddleView2, ballView, playersHBox, saveLoadButtonVBox);

                startGameLoop(paddleView1, paddleView2, gameScene, playerView1.getModel(), playerView2.getModel());

                System.out.println("success");
            } else
            {
                System.out.println("fail");
            }
        });


        Button dBSaveButton = new Button("Save to DB");
        dBSaveButton.setOnAction(event -> {
            // Call singleton saverloader instance
            DatabaseSaverLoader saverLoader = DatabaseSaverLoader.getInstance();
            saverLoader.saveGameToDatabase(playerView1.getModel().getName(),
                    playerView2.getModel().getName(), playerView1.getModel().getScore(),
                    playerView2.getModel().getScore(), PlayerModel.getWinningScore());
        });



        Button dBLoadButton = new Button("Load from DB");
        dBLoadButton.setOnAction(event -> {
            DatabaseSaverLoader saverLoader = DatabaseSaverLoader.getInstance();
            try
            {
                ballModel.setX(centerX);
                ballModel.setY(centerY);
                saverLoader.loadGameFromDatabaseAndReplacePlayers(playerView1.getModel(), playerView2.getModel());
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });




        // Add the button to the VBox
        saveLoadButtonVBox.getChildren().addAll(saveButton, loadButton, dBSaveButton, dBLoadButton);
        saveLoadButtonVBox.setVisible(false);
        saveButton.setFocusTraversable(false);
        loadButton.setFocusTraversable(false);
        dBSaveButton.setFocusTraversable(false);
        dBLoadButton.setFocusTraversable(false);

        root.getChildren().addAll(paddleView1, paddleView2, ballView, playersHBox, saveLoadButtonVBox);

        gameScene.setOnKeyPressed(event -> keysPressed.add(event.getCode())); //Listener for when racket movement keys are pressed
        gameScene.setOnKeyReleased(event -> keysPressed.remove(event.getCode())); //Listener for when racket movement keys are released
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                togglePause(saveLoadButtonVBox);
            } else if (event.getCode() == KeyCode.R) {
                restartGame(paddleView1, paddleView2, ballModel, playerView1.getModel(), playerView2.getModel(), gameScene);
            } else {
                keysPressed.add(event.getCode());
            }
        });

        //Start the Game Logic
        startGameLoop(paddleView1, paddleView2, gameScene, playerView1.getModel(), playerView2.getModel());
    }


    /**
     * handles the game logic on loop, such as ball movement and collision
     *
     * @param paddleView1 the paddle view 1
     * @param paddleView2 the paddle view 2
     * @param gameScene the scene for the game
     * @param playerModel1 the player model 1
     * @param playerModel2 the player model 2
     */


    private void startGameLoop(PaddleView paddleView1, PaddleView paddleView2, Scene gameScene, PlayerModel playerModel1, PlayerModel playerModel2) {
        // Stop the existing game loop if it's running
        stopGameLoop();
        // Create a new game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                if (playerModel1.getScore() >= PlayerModel.getWinningScore() || playerModel2.getScore() >= PlayerModel.getWinningScore()) {
                    // End the game
                    endGame(gameScene, playerModel1, playerModel2);
                    return; // Exit to stop the game
                }
                // Update the Game
                if (!isPaused) // Only update game if not pause
                {
                    handlePaddleMovement(gameScene.getHeight());
                    paddleView1.updatePaddle();
                    paddleView2.updatePaddle();
                    ballController.moveBall(paddleController1.getPaddleModel(), paddleController2.getPaddleModel(), playerModel1, playerModel2);
                }
            }
        };
        gameLoop.start();
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    /**
     * handles the user input and movement for paddles
     *
     * @param sceneHeight the height of the scene being used
     */
    private void handlePaddleMovement(double sceneHeight) {
        // check for paddle 1 movement
        if (keysPressed.contains(KeyCode.W)) {
            paddleController1.movePaddleUp(); // Move paddle 1 up
        } else if (keysPressed.contains(KeyCode.S)) {
            paddleController1.movePaddleDown(sceneHeight); // Move paddle 1 down
        }

        // check for paddle 2 movement
        if (keysPressed.contains(KeyCode.UP)) {
            paddleController2.movePaddleUp(); // Move paddle 2 up
        } else if (keysPressed.contains(KeyCode.DOWN)) {
            paddleController2.movePaddleDown(sceneHeight); // Move paddle 2 down
        }
    }

    /**
     * Toggles the game pause state
     */
    private void togglePause(VBox saveLoadButtonVBox) {
        isPaused = !isPaused;
        if (isPaused)
        {
            saveLoadButtonVBox.setVisible(true);
        }
        else
        {
            saveLoadButtonVBox.setVisible(false);
        }
    }

    /**
     * restarts the game
     *
     * @param paddleView1 the paddle view 1
     * @param paddleView2 the paddle view 2
     * @param ballModel the ball model
     * @param playerModel1 the player model 1
     * @param playerModel2 the player model 2
     * @param gameScene the scene for the game
     */
    private void restartGame(PaddleView paddleView1, PaddleView paddleView2, BallModel ballModel,
                             PlayerModel playerModel1, PlayerModel playerModel2, Scene gameScene) {
        // Reset ball position
        double centerX = gameScene.getWidth() / 2;
        double centerY = gameScene.getHeight() / 2;
        ballModel.setX(centerX);
        ballModel.setY(centerY);


        ballController.resetBallVelocity();

        // Reset paddle positions
        double paddleHeight = paddleView1.getHeight(); // Assuming both paddles have the same height
        double paddleGap = 50; // Adjust this value as needed
        paddleView1.setStartY((gameScene.getHeight() - paddleHeight) / 2);
        paddleView2.setStartY((gameScene.getHeight() - paddleHeight) / 2);

        // Reset player scores
        playerModel1.resetScore();
        playerModel2.resetScore();

        // Clear key presses
        keysPressed.clear();

        // Resume game if paused
        isPaused = false;
    }

    /**
     * Ends the game once the winning score is reached
     *
     * @param gameScene the scene for the game
     * @param playerModel1 the player model 1
     * @param playerModel2 the player model 2
     */
    private void endGame(Scene gameScene, PlayerModel playerModel1, PlayerModel playerModel2) {
        // Create text for winning player
        Text winMessage = new Text();
        winMessage.setTextAlignment(TextAlignment.CENTER);
        winMessage.setFont(Font.font("Arial", 24));

        // Determine which player won
        if (playerModel1.getScore() >= PlayerModel.getWinningScore()) {
            winMessage.setText("Player 1 wins!");
        } else {
            winMessage.setText("Player 2 wins!");
        }

        // Position the text in the center of the game scene
        winMessage.setLayoutX((gameScene.getWidth() - winMessage.getLayoutBounds().getWidth()) / 2);
        winMessage.setLayoutY((gameScene.getHeight() - winMessage.getLayoutBounds().getHeight()) / 2);

        // Add to the game scene
        ((Group)gameScene.getRoot()).getChildren().add(winMessage);
    }
}
