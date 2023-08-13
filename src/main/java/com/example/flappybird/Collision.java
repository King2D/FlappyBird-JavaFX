package com.example.flappybird;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Collision {

    public static boolean collisionDetection(ImageView bird, Rectangle pipe) {
        Bounds birdBounds = bird.getBoundsInParent();
        Bounds pipeBounds = pipe.getBoundsInParent();

        return birdBounds.intersects(pipeBounds);
    }
}
