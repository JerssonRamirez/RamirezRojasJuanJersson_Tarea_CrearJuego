package com.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private static final long serialVersionUID = 1L; // Añadir serialVersionUID
    private Game game;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private int player1Wins = 0;
    private int player2Wins = 0;

    public GameView() {
        showStartMenu();
    }

    private void showStartMenu() {
        String[] options = {"Nuevo Juego", "Reglas", "Salir"};
        int choice = JOptionPane.showOptionDialog(this, "¡ Tic Tac Toe Game >:V !\n\nElige una opción:",
                "Menú Principal", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                String player1Name = JOptionPane.showInputDialog(this, "Ingresa el nombre del Jugador 1 (X):");
                String player2Name = JOptionPane.showInputDialog(this, "Ingresa el nombre del Jugador 2 (O):");
                startGame(player1Name, player2Name);
                break;
            case 1:
                showRules();
                showStartMenu(); // Regresar al menú después de mostrar las reglas
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.exit(0);
        }
    }

    private void showRules() {
        String rules = "Reglas del Juego:\n\n" +
                       "1. El juego se juega en un tablero de 3x3.\n" +
                       "2. Un jugador es 'X' y el otro es 'O'.\n" +
                       "3. El juego es por turnos para marcar en una celda .-.\n" +
                       "4. El primer jugador en alinear tres de sus símbolos en una fila, columna o diagonal gana :D.\n" +
                       "5. Si todas las celdas están llenas y no hay ganador, el juego termina en empate >:v.\n";
        JOptionPane.showMessageDialog(this, rules, "Reglas del Juego", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startGame(String player1Name, String player2Name) {
        game = new Game(player1Name, player2Name);
        buttons = new JButton[3][3];
        statusLabel = new JLabel(" Turno de: " + game.getCurrentPlayer().getName());
        scoreLabel = new JLabel(player1Name + " (X): 0  |  " + player2Name + " (O): 0");

        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
        initializeButtons(buttonPanel);
        
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, 2));
        scorePanel.add(scoreLabel);
        scorePanel.add(statusLabel);

        add(buttonPanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.SOUTH);
        
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeButtons(JPanel buttonPanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(" ");
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.play(row, col)) {
                            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer().getSymbol()));
                            checkForWinner();
                        }
                    }
                });
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void checkForWinner() {
        if (game.checkWin()) {
            highlightWinningLine(game.getBoard().getWinningLine(game.getCurrentPlayer().getSymbol()));
            statusLabel.setText("¡Ganador: " + game.getCurrentPlayer().getName() + "!");
            updateScore(); // Solo se actualiza el marcador si hay un ganador
            disableButtons();
            int response = JOptionPane.showConfirmDialog(this, "¿Quieres jugar de nuevo?", "Juego terminado", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                showStartMenu(); // Regresar al menú de inicio
            }
        } else if (game.isFull()) {
            statusLabel.setText("¡Es un empate!");
            int response = JOptionPane.showConfirmDialog(this, "¿Quieres jugar de nuevo?", "Juego terminado", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                showStartMenu(); // Regresar al menú de inicio
            }
        } else {
            game.switchPlayer();
            statusLabel.setText("Turno de: " + game.getCurrentPlayer().getName());
        }
    }

    private void updateScore() {
        if (game.getCurrentPlayer() == game.getPlayer1()) {
            player1Wins++;
        } else {
            player2Wins++;
        }
        scoreLabel.setText(game.getPlayer1().getName() + " (X): " + player1Wins + "  |  " + game.getPlayer2().getName() + " (O): " + player2Wins);
    }

    private void highlightWinningLine(int[][] winningLine) {
        for (int[] position : winningLine) {
            buttons[position[0]][position[1]].setBackground(Color.GREEN);
        }
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        game.reset(); // Reinicia el juego
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(null); // Restablece el color de fondo
            }
        }
        statusLabel.setText("Turno de: " + game.getCurrentPlayer().getName());
    }

    public static void main(String[] args) {
        new GameView();
    }
}