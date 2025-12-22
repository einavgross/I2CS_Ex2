import java.io.Serializable;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{
    private int [][] _map;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
        init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {
        this(size,size, 0);
    }
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
        _map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                _map[i][j] = v;
            }
        }
    }
	@Override
	public void init(int[][] arr) {
        if (arr != null) {
            int w = arr.length;
            int h = arr[0].length;
            _map = new int[w][h];
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        _map[i][j] = arr[i][j];
                    }
                }
            }
        }
	@Override
	public int[][] getMap() {;
		return _map;
	}
	@Override
	public int getWidth() {
        return _map.length;
    }
	@Override
	public int getHeight() {
        return _map[0].length;
    }
	@Override
	public int getPixel(int x, int y) {
        return _map[x][y];
    }
	@Override
	public int getPixel(Pixel2D p) {
        return _map[p.getX()][p.getY()];
	}
	@Override
	public void setPixel(int x, int y, int v) {
        _map[x][y] = v;
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        _map[p.getX()][p.getY()] = v;
	}

    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = false;
        if (p != null) {
            int x = p.getX();
            int y = p.getY();
            if (x>=0 && x < getWidth() && y>=0 && y < getHeight()) {
                ans = true;
            }
        }
        return ans;
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        boolean ans = false;
        if (p != null) {
            int pWidth = p.getWidth();
            int pHeight = p.getHeight();
            if(pWidth==getWidth() && pHeight==getHeight()) {
                ans=true;}
        }
        return ans;
    }

    @Override
    public void addMap2D(Map2D p) {

    }

    @Override
    public void mul(double scalar) {

    }

    @Override
    public void rescale(double sx, double sy) {

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public boolean equals(Object ob) {
        boolean ans = false;

        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
