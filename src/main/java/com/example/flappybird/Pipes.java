package com.example.flappybird;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pipes {

    private final List<Rectangle> pipes;
    private final Pane gamePane;
    private final Random random;

    private static final int PIPE_WIDTH = 80;
    private static final int PIPE_SPACING = 200;
    private static final int PIPE_HEIGHT_RANGE = 300;
    private static final Color PIPE_COLOR = Color.GREEN;
    private static final Color PIPE_OUTLINE_COLOR = Color.BLACK;

    public Pipes(Pane gamePane) {
        this.gamePane = gamePane;
        this.pipes = new ArrayList<>();
        this.random = new Random();
    }

    public void createPipePair(double x) {
        int windowHeight = (int) gamePane.getHeight();

        //height for lower and upper pipe
        int upperPipeHeight = random.nextInt(PIPE_HEIGHT_RANGE) + 50;
        int lowerPipeHeight = windowHeight - upperPipeHeight - PIPE_SPACING;

       //set pipe dimensions
        Rectangle upperPipe = new Rectangle(x, 0, PIPE_WIDTH, upperPipeHeight);
        Rectangle lowerPipe = new Rectangle(x, windowHeight - lowerPipeHeight, PIPE_WIDTH, lowerPipeHeight);

        pipeColor(upperPipe);
        pipeColor(lowerPipe);

        pipes.add(upperPipe);
        pipes.add(lowerPipe);
        gamePane.getChildren().addAll(upperPipe, lowerPipe);
    }

    private void pipeColor(Rectangle pipe) { //method for pipe colors
        pipe.setFill(PIPE_COLOR);
        pipe.setStroke(PIPE_OUTLINE_COLOR);
        pipe.setStrokeWidth(3); //outline
    }
    public void movePipes(double speed) { //move the pipe to the left towards the bid
        for (Rectangle pipe : pipes) {
            pipe.setX(pipe.getX() - speed);
        }
    }

    public void removeOffscreenPipes() { //remove all pipes that are off-screen to the left
        pipes.removeIf(pipe -> pipe.getX() + PIPE_WIDTH < 0);
        gamePane.getChildren().removeIf(node -> node instanceof Rectangle && ((Rectangle) node).getX() + PIPE_WIDTH < 0);
    }

    public List<Rectangle> getPipes() {
        return pipes;
    }
}
