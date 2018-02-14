package com.hotarusan.graphics;

import java.awt.image.BufferedImage;

/** Класс для выделения элементов требуемого изображения объекта
 * из единого набора картинок, так назваемого атласа
 */

public class SpriteSheet {
    private BufferedImage   sheet;
    private int             spriteCount;
    private int             scale;
    private int             spriteInWidth;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale){
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;

        this.spriteInWidth = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index){
        index = index % spriteCount;        //для прохождения цикла по кругу без обнуления в loop

        int x = index % spriteInWidth * scale;
        int y = index / spriteInWidth * scale;

        return sheet.getSubimage(x, y, scale, scale);
    }
}
