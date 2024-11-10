package com.tictactoe;

public class Board {
    private char[][] board;

    public Board() {
        board = new char[3][3];
        // Inicializar el tablero vacío
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean checkWin(char symbol) {
        return getWinningLine(symbol) != null; // Verifica si hay una línea ganadora
    }

    // Método para obtener la línea ganadora
    public int[][] getWinningLine(char symbol) {
        // Comprobar filas
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return new int[][]{{i, 0}, {i, 1}, {i, 2}}; // Ganó en la fila
            }
        }
        // Comprobar columnas
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return new int[][]{{0, i}, {1, i}, {2, i}}; // Ganó en la columna
            }
        }
        // Comprobar diagonales
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}}; // Ganó en la diagonal
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}}; // Ganó en la diagonal
 }
        return null; // No hay ganador
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Hay al menos una casilla vacía
                }
            }
        }
        return true; // El tablero está lleno
    }
}