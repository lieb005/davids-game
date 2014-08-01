import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Tile extends WorldObj {
	int rotation = 0;

	public Tile(int code, int rotation) {
		super(code);
		this.rotation = rotation;
	}

	@Override
	public BufferedImage getImage() {
		BufferedTile tile = ImageHandler.getBufferedTile(getCode(), true);
		BufferedImage img = tile.getBufferedImage();
		img.createGraphics().transform(AffineTransform.getRotateInstance(Math.toRadians(getRotation())));
		img.getScaledInstance(TILE_SIZE * Map.scaleFactor, TILE_SIZE * Map.scaleFactor, img.SCALE_DEFAULT);
		return img;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
}