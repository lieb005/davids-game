import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile extends WorldObj {
	int rotation = 0;
	boolean isCorner = false;
	public Tile(int code, int rotation) {
		super(code);
		this.rotation = rotation;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return ImageHandler.getBufferedTile(getCode(), true).getBufferedImage();
	}
	
	public Image getImage(){
		Image img = ImageHandler.getBufferedTile(getCode(), true).getImage();
		return img;
	}
	
	@SuppressWarnings("static-access")
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform identity = new AffineTransform();
		AffineTransform transform = new AffineTransform();
		transform.setTransform(identity);
		transform = transform.getRotateInstance((double)Math.toRadians(getRotation()),
				xLoc * WorldObj.TILE_SIZE * Map.scaleFactor + WorldObj.TILE_SIZE * Map.scaleFactor / 2,
				yLoc * WorldObj.TILE_SIZE * Map.scaleFactor + WorldObj.TILE_SIZE * Map.scaleFactor / 2);
		identity = g2d.getTransform();
		g2d.setTransform(transform);
		if(getImage() == null){
			g2d.drawImage(getBufferedImage(), xLoc * WorldObj.TILE_SIZE * Map.scaleFactor, yLoc
					* WorldObj.TILE_SIZE * Map.scaleFactor, WorldObj.TILE_SIZE
					* Map.scaleFactor, WorldObj.TILE_SIZE * Map.scaleFactor, null);
		}else{
			g2d.drawImage(getImage(), xLoc * WorldObj.TILE_SIZE * Map.scaleFactor, yLoc
					* WorldObj.TILE_SIZE * Map.scaleFactor, WorldObj.TILE_SIZE
					* Map.scaleFactor, WorldObj.TILE_SIZE * Map.scaleFactor, null);
		}
		g2d.setTransform(identity);
	}
	
	public BufferedImage makeCorner(){
		BufferedImage img = ImageHandler.getBufferedTile(getCode(), true).getBufferedImage();
		BufferedImage rotated = img;
		for(int i = 0; i < img.getWidth(); i++)
	        for(int j = 0; j < img.getHeight(); j++)
	            rotated.setRGB(img.getHeight()-1-j, img.getWidth()-1-i, img.getRGB(i, j));
		isCorner = true;
		return rotated;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public String toString() {
		return getCode() + " " + getRotation();
	}
}