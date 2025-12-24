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
        if (sameDimensions(p)) {
            for (int i=0; i<getWidth(); i++) {
                for (int j=0; j<getHeight(); j++) {
                    _map[i][j]+=p.getPixel(i,j);
                }
            }
        }
    }

    @Override
    public void mul(double scalar) {
        for (int i=0; i<getWidth(); i++) {
            for (int j=0; j<getHeight(); j++) {
                _map[i][j]*=(int)scalar;
            }
        }
    }

    @Override
    public void rescale(double sx, double sy) {
        int [][] res = new int [(int)(getWidth()*sx)][(int)(getHeight()*sy)];
        for (int i=0; i<res.length; i++) {
            for (int j=0; j<res[0].length; j++) {
                res[i][j] = _map[i/(int)sx][j/(int)sy];
            }
        }
        init(res);
    }



    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        int minX = Math.max(0,(int)(center.getX()-rad));
        int maxX = Math.max(getWidth()-1,(int)(center.getX()+rad));
        int minY = (int)(center.getY()-rad);
        int maxY = (int)(center.getY()+rad);
        for (int i=minX; i<maxX; i++) {
            for (int j=minY; j<maxY; j++) {
                Pixel2D p = new Index2D(i,j);
                if (center.distance2D(p)<=rad) {
                    setPixel(i,j,color);
                }
            }
        }
    }

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
            for (int i = p1.getX(); i <= p2.getX(); i++) {
                double y = p1.getY() + m * (i - p1.getX());
                setPixel(i, (int) Math.round(y), color);
            }
        }
        if (dy>dx){ // קו עומד
            if (p2.getY() < p1.getY()) {
                drawLine(p2, p1, color);
                return;
            }
            double m2 = (double)(p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
            for (int i = p1.getY(); i <= p2.getY(); i++) {
                double x = p1.getX() + m2 * (i - p1.getY());
                setPixel((int) Math.round(x), i, color);
            }
        }
    }

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
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
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

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
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
