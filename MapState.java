import javax.swing.*;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

public class MapState {
	final int Width = 15;
	final int Height = 14;

	boolean [][] isWall;
	static DrawCell[][] drawCells;
	ArrayList<Point> upStairs = new ArrayList<Point>();
	ArrayList<Point> downStairs = new ArrayList<Point>();
	ArrayList<Point> doors = new ArrayList<Point>();
	String stringMap = 
"xxxxxxxxxxxxxxx\n" +
"xx<xxx   xxxxxx\n" +
"xx xx      xxxx\n" +
"xx  xx        x\n" +
"xx  x        xx\n" +
"xxx=xxx   x   x\n" +
"x     x  xxxxxx\n" +
"x     =  x    x\n" +
"xxx   x  =    x\n" +
"xxxxxxx  x  > x\n" +
"xx   x   xx   x\n" +
"xxxx     xxxxxx\n" +
"xxxxxx   xxxxxx\n" +
"xxxxxxxxxxxxxxx\n";


	public MapState() {
		isWall = new boolean[15][14];
		drawCells = new DrawCell[15][14];
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 14; y++) {
				isWall[x][y] = false;
			}
		}
		setupMapIndices();
		parseInitialMap();
	}

	private void parseInitialMap() {
		// Split into substrings
		String[] rows = new String[14];
		rows = stringMap.split("\\n");

		int rowNumber = 0;
		for(String row : rows) {
			parseRow(row, rowNumber);
			rowNumber++;
		}

		// We've parsed it, so now we must update the tile indices
	}

	private void parseRow(String rowChars, int rowNumber) {
		System.out.println("parse a row!");
		for(int i = 0; i < rowChars.length(); i++) {
			if(rowChars.charAt(i) == 'x') {
				isWall[i][rowNumber] = true;
			} else if(rowChars.charAt(i) == '=') {
				// Add a door
				doors.add(new Point(i, rowNumber));
			} else if(rowChars.charAt(i) == '>') {
				// Add downstairs
				downStairs.add(new Point(i, rowNumber));
			} else if(rowChars.charAt(i) == '<') {
				// Add upstairs
				upStairs.add(new Point(i, rowNumber));
			}
		}
	}

	private void addDoor(int x, int y) {
		final int HORIZ_DOOR = 9;
		final int VERT_DOOR = 7;
		if(x >= 0 && x < 15 && y >= 0 && y < 14) {
			if(x + 1 < 15 && isWall[x+1][y]) {
				// To the right is a wall, so it's a vertical door
				drawCells[x][y].addIndex(VERT_DOOR);
			} else {
				// Horizontal door
				drawCells[x][y].addIndex(HORIZ_DOOR);
			}
		}
	}

	private void addUpStairs (int x, int y) {
		System.out.println(x + " " + y);
		final int INDEX = 2;
		drawCells[x][y].addIndex(INDEX);
	}

	private void addDownStairs (int x, int y) {
		final int INDEX = 3;
		drawCells[x][y].addIndex(INDEX);
	}

	private void setupMapIndices() {
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 14; y++) {
				int index = 0;
				drawCells[x][y] = new DrawCell(x,y,0);
			}
		}	
	}

	private void clearTiles() {
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 14; y++) {
				drawCells[x][y].clearIndices();
			}
		}
	}

	private void processAutotiling(boolean wall, int row) {
		clearTiles();

		System.out.println("Process autotiling.");
		// the big, awful function is here
		ArrayList<Point> matchingTiles = new ArrayList<Point>();	

		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 14; y++) {
				if(isWall[x][y] == wall) {
					matchingTiles.add(new Point(x,y));
				}
			}
		}

		int columns = TilesetPreviewer.getTileset().getColumns();
		int TOP_LEFT = row * columns  + 0;
		int TOP = row * columns + 1;
		int TOP_RIGHT = row * columns + 2;
		int LEFT = (row + 1) * columns + 0;
		int CENTER = (row + 1) * columns + 1;
		int RIGHT = (row + 1) * columns + 2;
		int BOTTOM_LEFT = (row + 2) * columns + 0;
		int BOTTOM = (row + 2) * columns + 1;
		int BOTTOM_RIGHT = (row + 2) * columns + 2;

		int VERTICAL_TOP = row * columns + 3;
		int VERTICAL = (row + 1) * columns + 3;
		int VERTICAL_BOTTOM = (row + 2) * columns + 3;

		int HORIZONTAL_LEFT = (row + 2) * columns + 4;
		int HORIZONTAL = (row + 2) * columns + 5;
		int HORIZONTAL_RIGHT = (row + 2) * columns + 6;

		int SOLO = row * columns + 4;

		int CORNER_BOTTOM_RIGHT = row * columns + 5;
		int CORNER_BOTTOM_LEFT = row * columns + 6;
		int CORNER_TOP_RIGHT = (row + 1) * columns + 5;
		int CORNER_TOP_LEFT = (row + 1) * columns + 6;

		Point newPoint;
		boolean[] neighborWalls = new boolean[4];
		boolean[] cornerWalls = new boolean[4];
		Point[] steps = new Point[4];
		int id = CENTER;

		for(Point p : matchingTiles) {
			// Clockwise, 0 = up
			neighborWalls = new boolean[4];
			steps = new Point[4];
			steps[0] = new Point (0, -1);
			steps[1] = new Point(1, 0);
			steps[2] = new Point(0,1);
			steps[3] = new Point(-1, 0);
		
			for (int i = 0; i < 4; i++)	{
				newPoint = new Point(p.getX() + steps [i].getX(),
					p.getY() + steps[i].getY());

				if (newPoint.getX() < 0 || newPoint.getX() >= Width ||
					newPoint.getY() < 0 || newPoint.getY() >= Height) {
					// If the step is out of the level's bounds, defer to the default tile
					neighborWalls [i] = (wall);
				} else {
					neighborWalls [i] = 
					(isWall [newPoint.getX()][newPoint.getY()] == wall);
				}
			}

			// Now get the corners... top-left = 0, clockwise
			steps = new Point[4];
			steps[0] = new Point(-1,-1); 
			steps[1] = new Point(1, -1);
			steps[2] = new Point(1,1);
			steps[3] = new Point(-1, 1); 

			for(int i = 0; i < 4; i++) 	{
				newPoint = new Point(p.getX() + steps[i].getX(),
					p.getY() + steps[i].getY());

				if (newPoint.getX() < 0 || newPoint.getX() >= Width ||
				newPoint.getY() < 0 || newPoint.getY() >= Height)
				{
					cornerWalls [i] = (wall);
				}
				else
				{
					cornerWalls[i] = (isWall [newPoint.getX()] 
						[newPoint.getY()] == wall);
				}
			}

			// We now have the array of our neighbors, so we must process the tile.

			// For the "wide" edges
			if (!neighborWalls [0] && neighborWalls [1] && neighborWalls [2] && !neighborWalls [3])
				id = TOP_LEFT;
			else if (!neighborWalls [0] && neighborWalls [1] && neighborWalls [2] && neighborWalls [3])
				id = TOP;
			else if (!neighborWalls [0] && !neighborWalls [1] && neighborWalls [2] && neighborWalls [3])
				id = TOP_RIGHT;
			else if (neighborWalls [0] && neighborWalls [1] && neighborWalls [2] && !neighborWalls [3])
				id = LEFT;
			else if (neighborWalls [0] && neighborWalls [1] && neighborWalls [2] && neighborWalls [3])
				id = CENTER;
			else if (neighborWalls [0] && !neighborWalls [1] && neighborWalls [2] && neighborWalls [3])
				id = RIGHT;
			else if (neighborWalls [0] && neighborWalls [1] && !neighborWalls [2] && !neighborWalls [3])
				id = BOTTOM_LEFT;
			else if (neighborWalls [0] && neighborWalls [1] && !neighborWalls [2] && neighborWalls [3])
				id = BOTTOM;
			else if (neighborWalls [0] && !neighborWalls [1] && !neighborWalls [2] && neighborWalls [3])
				id = BOTTOM_RIGHT;

			// For vertical tiles
			else if (!neighborWalls [0] && !neighborWalls [1] && neighborWalls [2] && !neighborWalls [3])
				id = VERTICAL_TOP;
			else if (neighborWalls [0] && !neighborWalls [1] && neighborWalls [2] && !neighborWalls [3])
				id = VERTICAL;
			else if (neighborWalls [0] && !neighborWalls [1] && !neighborWalls [2] && !neighborWalls [3])
				id = VERTICAL_BOTTOM;

				// For horizontal tiles
			else if (!neighborWalls [0] && neighborWalls [1] && !neighborWalls [2] && !neighborWalls [3])
				id = HORIZONTAL_LEFT;
			else if (!neighborWalls [0] && neighborWalls [1] && !neighborWalls [2] && neighborWalls [3])
				id = HORIZONTAL;
			else if (!neighborWalls [0] && !neighborWalls [1] && !neighborWalls [2] && neighborWalls [3])
				id = HORIZONTAL_RIGHT;

				// For orphans
			else if (!neighborWalls [0] && !neighborWalls [1] && !neighborWalls [2] && !neighborWalls [3])
				id = SOLO;

			drawCells[p.getX()][p.getY()].addIndex(id);

			// We have the base texture, now check to see if we need to add a corner
			if(neighborWalls[0] && neighborWalls[1] && !cornerWalls[1])
				drawCells[p.getX()][p.getY()].addIndex(CORNER_TOP_RIGHT);
			if(neighborWalls[1] && neighborWalls[2] && !cornerWalls[2])
				drawCells[p.getX()][p.getY()].addIndex(CORNER_BOTTOM_RIGHT);
			if(neighborWalls[2] && neighborWalls[3] && !cornerWalls[3])
				drawCells[p.getX()][p.getY()].addIndex(CORNER_BOTTOM_LEFT);
			if(neighborWalls[3] && neighborWalls[0] && !cornerWalls[0])
				drawCells[p.getX()][p.getY()].addIndex(CORNER_TOP_LEFT);
		}
	}

	public void refresh() {
		System.out.println("Refresh");
		// This is awful, but we want to have the ability to edit, so...
		setupMapIndices();
		processAutotiling(true, 2);
		processDoodads();
	}

	private void processDoodads() {
		for(Point p : doors) {
			addDoor(p.getX(), p.getY());
		}
		for(Point p : upStairs) {
			addUpStairs(p.getX(), p.getY());
		}
		for(Point p : downStairs) {
			addDownStairs(p.getX(), p.getY());
		}
	}

	public static DrawCell[][] getDrawCells() {
		return drawCells;
	}

	class Point {
		int x;
		int y;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
