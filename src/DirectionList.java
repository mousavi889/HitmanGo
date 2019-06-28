import javax.swing.*;
import java.awt.*;

public class DirectionList extends JFrame
{
    Direction[] directions = new Direction[4];
    Cell cell;
    final static String[] directionNames = {"UP", "Right", "Down", "Left"};

    DirectionList(GameObject gameObject, Cell cell)
    {
        this.cell = cell;
        setLayout(new GridLayout(2, 2, 5, 5));
        for (int i = 0; i < 4; i++)
        {
            directions[i] = new Direction(i, this, cell, gameObject);
            add(directions[i]);
        }
        setSize(200, 200);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
    }
}