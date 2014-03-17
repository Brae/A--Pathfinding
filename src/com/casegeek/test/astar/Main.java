package com.casegeek.test.astar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Main {
	
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closedList = new ArrayList<Node>();
	private ArrayList<Node> path = new ArrayList<Node>();
	private Robot robot;
	private Room room;
	private Picture pic;
	private int ranX, ranY;
	
	public Main() {
		System.out.println("CREATING");
		robot = new Robot();
		room = new Room(8);
		pic = new Picture(room, robot);
		pic.draw(room, robot);
		System.out.println("CREATED");
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	public void run() {	
		Random ran = new Random();
		ranX = ran.nextInt(18) + 1;
		ranY = ran.nextInt(18) + 1;
		room.setTarget(ranX, ranY);
		//Add the starting node to open list
		openList.add(new Node(robot.get_xpos(), robot.get_ypos(), null,
				ranX, ranY));
		System.out.println("ADDED START NODE - \n" + openList.get(0).toString());
		//while the open list is not empty
		while (!openList.isEmpty()) {
			//Current node = node from open list with the lowest cost
			Node currNode = getLowestCost();
			//if current node = goal node then
			if (currNode.getX() == ranX && currNode.getY() == ranY) {
				//path complete
				System.out.println("PATH COMPLETE");
				dump(openList);
				markPath(currNode);
				pic.draw(room, robot);
				move(path, ranX, ranY);
				break;
			//else
			} else {
				//move the current node to the closed list
				openList.remove(currNode);
				closedList.add(currNode);
				//examine each node adjacent to the current node
				for (int row=1; row>=-1; row--) {
					for (int col=-1; col<=1; col++) {
						//for each adjacent node
						int tempx = currNode.getX() + col;
						int tempy = currNode.getY() + row;
						if (tempx == currNode.getX() && tempy == currNode.getY()) {
							//do nothing if this is the current node
						} else {
							//if it isn't on the open list
							if (!(isOnOpen(tempx, tempy)) &&
									//and isn't on the closed list
									!(isOnClosed(tempx, tempy)) &&
									//and isn't an obstacle then
									!(room.isObstacle(tempx, tempy))) {
								//move it to the open list and calculate cost
								Node temp = new Node(tempx, tempy, currNode, ranX, ranY);
								openList.add(temp);
								System.out.println("ADDED OPEN NODE - \n" + temp.toString());
							}
						}						
					}
				}				
			}		
		} //while end
		
	}
	
	private boolean isOnOpen(int x, int y) {
		for (Node n:openList) {
			if (x == n.getX() && y == n.getY()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isOnClosed(int x, int y) {
		for (Node n:closedList) {
			if (x == n.getX() && y == n.getY()) {
				return true;
			}
		}
		return false;
	}
	
	private Node getLowestCost() {
		Node min = openList.get(0);
		for (Node n:openList) {
			if (n.getC() < min.getC()) {
				min = n;
			}
		}
		return min;
	}
	
	private void markPath(Node end) {
		boolean complete = false;
		path.clear();
		path.add(end.getParent());
		room.setPath(end.getParent().getX(), end.getParent().getY());
		int counter = 0;
		while (!complete) {
			Node temp = path.get(counter);
			if (!(temp.getParent().getX() == 1 && temp.getParent().getY() == 1)) {
				path.add(temp.getParent());
				room.setPath(temp.getParent().getX(), temp.getParent().getY());
			} else {
				complete = true;
				dump(path);
			}
			counter++;
		}
	}
	
	private void dump(ArrayList<Node> array) {
		try {
			File file = new File("dump.txt");
		 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (Node n:array) {
				bw.append("\n" + n.toString());
			}		
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void move(ArrayList<Node> path, int destX, int destY) {
		for (int i=path.size()-1; i>-1; i--) {
			robot.setX(path.get(i).getX());
			robot.setY(path.get(i).getY());
			pic.draw(room, robot);
		}
		robot.setX(destX);
		robot.setY(destY);
		pic.draw(room, robot);
	}

}
