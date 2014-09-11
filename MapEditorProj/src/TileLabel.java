import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TileLabel extends JPanel {
	int lastX = 0;
	int lastY = 70;
	
	int startX = 0;
	int startY = 70;
	int maxHeight = 0;
	
	JLabel lab;
	public static ArrayList<JButton> tiles;
	public static ArrayList<JButton> decor;
	public static boolean onTile = true;
	public static int selectedObjectIndex = -1;

	public TileLabel(boolean t) {// if true tile panel, if false, decorations
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setForeground(Color.white);

		if (t) {
			tiles = new ArrayList<JButton>();
			lab = new JLabel("Tiles");
			lab.setBounds(70, 30, 100, 37);
			try {
				getTiles();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Some Tiles failed to load! Program may not run "
						+ "as intended", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
			decor = new ArrayList<JButton>();
			lab = new JLabel("Decorations");
			lab.setBounds(30, 30, 300, 37);
			try {
				getDecor();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Some Decorations failed to load! Program may not"
						+ " run as intended", "Error",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		lab.setFont(new Font("Times", Font.PLAIN, 35));
		lab.setForeground(Color.black);
		this.add(lab);
	}

	public void getTiles(){
		Hashtable<Integer, BufferedTile> tileImages = ImageHandler.getTiles();
		for (int i = tiles.size(); i < Controller.getNumObjs(true); i++) {
			JButton tile;
			if (tileImages.get(i).getIcon() == null) {
				tile = new JButton(new ImageIcon(Toolkit.getDefaultToolkit()
						.createImage(
								tileImages.get(i).getBufferedImage()
										.getSource())));
			} else {
				tile = new JButton(tileImages.get(i).getIcon());
			}
			
			if (lastX + 10 + WorldObj.TILE_SIZE <= 216) {
				if (i > 0) {
					lastX += 10 + WorldObj.TILE_SIZE;
				} else {
					lastX += 10;
				}
			} else {
				lastX = 10;
				lastY += (10 + WorldObj.TILE_SIZE);
			}
			tile.setBounds(lastX, lastY, WorldObj.TILE_SIZE, WorldObj.TILE_SIZE);
			tile.setToolTipText(tileImages.get(i).getName());
			tile = addActionListener(i, tile, true);
			tiles.add(tile);
			this.add(tiles.get(i));
		}
		this.setPreferredSize(new Dimension(216, lastY));
	}

	public void getDecor() {
		int prevW = 0;
		Hashtable<Integer, BufferedTile> decorImages = ImageHandler.getDecor();
		for (int i = decor.size(); i < Controller.getNumObjs(false); i++) {
			JButton decoration;
			int w = decorImages.get(i).getBufferedImage().getWidth();
			int h = decorImages.get(i).getBufferedImage().getHeight();
			if(decorImages.get(i).getIcon() == null){
				Image img = Toolkit.getDefaultToolkit().createImage(
						decorImages.get(i).getBufferedImage().getSource());
				if(img.getWidth(null) > 216){
					img = img.getScaledInstance(216, img.getHeight(null) * 216 / img.getWidth(null),
							Image.SCALE_SMOOTH);
					w = img.getWidth(null);
					h = img.getHeight(null);
				}
			decoration = new JButton(new ImageIcon(img));
			}else{
				decoration = new JButton(decorImages.get(i).getIcon());
			}
			
			if (h > maxHeight && startX + 10 + prevW + w <= 216) {
				maxHeight = h;
			}
			if (startX + 10 + prevW + w <= 216) {
				if (i > 0) {
					startX += 10 + prevW;
				} else {
					startX += 10;
					prevW = w;
				}
			} else {
				startX = 10;
				startY += (10 + maxHeight);
				maxHeight = h;
			}
			decoration.setBounds(startX, startY, w, h);
			decoration.setToolTipText(decorImages.get(i).getName());
			prevW = w;
			decoration = addActionListener(i, decoration, false);
			decor.add(decoration);
			this.add(decor.get(i));
		}
		this.setPreferredSize(new Dimension(216, startY + maxHeight + 10));
	}

	public static void blankBorders() {
		for (JButton t : TileLabel.tiles) {
			t.setBorder(null);
		}
		for (JButton d : TileLabel.decor) {
			d.setBorder(null);
		}
	}
	
	//this is kinda roundabout, but I couldn't find another way. It works though!
	public JButton addActionListener(final int index, JButton obj, final boolean tile){
		final LineBorder b = new LineBorder(Color.RED);
		obj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//fix this
				if(tile){
					blankBorders();
					onTile = true;
					if(index != 0){
						selectedObjectIndex = index;
					}else{
						selectedObjectIndex = -1;
					}
					
					tiles.get(index).setBorder(b);
				}else{
					blankBorders();
					onTile = false;
					if(index != 0){
						selectedObjectIndex = index;
					}else{
						selectedObjectIndex = -1;
					}
					decor.get(index).setBorder(b);
				}
			}
		});
		return obj;
	}
}
