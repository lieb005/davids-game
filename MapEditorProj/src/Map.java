import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

// we will actually put the grid on this
public class Map extends JPanel implements MouseListener, MouseMotionListener {
	public static final Color FOREGROUND_GRID_COLOR = Color.BLACK;
	public static final Color GRID_GRID_COLOR = Color.BLACK;
	public static final Color BACKGROUND_GRID_COLOR = Color.WHITE;
	static int scaleFactor;
	
	private boolean scaleIt = true;

	// stored as grid.get(x).get(y)[0] = tile
	// grid.get(x).get(y)[1] = decor
	private Grid grid;

	public Map(String name, int w, int h) {
		super(true);
		setSize(w * Tile.TILE_SIZE, h * Tile.TILE_SIZE);
		setName(name);
		setScale(2);
		grid = new Grid(w, h);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawTiles(g);
		drawBorders(g);
	}
	
	public static void setScale(int newScale){
		scaleFactor = newScale;
	}

	private void drawBorders(Graphics g) {
		g.setColor(GRID_GRID_COLOR);
		for (int x = 0; x < getGridWidth(); x++) {
			for (int y = 0; y < getGridHeight(); y++) {// with scaling
				g.drawRect(x * Tile.TILE_SIZE * scaleFactor, y
						* Tile.TILE_SIZE * scaleFactor, Tile.TILE_SIZE
						* scaleFactor, Tile.TILE_SIZE * scaleFactor);
			}
		}
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	private void drawTiles(Graphics g) {
		g.setColor(BACKGROUND_GRID_COLOR);
		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(x).size(); y++) {
				BufferedImage img = grid.get(x).get(y)[0].getImage();
				g.drawImage(img, 0, 0,
						(int) (x * WorldObj.TILE_SIZE * scaleFactor), (int) (y
								* WorldObj.TILE_SIZE * scaleFactor),
						img.getWidth(), img.getHeight(),
						(int) (WorldObj.TILE_SIZE * scaleFactor),
						(int) (WorldObj.TILE_SIZE * scaleFactor), null);
				img = grid.get(x).get(y)[1].getImage();

				g.drawImage(img, 0, 0,
						(int) (x * WorldObj.TILE_SIZE * scaleFactor), (int) (y
								* WorldObj.TILE_SIZE * scaleFactor),
						img.getWidth(), img.getHeight(), img.getWidth(),
						img.getHeight(), null);
			}
		}
		g.setColor(BACKGROUND_GRID_COLOR);
	}

	public ArrayList<ArrayList<WorldObj[]>> getGrid() {
		return grid;
	}

	public void setGridSize(int width, int height) {
		grid.resize(width, height);
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
	}

	@Override
	public void mouseClicked(MouseEvent c) {
		// TODO Auto-generated method stub
		System.out.println("CLICK!");
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		System.out.println(getTileXY(e.getPoint()));
		repaint();
	}

	public Dimension getTileXY(Point mouse) {// returns the XY coordinates of
												// the clicke tile
		int scaleMultiplier = scaleFactor;
		if (scaleIt == false) {
			scaleMultiplier = 1;
		}
		Dimension loc = new Dimension(
				((int) (mouse.getX()) + (WorldObj.TILE_SIZE * scaleMultiplier) - 1)
						/ (WorldObj.TILE_SIZE * scaleMultiplier) - 1,
				((int) (mouse.getY()) + (WorldObj.TILE_SIZE * scaleMultiplier) - 1)
						/ (WorldObj.TILE_SIZE * scaleMultiplier) - 1);
		
		if((int)loc.getWidth() <= grid.size() -1 && (int)loc.getHeight() <= grid.get(0).size() -1){
			return loc;
		}else{
			loc = null;
			return loc;
		}
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
		System.out.println("Wanted: (" + w + ", " + h + ")");
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
		for (int x = 0; x < Math.max(size(), w); x++) {
			// if empty or we have looped past the max index, add one to start
			// out
			if (size() <= x || size() == 0) {
				add(x, new ArrayList<WorldObj[]>());
			} else {
				remove(x);
				continue;
			}
			for (int y = 0; y < Math.max(get(x).size(), h); y++) {
				if (size() >= x) {
					get(x).add(
							new WorldObj[] { new Tile(-1, 0),
									new Decoration(-1) });
				} else {
					remove(y);
					continue;
				}
			}
		}
		try {
			System.out.println("Wanted: (" + w + ", " + h + ")    Got: ("
					+ size() + ", " + get(0).size() + ")");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	public Dimension getSize() {
		if (size() == 0) {
			return new Dimension(size(), 0);
		} else {
			return new Dimension(size(), get(0).size());
		}
	}
}
