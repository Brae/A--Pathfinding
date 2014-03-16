package com.casegeek.test.astar;

/**
Base Room logic...
*/

public class Room {

	/**
	Creates a 20x20 empty room
	*/
	public Room() {
		this(0);
	}

	/**
	Creates a 20x20 room of variable content

	@param identifier the exact room to be loaded
	*/
	public Room (final int identifier) {
		grid = new int[size][size];
		init(identifier);
	}

	/**
	Room location (x/y) is made blank ( => WHITE )

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	*/
	public void setBlank (final int x, final int y) {
		grid[x][y] = WHITE;
	}

	/**
	Room location (x/y) is set as an unbreakable wall ( => BLACK )

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	*/
	public void setWall (final int x, final int y) {
		grid[x][y] = BLACK;
	}

	/**
	Room location (x/y) is set as an obstacle ( => BLUE )

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	*/
	public void setObstacle (final int x, final int y) {
		grid[x][y] = BLUE;
	}

	/**
	Room location (x/y) is set as an path ( => YELLOW )

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	*/
	public void setPath (final int x, final int y) {
		grid[x][y] = YELLOW;
	}

	/**
	Room location (x/y) is set as a target ( => GREEN )

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	*/
	public void setTarget (final int x, final int y) {
		grid[x][y] = GREEN;
	}

	/**
	Room location (x/y) is set to the color specified

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	@param color the color to set the specified location to
	*/
	public void setSquare (final int x, final int y, final String color) {
		if (color.compareTo("white")		== 0)	{grid[x][y] = WHITE;}
		else if (color.compareTo("black")	== 0)	{grid[x][y] = BLACK;}
		else if (color.compareTo("blue")	== 0)	{grid[x][y] = BLUE;}
		else if (color.compareTo("yellow")	== 0)	{grid[x][y] = YELLOW;}
		else if (color.compareTo("green")	== 0)	{grid[x][y] = GREEN;}
	}

	/**
	Room location (x/y) is set to the color specified

	@param x the x coordinate of the grid reference
	@param y the y coordinate of the grid reference
	@param color the color to set the specified location to
	*/
	public void setSquare (final int x, final int y, final int color) {
		switch (color) {
			case 0:		grid[x][y] = WHITE;
						break;
			case 1:		grid[x][y] = BLACK;
						break;
			case 2:		grid[x][y] = BLUE;
						break;
			case 3:		grid[x][y] = YELLOW;
						break;
			case 4:		grid[x][y] = GREEN;
						break;
			default:	break;
		}
	}
	
	public String toString () {
		StringBuffer state = new StringBuffer(1024);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				state.append(grid[x][y]).append(' ');
			}
			state.append("\n");
		}
		return state.toString();
	}

	/**
	@return the size of the room
	*/
	protected final int room_size() {
		return size;
	}

	/**
	@return the identity matrix that defines the room
	*/
	protected final int[][] getGrid() {
		return grid;
	}

	/**
	@param grid the new identity matrix
	*/
	protected void setGrid (final int[][] grid) {
		this.grid = grid;
		size = grid.length;
	}

	/**
	@return the 'state' (color) of the cell in question
	*/
	protected int cell_state (final int x, final int y) {
		if (x < 0 || x > room_size() || y < 0 || y > room_size()) {
			return -1;
		}
		return grid[x][y];
	}

	/**
	@return true if the cell directly ahead is an obstacle (BLACK or BLUE square)
	*/
	protected boolean isObstacle (final int x, final int y) {
		if (grid[x][y] == BLUE || grid[x][y] == BLACK) {
			return true;
		}
		return false;
	}

	/**
	Attempt to load the content of the room from the local filesystem. If the load fails,
	create a standard empty room bordered by black walls

	@param identifier the room content to be loaded
	*/
	private void init (final int identifier) {

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (x == 0 || (x == size - 1) || y == 0 || (y == size - 1))
					grid[x][y] = BLACK;
				else
					grid[x][y] = WHITE;
			}
		}

        switch (identifier) {
        case 1:
            grid[3][3] = 2;
            grid[4][3] = 2;
            grid[5][3] = 2;
            grid[6][3] = 2;
            grid[3][4] = 2;
            grid[5][4] = 4;
            break;

        case 2:
            grid[1][7] = 2;
            grid[2][7] = 2;
            grid[3][7] = 2;
            grid[4][7] = 2;
            grid[4][8] = 2;
            grid[4][9] = 2;
            grid[4][10] = 2;
            grid[9][5] = 2;
            grid[9][6] = 2;
            grid[9][7] = 2;
            grid[9][8] = 2;
            grid[10][8] = 2;
            grid[11][8] = 2;
            grid[12][8] = 2;
            grid[13][8] = 2;
            grid[14][8] = 2;
            grid[10][13] = 2;
            grid[10][12] = 2;
            grid[11][12] = 2;
            grid[12][12] = 2;
            grid[11][13] = 4;
            break;

        case 3:
            grid[1][2] = 2;
            break;

        case 4:
            grid[1][2] = 3;
            grid[1][3] = 3;
            grid[1][4] = 3;
            grid[1][5] = 3;
            grid[1][6] = 3;
            grid[1][7] = 3;
            grid[1][8] = 3;
            grid[2][8] = 3;
            grid[3][8] = 3;
            grid[4][8] = 3;
            grid[5][8] = 3;
            grid[6][8] = 3;
            grid[7][8] = 3;
            grid[8][8] = 3;
            grid[8][7] = 3;
            grid[8][6] = 3;
            grid[8][5] = 3;
            grid[8][4] = 3;
            grid[8][3] = 3;
            grid[8][2] = 3;
            grid[7][2] = 3;
            grid[6][2] = 3;
            grid[5][2] = 3;
            grid[4][2] = 3;
            grid[3][2] = 3;
            grid[3][3] = 3;
            grid[3][4] = 3;
            grid[3][5] = 3;
            grid[4][5] = 3;
            grid[5][5] = 4;
            break;

        case 5:
            grid[1][2] = 3;
            grid[1][3] = 3;
            grid[1][4] = 3;
            grid[2][4] = 3;
            grid[3][4] = 3;
            grid[4][4] = 3;
            grid[5][4] = 3;
            grid[6][4] = 3;
            grid[7][4] = 3;
            grid[8][4] = 3;
            grid[8][5] = 3;
            grid[8][6] = 3;
            grid[8][7] = 3;
            grid[7][7] = 3;
            grid[6][7] = 3;
            grid[6][8] = 3;
            grid[6][9] = 3;
            grid[6][10] = 3;
            grid[6][11] = 3;
            grid[5][11] = 3;
            grid[4][11] = 3;
            grid[4][10] = 3;
            grid[4][9] = 3;
            grid[4][8] = 3;
            grid[3][8] = 3;
            grid[2][8] = 3;
            grid[1][8] = 3;
            grid[1][9] = 3;
            grid[1][10] = 3;
            grid[1][11] = 3;
            grid[1][12] = 3;
            grid[1][13] = 3;
            grid[1][14] = 3;
            grid[1][15] = 3;
            grid[2][15] = 3;
            grid[3][15] = 3;
            grid[4][15] = 3;
            grid[5][15] = 3;
            grid[5][16] = 3;
            grid[5][17] = 3;
            grid[5][18] = 4;
            break;

        case 6:
            grid[1][2] = 3;
            grid[1][3] = 3;
            grid[1][4] = 3;
            grid[2][4] = 3;
            grid[3][4] = 3;
            grid[4][4] = 3;
            grid[5][4] = 3;
            grid[6][4] = 3;
            grid[7][4] = 3;
            grid[8][4] = 3;
            grid[8][5] = 3;
            grid[8][6] = 3;
            grid[8][7] = 3;
            grid[7][7] = 3;
            grid[6][7] = 3;
            grid[6][8] = 3;
            grid[6][9] = 3;
            grid[6][10] = 3;
            grid[6][11] = 3;
            grid[5][11] = 3;
            grid[4][11] = 3;
            grid[4][10] = 3;
            grid[4][9] = 3;
            grid[4][8] = 3;
            grid[3][8] = 3;
            grid[2][8] = 3;
            grid[1][8] = 3;
            grid[1][9] = 3;
            grid[1][10] = 3;
            grid[1][11] = 3;
            grid[1][12] = 3;
            grid[1][13] = 3;
            grid[1][14] = 3;
            grid[1][15] = 3;
            grid[2][15] = 3;
            grid[3][15] = 3;
            grid[4][15] = 3;
            grid[5][15] = 3;
            grid[5][16] = 3;
            grid[5][17] = 3;
            grid[4][2] = 3;
            grid[4][3] = 3;
            grid[4][5] = 3;
            grid[4][6] = 3;
            grid[9][4] = 3;
            grid[10][4] = 3;
            grid[11][4] = 3;
            grid[12][4] = 3;
            grid[13][4] = 3;
            grid[11][3] = 3;
            grid[11][2] = 3;
            grid[9][7] = 3;
            grid[10][7] = 3;
            grid[11][7] = 3;
            grid[12][7] = 3;
            grid[8][8] = 3;
            grid[8][9] = 3;
            grid[8][10] = 3;
            grid[8][11] = 3;
            grid[8][12] = 3;
            grid[9][12] = 3;
            grid[9][10] = 3;
            grid[10][10] = 3;
            grid[11][10] = 3;
            grid[11][11] = 3;
            grid[6][15] = 3;
            grid[7][15] = 3;
            grid[8][15] = 3;
            grid[5][18] = 4;
            break;

        case 7:
            grid[1][9] = 2;
            grid[2][9] = 2;
            grid[3][9] = 2;
            grid[4][9] = 2;
            grid[5][9] = 2;
            grid[5][18] = 2;
            grid[5][17] = 2;
            grid[5][16] = 2;
            grid[5][15] = 2;
            grid[5][14] = 2;
            grid[14][18] = 2;
            grid[14][17] = 2;
            grid[14][16] = 2;
            grid[14][15] = 2;
            grid[14][14] = 2;
            grid[18][9] = 2;
            grid[17][9] = 2;
            grid[16][9] = 2;
            grid[15][9] = 2;
            grid[14][9] = 2;
            grid[11][1] = 2;
            grid[11][2] = 2;
            grid[11][3] = 2;
            grid[11][4] = 2;
            grid[11][5] = 2;
            grid[8][1] = 2;
            grid[8][2] = 2;
            grid[8][3] = 2;
            grid[8][4] = 2;
            grid[8][5] = 2;
            break;

        case 8:
            grid[1][9] = 2;
            grid[2][9] = 2;
            grid[3][9] = 2;
            grid[4][9] = 2;
            grid[5][9] = 2;
            grid[5][18] = 2;
            grid[5][17] = 2;
            grid[5][16] = 2;
            grid[5][15] = 2;
            grid[5][14] = 2;
            grid[5][13] = 2;
            grid[5][12] = 2;
            grid[14][18] = 2;
            grid[14][17] = 2;
            grid[18][9] = 2;
            grid[17][9] = 2;
            grid[16][9] = 2;
            grid[15][9] = 2;
            grid[14][9] = 2;
            grid[13][9] = 2;
            grid[12][9] = 2;
            grid[11][9] = 2;
            grid[10][9] = 2;
            grid[11][6] = 2;
            grid[11][1] = 2;
            grid[11][2] = 2;
            grid[11][3] = 2;
            grid[11][4] = 2;
            grid[11][5] = 2;
            grid[8][1] = 2;
            grid[8][2] = 2;
            grid[8][3] = 2;
            grid[8][4] = 2;
            break;

        case 9:
            grid[1][9] = 2;
            grid[2][9] = 2;
            grid[3][9] = 2;
            grid[4][9] = 2;
            grid[5][9] = 2;
            grid[5][18] = 2;
            grid[5][17] = 2;
            grid[5][16] = 2;
            grid[6][16] = 2;
            grid[7][16] = 2;
            grid[8][16] = 2;
            grid[9][16] = 2;
            grid[10][16] = 2;
            grid[10][17] = 2;
            grid[10][18] = 2;
            grid[18][13] = 2;
            grid[17][13] = 2;
            grid[16][13] = 2;
            grid[15][13] = 2;
            grid[14][13] = 2;
            grid[13][13] = 2;
            grid[13][12] = 2;
            grid[13][11] = 2;
            grid[13][10] = 2;
            grid[14][10] = 2;
            grid[15][10] = 2;
            grid[15][9] = 2;
            grid[15][8] = 2;
            grid[15][7] = 2;
            grid[16][7] = 2;
            grid[17][7] = 2;
            grid[17][6] = 2;
            grid[17][5] = 2;
            grid[17][4] = 2;
            grid[18][4] = 2;
            grid[12][1] = 2;
            grid[12][2] = 2;
            grid[12][3] = 2;
            grid[12][4] = 2;
            grid[12][5] = 2;
            grid[12][6] = 2;
            grid[11][6] = 2;
            grid[10][6] = 2;
            grid[10][5] = 2;
            grid[10][4] = 2;
            grid[10][3] = 2;
            grid[9][3] = 2;
            grid[8][3] = 2;
            grid[7][3] = 2;
            grid[6][3] = 2;
            grid[6][4] = 2;
            grid[6][5] = 2;
            grid[6][6] = 2;
            grid[5][6] = 2;
            grid[4][6] = 2;
            grid[4][5] = 2;
            grid[4][4] = 2;
            grid[4][3] = 2;
            grid[4][2] = 2;
            grid[4][1] = 2;
            break;
        }
	}

	public static final int WHITE	= 0;
	public static final int BLACK	= 1;
	public static final int BLUE	= 2;
	public static final int YELLOW	= 3;
	public static final int GREEN	= 4;

	private int[][] grid = null;
	private int size = 20;
}