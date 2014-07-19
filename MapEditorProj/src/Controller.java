import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {//responsible for common used methods for interacting with stored files
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
			try {
				FileWriter write = new FileWriter("Maps/" + name, false);
				PrintWriter out = new PrintWriter(write);
				
				for(int y = 0; y < yCols; y++){
					for(int x = 0; x < xCols; x++){
						out.print("-1 ");
					}
					out.println("!");
				}
				out.close();
			} catch (IOException e) {
				System.out.println("Couldn't make map file");
			}
		}
	}
	
	public static void save(){
		try{
			FileWriter write = new FileWriter("Maps/" + Map.mapName, false);
			PrintWriter out = new PrintWriter(write);
			for(ArrayList<Tile> row : Map.grid){
				for(Tile t : row){
					out.print(t.toString());
				}
				out.println("!");
			}
			out.close();
		}catch(Exception e){
			System.out.println("Error, map couldn't be saved");
		}
	}

	public static boolean checkFileExistance(String name) { // true: file exists
		File dir;
		String[] list;

		if (name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".txt")) {
			//maps saved as a .txt file


			dir = new File("Maps");
			list = dir.list();
			for (String fileName : list) {//search in folder Maps
				if (fileName.equals(name)) {
					return true;
				}
			}
			return false;
		} else {
			// for tiles


			dir = new File("World/Tiles");
			list = dir.list();
			for (String fileName : list) {
				if (fileName.equals(name)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static int getNumObj(boolean tile){
		File dir = null;
		String[] list;
		if(tile){
			dir = new File("World/Tiles");
		}else{
			dir = new File("World/Decorations");
		}
		list = dir.list();
		return list.length -1;
	}
}
