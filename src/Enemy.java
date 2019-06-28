import java.util.Vector;

class Enemy
{
    final int[][] NEIGHBERS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    int direction; //0: up, 1: right, 2: down, 3: left
    boolean hasTarget = false, pathIsFound = false;
    Vector2D position, target;
    Vector<Integer> path = new Vector<Integer>();
    int objectNumber;

    boolean attack(int x, int y)
    {
        return false;
    }

    void findPath(Map map)
    {
        boolean[][] mark = new boolean[map.size.x][map.size.y];
        int[][] parent = new int[map.size.x][map.size.y];
        int x = position.x, y = position.y;
        mark[x][y] = true;
        Vector <Vector2D> q = new Vector<Vector2D>();
        q.addElement(new Vector2D(x, y));
        parent[x][y] = -1;
        while (!mark[target.x][target.y])
        {
            if (q.size() == 0)
            {
                hasTarget = false;
                return;
            }
            x = q.elementAt(0).x;
            y = q.elementAt(0).y;
            for (int i = 0; i < 4; i++)
            {
                int nx = x + NEIGHBERS[i][0], ny = y + NEIGHBERS[i][1];
                if (nx >= 0 && nx < map.size.x && ny >= 0 && ny < map.size.y && !mark[nx][ny] && map.cells[x][y].isNeighber[i])
                {
                    mark[nx][ny] = true;
                    parent[nx][ny] = i;
                    q.addElement(new Vector2D(nx, ny));
                }
            }
            q.remove(0);
        }
        x = target.x;
        y = target.y;
        while(parent[x][y] != -1)
        {
            path.add(0, parent[x][y]);
            int tmp;
            tmp = x - NEIGHBERS[parent[x][y]][0];
            y = y - NEIGHBERS[parent[x][y]][1];
            x = tmp;
        }
    }

    void updateToTarget(Map map)
    {
        if(!pathIsFound)
        {
            path.clear();
            findPath(map);
            if(path.size() == 0)
            {
                hasTarget = false;
                return;
            }
            pathIsFound = true;
        }
        position.x += map.NEIGHBERS[path.elementAt(0)][0];
        position.y += map.NEIGHBERS[path.elementAt(0)][1];
        direction = path.elementAt(0);
        path.remove(0);
        if (position.x == target.x && position.y == target.y)
        {
            hasTarget = false;
            pathIsFound = false;
        }
    }

    void update(Map map)
    {
    }
}