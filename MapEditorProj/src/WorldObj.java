import java.awt.image.BufferedImage;

// use this to override later.  Too much shared code.
public abstract class WorldObj{
	public static final int TILE_SIZE = 25;
	public static final int ROTATION0 = 0;
	public static final int ROTATION1 = 90;
	public static final int ROTATION2 = 180;
	public static final int ROTATION4 = 360;
	
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
