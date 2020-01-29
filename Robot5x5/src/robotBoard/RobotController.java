package robotBoard;

import java.util.Scanner;  // Import the Scanner class

/*
 *  Author        : Steve Davis
 *  Last modified : 29/01/2020
 *  Version       : 1.0
 *  Program name  : Robot5x5
 *  Purpose       : Toy Robot Simulator 
 *  Description   : Simulates the movement of a virtual robot on a 5 x 5 board, using typed command inputs
 *                  from the user to PLACE X,Y,F and MOVE, LEFT, RIGHT and REPORT the robot's position.
*  
 *                  Movement and placement must be within the constraints of the board. With 0,0 being the 
 *                  further South West starting position.The position and direction of the robot can be
 *                  reported to the user.
 *  Comments      : Constructive comments welcomed.
 */

// define Robot Controller Class
public class RobotController {

	// Facing directions constants
	static final int FACE_WEST  = 1;		
	static final int FACE_NORTH = 2;
	static final int FACE_EAST  = 3;
	static final int FACE_SOUTH = 4;

	static final String FACE_WEST_SYMBOL  = "W";		
	static final String FACE_NORTH_SYMBOL = "N";
	static final String FACE_EAST_SYMBOL  = "E";
	static final String FACE_SOUTH_SYMBOL = "S";	
	
	static final String FACE_ALL          = "WNES";
	
	int    robotX;    // X coordinate board
	int    robotY;    // Y coordinate board
	String robotF;    // Symbolising: WNES  e.g. WEST,NORTH,EAST,SOUTH
	int    robotR;    // Robot rotation

	//  Used to get user input
	private static Scanner myObj;

	// default Robot initialisation method 
	RobotController() {

		setRobotX(0);
		setRobotY(0);
		setRobotF(FACE_NORTH_SYMBOL);
		setRobotR(FACE_NORTH);

	}

	// Convert Symbol to facing direction
	String SymbolToDirectionRobot(String direction) {
		switch (direction) {
		case FACE_WEST_SYMBOL:
			return "WEST";
		case FACE_NORTH_SYMBOL:
			return "NORTH";
		case FACE_EAST_SYMBOL:
			return "EAST";
		case FACE_SOUTH_SYMBOL:
			return "SOUTH";
		}
		return "ERROR";
	}

	// Convert a word direction to navigation 	
	String WordDirectionToNavRobot(String direction) {
		switch (direction) {
		case "WEST":
			setRobotR(FACE_WEST);
			return FACE_WEST_SYMBOL;
		case "NORTH":
			setRobotR(FACE_NORTH);
			return FACE_NORTH_SYMBOL;
		case "EAST":
			setRobotR(FACE_EAST);
			return FACE_EAST_SYMBOL;
		case "SOUTH":
			setRobotR(FACE_SOUTH);
			return FACE_SOUTH_SYMBOL ;
		}
		return "ERROR";
	}

	// Validate all Robot commands
	void validateRobot(String xCommand) {
		
		// No command, exit validation
		if (xCommand.trim().equals("")) return;
		
		// Split user command into array
		String[] tokens = xCommand.trim().toUpperCase().split("[, ]");

		// Get the command
		xCommand = tokens[0]; 	
		
		// Only allow supported valid commands
		if (getRobotX() < 0 || getRobotX() > 4 || getRobotY() < 0
				|| getRobotY() > 4
				|| "PLACE,MOVE,LEFT,RIGHT,REPORT".indexOf(xCommand) < 0) {
			return;
		}

		switch (xCommand) {

		case "PLACE":

			int tmpCoordX = 0;	   
	    	int tmpCoordY = 0;
	    	String tmpFacing = "";
	    	
	    	// Protect against invalid user input for X,Y
			try {
				if (tokens.length>1) {
					tmpCoordX = Integer.parseInt(tokens[1]);	   
					tmpCoordY = Integer.parseInt(tokens[2]);
					tmpFacing = tokens[3];
					
					// Exit if invalid facing direction
					if (!"NORTH,SOUTH,EAST,WEST".contains(tmpFacing)) return;
						
				}
			} 	catch (Exception e)
			{
				// Do not continue if error
				return;
			}			
			
			// Set new Robot position/facing direction
			setRobotX(tmpCoordX);
			setRobotY(tmpCoordY);
			setRobotF(WordDirectionToNavRobot(tmpFacing));
			
			// Notify user
			System.out.println("PLACE:" + getRobotX() + "," + getRobotY() + ","
					+ SymbolToDirectionRobot(getRobotF()));
						
			break;

		case "MOVE":
			
			// Calc whether Robot is able to move on the board safely 
			switch (getRobotF()) {
			case FACE_WEST_SYMBOL:
				setRobotX(getRobotX() - 1 >= 0 ? getRobotX() - 1 : getRobotX());
				break;
			case FACE_NORTH_SYMBOL:
				setRobotY(getRobotY() + 1 <= 4 ? getRobotY() + 1 : getRobotY());
				break;
			case FACE_EAST_SYMBOL:
				setRobotX(getRobotX() + 1 <= 4 ? getRobotX() + 1 : getRobotX());
				break;
			case FACE_SOUTH_SYMBOL:
				setRobotY(getRobotY() - 1 >= 0 ? getRobotY() - 1 : getRobotY());
			}

			break;

		case "LEFT":

			setRobotR(getRobotR() - 1);
			setRobotR((getRobotR() < 1 ? 4 : getRobotR()));	
			setRobotF(FACE_ALL.substring(getRobotR()-1, getRobotR()));
			break;

		case "RIGHT":
			
			setRobotR(getRobotR() + 1);
			setRobotR(getRobotR() > 4 ? 1 : getRobotR());			
		    setRobotF(FACE_ALL.substring(getRobotR()-1, getRobotR()));			
			break;

		case "REPORT":

			System.out.println("Output:" + getRobotX() + "," + getRobotY()
					+ "," + SymbolToDirectionRobot(getRobotF()));
			System.out.println("");
			
			break;
		}

	}

	// Main pogram entry point
	public static void main(String[] args) {		
		
		// User Commands
		String userCmd = "";
		
		// Instantiate Robot class
		RobotController myRobot = new RobotController();
				
	    myObj = new Scanner(System.in);
	    
	    System.out.println("Toy Robot Simulator 1.0  5x5 Board");  
	    System.out.println("----------------------------------");
	    System.out.println("Available COMMANDS----------------");
	    System.out.println("PLACE X,Y,F");
	    System.out.println("MOVE");
	    System.out.println("LEFT");
	    System.out.println("RIGHT");
	    System.out.println("REPORT");
	    System.out.println("----------------------------------");
	    System.out.println("Enter a Command, or enter EXIT to quit simulation.");	    
	    System.out.println("");
	    
	    while (!userCmd.trim().toUpperCase().equals("EXIT"))
	    {
	    	userCmd = myObj.nextLine();  		// Read user input	
	    	myRobot.validateRobot(userCmd);	    // Validate all user commands	
	    }
       
	    System.out.println("Robot Simulation Terminated.");

	}
	
	
	
	// setup Getters and Setters

	public int getRobotX() {
		return robotX;
	}

	public void setRobotX(int robotX) {
		this.robotX = robotX;
	}

	public int getRobotY() {
		return robotY;
	}

	public void setRobotY(int robotY) {
		this.robotY = robotY;
	}

	public String getRobotF() {
		return robotF;
	}

	public void setRobotF(String robotF) {
		this.robotF = robotF;
	}

	public int getRobotR() {
		return robotR;
	}

	public void setRobotR(int robotR) {
		this.robotR = robotR;
	}

	
}
