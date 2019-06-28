import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rout extends JButton implements ActionListener
{
    public int x, y, xSize, ySize, colorNumber;
    final Color colors[] = {Color.BLUE, Color.cyan};

    Rout(int x, int y, int xSize, int ySize)
    {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        colorNumber = 1;
        setBackground(colors[colorNumber]);
        setBorder(null);
        setOpaque(true);
        setBounds(this.x, this.y, this.xSize, this.ySize);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        colorNumber = (colorNumber + 1) % 2;
        setBackground(colors[colorNumber]);
    }
}