
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * this is a tests class for the 2D map class
 */
public class MapTest {
    /**\
     * tests constructor from 3 values
     */
    @Test
    public void testInitFrom3V() {
        Map2D m = new Map(3,7,1);
        assertEquals(3,m.getWidth());
        assertEquals(7,m.getHeight());
        assertEquals(1,m.getPixel(2,6));
        assertEquals(1,m.getPixel(1,4));
    }

    /**
     * tests constructor from a 2D array
     */
    @Test
    public void testInitFromArray() {
        int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
        Map2D m2 = new Map(m);
        assertEquals(m.length,m2.getWidth());
        assertEquals(m[0].length,m2.getHeight());
        assertEquals(5,m2.getPixel(1,1));
        assertEquals(3,m2.getPixel(0,2));
        assertEquals(m[1][0],m2.getPixel(1,0));
    }

    /**
     * tests getMap function - if returns the right 2D array
     */
    @Test
    public void getMapTest() {
        int [][] m1= {{1,2,3},{4,5,6},{7,8,9}};
        Map2D m2 = new Map(m1);
        assertArrayEquals(m1,m2.getMap());
    }

    /**
     * tests getMap function - when setting the Map from values
     */
    @Test
    public void getMapTest4() {
        Map2D m1 = new Map(2, 2, 2);
        int[][] m2 = {{2, 2}, {2, 2}};
        assertArrayEquals(m2, m1.getMap());
    }
    /**
     * tests getMap function for a single object with zeros
     */
    @Test
    public void getMapTest2() {
        int [][] m1= {{0,0,0}};
        Map2D m2 = new Map(m1);
        assertArrayEquals(m1,m2.getMap());
    }
    /**
     * test getMap for a big array
     */
    @Test
    public void getMapTestBig() {
        int [][] m1= new int[1000][1000];
        Map2D m2 = new Map(m1);
        assertArrayEquals(m1,m2.getMap());
    }

    /**
     * a simple test for getWidth and getHeight functions
     */
    @Test
    public void getMapWidAndHeightTest() {
        Map2D m1 = new Map(1);
        assertEquals(1,m1.getWidth());
        assertEquals(1,m1.getHeight());
    }

    /**
     * tests getWidth and getHeight functions with big numbers
     */
    @Test
    public void getWidthAndHeightTestBig() {
        Map2D m1 = new Map(1000,1,0);
        assertEquals(1000,m1.getWidth());
        assertEquals(1,m1.getHeight());
        Map2D m2 = new Map(1,1000,0);
        assertEquals(1,m2.getWidth());
        assertEquals(1000,m2.getHeight());
        Map2D m3 = new Map(2000,2000,0);
        assertEquals(2000,m3.getWidth());
        assertEquals(2000,m3.getHeight());
    }

    /**
     * tests getWidth and getHeight functions after changing the values
     */
    @Test
    public void testUpdate() {
        Map m = new Map(10, 10, 0);
        assertEquals(10, m.getWidth());
        m.init(5, 8, 0);
        assertEquals(5, m.getWidth());
        assertEquals(8, m.getHeight());
    }

    /**
     * tests if getPixel functions are identical(with x,y and with Pixel2D)
     */
    @Test
    public void testGetPixelMethods() {
        Map m = new Map(10, 10, 5);
        Pixel2D p = new Index2D(5, 5);
        assertEquals(m.getPixel(5, 5), m.getPixel(p));
    }

    /**
     * tests setPixel function - set with x,y,v -  checks if the pixel have changed and the others remained the same
     */
    @Test
    public void testSetGetPixelFromXYV() {
        Map m = new Map(8, 8, 3);
        m.setPixel(2, 3, 100);
        assertEquals(100, m.getPixel(2, 3));
        assertEquals(3, m.getPixel(0, 5));
    }

    /**
     * tests setPixel function - set with x,y,v -  checks if the pixel have changed and the others remained the same
     */
    @Test
    public void testSetGetPixelFromPV() {
        Map2D m = new Map(45);
        Pixel2D p = new Index2D(30,30);
        Pixel2D p2 = new Index2D(20,20);
        m.setPixel(p,8);
        assertEquals(8, m.getPixel(p));
        assertEquals(0, m.getPixel(p2));

    }

    /**
     * tests isInside function, checks if returns true for pixels inside, and false if outside.
     */
    @Test
    public void testIsInside() {
        Map m = new Map(8, 8, 3);
        Pixel2D p = new Index2D(5, 7);
        Pixel2D p2 = new Index2D(2, 0);
        Pixel2D p3 = new Index2D(-5, 0);
        Pixel2D p4 = new Index2D(3, 20);
        assertTrue(m.isInside(p));
        assertTrue(m.isInside(p2));
        assertFalse(m.isInside(p3));
        assertFalse(m.isInside(p4));
    }

    /**
     * tests sameDimensions function - if returns true for identical maps or different maps with same dimensions.
     */
    @Test
    public void testSameDimensions() {
        Map m1 = new Map(15, 20, 0);
        Map m2 = new Map(15, 20, 0);
        Map m3 = new Map(15, 20, 1);
        Map m4 = new Map(15, 7, 12);
        assertTrue(m1.sameDimensions(m2));
        assertTrue(m1.sameDimensions(m3));
        assertFalse(m1.sameDimensions(m4));
    }

    /**
     * tests addMap2D function - checks if the map of a manual sum is equal to the function result
     */
    @Test
    public void testAddMap2D() {
        Map2D m = new Map(4,4,3);
        Map2D m2 = new Map(4,4,1);
        Map2D m3 = new Map(4,4,4);
        m.addMap2D(m2);
        assertArrayEquals(m3.getMap(),m.getMap());
    }

    /**
     * test if addMap2D is symmetrical - m1+m2 = m4 (copy of m2) + m3 (copy of m1)
     */
    @Test
    public void testIfAddSymmetric() {
        Map2D m1 = new Map(5,6,3);
        Map2D m2 = new Map(5,6,1);
        Map2D m3 = new Map(m1.getMap());
        Map2D m4 = new Map(m2.getMap());
        m1.addMap2D(m2); //m1+m2
        m4.addMap2D(m3); //m2+m1
        assertArrayEquals(m1.getMap(),m4.getMap());
    }

    /**
     * tests mul by scalar function - checks if multiplying the map by scalar 0.0 returns a map with all zeros
     */
    @Test
    public void testMulBy0() {
        Map2D m1 = new Map(5,9,2);
        Map2D m2 = new Map(5,9,0);
        double scalar = 0.0;
        m1.mul(scalar);
        assertArrayEquals(m1.getMap(),m2.getMap());
    }
    /**
     * tests mul by scalar function - checks if multiplying the map by scalar 1.0 returns the same map
     */
    @Test
    public void testMulBy1() {
        Map2D m1 = new Map(5,6,3);
        Map2D m2 = new Map(m1.getMap());
        double scalar = 1.0;
        m1.mul(scalar);
        assertArrayEquals(m1.getMap(),m2.getMap());
    }
    /**
     * tests mul by scalar function - checks if the map of a manual multiplication is equal to the function result
     */
    @Test
    public void testMul() {
        Map2D m1 = new Map(5,6,3);
        Map2D m2 = new Map(5,6,15);
        double scalar = 5.0;
        m1.mul(scalar);
        assertArrayEquals(m1.getMap(),m2.getMap());
    }
    /**
     * tests mul by scalar function with a double result - check the casting of the scalar
     */
    @Test
    public void testMulCasting() {
        Map2D m1 = new Map(2,7,3);
        double scalar = 7.4;
        Map2D m2 = new Map(2,7,21);
        m1.mul(scalar);
        assertArrayEquals(m1.getMap(),m2.getMap());
    }

    /**
     * test rescale function - checks that the width, height and pixels have changed properly
     */
    @Test
    public void testRescaleSimple() {
        int[][] data = {{1, 2},{3, 4},{5, 6}};
        Map m = new Map(data);
        m.rescale(2.0, 2.0);
        assertEquals(6, m.getWidth());
        assertEquals(4, m.getHeight());
        assertEquals(6, m.getPixel(5, 3));
        assertEquals(2, m.getPixel(0, 2));
        assertEquals(1, m.getPixel(1, 1));
    }

    /**
     * tests drawCircle function - checks if the function changes the color only for the pixels inside the circle
     */
    @Test
    public void testDrawCircleSimple() {
        int[][] data = new int[10][10];
        Map m = new Map(data);
        int color = 1;
        Pixel2D center = new Index2D(5, 5);
        double rad = 2.0;
        m.drawCircle(center, rad, color);
        assertEquals(color, m.getPixel(5, 5));
        assertEquals(color, m.getPixel(5, 6));
        assertEquals(0, m.getPixel(0, 0));
        assertEquals(0, m.getPixel(5, 8));
    }

    /**
     * tests drawRect function - checks if the function changes the color only for the pixels inside the rectangle
     */
    @Test
    public void testDrawRect() {
        Map m = new Map(7);
        Pixel2D p1 = new Index2D(2, 2);
        Pixel2D p2 = new Index2D(4, 4);
        m.drawRect(p1, p2, 7);
        assertEquals(7, m.getPixel(2, 2));
        assertEquals(7, m.getPixel(4, 4));
        assertEquals(7, m.getPixel(2, 4));
        assertEquals(7, m.getPixel(3, 3));
        assertEquals(0, m.getPixel(1, 1));
        assertEquals(0, m.getPixel(5, 5));
    }

    /**
     * tests drawLine function - checks if the function changes the color only for the pixels on the line between the given points
     */
    @Test
    public void testDrawLine() {
        int[][] data = new int[10][10];
        Map m = new Map(data);
        m.drawLine(new Index2D(1, 1), new Index2D(5, 1), 3);
        for (int x = 1; x <= 5; x++) {assertEquals(3, m.getPixel(x, 1));}
        assertEquals(0, m.getPixel(1, 2));
        m.drawLine(new Index2D(0, 0), new Index2D(4, 2), 5);
        assertEquals(5, m.getPixel(0, 0));
        assertEquals(5, m.getPixel(4, 2));
        assertEquals(5, m.getPixel(2, 1));
    }

    /*private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0,_m1,_m3_3;

    @BeforeEach
    public void setup() {
        _m0 = new Map(2);
        _m3_3 = new Map(_map_3_3);
        _m1 = new Map(1);
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init () {
        int[][] big_arr = new int[500][500];
        _m1.init(big_arr);
        assertEquals(big_arr.length, _m1.getWidth());
        assertEquals(big_arr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3, 2);
        _m1.fill(p1, 1, true);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }*/
}