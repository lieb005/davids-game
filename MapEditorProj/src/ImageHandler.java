import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;

public class ImageHandler {
	// it will hold all of the images for the tiles
	private static Hashtable<Integer, BufferedTile> tileImageList = new Hashtable<Integer, BufferedTile>();
	// it will hold all of the images for the decor
	private static Hashtable<Integer, BufferedTile> decorImageList = new Hashtable<Integer, BufferedTile>();

	// used to load all the images
	public static int loadImages(boolean decor) {
		if (decor == true) {
			// load decor too
			loadImages(false);
		}
		int failedCount = 0;
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// filter out non-images
				return pathname.getName().toLowerCase().endsWith(".bmp")
						|| pathname.getName().toLowerCase().endsWith(".gif")
						|| pathname.getName().toLowerCase().endsWith(".png")
						|| pathname.getName().toLowerCase().endsWith(".jpeg")
						|| pathname.getName().toLowerCase().endsWith(".jpg");
			}
		};
		File directory;
		if (decor == false) {
			directory = new File("./World/Tiles/");
		} else {
			directory = new File("./World/Decorations/");
		}
		//System.out.println(directory.listFiles().length);
		System.out.println(directory.getAbsolutePath());
		if (directory.isDirectory()) {
			BufferedTile img = null;
			for (File imageFile : directory.listFiles(filter)) {
				try {
					img = new BufferedTile(imageFile);
					img.setBufferedImage(copyImageNamed(img.getBufferedImage(), img.getName(), img.getIndex()));
					//System.out.println(new Integer(name[0]));
					if (decor == false) {
						img.setType(true);
						tileImageList.put(img.getIndex(), img);
					} else {
						img.setType(false);
						decorImageList.put(img.getIndex(), img);
					}
				} catch (Exception e) {
					e.printStackTrace();
					failedCount++;
				}
				if (img == null) {
					failedCount++;
					continue;
				}
			}
		} else {
			System.err.println("Files not found.");
		}

		//System.out.println(failedCount + " images failed to load.");
		return failedCount;
	}

	// used to get images from the list
	// We have to copy them so that if we use the graphics of one, it doesn't
	// alter the actual image
	// we need to add one because they start at -1
	public static BufferedImage getImage(int index, boolean tile)
			throws ArrayIndexOutOfBoundsException {
		if (tile) {
			return copyImage(tileImageList.get(new Integer(index + 1)).getBufferedImage());
		} else {
			return copyImage(decorImageList.get(new Integer(index + 1)).getBufferedImage());
		}
	}

	// A roundabout way to copy the image, but we can't do it in a non
	// roundabout way
	public static BufferedImage copyImage(BufferedImage source) {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("name", source.getProperty("name"));
		properties.put("index", source.getProperty("index"));

		ColorModel cm = source.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = source.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
	}

	public static BufferedImage copyImageNamed(BufferedImage source,
			String name, int index) {
		Hashtable<String, Object> properties = new Hashtable<String, Object>();
		properties.put("name", name);
		properties.put("index", new Integer(index));
		ColorModel cm = source.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = source.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
	}
	
	public static BufferedTile getBufferedTile(int code, boolean tile){
		if(tile){
			return tileImageList.get(code);
		}else{
			return decorImageList.get(code);
		}
	}

	public static Hashtable<Integer, BufferedTile> getTiles() {
		return tileImageList;
	}

	public static Hashtable<Integer, BufferedTile> getDecor() {
		return decorImageList;
	}

}