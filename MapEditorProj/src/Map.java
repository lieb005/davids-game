import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// we will actually put the grid on this
public class Map extends JPanel implements MouseListener, MouseMotionListener, Runnable {
	public static final Color FOREGROUND_GRID_COLOR = Color.BLACK;
	public static final Color GRID_GRID_COLOR = Color.BLACK;
	public static final Color BACKGROUND_GRID_COLOR = Color.WHITE;
	static int scaleFactor = 1; //MAKE THIS A DOUBLE

	public boolean showGrid = true;
	public static boolean showColisions = false;

	private Dimension lastTile = null;

	Grid grid = new Grid(0, 0);

	private Thread updateDisplay;

	public Map(String name, int w, int h) {
		super(true);
		this.setPreferredSize(new Dimension(w * WorldObj.TILE_SIZE * scaleFactor,
				h* WorldObj.TILE_SIZE * scaleFactor));
		Editor.setTitle(name);
		setSize(w * Tile.TILE_SIZE, h * Tile.TILE_SIZE);
		setName(name);
		grid.resize(w, h);
		addMouseListener(this);
		addMouseMotionListener(this);
		updateDisplay = new Thread(this);
		this.setVisible(true);
		updateDisplay.start();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				updateDisplay.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {// renders only the content visible on the screen
		super.paintComponent(g);
		drawTiles(g);
		if (showGrid) {
			drawBorders(g);
		}
		g.dispose();
	}

	public void setScale(int newScale) {
		scaleFactor = newScale;
		this.setPreferredSize(new Dimension(getGridWidth() * WorldObj.TILE_SIZE * scaleFactor,
				getGridHeight()* WorldObj.TILE_SIZE * scaleFactor));
		repaint();
	}

	public void setGridVisability(boolean g) {
		showGrid = g;
		repaint();
	}
	
	public static void setColisionVisibility(){
		showColisions = !showColisions;
	}

	public Dimension getTileXY(Point mouse) {// returns the XY coordinates of
		// the clicked tile
		Dimension loc = new Dimension(
				((int) (mouse.getX()) + (WorldObj.TILE_SIZE * scaleFactor) - 1)
						/ (WorldObj.TILE_SIZE * scaleFactor) - 1,
				((int) (mouse.getY()) + (WorldObj.TILE_SIZE * scaleFactor) - 1)
						/ (WorldObj.TILE_SIZE * scaleFactor) - 1);

		if ((int) loc.getWidth() <= grid.size() - 1
				&& (int) loc.getHeight() <= grid.get(0).size() - 1) {
			return loc;
		} else {
			loc = null;
			return loc;
		}
	}

	private void drawBorders(Graphics g) {
		g.setColor(GRID_GRID_COLOR);
		for (int x = 0; x < getGridWidth(); x++) {
			for (int y = 0; y < getGridHeight(); y++) {// with scaling
				g.drawRect(x * Tile.TILE_SIZE * scaleFactor, y * Tile.TILE_SIZE
						* scaleFactor, Tile.TILE_SIZE * scaleFactor,
						Tile.TILE_SIZE * scaleFactor);
			}
		}
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	private void drawTiles(Graphics g) {
		g.setColor(BACKGROUND_GRID_COLOR);
		//drawing
		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(0).size(); y++) {
				if(grid.get(x).get(y)[0].getCode() > 0){
					grid.get(x).get(y)[0].draw(g);
				}
			}
		}

		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(0).size(); y++) {
				try{
					if(grid.get(x).get(y)[1].getCode() > 0){
						grid.get(x).get(y)[1].draw(g);
					}
				}catch(Exception e){}
			}
		} 
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	public Hashtable<Integer, Hashtable<Integer, WorldObj[]>> getGrid() {
		return grid;
	}


	public void setGridSize(int width, int height) {
		grid.changes.add(grid);
		grid.resize(width, height);
		this.setPreferredSize(new Dimension(getGridWidth() * WorldObj.TILE_SIZE * scaleFactor,
				getGridHeight()* WorldObj.TILE_SIZE * scaleFactor));
		Editor.mapScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Editor.mapScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		repaint();
	}

	public int getGridWidth() {
		return (int) grid.getSize().getWidth();
	}

	public int getGridHeight() {
		return (int) grid.getSize().getHeight();

	}

	public Dimension getGridSize() {
		return grid.getSize();
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		Editor.setTitle(name);
	}

	@Override
	public void mouseClicked(MouseEvent c) {
		// TODO Auto-generated method stub
		Dimension mouse = getTileXY(c.getPoint());
		try {
			if (c.isShiftDown() == false) {
				grid.changes.add(grid);
				grid.setTile((int) mouse.getWidth(), (int) mouse.getHeight());
			}
		} catch (Exception e) {
			// out of bounds
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.isShiftDown()) {
			try {
				grid.changes.add(grid);
				grid.setRotation((int) getTileXY(e.getPoint()).getWidth(),
						(int) getTileXY(e.getPoint()).getHeight());
				lastTile = getTileXY(e.getPoint());
				repaint();
			} catch (Exception ex) {
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.isShiftDown() == false) {
			try {
				grid.changes.add(grid);
				grid.setTile((int) getTileXY(e.getPoint()).getWidth(),
						(int) getTileXY(e.getPoint()).getHeight());
			} catch (Exception ex) {
				// out of bounds
			}
		} else {// shift click to rotate
			Point mouse = e.getPoint();
			try {
				if (getTileXY(mouse).width != lastTile.width
						|| getTileXY(mouse).height != lastTile.height) {
					grid.changes.add(grid);
					grid.setRotation((int) getTileXY(e.getPoint()).getWidth(),
							(int) getTileXY(e.getPoint()).getHeight());
				}
			} catch (Exception ex) {

			}
			lastTile = getTileXY(mouse);
		}
		repaint();
	}
	
	public void undo(){
		grid.setEqual(grid.changes.undo());// fix all this
	}
	
	public void redo(){
		grid = grid.changes.redo();
	}
}

class Grid extends Hashtable<Integer, Hashtable<Integer, WorldObj[]>> {
	ChangeManager changes;
	public Grid(int w, int h) {
		changes = new ChangeManager();
		resize(w, h);
	}

	public void resize(int w, int h) {
		// don't need to check the y direction because the if x = 0, then there
		// can be no y.
		// print numbers we are fed
		// If has no width or height, exit
		if (w == 0 && h == 0) {
			if (size() != 0) {
				// remove everything
				clear();
				changes.add(this);
			}
			return;
		}
		// loop through the collection to add or remove
		// Set the largest of both the size and the new width
		int staticSizeX = size();
		for (int x = Math.min(staticSizeX, w); x < Math.max(staticSizeX, w); x++) {
			// if empty or we have looped past the max index, add one to start
			// out
			if (w > size()) {
				put(x, new Hashtable<Integer, WorldObj[]>());
			}// if height given is smaller than size
			else {
				remove(x);
				//x--;
			}
		}

		for (int x = 0; x < size(); x++) {
			int staticSizeY = get(x).size();
			for (int y = Math.min(staticSizeY, h); y < Math.max(
					staticSizeY, h); y++) {
				if (h > get(x).size()) {
					get(x).put(y,
							new WorldObj[] { new Tile(-1, 0),
									new Decoration(-1) });
					get(x).get(y)[0].setXYLoc(x, y);
					get(x).get(y)[1].setXYLoc(x, y);
				} else {
					get(x).remove(y);
					//y--;
				}
			}
		}
		changes.add(this);
	}

	public void setTile(int x, int y) {
		if (TileLabel.onTile) {
			get(x).get(y)[0] = new Tile(TileLabel.selectedObjectIndex,
					WorldObj.ROTATION0);
			get(x).get(y)[0].setXYLoc(x, y);
		} else {
			get(x).get(y)[1] = new Decoration(TileLabel.selectedObjectIndex);
			get(x).get(y)[1].setXYLoc(x, y);
		}
	}

	public void setRotation(int x, int y) {
		Tile temp = (Tile) get(x).get(y)[0];
		if (temp.getRotation() == 0) {
			temp.setRotation(WorldObj.ROTATION1);
		} else if (temp.getRotation() == 1) {
			temp.setRotation(WorldObj.ROTATION2);
		} else if (temp.getRotation() == 2) {
			temp.setRotation(WorldObj.ROTATION3);
		} else if (temp.getRotation() == 3) {
			temp.setRotation(WorldObj.ROTATION0);
		}
		get(x).get(y)[0] = temp;
	}

	public Dimension getSize() {
		if (size() != 0) {
			return new Dimension(size(), get(0).size());
		} else {
			
		}return new Dimension(0, 0);
	}
	
	public void setEqual(Grid g){
		
	}
}
