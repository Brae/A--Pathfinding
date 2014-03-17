package com.casegeek.test.astar;

public class Node {
	private int h,s,c;
	private int[][] pos = new int[1][2];
	private Node parent;
	
	public Node(int x, int y, Node origin, int destX, int destY) {
		//Set the basic variables
		this.pos[0][0] = x;
		this.pos[0][1] = y;
		this.parent = origin;
		
		//Get heuristic value
		this.h = heuristic(destX, destY);
		
		//Get score from starting position
		if (parent!=null) {
			if (x == parent.getX() || y == parent.getY()) {
				this.s = parent.getS()+1;
			} else {
				this.s = parent.getS()+2;
			}
			
		}		
		
		//Calculate cost
		this.c = this.s + this.h;
	}
	
	public int getS() {
		return this.s;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public int getC() {
		return c;
	}
	
	public int getH() {
		return this.h;
	}
	
	public int getX() {
		return this.pos[0][0];
	}
	
	public int getY() {
		return this.pos[0][1];
	}
	
	private int heuristic(int destx, int desty) {
		int movementCost = 1;
		int dx = Math.abs(this.getX() - destx);
		int dy = Math.abs(this.getY() - desty);
		return movementCost*Math.max(dx, dy);
	}
	
	public String toString() {
		String output = null;
		if (parent!=null) {
			output = "H: " + h + " | S: " + s + " | C: " + c + "\n"
					+ "x: " + pos[0][0] + " | y: " + pos[0][1] + "\n"
					+ "Parent: true";
		} else {
			output = "H: " + h + " | S: " + s + " | C: " + c + "\n"
					+ "x: " + pos[0][0] + "y: " + pos[0][1] + "\n"
					+ "Parent: false";
		}
		
		return output;
	}

}
