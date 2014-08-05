import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

// use this to override later.  Too much shared code.
public abstract class WorldObj{
	public static final int TILE_SIZE = 25;
	public static final int ROTATION0 = 0;
	public static final int ROTATION1 = 90;
	public static final int ROTATION2 = 180;
	public static final int ROTATION3 = 270;
	
	private int code;
	int xLoc = 0; 
	int yLoc = 0;

	public WorldObj(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	abstract public BufferedImage getBufferedImage();
	abstract public Image getImage();
	abstract public int getRotation();
	abstract public void draw(Graphics g);
	
	
	public void setXYLoc(int x, int y){
		xLoc = x;
		yLoc = y;
	}

	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "";
	}
	public String getDescription()
	{
		return "";
	}
}
