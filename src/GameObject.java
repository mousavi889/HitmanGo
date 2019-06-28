import javax.swing.*;
import java.awt.*;

public class GameObject extends JButton
{
    int direction;
    int index;
    String name;
    int  xTarget, yTarget;
    GameObject target;
    Color color;
    boolean isGunTarget = false;

    GameObject(String name, Color color)
    {
        super(name);
        this.color = color;
        this.name = name;
        setBackground(color);
        setBorder(null);
        setOpaque(true);
    }

    GameObject(int index, int direction)
    {
        String twoLines = ObjectList.objectNames[index] + "\n(" + DirectionList.directionNames[direction] + ")";
        setFont(new Font("Arial", Font.PLAIN, 10));
        setText("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
        this.index = index;
        this.direction = direction;
        this.name = ObjectList.objectNames[this.index];
        this.color = ObjectList.objectColors[this.index];
        setBackground(color);
        setBorder(null);
        setOpaque(true);
    }

    GameObject(GameObject gameObject, int x, int y)
    {
        String twoLines = gameObject.name + "\n(" + (x * MainFrame.m + y) + ")";
        setFont(new Font("Arial", Font.PLAIN, 10));
        setText("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
        this.color = gameObject.color;
        this.name = gameObject.name;
        this.index = gameObject.index;
        this.xTarget = x;
        this.yTarget = y;
        setBackground(color);
        setBorder(null);
        setOpaque(true);
    }
}
