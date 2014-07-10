import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class DrawCell {
	int positionX;
	int positionY;
	int baseDrawIndex = 0;
	ArrayList<Integer> overlayIndices = new ArrayList<Integer>();

	public DrawCell(int xPos, int yPos, int baseIndex) {
		positionX = xPos;
		positionY = yPos;
		baseDrawIndex = 0;
	}

	public void addIndex(int i) {
		overlayIndices.add(i);
	}

	public void clearIndices() {
		overlayIndices = new ArrayList<Integer>();
	}

	public int getBaseIndex() {
		return baseDrawIndex;
	}

	public ArrayList<Integer> getOtherIndices() {
		return overlayIndices;
	}

	public int getX() {
		return positionX;
	}

	public int getY() {
		return positionY;
	}
}
