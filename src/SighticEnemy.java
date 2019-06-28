class SighticEnemy extends Enemy
{
    final int[][] REACH = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    int lightDirection;

    SighticEnemy(int x, int y, int d)
    {
        super();
        position = new Vector2D(x, y);
        direction = d;
        lightDirection = (d + 1) % 4;
        objectNumber = 10;
    }

    boolean attack(int x, int y)
    {
        int reachX = position.x + REACH[direction][0], reachY = position.y + REACH[direction][1];
        if (reachX == x && reachY == y)
            return true;
        reachX = position.x + REACH[lightDirection][0];
        reachY = position.y + REACH[lightDirection][1];
        if (reachX == x && reachY == y)
            return true;
        return false;
    }

    void update(Map map)
    {
        lightDirection = (lightDirection + 2) % 4;
        if(!hasTarget)
            return;
        updateToTarget(map);
    }
}