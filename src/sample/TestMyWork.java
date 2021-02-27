package sample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMyWork {

    @Test
    public void MyOptionsProgramm() throws Exception {
        String myOptionsProgrammString;
        List<String> FileNameList = new ArrayList<String>(); // список имён файлов

        myOptionsProgrammString="-i -d in1.txt in2.txt in3.txt ";
        technical_homework_main.MyOptionsProgramm myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();
        assertEquals(FileNameList.get(0), "in1.txt");
        assertEquals(FileNameList.get(1), "in2.txt");
        assertEquals(FileNameList.get(2), "in3.txt");
        assertEquals(technical_homework_main.S, false);
        assertEquals(technical_homework_main.A, false);
        assertEquals(technical_homework_main.I, true);
        assertEquals(technical_homework_main.D, true);

        myOptionsProgrammString="-s in1.txt-ain2.txt in3.txt";
        myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();
        assertEquals(FileNameList.get(0), "in1.txt");
        assertEquals(FileNameList.get(1), "in1.txt-ain2.txt");
        assertEquals(FileNameList.get(2), "in3.txt");
        assertEquals(technical_homework_main.S, true);
        assertEquals(technical_homework_main.A, true);
        assertEquals(technical_homework_main.I, false);
        assertEquals(technical_homework_main.D, false);
    }

    @Test
    public void bubbleSort() throws Exception {
        //-------------------------------------------------------------------------1
        List<String> lines1 = new ArrayList<String>(); // берем список строк
        List<String> lines2 = Arrays.asList(new String[]{"2", "s", "aa22", "aa4", "5", "v", "d", "fdsfg", "4", "23", "bdf", "34", "dg", "54", "g", "ss", "-", "*", "7"}); // берем список строк

        //настройки для сортировки
        String myOptionsProgrammString="-s -a";
        technical_homework_main.MyOptionsProgramm myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        List<String> FileNameList = myOptionsProgramm.MySorted();

        for (String s : lines2) { // пока не закончиться считывания файла
            lines1 = technical_homework_main.bubbleSort(lines1, s); // зписываем в список, новый список из предыдущего, но уже отсортированный
        }

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            lines1 = technical_homework_main.TrasparentList(lines1); // разворачиваем наш список в другую сторону
        }

        assertEquals(technical_homework_main.toString(lines1), "*|-|23|2|34|4|54|5|7|aa22|aa4|bdf|dg|d|fdsfg|g|ss|s|v|");

        //-------------------------------------------------------------------------2
        lines1 = new ArrayList<String>(); // берем список строк
        lines2 = Arrays.asList(new String[]{"2", "7", "3", "6", "5", "2", "4", "44", "22", "17", "2", "3", "8", "32", "0", "5"}); // берем список строк

        //настройки для сортировки
        myOptionsProgrammString="-i -d";
        myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();

        for (String s : lines2) { // пока не закончиться считывания файла
            lines1 = technical_homework_main.bubbleSort(lines1, s); // зписываем в список, новый список из предыдущего, но уже отсортированный
        }

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            lines1 = technical_homework_main.TrasparentList(lines1); // разворачиваем наш список в другую сторону
        }

        assertEquals(technical_homework_main.toString(lines1), "44|32|22|17|8|7|6|5|5|4|3|3|2|2|2|0|");

        //-------------------------------------------------------------------------3
        lines1 = new ArrayList<String>(); // берем список строк
        lines2 = Arrays.asList(new String[]{"2", "7", "d", "6", "5", "2", "4", "f", "g", "17", "2", "3", "8", "32", "0", "5"}); // берем список строк

        //настройки для сортировки
        myOptionsProgrammString="-i -a";
        myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();

        for (String s : lines2) { // пока не закончиться считывания файла
            lines1 = technical_homework_main.bubbleSort(lines1, s); // зписываем в список, новый список из предыдущего, но уже отсортированный
        }

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            lines1 = technical_homework_main.TrasparentList(lines1); // разворачиваем наш список в другую сторону
        }

        assertEquals(technical_homework_main.toString(lines1), "0|2|3|5|8|17|32|g|f|2|4|5|6|d|2|7|");

        //-------------------------------------------------------------------------4
        lines1 = new ArrayList<String>(); // берем список строк
        lines2 = Arrays.asList(new String[]{"2", "aff", "dff", "6", "5", "2", "4", "fff", "gaa", "17", "gaf", "3", "8", "32", "#", "**"}); // берем список строк

        //настройки для сортировки
        myOptionsProgrammString="-s -d";
        myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();

        for (String s : lines2) { // пока не закончиться считывания файла
            lines1 = technical_homework_main.bubbleSort(lines1, s); // зписываем в список, новый список из предыдущего, но уже отсортированный
        }

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            lines1 = technical_homework_main.TrasparentList(lines1); // разворачиваем наш список в другую сторону
        }

        assertEquals(technical_homework_main.toString(lines1), "gaf|gaa|fff|dff|aff|8|6|5|4|3|32|2|2|17|#|**|");
    }

    @Test
    public void TrasparentList() throws Exception {
        List<String> lines = Arrays.asList(new String[]{"2", "aff", "dff", "**"}); // берем список строк
        lines = technical_homework_main.TrasparentList(lines); // разворачиваем наш список в другую сторону
        assertEquals(technical_homework_main.toString(lines), "**|dff|aff|2|");
        lines = technical_homework_main.TrasparentList(lines); // разворачиваем наш список в другую сторону
        assertEquals(technical_homework_main.toString(lines), "2|aff|dff|**|");
    }
}