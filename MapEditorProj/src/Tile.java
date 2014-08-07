import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile extends WorldObj {
	int rotation = 0;
	int corners = 0;
	public Tile(int code, int rotation, int corners) {
		super(code);
		this.rotation = getRotationValue(rotation);
		this.corners = corners;
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
		AffineTransform transform = new AffineTransform();
		transform.setTransform(identity);
		transform = AffineTransform.getRotateInstance((double)Math.toRadians(getRotationValue(getRotation())),
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
		return rotated;
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
	
	public void addCorner(){//measured 0, 1, 2, 3. O is default tile
		if(corners < 3){
			corners++;
		}else{
			corners = 0;
		}
		System.out.println(xLoc + ", " + yLoc +" has corners: " + corners);
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public int getCorners(){
		return corners;
	}
	
	public String toString() {
		return getCode() + " " + getRotation() + " " + getCorners();
	}
}