class DynamicEnemy extends Enemy
{
    final int[][] REACH = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    DynamicEnemy(int x, int y, int d)
    {
        super();
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
            int nextX = position.x + NEIGHBERS[direction][0];
            int nextY = position.y + NEIGHBERS[direction][1];
            if (nextX >= map.size.x || nextX < 0 || !map.cells[position.x][position.y].isNeighber[direction] || nextY >= map.size.y || nextY < 0)
            {
                direction = (direction + 2) % 4;
                nextX = position.x + NEIGHBERS[direction][0];
                nextY = position.y + NEIGHBERS[direction][1];
                if (nextX >= map.size.x || nextX < 0 || !map.cells[position.x][position.y].isNeighber[direction] || nextY >= map.size.y || nextY < 0)
                    return;
            }
            position.x += NEIGHBERS[direction][0];
            position.y += NEIGHBERS[direction][1];
            return;
        }
        updateToTarget(map);
    }
}