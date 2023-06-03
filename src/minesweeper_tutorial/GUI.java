package minesweeper_tutorial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class GUI extends JFrame{
    int margins = 8;
    int top_padding = 30;
    int mx = 0;
    int my = 0;

    public GUI()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Minesweeper");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width/2 - 600, dimension.height/2 - 400, 1296, 839);
        this.setResizable(false);

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
                    g.setColor(Color.PINK);
                    //if(mx % 80 >= margins * 2 && my >= 80 + 80 * j + margins && my <= 80 + 80*(j+1) - margins
                    // /*&& mx % 80 < 80 - margins + 10*/)
                    if(mx >= margins * 2 + 80 * i &&
                            mx <= 80*(i+1) &&
                            my >= 80 + margins + 80 * j + top_padding &&
                            my <= top_padding + 80 + 80*(j+1) - margins)
                    {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect(margins + 80 * i, margins + 80 + 80 * j, 80 - margins * 2, 80 - margins * 2);
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
            System.out.println("X: " + mx +  ", Y: " + my);
        }
    }

    public class Click implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Clicked");
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
    }


}
