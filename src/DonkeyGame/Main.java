package DonkeyGame;

import java.awt.*;


public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var gameBoard = new GameBoard();
            gameBoard.add(new Container());
            gameBoard.pack();
            gameBoard.setResizable(false);
            gameBoard.setVisible(true);
        });
    }


}







