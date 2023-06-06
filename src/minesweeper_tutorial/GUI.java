package minesweeper_tutorial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class GUI extends JFrame {
    int margins = 8;
    int top_padding = 30;
    int mx = 0;
    int my = 0;

    Random rand = new Random();

    //arrays
    boolean[][] contains_mine = new boolean[16][9];
    int[][] nearby_mines = new int[16][9];
    boolean[][] revealed = new boolean[16][9];
    boolean[][] flagged = new boolean[16][9];

    int initial_mines = 50;

    public GUI()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Minesweeper");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 600, dimension.height/2 - 400, 1296, 839);
        this.setResizable(false);

        int marked_mines = 0;
        int decreased_chance = 0;
        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                // calculating left bomb_chance
                contains_mine[i][j] = (rand.nextInt(16 * 9 - decreased_chance)  < initial_mines - marked_mines);
                if(contains_mine[i][j]) {marked_mines++;}
                decreased_chance++;
                revealed[i][j] = false;
                nearby_mines[i][j] = 0;
            }
        }

        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {

                for(int m = i - 1; m <= i + 1; ++m)
                {
                    for(int n = j - 1; n <= j + 1; ++n)
                    {
                        if (n == -1 || m == -1 || m == 16 || n == 9 || (i == m && j == n) ) { continue;}
                        if(contains_mine[m][n])
                        {
                            nearby_mines[i][j]++;
                        }
                    }
                }
            }
        }

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public class Board extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, 1296, 829);
            g.setColor(Color.GRAY);

            for(int i = 0; i < 16; ++i)
            {
                for(int j = 0; j < 9; ++j)
                {
                    g.setColor(Color.GRAY);
                    if(contains_mine[i][j])
                    {
                        g.setColor(Color.YELLOW);
                    }
                    if(revealed[i][j])
                    {
                        g.setColor(Color.white);
                        if(contains_mine[i][j])
                        {
                            g.setColor(Color.red);
                        }
                    }
                    if(mx >= margins * 2 + 80 * i &&
                            mx <= 80*(i+1) &&
                            my >= 80 + margins + 80 * j + top_padding &&
                            my <= top_padding + 80 + 80*(j+1) - margins)
                    {
                        g.setColor(Color.lightGray);
                    }

                    g.fillRect(margins + 80 * i, margins + 80 + 80 * j, 80 - margins * 2, 80 - margins * 2);

                    //again revealed[i][j] as we can't change a colour from a single condition
                    if(revealed[i][j])
                    {
                        g.setColor(Color.black);
                        if(!contains_mine[i][j])
                        {
                            g.setFont(new Font("Tahoma", Font.BOLD, 40));
                            g.drawString(Integer.toString(nearby_mines[i][j]), 20 + margins + 80 * i, top_padding + 25 + 80 * (j+1));
                        }
                        else {
                            g.fillOval(i * 80 + margins + 11, j * 80 + 80 + top_padding - 10, 40, 40);
                        }
                    }

                }
            }
        }
    }
    public class Move implements MouseMotionListener
    {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            //System.out.println("X: " + mx +  ", Y: " + my);
        }
    }

    public class Click implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = inBoxY();
            int col = inBoxX();
            if (row != -1 && col != -1)
            {
                revealed[inBoxX()][inBoxY()] = true;
                System.out.println("Row: " + row + ", Col: " + col + " Neighbour mines: " + nearby_mines[inBoxX()][inBoxY()]);
            }

            /*if(row != -1 && col != -1)
            {
                System.out.println("Row: " + inBoxY() + ", Col: " + inBoxX());
            }*/
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        public int inBoxX()
        {
            for(int i = 0; i < 16; ++i)
            {
                if(mx >= margins * 2 + 80 * i &&
                        mx <= 80*(i+1))
                {
                    return i;
                }
            }
            return -1;
        }

        public int inBoxY()
        {
            for(int j = 0; j < 9; ++j)
            {
                if(my >= 80 + margins + 80 * j + top_padding &&
                        my <= top_padding + 80 + 80*(j+1) - margins)
                {
                    return j;
                }
            }
            return -1;
        }

        public boolean isNeighbour(int main_box_X, int main_box_Y, int side_box_X, int side_box_Y)
        {

            if(Math.abs(main_box_X - side_box_X) < 2 && Math.abs(main_box_Y - side_box_Y) < 2 && contains_mine[side_box_X][side_box_Y])
            {
                return true;
            }
            return false;
        }

    }


}
