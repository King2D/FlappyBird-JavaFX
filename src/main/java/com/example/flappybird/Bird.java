package com.example.flappybird;

import javafx.scene.image.ImageView;

public class Bird {

    private final ImageView imageView;
    private double time = 0;

    public Bird(ImageView imageView) {
        this.imageView = imageView;
    }

    public void fly() {
        int jumpHeight = 70;
        if (imageView.getLayoutY() + imageView.getY() <= jumpHeight) {
            moveBirdY(-(imageView.getLayoutY() + imageView.getY()));
            time = 0;
            return;
        }
        moveBirdY(-jumpHeight);
        time = 0;
    }

    public void update() {
        time++;
        double yDelta = 0.02; //gravity or speed by which the bird falls (dependent to the framerate)
        moveBirdY(yDelta * time);
    }

    private void moveBirdY(double positionChange) {
        imageView.setY(imageView.getY() + positionChange);
    }

    public void reset() { //reset bird's position)
        imageView.setY(0);
        imageView.setX(0);
        time = 0;
    }
    public boolean isDead(double planeHeight) { //check if the bird is on the ground, then the bird is dead
        double birdY = imageView.getLayoutY() + imageView.getY();
        return birdY >= planeHeight;
    }
}
