import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class BufferedTile{
	private BufferedImage img = null;
	private ImageIcon icon = null;
	boolean isTile;
	private int index;
	private String name;

	public BufferedTile(File imageFile){
		if(imageFile.getName().toLowerCase().endsWith(".gif")){
			icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(imageFile.getAbsolutePath()));
		}
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println(imageFile.getName() + "Failed to load");
		}
		String[] data = imageFile.getName().substring(1).split("[()]", 3);
		index = Integer.parseInt(data[0]);
		name = data[1].substring(0, data[1].length() - 4);
	}
	
	public void setType(boolean tileType){
		isTile = tileType;
	}
	
	public void setBufferedImage(BufferedImage newImage){
		img = newImage;
	}
	
	public void setIcon(ImageIcon newIcon){
		icon = newIcon;
	}
	
	public BufferedImage getBufferedImage(){
		return img;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	
	public String getName(){
		return name;
	}
	
	public int getIndex(){
		return index;
	}
}
