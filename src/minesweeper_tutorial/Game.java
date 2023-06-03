package minesweeper_tutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {
    private Board layout;
    // set up
    public static final int width = 20;
    public static final int height = 20;
    public static final int X = 30;
    public static final int Y = 16;
    protected JFrame window;
    // game attributes
    public static final int bomb_amount = 50;
    private int scoreboard_width = 100;
    private int scoreboard_heigth = 100;
    private boolean end = false;
    private boolean win = false;
    private int flags;

    public Game() {
        window = new JFrame();
        layout = new Board();
        flags = bomb_amount;
        window.setTitle("Minesweeper");
        window.setSize(X * width, Y * height);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException
    {
        Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
