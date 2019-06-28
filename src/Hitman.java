class Hitman
{
    Vector2D position;
    boolean hide = false;

    Hitman(int x, int y)
    {
        position = new Vector2D(x, y);
    }

    void update(int c, Map map)
    {
        switch (c)
        {
            case 0:
                position.x--;
                break;
            case 1:
                position.y++;
                break;
            case 2:
                position.x++;
                break;
            case 3:
                position.y--;
                break;
            case 4:
                int tmp = map.cells[position.x][position.y].subway.x;
                position.y = map.cells[position.x][position.y].subway.y;
                position.x = tmp;
                Sounds.play(Sounds.subway);
        }
        hide = map.cells[position.x][position.y].hasPot;
        if (hide)
            Sounds.play(Sounds.pot);
    }
}
