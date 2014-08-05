import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Decoration extends WorldObj{
	
	public Decoration(int code) {
		super(code);
		
	}
	int rotation = 0;
	
	@Override
	public BufferedImage getBufferedImage() {
		BufferedTile tile = ImageHandler.getBufferedTile(getCode(), false);
		BufferedImage img = tile.getBufferedImage();
		return img;
	}
	public Image getImage(){
		return ImageHandler.getBufferedTile(getCode(), false).getImage();
	}
	@Override
	public int getRotation() {
		return 0;
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		BufferedImage img = getBufferedImage();

		g.drawImage(img, (xLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
						+ (WorldObj.TILE_SIZE / 2 * Map.scaleFactor)
						- (img.getWidth() / 2 * Map.scaleFactor),

				(yLoc * WorldObj.TILE_SIZE * Map.scaleFactor)
						+ ((int) ((WorldObj.TILE_SIZE * Map.scaleFactor) * .9))
						- (img.getHeight() * Map.scaleFactor),

				img.getWidth() * Map.scaleFactor, img.getHeight() * Map.scaleFactor, null);
	}
	
	public String toString() {
		return "" + getCode();
	}
}