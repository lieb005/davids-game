import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Editor extends JApplet implements ActionListener{

	static Map edit; 
	static JFrame window;
	JMenuItem editMode;
	static Editor e;
	
	static TileLabel decorations;
	static TileLabel tiles;
	
	static JScrollPane mapScroll;

	public static void main(String[] args) {
		window = new JFrame("Map Creator");
		e = new Editor();
		window.add(e);
		
		e.init();
		e.start();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(new Dimension(1200, 722));
		window.setVisible(true);
	}

	public void init() { // set up GUI

		// this loads decor and tiles
		ImageHandler.loadImages(true);

		setLayout(new BorderLayout());
		
		edit = new Map("New Map", 50, 50);
		mapScroll = new JScrollPane(edit);
		mapScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mapScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(mapScroll, BorderLayout.CENTER);

		JTabbedPane labelPanel = new JTabbedPane();
		labelPanel.setBackground(Color.black);
		// labelPanel.setBounds(950, 0, 250, 700);
		tiles = new TileLabel(true);
		JScrollPane tileScroll = new JScrollPane(tiles);
		tileScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tileScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		labelPanel.add("Tiles", tileScroll);
		decorations = new TileLabel(false);
		JScrollPane decorScroll = new JScrollPane(decorations);
		decorScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		decorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		labelPanel.add("Decorations", decorScroll);
		add(labelPanel, "East");

		// Set up menu bar

		// main menu bar
		final JMenuBar mBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu propMenu = new JMenu("Properties");
		JMenu tilesMenu = new JMenu("Tiles");
		JMenu helpMenu = new JMenu("Help");
		// drop downs

		// file
		JMenuItem newMap = new JMenuItem("New Map");
		newMap.addActionListener(this);
		JMenuItem open = new JMenuItem("Open Map");
		open.addActionListener(this);
		JMenuItem save = new JMenuItem("Save Map");
		save.addActionListener(this);
		JMenuItem rename = new JMenuItem("Rename Map");
		rename.addActionListener(this);
		JMenuItem del = new JMenuItem("Delete This Map");
		del.addActionListener(this);
		JMenuItem importTiles = new JMenuItem("Import Image");
		importTiles.addActionListener(this);

		// edit
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		JMenuItem redo = new JMenuItem("Redo");
		redo.addActionListener(this);
		JMenuItem copy = new JMenuItem("Copy Selection");
		copy.addActionListener(this);
		JMenuItem cut = new JMenuItem("Cut Selection");
		cut.addActionListener(this);
		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(this);
		
		//properties
		JMenuItem resize = new JMenuItem("Map Data");
		resize.addActionListener(this);
		editMode = new JMenuItem("Game Mode");
		editMode.addActionListener(this);
		JMenuItem toggleGrid = new JMenuItem("Show/Hide Grid");
		toggleGrid.addActionListener(this);
		JMenuItem timeSet = new JMenuItem("Set Time");
		timeSet.addActionListener(this);
		JMenuItem showCols = new JMenuItem("Show/Hide Collision Boxes");
		showCols.addActionListener(this);

		// adds
		mBar.add(fileMenu);
		mBar.add(editMenu);
		mBar.add(propMenu);
		mBar.add(tilesMenu);
		mBar.add(helpMenu);

		fileMenu.add(newMap);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(rename);
		fileMenu.add(del);
		fileMenu.add(importTiles);

		editMenu.add(undo);
		editMenu.add(redo);
		editMenu.addSeparator();
		editMenu.add(copy);
		editMenu.add(cut);
		editMenu.add(paste);
		
		propMenu.add(resize);
		propMenu.add(showCols);
		propMenu.add(editMode);
		propMenu.add(toggleGrid);
		propMenu.add(timeSet);
		
		setJMenuBar(mBar);
		mBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				validate();
				invalidate();
			}
		});
		validate();
		invalidate();
	}

	public static void setTitle(String title) {
		window.setTitle("Map Creator - " + title);
	}
	
	public static void setMap(Map m){
		edit.grid.resize(0, 0);
		edit.grid.resize(m.getGridWidth(), m.getGridHeight());
		edit.grid = m.grid;
		edit.setName(m.getName());
		edit.setScale(1);
		e.repaint();
	}
	
	public static void refreshImages(){
		ImageHandler.loadImages(true);
		tiles.getTiles();
		decorations.getDecor();
	}

	public void actionPerformed(ActionEvent e) {
		String buttonLabel = ((AbstractButton) e.getSource()).getText();
		if (buttonLabel == "New Map") {
			editMode.setText("Game Mode");
			propertiesDialog(true);
		}
		else if(buttonLabel == "Open Map"){
			JFileChooser choose = new JFileChooser("./Maps/");
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt Files", "txt");
			choose.setFileFilter(filter);
			choose.setFileHidingEnabled(true);
			int returnVal = choose.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				Controller.open(choose.getSelectedFile().getAbsolutePath());
				editMode.setText("Game Mode");
			}
		}
		else if (buttonLabel == "Save Map") {
			Controller.save(edit);
		}
		else if(e.getSource() == editMode){
			if(buttonLabel.equals("Editing Mode")){
				edit.setScale(1);
				editMode.setText("Game Mode");
			}else{
				edit.setScale(2);
				editMode.setText("Editing Mode");
			}
		}
		else if(buttonLabel == "Show/Hide Grid"){
			edit.setGridVisability(!edit.showGrid);
		}
		else if(buttonLabel == "Map Data"){
			propertiesDialog(false);
		}
		else if(buttonLabel == "Rename Map"){
			JPanel name = new JPanel();
			JPanel buttons = new JPanel();
			JButton ok = new JButton("Ok");
			JButton cancel = new JButton("Cancel");
			buttons.add(ok);
			buttons.add(cancel);
			final JTextField nameField = new JTextField(15);
			name.add(nameField);
			final JOptionPane rename = new JOptionPane(null,
					JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null,
					new JPanel[] {buttons, name});
			final JDialog d = rename.createDialog("Rename this map");
			ok.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					boolean canWrite = false;
					if(nameField.getText().length() != 0){
						canWrite = true;
					}else{
						JOptionPane.showMessageDialog(null, "Enter a name for your map",
								"Couldn't rename", JOptionPane.ERROR_MESSAGE);
					}
					if(canWrite){
						d.setVisible(false);
						d.dispose();
						Controller.rename(nameField.getText());
						edit.setName(nameField.getText());
					}
				}
			});
			cancel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					d.setVisible(false);
					d.dispose();
				}
			});
			d.setVisible(true);
		}
		else if(buttonLabel == "Delete This Map"){
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this map?",
					"Delete?", JOptionPane.YES_NO_OPTION);
			if(choice == 0){
				Controller.deleteMap(edit.getName());
			}
		}
		else if(buttonLabel == "Import Image"){
			Controller.importTile();
		}
		else if(buttonLabel == "Show/Hide Collision Boxes"){
			Map.setColisionVisibility();
		}else if(buttonLabel == "Undo"){
			edit.undo();
		}else if(buttonLabel == "Redo"){
			edit.redo();
		}
	}

	private void propertiesDialog(boolean newMap) {
		final boolean newMapBool = newMap;
		JPanel sizePanel = new JPanel();
		JLabel xLabel = new JLabel("Width:");
		JLabel yLabel = new JLabel("Hieght:");
		final JTextField widthTextField;
		final JTextField heightTextField;

		if (newMapBool) {
			widthTextField = new JTextField("50", 5);
			heightTextField = new JTextField("50", 5);
		} else {
			widthTextField = new JTextField(
					String.valueOf(edit.getGridWidth()), 5);
			heightTextField = new JTextField(String.valueOf(edit
					.getGridHeight()), 5);
		}
		sizePanel.add(xLabel);
		sizePanel.add(widthTextField);
		sizePanel.add(yLabel);
		sizePanel.add(heightTextField);

		// JPanel namePanel = new JPanel();
		// JLabel nameLabel = new JLabel("Name:");
		//final JTextField nameTextField = new JTextField(15);
		// namePanel.add(nameLabel);
		// namePanel.add(nameTextField);

		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("Okay");
		final JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		JPanel name = new JPanel();
		final JTextField nameField = new JTextField("Map Name", 20);
		name.add(nameField);
		
		final JOptionPane options = new JOptionPane(null,
				JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null,
				new JPanel[] {buttonPanel, sizePanel, name });

		final JDialog d;
		if (newMapBool) {
			nameField.setText("Map Name");
			d = options.createDialog("New Map");
		} else {
			nameField.setText(edit.getName());
			d = options.createDialog("Properties For " + edit.getName());
		}

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean canWrite = true;
				int w = 0;
				int h = 0;
				try {
					w = Integer.parseInt(widthTextField.getText());
					h = Integer.parseInt(heightTextField.getText());
				} catch (Exception ex) {
					canWrite = false;
					JOptionPane.showMessageDialog(null,
							"Only numbers can be used for a map dimension");
				}
				
				if(w * h > 90000){
					canWrite = false;
					JOptionPane.showMessageDialog(null,
							"Your map is too big: " + (w * h) + " tiles. Max: 90000 tiles");
				}
				
				if(Controller.checkFileExistance(nameField.getText() + ".txt") && newMapBool){
					canWrite = false;
					int choice=JOptionPane.showConfirmDialog(null, "A map with this name already exists."
							+ " Would you like to overwrite it?", "Map already exists", 
							JOptionPane.YES_NO_OPTION);
					if(choice == 0){
						canWrite = true;
					}
				}
				
				if (canWrite) {
					if (newMapBool || edit == null){
						setMap(new Map(nameField.getText(), w, h));
						
					} else {
						edit.setName(nameField.getText());
						edit.setGridSize(w, h);
					}
					d.setVisible(false);
					d.dispose();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
			}
		});
		d.setVisible(true);
		d.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				cancelButton.getActionListeners()[0]
						.actionPerformed(new ActionEvent(cancelButton,
								ActionEvent.ACTION_PERFORMED, "Cancelled"));
			}
		});
	}
}
