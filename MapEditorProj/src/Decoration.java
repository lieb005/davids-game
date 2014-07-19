
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

@SuppressWarnings("serial")
public class Decoration extends WorldObj implements ActionListener {

	public Decoration(int des) {
		img = setUp(des, false);
		this.des = des;
	}

	public void giveListener(boolean onGrid) {
		this.onGrid = onGrid;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this) && onGrid == false) {
			// add red border when tile is clicked & remove border from previous
			
			TileLabel.blankBorders();
			Map_Edit.onTile = false;
			Map_Edit.tileChosen = des;
			javax.swing.border.Border b = BorderFactory.createLineBorder(Color.RED);
			this.setBorder(b);
			System.out.println(objName);
		}

	}
}
