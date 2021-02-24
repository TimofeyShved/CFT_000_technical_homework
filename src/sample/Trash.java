package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
/*
public class Trash {

    //он не пригодиться
    public static List bubbleSort2 (List<String> lines, String newString) {
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
/*
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

/*
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

    static boolean S,A,I,D;

    public static void main(String[] args) throws Exception{
        List<String> FileNameList = new ArrayList<String>();

        //Console console = System.console();

        //System.out.println("Введите параметры, например мы вводим: sort-it.exe -s out.txt in.txt in1.txt in2.txt in3.txt");
        //Scanner scanner = new Scanner(System.in);
        //String myOptionsProgramm = scanner.nextLine();  // например мы вводим " sort-it.exe -s out.txt in.txt in1.txt in2.txt in3.txt"

        //String myOptionsProgramm = console.readLine();
        String myOptionsProgramm="";
        for (int i=0; i< args.length; i++) {
            myOptionsProgramm += " " + args[i];
            System.out.println(args[i]);
        }
        System.out.println(myOptionsProgramm);

        A=true;
        D=false;
        I=false;
        S=true;

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
            if (myOptionsProgramm.charAt(i)=='.'){ // сортировка String
                if (myOptionsProgramm.charAt(i+1)=='t'){ // сортировка String
                    String nameFile ="";
                    int n=i;
                    n--;
                    while (myOptionsProgramm.charAt(n)!=' '){
                        nameFile = myOptionsProgramm.charAt(n)+nameFile;
                        n--;
                    }
                    FileNameList.add(nameFile+".txt");
                }
            }

        }

        // создаем счётчик потоков
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()-1); // создаем счётчик

        for (int i=1; i<FileNameList.size();i++){
            new InString(FileNameList.get(i), countDownLatch);
        }

        countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

        if(A==true){
            OutFile = TrasparentList(OutFile);
        }

        try(FileWriter writer = new FileWriter(FileNameList.get(0), false))
        {
            // запись всей строки
            for (String s : OutFile) {
                writer.write(s);
                writer.append('\n');
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
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
                        lines = bubbleSort(lines, line); // считываем остальные строки в цикле
                        //System.out.println(line);
                    }
                }

                //for (String s : lines) { System.out.println(Thread.currentThread().getName()+" - "+s);}

                for (String s : lines) {
                    synchronized (OutFile){
                        OutFile = bubbleSort(OutFile, s);
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

    // выборка пузырьковым
    public static List<String> bubbleSort (List<String> lines, String newString) {
        boolean sorted = false;
        String temp;
        lines.add(newString);
        try {
            while(!sorted) {
                sorted = true;

                //сравнение строк
                for (int i = lines.size()-1; i > 0 ; i--) {

                    //берем 2 строчки и сравниваем их символы внутри
                    int NumChar1=-1, NumChar2=-1;
                    int n=0;
                    do{
                        if(S==true){
                            NumChar1 = Character.getNumericValue(lines.get(i-1).charAt(n));
                            NumChar2 = Character.getNumericValue(lines.get(i).charAt(n));
                        }
                        if(I==true){
                            NumChar1 = Integer.parseInt(lines.get(i-1));
                            NumChar2 = Integer.parseInt(lines.get(i));
                        }
                        n++;
                    } while ((NumChar1==NumChar2)&&(lines.get(i-1).length()!=n)&&(lines.get(i).length()!=n));

                    // проверка большего, выборка пузырьковым
                    if (NumChar1 < NumChar2) {
                        temp = lines.get(i-1);
                        lines.set(i-1,lines.get(i));
                        lines.set(i,temp);
                        sorted = false;
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

    public static List<String> TrasparentList (List<String> lines) {
        List<String> list2 = new ArrayList<String>();
        for (int i = lines.size()-1; i >= 0 ; i--) {
            list2.add(lines.get(i));
        }
        return list2;
    }
}
*/