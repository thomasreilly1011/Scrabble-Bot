package main;

import java.util.ArrayList;
import java.util.Random;

public class Pool implements Cloneable
{
    public ArrayList<Tile> pool = new ArrayList<>();

    public Pool()
    {
        set();
    }

    public void set()
    {
        int i;
        for(i=0; i<9; i++)// A Tiles
        {
            pool.add(new Tile('A', 1));
        }
        for(i=0; i<2; i++)// B Tiles
        {
            pool.add(new Tile('B', 3));
        }
        for(i=0; i<2; i++)// C Tiles
        {
            pool.add(new Tile('C', 3));
        }
        for(i=0; i<4; i++)// D Tiles
        {
            pool.add(new Tile('D', 2));
        }
        for(i=0; i<12; i++)// E Tiles
        {
            pool.add(new Tile('E', 1));
        }
        for(i=0; i<2; i++)// F Tiles
        {
            pool.add(new Tile('F', 4));
        }
        for(i=0; i<3; i++)// G Tiles
        {
            pool.add(new Tile('G', 2));
        }
        for(i=0; i<2; i++)// H Tiles
        {
            pool.add(new Tile('H', 4));
        }
        for(i=0; i<9; i++)// I Tiles
        {
            pool.add(new Tile('I', 1));
        }
        pool.add(new Tile('J', 8)); //single J Tile
        pool.add(new Tile('K', 5)); //single K Tile
        for(i=0; i<4; i++)// L Tiles
        {
            pool.add(new Tile('L', 1));
        }
        for(i=0; i<2; i++)// M Tiles
        {
            pool.add(new Tile('M', 3));
        }
        for(i=0; i<6; i++)// N Tiles
        {
            pool.add(new Tile('N', 1));
        }
        for(i=0; i<8; i++)// O Tiles
        {
            pool.add(new Tile('O', 1));
        }
        for(i=0; i<2; i++)// P Tiles
        {
            pool.add(new Tile('P', 3));
        }
        pool.add(new Tile('Q', 10)); //single Q Tile
        for(i=0; i<6; i++)// R Tiles
        {
            pool.add(new Tile('R', 1));
        }
        for(i=0; i<4; i++)// S Tiles
        {
            pool.add(new Tile('S', 1));
        }
        for(i=0; i<6; i++)// T Tiles
        {
            pool.add(new Tile('T', 1));
        }
        for(i=0; i<4; i++)// U Tiles
        {
            pool.add(new Tile('U', 1));
        }
        for(i=0; i<2; i++)// V Tiles
        {
            pool.add(new Tile('V', 4));
        }
        for(i=0; i<2; i++)// W Tiles
        {
            pool.add(new Tile('W', 4));
        }
        pool.add(new Tile('X', 8)); //single X Tile
        for(i=0; i<2; i++)// Y Tiles
        {
            pool.add(new Tile('Y', 4));
        }
        pool.add(new Tile('Z', 10)); //single Z Tile
        for(i=0;i<2; i++)// BLANK Tiles
        {
            pool.add(new Tile('_', 0));
        }
    }

    //removing a random tile
    public Tile getRandomTile()
    {
        Random random = new Random();
        int random_num = random.nextInt(pool.size());

        return pool.remove(random_num);
    }

    public void returnTile(Tile t)
    {
        pool.add(t);
    }

    public void resetPool()
    {
        pool.clear();
        set();
    }

    @Override
    protected Pool clone() throws CloneNotSupportedException {
        //Perform a depp copy of the tiles in the Pool.
        Pool clone = new Pool();
        for (Tile tile:pool) {
            clone.pool.add((Tile) tile.clone());
        }
        return clone;

    }


    public int getNumberOfTilesRemaining()
    {
        return pool.size();
    }

    public boolean isEmpty()
    {
        return pool.size() == 0;
    }

}