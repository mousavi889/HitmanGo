class Gun extends Tool
{
    Vector2D target;

    Gun (int x, int y, int tx, int ty, Map map)
    {
        position = new Vector2D(x, y);
        target = new Vector2D(tx, ty);
    }

    void shoot(Map map)
    {
        for (int i = 0; i < map.enemies.size(); i++)
            if (target.x == map.enemies.elementAt(i).position.x && target.y == map.enemies.elementAt(i).position.y)
            {
                map.enemies.remove(i);
                Sounds.play(Sounds.kill);
                i--;
            }
    }
}