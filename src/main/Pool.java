package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class Pool
{
    //9A_1, 2B_3, 2C_3, 4D_2, 12E_1, 2F_4, 3G_2, 2H_4, 9I_1, 1J_8, 1K_5, 4L_1, 2M_3, 6N_1
    //8O_1, 2P_3, 1Q_10, 6R_1, 4S_1, 6T_1, 4U_1, 2V_4, 2W_4, 1X_8, 2Y_4, 1Z_10, 2BLANK_0

    //Key: 9A_1 implies there are 9 A tiles and each is of value 1

    private static ArrayList<Tile> pool = new ArrayList<>();

    public Pool() //constructor
    {
        set();
    }

    public static void set()
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
    public static Tile getRandomTile()
    {
        Random random = new Random();
        int random_num = random.nextInt(pool.size());

        return pool.remove(random_num);
    }

    public static void returnTile(Tile t)
    {
        pool.add(t);
    }

    public static void resetPool()
    {
        pool.clear();
        set();
    }

    public static int getNumberOfTilesRemaining()
    {
        return pool.size();
    }

    public static boolean isEmpty()
    {
        return pool.size() == 0;
    }

}