import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {// responsible for common used methods for interacting
							// with stored files
	public static Controller controller = new Controller();

	public static void createNewMap(String name, int xCols, int yCols) {
		boolean overwrite = false;
		boolean taken = false;
		if (taken = checkFileExistance(name)) {
			String[] options = { "Yes", "No" };
			String msg = "Warning, there is already a map by that name, do you wish to overwrite it?";
			int choice = JOptionPane.showOptionDialog(null, msg, "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, options, 1);
			if (choice == 0) {
				overwrite = true;
			}
		}
		if (!taken || (taken && overwrite)) {

		}
	}

	public static void save(String name, Map map) {
		try {
			FileWriter write = new FileWriter(".Maps/" + name, false);
			BufferedWriter out = new BufferedWriter(write);
			for (ArrayList<WorldObj[]> row : map.getGrid()) {
				for (WorldObj[] t : row) {
					out.append(t[0].toString() + " " + t[1].toString() + "   ");
				}
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			System.out.println("Error, map couldn't be saved");
		}
	}

	public static void open(String name) {
		BufferedReader read = null;
		try {
			Map map = new Map(name, 0, 0);

			read = new BufferedReader(new FileReader(new File(".Maps/" + name)));
			int y = 0;
			while (read.ready()) {
				int x = 0;
				for (String grid : read.readLine().split("   [ ]*")) {
					String[] ids = grid.split(" ");
					Tile tile = new Tile(Integer.parseInt(ids[0]),
							Integer.parseInt(ids[1]));
					Decoration decor = new Decoration(Integer.parseInt(ids[2]));
					WorldObj obj[] = new WorldObj[] { tile, decor };
					if (map.getGrid().size() <= x) {
						map.getGrid().add(new ArrayList<WorldObj[]>());
					}
					// add cell
					map.getGrid().get(x).add(y, obj);
				}
			}
		} catch (Exception e) {
			System.out.println("Error, map couldn't be saved");
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
	public static int getNumObjs(boolean tile){
		File dir;
		if(tile){
			dir = new File("./World/Tiles");
		}else{
			dir = new File("./World/Decorations");
		}
		return dir.list().length - 1;
	}
}
