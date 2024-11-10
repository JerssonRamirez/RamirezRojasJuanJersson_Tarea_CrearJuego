package com.tictactoe;

public class Game {
    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;

    public Game(String player1Name, String player2Name) {
        player1 = new Player(player1Name, 'X');
        player2 = new Player(player2Name, 'O');
        currentPlayer = player1;
        board = new Board();
    }

    public boolean play(int row, int col) {
        if (board.getBoard()[row][col] == ' ') { // Verificar si la casilla está vacía
            board.getBoard()[row][col] = currentPlayer.getSymbol(); // Colocar el símbolo del jugador
            return true; // Movimiento válido
        }
        return false; // Movimiento inválido
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambiar de jugador
    }

    public boolean checkWin() {
        return board.checkWin(currentPlayer.getSymbol());
    }

    public boolean isFull() {
        return board.isFull();
    }

    public void reset() {
        board = new Board(); // Reiniciar el tablero
        currentPlayer = player1; // Reiniciar al jugador 1
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}