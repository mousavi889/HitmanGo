import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Direction extends JButton implements ActionListener
{
    int direction;
    Cell cell;
    GameObject gameObject;
    DirectionList directionList;

    Direction(int direction, DirectionList directionList, Cell cell, GameObject gameObject)
    {
        super(DirectionList.directionNames[direction]);
        this.direction = direction;
        this.cell = cell;
        this.gameObject = gameObject;
        this.directionList = directionList;
        addActionListener(this);
        setVisible(true);
        setBorder(null);
        setOpaque(true);
        setBackground(Color.getHSBColor(90, 140, 300));
    }

    public void actionPerformed(ActionEvent e)
    {
        GameObject newGameObject = new GameObject(gameObject.index, direction);
        cell.addGameObject(newGameObject);
        directionList.setVisible(false);
    }
}