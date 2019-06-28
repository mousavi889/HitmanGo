class SmellicEnemy extends Enemy
{
    final int[][][] REACH = {{{-1, 0},{-2, 0}}, {{0, 1}, {0, 2}}, {{1, 0}, {2, 0}}, {{0, -1}, {0, -2}}};

    SmellicEnemy(int x, int y, int d)
    {
        super();
        position = new Vector2D(x, y);
        direction = d;
        objectNumber = 11;
    }

    boolean attack(int x, int y)
    {
        for (int i = 0; i < 2; i++)
        {
            int reachX = position.x + REACH[direction][i][0], reachY = position.y + REACH[direction][i][1];
            if (reachX == x && reachY == y)
                return true;
        }
        return false;
    }

    void update(Map map)
    {
        if(!hasTarget)
            return;
        updateToTarget(map);
    }
}