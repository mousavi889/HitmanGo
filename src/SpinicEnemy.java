//n
class SpinicEnemy extends Enemy
{
    final int[][] REACH = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    SpinicEnemy(int x, int y, int d)
    {
        position = new Vector2D(x, y);
        direction = d;
        objectNumber = 8;
    }

    boolean attack(int x, int y)
    {
        int reachX = position.x + REACH[direction][0], reachY = position.y + REACH[direction][1];
        return reachX == x && reachY == y;
    }

    void update(Map map)
    {
        if(!hasTarget)
        {
            direction = (direction + 2) % 4;
            return;
        }
        updateToTarget(map);
    }
}