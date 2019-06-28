import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class GameView extends JPanel
{
    Cell[][] cells;
    Route[][] xRoutes, yRoutes;
    final int X_SIZE, Y_SIZE;
    int n, m;
    public final int f = 10, e = 2, g = 12;
    final static Color[] objectColors = {Color.RED, Color.getHSBColor(20, 20, 20), Color.GRAY, Color.LIGHT_GRAY, Color.BLUE, Color.YELLOW, Color.CYAN, Color.PINK, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.getHSBColor(90, 90, 90)};
    int state = 0;
    final int MAX_CELL_OBJECT = 3;
    final String[] winner = {"", "Hitman", "Enemies"};
    File gameUpdate;

    GameView(int xSize, int ySize, File gameUpdate)
    {
        setBackground(Color.BLACK);
        X_SIZE = xSize;
        Y_SIZE = ySize;
        this.gameUpdate = gameUpdate;
    }

    void initial(File gameFile)
    {
        try
        {
            Scanner fileReader = new Scanner(gameFile);
            n = fileReader.nextInt();
            m = fileReader.nextInt();
            fileReader.nextLine();
            cells = new Cell[n][m];
            xRoutes = new Route[n - 1][m];
            yRoutes = new Route[n][m - 1];
            makeCells();
            makeRoutes();

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                {
                    fileReader.nextLine();
                    fileReader.nextLine();
                    if (fileReader.nextInt() == 1)
                        cells[i][j].exist = true;
                    fileReader.nextLine();
                    if (fileReader.nextInt() == 1)
                        xRoutes[i - 1][j].exist = true;
                    if (fileReader.nextInt() == 1)
                        yRoutes[i][j].exist = true;
                    if (fileReader.nextInt() == 1)
                        xRoutes[i][j].exist = true;
                    if (fileReader.nextInt() == 1)
                        yRoutes[i][j - 1].exist = true;
                    fileReader.nextLine();
                    while((fileReader.nextLine()).length() != 0)
                    {
                    }
                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void drawEnemies(Graphics2D g, int direction, int x, int y, int xSize, int ySize)
    {
        int xDirection = 0, yDirection = 0;
        switch (direction)
        {
            case 0:
                xDirection = x + xSize / 2 - xSize / 10;
                yDirection = y + 3;
                break;
            case 1:
                xDirection = x + xSize - xSize / 5 - 3;
                yDirection = y + ySize / 2 - ySize / 10;
                break;
            case 2:
                xDirection = x + xSize / 2 - xSize / 10;
                yDirection = y + ySize - ySize / 5 - 3;
                break;
            case 3:
                xDirection = x + 3;
                yDirection = y + ySize / 2 - ySize / 10;
        }
        g.fillOval(xDirection, yDirection, xSize / 5, ySize / 5);
    }
    void draw(Graphics2D g)
    {

        final BufferedImage reset;
        try
        {
            reset = ImageIO.read(new File("reset.jpg"));
            g.drawImage(reset, 0, 0, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (state != 0)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 48));
            g.drawString(winner[state]+ " Won.", 600, 350);
            return;
        }

        g.setPaint(Color.GRAY);
        for (int i = 0; i < n; i++)
            for (int j = 0; j  < m; j++)
            {
                if (cells[i][j].exist)
                    g.drawRect(cells[i][j].x, cells[i][j].y, cells[i][j].xSize, cells[i][j].ySize);
                cells[i][j].numberOfObjects = 0;
            }

        g.setColor(Color.GRAY);

        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < m; j++)
                if(xRoutes[i][j].exist)
                    g.fillRect(xRoutes[i][j].x, xRoutes[i][j].y, xRoutes[i][j].xSize, xRoutes[i][j].ySize);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m - 1; j++)
                if(yRoutes[i][j].exist)
                    g.fillRect(yRoutes[i][j].x, yRoutes[i][j].y, yRoutes[i][j].xSize, yRoutes[i][j].ySize);


        Scanner scanner;
        try
        {
            scanner = new Scanner(gameUpdate);
            for (int i = 0; i< n; i++)
                for (int j = 0; j < m; j++)
                {
                    cells[i][j].numberOfObjects = 0;

                    scanner.nextLine();
                    String object = scanner.next();
                    while(object.charAt(0) != '(')
                    {
                        int kind = Integer.parseInt(object);
                        g.setColor(objectColors[kind]);
                        int x = cells[i][j].x + (cells[i][j].numberOfObjects % MAX_CELL_OBJECT)* cells[i][j].xSize / MAX_CELL_OBJECT;
                        int y = cells[i][j].y +  cells[i][j].numberOfObjects / MAX_CELL_OBJECT * cells[i][j].ySize/ MAX_CELL_OBJECT;
                        int xSize = cells[i][j].xSize / MAX_CELL_OBJECT;
                        int ySize = cells[i][j].ySize / MAX_CELL_OBJECT;
                        g.fillRect(x, y, xSize, ySize);
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Arial", Font.BOLD, 10));
                        g.drawString(ObjectList.objectNames[kind], x, y + ySize / 3);
                        if (kind > 6)
                        {
                            int direction = scanner.nextInt();
                            int lightDirection = direction;
                            if (kind == 10)
                            {
                                lightDirection = scanner.nextInt();
                                g.setColor(Color.WHITE);
                                drawEnemies(g,lightDirection, x, y, xSize, ySize);
                                g.setColor(Color.BLACK);
                            }
                            drawEnemies(g, direction, x, y, xSize, ySize);
                        }
                        if (kind == 4 || kind == 5)
                            g.drawString("(" + scanner.nextInt() + "," + scanner.nextInt() + ")", x, y + ySize / 3 + 10);
                        scanner.nextLine();
                        if(scanner.hasNext())
                            object = scanner.next();
                        cells[i][j].numberOfObjects++;
                    }
                }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g)
    {
        super.paint(g);
        draw((Graphics2D) g);
    }

    class Cell
    {
        int x, y, xSize, ySize, numberOfObjects;
        boolean exist;

        Cell(int x, int y, int xSize, int ySize, boolean exist)
        {
            this.x = x;
            this.y = y;
            this.xSize = xSize;
            this.ySize = ySize;
            this.exist = exist;
        }

        public boolean isIn(int x, int y)
        {
            return (x > this.x) && (x < this.x + xSize) && (y > this.y) && (y < this.y + ySize);
        }
    }

    class Route
    {
        int x, y, xSize, ySize;
        boolean exist;

        Route(int x, int y, int xSize, int ySize, boolean exist)
        {
            this.x = x;
            this.y = y;
            this.xSize = xSize;
            this.ySize = ySize;
            this.exist = exist;
        }
    }

    void makeCells()
    {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                cells[i][j] = new Cell(findCellX(j), findCellY(i), findCellXSize(), findCellYSize(), false);
    }

    public void makeRoutes()
    {
        int xRoutesXSize = findXRoutesXSize(), xRoutesYSize = findXRoutesYSize(), yRoutesXSize= findYRoutesXSize(), yRoutesYSize = findYRoutesYSize() ;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < m; j++)
                xRoutes[i][j] = new Route(findXRouteX(j), findXRouteY(i), xRoutesXSize, xRoutesYSize, false);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m - 1; j++)
                yRoutes[i][j] = new Route(findYRoutesX(j), findYRoutesY(i), yRoutesXSize, yRoutesYSize, false);
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
        return findCellXSize() / 30;
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
        return findCellYSize() / 30;
    }

    public int findXRouteX(int i)
    {
        return findCellX(i) + findCellXSize() / 2;
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
        return findCellY(j) + findCellYSize() / 2;
    }

}