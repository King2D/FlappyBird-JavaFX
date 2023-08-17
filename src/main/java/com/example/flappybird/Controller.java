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
import javafx.scene.text.Text;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer gameLoop;
    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView birdImageView;

    @FXML
    private Text scoreText;

    @FXML
    private Text gameOverText;

    private Bird bird;
    private Pipes pipes;
    private static final double PIPE_SPAWN_INTERVAL = 150;
    private double pipeSpawnTimer = 0;
    private Score score;
    private boolean passedPipes = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();

        bird = new Bird(birdImageView);

        pipes = new Pipes(plane);

        score = new Score(scoreText);

        gameOverText.setVisible(false);

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

        double xDelta = 2;  //pipe movement speed
        pipes.movePipes(+xDelta);

        boolean collision = checkCollision();
        if (bird.isDead(plane.getHeight()) || collision) {
            showGameOver();
            return;
        }
        if (checkPassedPipe()) {
            passedPipes = true;
            score.incrementScore();
            updateScoreCount();
        }

        pipeSpawnTimer += 0.9;  //.9 for 144fps
        if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
            pipes.createPipePair(plane.getWidth());
            pipeSpawnTimer = 0;
            passedPipes = false;
        }

        pipes.removeOffscreenPipes();

        List<Rectangle> pipeList = pipes.getPipes();
        for (Rectangle pipe : pipeList) {
            if (!plane.getChildren().contains(pipe)) {
                plane.getChildren().add(pipe);
            }
        }
            scoreText.toFront();

    }

    private boolean checkCollision() {
        List<Rectangle> pipeList = pipes.getPipes();
        for (Rectangle pipe : pipeList) {
            if (Collision.collisionDetection(birdImageView, pipe)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPassedPipe() {
        double birdCenterX = birdImageView.getX() + birdImageView.getFitWidth() / 2;
        List<Rectangle> pipeList = pipes.getPipes();
        for (Rectangle pipe : pipeList) {
            double pipeX = pipe.getX();
            if (birdCenterX > pipeX && !passedPipes) {
                return true;
            }
        }
        return false;
    }

    private void updateScoreCount() {
        if (!bird.isDead(plane.getHeight())) {
            int currentScore = score.getScore();
            scoreText.setText("Score: " + currentScore);
        }
    }

    @FXML
        private void resetGame(KeyEvent event3) {
        if (event3.getCode() == KeyCode.ESCAPE) {
            resetBird();
            pipes.getPipes().clear();
            plane.getChildren().removeIf(node -> node instanceof Rectangle);
            passedPipes = false;
            scoreText.setVisible(true);
            birdImageView.setVisible(true);
            gameOverText.setVisible(false);
            resetScore();
        }
    }

    private void showGameOver() {
        gameOverText.setVisible(true);
        gameOverText.toFront();
        scoreText.setVisible(true);
        birdImageView.setVisible(false);
    }
    private void resetScore() {
        score.resetScore();

    }
    private void load() {
    }
    private void resetBird() {
        bird.reset();
    }
}
