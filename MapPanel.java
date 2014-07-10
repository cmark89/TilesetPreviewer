import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class MapPanel extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("DRAWING");
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw the grid
		if(TilesetPreviewer.isShowingGrid()) {
			g.setColor(Color.yellow);
			for(int x = 0; x <= getWidth(); x += 48) {
				g.drawLine(x, 0, x, getHeight());
			}
			for(int y = 0; y <= getHeight(); y += 48) {
				g.drawLine(0, y, getWidth(), y);
			}
		}
	}
}
