import apple.laf.JRSUIConstants;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

class Map
{
    final int[][] NEIGHBERS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    House[][] cells;
    Hitman hitman;
    Vector2D size, target;
    Vector<Enemy> enemies = new Vector<Enemy>();
    Vector<Stone> stones = new Vector<Stone>();
    Vector<Gun> guns = new Vector<Gun>();
    Vector<Camera> cameras = new Vector<Camera>();

    Map(File file)
    {
        initial(file);
    }

    void initial(File file)
    {
        Scanner fileReader;
        try
        {
            fileReader = new Scanner(new File("file.txt"));
            size = new Vector2D(fileReader.nextInt(), fileReader.nextInt());
            fileReader.nextLine();
            fileReader.nextLine();
            cells = new House[size.x][size.y];
            for (int i = 0; i < size.x; i++)
                for (int j = 0; j < size.y; j++)
                    cells[i][j] = new House(i, j, size.y);
            for (int i = 0; i < size.x; i++)
                for (int j = 0; j < size.y; j++)
                {
                    fileReader.nextLine();
                    fileReader.nextLine();
                    for (int k = 0; k < 4; k++)
                    {
                        if(fileReader.nextInt() == 1)
                            cells[i][j].isNeighber[k] = true;
                    }
                    String x;
                    x = fileReader.next();
                    while (x.charAt(0) != '(')
                    {
                        int kind = Integer.parseInt(x);
                        fileReader.next();
                        switch (kind)
                        {
                            case 0:
                                target = new Vector2D(i, j);
                                break;
                            case 1:
                                cells[i][j].hasPot = true;
                                break;
                            case 2:
                                cameras.addElement(new Camera(i, j));
                                break;
                            case 3:
                                stones.addElement(new Stone(i, j));
                                break;
                            case 4:
                                cells[i][j].hasSubway = true;
                                cells[i][j].subway = new Vector2D(fileReader.nextInt(), fileReader.nextInt());
                                break;
                            case 5:
                                guns.addElement(new Gun(i, j, fileReader.nextInt(), fileReader.nextInt(), this));
                                break;
                            case 6:
                                hitman = new Hitman(i, j);
                                break;
                            case 7:
                                enemies.addElement(new StaticEnemy(i, j, fileReader.nextInt()));
                                break;
                            case 8:
                                enemies.addElement(new SpinicEnemy(i, j, fileReader.nextInt()));
                                break;
                            case 9:
                                enemies.addElement(new DynamicEnemy(i, j, fileReader.nextInt()));
                                break;
                            case 10:
                                enemies.addElement(new SighticEnemy(i, j, fileReader.nextInt()));
                                break;
                            case 11:
                                enemies.addElement(new SmellicEnemy(i, j, fileReader.nextInt()));
                                break;
                        }
                        fileReader.nextLine();
                        if(fileReader.hasNext())
                            x = fileReader.next();
                    }
                }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    int findChoice(int x, int y)
    {
        if (cells[hitman.position.x][hitman.position.y].hasSubway && cells[hitman.position.x][hitman.position.y].subway.x == x && cells[hitman.position.x][hitman.position.y].subway.y == y)
            return 4;
        for (int i = 0; i < 4; i++)
        {
            int nx = hitman.position.x + NEIGHBERS[i][0], ny = hitman.position.y + NEIGHBERS[i][1];
            if (nx == x && ny == y && cells[hitman.position.x][hitman.position.y].isNeighber[i])
                return i;
        }
        return -1;
    }

    boolean isStoneTurn(int x, int y)
    {
        for (int i = 0; i < 4; i++)
        {
            int nx = hitman.position.x + NEIGHBERS[i][0], ny = hitman.position.y + NEIGHBERS[i][1];
            if (nx == x && ny == y)
                return true;
        }
        return false;
    }

    void write(PrintWriter printWriter, int x, int y)
    {
        printWriter.println("( " + x + " , " + y + " )");
        if (target.x == x && target.y == y)
            printWriter.println(0);

        if (cells[x][y].hasPot)
            printWriter.println(1);

        for (int i = 0; i < cameras.size(); i++)
            if (cameras.elementAt(i).position.x == x && cameras.elementAt(i).position.y == y)
                printWriter.println(2);

        for (int i = 0; i < stones.size(); i++)
            if (stones.elementAt(i).position.x == x && stones.elementAt(i).position.y == y)
                printWriter.println(3);

        if (cells[x][y].hasSubway)
            printWriter.println(4 + " " + cells[x][y].subway.x + " " + cells[x][y].subway.y);

        for (int i = 0; i < guns.size(); i++)
            if (guns.elementAt(i).position.x == x && guns.elementAt(i).position.y == y)
                printWriter.println(5 + " " + guns.elementAt(i).target.x + " " + guns.elementAt(i).target.y);

        if(hitman.position.x == x && hitman.position.y == y)
            printWriter.println(6);

        for (int i = 0; i < enemies.size(); i++)
        {
            if (enemies.elementAt(i).position.x == x && enemies.elementAt(i).position.y == y)
                if(enemies.get(i) instanceof SighticEnemy)
                    printWriter.println(enemies.elementAt(i).objectNumber + " " + enemies.elementAt(i).direction + " " + ((SighticEnemy)enemies.elementAt(i)).lightDirection);
                else
                    printWriter.println(enemies.elementAt(i).objectNumber + " " + enemies.elementAt(i).direction);
        }
    }
}