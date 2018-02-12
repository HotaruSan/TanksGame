package com.hotarusan.display;

import com.hotarusan.IO.Input;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 * Created by HotaruSan on 05.02.2018.
 * Создаем основное игровое окно
 */
public abstract class Display {

    public static boolean created = false;      //Проверяем, что окно уже создано
    public static JFrame window;                //Указываем рамки нашего окна с помощью стандартного элемента JFrame
    private static Canvas content;              //Клас для отображения объектов

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;   //Создаем ссылку на мультибуферную стратегию


    public static void create(int width, int height, String title, int _clearColor, int numBuffers){
        if (created)
            return;
        window = new JFrame(title);
        content = new Canvas();

        Dimension size = new Dimension(width, height);  //Передаем заданные размеры окна в Dimension, потому-что на прямую мы задать не можем
        content.setPreferredSize(size);                 //И присваеваем данные размеры нашему активному полю

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Без этого элемента наша программа будет продолжать выполняться после закрытия окна
        window.setResizable(false);                     //Запрещаем пользователю менять размер окна
        window.getContentPane().add(content);           //Окно включает в себя менюшки, кнопочки и скролы, чтобы они не перекрывали наше активное поле
        window.pack();                                  //Чтобы размер окна учитывал размер поля
        window.setLocationRelativeTo(null);             //Задаем относительное начальное положение окна
        window.setVisible(true);                        //Задает видимость окна

        buffer = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);       //Создаем стратегию буферизации
        bufferStrategy = content.getBufferStrategy();

        created = true;                                 //Обозначаем, что окно уже создано
    }

    public static void clear(){
        Arrays.fill(bufferData, clearColor);
    }

    public static void swapBuffer(){
        Graphics g = bufferStrategy.getDrawGraphics();      //Получили графику
        g.drawImage(buffer, 0, 0, null);     //Записали новую графику
        bufferStrategy.show();                              //Показываем новую графику
    }

    public static Graphics2D getGraphics(){
        return (Graphics2D) bufferGraphics;
    }

    public static void setTitle(String title){
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener){
        window.add(inputListener);
    }

    public static void destroy(){
        if (!created)
            return;
        window.dispose();
    }

}
