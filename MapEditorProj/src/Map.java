import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// we will actually put the grid on this
public class Map extends Canvas {
	public static final Color FOREGROUND_GRID_COLOR = Color.BLACK;
	public static final Color BACKGROUND_GRID_COLOR = Color.WHITE;

	private double scaleFactor = 2;

	// stored as grid.get(x).get(y)[0] = tile
	// grid.get(x).get(y)[1] = decor
	private Grid grid;

	public Map(String name, int w, int h) {
		setName(name);
		grid = new Grid(w, h);
	}

	@Override
	public void paint(Graphics g) {
		drawTiles(g);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public ArrayList<ArrayList<WorldObj[]>> getGrid() {
		return grid;
	}

	public void drawTiles(Graphics g) {
		g.setColor(BACKGROUND_GRID_COLOR);
		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(x).size(); y++) {
				BufferedImage img = grid.get(x).get(y)[0].getImage();
				g.drawImage(img, 0, 0,
						(int) (x * Tile.TILE_SIZE * scaleFactor), (int) (y
								* Tile.TILE_SIZE * scaleFactor),
						img.getWidth(), img.getHeight(),
						(int) (Tile.TILE_SIZE * scaleFactor),
						(int) (Tile.TILE_SIZE * scaleFactor), null);
				img = grid.get(x).get(y)[1].getImage();

				g.drawImage(img, 0, 0,
						(int) (x * Tile.TILE_SIZE * scaleFactor), (int) (y
								* Tile.TILE_SIZE * scaleFactor),
						img.getWidth(), img.getHeight(), img.getWidth(),
						img.getHeight(), null);

			}
		}
	}

	public void setSize(int w, int h) {
		grid.resize(w, h);
		repaint();
	}

	public int getWidth() {
		return (int) grid.getSize().getWidth();
	}

	public int getHeight() {
		return (int) grid.getSize().getHeight();

	}

	public Dimension getSize() {
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
}

class Grid extends ArrayList<ArrayList<WorldObj[]>> {
	public Grid(int w, int h) {
		resize(w, h);
	}

	public void resize(int w, int h) {
		// don't need to check the y direction because the if x = 0, then there
		// can be no y.
		System.out.println("Wanted: (" + h + ", " + w + ")");
		if (size() == 0 && w == 0 && h == 0) {
			return;
		}
		for (int x = 0; x < Math.max(size(), w); x++) {
			if (size() == 0 && w != 0) {
				add(new ArrayList<WorldObj[]>());
			}
			if (size() >= x) {
				add(new ArrayList<WorldObj[]>());
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
			System.out.println("Wanted: (" + h + ", " + w + ")    Got: ("
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
