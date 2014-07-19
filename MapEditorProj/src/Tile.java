import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

@SuppressWarnings("serial")
public class Tile extends WorldObj implements ActionListener {
	// tile size in pixels
	final static int size = 25;
	int xLoc;
	int yLoc;
	static int rotation = 0;
	static Decoration dec = null;

	public Tile(int des) {
		img = setUp(des, true);
	}
	
	public String toString(){//as a string: TileNum(Decor, rotation, rgb)~
		if(dec != null || rotation > 0 || colorChange){
			String tile = des + "(";
			if(dec != null){//decor
				tile += dec.des + ",";
			}else{
				tile += "n,";
			}if(rotation > 0){//rotation
				tile += rotation +",";
			}else{
				tile += "n,";
			}if(colorChange == false){
				tile += "n,";
			}else{
				//ADD RGB VALUES HERE!
			}
			tile += ") ";
			return tile;
		}else{
			return des+" ";
		}
	}
	public static void addDecor(Decoration d){
		dec = d;
	}
	
	public static void setRotation(int r){
		rotation = r;
	}

	public void giveListener(boolean onGrid) {
		this.onGrid = onGrid;
		this.addActionListener(this);
		if(this.onGrid){
			this.setToolTipText(objName+ " " + (xLoc + 1) +", " + (yLoc + 1));
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this) && onGrid == false) {
			// add red border when tile is clicked & remove border from previous

			TileLabel.blankBorders();
			Map_Edit.onTile = true;
			Map_Edit.tileChosen = des;
			selected = true;
			javax.swing.border.Border b = BorderFactory.createLineBorder(Color.RED);
			this.setBorder(b);
			//System.out.println(objName);
		}else if(e.getSource().equals(this) && onGrid && Map_Edit.onTile){
			//add something here to change this to another Tile
			img = setUp(Map_Edit.tileChosen, Map_Edit.onTile);
			Map.grid.get(yLoc).set(xLoc, this);
			this.setToolTipText(objName+ " " + (xLoc + 1) +", " + (yLoc + 1));
			if(synchronizable){
				//Map.sync();
			}
		}
	}
}
