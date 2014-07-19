import java.util.ArrayList;

public class Map {
	int x, y;
	static String mapName;
	static ArrayList<ArrayList<Tile>> grid;
	public Map(int x, int y){
		this.x = x;
		this.y = y;
		grid = new ArrayList<ArrayList<Tile>>();
		makeGrid(this.x, this.y);
	}
	
	public void makeGrid(int x, int y){
		int Yloc = 0;//bottom of menu bar
		int Xloc = 0;
		for(int yT = 0; yT < y; yT++){
			grid.add(new ArrayList<Tile>());
			for(int xT = 0; xT < x; xT++){
				Tile t = new Tile(-1);
				t.xLoc = xT;
				t.yLoc = yT;
				t.giveListener(true);
				t.setBounds(Xloc, Yloc, Tile.size, Tile.size);
				t.setVisible(true);
				grid.get(yT).add(t);
				Map_Edit.editSpace.add(grid.get(yT).get(xT));
				Xloc += 25;
			}
			Yloc += 25;
			Xloc = 0;
		}
	}
	
	public static void sync(){
		for(ArrayList<Tile> row : grid){
			for(Tile t : row){
				if(t.synchronizable){
					
				}
			}
		}
	}
	
	public void addCol(){
		x += 1;
		for(ArrayList<Tile> row: grid){
			row.add(new Tile(-1));
		}
	}
	
	public void addRow(){
		y += 1;
		ArrayList<Tile> temp = new ArrayList<Tile>();
		for(int i = 0; i < x; i++){
			temp.add(new Tile(-1));
		}
		grid.add(temp);
	}
}
