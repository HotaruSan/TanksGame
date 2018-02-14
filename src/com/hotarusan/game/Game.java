package com.hotarusan.game;

import com.hotarusan.IO.Input;
import com.hotarusan.display.Display;
import com.hotarusan.game.level.Level;
import com.hotarusan.graphics.TextureAtlas;
import com.hotarusan.utils.Time;

import java.awt.*;

/**
 * Created by HotaruSan on 08.02.2018.
 */
public class Game implements Runnable{                                          //Подключаем интерфей многопоточности
    //int width, int height, String title, int _clearColor, int numBuffers
    public static final int     WIDTH       = 800;
    public static final int     HEIGHT      = 600;
    public static final String  TITLE       = "Tanks";
    public static final int     CLEAR_COLOR = 0xff000000;
    public static final int     NUM_BUFFERS = 3;

    public static final float   UPDATE_RATE = 60.0f;
    public static final float   UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long    IDLE_TIME = 1;                                     //Переменная для задания времени передышки потока в миллисекундах

    public static final String  ATLAS_FILE_NAME = "texture_atlas.png";

    private boolean     running;
    private Thread      gameThread;
    private Graphics2D  graphics;
    private Input       input;
    private TextureAtlas    atlas;
    private Player      player;
    private Level       level;

    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        player = new Player(300, 300, 2, 3, atlas);
        level = new Level(atlas);
    }

    public synchronized void start(){                                   //Позволяет запускать только в одном процессе
        if(running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;

        try{
            gameThread.join();                                          //Данный метод требует проверки на исключение
        }catch (InterruptedException e){
            e.printStackTrace();                                        //В элементе пойманного исключения распечатываем трассировку ошибки
        }

        cleanUp();
    }

    private void update(){
        player.update(input);
        level.update();
    }

    private void render(){
        Display.clear();

        level.render(graphics);
        player.render(graphics);

        Display.swapBuffer();
    }

    public void run(){
        int fps = 0;                                                            //количество отрисовок экрана
        int upd = 0;                                                            //количество расчетов
        int updl = 0;                                                           //количество повторяющихся расчетов

        long count = 0;

        float delta = 0;

        long lastTime = Time.get();                                              //Расчитываем время последней загрузки процесса
        while (running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);                             //Высчитываем отклонение от установленого интервала FPS
            while (delta > 1){
                update();
                upd++;
                delta--;
                if (render){
                    updl++;
                }else {
                    render = true;
                }
            }
            if (render){
                render();
                fps++;
            }else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= Time.SECOND){
                Display.setTitle(TITLE + "  /Fps: " + fps + " /Upd: " + upd + " /Updl: " + updl);
                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }
        }
    }

    private void cleanUp(){
        Display.destroy();
    }
}
