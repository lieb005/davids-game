import java.awt.image.BufferedImage;

public class Decoration extends WorldObj
{
	public Decoration(int code) {
		super(code);
		
	}
	int rotation = 0;
	
	@Override
	public BufferedImage getImage() {
		BufferedImage img = ImageHandler.getImage(getCode(), false);
		return img;
	}
}