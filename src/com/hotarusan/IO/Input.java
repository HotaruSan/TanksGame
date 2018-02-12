package com.hotarusan.IO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Input extends JComponent{
    private boolean[] map;

    public Input(){
        map = new boolean[256];

        for (int i = 0; i < map.length; i++) {
            final int KEY_CODE = i;         //Для использования в ананимусиннер класе переменная должна быть final

            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i*2);       //Создаем карту кнопок по ASCII коду от 0 до 255 и соответсвию экшнкарте
            getActionMap().put(i * 2, new AbstractAction() {        //Назначаем функцию для элементов экшн карты
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;
                }
            });

            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i*2+1);      //Нажатым клавишам даем i*2+1 нечетные имена
            getActionMap().put(i * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = false;
                }
            });
        }
    }

    public boolean[] getMap(){
        return Arrays.copyOf(map, map.length);
    }

    public boolean getKey(int keyCode){
        return map[keyCode];
    }
}
