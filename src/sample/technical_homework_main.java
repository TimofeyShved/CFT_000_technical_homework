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

        Scanner scanner = new Scanner(System.in);
        String myOptionsProgramm = scanner.nextLine();  // �������� �� ������ " sort-it.exe -s out.txt in.txt in1.txt in2.txt in3.txt"

        A=true;
        D=false;
        I=false;
        S=true;

        for (int i = 0; i < myOptionsProgramm.length(); i++) {
            if (myOptionsProgramm.charAt(i)=='-'){
                if (myOptionsProgramm.charAt(i+1)=='a'){ // ���������� �� �����������
                    A=true;
                    D=false;
                }
                if (myOptionsProgramm.charAt(i+1)=='i'){ // ���������� int
                    I=true;
                    S=false;
                }
                if (myOptionsProgramm.charAt(i+1)=='d'){ // ���������� �� ��������
                    A=false;
                    D=true;
                }
                if (myOptionsProgramm.charAt(i+1)=='s'){ // ���������� String
                    I=false;
                    S=true;
                }
            }
            if (myOptionsProgramm.charAt(i)=='.'){ // ���������� String
                if (myOptionsProgramm.charAt(i+1)=='t'){ // ���������� String
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

        // ������� ������� �������
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()-1); // ������� �������

        for (int i=1; i<FileNameList.size();i++){
            new InString(FileNameList.get(i), countDownLatch);
        }

        countDownLatch.await(); // ������ �����, ��� ���� ������� ������� �� ���������

        if(A==true){
            OutFile = TrasparentList(OutFile);
        }

        try(FileWriter writer = new FileWriter(FileNameList.get(0), false))
        {
            // ������ ���� ������
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
            this.countDownLatch = countDownLatch; // �������� �������
            start();
        }

        @Override
        public void run() {
            // ���������� ���������� �����
            try {
                File file = new File(FileName);

                //������� ������ FileReader ��� ������� File
                FileReader fr = new FileReader(file);

                //������� BufferedReader � ������������� FileReader ��� ����������� ����������
                BufferedReader reader = new BufferedReader(fr);

                String line;
                List<String> lines = new ArrayList<String>();
                while ((line = reader.readLine()) != null) {
                    if(!line.trim().isEmpty()){
                        lines = bubbleSort(lines, line); // ��������� ��������� ������ � �����
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
            countDownLatch.countDown(); // ���������� � �������� +1
        }
    }

    // ������� �����������
    public static List bubbleSort (List<String> lines, String newString) {
        boolean sorted = false;
        String temp;
        lines.add(newString);
        try {
            while(!sorted) {
                sorted = true;

                //��������� �����
                for (int i = lines.size()-1; i > 0 ; i--) {

                        //����� 2 ������� � ���������� �� ������� ������
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

                        // �������� ��������, ������� �����������
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

    public static List TrasparentList (List<String> lines) {
        List<String> list2 = new ArrayList<String>();
        for (int i = lines.size()-1; i >= 0 ; i--) {
            list2.add(lines.get(i));
        }
        return list2;
    }
}
