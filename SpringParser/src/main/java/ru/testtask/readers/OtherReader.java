package ru.testtask.readers;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.testtask.Interface.Reader;

import java.util.ArrayList;
import java.util.LinkedList;

@Component
@Scope("prototype")
public class OtherReader implements Reader {

    private LinkedList linkedList;
    private ArrayList column;

    public LinkedList getList() {
        return linkedList;
    }


    @Override
    public void objectReader(String string, String filename, int lineNumber) {
        int i = 0;
        String result = "";
        linkedList = new LinkedList();
        column = new ArrayList();


        String[] array = string.split(",");

        try {
            for (i = 0; i < array.length; i++) {
                try {
                    if (i < 2) {
                        if (NumberUtils.isDigits(array[i]))
                            linkedList.add(array[i]);
                        else
                            throw new NumberFormatException();
                    } else if (i == 2) {
                        continue;
                    } else if (i > 3) {
                        throw new IllegalArgumentException();
                    } else if (array[i].equals("")) {
                        throw new NullPointerException();
                    } else
                        linkedList.add(array[i]);

                } catch (NumberFormatException nfe) {
                    linkedList.add(null);
                    result += "Столбец № " + (i + 1) + " не число; ";
                } catch (NullPointerException npe) {
                    linkedList.add(null);
                    column.add(i + 1);
                    result += "Столбец № " + (i + 1) + " пуст; ";
                }
            }
        } catch (ArrayIndexOutOfBoundsException ai) {
            for (int j = 0; j < 3; j++) {
                linkedList.add(null);
            }
            result += "Пустая строка ";
        } catch (IllegalArgumentException iae) {
            column.add(i + 1);
            result += "Количество столбцов больше 4 из-за № " + column.toString();

        } catch (Exception e) {
            result += "Ошибка " + e;
        }
        linkedList.add(filename);
        linkedList.add(lineNumber);
        linkedList.add(result.equals("") ? "OK" : result);

    }


}
