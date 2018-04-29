package ru.testtask.readers;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.testtask.Interface.Reader;

import java.util.LinkedList;

@Component
@Scope("prototype")
public class XlsxReader implements Reader {


    LinkedList linkedList = new LinkedList();

    public LinkedList getList() {
        return linkedList;
    }


    @Override
    public void objectReader(String string, String filename, int lineNumber) {

        String result = "";
        linkedList.clear();

        try {
            String[] array = string.split(",");

            for (int i = 0; i < array.length; i++) {
                if (i == 4) throw new Exception("Не корректный формат строки");
                else if (i != 2) {
                    linkedList.add(array[i]);
                }

            }
        } catch (Exception e) {
            result += "Не корректный формат строки";
        }
        linkedList.add(filename);
        linkedList.add(lineNumber);
        linkedList.add(result.equals("") ? "OK" : result);


    }
}
