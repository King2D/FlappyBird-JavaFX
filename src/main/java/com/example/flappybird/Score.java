package com.example.flappybird;

import javafx.scene.text.Text;

public class Score {

    private int score = 0;
    private final Text scoreText;       // Text element for displaying the score

    public Score(Text scoreText) {
        this.scoreText = scoreText;
        updateScoreText();
    }

    public void incrementScore() {
        score++;
        updateScoreText();
    }

    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0; // Reset the score to zero
        updateScoreText();
    }

    private void updateScoreText() {
        scoreText.setText("Score: ");
    }
}
