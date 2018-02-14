package com.hotarusan.utils;

import java.awt.image.BufferedImage;

/** Клас для уменьшения ресурсов вычисления на масштабирование изображения объекта
 * масштабирует объект из исходного размера вырезанного в SpriteSheet из атласа
 */

public class Utils {
    public static BufferedImage resize(BufferedImage image, int width, int height){
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image,0, 0, width, height, null);
        return newImage;
    }
}
