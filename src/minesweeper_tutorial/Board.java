package minesweeper_tutorial;


import java.io.IOException;

public class Board {
    private Space[][] board = new Space[Game.Y][Game.X];
    public Board()
    {
        int remainder = Game.bomb_amount;
        double probability = (double)Game.bomb_amount / (Game.X * Game.Y);
        try{
            for(int i = 0; i < Game.Y; ++i)
            {
                for(int j = 0; j < Game.X; ++j)
                {
                    if(Math.random() < probability && remainder > 0)
                    {
                        board[i][j] = new Space(true);
                        remainder--;
                    }
                    else {
                        board[i][j] = new Space(false);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void calculate()
    {
        for(int y = 0; y < Game.Y; ++y)
        {
            for(int x = 0; x < Game.X; ++x)
            {
                
            }
        }
    }
}
