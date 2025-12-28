import java.io.Serializable;
import java.util.LinkedList;

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

    /**
     * Initializes the map (2D array) with dimensions w by h and fills all cells with the value v.
     * @param w the width of the map.
     * @param h the height of the map.
     * @param v the init value of all the entries in the map.
     * Creates a map from a new 2D array with w as width and h as height and inserts v to all the cells of the 2D array.
     */
	@Override
	public void init(int w, int h, int v) {
        _map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                _map[i][j] = v;
            }
        }
    }

    /**
     * Initializes the map (2D array) from a given int 2D array
     * @param arr a 2D int array.
     * if the given 2D array is not null,the function creates a map with the width,height and values of the given 2D array
     */
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

    /**
     * @return the 2D array that represent the map (_map field)
     */
	@Override
	public int[][] getMap() {;
		return _map;
	}
    /** Returns the width of the map*/
	@Override
	public int getWidth() {
        return _map.length;
    }
    /** Returns the height of the map */
	@Override
	public int getHeight() {
        return _map[0].length;
    }

    /**
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the color value of the pixel at the coordinates x,y
     */
	@Override
	public int getPixel(int x, int y) {
        return _map[x][y];
    }

    /**
     * @param p a given pixel in the map
     * @return the color value of the pixel at the given pixel p
     */
	@Override
	public int getPixel(Pixel2D p) {
        return _map[p.getX()][p.getY()];
	}

    /**
     * Sets the color value of the pixel at the specified coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @param v the color that the entry at the coordinate [x][y] is set to.
     */
	@Override
	public void setPixel(int x, int y, int v) {
        _map[x][y] = v;
    }

    /**
     * Sets the color value of the given pixel
     * @param p the given pixel in the map
     * @param v the value that the entry at the coordinate [p.x][p.y] is set to
     */
	@Override
	public void setPixel(Pixel2D p, int v) {
        _map[p.getX()][p.getY()] = v;
	}

    /**
     * Checks if the given pixel coordinates are within the map boundaries
     * @param p the given pixel in the map.
     * @return true if the given pixel is inside the map, else return false
     * this function return true if:
     *      the x coordinate of the pixel is non-negative and <= the map's width
     *      the y coordinate of the pixel is non-negative and <= the map's height
     * else, or if the pixel is null, return false.
     */
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

    /**
     * checks if a given map has the same dimensions - same width and height values - as this map
     * @param p a given map2D
     * @return true if this map and the given Map2D p have the same dimensions
     * This function returns true only if both the height and the width of the given Map2D p
     * are equals to the height and the width of this map
     */
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

    /**
     * This function adds the map p to this map - assuming they have the same dimensions
     * @param p - the map that should be added to this map (just in case they have the same dimensions).
     * this function adds each value of a pixel in the given Map2D p to the value of the same pixel in this map
     */
    @Override
    public void addMap2D(Map2D p) {
        if (sameDimensions(p)) {
            for (int i=0; i<getWidth(); i++) {
                for (int j=0; j<getHeight(); j++) {
                    _map[i][j]+=p.getPixel(i,j);
                }
            }
        }
    }

    /**
     * This function multiplay this map with a scalar (casting to int).
     * @param scalar a double value
     * this function multiply the double scalar with every int value in the map, casting the result to int
     */
    @Override
    public void mul(double scalar) {
        for (int i=0; i<getWidth(); i++) {
            for (int j=0; j<getHeight(); j++) {
                _map[i][j]*=(int)scalar;
            }
        }
    }

    /**
     * This method changes the dimensions of this map, a map of size [100][200],rescaled with (1.2,0.5) will change to [120][100].
     * @param sx the double value to increase the width with
     * @param sy the double value to increase the height with
     * this function creates a new int 2D array with the new width and height,
     * inserts a new value by dividing each new coordinate by the sx and sy to find its matching value in this map and updates the map
     */
    @Override
    public void rescale(double sx, double sy) {
        int [][] res = new int [(int)(getWidth()*sx)][(int)(getHeight()*sy)];
        for (int i=0; i<res.length; i++) {
            for (int j=0; j<res[0].length; j++) {
                res[i][j] = _map[(int)(i/sx)][(int)(j/sy)];
            }
        }
        init(res);
    }

    /**
     * This method draws a circle by changing all the pixels in this map which their distance to the center is lower than rad to color
     * @param center a Pixel2D value to represent the center
     * @param rad a double value for the radius's length
     * @param color - the (new) color to be used in the drawing.
     * this function computes a min range (rectangle blocking the given circle)
     * change the value (color) to the new color for all the pixels in the range only if the distance between the pixel
     * and the center is lower than the radius
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        int minX = Math.max(0,(int)(center.getX()-rad));
        int maxX = Math.max(getWidth()-1,(int)(center.getX()+rad));
        int minY = (int)(center.getY()-rad);
        int maxY = (int)(center.getY()+rad);
        for (int i=minX; i<=maxX; i++) {
            for (int j=minY; j<=maxY; j++) {
                Pixel2D p = new Index2D(i,j);
                if (isInside(p)) {
                    if (center.distance2D(p) < rad) {
                        setPixel(i, j, color);
                    }
                }
            }
        }
    }

    /**
     * This method draws a line by changing the pixels between p1 to p2 to the newColor.
     * @param p1
     * @param p2
     * @param color - the (new) color to be used in the drawing.
     * this function computes the linear equation of the 2 pixels and change the color for all the pixels along the path
     */
    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        int dx = Math.abs(p2.getX() - p1.getX());
        int dy = Math.abs(p2.getY() - p1.getY());

        if (dx >= dy) { // קו שוכב
            if (p2.getX() < p1.getX()) {
                drawLine(p2, p1, color);
                return;
            }
            double m = (double)(p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
            double y = p1.getY();
            for (int i = p1.getX(); i <= p2.getX(); i++) {
                setPixel(i, (int) Math.round(y), color);
                y+=m;
            }
        }
        else {
            if (dy > dx) { // קו עומד
                if (p2.getY() < p1.getY()) {
                    drawLine(p2, p1, color);
                    return;
                }
                double m2 = (double) (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
                double x = p1.getX();
                for (int i = p1.getY(); i <= p2.getY(); i++) {
                    setPixel((int) Math.round(x), i, color);
                    x += m2;
                }
            }
        }
    }

    /**
     * This method draws a rectangle by changing all the pixels in this map which are within the [p1,p2] range to color.
     * @param p1 a given pixel
     * @param p2 a given pixel
     * @param color - the (new) color to be used in the drawing.
     * this function computes the minium x and y from the pixels coordinates and change the color of all the pixels between them
     */
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        for (int i=minX; i<=maxX; i++) {
            for (int j=minY; j<=maxY; j++) {
                setPixel(i,j,color);
            }
        }
    }

    /**
     * This function checks if a given object is a map that equals to this map
     * @param ob the reference object with which to compare.
     * @return true if the given object is a map with the same dimensions and values as this map,else return false
     */
    @Override
    public boolean equals(Object ob) {
        boolean ans = false;
        if (ob!=null) {
            if (ob instanceof Map m) {
                if (sameDimensions(m)) {
                    ans = true;
                    for (int i = 0; i < getWidth() && ans; i++) {
                        for (int j = 0; j < getHeight() && ans; j++) {
                            if (m.getPixel(i, j) != getPixel(i, j)) {
                                ans = false;
                            }
                        }
                    }
                }
            }
        }
        return ans;
    }

    /**
     * this function fills this map with the new color (new_v) starting from p (https://en.wikipedia.org/wiki/Flood_fill)
     * @param xy the pixel to start from.
     * @param new_v - the new "color" to be filled in p's connected component.
     * @param cyclic is the map cyclic (boolean)
     * @return the number of pixels colored
     * This function changes the color of the connected component by starting at point p
     * and spreading to every neighbor of the same color until the entire area is updated.
     */
	@Override
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = 0;
        if (xy != null) {
            int oldColor = getPixel(xy);
            if (oldColor != new_v) {
                LinkedList<Pixel2D> q = new LinkedList<Pixel2D>();
                q.add(xy);
                while (!q.isEmpty()) {
                    Pixel2D c = q.poll();
                    if (getPixel(c) == oldColor) {
                        setPixel(c, new_v);
                        ans++;
                        if (!cyclic) {
                            Pixel2D up = new Index2D(c.getX(), c.getY() + 1);
                            if (isInside(up) && getPixel(up) != new_v && getPixel(up) == oldColor) {
                                q.add(up);
                            }
                            Pixel2D down = new Index2D(c.getX(), c.getY() - 1);
                            if (isInside(down) && getPixel(down) != new_v && getPixel(down) == oldColor) {
                                q.add(down);
                            }
                            Pixel2D left = new Index2D(c.getX() - 1, c.getY());
                            if (isInside(left) && getPixel(left) != new_v && getPixel(left) == oldColor) {
                                q.add(left);
                            }
                            Pixel2D right = new Index2D(c.getX() + 1, c.getY());
                            if (isInside(right) && getPixel(right) != new_v && getPixel(right) == oldColor) {
                                q.add(right);
                            }
                        }
                        if (cyclic) {
                            Pixel2D up = new Index2D(c.getX(), (c.getY() + (getHeight() - 1)) % getHeight());
                            if (getPixel(up) != new_v && getPixel(up) == oldColor) {
                                q.add(up);
                            }
                            Pixel2D down = new Index2D(c.getX(), (c.getY() + 1) % getHeight());
                            if (getPixel(down) != new_v && getPixel(down) == oldColor) {
                                q.add(down);
                            }
                            Pixel2D left = new Index2D((c.getX() + (getWidth() - 1)) % getWidth(), c.getY());
                            if (getPixel(left) != new_v && getPixel(left) == oldColor) {
                                q.add(left);
                            }
                            Pixel2D right = new Index2D((c.getX() + 1) % getWidth(), c.getY());
                            if (getPixel(right) != new_v && getPixel(right) == oldColor) {
                                q.add(right);
                            }
                        }
                    }
                }
            }
        }
		return ans;
	}

    /**
     * Compute the shortest valid path between p1 and p2 using BFS (like shortest the computation based on iterative raster implementation:
     * https://en.wikipedia.org/wiki/Breadth-first_search)
     * @param p1 first coordinate (start point).
     * @param p2 second coordinate (end point).
     * @param obsColor the color which is addressed as an obstacle.
     * @param cyclic is the map cyclic (boolean)
     * @return a Pixel2D array with all the points in the shortest Path. if none - returns null.
     * This function finds the shortest path by starting at the destination and backtracking to the source
     * by following the descending distance values in the distance map.
     */
	@Override
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
        Pixel2D[] ans = null;
        Map2D distanceMap = allDistance(p1, obsColor, cyclic);
        if (distanceMap.getPixel(p2) != -1 && distanceMap.getPixel(p2) != -obsColor) {
            ans = new Pixel2D[distanceMap.getPixel(p2) + 1];
            Pixel2D c = p2;
            for (int i = ans.length - 1; i >= 0; i--) {
                ans[i] = c;
                if (i > 0) {
                    if (!cyclic) {
                        Pixel2D up = new Index2D(c.getX(), c.getY() + 1);
                        if (isInside(up) && distanceMap.getPixel(c) == distanceMap.getPixel(up) + 1) {
                            c=up;
                            continue;
                        }
                        Pixel2D down = new Index2D(c.getX(), c.getY() - 1);
                        if (isInside(down) && distanceMap.getPixel(c) == distanceMap.getPixel(down) + 1) {
                            c=down;
                            continue;
                        }
                        Pixel2D left = new Index2D(c.getX() - 1, c.getY());
                        if (isInside(left) && distanceMap.getPixel(c) == distanceMap.getPixel(left) + 1) {
                            c=left;
                            continue;
                        }
                        Pixel2D right = new Index2D(c.getX() + 1, c.getY());
                        if (isInside(right) && distanceMap.getPixel(c) == distanceMap.getPixel(right) + 1) {
                            c=right;
                            continue;
                        }
                    }
                    if (cyclic) {
                        Pixel2D up = new Index2D(c.getX(), (c.getY() + (getHeight() - 1)) % getHeight());
                        if (distanceMap.getPixel(c) == distanceMap.getPixel(up) + 1) {
                            c=up;
                            continue;
                        }
                        Pixel2D down = new Index2D(c.getX(), (c.getY() + 1) % getHeight());
                        if (distanceMap.getPixel(c) == distanceMap.getPixel(down) + 1) {
                            c=down;
                            continue;
                        }
                        Pixel2D left = new Index2D((c.getX() + (getWidth() - 1)) % getWidth(), c.getY());
                        if (distanceMap.getPixel(c) == distanceMap.getPixel(left) + 1) {
                            c=left;
                            continue;
                        }
                        Pixel2D right = new Index2D((c.getX() + 1) % getWidth(), c.getY());
                        if (distanceMap.getPixel(c) == distanceMap.getPixel(right) + 1) {
                            c=right;
                        }
                    }
                }
            }
        }
    return ans;
    }

    /**
     * Compute a new map (with the same dimension as this map) with the
     * shortest path distance (obstacle avoiding) from the start point.
     * None accessible entries should be marked -1.
     * @param start the source (starting) point
     * @param obsColor the color representing obstacles
     * @return a new map with all the shortest path distances from the starting point to each entry in this map
     * This function calculates the distance from the start point to every reachable pixel by spreading outward
     * and marking each new neighbor with a value higher by 1 than the current pixel.
     */
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;
        if (start != null && getPixel(start)!=obsColor && isInside(start))
        {
            ans = new Map(this.getMap());
            for (int i=0; i<getWidth(); i++) {
                for (int j=0; j<getHeight(); j++) {
                    ans.getMap()[i][j]=-1;
                }
            }
            LinkedList<Pixel2D> q = new LinkedList<>();
            q.add(start);
            ans.setPixel(start, 0);
            while (!q.isEmpty()) {
                Pixel2D c = q.poll();
                if (getPixel(c) != obsColor) {
                    if (!cyclic) {
                        Pixel2D up = new Index2D(c.getX(), c.getY() + 1);
                        if (isInside(up) && getPixel(up) != obsColor && ans.getPixel(up) == -1) {
                            ans.setPixel(up, ans.getPixel(c) + 1);
                            q.add(up);
                        }
                        Pixel2D down = new Index2D(c.getX(), c.getY() - 1);
                        if (isInside(down) && getPixel(down) != obsColor && ans.getPixel(down) == -1) {
                            ans.setPixel(down, ans.getPixel(c) + 1);
                            q.add(down);
                        }
                        Pixel2D left = new Index2D(c.getX() - 1, c.getY());
                        if (isInside(left) && getPixel(left) != obsColor && ans.getPixel(left) == -1) {
                            ans.setPixel(left, ans.getPixel(c) + 1);
                            q.add(left);
                        }
                        Pixel2D right = new Index2D(c.getX() + 1, c.getY());
                        if (isInside(right) && getPixel(right) != obsColor && ans.getPixel(right) == -1) {
                            ans.setPixel(right, ans.getPixel(c) + 1);
                            q.add(right);
                        }
                    }
                    if (cyclic) {
                        Pixel2D up = new Index2D(c.getX(), (c.getY() + (getHeight() - 1)) % getHeight());
                        if (getPixel(up) != obsColor && ans.getPixel(up) == -1) {
                            ans.setPixel(up, ans.getPixel(c) + 1);
                            q.add(up);
                        }
                        Pixel2D down = new Index2D(c.getX(), (c.getY() + 1) % getHeight());
                        if (getPixel(down) != obsColor && ans.getPixel(down) == -1) {
                            ans.setPixel(down, ans.getPixel(c) + 1);
                            q.add(down);
                        }
                        Pixel2D left = new Index2D((c.getX() + (getWidth() - 1)) % getWidth(), c.getY());
                        if (getPixel(left) != obsColor && ans.getPixel(left) == -1) {
                            ans.setPixel(left, ans.getPixel(c) + 1);
                            q.add(left);
                        }
                        Pixel2D right = new Index2D((c.getX() + 1) % getWidth(), c.getY());
                        if (getPixel(right) != obsColor && ans.getPixel(right) == -1) {
                            ans.setPixel(right, ans.getPixel(c) + 1);
                            q.add(right);
                        }
                    }
                }
            }
        }
        return ans;
    }
}
