package com.example.flappybird;

import javafx.scene.text.Text;

public class Score {

    private int score = -1;        // Initial score
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
        score = -1; // Reset the score to zero
        updateScoreText();
    }

    private void updateScoreText() {
        scoreText.setText("Score: "); // Update the UI text with the current score
    }
}
