import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

// we will actually put the grid on this
public class Map extends JPanel implements MouseListener, MouseMotionListener,
		Runnable {
	public static final Color FOREGROUND_GRID_COLOR = Color.BLACK;
	public static final Color GRID_GRID_COLOR = Color.BLACK;
	public static final Color BACKGROUND_GRID_COLOR = Color.WHITE;
	static int scaleFactor = 2;

	public boolean showGrid = true;

	private Dimension lastTile = null;

	// stored as grid.get(x).get(y)[0] = tile
	// grid.get(x).get(y)[1] = decor
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
		Rectangle view = Editor.mapScroll.getViewport().getViewRect();
		
		int startX = (view.x + (WorldObj.TILE_SIZE * scaleFactor) - 1)
		/ (WorldObj.TILE_SIZE * scaleFactor) - 1;
		if(startX < 0){
			startX++;
		}
		
		int startY = (view.y + (WorldObj.TILE_SIZE * scaleFactor) - 1)
				/ (WorldObj.TILE_SIZE * scaleFactor) - 1;
		if(startY < 0){
			startY++;
		}
		
		int endX = ((int)view.getWidth() + (WorldObj.TILE_SIZE * scaleFactor) - 1)
				/ (WorldObj.TILE_SIZE * scaleFactor) + startX + 2;
		if(endX > grid.size()){
			endX = grid.size();
		}
		
		int endY = ((int)view.getHeight() + (WorldObj.TILE_SIZE * scaleFactor) - 1)
				/ (WorldObj.TILE_SIZE * scaleFactor) + startY + 2;
		if(endY > grid.get(0).size()){
			endY = grid.get(0).size();
		}
		
		drawTiles(g, startX, startY, endX, endY);
		if (showGrid) {
			drawBorders(g, startX, startY, endX, endY);
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

	private void drawBorders(Graphics g, int startX, int startY, int endX, int endY) {
		g.setColor(GRID_GRID_COLOR);
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {// with scaling
				g.drawRect(x * Tile.TILE_SIZE * scaleFactor, y * Tile.TILE_SIZE
						* scaleFactor, Tile.TILE_SIZE * scaleFactor,
						Tile.TILE_SIZE * scaleFactor);
			}
		}
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	private void drawTiles(Graphics g, int startX, int startY, int endX, int endY) {
		g.setColor(BACKGROUND_GRID_COLOR);
		
		//drawing
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				grid.get(x).get(y)[0].draw(g);
			}
		}

		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				grid.get(x).get(y)[1].draw(g);
			}
		}
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	public ArrayList<ArrayList<WorldObj[]>> getGrid() {
		return grid;
	}


	public void setGridSize(int width, int height) {
		grid.resize(width, height);
		this.setPreferredSize(new Dimension(width * WorldObj.TILE_SIZE * scaleFactor,
				height * WorldObj.TILE_SIZE * scaleFactor));
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
			if (c.isShiftDown() == false && c.isAltDown() == false) {
				grid.setTile((int) mouse.getWidth(), (int) mouse.getHeight());
			}else if(c.isAltDown()){
				((Tile)grid.get((int)mouse.getWidth()).get((int)mouse.getHeight())[0]).addCorner();
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
					grid.setRotation((int) getTileXY(e.getPoint()).getWidth(),
							(int) getTileXY(e.getPoint()).getHeight());
				}
			} catch (Exception ex) {

			}
			lastTile = getTileXY(mouse);
		}
		repaint();
	}
}

class Grid extends ArrayList<ArrayList<WorldObj[]>> {
	public Grid(int w, int h) {
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
				removeRange(0, size());
			}
			return;
		}
		// loop through the collection to add or remove
		// Set the largest of both the size and the new width
		for (int x = Math.min(size(), w); x < Math.max(size(), w); x++) {
			// if empty or we have looped past the max index, add one to start
			// out
			if (w > size()) {
				add(new ArrayList<WorldObj[]>());
			}// if height given is smaller than size
			else {
				remove(x);
				x--;
			}
		}

		for (int x = 0; x < size(); x++) {
			for (int y = Math.min(get(x).size(), h); y < Math.max(
					get(x).size(), h); y++) {
				if (h > get(x).size()) {
					get(x).add(
							new WorldObj[] { new Tile(-1, 0, 0),
									new Decoration(-1) });
				} else {
					get(x).remove(y);
					y--;
				}
				get(x).get(y)[0].setXYLoc(x, y);
				get(x).get(y)[1].setXYLoc(x, y);
			}
		}
	}

	public void setTile(int x, int y) {
		if (TileLabel.onTile) {
			get(x).get(y)[0] = new Tile(TileLabel.selectedObjectIndex,
					WorldObj.ROTATION0, 0);
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
}
