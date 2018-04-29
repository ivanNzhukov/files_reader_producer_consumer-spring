package ru.testtask.readers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.testtask.Interface.Reader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("prototype")
public class JsonReader implements Reader {


    final private List<String> ARRAY = Arrays.asList("orderId", "amount", "comment");
    LinkedList linkedList = new LinkedList();

    @Override
    public LinkedList getList() {
        return linkedList;
    }

    private void setList(LinkedList list) {

        linkedList.addAll(list);

    }

    @Override
    public void objectReader(String string, String filename, int lineNumber) {
        linkedList.clear();
        String result = "";
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;

        try {
            object = (JSONObject) jsonParser.parse(string);

            for (int i = 0; i < ARRAY.size(); i++) {

                try {
                    linkedList.add(object.get(ARRAY.get(i)).toString());

                } catch (Exception e) {
                    linkedList.add(null);
                    result += "Колонка \'" + ARRAY.get(i) + "\' названа не правильно или отсутствует; ";
                }
            }
        } catch (ParseException e) {
            result = "Ошибка в строке, возможно вы используете массив, а не строку ";
        }
        linkedList.add(filename);
        linkedList.add(lineNumber);
        linkedList.add(result.equals("") ? "OK" : result);


    }
}


