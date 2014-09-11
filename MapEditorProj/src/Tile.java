import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile extends WorldObj {
	int rotation = 0;
	public Tile(int code, int rotation) {
		super(code);
		this.rotation = getRotationValue(rotation);
	}

	@Override
	public BufferedImage getBufferedImage() {
		return ImageHandler.getBufferedTile(getCode(), true).getBufferedImage();
	}
	
	public Image getImage(){
		Image img = ImageHandler.getBufferedTile(getCode(), true).getImage();
		return img;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform identity = new AffineTransform();
		if(rotation > 0){
			AffineTransform transform = new AffineTransform();
			transform.setTransform(identity);
			transform = AffineTransform.getRotateInstance((double)Math.toRadians(getRotationValue(getRotation())),
					xLoc * WorldObj.TILE_SIZE * Map.scaleFactor + WorldObj.TILE_SIZE * Map.scaleFactor / 2,
					yLoc * WorldObj.TILE_SIZE * Map.scaleFactor + WorldObj.TILE_SIZE * Map.scaleFactor / 2);
			identity = g2d.getTransform();
			g2d.setTransform(transform);
		}
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

	public int getRotation() {
		if(rotation == WorldObj.ROTATION0){
			return 0;
		}else if(rotation == WorldObj.ROTATION1){
			return 1;
		}else if(rotation == WorldObj.ROTATION2){
			return 2;
		}else{
			return 3;
		}
	}
	
	public int getRotationValue(int index){
		if(index == 0){
			return WorldObj.ROTATION0;
		}else if(index == 1){
			return WorldObj.ROTATION1;
		}else if(index == 2){
			return WorldObj.ROTATION2;
		}else{
			return WorldObj.ROTATION3;
		}
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public String toString() {
		return getCode() + " " + getRotation();
	}
}