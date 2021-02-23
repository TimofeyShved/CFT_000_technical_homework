package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {
    @FXML public TextArea text1;
    @FXML public TextArea text2;
    @FXML public TextField ta;
    @FXML public TextField tb;
    @FXML public Button button1;
    @FXML public Button button2;

    protected int sh=0;
    @FXML
    public void handleReset(MouseEvent mouseEvent) {
        //внутренний массив с которым ми работаем
        char[] digits= new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g','h', 'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] digits2= new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g','h', 'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        String t1=text1.getText(), t2="", t3="";
        int a=0,b=0,k=0;
        for (int i=0;i<digits.length;i++){
            if (t1.charAt(0) == digits[i]) a=i;
            if (t1.charAt(1) == digits[i]) b=i;
        }
        ta.setText(""+a);
        tb.setText(""+b);

        for (int i=0;i<t1.length();i++){
            for (int j=0;j<digits.length;j++){
                if (t1.charAt(i) == digits[j]) {
                    k=j; break;
                } else {
                    k=-100;
                }
            }
            if(k==-100){
                t2+=t1.charAt(i);
            } else {
                t2+=digits[((b*(k+digits.length-a))%digits.length)];
            }
        }
        System.out.println(sh+"");
        for (int i=0;i>sh;i--){
            for (int j=0;j<digits.length;j++){
                if (j==digits.length-1) {
                    digits[j]=digits[0];
                } else {
                    digits[j]=digits[j+1];
                }
            }
        }
        for (int i=0;i<t2.length();i++){
            for (int j=0;j<digits2.length;j++){
                if (t2.charAt(i) == digits2[j])  {
                    k=j; break;
                } else {
                    k=-100;
                }
            }
            if(k==-100){
                t3+=t2.charAt(i);
            } else {
                t3+=digits[k];
            }
        }
        text2.setText(t3);
    }

    @FXML
    public void shift(MouseEvent mouseEvent) {
        sh--;
    }
}
