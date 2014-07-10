import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class MapState {
	boolean [][] isWall;
	static DrawCell[][] drawCells;
	String stringMap = 
"xxxxxxxxxxxxxxx\n" +
"xx xxx   xxxxxx\n" +
"xx xx      xxxx\n" +
"xx  xx        x\n" +
"xx  x        xx\n" +
"xxx=xxx   x   x\n" +
"x     x  xxxxxx\n" +
"x     =  x    x\n" +
"xxx   x  =    x\n" +
"xxxxxxx  x    x\n" +
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

		parseInitialMap();
	}

	private void parseInitialMap() {
		// Split into substrings
		String[] rows = new String[14];
		rows = stringMap.split("\\n");

		int rowNumber = 0;
		for(String row : rows) {
			parseRow(row, rowNumber);
		}

		// We've parsed it, so now we must update the tile indices
	}

	private void parseRow(String rowChars, int rowNumber) {
		for(int i = 0; i < rowChars.length(); i++) {
			if(rowChars.charAt(i) == 'x') {
				isWall[i][rowNumber] = true;
			} else if(rowChars.charAt(i) == '=') {
				// Add a door
			} else if(rowChars.charAt(i) == '>') {
				// Add downstairs
			} else if(rowChars.charAt(i) == '<') {
				// Add upstairs
			}
		}
	}

	private void setupMapIndices() {
		for(int x = 0; x < 15; x++) {
			for(int y = 0; y < 14; y++) {
				int index = 0;
				drawCells[x][y] = new DrawCell(x,y,0);
			}
		}	
	}

	private void processAutotiling() {
		// the big, awful function is here
		//
	}

	public void updateMapIndices() {
		// This is awful, but we want to have the ability to edit, so...
		setupMapIndices();
		processAutotiling();
	}

	public static DrawCell[][] getDrawCells() {
		return drawCells;
	}
}
