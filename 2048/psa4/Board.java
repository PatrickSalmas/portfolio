// Patrick Salmas
// cs8bwash
// January 28, 2016
// Board.java
// Has two different constructors. One for creating a board, and another 
// for loading a saved board. As allows the user to save their board. 
// This file provides a function for producing random tiles for a board.
// Also contains a fuction that rotates thee board clockwise or counter clockwise

//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;



    private final Random random;
    private int[][] grid;
    private int score;


    // Constructs a fresh board, at a specified length, and 
    // places a number of random tiles based on NUM_START_TILES 
    // Takes int boardSize and Random random as parameters
    // Returns (not explicitly) a contructed board with a size and random variable
    public Board(int boardSize, Random random) {
        this.random = random; 
        this.GRID_SIZE = boardSize; 
        this.grid = new int[boardSize][boardSize]; // creates grid using boardSize to 
						   // determine size
	for (int count = 0; count < this.NUM_START_TILES; count++) // adds two random tiles
	   this.addRandomTile(); 
         
 	}
    
    // Construct a board based off of an input file. Uses info from the
    // file to determine its size.
    // Takes a String file and a Random as parameters
    // Returns (not explicitly) a grid/board that is properly formatted with all expected
    // qualities in tact
    public Board(String inputBoard, Random random) throws IOException {
        this.random = random; 
	File file = new File(inputBoard);   
	Scanner input = new Scanner(file);   // Creates a variable to handle file input

	this.GRID_SIZE = input.nextInt();   // uses input from file to set GRID_SIZE
	this.grid = new int[GRID_SIZE][GRID_SIZE]; // set size of grid with GRID_SIZE

	this.score = input.nextInt();
	 
	if (inputBoard == null)  // makes sure input isn't null
	   throw new IOException("invalid file");

	for (int x = 0; x < GRID_SIZE; x++) // nested for-loop to fill grid 
	{
	   for (int y = 0; y < GRID_SIZE; y++)
	   {
	      if (input.hasNext() && input.hasNextLine())
     	         this.grid[x][y] = input.nextInt();
           }
	}
	input.close();
    }

    // Saves the current board to a file while keeping all its
    // data intact and in the same order
    // Takes a file name as a parameter that serves the purpose of
    // an output file
    // Returns nothing
    public void saveBoard(String outputBoard) throws IOException 
    {

	java.io.PrintWriter file = new java.io.PrintWriter(outputBoard);
	if (outputBoard == null)                  // Checks to make sure input isn't null
	  throw new IOException("invalid input");
        
	file.println(this.GRID_SIZE);  // saves the GRID_SIZE
	file.println(this.getScore()); // saves the score
	for (int x = 0; x < this.GRID_SIZE; x++) // saves the grid
	{
	   for (int y = 0; y < this.GRID_SIZE; y++)
	   {
	      file.print(this.grid[x][y] + " ");
	   }
	   file.println();
	}
	file.close();
    }

    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    // Takes no parameters
    // Returns nothing
    // SIDE NOTE: the function produces random tiles,
    // but doesn't pass the PSA Tester. Can't figure
    // what is wrong with this function
    public void addRandomTile() {
   
       int count = -1;
       for (int x = 0; x < this.GRID_SIZE; x++) // nested for-loop finds out how
       {					// many empty spaces are in the grid
	  for (int y = 0; y < this.GRID_SIZE; y++)
	  {
	     if (this.grid[x][y] == 0)
	        count++;
	  }
       }
       int value = 0;
       int location = 0;
       if (count >= 0) // checks if there is at least one empty space
       {
       	  location = this.random.nextInt(count+1); // creates a random location from 0 to
	  value = this.random.nextInt(99);	   // the number of empty spaces
       }
       else
	  return;     // exits function if there are no empty spaces

       int i = 0;
       for (int x = 0; x < this.GRID_SIZE; x++) // nested for-loop finds the random 
       {					// location and sets its value to
	  for (int y = 0; y < this.GRID_SIZE; y++) // 2 or 4
	  {
	     if (this.grid[x][y] == 0)
	     {		 
		if (i == location && value < this.TWO_PROBABILITY)
		   this.grid[x][y] = 2;
		else if (i == location && value >= this.TWO_PROBABILITY)
		   this.grid[x][y] = 4;
	
       	        i++;
	     }
		
	  }
       }
    }

    // Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    // If rotateClockwise == true, rotates the board clockwise , else rotates
    // the board counter-clockwise
    // Takes a boolean as an argument
    // Returns nothing
    public void rotate(boolean rotateClockwise) {
      int tempGrid[] = new int[GRID_SIZE * GRID_SIZE]; // Creates a normal array to store 
     						       // the values of this.grid						    
      int count = 0;				     
      if (rotateClockwise == true)   // Checks if rotateClockwise is true. If is true,
      {				     // rotate clockwise. Else rotate counter clockwise 
         
	 for (int y = 0; y < GRID_SIZE; y++)  // Places all the values of this.grid
         {  				      // in the order for a clockwise rotation	
	    for (int x = GRID_SIZE-1; x >= 0; x--) 
	    {
	       tempGrid[count] = this.grid[x][y];
	       count++;
  	    }
         }
      }
      else
      {
         for (int y = this.GRID_SIZE-1; y >= 0; y--) // Places all the values of this.grid
         {					     // int the order for a counter
	    for (int x = 0; x < this.GRID_SIZE; x++) // clockwise rotation
	    {
	       tempGrid[count] = this.grid[x][y];
	       count++;
            }
         }
      } 
      count = 0;
      for (int x = 0; x < GRID_SIZE; x++)       // Nested for loop to remap out 
      {						// this.grid
	 for (int y = 0; y < GRID_SIZE; y++)
	 {
	    this.grid[x][y] = tempGrid[count];
	    count++;	 
	 }
      } 	 
    }

    //Complete this method ONLY if you want to attempt at getting the extra credit
    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }
   
    // Moves all zeroes to one side of a 
    // one dimmensional array
    // Takes an int[] as an argument
    // Returns nothing
    public void slide(int[] array){
      for (int topCount = 0; topCount < GRID_SIZE; topCount++) {
	 for (int count = 0; count < GRID_SIZE-1; count++) {
	    if (array[count] == 0 && array[count+1] > 0){
	       int temp = array[count];  
	       array[count] = array[count+1]; // Swaps a zero with a non zero
	       array[count+1] = temp;
	    }      
	 }
      }
    }
    
    // Combines tiles that are abble to combine
    // Takes an int[] as an argument
    // Returns nothing
    public void combine(int[] array){
      for (int count = 0; count < GRID_SIZE-1; count++){
         if (array[count] == array[count+1]){
	 array[count] = array[count]*2;  // Combines a tile
	 addScore(array[count]);       // Adds to score
	 array[count+1] = 0;        // Sets old, combined tile to 0
	 }
      }
    }
    
    // Allows user to choose a direction to move in
    // Takes a Direction as a parameter
    // Returns nothing    
    public boolean move(Direction direction){

      if (direction == Direction.RIGHT){
         moveRight(this.grid);
	 return true;
      }
      else if (direction == Direction.LEFT){
	 moveLeft(this.grid);
	 return true;
      }
      else if (direction == Direction.UP){
	 moveUp(this.grid);
	 return true;
      }
      else if (direction == Direction.DOWN){
	 moveDown(this.grid);
	 return true;
      }	
      return false;
    }

    // moves board right
    // Takes an int[][] as an argument
    // Returns nothing
    public void moveRight(int[][] aGrid){
      int[] array = new int[GRID_SIZE];
      for (int x = 0; x < GRID_SIZE; x++){
	 int count = 0;
	 for (int y = GRID_SIZE-1; y >= 0; y--){
	    array[count] = aGrid[x][y];
	    count++;
 	 }
	 slide(array);    // Calls slide method
	 combine(array);  // Combines joinable tiles
	 slide(array);    // Calls slide again
	 count = 0;	   
	 for (int y = GRID_SIZE-1; y >= 0; y--){
	    aGrid[x][y] = array[count];
	    count++;
	 }
      }
    }

    // moves board left
    // Takes an int[][] as an argument
    // Returns nothing
    public void moveLeft(int[][] aGrid){
      int[] array = new int[GRID_SIZE];
      for (int x = 0; x < GRID_SIZE; x++){
	 int count = 0;
	 for (int y = 0; y < GRID_SIZE; y++){
	    array[count] = aGrid[x][y];
	    count++;
         }
	 slide(array);
	 combine(array);
	 slide(array);
	 count = 0;
	 for (int y = 0; y < GRID_SIZE; y++){
	    aGrid[x][y] = array[count];
	    count++;
	 }
      }
    }
    
    // moves board up
    // Takes an int[][] as an argument
    // Returns nothing
    public void moveUp(int[][] aGrid){
      int[] array = new int[GRID_SIZE];
      for (int y = 0; y < GRID_SIZE; y++){
         int count = 0;
	 for (int x = 0; x < GRID_SIZE; x++){
	    array[count] = aGrid[x][y];
	    count++;
	 }
	 slide(array);
	 combine(array);
	 slide(array);
	 count = 0;
	 for (int x = 0; x < GRID_SIZE; x++){
	    aGrid[x][y] = array[count];
	    count++;
	 } 
      }
    }
    
    // moves board down
    // Takes an int[][] as an argument
    // Returns nothing
    public void moveDown(int[][] aGrid){
      int[] array = new int[GRID_SIZE];
      for (int y = 0; y < GRID_SIZE; y++){
	 int count = 0;
	 for (int x = GRID_SIZE-1; x >= 0; x--){
	    array[count] = aGrid[x][y];
	    count++;
	 } 
	 slide(array);
	 combine(array);
	 slide(array);
	 count = 0;
	 for (int x = GRID_SIZE-1; x >= 0; x--){
	    aGrid[x][y] = array[count];
	    count++;
	 } 
      }
    }


    // Checks if chosen direction can make a move
    // Takes a Direction as a parameter
    // Returns a boolean
    public boolean canMove(Direction direction) {
      int temp[][] = new int[GRID_SIZE][GRID_SIZE];
      copyGrid(temp);
      int tempScore = this.score;

      if (direction == Direction.RIGHT) {
        moveRight(temp);
	if (Arrays.deepEquals(temp, grid)) {
 	  return false;
	}
      }
      else if (direction == Direction.LEFT) {
	moveLeft(temp);
	if (Arrays.deepEquals(temp, grid)) {
	  return false;
	}
      } 
      else if (direction == Direction.UP) {
	moveUp(temp);
	if (Arrays.deepEquals(temp, grid)) {
	   return false;
	}
      }
      else if (direction == Direction.DOWN) {
	moveDown(temp);
	if (Arrays.deepEquals(temp, grid)) {
	  return false;
	}
      }
      this.score = tempScore;
      
      return true;
    }
  
    // Checks if canMove in each direction
    // If cannot move, game is over
    // Takes no arguments and returns a boolean
    public boolean isGameOver() {
      
      boolean right = true, left = true, up = true, down = true;
      right = canMove(Direction.RIGHT);
      left = canMove(Direction.LEFT);
      up = canMove(Direction.UP);
      down = canMove(Direction.DOWN);	
      
      if (right == false && left == false && up == false && down == false)
	return true;
      else
	return false;
    } 
 
    // No need to change this for PSA3
    // Determine if we can move in a given direction
	   

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
      return grid;
    }

    public void copyGrid(int[][] temp) {
      //int[][] temp = new int[GRID_SIZE][GRID_SIZE];
      for (int x = 0; x < GRID_SIZE; x++) {
	for (int y = 0; y < GRID_SIZE; y++) {
          temp[x][y] = grid[x][y];
        }
      }  
    }
     
    public void addScore(int add) {
      this.score = add + this.score;
    }    

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
  
}


