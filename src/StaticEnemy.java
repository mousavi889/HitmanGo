class StaticEnemy extends Enemy
{
    final int[][] REACH = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    StaticEnemy(int x, int y, int d)
    {
        position = new Vector2D(x, y);
        direction = d;
        objectNumber = 7;
    }

    boolean attack(int x, int y)
    {
        int reachX = position.x + REACH[direction][0], reachY = position.y + REACH[direction][1];
        return reachX == x && reachY == y;
    }

    void update(Map map)
    {
        if(!hasTarget)
            return;
        updateToTarget(map);
    }
}