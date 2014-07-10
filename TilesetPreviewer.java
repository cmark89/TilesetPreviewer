import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TilesetPreviewer {
	JFrame frame;
	JCheckBoxMenuItem showGridCheckbox;
	static boolean showingGrid = false;

	public static void main(String[] args) {
		new TilesetPreviewer().buildGUI();
	}


	public void buildGUI() {
		frame = new JFrame("Tileset Previewer");
		frame.getContentPane().add(new MapPanel());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");

		JMenuItem loadTilesetItem = new JMenuItem("Load Tileset");
		JMenuItem saveScreenshotItem = new JMenuItem("Save Screenshot");

		fileMenu.add(loadTilesetItem);
		fileMenu.add(saveScreenshotItem);
		
		showGridCheckbox = new JCheckBoxMenuItem("Show Grid");
		showGridCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				showingGrid = showGridCheckbox.isSelected();
				frame.repaint();
			}
		});
		viewMenu.add(showGridCheckbox);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		frame.setJMenuBar(menuBar);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(15 * 48,14 * 48);
		frame.setVisible(true);
	}	


	public static boolean isShowingGrid() {
		return showingGrid;
	}
}
