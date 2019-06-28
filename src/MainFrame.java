import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class MainFrame extends JFrame implements ActionListener
{
    public final int X_SIZE = 1400, Y_SIZE = 700;
    public final int f = 10, e = 2, g = 12;
    static int n, m;
    Cell[][] cells;
    Rout[][] xRoutes, yRoutes;
    JButton nextButton = new JButton("Next");
    GameManager gameManager;

    MainFrame(int n, int m, GameManager gameManager)
    {
        this.n = n;
        this.m = m;
        this.gameManager = gameManager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        cells = new Cell[n][m];
        makeCells(cells, mainPanel);

        xRoutes = new Rout[n - 1][m];
        yRoutes = new Rout[n][m - 1];
        makeRoutes(xRoutes, yRoutes, mainPanel);

        nextButton.addActionListener(this);
        nextButton.setBounds(X_SIZE - 70, Y_SIZE + 20, 60, 40);
        add(nextButton);

        setSize(X_SIZE, Y_SIZE + 140);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        try
        {
            save("file.txt");
            this.dispose();

        }
        catch (IOException iOException)
        {
            iOException.printStackTrace();
        }
        finally
        {
            this.dispose();
            return;
        }
    }

    void makeCells(Cell[][] cells, JPanel mainPanel)
    {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
            {
                cells[i][j] = new Cell(i, j, this);
                cells[i][j].setBounds(findCellX(j), findCellY(i), findCellXSize(), findCellYSize());
                mainPanel.add(cells[i][j]);
            }
    }

    public void makeRoutes(Rout[][] xRoutes, Rout[][] yRoutes, JPanel mainPanel)
    {
        int xRoutesXSize = findXRoutesXSize(), xRoutesYSize = findXRoutesYSize(), yRoutesXSize = findYRoutesXSize(), yRoutesYSize = findYRoutesYSize();

        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < m; j++)
            {
                xRoutes[i][j] = new Rout(findXRouteX(j), findXRouteY(i), xRoutesXSize, xRoutesYSize);
                mainPanel.add(xRoutes[i][j]);
            }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m - 1; j++)
            {
                yRoutes[i][j] = new Rout(findYRoutesX(j), findYRoutesY(i), yRoutesXSize, yRoutesYSize);
                mainPanel.add(yRoutes[i][j]);
            }
    }

    public int findCellX(int i)
    {
        return X_SIZE * (i * g + e) / (m * f + (m + 1) * e);
    }

    public int findCellY(int j)
    {
        return Y_SIZE * (j * g + e) / (n * f + (n + 1) * e);
    }

    public int findCellXSize()
    {
        return X_SIZE * f / (m * f + (m + 1) * e);
    }

    public int findCellYSize()
    {
        return Y_SIZE * f / (n * f + (n + 1) * e);
    }

    public int findXRoutesXSize()
    {
        return findCellXSize() / 5;
    }

    public int findXRoutesYSize()
    {
        return Y_SIZE * e / (n * f + (n + 1) * e);
    }

    public int findYRoutesXSize()
    {
        return X_SIZE * e / (m * f + (m + 1) * e);
    }

    public int findYRoutesYSize()
    {
        return findCellYSize() / 5;
    }

    public int findXRouteX(int i)
    {
        return findCellX(i) + findCellXSize() * 2 / 5;
    }

    public int findXRouteY(int j)
    {
        return findCellY(j) + findCellYSize();
    }

    public int findYRoutesX(int i)
    {
        return findCellX(i) + findCellXSize();
    }

    public int findYRoutesY(int j)
    {
        return findCellY(j) + findCellYSize() * 2 / 5;
    }

    public void removeRoutes(int x, int y)
    {
        if (x > 0)
            xRoutes[x - 1][y].colorNumber = 0;
        if (x < n - 1)
            xRoutes[x][y].colorNumber = 0;
        if (y > 0)
            yRoutes[x][y - 1].colorNumber = 0;
        if (y < m - 1)
            yRoutes[x][y].colorNumber = 0;



    }
    public void save(String destination) throws IOException
    {
        File file = new File(destination);
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        writer.println(n + " " + m + "\n");

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (cells[i][j].colorNumber == 0)
                {
                    for (int k = 0; k < cells[i][j].objects.size(); k++)
                    {
                        GameObject x = cells[i][j].objects.get(k);
                        if (x.index == 4 || x.index == 5)
                            cells[x.xTarget][x.yTarget].objects.remove(x.target);
                    }
                    removeRoutes(i, j);
                }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++, writer.println(), writer.println())
            {
                writer.println("( " + i + " , " + j + " )");
                writer.println(cells[i][j].colorNumber);
                writeRoutes(i, j, writer);
                if (cells[i][j].colorNumber == 0)
                    continue;
                for (int k = 0; k < cells[i][j].objects.size(); k++)
                {
                    GameObject x = cells[i][j].objects.get(k);
                    if(x.isGunTarget)
                        continue;
                    writer.print(x.index + " " + x.name);
                    if (x.index >= ObjectList.TOOLS_NUMBER_NT && x.index < ObjectList.TOOLS_NUMBER && !x.isGunTarget)
                    {
                        writer.print(" " + x.xTarget + " " + x.yTarget);
                    }
                    if (x.index >= ObjectList.TOOLS_NUMBER)
                        writer.print(" " + x.direction);
                    writer.println();
                }
            }
        writer.println("(");
        writer.flush();
        writer.close();
        gameManager.start();
    }

    private void writeRoutes(int x, int y, PrintWriter writer)
    {
        int r0 = 0, r1 = 0, r2 = 0, r3 = 0;
        if(x > 0)
            r0 = xRoutes[x - 1][y].colorNumber;
        if(y < m - 1)
            r1 = yRoutes[x][y].colorNumber;
        if (x < n - 1)
            r2 = xRoutes[x][y].colorNumber;
        if (y > 0)
            r3 = yRoutes[x][y - 1].colorNumber;
        writer.println(r0 + " " + r1 + " " + r2 + " " + r3);
    }
}