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

    //------------------------------------------------------------------------------------Загрузка------------------------------------------------------------------
    @FXML
    public void load(MouseEvent mouseEvent) {
        myOptionsProgrammString=inNameFile.getText(); // получаем строчку с настройками и именем файлов. Например: -i -d in1.txt in2.txt in3.txt
        FileNameList = new ArrayList<String>(); // передаем его в список

        status.setText("загружен.");
    }

    //------------------------------------------------------------------------------------Обработка------------------------------------------------------------------
    @FXML
    public void start(MouseEvent mouseEvent) throws Exception {
        technical_homework_main.OutFile = Collections.synchronizedList(new ArrayList<String>()); // обнуляем итоговый список

        // закидываем в статический класс наши опции-настройки, имена файлов
        technical_homework_main.MyOptionsProgramm myOptionsProgramm = new technical_homework_main.MyOptionsProgramm(myOptionsProgrammString);
        FileNameList = myOptionsProgramm.MySorted(); // сортируем наши файлы

        // создаем счётчик потоков
        CountDownLatch countDownLatch = new CountDownLatch(FileNameList.size()); // создаем счётчик

        for (int i=0; i<FileNameList.size();i++){
            // запихиваем в цикле, наши файлы в потоки, а так же присваиваем нашим потокам счётчик
            new technical_homework_main.InString(FileNameList.get(i), countDownLatch);
        }

        countDownLatch.await(); // данный поток, ждёт пока счётчик потоков не зоплнится

        //--------------------------------------ЖДЁМ ВЫПОЛНЕНИЯ ВСЕХ ПОТОКОВ------------------------------------------

        if(technical_homework_main.A==true){ // сортировка по возростанию true
            // тогда разворачиваем наш список в другую сторону
            technical_homework_main.OutFile = technical_homework_main.TrasparentList(technical_homework_main.OutFile);
        }

        outNameFileMemo.setText(""); // обнуляем поле МЕМО

        for (String s : technical_homework_main.OutFile){ // закидываем в него полученный файл, для визуальной состовляющей программы
            outNameFileMemo.appendText(s+"\n");
        }
        status.setText("обработан.");
    }

    //------------------------------------------------------------------------------------Сохранение------------------------------------------------------------------
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
