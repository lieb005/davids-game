import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile extends WorldObj
{
	int rotation = 0;
	
	public Tile(int code, int rotation) {
		super(code);
		this.rotation = rotation;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage img = ImageHandler.getImage(getCode(), true);
		img.createGraphics().transform(AffineTransform.getRotateInstance(Math.toRadians(getRotation())));
		return img;
	}
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
}