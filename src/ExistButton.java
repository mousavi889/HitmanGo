import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistButton extends JButton
{
    final Cell cell;

    ExistButton(final Cell cell)
    {
        this.cell = cell;
        add(new JLabel("isExit"));
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cell.colorNumber = (cell.colorNumber + 1) % 2;
                cell.setBackground(cell.colors[cell.colorNumber]);

            }
        });
    }
}
