package com.TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static Scanner sc = new Scanner(System.in);

    public void startGame() {
        Board board = new Board();

        System.out.println("***************************** Starting Game *****************************");

        //randomly assign 1st turn to HUMAN/COMPUTER and assign content as X
        Player currentPlayer = getFirstTurn() == 0 ? Player.HUMAN : Player.COMPUTER;
        Content currentContent = Content.X;

        while (true) {
            //if currentPlayer is COMPUTER, randomly fill a cell with currentContent
            //else let user choose empty cell to fill currentContent
            if(currentPlayer.equals(Player.COMPUTER)) {
                board.printBoard();
                board.fillRandomCell(currentPlayer, currentContent);
            } else {
                board.printBoard();
                getHumanTurn(board, currentContent);
            }

            //only currentPlayer can win/draw the game after current turn
            //check if all cells are filled then set GameStatus to OVER else determine Game result
            //if game is OVER display and end game, else continue game after inverting currentPlayer and currentContent
            GameStatus gameStatus = board.getGameStatus(currentPlayer, currentContent);
            if(!gameStatus.equals(GameStatus.ACTIVE)) {
                System.out.println("Game Over - "+gameStatus);
                break;
            } else {
                currentPlayer = currentPlayer == Player.HUMAN ? Player.COMPUTER : Player.HUMAN;
                currentContent = currentContent == Content.X ? Content.O : Content.X;
            }
        }
    }

    int getFirstTurn() {
        Random random = new Random();
        return random.nextInt(2);
    }

    public void getHumanTurn(Board board, Content content) {
        System.out.println("Enter row and column to mark "+content+" : ");
        int row = sc.nextInt();
        int col = sc.nextInt();
        if(!board.fillCell(Player.HUMAN, content, row, col)) {
            System.out.println("Cell with : row = "+row+" | column = "+col+" is occupied.");
            getHumanTurn(board, content);
        }
    }
}
