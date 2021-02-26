package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Controller {
    @FXML public Label status;
    @FXML public TextArea outNameFileMemo;
    @FXML public TextField inNameFile;
    @FXML public TextField outNameFile;
    @FXML public Button button1;
    @FXML public Button button2;

    static List<String> FileNameList = new ArrayList<String>(); // список имён файлов
    static String myOptionsProgrammString;

    protected int sh=0;
    @FXML
    public void load(MouseEvent mouseEvent) {
        myOptionsProgrammString=inNameFile.getText();
        System.out.println(myOptionsProgrammString);
        FileNameList = new ArrayList<String>();
        status.setText("загружен.");
    }

    @FXML
    public void start(MouseEvent mouseEvent) throws Exception {
        technical_homework_main.OutFile = Collections.synchronizedList(new ArrayList<String>());
        technical_homework_main.MyOptionsProgramm myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted();

        // создаем счётчик потоков
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()); // создаем счётчик

        for (int i=0; i<FileNameList.size();i++){
            new technical_homework_main.InString(FileNameList.get(i), countDownLatch); // запихиваем в цикле, наши файлы в потоки, а так же присваиваем нашим потокам счётчик
        }

        countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

        //------------------------------------------------------------------------------------ЖДЁМ ВЫПОЛНЕНИЯ ПОТОКА------------------------------------------

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            technical_homework_main.OutFile = technical_homework_main.TrasparentList(technical_homework_main.OutFile); // разворачиваем наш список в другую сторону
        }

        outNameFileMemo.setText("");

        for (String s : technical_homework_main.OutFile){
            outNameFileMemo.appendText(s+"\n");
        }
        status.setText("обработан.");
    }

    @FXML
    public void save(MouseEvent mouseEvent) {
        status.setText("нет действий.");
        // запись данных в файл
        try(FileWriter writer = new FileWriter(outNameFile.getText(), false))
        {
            // запись всей строки
            for (String s : technical_homework_main.OutFile) { // пока не закончится наш список
                writer.write(s); // записываем строку
                writer.append('\n'); // переходим на новую строчку
            }
            writer.flush(); // перезапись файла
            status.setText("сохранён.");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage()); // вывод ошибок при работе с файлом
        }
    }
}
