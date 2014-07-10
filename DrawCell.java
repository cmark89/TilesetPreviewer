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
		baseDrawIndex = 0;
	}

	public void paint(Graphics g) {
		// Draw that part of the image here
	}
}
