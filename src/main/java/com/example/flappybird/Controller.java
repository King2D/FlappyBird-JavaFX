package com.example.flappybird;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer gameLoop;
    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView birdImageView;

    private Bird bird;
    private Pipes pipes;
    private static final double PIPE_SPAWN_INTERVAL = 150;
    private double pipeSpawnTimer = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();

        bird = new Bird(birdImageView);

        pipes = new Pipes(plane);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        gameLoop.start();
    }

    @FXML
    void pressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            bird.fly();
        }
    }

    @FXML
    void click(MouseEvent event2) {
        if (event2.isPrimaryButtonDown()) {
            bird.fly();
        }
    }
    private void update() {
        bird.update();

        double xDelta = 2;
        pipes.movePipes(+xDelta); // Adjust the pipe movement speed as needed

        if (bird.isDead(plane.getHeight()) || checkCollision()) { // Call the isDead method
            resetBird();
            pipes.getPipes().clear();
            plane.getChildren().removeIf(node -> node instanceof Rectangle);
        }


        pipeSpawnTimer += 0.7; // You might need to adjust this value based on your frame rate
        if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
            pipes.createPipePair(plane.getWidth());
            pipeSpawnTimer = 0;
        }

        pipes.removeOffscreenPipes();

        // Add the following code to add pipes to the game scene
        List<Rectangle> pipeList = pipes.getPipes();
        for (Rectangle pipe : pipeList) {
            if (!plane.getChildren().contains(pipe)) {
                plane.getChildren().add(pipe);
            }
        }
    }

    private boolean checkCollision(){
        List<Rectangle> pipeList = pipes.getPipes();
        for (Rectangle pipe : pipeList){
            if (Collision.collisionDetection(birdImageView, pipe)){
                return true;
            }
        }
        return false;
    }
    private void load(){

    }
    private void resetBird(){
        bird.reset();
    }
}
