import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class ImageHandler {
	// it will hold all of the images for the tiles
	private static Hashtable<Integer, BufferedImage> tileImageList = new Hashtable<Integer, BufferedImage>();
	// it will hold all of the images for the decor
	private static Hashtable<Integer, BufferedImage> decorImageList = new Hashtable<Integer, BufferedImage>();

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
		if (decor) {
			directory = new File("./World/Decorations/");
		} else {
			directory = new File("./World/Tiles/");
		}
		//System.out.println(directory.listFiles().length);
		System.out.println(directory.getAbsolutePath());
		if (directory.isDirectory()) {
			BufferedImage img = null;
			for (File imageFile : directory.listFiles(filter)) {
				try {
					img = ImageIO.read(imageFile);
					String[] name = imageFile.getName().substring(1)
							.split("[()]", 3);
					Integer i = Integer.parseInt(name[0]);
					img = copyImageNamed(img, name[1], i);
					//System.out.println(new Integer(name[0]));
					if (decor) {
						decorImageList.put(new Integer(name[0]), img);
					} else {
						tileImageList.put(new Integer(name[0]), img);
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
			return copyImage(tileImageList.get(new Integer(index + 1)));
		} else {
			return copyImage(decorImageList.get(new Integer(index + 1)));
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

	public static Hashtable<Integer, BufferedImage> getTiles() {
		return tileImageList;
	}

	public static Hashtable<Integer, BufferedImage> getDecor() {
		return decorImageList;
	}

}