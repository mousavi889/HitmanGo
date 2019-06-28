class House
{
    int number;
    Vector2D position, subway;
    boolean hasPot = false, hasSubway = false;
    boolean[] isNeighber = new boolean[4];

    House(int i, int j, int sizeOfY)
    {
        number = i * sizeOfY + j;
        position = new Vector2D(i, j);
    }
}