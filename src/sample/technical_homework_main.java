package sample;

import javax.sound.sampled.Line;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class technical_homework_main {

    // готовый список
    static List<String> OutFile = Collections.synchronizedList(new ArrayList<String>());

    static boolean S,A,I,D; // виды сортировки

    static class MyOptionsProgramm{
        String myOptionsProgramm;
        List<String> FileNameList = new ArrayList<String>(); // список имён файлов

        public MyOptionsProgramm(String myOptionsProgrammString){
            this.myOptionsProgramm = myOptionsProgrammString;
        }

        public List<String> MySorted(){
            // виды сортировки
            A=true; // сортировка по возростанию
            D=false;// сортировка по убыванию
            I=false;// сортировка int
            S=true;// сортировка String

            for (int i = 0; i < myOptionsProgramm.length(); i++) {
                if (myOptionsProgramm.charAt(i)=='-'){
                    if (myOptionsProgramm.charAt(i+1)=='a'){ // сортировка по возростанию
                        A=true;
                        D=false;
                    }
                    if (myOptionsProgramm.charAt(i+1)=='i'){ // сортировка int
                        I=true;
                        S=false;
                    }
                    if (myOptionsProgramm.charAt(i+1)=='d'){ // сортировка по убыванию
                        A=false;
                        D=true;
                    }
                    if (myOptionsProgramm.charAt(i+1)=='s'){ // сортировка String
                        I=false;
                        S=true;
                    }
                }
                if (myOptionsProgramm.charAt(i)=='.'){ // ищем файлы, они начинаются с точки
                    if (myOptionsProgramm.charAt(i+1)=='t'){ // если, это не запускаемый файл и имеет расширение txt, хотя бы начинается t, то:
                        String nameFile ="";
                        int n=i;
                        n--;
                        while (myOptionsProgramm.charAt(n)!=' '){ // пока не нашли пробел
                            nameFile = myOptionsProgramm.charAt(n)+nameFile; // выписываем имя файла посимвольно
                            n--;
                        }
                        FileNameList.add(nameFile+".txt"); // заисываем имя файла в список файлов
                    }
                }

            }
            return FileNameList;
        }

    }

    public static void main(String[] args) throws Exception{
        List<String> FileNameList = new ArrayList<String>(); // список имён файлов

        String myOptionsProgrammString=""; // настройки сортировки
        for (int i=0; i< args.length; i++) {
            myOptionsProgrammString += " " + args[i]; // принимаем в виде аргументов
        }

        MyOptionsProgramm myOptionsProgramm =new MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();


        // создаем счётчик потоков
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()-1); // создаем счётчик

        for (int i=1; i<FileNameList.size();i++){
            new InString(FileNameList.get(i), countDownLatch); // запихиваем в цикле, наши файлы в потоки, а так же присваиваем нашим потокам счётчик
        }

        countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

        //------------------------------------------------------------------------------------ЖДЁМ ВЫПОЛНЕНИЯ ПОТОКА------------------------------------------

        if(A==true){ // сортировка по возростанию true
            OutFile = TrasparentList(OutFile); // разворачиваем наш список в другую сторону
        }

        // запись данных в файл
        try(FileWriter writer = new FileWriter(FileNameList.get(0), false))
        {
            // запись всей строки
            for (String s : OutFile) { // пока не закончится наш список
                writer.write(s); // записываем строку
                writer.append('\n'); // переходим на новую строчку
            }
            writer.flush(); // перезапись файла
        }
        catch(IOException ex){
            System.out.println(ex.getMessage()); // вывод ошибок при работе с файлом
        }
    }

    // наш с вами поток
    static class InString extends Thread{
        String FileName; // файловое имя
        CountDownLatch countDownLatch; // счётчик

        public InString(String FileName, CountDownLatch countDownLatch) {
            this.FileName = FileName;// передаем файловое имя
            this.countDownLatch = countDownLatch; // передаем счётчик
            start(); // запускаем поток, run()
        }

        @Override
        public void run() {
            // построчное считывание файла
            try {
                File file = new File(FileName);

                //создаем объект FileReader для объекта File
                FileReader fr = new FileReader(file);

                //создаем BufferedReader с существующего FileReader для построчного считывания
                BufferedReader reader = new BufferedReader(fr);

                String line; // берем строку
                List<String> lines = new ArrayList<String>(); // берем список строк

                while ((line = reader.readLine()) != null) { // пока не закончиться считывания файла
                    if(!line.trim().isEmpty()){ // проверяем наличие сиволов в выбранной строчке
                        lines = bubbleSort(lines, line); // зписываем в список, новый список из предыдущего, но уже отсортированный
                    }
                }

                for (String s : lines) { // пока не закончиться список
                    synchronized (OutFile){ // синхронизируем список, что бы потоки не теряли данные
                        OutFile = bubbleSort(OutFile, s); // зписываем в список, новый список из предыдущего, но уже отсортированный
                        OutFile.notify(); // говорим другому потоку, что мы сделали, то что хотели
                    }
                    Thread.yield(); // даем возможность выбрать другой поток
                    Thread.sleep(100); // отправляем спать этот поток, не объязательно, но помогает, лишний раз не сортировать список
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown(); // прибовляем к счётчику +1
        }
    }

    // выборка пузырьковым
    public static List<String> bubbleSort (List<String> lines, String newString) {
        boolean sorted = false; // наша проверка на сортировку
        String temp; // временная строка
        lines.add(newString); // добавляем новую строчку в список
        try {
            while(!sorted) { // пока он не сортрованный
                sorted = true; // ставим знак, что сортирован

                //сравнение строк
                for (int i = lines.size()-1; i > 0 ; i--) {

                        //берем 2 строчки и сравниваем их символы внутри
                        int NumChar1=-1, NumChar2=-1;
                        int n=0;
                        do{
                            if(S==true){ // если сортируем строки
                            NumChar1 = Character.getNumericValue(lines.get(i-1).charAt(n)); //строка записанная ранее нашей
                            NumChar2 = Character.getNumericValue(lines.get(i).charAt(n)); // наша строка
                            }
                            if(I==true){// если сортируем целое число
                                NumChar1 = Integer.parseInt(lines.get(i-1));//строка записанная ранее нашей
                                NumChar2 = Integer.parseInt(lines.get(i));// наша строка
                            }
                            n++;
                        } while ((NumChar1==NumChar2)&&(lines.get(i-1).length()!=n)&&(lines.get(i).length()!=n)); // условия, если совпали символы или значения

                        // проверка большего, выборка пузырьковым
                        if (NumChar1 < NumChar2) { // пока число ранее, меньше нашего
                            temp = lines.get(i-1); //помешаем выбранную строку во временный
                            lines.set(i-1,lines.get(i)); // вставляем туда, нашу строчку
                            lines.set(i,temp); // а в нашу, помещаем временный
                            sorted = false; // говорим, что не сортирован
                        }
                    }
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines; // возврашаем отсортированный список
    }

    // нужен, для того что бы перевернуть список, верх ногами
    public static List<String> TrasparentList (List<String> lines) {
        List<String> list2 = new ArrayList<String>(); // создаем список
        for (int i = lines.size()-1; i >= 0 ; i--) { // пока не переберём все значения списка
            list2.add(lines.get(i)); // записываем в новый список из старого, но не в том порядке
        }
        return list2; // возврашаем отсортированный список
    }
}
