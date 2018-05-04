package ru.testtask.writer;

import org.json.simple.JSONValue;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.testtask.Interface.Writer;

import java.util.*;


@Component
@Scope("prototype")
public class JsonWriter implements Writer {


    final private List<String> ARRAY = Arrays.asList("id", "amount", "comment", "filename", "line", "result");

    @Override
    public void Printer(LinkedList list) {

        Map object = new LinkedHashMap();
        if (list.size() == 3) {
            for (int i = 0; i < 6; i++) {
                while (i<3){
                    object.put(ARRAY.get(i), null);
                    i++;
                }
                object.put(ARRAY.get(i), list.get(i-3));
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                object.put(ARRAY.get(i), list.get(i));
            }
        }
        String jsonText = JSONValue.toJSONString(object);
        System.out.println(jsonText);


    }


}
