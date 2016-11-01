// Patrick Salmas
// cs8bwash A11695058
// 2/12/2016
// Gui2048.java
// Creates a GridPane, a Scene, a Stage, and a keyHandler.
// Contains a keyHandler class. Contains functions for creating
// a visual board, ending the game, setting the tile color, 
// setting the tile font, and performing the moves on the Gui

/** Gui2048.java */
/** PSA8 Release */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

// Class Gui2048: Contains the overridden function start. Runs start
// and creates the Gui for 2048.
public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8

    private GridPane pane;   // GridPane holds entire game    

    /** Add your own Instance Variables here */
    private Rectangle[][] gameGrid;     // 2d array for tiles
    private Rectangle rect;             // used for each tile
    private Label title;                // label for title
    private Label label;		// label for each tile
    private Label score;	        // label for score
    private Direction direction;        // type Direction
    private Random rand = new Random(); // type Random
    private int rowCount = 0;           // counts num of rows on board
    private int columnCount = 0;        // counts num of columns on board
    private int height = 0;             // height of board
    private int width = 0;		// width of board
    private boolean done = false;       // keeps track if game is over

    
    // Method creates the board, scene, and presents the stage.
    // As well handles the user input and starts the game 
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Creates the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);  
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);
      
	primaryStage.setTitle("Gui248");           // sets the title "Gui2048"
         
        this.board = new Board(4,rand);            // creates a 4x4 board
	createBoard();                             // assigns tiles to board

	// Creates and sets the scene
        Scene scene = new Scene(pane,height-(TILE_WIDTH/2),height); 
	primaryStage.setScene(scene);            

        myKeyHandler handle = new myKeyHandler();  // creates a key handler
        scene.setOnKeyPressed(handle);             // handles keyboard input

        primaryStage.show();                       // shows the stage

    }
        // Inner private class created to handle key events
	private class myKeyHandler implements EventHandler<KeyEvent> {
	    // Overriden method that handles the key events	
            @Override
	    public void handle(KeyEvent e) {
		// Switch statement for to handle various input options
		// If a direction is picked, the apporiate move combo is
		// executed. If case is 's', then the game is saved

		if (!done) {    // Checks if game is done before input
		    switch (e.getCode()) {
		        case UP: moveCombo(Direction.UP); 
			    System.out.println("Moving Up");
		            break;
		    	case DOWN: moveCombo(Direction.DOWN); 
			    System.out.println("Moving Down");
			    break;
		    	case RIGHT: moveCombo(Direction.RIGHT); 
			    System.out.println("Moving Right");
			    break;
		    	case LEFT: moveCombo(Direction.LEFT); 
			    System.out.println("Moving Left");
			    break;
                    	case S:
			    try {
		                board.saveBoard(outputBoard); //saves game
			    }
			    catch(IOException ex) {
			    	System.out.println("Caught"); //catch exception
			    }  

			    System.out.println("Saving Board to 2048.board");    
			    done = true;    // sets done to true
			    gameOver('s');  // calls gameOver
			    break;		    
			
		    }
		}		
	    }  
        }


    
    /** Add your own Instance Methods Here */


    // Creates the current version of the board. This as well
    // adds all the tiles, the title, and the score. Takes no
    // arguments and returns nothing.
    public void createBoard() {
	// Creates the Label for the title, "2048", and 
	// sets the font style and size of the title
	title = new Label("2048");
 	title.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
	// Creates the Label for the score and sets the
	// font style and size for the score 
	score = new Label("Score: " + board.getScore());
	score.setFont(Font.font("Times New Roman",FontWeight.BOLD,25));
	
	pane.add(score,2,0);         // sets the score over the second
				     // column
	pane.setColumnSpan(score, 2);// spans the score over two columns

        pane.add(title, 0, 0);       // adds the title 
	pane.setColumnSpan(title, 2);// spans the title over two columns

	GridPane.setHalignment(title, HPos.CENTER); // Centers the title
      
        rowCount = 0;                // sets the row count to 0 
	columnCount = 0;             // sets the column count to 0
	int size = board.getGrid().length; // sets size to length of board
	
	// Nested for-loop for adding the tiles
        for (int x = 0; x < size; x++) 
	{
	    rowCount++;    // adds one to rows's count
	    for (int y = 0; y < size; y++)
	    {   // creates a rectangle
                rect = new Rectangle(TILE_WIDTH,TILE_WIDTH); 
		pane.add(rect, x, y+1);  // adds rect to pane
              
 		int value = board.getElement(x,y); // saves value at tile
		colorTile(value, rect);		   // sets tile's color

		// converts int value to String
	        String sValue = Integer.toString(value);	

                label = new Label(sValue); // creates tile label
		setFont(value, label);	   // sets tile's font

		if (value != 0)
		    pane.add(label,x,y+1); // if tile is not 0, add to pane                  		   
		// Centers tile label
	        GridPane.setHalignment(label, HPos.CENTER);	
	    }
	}
	rowCount++;                     // adds one to row's count
	height = TILE_WIDTH * rowCount; // sets height to tile width			
					// multiplied by the row count
    }

    // Creates an overlay covering game board
    // if called. Take a char argument and
    // returns nothing
    public void gameOver(char endOrSave) {
	 
        height = TILE_WIDTH * rowCount;   // sets size of overlay height
	width = TILE_WIDTH * rowCount;    // sets size of overlay width
	
     	// Creates a rectangle for overlay
        Rectangle overlay = new Rectangle(height-(TILE_WIDTH/2),height);

        overlay.setFill(COLOR_GAME_OVER); // sets color of overlay
	pane.add(overlay,0,0);            // adds overlay to pane
	int size = board.getGrid().length;// sets size to board length
	
	// spans overlay across the whole board
	pane.setColumnSpan(overlay, rowCount); 
	pane.setRowSpan(overlay, rowCount);

	Label end = new Label();  // creates a label for the overlay
        if (endOrSave == 'e')
	    end = new Label("Game Over!");  // overlay Game Over
	if (endOrSave == 's')
	    end = new Label("Game Saved!"); // overlay Game Saved

        // sets the font style and size of overlay label
	end.setFont(Font.font("Times New Roman",FontWeight.BOLD,50));

	pane.add(end,0,0);                 // adds overlay label
	
	// sets the span of the overlay label
	pane.setColumnSpan(end, rowCount);
	pane.setRowSpan(end, rowCount);
	GridPane.setHalignment(end, HPos.CENTER); // centers the label
    }

    // Sets the font of the tiles. Takes an int and Label
    // argument and returns nothing 	
    public void setFont(int value, Label label) {
	if (value <= 64)  
	    // Sets font size low if value is <= 64  
	    label.setFont(Font.font("Times New Roman", FontWeight.BOLD,
				     TEXT_SIZE_LOW));
	else if (value > 64 && value <= 512) 
	    // Sets font size medium of font size is between 64 & 512
	    label.setFont(Font.font("Times New Roman", FontWeight.BOLD,
				     TEXT_SIZE_MID));
	else
	    // Else sets font size high
	    label.setFont(Font.font("Times New Roman", FontWeight.BOLD,
				     TEXT_SIZE_HIGH));
	    
    }

    // Sets the color of the tiles. Takes an int and a 
    // Rectangle value and returns nothing
    public void colorTile(int value, Rectangle rect) {
	// The value of the tile corresponds to the appropriate color
	if (value == 2)
	    rect.setFill(COLOR_2);
	else if (value == 4)
	    rect.setFill(COLOR_4);
	else if (value == 8)
	    rect.setFill(COLOR_8);
	else if (value == 16)
	    rect.setFill(COLOR_16);
	else if (value == 32)
	    rect.setFill(COLOR_32);
	else if (value == 64)
	    rect.setFill(COLOR_64);
	else if (value == 128)
	    rect.setFill(COLOR_128);
	else if (value == 256)
	    rect.setFill(COLOR_256);
	else if (value == 512)
	    rect.setFill(COLOR_512);
	else if (value == 1024)
	    rect.setFill(COLOR_1024);
	else if (value == 2048)
	    rect.setFill(COLOR_2048);
	else if (value == 0)
	    rect.setFill(COLOR_EMPTY);
	else
	    rect.setFill(COLOR_2048);	   
	
    }
    
    // Checks if user can move in desired direction,
    // then moves if valid, and then adds a random tile 
    public void moveCombo(Direction direction) {
	if (board.isGameOver()) {  // Chceks if game is over
	   gameOver('e');          // calls game over
	   done = true;		   // sets done to true
	}
	// if done is false, try to move and adds random tile
        if (!done) { 
	    if (direction == Direction.UP) { 
	        if (board.canMove(Direction.LEFT)) {
	            board.moveLeft(board.getGrid());  // moves up
	            board.addRandomTile();            // adds random tile
	        }
	    }  
	    if (direction == Direction.DOWN) {
	        if (board.canMove(Direction.RIGHT)) {
	    	    board.moveRight(board.getGrid()); // moves down
	 	    board.addRandomTile();	      // adds random tile
	        }
	    } 
	    if (direction == Direction.RIGHT) {
	        if (board.canMove(Direction.DOWN)) {
	    	    board.moveDown(board.getGrid());  // moves right
		    board.addRandomTile();	      // adds random tile
	    	}	
	    }
	    if (direction == Direction.LEFT) {
	    	if (board.canMove(Direction.UP)) {
	    	    board.moveUp(board.getGrid());    // moves left
		    board.addRandomTile();	      // adds random tile 
	    	} 
	    }

	    pane.getChildren().remove(score);         // removes old score
	}
	
	if (!done)
	    createBoard();   // if done is false then creates current board
	
    }

   


    




    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
