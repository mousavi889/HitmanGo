import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeGetter extends JFrame implements ActionListener
{
    JTextField n, m;
    GameManager gameManager;

    SizeGetter(GameManager gameManager)
    {
        setVisible(true);
        setSize(460, 110);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        n = new JTextField();
        n.setBounds(20, 45, 100, 30);
        m = new JTextField();
        m.setBounds(140, 45, 100, 30);
        add(n);
        add(m);
        JLabel xLable = new JLabel("X: ");
        xLable.setBounds(25, 20, 15, 10);
        xLable.setFont(new Font("Arial", Font.PLAIN, 12));
        add(xLable);
        JLabel yLable = new JLabel("Y: ");
        yLable.setBounds(145, 20, 15, 10);
        yLable.setFont(new Font("Arial", Font.PLAIN, 12));
        add(yLable);
        JButton ok = new JButton("OK");
        ok.setBounds(300, 30, 70, 40);
        ok.addActionListener(this);
        add(ok);
        this.gameManager = gameManager;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        dispose();
        new MainFrame(Integer.parseInt(n.getText()), Integer.parseInt(m.getText()), gameManager);
    }
}
