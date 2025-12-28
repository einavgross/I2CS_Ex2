# I2CS_Ex2 - 2D Map Analysis
This is a set of algorithms on 2D arrays representing a maze or an image. It includes tools for drawing shapes, filling areas (Flood Fill), and calculating the shortest path between two points, including support for cyclic maps, based on Breadth-First Search (BFS) algorithm.

**What is in this project?**
- Map Management: Initialize, copy, and modify 2D integer-based maps.
- Drawing Tools:
  - Points & Lines: Draw individual pixels or connected lines.
  - Shapes: Draw circles and rectangles.
- Fill: A "Bucket Fill" algorithm to color connected areas.
- Pathfinding: Finds the shortest path between two points while avoiding obstacles.
- File Operations: You can Save your current map to a file and Load it back later.
- Cyclic Support: The map can behave as a "wrap-around" grid (where moving off the right edge brings you back to the left edge, etc.).
- Interactive GUI: A graphical interface built with an updated version of StdDraw to interact with the map using your mouse.

**Project Structure**
- Map.java: The main implementation of the Map2D interface. It contains the BFS for pathfinding and the drawing logic for all shapes.
- Index2D.java: A class to represent a pixel coordinate $(x, y)$.
- Tests: JUnit tests in MapTest.java and Index2DTest.java to verify the math and the pathfinding logic.
- Ex2_GUI.java: The main class that runs the application and handles user inputs and the menu system.
  - Note: The main method initializes a 20x20 map by default for better aesthetics in the GUI, but this can be easily changed in the code.
- StdDraw.java: I used a modified version of this library, adding custom features like a MenuBar to support the different modes and colors of the application.
  
**How to use it?**
1. Run the Ex2_GUI.java file.
2. Colors: You can change the color by choosing a color from the menu.
3. Top Menu: You can choose what you want to draw (Point, Line, Circle,Rectangle or the Shortest path) or fill the grid with one color (excluding obstacles)
4. Click on the grid to draw or find a path. For shapes and paths, you need to click two different points (the first chosen point will be temporarily marked).
5. Cyclic Mode: You can toggle "Cyclic" mode in the 'Options' menu to change how the map boundaries and pathfinding behave.

<img width="1020" height="540" alt="image" src="https://github.com/user-attachments/assets/10212fa4-1f90-422e-886f-e13bdd8964ff" />


