//package src;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
    public class Ex2_GUI {
    private static int _color = 1; // צבע נוכחי
    private static String _mode = "Point";
    private static Index2D _p1 = null;
    private static boolean _cyclic = false;

    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = new Map(20,20,0);
        StdDraw.enableDoubleBuffering();
        StdDraw.createMenuBar();
        drawMap(map);
        while (true) {
            String action = StdDraw.getActionCommand();
            if (action != null) {
                map = handleMenu(action, map);
            }
            if (StdDraw.isMousePressed()) {
                int x = (int)Math.round(StdDraw.mouseX());
                int y = (map.getHeight() - 1) - (int)Math.round(StdDraw.mouseY());
                if (map.isInside(new Index2D(x, y))) {
                    handleMouse(x,y, map);
                    drawMap(map);
                }
                while (StdDraw.isMousePressed()) {
                    StdDraw.pause(10);
                }
            }
            StdDraw.pause(20);
        }
    }

    public static void drawMap(Map2D map) {
        //  Padding
        StdDraw.setXscale(-0.5, map.getWidth() - 0.5);
        StdDraw.setYscale(-2.0, map.getHeight() - 0.5);
        StdDraw.clear();
        //draw grid
        StdDraw.setPenColor(Color.gray);
        for (int i = 0; i < map.getWidth(); i++) {
            StdDraw.line(i, 0, i, map.getHeight() - 1);
        }
        for (int j = 0; j < map.getHeight(); j++) {
            StdDraw.line(0, j, map.getWidth() - 1, j);
        }
        // Drew pixels as grey circles
        StdDraw.setPenColor(Color.lightGray);
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getPixel(x, y) == 0) StdDraw.setPenColor(Color.lightGray);
                else if (map.getPixel(x, y) == 1) StdDraw.setPenColor(Color.BLACK);
                else if (map.getPixel(x, y) == 2) StdDraw.setPenColor(Color.BLUE);
                else if (map.getPixel(x, y) == 3) StdDraw.setPenColor(Color.RED);
                else if (map.getPixel(x, y) == 4) StdDraw.setPenColor(Color.YELLOW);
                else if (map.getPixel(x, y) == 5) StdDraw.setPenColor(Color.GREEN);
                StdDraw.filledCircle(x, (map.getHeight() - 1) - y, 0.25);
            }
        }
        if (_p1 != null) {
            StdDraw.setPenColor(Color.MAGENTA);
            StdDraw.circle(_p1.getX(), (map.getHeight() - 1) - _p1.getY(), 0.3);
        }
        StdDraw.setPenColor(Color.BLACK);
        String status = _cyclic ? "Cyclic: ON" : "Cyclic: OFF";
        StdDraw.textLeft(0, -1.2, status);
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName)  {
        Map2D ans = null;
        try {
            java.io.File file = new java.io.File(mapFileName);
            java.util.Scanner readerForSize = new java.util.Scanner(file);
            int rows = 0;
            int cols = 0;

            while (readerForSize.hasNextLine()) {
                String line = readerForSize.nextLine().trim();
                if (line.isEmpty()) continue;
                if (rows == 0) {
                    cols = line.split("\\s+").length;
                }
                rows++;
            }
            readerForSize.close();
            ans = new Map(cols, rows,0);
            java.util.Scanner sc = new java.util.Scanner(file);
            int currentRow = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] values = line.split("\\s+");

                for (int currentCol = 0; currentCol < values.length && currentCol < cols; currentCol++) {
                    ans.setPixel(currentCol, currentRow, Integer.parseInt(values[currentCol]));
                }
                currentRow++;
            }
            sc.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ans;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    sb.append(map.getMap()[x][y]).append(" ");
                }
                sb.append("\n");
            }
            String data =  sb.toString();
            FileWriter myWriter = new FileWriter((mapFileName));
            myWriter.write(data+"\n");
            myWriter.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static Map2D handleMenu(String action, Map2D map) {
        if (action.equals("Black")) _color = 1;
        else if (action.equals("Clear")) _color = 0;
        else if (action.equals("Blue")) _color = 2;
        else if (action.equals("Red")) _color = 3;
        else if (action.equals("Yellow")) _color = 4;
        else if (action.equals("Green")) _color = 5;
        else if (action.equals("Clear")) _mode = "Clear";
        else if (action.contains("Clear all")) {
            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    map.setPixel(i, j, 0);
                }
            }
            drawMap(map);
            _p1 = null;
        }
        else if (action.contains("Save Map")) saveMap(map, "Map.txt");
        else  if (action.equals("Load")) {
            loadMap("Map.txt");
            Map2D temp = loadMap("Map.txt");
            if (temp != null) {
                map = temp; //
                drawMap(map);
            }
        }
        else if (action.equals("Point")) _mode = "Point";
        else if (action.equals("Fill")) _mode = "Fill";
        else if (action.equals("Line")) {
            _mode = "Line";
            _p1 = null;
        } else if (action.equals("Circle")) {
            _mode = "Circle";
            _p1 = null;
        } else if (action.equals("Rectangle")) {
            _mode = "Rectangle";
            _p1 = null;
        } else if (action.equalsIgnoreCase("ShortestPath")) {
            _mode = "ShortestPath";
            _p1 = null;
        } else if (action.equalsIgnoreCase("Cyclic")) {
            _cyclic = !_cyclic;
            drawMap(map);
        }
        return map;
    }
    private static void handleMouse(int x, int y, Map2D map) {
        Index2D p2 = new Index2D(x, y);
        if (_mode.equals("Point")) {
            map.setPixel(x, y, _color);
        }
        else if (_mode.equals("Fill")) {
            map.fill(p2, _color,_cyclic);
        }
        else if (_mode.equals("Clear")) {
            map.setPixel(x, y,0);
        }
        else if (_mode.equals("Line") || _mode.equals("Circle") || _mode.equals("Rectangle")) {
            if (_p1 == null) {
                _p1 = p2; // לחיצה ראשונה
            }
            else {
                if (_mode.equals("Line"))   map.drawLine(_p1, p2, _color);
                if (_mode.equals("Circle")) map.drawCircle(_p1, _p1.distance2D(p2), _color);
                if (_mode.equals("Rectangle"))   map.drawRect(_p1, p2, _color);
                _p1 = null;
            }
        }
        else if (_mode.equals("ShortestPath")) {
            if (_p1 == null) {
                _p1 = p2;
            }
            else {
                Pixel2D[] path = map.shortestPath(_p1, p2,1,_cyclic);
                if (path != null) {
                    for (int i = 0; i < path.length; i++) {
                        Pixel2D node = path[i];
                        map.setPixel(node.getX(), node.getY(), _color);
                    }
                }
                _p1 = null;
            }
        }
    }
}
