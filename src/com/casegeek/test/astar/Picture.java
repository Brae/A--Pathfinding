package com.casegeek.test.astar;

import java.awt.*;
import javax.swing.*;
import SECommon.*;

/**
The main drawing 'canvas' for the Room & Robot sim
*/
public class Picture extends JPanel {

    public Picture (Room room, Robot robot) {
		this.room = room;
		this.robot = robot;

		//prep the images
		this.prepImages();

		//init GUI
		int squareDim = CELLSIZE * room.room_size();
		this.setPreferredSize (new Dimension(squareDim, squareDim));
		frame = new DrawFrame(this);
		frame.display();
		this.delay(1000);
	}

    public void paintComponent (Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			for (int x = 0; x < room.room_size(); ++x) {
				for (int y = 0; y < room.room_size(); ++y) {
					//get the floor value of this room location, draw pertinent floor image
					int cellState = room.cell_state(x, y);
					g2d.drawImage(floor_cells[cellState], x * CELLSIZE, (room.room_size() * CELLSIZE) - ((y + 1)* CELLSIZE), CELLSIZE, CELLSIZE, this);
				}
			}

			//draw the robot at its coords
			g2d.drawImage(jBell[robot.get_dir()], (robot.get_xpos() - 1) * CELLSIZE, (room.room_size() * CELLSIZE) - ((robot.get_ypos() + 2) * CELLSIZE), CELLSIZE * 3, CELLSIZE * 3, this);
    }

	/**
	Draws the Robot at it's current position in the Room
	*/
    public void draw (Room room, Robot robot) {
		this.repaint();
		frame.postPos(robot.get_xpos(), robot.get_ypos());
		delay(BASEDELAY);
    }

	/**
	Displays the target message on the statusbar at the bottom of the GUI

	@param msg the message to display
	*/
    public void printStatus (String msg) {
		frame.showStatus(msg);
    }

    /**
    Pauses the currently executing Thread

    @param i the time in millisecinds to delay for
    */
    protected void delay (int i) {
    	try {
		    Thread.sleep(i);
	    }
	    catch (InterruptedException e) {}
    }

    private void prepImages() {
		tracker = new MediaTracker(this);
		int counter = 0;

		//prep the floor images
		for (int x = 0; x < floor_cells.length; ++x) {
			tracker.addImage(floor_cells[x], counter);
			++counter;
		}

		//prep the jelly belly images
		for (int x = 0; x < jBell.length; ++x) {
			tracker.addImage(jBell[x], counter);
			++counter;
		}

		try {
			tracker.waitForAll();
		}
		catch (InterruptedException ignored) {}
	}

	protected final Room getRoom() {
		return room;
	}

    private Room room = null;
    private Robot robot = null;
    private DrawFrame frame = null;

    //image vars
    private Toolkit tk = Toolkit.getDefaultToolkit();
    private MediaTracker tracker = null;
    private Image[] jBell =  {
						tk.getImage(Picture.class.getResource("media/jBell0.png")),
						tk.getImage(Picture.class.getResource("media/jBell1.png")),
						tk.getImage(Picture.class.getResource("media/jBell2.png")),
						tk.getImage(Picture.class.getResource("media/jBell3.png"))
						};
    private final Image[] floor_cells = {
						tk.getImage(Picture.class.getResource("media/0.gif")),
    					tk.getImage(Picture.class.getResource("media/1.gif")),
    					tk.getImage(Picture.class.getResource("media/2.gif")),
    					tk.getImage(Picture.class.getResource("media/3.gif")),
    					tk.getImage(Picture.class.getResource("media/4.gif"))
    					};

    protected static final int CELLSIZE = 25;
    private static final int BASEDELAY = 800;
}