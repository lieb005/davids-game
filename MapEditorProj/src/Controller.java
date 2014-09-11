import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {// responsible for common used methods for interacting
							// with stored files
	public static Controller controller = new Controller();

	public static void save(Map map) {
		try {
			String suffix = "";
			if (!map.getName().endsWith(".txt")) {
				suffix = ".txt";
			}
			Hashtable<Integer, Hashtable<Integer, WorldObj[]>> grid = map
					.getGrid();
			FileWriter write = new FileWriter("./Maps/" + map.getName()
					+ suffix, false);
			BufferedWriter out = new BufferedWriter(write);
			for (int i = 0; i < map.getGrid().size(); i++) {
				for (int j = 0; j < map.getGrid().get(i).size(); j++) {
					out.append(grid.get(i).get(j)[0].toString() + " "
							+ grid.get(i).get(j)[1].toString() + "   ");
				}
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error, map couldn't be saved");
		}
	}

	public static void open(String path) {
		BufferedReader read = null;
		File mapData = new File(path);
		Map map = new Map(mapData.getName().substring(0, mapData.getName().length() - 4), 0, 0);
		try {
			read = new BufferedReader(
					new FileReader(mapData));
			int y = 0;
			while (read.ready()) {
				int x = 0;
				map.grid.put(y, new Hashtable<Integer, WorldObj[]>());
				for (String grid : read.readLine().split("   [ ]*")) {
					String[] ids = grid.split(" ");
					Tile tile = new Tile(Integer.parseInt(ids[0]),
							Integer.parseInt(ids[1]));
					tile.setXYLoc(y, x);
					Decoration decor = new Decoration(Integer.parseInt(ids[2]));
					decor.setXYLoc(y, x);
					WorldObj obj[] = new WorldObj[] { tile, decor };

					map.grid.get(y).put(x, obj);
					x++;
				}
				y++;
			}
			Editor.setMap(map);
		} catch (Exception e) {
			System.out.println("Error, map couldn't be opened");
		}
		if (read != null) {
			try {
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean checkFileExistance(String name) { // true: file exists
		return new File("./Maps/", name).exists()
				|| new File("./World/Tiles/", name).exists()
				|| new File("./World/Decorations/", name).exists();
	}

	public static int getNumObjs(boolean tile) {
		File dir;
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
		if (tile) {
			dir = new File("./World/Tiles");
		} else {
			dir = new File("./World/Decorations");
		}
		return dir.listFiles(filter).length - 1;
	}

	public static void rename(String name) {
		File map = new File("./Maps/" + Editor.edit.getName() + ".txt");
		File newName = new File("./Maps/" + name + ".txt");
		if (map.exists()) {
			map.renameTo(newName);
		}
	}

	public static void deleteMap(String name) {
		File map = new File("./Maps/" + name + ".txt");
		map.delete();
		Editor.edit.setName("No Map Open");
		Editor.edit.setGridSize(0, 0);
	}

	public static void importTile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
				"png", "jpg", "gif", "jpeg", "bmp");
		chooser.setFileFilter(filter);
		chooser.setFileHidingEnabled(true);
		int returnVal = chooser.showOpenDialog(Editor.e);
		File image = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			image = chooser.getSelectedFile();
			BufferedImage actualImage = null;
			try {
				actualImage = ImageIO.read(image);
			} catch (IOException e) {
			}
			File tile;
			if (actualImage.getWidth() == 25 && actualImage.getWidth() == 25) {
				tile = new File("./World/Tiles/" + "(" + (getNumObjs(true))
						+ ")" + image.getName());
			} else {
				tile = new File("./World/Decorations/" + "("
						+ (getNumObjs(false)) + ")" + image.getName());
			}
			try {
				InputStream in = new FileInputStream(image);
				OutputStream out = new FileOutputStream(tile);

				// Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Images could not be imported");
			}
			Editor.refreshImages();
		}
	}
}
