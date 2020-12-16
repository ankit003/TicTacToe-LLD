package com.TicTacToe;

import java.util.Random;

public class Board {
    Cell cells[][] = null;
    GameStatus gameStatus;
    int filledCells;

    public Board() {
        this.cells = new Cell[3][3];
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                this.cells[i][j] = new Cell(Player.NONE, Content.N);
            }
        }
        this.filledCells = 0;
    }

    //fill cell with X/O and return true else if cell is already occupied then return false
    public boolean fillCell(Player player, Content content, int row, int col) {
        if(this.cells[row][col].content == Content.N) {
            this.cells[row][col] = new Cell(player, content);
            this.filledCells++;
            return true;
        }
        return false;
    }

    //print board
    public void printBoard() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                if(this.cells[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(this.cells[i][j].content);
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }


    public void fillRandomCell(Player currentPlayer, Content currentContent) {
        Random random = new Random();
        //TODO: Optimise Random function as current flow might get stack overflow error
        int row = random.nextInt(3);
        int col = random.nextInt(3);
        while(!fillCell(currentPlayer, currentContent, row, col)) {
            fillRandomCell(currentPlayer, currentContent);
        }
    }

    public GameStatus getGameStatus(Player player, Content content) {
        this.gameStatus = checkRows(player, content);
        if(this.gameStatus == null)
            this.gameStatus = checkColumns(player, content);
        if(this.gameStatus == null)
            this.gameStatus = checkDiagonals(player, content);
        if(this.gameStatus != null && this.filledCells == 9)
            this.gameStatus = GameStatus.DRAW;
        else if(this.gameStatus == null)
            this.gameStatus = GameStatus.ACTIVE;
        return this.gameStatus;
    }

    private GameStatus checkRows(Player player, Content content) {
        //check rows for winner
        boolean res = this.cells[0][0].content.equals(content);
        for(int i=0;i<3;i++) {
            if(this.cells[i][0].content.equals(content) &&
                    this.cells[i][1].content.equals(content) &&
                    this.cells[i][2].content.equals(content)) {
                return player == Player.HUMAN ? GameStatus.HUMAN_WINS : GameStatus.COMPUTER_WINS;
            }
        }
        return null;
    }

    private GameStatus checkColumns(Player player, Content content) {
        //check columns for winner
        for(int i=0;i<3;i++) {
            if(this.cells[0][i].content.equals(content) &&
                    this.cells[1][i].content.equals(content) &&
                    this.cells[2][i].content.equals(content)) {
                return player == Player.HUMAN ? GameStatus.HUMAN_WINS : GameStatus.COMPUTER_WINS;
            }
        }
        return null;
    }

    private GameStatus checkDiagonals(Player player, Content content) {
        //check diagonals for winner
        if((this.cells[0][0].content.equals(content) &&
                this.cells[1][1].content.equals(content) &&
                this.cells[2][2].content.equals(content)) ||
                (this.cells[0][2].content.equals(content) &&
                        this.cells[1][1].content.equals(content) &&
                        this.cells[2][0].content.equals(content))) {
            return player == Player.HUMAN ? GameStatus.HUMAN_WINS : GameStatus.COMPUTER_WINS;
        }
        return null;
    }
}