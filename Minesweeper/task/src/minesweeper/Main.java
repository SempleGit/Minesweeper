package minesweeper;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many mines?");
        int mines = Integer.parseInt(sc.nextLine());
        int size = 9;


        MineSweeper game = new MineSweeper(size, mines);
        game.createBoard();

        while (game.gameOn) {
            game.printBoard(1);
            System.out.println("Enter coordinates and \"free\" or \"mine\" to mark free space or mine: ex: 1 1 free");
            String[] move = sc.nextLine().split(" ");
            if (game.checkIndex(Integer.parseInt(move[1]) - 1, Integer.parseInt(move[0]) - 1)) {
                game.updateMove(Integer.parseInt(move[1]) - 1, Integer.parseInt(move[0]) - 1, move[2]);
            } else {
                System.out.println("Invalid coordinates.");
            }
        }


    }
}
