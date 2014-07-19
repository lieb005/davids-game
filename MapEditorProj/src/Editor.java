import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class Editor {

	static TileLabel tiles;
	static TileLabel decorations;
	static Map_Edit edit;
	static JFrame window;
	public static void main(String[] args) { //set up GUI
		window = new JFrame("Map Creator");
		
		JTabbedPane labelPanel = new JTabbedPane();
		tiles = new TileLabel(true);
		decorations = new TileLabel(false);
		
		edit = new Map_Edit();
		window.add(edit);
		edit.setBounds(0, 0, 950, 722);
		edit.setVisible(true);
		
		labelPanel.setBackground(Color.black);
		labelPanel.setBounds(950, 0, 250, 700);
		labelPanel.add("Tiles", tiles);
		labelPanel.add("Decorations", decorations);
		window.add(labelPanel);
		
		
		window.setPreferredSize(new Dimension (1200, 722));
		window.setLayout(null);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setFocusable(true);
	}	
}
