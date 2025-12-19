//package assignments.Ex2;

public class Index2D implements Pixel2D {
    private int x;
    private int y;
    public Index2D(int w, int h) {
        x = w;
        y = h;
    }

    public Index2D(Pixel2D other) {
        x = other.getX();
        y = other.getY();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        double dx = this.x - p2.getX();
        double dy = this.y - p2.getY();
        double pdx = Math.pow(dx, 2);
        double pdy = Math.pow(dy, 2);
        return Math.sqrt(pdx + pdy);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = true;
        if (p instanceof Pixel2D p2) {
            ans = this.x == p2.getX() && this.y == p2.getY();
        }
        return ans;
    }
}
