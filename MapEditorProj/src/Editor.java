import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
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
		edit = new Map("New Map", 950 / Tile.TILE_SIZE, 722 / Tile.TILE_SIZE);
		add(new JScrollPane((JPanel) edit), "Center");
		// edit.setBounds(0, 0, 950, 722);
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
			propertiesDialog(true);
		}
		if (buttonLabel == "Save Map") {
			Controller.save(getName(), edit);
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
			widthTextField = new JTextField("20", 5);
			heightTextField = new JTextField("20", 5);
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
		// final JTextField nameTextField = new JTextField(15);
		// namePanel.add(nameLabel);
		// namePanel.add(nameTextField);

		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("Okay");
		final JButton cancelButton = new JButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		final JOptionPane options = new JOptionPane("Map:",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null,
				new JPanel[] { sizePanel, buttonPanel });
		options.setWantsInput(true);

		final JDialog d;
		if (newMapBool) {
			options.setInitialSelectionValue("New Map 1");
			d = options.createDialog("New Map");
		} else {
			options.setInitialSelectionValue(edit.getName());
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
				if (canWrite) {
					if (newMapBool || edit == null) {
						edit = new Map((String) (options.getInputValue()), w, h);
					} else {
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
