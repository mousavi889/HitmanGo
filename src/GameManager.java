import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GameManager implements MouseListener
{
    private File gameFile, gameUpdate;
    GameView gameView;
    Logic hitmanGo;
    JFrame frame;
    int state;

     void start()
    {
        gameFile = new File("file.txt");
        gameUpdate = new File("ttt.txt");
        frame = new JFrame();
        frame.addMouseListener(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1500, 800);
        hitmanGo = new Logic(gameFile);
        gameView = new GameView(1500, 800, gameUpdate);
        gameView.addMouseListener(this);
        gameView.initial(gameFile);
        frame.setContentPane(gameView);
        frame.repaint();
    }

    public void manage()
    {
        new SizeGetter(this);
       // start();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        for (int i = 0; i < gameView.n; i++)
            for (int j = 0; j < gameView.m; j++)
                if (gameView.cells[i][j].isIn(x, y - 45))
                {
                    state = hitmanGo.play(i, j);
                    gameView.state = state;
                    frame.repaint();
                }
        if(x < 100 && y < 100)
        {
            frame.dispose();
            start();
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}