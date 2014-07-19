import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Map_Edit extends JPanel implements ActionListener{
	static //contain the tiles making up the grid
	ArrayList<ArrayList<Tile>> grid = new ArrayList<ArrayList<Tile>>();
	static Map curMap;
	static JScrollPane scroll;
	
	//main menu bar
	JMenuBar mBar;
	JMenu file;
	JMenu edit;
	JMenu prop;
	JMenu tiles;
	JMenu help;
	//drop downs 
	
	//file
	JMenuItem newMap;
	JMenuItem open;
	JMenuItem save;
	JMenuItem del;
	
	//edit
	JMenuItem undo;
	JMenuItem redo;
	JMenuItem copy;
	JMenuItem cut;
	JMenuItem paste;
	
	public static int tileChosen = -1;
	public static boolean onTile;
	
	static JPanel editSpace;
	public Map_Edit(){
		this.setLayout(null);
		this.setPreferredSize(new Dimension(950, 722));
		this.setBackground(Color.LIGHT_GRAY);
		editSpace = new JPanel();
		editSpace.setBounds(0, 25, 10000, 10025);
		editSpace.setBackground(Color.LIGHT_GRAY);
		scroll = new JScrollPane(editSpace);
		scroll.setBounds(0, 25, 950, 677);
		scroll.setVisible(true);
		this.add(scroll);
		
		//Main menus
		mBar = new JMenuBar();
		mBar.setBounds(0, 0, 950, 25);
		mBar.setVisible(true);
		file = new JMenu("File");
		edit = new JMenu("Edit");
		prop = new JMenu("Properties");
		tiles = new JMenu("Tiles");
		help = new JMenu("Help");
		
		//drop-down menus
		//file
		newMap = new JMenuItem("New Map");
		newMap.addActionListener(this);
		open = new JMenuItem("Open previous project");
		save = new JMenuItem("Save Map");
		save.addActionListener(this);
		del = new JMenuItem("Delete this map");
		
		//edit
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		copy = new JMenuItem("Copy Selection");
		cut = new JMenuItem("Cut Selection");
		paste = new JMenuItem("Paste");
		
		//adds
		this.add(mBar);
		mBar.add(file);
		mBar.add(edit);
		mBar.add(prop);
		mBar.add(tiles);
		mBar.add(help);
		
		file.add(newMap);
		file.add(open);
		file.add(save);
		file.add(del);
		
		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		edit.add(copy);
		edit.add(cut);
		edit.add(paste);
		
	}
	
	public static void setUpEditSpace(Dimension d){
		editSpace.setPreferredSize(d);
		editSpace.setLayout(null);
		scroll.setViewportView(editSpace);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(newMap)){
			Props_Window props = new Props_Window("New Map");
			props.setVisible(true);
		}
		if(e.getSource().equals(save)){
			Controller.save();
		}
	}
}
