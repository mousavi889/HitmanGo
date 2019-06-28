class Stone extends Tool
{
    Stone (int x, int y)
    {
        position = new Vector2D(x, y);
    }

    static void shootAtHouse(Map map, int x, int y)
    {
        for (int s = x - 1; s < x + 2; s++)
            for (int t = y - 1; t < y + 2; t++)
                for (int k = 0; k < map.enemies.size(); k++)
                    if (map.enemies.elementAt(k).position.x == s && map.enemies.elementAt(k).position.y == t)
                    {
                        map.enemies.elementAt(k).hasTarget = true;
                        map.enemies.elementAt(k).target = new Vector2D(x, y);
                    }
    }
}