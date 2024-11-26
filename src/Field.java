// Field.java

import java.awt.Color;

public class Field {
    private Color color;
    private char player;

    public Field() {
        this.color = Color.WHITE;
        this.player = ' ';
    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
        if (player == 'X') {
            this.color = new Color(44, 62, 80);  // Dark blue/navy for X
        } else if (player == 'O') {
            this.color = new Color(72, 175, 148);  // Turquoise/green for O
        } else {
            this.color = Color.WHITE;
        }
    }

    public Color getColor() {
        return color;
    }

    public boolean isEmpty() {
        return player == ' ';
    }
}