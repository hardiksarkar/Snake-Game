import javax.swing.*;   // importing java swing whole package

public class SnakeGame extends JFrame{  // Main Window for the game

    Board board;
    // declaring Board reference
    SnakeGame(){        // snake game constructor
        board=new Board();      // creating board object
        add(board);     // adding board within the snake game window frame
        pack();         // used to size the window frame according to board size
        setResizable(false);    // user cannot resize window
        setLocation(400,100); // set frame location
        setVisible(true);       // frame visibility to user
    }

    public static void main(String[] args) {        // main function

        SnakeGame snakegame=new SnakeGame();        // creating object of SnakeGame class


    }
}
