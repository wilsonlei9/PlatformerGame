package utils;

import Main.Game;
import entities.Pirate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {
    public static final String PLAYER_SPRITE = "Monkey D Luffy.png";
    public static final String LEVEL_SPRITE = "outside_sprites.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String BACKGROUND = "ocean.png";
    public static final String ENEMY_SPRITE = "enemy.png";

    public static BufferedImage getSprite(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }
    }
    // Goes through the level image. If a tile has a value of 0 for green, add a new Pirate object to the arraylist.
    public static ArrayList<Pirate> getPirates()
    {
        BufferedImage img = getSprite(LEVEL_ONE_DATA);
        ArrayList<Pirate> arr = new ArrayList<Pirate>();

        for (int i = 0; i < img.getHeight(); i++)
        {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getGreen();
                if (value == 0)
                {
                    arr.add(new Pirate(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
                }
            }
        }
        return arr;
    }
    public static int[][] getLevelData()
    {
        BufferedImage img = getSprite(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++)
        {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 48)
                {
                    value = 0;
                }
                lvlData[i][j] = value;
            }
        }
        return lvlData;
    }
}
