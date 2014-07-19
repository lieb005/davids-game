import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
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

public class Editor extends JApplet implements ActionListener {

	private Map edit;

	public static void main(String[] args) {
		final JFrame window;
		window = new JFrame("Map Creator");
		Editor e = new Editor();
		window.add(e);
		e.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == "name") {
					window.setTitle("Map Creator - "
							+ (String) evt.getNewValue());
					// System.out.println(evt.getPropertyName() + " = " +
					// evt.getNewValue());
				}
			}
		});
		e.init();
		e.start();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// window.setResizable(false);
		// window.setLayout(null);
		window.setSize(new Dimension(1200, 722));
		// window.pack();
		window.setVisible(true);
	}

	public void init() { // set up GUI
		TileLabel decorations;
		TileLabel tiles;

		// this loads decor and tiles
		ImageHandler.loadImages(true);

		setLayout(new BorderLayout());

		// 950 / Tile.TILE_SIZE, 722 /Tile.TILE_SIZE
		edit = new Map("New Map", 0, 0);
		add(new JScrollPane(edit), "Center");
//		edit.setBounds(0, 0, 950, 722);
		// edit.setVisible(true);

		JTabbedPane labelPanel = new JTabbedPane();
		labelPanel.setBackground(Color.black);
		// labelPanel.setBounds(950, 0, 250, 700);
		tiles = new TileLabel(true);
		labelPanel.add("Tiles", tiles);
		decorations = new TileLabel(false);
		labelPanel.add("Decorations", decorations);
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
		JMenuItem open = new JMenuItem("Open previous project");
		JMenuItem save = new JMenuItem("Save map");
		save.addActionListener(this);
		JMenuItem del = new JMenuItem("Delete this map");

		// edit
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem copy = new JMenuItem("Copy Selection");
		JMenuItem cut = new JMenuItem("Cut Selection");
		JMenuItem paste = new JMenuItem("Paste");

		// adds
		mBar.add(fileMenu);
		mBar.add(editMenu);
		mBar.add(propMenu);
		mBar.add(tilesMenu);
		mBar.add(helpMenu);

		fileMenu.add(newMap);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(del);

		editMenu.add(undo);
		editMenu.add(redo);
		editMenu.addSeparator();
		editMenu.add(copy);
		editMenu.add(cut);
		editMenu.add(paste);
		setJMenuBar(mBar);
		validate();
		invalidate();
	}

	public void setTitle(String title) {
		if (getPropertyChangeListeners().length > 0) {
			String old = getName();
			setName(title);
			getPropertyChangeListeners()[0]
					.propertyChange(new PropertyChangeEvent(this, "name", old,
							title));
		}
	}

	public void actionPerformed(ActionEvent e) {
		String buttonLabel = ((AbstractButton) e.getSource()).getText();
		if (buttonLabel == "New Map") {
		}
		if (buttonLabel == "Save Map") {
			Controller.save(getName(), edit);
		}

	}

	public void propertiesDialog(boolean newMap) {
		final boolean newMapBool = newMap;
		JPanel sizePanel = new JPanel();
		JLabel xLabel = new JLabel("Width:");
		JLabel yLabel = new JLabel("Hieght:");
		final JTextField widthTextField;
		final JTextField heightTextField;

		if (newMapBool) {
			widthTextField = new JTextField("20", 5);
			heightTextField = new JTextField("20", 5);
		} else {
			widthTextField = new JTextField(String.valueOf(edit.getWidth()), 5);
			heightTextField = new JTextField(String.valueOf(edit.getHeight()),
					5);
		}
		sizePanel.add(xLabel);
		sizePanel.add(widthTextField);
		sizePanel.add(yLabel);
		sizePanel.add(heightTextField);

		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Name:");
		final JTextField nameTextField = new JTextField(15);
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);

		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("Okay");
		JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		JOptionPane options = new JOptionPane("Map:",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null,
				new JPanel[] { sizePanel });
		options.setWantsInput(true);

		if (newMapBool) {
			options.setInitialSelectionValue("New Map 1");
		} else {
			options.setInitialSelectionValue(edit.getName());
		}
		final JDialog d = options.createDialog("New Map");

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int w = Integer.parseInt(widthTextField.getText());
				int h = Integer.parseInt(heightTextField.getText());
				if (newMapBool || edit == null) {
					edit = new Map(nameTextField.getText(), w, h);
				} else {
					edit.setSize(w, h);
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
		// add a window listener maybe to get values on close
	}
}
