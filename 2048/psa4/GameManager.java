// Patrick Salmas
// cs8bwash
// January 28, 2016
// GameManager.java
// This program has two contructors. One creates a new game, and the
// other loads a saved game. GameManager.java also has a function named
// play that simulates the actions of the game. This also has two print
// funtions, printGrid and printControls, that are used to prompt the user 

//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;

public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting

    // GameManager Constructor
    // Generates a new game
    // Takes an int boardSize, a String for output, and a Random
    // as arguments
    // Returns a new game (not explicitly)
    public GameManager(int boardSize, String outputBoard, Random random) {
        System.out.println("Generating a New Board");

	if (boardSize < 2) // ensures that the boardSize is at least 2
	  this.board = new Board(2,random);
        else
	  this.board = new Board(boardSize, random);
	
	this.outputFileName = outputBoard;	

    }

    // GameManager Constructor
    // Load a saved game
    // Takes a file as input, a file for output, and a Random 
    // as arguments
    // Returns a loaded game (not explicitly)
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
	Board board1;
	board1 = new Board(inputBoard, random);
	this.outputFileName = outputBoard;
    }

    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    //  Takes no arguments
    //  Returns nothing
    public void play() throws IOException
    {     
       this.printControls();  // prints the controls
       String[] options = {"k", "j", "h", "l", "q"}; // array of user options
     
       while (!board.isGameOver()) // checks if game is over
       {  
	  this.printGrid(board.getGrid()); // prints the grid
       	  String move;
          
	  System.out.println("Enter an option please");
          Scanner input = new Scanner(System.in); // declares a variable for input
          move = input.next();

	  if (move == null)                     // makes sure input is valid
	     throw new IOException("Invalid input");      
          while (!Arrays.asList(options).contains(move)) // Asks the user to re enter
          {						 // a valid option if is invalid
       	      System.out.println("Option invalid. Please enter a valid option");
	      this.printGrid(board.getGrid());
              move = input.next();
          }
	
          if (move.equals("k")){                  
 	     board.moveUp(board.getGrid());
	     board.addRandomTile(); }               	 		
          else if (move.equals("j")) {
             board.moveDown(board.getGrid());	     
	     board.addRandomTile(); }  // valid, and adds a random tile
          else if (move.equals("h")) {            
	     board.moveLeft(board.getGrid()); 
	     board.addRandomTile(); }
          else if (move.equals("l")) {
	     board.moveRight(board.getGrid());
	     board.addRandomTile(); }
	  else if (move.equals("q"))    // checks if user wants to save and quit
	  {
	     try{
	        board.saveBoard(this.outputFileName);  // saves game
	     }
	     catch(IOException ex) {
 	        System.out.println("Caught");
	     }
	     System.out.println("quitting and saving");
	     return;    	
          }					 
       }
       printGrid(board.getGrid());
       System.out.println("Game over");	
    }
    
    // Prints grid
    // Takes an int[][] as an argument
    // Returns nothing
    private void printGrid(int[][] grid)
    {
       for (int x = 0; x < grid.length; x++)
       {
	  for (int y = 0; y < grid.length; y++)
	  {
	     System.out.print(grid[x][y] + " ");
 	  }
	  System.out.println();
       }
    }

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
/*
    public static void main(String[] args)
    {
     Random random = new Random();
     GameManager game = new GameManager(4, "bob.txt", random);
 
      try{
        game.play();
      }
      catch (IOException ex) {
        System.out.println("Caught");
      } 
    } 
*/
}

