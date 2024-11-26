// Board.java
public class Board {
    private final int rows;
    private final int columns;
    private final Field[][] fields;
    private final int[] winningCells;
    private char currentPlayer;
    private boolean gameEnded;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.fields = new Field[rows][columns];
        this.winningCells = new int[8]; // Store 4 pairs of coordinates
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fields[i][j] = new Field();
            }
        }
        currentPlayer = 'X';
        gameEnded = false;
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= columns || gameEnded) {
            return false;
        }

        int row = getLowestEmptyRow(column);

        if (row < 0) {
            return false;
        }

        fields[row][column].setPlayer(currentPlayer);

        if (checkWin(row, column)) {
            gameEnded = true;
            return true;
        }

        if (checkDraw()) {
            gameEnded = true;
            return true;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }

    private int getLowestEmptyRow(int column) {
        for (int row = rows - 1; row >= 0; row--) {
            if (fields[row][column].isEmpty()) {
                return row;
            }
        }
        return -1;
    }

    private boolean checkWin(int row, int column) {
        char player = fields[row][column].getPlayer();

        // Check horizontal
        for (int j = 0; j <= columns - 4; j++) {
            if (fields[row][j].getPlayer() == player &&
                    fields[row][j + 1].getPlayer() == player &&
                    fields[row][j + 2].getPlayer() == player &&
                    fields[row][j + 3].getPlayer() == player) {

                for (int k = 0; k < 4; k++) {
                    winningCells[k * 2] = row;
                    winningCells[k * 2 + 1] = j + k;
                }
                return true;
            }
        }

        // Check vertical
        for (int i = 0; i <= rows - 4; i++) {
            if (fields[i][column].getPlayer() == player &&
                    fields[i + 1][column].getPlayer() == player &&
                    fields[i + 2][column].getPlayer() == player &&
                    fields[i + 3][column].getPlayer() == player) {

                for (int k = 0; k < 4; k++) {
                    winningCells[k * 2] = i + k;
                    winningCells[k * 2 + 1] = column;
                }
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j <= columns - 4; j++) {
                if (fields[i][j].getPlayer() == player &&
                        fields[i + 1][j + 1].getPlayer() == player &&
                        fields[i + 2][j + 2].getPlayer() == player &&
                        fields[i + 3][j + 3].getPlayer() == player) {

                    for (int k = 0; k < 4; k++) {
                        winningCells[k * 2] = i + k;
                        winningCells[k * 2 + 1] = j + k;
                    }
                    return true;
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = columns - 1; j >= 3; j--) {
                if (fields[i][j].getPlayer() == player &&
                        fields[i + 1][j - 1].getPlayer() == player &&
                        fields[i + 2][j - 2].getPlayer() == player &&
                        fields[i + 3][j - 3].getPlayer() == player) {

                    for (int k = 0; k < 4; k++) {
                        winningCells[k * 2] = i + k;
                        winningCells[k * 2 + 1] = j - k;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkDraw() {
        for (int j = 0; j < columns; j++) {
            if (fields[0][j].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Field getField(int row, int col) {
        return fields[row][col];
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[] getWinningCells() {
        return winningCells;
    }
}