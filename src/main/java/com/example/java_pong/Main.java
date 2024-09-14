package com.example.java_pong;


//Imports
import com.example.java_pong.controller.BallController;
import com.example.java_pong.controller.PaddleController;
import com.example.java_pong.controller.PlayerController;
import com.example.java_pong.model.BallModel;
import com.example.java_pong.model.PaddleModel;
import com.example.java_pong.model.PlayerModel;
import com.example.java_pong.view.BallView;
import com.example.java_pong.view.PaddleView;
import com.example.java_pong.view.PlayerView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.*;
import java.util.Objects;


/**
 * Main Class, Sets up all needed elements, such as Scene, Stage, UI elements etc
 */
public class Main extends Application {

    private PaddleController paddleController1;
    private PaddleController paddleController2;
    private BallController ballController;
    private BallView ballView;

    private BallModel ballModel;

    private PlayerView playerView1;

    private PlayerView playerView2;

    private PaddleView paddleView1;

    private PaddleView paddleView2;


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates a New Scene and adds all needed elements to it
     *
     * @param stage the stage
     * @throws Exception exceptions that may be thrown
     */
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = null; //create a new empty scene

        Group root = new Group(); //create a new Group
        Text welcome = Welcome(); //Create the Welcome Text Property
        try
        {
            scene = new Scene(root, 650, 500, Color.WHITESMOKE); //Set scene, add group and size
            stage.setMinWidth(650);
            stage.setMinHeight(500);
            stage.setScene(scene); //set the stage onto the scene
            welcome.xProperty().bind(scene.widthProperty().subtract(welcome.getBoundsInLocal().getWidth()).divide(2)); // Welcome Text on Top of Screen
        } catch (Exception e) {
            System.err.println("Error creating main scene: " + e.getMessage());
        }

        // Create Ball Model, view and Controller
        Image ballImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Black_Materia.png")));
        ballModel = new BallModel(50, 50);
        ballView = new BallView(ballImage, 50, 50, scene);
        ballController = new BallController(ballModel, ballView);
        ballView.layoutXProperty().bind(scene.widthProperty().subtract(ballView.getFitWidth()).divide(2));
        ballView.layoutYProperty().bind(scene.heightProperty().subtract(ballView.getFitHeight()).divide(2));


        // Create Paddle Models, views and controller
        PaddleModel paddleModel1 = new PaddleModel(0, 0, 0, 100, Color.RED);
        PaddleModel paddleModel2 = new PaddleModel(scene.getWidth() , 0, scene.getWidth(), 100, Color.BLUE);
        paddleView1 = new PaddleView(paddleModel1);
        paddleView2 = new PaddleView(paddleModel2);
        paddleController1 = new PaddleController(paddleModel1, paddleView1);
        paddleController2 = new PaddleController(paddleModel2, paddleView2);

        //Listens for if the screen size is changed, and keeps paddle 2 on the right side of the screen always
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) ->
        {
            paddleModel2.setStartX(newValue.doubleValue());
            paddleModel2.setEndX(newValue.doubleValue());
            paddleView2.updatePaddle();
        };
        scene.widthProperty().addListener(widthListener);


        // Create Player Models, views and controllers
        PlayerModel playerModel1 = new PlayerModel("Player 1");
        PlayerModel playerModel2 = new PlayerModel("Player 2");
        PlayerController playerController1 = new PlayerController(playerModel1, "Player 1 Name", Color.RED);
        PlayerController playerController2 = new PlayerController(playerModel2, "Player 2 Name", Color.BLUE);
        playerView1 = playerController1.getView();
        playerView2 = playerController2.getView();


        //Create a HBox to hold Player Items
        HBox playersHBox = new HBox();
        playersHBox.prefWidthProperty().bind(scene.widthProperty());
        playersHBox.setAlignment(Pos.CENTER);
        playersHBox.spacingProperty().bind(scene.widthProperty().multiply(0.3)); // 30% of the screen width as spacing
        // Set alignment for playerView1 to appear
        playerView1.setAlignment(Pos.CENTER);
        // Set alignment for playerView2 to appear
        playerView2.setAlignment(Pos.CENTER);
        // Add PlayerViews to the HBox
        playersHBox.getChildren().addAll(playerView1, playerView2);


        //Creating Various Buttons
        HBox ballSizeButtons = BallView.createBallSizeButtons(scene, ballController);
        ballSizeButtons.setAlignment(Pos.CENTER);
        HBox paddleSizeButtons = PaddleView.createPaddleSizeButtons(scene, paddleModel1, paddleModel2, paddleView1, paddleView2);
        HBox velocityButtons = BallView.createVelocityButtons(scene, ballController); // Assuming 'scene' is your JavaFX Scene object
        HBox velocityIncreaseButtons = BallView.createVelocityIncreaseButtons(scene, ballController);
        HBox winningScoreButtons = PlayerView.createWinningScoreButtons(scene);

        //Making a VBox to hold all buttons and adding the buttons to it
        VBox buttons = new VBox();
        buttons.getChildren().addAll(playersHBox, ballSizeButtons, paddleSizeButtons, velocityButtons, velocityIncreaseButtons, winningScoreButtons);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);


        // Start Game Buttons
        Button startGameButton = new Button("Start Game");
        startGameButton.layoutXProperty().bind(scene.widthProperty().subtract(startGameButton.widthProperty()).divide(2));
        startGameButton.layoutYProperty().bind(scene.heightProperty().divide(1.6));
        Scene finalScene = scene;


        //create the action for the button
       startGameButton.setOnAction(e ->
       {
           // Call createGameScene method from Game class when the button is pressed
           Game game = new Game();
           game.createGameScene(stage, paddleView1, paddleView2, paddleController1, paddleController2, playerView1, playerView2, playersHBox,
                   ballModel, ballController, ballView,finalScene.getWidth(), finalScene.getHeight());
        });

        // Exit Game Button, also saves the settings for Ball Size, velocity, racket size and player names
        Button exitGameButton = new Button("Save State & Exit Game");
        exitGameButton.layoutXProperty().bind(scene.widthProperty().subtract(exitGameButton.widthProperty()).divide(2));
        exitGameButton.layoutYProperty().bind(scene.heightProperty().divide(1.35));
        exitGameButton.setOnAction(e -> {
            saveGameSettings("game_state.ser");
            stage.close();
        });


        // Adds relevant objects to the root
        root.getChildren().addAll(welcome, paddleView1.getNode(), paddleView2.getNode(), startGameButton, exitGameButton, ballView, buttons);
        // Sets Stage title
        loadGameSettings("game_state.ser");
        stage.setTitle("Java Pong");
        stage.show();
    }

    /**
     * Welcome text.
     *
     * @return the text
     */
// Creates Welcome Text
    public Text Welcome() {
        Text welcome = new Text();
        welcome.setText("Welcome to Black Materia Pong");
        welcome.setFont(Font.font("Arial", 30));
        welcome.setFill(Color.BLACK);
        welcome.setY(72);
        return welcome;
    }


    /**
     * Save game settings.
     *
     * @param fileName the file name
     */
    public void saveGameSettings(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(paddleController1.getPaddleModel().getStartY());
            oos.writeObject(paddleController1.getPaddleModel().getEndY());

            oos.writeObject(ballModel.getWidth());
            oos.writeObject(ballModel.getHeight());
            oos.writeObject(ballModel.getVelocityX());
            oos.writeObject(ballModel.getVelocityY());

            oos.writeObject(playerView1.getName());
            oos.writeObject(playerView2.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load game settings.
     *
     * @param fileName the file name
     */
    public void loadGameSettings(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            double paddleYStart = (double) ois.readObject();
            paddleController1.getPaddleModel().setStartY(paddleYStart);
            paddleView1.setStartY(paddleYStart);
            paddleController2.getPaddleModel().setStartY(paddleYStart);
            paddleView2.setStartY(paddleYStart);

            double paddleYEnd = (double) ois.readObject();
            paddleController1.getPaddleModel().setEndY(paddleYEnd);
            paddleView1.setEndY(paddleYEnd);
            paddleController2.getPaddleModel().setEndY(paddleYEnd);
            paddleView2.setEndY(paddleYEnd);


            double ballWidth = (double) ois.readObject();
            double ballHeight = (double) ois.readObject();
            ballController.setBallSize(ballWidth, ballHeight);
            ballModel.setVelocityX((double) ois.readObject());
            ballModel.setVelocityY((double) ois.readObject());


            playerView1.setName((String) ois.readObject());
            playerView2.setName((String) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
