import javax.swing.*;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class MapPanel extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("DRAWING");
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// If the tileset is not null...
		if(TilesetPreviewer.getTileset() != null) {
			// Loop over the draw cells
			BufferedImage texture = TilesetPreviewer.getTileset().getTexture();
			DrawCell[][] cells = TilesetPreviewer.getMapState().getDrawCells();
			ArrayList<DrawCell> cellsToDraw = new ArrayList<DrawCell>();
			for(int x = 0; x < 15; x++) {
				for(int y = 0; y < 14; y++) {
					cellsToDraw.add(cells[x][y]);	
				}
			}

			for(DrawCell d : cellsToDraw) {
				System.out.println("Draw cell at " + d.getX() + ", " + d.getY());
				// Draw the base
				int index = d.getBaseIndex();
				Tileset tileset = TilesetPreviewer.getTileset();
				Rect r = tileset.getRectFromIndex(index);
				g.drawImage(texture, d.getX() * 48, d.getY() * 48, (d.getX() * 48) + 48,
					(d.getY() * 48) + 48, r.getX(), r.getY(), r.getX() + 48, 
					r.getY() + 48, null); 

				for(int i : d.getOtherIndices()) {
					System.out.println("Draw wall");
					r = tileset.getRectFromIndex(i);
					g.drawImage(texture, d.getX() * 48, d.getY() * 48, (d.getX() * 48) + 48,
					(d.getY() * 48) + 48, r.getX(), r.getY(), r.getX() + 48, 
					r.getY() + 48, null); 
				}
			}
		}

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
