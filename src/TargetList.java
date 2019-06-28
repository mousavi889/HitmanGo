import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TargetList extends JFrame implements ActionListener
{
    int n, m;
    Cell cell;
    GameObject gameObject;

    TargetList(Cell cell, GameObject gameObject)
    {
        n = cell.mainFrame.n;
        m = cell.mainFrame.m;
        this.cell = cell;
        this.gameObject = gameObject;
        setLayout(new GridLayout(n, m, 5, 5));
        for (int i = 0; i < n; i++)

            for (int j = 0; j < m; j++)
            {
                JButton x = new JButton(Integer.toString(i * m + j));
                x.setOpaque(true);
                x.setBorder(null);
                x.setBackground(Color.getHSBColor(200, 140, 300));
                x.addActionListener(this);
                add(x);
            }
        setSize(300, 300);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
    }

    public void actionPerformed(ActionEvent e)
    {
        String id = ((JButton)e.getSource()).getText();
        int x = Integer.parseInt(id) / m, y = Integer.parseInt(id) % m;
        GameObject s = new GameObject(gameObject, x, y);
        GameObject t = new GameObject(gameObject, cell.x , cell.y);
        s.target = t;
        t.target = s;
        if (s.name.equals("Gun"))
        {
            t.isGunTarget = true;
            String twoLines = t.name + " Target\n(" + (cell.x * cell.mainFrame.m + cell.y) + ")";
            t.setFont(new Font("Arial", Font.PLAIN, 10));
            t.setText("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
            t.setBackground(t.color);
            t.setBorder(null);
            t.setOpaque(true);
        }
        cell.addGameObject(s);
        cell.mainFrame.cells[x][y].addGameObject(t);
        setVisible(false);
    }
}
