import java.awt.image.BufferedImage;

// use this to override later.  Too much shared code.
public abstract class WorldObj {
	public static final int TILE_SIZE = 100;
	
	
	private int code;

	public WorldObj(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	abstract public BufferedImage getImage();

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
