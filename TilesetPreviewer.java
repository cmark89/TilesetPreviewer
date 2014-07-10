import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*; 
import java.io.*;
public class TilesetPreviewer {
	JFrame frame;
	JCheckBoxMenuItem showGridCheckbox;
	static Tileset currentTileset;
	static MapState mapState;

	static boolean showingGrid = false;

	public static void main(String[] args) {
		new TilesetPreviewer().buildGUI();
	}

	public TilesetPreviewer() {
		mapState = new MapState();
	}

	public static MapState getMapState() {
		return mapState;
	}

	public static Tileset getTileset() {
		return currentTileset;
	}

	public void buildGUI() {
		frame = new JFrame("Tileset Previewer");
		frame.getContentPane().add(new MapPanel());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");

		JMenuItem loadTilesetItem = new JMenuItem("Load Tileset");
		loadTilesetItem.addActionListener(new OpenTextureListener());
		//JMenuItem saveScreenshotItem = new JMenuItem("Save Screenshot");

		fileMenu.add(loadTilesetItem);
		//fileMenu.add(saveScreenshotItem);
		
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

	private void trySetTileset(File f) {		
		currentTileset = new Tileset(f);
		mapState.refresh();
		frame.repaint();
	}

	class OpenTextureListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Images",
				ImageIO.getReaderFileSuffixes());
			chooser.addChoosableFileFilter(imageFilter);
			chooser.setAcceptAllFileFilterUsed(false);

			int returnValue = chooser.showOpenDialog(frame);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				trySetTileset(f);
			}
			if(returnValue == JFileChooser.CANCEL_OPTION) {
				return;
			}
		}
	}
}
