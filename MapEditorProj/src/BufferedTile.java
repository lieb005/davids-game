import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class BufferedTile{
	private BufferedImage buffImg = null;
	private ImageIcon icon = null;
	private Image image = null;
	boolean isTile;
	private int index;
	private String name;

	public BufferedTile(File imageFile){
		if(imageFile.getName().toLowerCase().endsWith(".gif")){
			image = Toolkit.getDefaultToolkit().createImage(imageFile.getAbsolutePath());
			icon = new ImageIcon(image);
		}
		try {
			buffImg = ImageIO.read(imageFile);
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
		buffImg = newImage;
	}
	
	public void setIcon(ImageIcon newIcon){
		icon = newIcon;
	}
	
	public BufferedImage getBufferedImage(){
		return buffImg;
	}
	
	public Image getImage(){
		return image;
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
