import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Decoration extends WorldObj {
	Rectangle colisionBox; // ADD COLISION BOX THINGY
	int width, height;

	public Decoration(int code) {
		super(code);
		width = getBufferedImage().getWidth();
		height = getBufferedImage().getHeight();

		colisionBox = new Rectangle(
				(xLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
						+ (WorldObj.TILE_SIZE / 2 * Map.scaleFactor)
						- (width / 2 * Map.scaleFactor),
				(yLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
						+ ((int) ((WorldObj.TILE_SIZE * Map.scaleFactor) + (.3 * height)))
						- (height * Map.scaleFactor), width * Map.scaleFactor,
				(int) ((height * Map.scaleFactor) - (.3 * height)));
	}

	@Override
	public BufferedImage getBufferedImage() {
		BufferedTile tile = ImageHandler.getBufferedTile(getCode(), false);
		BufferedImage img = tile.getBufferedImage();
		return img;
	}

	public Image getImage() {
		return ImageHandler.getBufferedTile(getCode(), false).getImage();
	}

	@Override
	public void draw(Graphics g) {
		if (getImage() == null) {
			BufferedImage img = getBufferedImage();

			g.drawImage(
					img,
					(xLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ (WorldObj.TILE_SIZE / 2 * Map.scaleFactor)
							- (img.getWidth() / 2 * Map.scaleFactor),

					(yLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ ((int) ((WorldObj.TILE_SIZE * Map.scaleFactor) * .9))
							- (img.getHeight() * Map.scaleFactor),

					img.getWidth() * Map.scaleFactor, img.getHeight()
							* Map.scaleFactor, null);
		} else {
			Image img = getImage();
			g.drawImage(
					img,
					(xLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ (WorldObj.TILE_SIZE / 2 * Map.scaleFactor)
							- (img.getWidth(null) / 2 * Map.scaleFactor),

					(yLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ ((int) ((WorldObj.TILE_SIZE * Map.scaleFactor) * .9))
							- (img.getHeight(null) * Map.scaleFactor),

					img.getWidth(null) * Map.scaleFactor, img.getHeight(null)
							* Map.scaleFactor, null);
		}
		if (Map.showColisions) {
			g.setColor(Color.red);
			g.drawRect(
					(xLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ (WorldObj.TILE_SIZE / 2 * Map.scaleFactor)
							- (width / 2 * Map.scaleFactor),
					(yLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
							+ ((int) ((WorldObj.TILE_SIZE * Map.scaleFactor) + (.3 * height)))
							- (height * Map.scaleFactor), width
							* Map.scaleFactor,
					(int) ((height * Map.scaleFactor) - (.3 * height)));
		}
	}

	public String toString() {
		return "" + getCode();
	}
}