package minesweeper_tutorial;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Space {
    private boolean bomb;
    private boolean flagged;
    private boolean cleared;
    private int bombNearby;
    private BufferedImage image;
    public Space(boolean bomb) throws IOException
    {
        this.bomb = bomb;
        flagged = false;
        cleared = false;
        bombNearby = 0;
        BufferedImage temp = ImageIO.read(new File("minesweeper_tutorial/imgs/facingDown.png"));
        image = Game.resizeImage(temp, Game.width, Game.height);
    }
    public void setFlagged(boolean f)
    {
        flagged = f;
    }
    public void clear()
    {
        cleared = true;
    }
    public boolean hasBomb()
    {
        return bomb;
    }
    public boolean isCleared()
    {
        return cleared;
    }
    public boolean isFlagged()
    {
        return flagged;
    }

    public int setBombNearby(boolean f)
    {
        return bombNearby;
    }
    public void setImage(String filename) throws IOException
    {
        BufferedImage temp = ImageIO.read(new File(filename));
        image = Game.resizeImage(temp, Game.width, Game.height);
    }
    public BufferedImage getImage()
    {
        return image;
    }
}
