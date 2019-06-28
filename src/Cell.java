import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Cell extends JPanel implements ActionListener
{
    int colorNumber, x, y;
    ArrayList<GameObject> objects = new ArrayList<GameObject>();
    Color[] colors = {Color.BLACK, Color.getHSBColor(90, 140, 300)};
    MainFrame mainFrame;

    Cell(int x, int y, MainFrame mainFrame)
    {
        this.x = x;
        this.y = y;
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(2, 3, 1, 1));
        this.colorNumber = 1;
        setBackground(colors[colorNumber]);
        JButton select = new JButton();
        String twoLines = "Select" + "\n(" + (x * MainFrame.m + y) + ")";
        select.setFont(new Font("Arial", Font.PLAIN, 11));
        select.setText("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
        select.addActionListener(this);
        add(select);
        add(new ExistButton(this));
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof GameObject)
        {
            GameObject gameObject = (GameObject) (e.getSource());
            this.remove(gameObject);
            this.objects.remove(gameObject);
            if (gameObject.target != null)
            {
                Cell targetCell = mainFrame.cells[gameObject.xTarget][gameObject.yTarget];
                targetCell.remove(gameObject.target);
                targetCell.objects.remove(gameObject.target);
                targetCell.revalidate();
                targetCell.repaint();
            }
            this.revalidate();
            this.repaint();
        }
        else
            new ObjectList(this);
    }

    public void addGameObject(GameObject gameObject)
    {
        gameObject.addActionListener(this);
        add(gameObject, 0);
        objects.add(gameObject);
        revalidate();
        repaint();
    }
}