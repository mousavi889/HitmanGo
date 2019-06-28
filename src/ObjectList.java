import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ObjectList extends JFrame implements ActionListener
{
    Cell cell;
    final static String[] objectNames = {"Target", "Pot", "Camera", "Stone", "Subway", "Gun", "Hitman", "StaticEnemy", "SpinicEnemy", "DynamicEnemy", "SighticEnemy", "SmellicEnemy"};
    final static Color[] objectColors = {Color.RED, Color.getHSBColor(20, 20, 20), Color.GRAY, Color.LIGHT_GRAY, Color.BLUE, Color.YELLOW, Color.CYAN, Color.PINK, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.getHSBColor(90, 90, 90)};

    final static int TOOLS_NUMBER = 6, OBJECT_NUMBER =12, TOOLS_NUMBER_NT = 4;

    ObjectList(Cell cell)
    {
        setLayout(new GridLayout(3, 4));
        setSize(400, 400);
        this.cell = cell;
        GameObject[] gameObjects = new GameObject[OBJECT_NUMBER];
        for (int i = 0; i < 12; i++)
        {
            gameObjects[i] = new GameObject(objectNames[i], objectColors[i]);
            gameObjects[i].index = i;
            gameObjects[i].addActionListener(this);
            add(gameObjects[i]);
        }
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        GameObject gameObject = (GameObject)e.getSource();
        if (gameObject.index < TOOLS_NUMBER_NT)
        {
            GameObject next = new GameObject(gameObject.name, gameObject.color);
            next.index = gameObject.index;
            cell.addGameObject(next);
        }
        else if (gameObject.index < TOOLS_NUMBER)
        {
            new TargetList(cell, gameObject);
        }
        else
            new DirectionList(gameObject, cell);
        setVisible(false);
    }
}
