package sample;

import java.util.List;

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
