package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class MineSweeper {
    private int size;
    private int mines;
    private char[][] board;
    private char[][] moveBoard;
    int[][] neighbors = {{-1,0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
    int mineCount = 0;
    int incorrectFlag = 0;
    char mine = 'X';
    char open = '.';
    char free = '/';
    char cleared = '*';
    boolean gameOn;

    public MineSweeper(int size, int mines) {
        this.size = size;
        this.mines = mines;
        board = new char[size][size];
        moveBoard = new char[size][size];
        gameOn = true;
    }

    public void printBoard() {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    public void printBoard(int x) {
        int row = 1;
        System.out.println(" │123456789│ \n" +
                "—│—————————│ ");
        for (char[] chars : moveBoard) {
            System.out.print(row + "|");
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.print("| ");
            row++;
            System.out.println();
        }
        System.out.println("—│—————————│ ");
    }

    public void createBoard() {

        int minesTouching;
        int x;
        int y;

        Random random = new Random();

        for (char[] chars : board) {
            Arrays.fill(chars, open);
        }

        while (mineCount < mines) {
            x = random.nextInt(size);
            y = random.nextInt(size);
            if (board[x][y] != mine) {
                board[x][y] = mine;
                mineCount++;
            }
        }

        for (int i = 0; i < board.length; i++) {
            minesTouching = 0;
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != mine) {
                    minesTouching = countingMines(i, j);
                    board[i][j] = minesTouching > 0 ? Character.forDigit(minesTouching, 10)  : board[i][j];
                }
            }
        }

        for (int i = 0; i < moveBoard.length; i++) {
            for (int j = 0; j < moveBoard[i].length; j++) {
                moveBoard[i][j] = open;
            }
        }
    }

    public int countingMines(int i, int j) {
        int count = 0;
        int a;
        int b;

        for (int x = 0 ; x < neighbors.length ; x++) {
            a = neighbors[x][0];
            b = neighbors[x][1];
            if (checkIndex(i + a, j + b) && board[i + a][j + b] == mine) {
                count++;
            }
        }

        return count;
    }

    public boolean checkIndex(int i, int j) {
        return i >= 0 && i < board.length && j >=0 && j < board[i].length;
    }

    public void updateMove(int x, int y, String move) {

        switch (move.substring(0,1).toLowerCase()) {
            case "f":
                markFreeSpace(x, y);
                break;
            case "m":
                markMine(x, y);
                break;
        }

    }

    public void markFreeSpace(int x, int y) {
        if (board[x][y] == mine) {
            System.out.println("You stepped on a mine and failed!");
            gameOn = false;
        } else if (board[x][y] >= '1' && board[x][y] <= '8') {
            moveBoard[x][y] = board[x][y];
            clearNeighbors(x, y);
        } else if (board[x][y] == open) {
            moveBoard[x][y] = free;
            clearNeighbors(x, y);
        } else {
            System.out.println("Invalid move.");
        }
    }

    public void markMine(int x, int y) {
        if (moveBoard[x][y] == open) {
            moveBoard[x][y] = cleared;

            if (board[x][y] == open) {
                incorrectFlag++;
            } else if (board[x][y] == mine) {
                mineCount--;
            }

        } else if (moveBoard[x][y] == cleared) {
            moveBoard[x][y] = open;

            if (board[x][y] == mine) {
                mineCount++;
            } else {
                incorrectFlag--;
            }

        } else {
            System.out.println("Invalid move.");
        }

        if (incorrectFlag == 0 && mineCount == 0) {
            gameOn = false;
            System.out.println("Congratulations! You found all mines!");
        }
    }

    public void clearNeighbors(int i, int j) {
        int a;
        int b;

        for (int x = 0; x < neighbors.length; x++) {
            a = neighbors[x][0];
            b = neighbors[x][1];
            if (checkIndex(i + a, j + b)
                    &&
                       (board[i + a][j + b] == open || board[i + a][j + b] >= '1' && board[i + a][j + b] <= '8')
                    &&
                        (moveBoard[i + a][j + b] == open || moveBoard[i + a][j + b] == cleared)) {

                if (moveBoard[i + a][j + b] == cleared) {
                    incorrectFlag--;
                }

                moveBoard[i + a][j + b] = board[i + a][j + b] == open ? free : board[i + a][j + b];
                if (moveBoard[i + a][j + b] == free) {
                    clearNeighbors(i + a, j + b);
                }
            }
        }
    }
}
