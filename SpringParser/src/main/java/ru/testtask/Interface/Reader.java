package ru.testtask.Interface;

import java.util.LinkedList;

public interface Reader {
    void objectReader(String string, String filename, int lineNumber);

    LinkedList getList();

}
