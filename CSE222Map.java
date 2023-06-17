import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;

/**
 * This class keeps the map read from a txt file.
 * The main purpose of this map is reading the txt file, constructing the map, writing the paths to the png files.
 * @author Emre Oytun
 */
public class CSE222Map {
    private int startX;
    private int startY;

    private int endX;
    private int endY;

    private int[][] map;
    private String fileName;

    /**
     * Constructor to initialize the map with a file name and sizes.
     * @param inputFile The name of the file.
     * @param xSize The size of the x.
     * @param ySize The size of the y.
     * @throws Exception If the file cannot be opened.
     */
    public CSE222Map(String inputFile, int xSize, int ySize) throws Exception {
        int pointIndex = inputFile.indexOf(".");
        fileName = inputFile.substring(0, pointIndex);
        
        File file = new File(inputFile);
        Scanner scanner = new Scanner(file);
        
        // Read the start and end points.
        String line = scanner.nextLine();
        String[] startTokens = line.split(",");
        startY = Integer.parseInt(startTokens[0]);
        startX = Integer.parseInt(startTokens[1]);
        
        line = scanner.nextLine();
        String[] endTokens = line.split(",");
        endY = Integer.parseInt(endTokens[0]);
        endX = Integer.parseInt(endTokens[1]);

        // Initialize and fill in the map.
        map = new int[ySize][xSize];
        int y = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();

            String[] nodes = line.split(",");
            for (int x = 0; x < map[0].length; ++x) {   // Be careful here not to miss the (y,x) detail.
                int num = Integer.parseInt(nodes[x]);
                if (num == -1) num = 1; // Be careful for -1.
                map[y][x] = num;
            }
            ++y;
        }
        scanner.close();
        
    }   

    /**
     * Gets the element whose coordinates are given.
     * @param rowIndex The row coordinate. 
     * @param colIndex The col coordinate.
     * @return An integer representing the element in the given coordinate.
     */
    public int get(int rowIndex, int colIndex) {
        return map[rowIndex][colIndex];
    }

    /**
     * Gets the length of the map.
     * @return An integer representing the map's lenght.
     */
    public int getLength() {
        return map.length;
    }

    /**
     * Gets the start node's x coordinate.
     * @return An integer representing the x coordinate of the start node.
     */
    public int startX() {
        return startX;
    }

    /**
     * Gets the start node's y coordinate.
     * @return An integer representing the y coordinate of the start node.
     */
    public int startY() {
        return startY;
    }

    /**
     * Gets the end node's x coordinate.
     * @return An integer representing the x coordinate of the end node.
     */
    public int endX() {
        return endX;
    }

    /**
     * Gets the end node's y coordinate.
     * @return An integer representing the y coordinate of the end node.
     */
    public int endY() {
        return endY;
    }

    /**
     * This method converts the map to the png.
     * @throws IOException If there is a problem with writing the map to the png file.
     */
    public void convertPNG() throws IOException {
        // Write the map to the png file.
        int lineCount = map.length;
        BufferedImage image = new BufferedImage(lineCount, lineCount, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < lineCount; ++row)  {
            for (int col = 0; col < lineCount; ++col) {
                int pixelValue = map[row][col] == 0 ? Color.WHITE.getRGB() : Color.GRAY.getRGB();
                image.setRGB(col, row, pixelValue);
            }
        }
        
        String pngName = fileName + ".png";
        File outputPng = new File(pngName);
        ImageIO.write(image, "png", outputPng);
    }

    /**
     * This method draws the given line onto the png.
     * @param list The list of the nodes/coordinates to be drawn with red.
     * @param resultName The name of the resulting list.
     * @throws Exception If there is a problem with the reading the map png or writing onto the new png file.
     */
    public void drawLine(List<Node> list, String resultName) throws Exception {        
        String pngName = fileName + ".png";
        BufferedImage image = ImageIO.read(new File(pngName));

        for (Node node : list) {
            int pixelValue = Color.RED.getRGB();
            image.setRGB(node.getX(), node.getY(), pixelValue);
        }

        String resultPngName = fileName + "_" + resultName + ".png";
        File outputPng = new File(resultPngName);
        ImageIO.write(image, "png", outputPng);
    }

    /**
     * Writes the given path to the txt file.
     * @param list The list of the coordinates to write.
     * @param resultName The file name.
     * @throws IOException If there is a problem with writing to the file.
     */
    public void writePath(List<Node> list, String resultName) throws IOException {
        String pathName = fileName + "_" + resultName + "_path.txt";
        File outputFile = new File(pathName);
        FileWriter fileWriter = new FileWriter(outputFile);

        for (Node node : list) {
            fileWriter.append("" + node.getY() + "," + node.getX() + "\n");
        }
        fileWriter.close();
    }

}