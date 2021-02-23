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

    static List<String> OutFile = Collections.synchronizedList(new ArrayList<String>());

    public static void main(String[] args) throws Exception{
        List<String> FileNameList = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);
        String myOptionsProgramm = scanner.nextLine();

        //for ()


        FileNameList.add("in.txt");
        FileNameList.add("in1.txt");
        FileNameList.add("in2.txt");
        FileNameList.add("in3.txt");

        // создаем счётчик
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()); // создаем счётчик

        for (int i=0; i<FileNameList.size();i++){
            new InString(FileNameList.get(i), countDownLatch);
        }

        countDownLatch.await(); // данный поток, ждёт пока счётчик не зоплнится

        OutFile = bubbleSort(OutFile);

        try(FileWriter writer = new FileWriter("out.txt", false))
        {
            // запись всей строки
            for (String s : OutFile) {
                //System.out.println(s);
                writer.write(s);
                writer.append('\n');
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //System.out.println("hi");

    }

    static class InString extends Thread{
        String FileName;
        CountDownLatch countDownLatch;

        public InString(String FileName, CountDownLatch countDownLatch) {
            this.FileName = FileName;
            this.countDownLatch = countDownLatch; // передаем счётчик
            start();
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

                String line;
                List<String> lines = new ArrayList<String>();
                while ((line = reader.readLine()) != null) {
                    if(!line.trim().isEmpty()){
                        lines.add(line); // считываем остальные строки в цикле
                        //System.out.println(line);
                    }
                }
                lines = bubbleSort(lines);
                for (String s : lines) {
                    synchronized (OutFile){
                        OutFile.add(s);
                        OutFile.notify();
                    }
                    Thread.yield();
                    Thread.sleep(100);
                    //System.out.println(Thread.currentThread().getName()+" "+s);
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

    public static List bubbleSort (List<String> lines) {
        //char[] digits= new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g','h', 'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        boolean sorted = false;
        String temp;
        try {
        while(!sorted) {
            sorted = true;

            //сравнение строк
            for (int i = 0; i < lines.size() - 1; i++) {
                if((lines.get(i)!="\n")&&(lines.get(i+1)!="\n")){
                //берем 2 строчки и сравниваем их символы внутри
                int NumChar1=-1, NumChar2=-1;
                    int n=0;
                    do{
                        NumChar1 = Character.getNumericValue((lines.get(i).charAt(n)));
                        NumChar2 = Character.getNumericValue((lines.get(i+1).charAt(n)));
                        /*
                        for (int j=0;j<digits.length;j++){
                            if(lines.get(i).charAt(n)==digits[j])
                                NumChar1=j;
                            if(lines.get(i+1).charAt(n)==digits[j])
                                NumChar2=j;
                        }
                         */
                        //System.out.println(lines.get(i));
                        //System.out.println(lines.get(i+1));
                        n++;
                    } while ((NumChar1==NumChar2)&&(lines.get(i).length()!=n)&&(lines.get(i+1).length()!=n));

                    //System.out.println(lines.get(i));
                    //System.out.println(Character.getNumericValue((lines.get(i).charAt(0))));

                    // проверка большего, выборка пузырьковым
                    if (NumChar1 > NumChar2) {
                        temp = lines.get(i);
                        lines.set(i,lines.get(i+1));
                        lines.set(i+1,temp);
                        sorted = false;
                    }
                }
            }
        }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }
}
