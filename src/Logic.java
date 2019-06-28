import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.util.Scanner;

public class Logic
{
    Map map;
    boolean isSToneTurn = false;
    Logic(File gameFile)
    {
        this.map = new Map(gameFile);
        save();
    }

    int play(int xx, int yy)
    {
        int state = 0; //0: ?, 1 = Hitman, 2 = Enemies
        int order = -2;
        if (isSToneTurn)
        {
            boolean iii = map.isStoneTurn(xx, yy);
            if (iii)
            {
                Stone.shootAtHouse(map, xx, yy);
                isSToneTurn = false;
                save();
                Sounds.play(Sounds.stone);
            }
            if(!iii)
            {
                Sounds.play(Sounds.error);
                return 0;
            }
        }
        else
            order = map.findChoice(xx, yy);
        if (order == -1)
        {
            Sounds.play(Sounds.error);

            return 0;
        }

        if(order != -2)
            map.hitman.update(order, map);

        for (int i = 0; i < map.cameras.size(); i++)
        {
            Camera camera = map.cameras.elementAt(i);
            if (camera.position.x == map.hitman.position.x && camera.position.y == map.hitman.position.y && !map.hitman.hide)
                camera.alarm(map);
        }

        for (int i = 0; i < map.enemies.size() && i >= 0; i++)
            if (map.hitman.position.x == map.enemies.elementAt(i).position.x && map.hitman.position.y == map.enemies.elementAt(i).position.y)
            {
                map.enemies.remove(i);
                Sounds.play(Sounds.kill);
                i--;
            }


        if (map.target.x == map.hitman.position.x && map.target.y == map.hitman.position.y)
            {
                state = 1;
                return state;
            }

        for (int i = 0; i < map.enemies.size(); i++)
        {
            if (map.hitman.hide)
                break;
            if (map.enemies.elementAt(i).attack(map.hitman.position.x, map.hitman.position.y))
                state = 2;
        }

        for (int i = 0; i < map.guns.size(); i++)
        {
            int x = map.guns.elementAt(i).position.x, y = map.guns.elementAt(i).position.y;
            if (map.hitman.position.x == x && map.hitman.position.y == y)
            {
                map.guns.elementAt(i).shoot(map);
                map.guns.remove(i);
                i--;
                Sounds.play(Sounds.gun);
            }
        }

        for (int i = 0; i < map.stones.size(); i++)
        {
            int x = map.stones.elementAt(i).position.x, y = map.stones.elementAt(i).position.y;
            if (map.hitman.position.x == x && map.hitman.position.y == y)
            {
                if(!isSToneTurn)
                {
                    isSToneTurn = true;
                    map.stones.remove(i);
                    i--;
                    save();
                    return 0;
                }
                else
                    isSToneTurn = false;
            }
        }

        for (int i = 0; i < map.enemies.size(); i++)
            map.enemies.elementAt(i).update(map);

        for (int i = 0; i < map.enemies.size(); i++)
            if (map.enemies.elementAt(i).position.x == map.hitman.position.x && map.enemies.elementAt(i).position.y == map.hitman.position.y && !map.hitman.hide)
                state = 2;

        save();
        return state;
    }

    public void save()
    {
        File file = new File("ttt.txt");
        PrintWriter printWriter;
        try
        {
            file.createNewFile();
            printWriter = new PrintWriter(file);
            for (int i = 0; i < map.size.x; i++)
                for (int j = 0; j < map.size.y; j++)
                {
                    map.write(printWriter, i, j);
                    printWriter.println();
                }
            printWriter.println("(");
            printWriter.flush();
            printWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}