package com.casegeek.test.astar;

/**
Base Robot logic...
*/

public class Robot {

	/**
	Creates a robot at x/y : 1/1, facing NORTH
	*/
	public Robot () {
		direction = Robot.NORTH;
		steps = 0;
		up = true;
		x = 1;
		y = 1;
	}

	/**
	Moves the robot one step forward in the current direction the robot is facing
	*/
	public void move () {
		switch (get_dir()) {
			case Robot.NORTH:	++y;
								break;
			case Robot.EAST	:	++x;
								break;
			case Robot.SOUTH:	--y;
								break;
			case Robot.WEST	:	--x;
								break;
		}
	}

	/**
	Tests whether there's a cell of the desired color directly in front of the robot

	@param color the color to test against
	@param room the room to test in

	@return true if the color is directly ahead of the robot
	*/
	public boolean ahead_is_colour (final Room room, final String color) {

		String colorAhead = null;
		int xTest = 0;
		int yTest = 0;
		// colour coding assumed: white, 0; black, 1; blue, 2; yellow, 3; green, 4;
		String[] colors = {"white", "black", "blue", "yellow", "green"};

		if (direction == Robot.NORTH) {
			xTest = x;
			yTest = y + 1;
		}
		else if (direction == Robot.SOUTH) {
			xTest = x;
			yTest = y - 1;
		}
		else if (direction == Robot.EAST) {
			xTest = x + 1;
			yTest = y;
		}
		else {
			xTest = x - 1;
			yTest = y;
		}

		colorAhead = colors[room.cell_state(xTest,yTest)];
		return (color.compareTo(colorAhead) == 0);
	}

	/**
	Tests whether there's a cell of the desired color directly ahead of the robot
	<BR>
	The color parameter must be one of the following constants: Room.WHITE, Room.BLACK, Room.BLUE, Room.YELLOW, or Room.GREEN

	@param color the color to test against
	@param room the room to test in

	@return true if the color is directly ahead of the robot
	*/
	public boolean ahead_is_colour (final Room room, final int color) {

		int xTest = 0;
		int yTest = 0;

		if (direction == Robot.NORTH) {
			xTest = x;
			yTest = y + 1;
		}
		else if (direction == Robot.SOUTH) {
			xTest = x;
			yTest = y - 1;
		}
		else if (direction == Robot.EAST) {
			xTest = x + 1;
			yTest = y;
		}
		else {
			xTest = x - 1;
			yTest = y;
		}

		if (room.cell_state(xTest,yTest) == color) {
			return true;
		}
		return false;
	}

	/**
	Tests whether there's an obstacle directly ahead of the robot

	@param room the room to test in

	@return true if there is an obstacle ahead
	*/
	public boolean obstacle_ahead (final Room room) {

		int xTest = 0;
		int yTest = 0;

		if (direction == Robot.NORTH) {
			xTest = x;
			yTest = y + 1;
		}
		else if (direction == Robot.SOUTH) {
			xTest = x;
			yTest = y - 1;
		}
		else if (direction == Robot.EAST) {
			xTest = x + 1;
			yTest = y;
		}
		else {
			xTest = x - 1;
			yTest = y;
		}
		return (room.isObstacle (xTest, yTest));
	}

	/**
	Turns the robot 90 degrees to the right
	*/
	public void right () {
		++direction;
		if (direction == 4) {
			direction = Robot.NORTH;
		}
		if (direction == Robot.NORTH) {
			up = true;
		}
		else if (direction == Robot.SOUTH) {
			up = false;
		}
	}

	/**
	Turns the robot 90 degrees to the left
	*/
	public void left() {
		--direction;
		if (direction < 0) {
			direction = Robot.WEST;
		}
		if (direction == Robot.NORTH) {
			up = true;
		}
		else if (direction == Robot.SOUTH) {
			up = false;
		}
	}
	
	/**
	Sets the robot's facing to up (north)
	*/
	public void face_up () {
		this.up = true;
	}
	
	/**
	Sets the robots facing to down (south)
	*/
	public void face_down () {
		this.up = false;
	}
	
	/**
	@returns true if the robot is facing up
	*/
	public boolean facing_up () {
		return up;
	}

	/**
	@returns true if the robot is facing up
	*/
	public boolean facing_down () {
		return up;
	}

	public String toString () {
		StringBuffer stats = new StringBuffer(1024);
		stats.append("My facing is : ").append(get_dir())
			.append("\nMy x/y coordinates are : ").append(get_xpos()).append("/").append(get_ypos());
		return stats.toString();
	}

	/**
	@return an int describing the current x position of the robot
	*/
	protected final int get_xpos() {
		return x;
	}

	/**
	@return an int describing the current y position of the robot
	*/
	protected final int get_ypos() {
		return y;
	}

	/**
	@param x the new x position of the robot
	*/
	protected void setX (final int x) {
		this.x = x;
	}

	/**
	@param y the new y position of the robot
	*/
	protected void setY (final int y) {
		this.y = y;
	}

	/**
	Sets the current orientation of the robot
	<BR>
	Out of range direction parameters leave the robot's orientation as is

	@param direction the new orientation of the robot (NORTH, EAST, SOUTH, WEST)
	*/
	protected void setDirection (final int direction) {
		if (direction >= 0 || direction <= 4) {
			this.direction = direction;
		}
	}

	/**
	@return the current orienation of the robot
	*/
	protected final int get_dir() {
		return direction;
	}

	/** ease of use counter for the no. of steps the robot has taken */
	public int steps = 0;
	public static final int NORTH	= 0;
	public static final int EAST	= 1;
	public static final int SOUTH	= 2;
	public static final int WEST	= 3;
	
	/** ease of use indicator for the current facing of the robot */
	private boolean up = false;

	/** indicates current orientation of robot */
	private int direction = 0;

	/** describes the current x position of the robot */
	private int x = 0;

	/** describes the current x position of the robot */
	private int y = 0;
}