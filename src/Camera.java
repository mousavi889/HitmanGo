class Camera extends Tool
{
    Camera (int x, int y)
    {
        position = new Vector2D(x, y);
    }

    void alarm(Map map)
    {
        Sounds.play(Sounds.alarm);
        int nx = position.x - 1, ny = position.y - 1;
        for (int i = nx; i < nx + 3; i++)
            for (int j = ny; j < ny + 3; j++)
                for (int k = 0; k < map.enemies.size(); k++)
                    if (map.enemies.elementAt(k).position.x == i && map.enemies.elementAt(k).position.y == j)
                    {

                        map.enemies.elementAt(k).hasTarget = true;
                        map.enemies.elementAt(k).target = position;
                    }
    }
}