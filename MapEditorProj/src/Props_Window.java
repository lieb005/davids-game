import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Props_Window extends JFrame implements ActionListener{
	//this class is responsible for opening window to change map propertied (File > New Map)
	JLabel numX;
	JLabel numY;
	JLabel mapName;
	JTextField name;
	JTextField x;
	JTextField y;
	JButton ok;
	JButton cancel;
	GridBagConstraints c;
	
	int xTiles;
	int yTiles;
	static String nameOfMap;
	
	public Props_Window(String title){
		this.setName(title);
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		this.setBounds(500, 0, 300, 200);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		numX = new JLabel("X Tiles:");
		numY = new JLabel("Y Tiles:");
		mapName = new JLabel("Map Name:");
		x = new JTextField();
		y = new JTextField();
		name = new JTextField();
		ok = new JButton("Create");
		ok.addActionListener(this);
		
		this.add(numX, c);
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 10.0;
		this.add(x, c);
		c.gridx = 2;
		this.add(numY, c);
		c.gridx = 3;
		c.weightx = 0;
		this.add(y, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		this.add(mapName, c);
		c.gridx = 2;
		c.weightx = .2;
		this.add(name, c);
		
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 3;
		c.weightx = 0;
		this.add(ok, c);
		this.setResizable(false);
	}
	
	public static Map makeMap(int x, int y){
		Editor.window.setTitle("Map Creator - " + nameOfMap.substring(0, nameOfMap.length() -4));
		Map m = new Map(x, y);
		Map.mapName = nameOfMap;
		Map_Edit.setUpEditSpace(new Dimension((x * Tile.size), (y * Tile.size)));
		return m;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			boolean write = true;
			// get nums and name
			if (x.getText() != null && y.getText() != null
					&& name.getText() != null) {
				try {
					xTiles = java.lang.Integer.parseInt(x.getText());
					yTiles = java.lang.Integer.parseInt(y.getText());
					if(xTiles > 200 || yTiles > 200){
						JOptionPane.showMessageDialog(null,
								"Maps can be no larger than 200 X 200");
						write = false;
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Only numbers can be included in X and Y");
					write = false;
				}
				nameOfMap = name.getText();
				
				if(!(nameOfMap.substring(nameOfMap.length() - 4, nameOfMap.length()).equals(".txt"))){
					nameOfMap += ".txt";
				}
				if(write){
					//Controller.createNewMap(nameOfMap, xTiles, yTiles);
					Map_Edit.curMap = makeMap(xTiles, yTiles);
					this.setVisible(false);
				}
			}else{
				JOptionPane.showMessageDialog(null,
						"Please fill in all the fields");
			}
		}

	}
}
