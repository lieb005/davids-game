import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {// responsible for common used methods for interacting
							// with stored files
	public static Controller controller = new Controller();

	public static void save(Map map) {
		try {
			String suffix ="";
			if(!map.getName().endsWith(".txt")){
				suffix = ".txt";
			}
			FileWriter write = new FileWriter("./Maps/" + map.getName() + suffix, false);
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
		String suffix ="";
		Map map;
		if(name.endsWith(".txt")){
			map = new Map(name.substring(0, name.length() - 4), 0, 0);
		}else{
			map = new Map(name, 0, 0);
		}
		try {
			read = new BufferedReader(new FileReader(new File("./Maps/" + name)));
			int y = 0;
			while (read.ready()) {
				map.grid.add(new ArrayList<WorldObj[]>());
				for (String grid : read.readLine().split("   [ ]*")) {
					String[] ids = grid.split(" ");
					Tile tile = new Tile(Integer.parseInt(ids[0]),
							Integer.parseInt(ids[1]));
					Decoration decor = new Decoration(Integer.parseInt(ids[2]));
					WorldObj obj[] = new WorldObj[] { tile, decor };
					
					map.grid.get(y).add(obj);
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
