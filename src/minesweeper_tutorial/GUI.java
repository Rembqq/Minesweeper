package minesweeper_tutorial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class GUI extends JFrame {

    public boolean resetter = false;

    Date startDate = new Date();
    Date endDate;
    int margins = 5;
    int top_padding = 30;
    public int mx = 0;
    public int my = 0;

    public int smiley_x = 605;
    public int smiley_y = 5;

    public int smileyCenter_X = smiley_x + 35;
    public int smileyCenter_Y = smiley_y + 35;

    public int timer_X = 1120;
    public int timer_Y = 5;

    String GameResMess = "";
    public int VicMessage_X = 15;
    public int VicMessage_Y = -50;

    public int sec = 0;
    public int min = 0;

    public boolean happy_face = true;

    public boolean victory = false;
    public boolean defeat = false;


    Random rand = new Random();

    //arrays
    boolean[][] contains_mine = new boolean[16][9];
    Color[] num_colours = new Color[] {Color.BLUE, new Color(33, 156, 41), new Color(122, 108, 91), new Color(0, 0, 128),
            new Color(64, 49, 11), new Color(33, 131, 156),
            Color.BLACK, new Color(17, 30, 33)};
    int[][] nearby_mines = new int[16][9];
    boolean[][] revealed = new boolean[16][9];
    boolean[][] flagged = new boolean[16][9];

    int initial_mines = 40;
    int user_mines_left = initial_mines;

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
        resetAll();

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, 1296, 829);
            g.setColor(Color.GRAY);

            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 9; ++j) {
                    g.setColor(Color.GRAY);
                    /*if (contains_mine[i][j]) {
                        g.setColor(new Color(176, 108, 40));
                    }*/
                    /*if (contains_mine[i][j]) {
                        g.setColor(new Color(245, 29, 90));
                    }*/
                    if (revealed[i][j]) {
                        g.setColor(new Color(175, 186, 189));
                        if (contains_mine[i][j]) {
                            g.setColor(new Color(245, 29, 90));
                        }
                    }
                    if (mx >= margins * 2 + 80 * i &&
                            mx <= 80 * (i + 1) &&
                            my >= 80 + margins + 80 * j + top_padding &&
                            my <= top_padding + 80 + 80 * (j + 1) - margins) {
                        g.setColor(Color.lightGray);
                    }

                    g.fillRect(margins + 80 * i, margins + 80 + 80 * j, 80 - margins * 2, 80 - margins * 2);

                    //again revealed[i][j] as we can't change a colour from a single condition
                    if (revealed[i][j]) {

                        if (!contains_mine[i][j] && nearby_mines[i][j] != 0) {
                            g.setColor(num_colours[nearby_mines[i][j] - 1]);
                            g.setFont(new Font("Tahoma", Font.BOLD, 45));
                            g.drawString(Integer.toString(nearby_mines[i][j]), 18 + margins + 80 * i, top_padding + margins + 22 + 80 * (j + 1));
                        } else if(contains_mine[i][j]){
                            g.setColor(Color.BLACK);
                            g.fillOval(i * 80 + margins + 12, j * 80 + 80 + top_padding - 10, 40, 40);
                            g.fillRect(i * 80 + margins + 30, j * 80 + 80 + top_padding - 17, 4, 56);
                            g.fillRect(i * 80 + margins + 5, j * 80 + 80 + top_padding + 10, 56, 4);
                        }
                    }

                }
            }

            // smiley face picture

            g.setColor(Color.YELLOW);
            g.fillOval(smiley_x, smiley_y, 70, 70);
            g.setColor(Color.GRAY);
            g.fillRect(smiley_x + 19, smiley_y + 22, 8, 8);
            g.fillRect(smiley_x + 43, smiley_y + 22, 8, 8);
            if(happy_face)
            {
                g.fillRect(smiley_x + 15, smiley_y + 45, 5, 5);
                g.fillRect(smiley_x + 50, smiley_y + 45, 5, 5);
                g.fillRect(smiley_x + 20, smiley_y + 50, 5, 5);
                g.fillRect(smiley_x + 45, smiley_y + 50, 5, 5);
                g.fillRect(smiley_x + 25, smiley_y + 55, 20, 4);
            }
            else
            {
                g.fillRect(smiley_x + 15, smiley_y + 50, 5, 5);
                g.fillRect(smiley_x + 50, smiley_y + 50, 5, 5);
                g.fillRect(smiley_x + 20, smiley_y + 45, 5, 5);
                g.fillRect(smiley_x + 45, smiley_y + 45, 5, 5);
                g.fillRect(smiley_x + 25, smiley_y + 45, 20, 4);
            }

            // timer panel
            g.setColor(Color.BLACK);
            g.fillRect(timer_X, timer_Y, 170, 80 - margins);
            if(!defeat && !victory)
            {
                sec = (int)((new Date().getTime() - startDate.getTime()) / 1000);
            }
            if(sec > 999)
            {
                sec = 999;
            }
            g.setColor(Color.RED);
            if(victory) {
                g.setColor(Color.green);
            }

            g.setFont(new Font("Century Schoolbook", Font.PLAIN, 80));

            if(sec < 10)
            {
                g.drawString("00" + Integer.toString(sec), timer_X + 15, timer_Y + 65);
            }

            else if(sec < 100){
                g.drawString( "0" + Integer.toString(sec), timer_X + 15, timer_Y + 65);
            }
            else {
                g.drawString(Integer.toString(sec), timer_X + 15, timer_Y + 65);
            }

            // Victory message

            if(victory)
            {
                g.setColor(Color.green);
                GameResMess = "Congrats! YOU WIN!";
            } else if(defeat)
            {
                g.setColor(Color.red);
                GameResMess = "YOU LOSE";
            }

            if(victory || defeat)
            {
                 VicMessage_Y = -50 + (int)(new Date().getTime() - endDate.getTime()) / 10;
                 if(VicMessage_Y > 60)
                 {
                     VicMessage_Y = 60;
                 }
                 g.setFont(new Font("Sitka Text", Font.PLAIN, 60));
                g.drawString(GameResMess, VicMessage_X, VicMessage_Y);
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

        }
    }

    public class Click implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            int row = inBoxY();
            int col = inBoxX();
            if (row != -1 && col != -1)
            {
                revealed[inBoxX()][inBoxY()] = true;
                //System.out.println("Row: " + row + ", Col: " + col + " Neighbour mines: " + nearby_mines[inBoxX()][inBoxY()]);
            }
            if(inSmiley())
            {
                resetAll();
                /*System.out.println("In smiley");*/
            }
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

        public boolean inSmiley()
        {
            int diff = (int)Math.sqrt(Math.abs(mx - smileyCenter_X) * Math.abs(mx - smileyCenter_X) +
                    Math.abs(my - smileyCenter_Y) * Math.abs(my - smileyCenter_Y));
            return diff <= 35;
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

    }

    public void checkVictory()
    {
        if(!defeat) {
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (revealed[i][j] && contains_mine[i][j]) {
                        defeat = true;
                        happy_face = false;
                        endDate = new Date();
                    }
                }
            }
        }
        if(totalBoxesRevealed() + initial_mines >= 144 && !victory)
        {
            victory = true;
            endDate = new Date();
        }
    }

    public void userMinesLeft()
    {
        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                if (flagged[i][j])
                {
                    user_mines_left--;
                }
            }
        }
    }

    public int totalBoxesRevealed()
    {
        int total = 0;
        for(int i = 0; i < 16; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                if (revealed[i][j])
                {
                    total++;
                }
            }
        }
        return total;
    }

    public void resetAll()
    {

        resetter = true;
        startDate = new Date();

        VicMessage_Y = -300;

        happy_face = true;
        victory = false;
        defeat = false;
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
                flagged[i][j] = false;
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
        resetter = false;
    }


}
