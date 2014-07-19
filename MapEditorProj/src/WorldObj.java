import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class WorldObj extends JButton{//world objects are what will graphically compose
									  //the game world (tiles for ground and decorations)
	int des;
	BufferedImage img;
	String objName;
	String imgType;
	boolean colorChange = false;
	boolean synchronizable;
	boolean selected;
	boolean onGrid;
	boolean type;// type: true = tile, false = decoration
	
	public BufferedImage setUp(int designation, boolean t){//t = tile, false = decor
		des = designation;
		String path;
		if(t){
			path = "World/Tiles";
		}else{
			path = "World/Decorations";
		}
		File dir = new File(path);
		String[] list = dir.list();
		int index = -1;
		for(int name = 0; name < list.length ; name++){
			int subLen = -1;
			int subStart = -1;
			for(int i = 0; i < list.length; i++){//get index number of file
				if(list[name].charAt(i) == '('){
					subStart = i;
				}
				if(list[name].charAt(i) == ')'){
					subLen = i;
				}
				if(subStart != -1 && subLen != -1){
					break;
				}
			}
			int num = java.lang.Integer.parseInt(list[name].substring(subStart + 1, subLen));
			if(num == designation){//compare if designation number matches number of file
				index = name;
				
				//set roll-over text
				objName = list[name].substring(subLen + 1, list[name].length() - 4);
				imgType = list[name].substring(list[name].length() - 3);
				this.setToolTipText(objName);
				break;
			}
		}
		//get the image of the tile
		dir = new File(path + "/" + list[index]);
		BufferedImage image = null;
		try {
			if(!(imgType.endsWith("gif"))){//non moving jbutton
				synchronizable = false;
				image = ImageIO.read(dir); 
				Image temp = Toolkit.getDefaultToolkit().createImage(image.getSource());//get icon
				this.setIcon(new ImageIcon(temp));//add icon
			}else{//moving jbutton
				synchronizable = true;
				Image move = Toolkit.getDefaultToolkit().createImage(path + "/" + list[index]);
				this.setIcon(new ImageIcon(move));
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't find image");
		}
		
		return image;
	}
	
	public void setType(boolean type){
		this.type = type;
	}
}
